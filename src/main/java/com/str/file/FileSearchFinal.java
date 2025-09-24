package com.str.file;

import cn.hutool.core.date.DateUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearchFinal {

    private static int ROOT_LENGTH = 6;
    private static String FILE_NAME = "query";
    private static String FILE_TYPE = "pptx";
    
    // 使用线程安全的结果集合
    private static final List<Path> results = new ArrayList<>();
    private static final Object resultsLock = new Object();
    
    // 添加计数器来跟踪完成的任务
    private static final AtomicInteger completedTasks = new AtomicInteger(0);
    private static volatile int totalTasks = 0;
    
    // 使用阻塞队列来管理任务
    private static final BlockingQueue<File> taskQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws Exception {
        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 获取系统根目录
        File[] roots = File.listRoots();
        System.out.println("检测到 " + (roots != null ? roots.length : 0) + " 个磁盘驱动器");
        
        // 收集所有一级子目录
        for (File root : roots) {
            File[] subDirs = root.listFiles(File::isDirectory);
            if (subDirs != null) {
                Collections.addAll(taskQueue, subDirs);
            }
        }
        
        totalTasks = taskQueue.size();
        ROOT_LENGTH = ROOT_LENGTH - 1;

        // 使用固定大小的线程池
        int threadCount = Math.min(totalTasks, Runtime.getRuntime().availableProcessors() * 10);
        ExecutorService sharedExecutor = Executors.newFixedThreadPool(threadCount);
        
        // 提交所有任务
        CountDownLatch countDownLatch = new CountDownLatch(totalTasks);
        
        // 提交工作线程
        for (int i = 0; i < threadCount; i++) {
            sharedExecutor.submit(new Worker(countDownLatch));
        }

        // 启动监控线程
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) sharedExecutor;
        new Thread(() -> {
            while (true) {
                try {
                    int activeCount = threadPool.getActiveCount();
                    int completed = completedTasks.get();
                    if (activeCount > 0 || completed < totalTasks) {
                        System.out.printf(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + 
                            " 活跃线程: %d, 总线程: %d, 完成任务: %d/%d, 待处理: %d\n",
                            activeCount, threadPool.getPoolSize(), completed, totalTasks, taskQueue.size());
                        TimeUnit.MILLISECONDS.sleep(5000);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        countDownLatch.await();
        
        // 输出结果
        System.out.println("搜索到 " + results.size() + " 个文件:");
        synchronized (resultsLock) {
            for (int i = 0; i < Math.min(results.size(), 100); i++) { // 限制输出前100个结果
                System.out.println((i + 1) + ". " + results.get(i));
            }
            if (results.size() > 100) {
                System.out.println("... 还有 " + (results.size() - 100) + " 个文件");
            }
        }
        
        // 计算耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("搜索耗时: " + duration/1000 + " 秒");
        
        sharedExecutor.shutdownNow();
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;
        
        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }
        
        @Override
        public void run() {
            File root;
            while ((root = taskQueue.poll()) != null) {
                try {
                    List<Path> paths = searchDirFile(root.toPath(), ROOT_LENGTH);
                    if (paths != null && !paths.isEmpty()) {
                        synchronized (resultsLock) {
                            results.addAll(paths);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("处理目录时出错: " + root.getAbsolutePath() + " - " + e.getMessage());
                } finally {
                    latch.countDown();
                    completedTasks.incrementAndGet();
                }
            }
        }
    }

    /**
     * 程序入口点。
     * 遍历指定目录结构并在控制台输出路径。
     *
     * @param args 命令行参数（未使用）
     */
    public static List<Path> searchDirFile(Path rootDir, int maxDepth) {
        String fileName = FILE_NAME.toLowerCase(); // 转换为小写以支持不区分大小写的搜索
        String fileType = "." + FILE_TYPE.toLowerCase(); // 确保扩展名以点开头
        List<Path> localResults = new ArrayList<>();

        try {
            // 使用 Files.walkFileTree 跳过无法访问的目录
            Files.walkFileTree(rootDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), maxDepth,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            // 直接在目录访问时搜索文件，而不是递归调用
                            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                                for (Path file : stream) {
                                    // 只处理常规文件
                                    if (Files.isRegularFile(file)) {
                                        String name = file.getFileName().toString().toLowerCase();
                                        // 先检查文件扩展名，再检查文件名（更高效）
                                        if (name.endsWith(fileType) && name.contains(fileName)) {
                                            localResults.add(file.toAbsolutePath());
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                // 忽略无法访问的目录
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            // 当访问文件失败时（如权限不足），跳过并继续
                            if (exc instanceof AccessDeniedException) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }

                            String dirName = file.getFileName().toString().toLowerCase();
                            if (dirName.equals("$recycle.bin") ||
                                    dirName.equals("system volume information") ||
                                    dirName.contains("window") ||
                                    dirName.contains("program") ||
                                    dirName.equals("temp")) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        

                        public FileVisitResult preVisitDirectoryFailed(Path dir, IOException exc) throws IOException {
                            // 当访问目录失败时，跳过整个子树
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                    });
        } catch (IOException e) {
            // 处理IO异常
            System.err.println("访问文件系统时发生错误: " + e.getMessage());
        }
        return localResults;
    }
}