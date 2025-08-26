package com.str.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearch {
    // 固定搜索条件
    private static final String FILE_NAME = "query";
    private static final String FILE_TYPE = "pptx";
    
    // 高性能线程数
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 100;
    
    // 性能计数器
    private static final AtomicInteger directoriesProcessed = new AtomicInteger(0);
    private static final AtomicInteger filesProcessed = new AtomicInteger(0);
    
    // 共享线程池
    private static ExecutorService sharedExecutor;
    
    public static void main(String[] args) throws Exception{
        System.out.println("开始极速多线程全盘搜索文件...");
        System.out.println("搜索条件: 文件名包含 \"" + FILE_NAME + "\", 文件类型 \"" + FILE_TYPE + "\"");
        System.out.println("使用线程数: " + THREAD_POOL_SIZE);
        
        // 初始化共享线程池
        sharedExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 搜索文件
        List<String> results = searchFiles();
        

        
        // 计算耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        for(int j=0;j<10;j++){
            if(results.size()>=20){
                break;
            }
            Thread.sleep(10000L);
        }

        // 输出结果和性能统计
        System.out.println("\n========== 搜索结果 ==========");
        System.out.println("总共找到 " + results.size() + " 个匹配的文件:");
        if (results.isEmpty()) {
            System.out.println("未找到匹配的文件。");
            System.out.println("\n为了测试程序是否正常工作，请尝试以下操作:");
            System.out.println("1. 在桌面或文档目录中创建一个名为 \"my_query_file.pptx\" 的文件");
            System.out.println("2. 或者修改程序中的搜索条件为系统中确实存在的文件");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
        System.out.println("==============================");
        System.out.println("性能统计:");
        System.out.println("  处理目录数: " + directoriesProcessed.get());
        System.out.println("  处理文件数: " + filesProcessed.get());
        System.out.println("  搜索耗时: " + duration + " 毫秒");
        System.out.println("  处理速度: " + (filesProcessed.get() * 1000 / Math.max(1, duration)) + " 文件/秒");


        System.out.println("-----------end---------------");

        // 关闭线程池
        sharedExecutor.shutdownNow();

    }
    
    /**
     * 极速多线程全盘搜索文件
     * @return 匹配的文件列表
     */
    private static List<String> searchFiles() {
        List<String> results = new ArrayList<>();
        List<Future<Void>> futures = new ArrayList<>();
        
        try {
            // 获取系统根目录
            File[] roots = File.listRoots();
            System.out.println("检测到 " + (roots != null ? roots.length : 0) + " 个磁盘驱动器");
            
            if (roots != null) {
                // 为每个根目录创建一个搜索任务
                for (File root : roots) {
                    if (root.exists()) {
                        System.out.println("启动驱动器搜索任务: " + root.getAbsolutePath());
                        Future<Void> future = sharedExecutor.submit(() -> {
                            searchDirectoryUltraFast(root, results);
                            return null;
                        });
                        futures.add(future);
                    }
                }
            }

            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) sharedExecutor;

            new Thread(() -> {
                try {
                    // 每隔0.5秒打印一次线程状态
                    for (int i = 0; i < 10; i++) {
                        int activeCount = threadPool.getActiveCount(); // 活跃线程数（正在运行）
                        int poolSize = threadPool.getPoolSize();       // 总线程数（活跃+空闲）
                        int coreSize = threadPool.getCorePoolSize();   // 核心线程数
                        int maxSize = threadPool.getMaximumPoolSize(); // 最大线程数

                        System.out.printf(
                                "活跃线程: %d, 总线程: %d, 核心线程: %d, 最大线程: %d%n",
                                activeCount, poolSize, coreSize, maxSize
                        );
                        TimeUnit.MILLISECONDS.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();




            // 等待所有任务完成
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    System.err.println("搜索任务超时或出错: " + e.getMessage());
                    future.cancel(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("搜索过程中发生错误: " + e.getMessage());
        }
        
        return results;
    }
    
    /**
     * 超高速递归搜索目录
     * @param directory 要搜索的目录
     * @param results 结果列表
     */
    private static void searchDirectoryUltraFast(File directory, List<String> results) {
        try {
            // 基本检查
            if (!directory.exists() || !directory.isDirectory() || results.size() >= 20) {
                return;
            }
            
            // 性能统计
            directoriesProcessed.incrementAndGet();
            
            // 获取目录内容
            File[] files = directory.listFiles();
            if (files == null) {
                return;
            }
            
            // 分离文件和目录以优化处理
            List<File> regularFiles = new ArrayList<>();
            List<File> subDirectories = new ArrayList<>();
            
            for (File file : files) {
                if (file.isFile()) {
                    regularFiles.add(file);
                } else if (file.isDirectory()) {
                    subDirectories.add(file);
                }
            }
            
            // 高并发处理文件（每个文件一个任务）
            List<Future<Void>> fileFutures = new ArrayList<>();
            for (File file : regularFiles) {
                if (results.size() >= 20) {
                    break;
                }
                Future<Void> future = sharedExecutor.submit(() -> {
                    filesProcessed.incrementAndGet();
                    String fileName = file.getName();
                    String lowerFileName = fileName.toLowerCase();
                    
                    // 检查文件扩展名
                    if (lowerFileName.endsWith("." + FILE_TYPE.toLowerCase())) {
                        // 检查文件名是否匹配
                        if (FILE_NAME.isEmpty() || lowerFileName.contains(FILE_NAME.toLowerCase())) {
                            if (results.size() < 20) {
                                results.add(file.getAbsolutePath());
                                System.out.println("  *** [" + Thread.currentThread().getName() + "] 发现匹配文件: " + fileName);
                            }
                        }
                    }
                    return null;
                });
                fileFutures.add(future);
            }
            // 如果已经找到足够结果，直接返回
            if (results.size() >= 20) {
                return;
            }

            // 高并发处理子目录（每个目录一个任务）

            for (File subDir : subDirectories) {
                if (results.size() >= 20) {
                    break;
                }
                
                // 排除一些系统目录
                String dirName = subDir.getName().toLowerCase();
                String[] split = subDir.getAbsolutePath().split("\\\\");

                if (!dirName.equals("$recycle.bin") && 
                    !dirName.equals("system volume information") &&
                    split.length<8&&
                    dirName.indexOf("window")<0&&
                    dirName.indexOf("Program")<0&&
                    !dirName.equals("temp")) {
                    Future<Void> future = sharedExecutor.submit(() -> {
                        searchDirectoryUltraFast(subDir, results);
                        return null;
                    });
                    fileFutures.add(future);
                }
            }
            // 等待目录处理完成（短超时）
//            for (Future<Void> future : fileFutures) {
//                try {
//                    future.get();
//                } catch (Exception e) {
//                    future.cancel(true);
//                }
//            }
        } catch (Exception e) {
            // 忽略访问权限等问题，继续搜索其他目录
        }
    }
}
