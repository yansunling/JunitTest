package com.str.file;

import com.yd.utils.common.CollectionUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSearchFinal {

    private static int ROOT_LENGTH=6;

    private static String FILE_NAME="query";
    private static String FILE_TYPE="pptx";

    public static void main(String[] args) throws Exception {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        ExecutorService sharedExecutor = Executors.newFixedThreadPool(10);
        // 获取系统根目录
        File[] roots = File.listRoots();
        List<Path> results = new ArrayList<>();
        System.out.println("检测到 " + (roots != null ? roots.length : 0) + " 个磁盘驱动器");
        List<File> fileList = Arrays.asList(roots);
        CountDownLatch countDownLatch = new CountDownLatch(fileList.size());
        System.out.println("size:"+fileList.size());
        // 为每个根目录创建一个搜索任务
        for (File root : fileList) {
            System.out.println("启动驱动器搜索任务: " + root.getAbsolutePath());
            sharedExecutor.submit(() -> {
                List<Path> paths = searchDirFile(root.toPath(), ROOT_LENGTH);
                if (CollectionUtil.isNotEmpty(paths)) {
                    results.addAll(paths);
                }
                countDownLatch.countDown();
                return null;
            });
        }
        countDownLatch.await();
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }
        // 计算耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("  搜索耗时: " + duration/1000 + " 秒");
        sharedExecutor.shutdownNow();


    }


    /**
     * 程序入口点。
     * 遍历指定目录结构并在控制台输出路径。
     *
     * @param args 命令行参数（未使用）
     */
    public static  List<Path>  searchDirFile(Path rootDir,int maxDepth) {

        String fileName=FILE_NAME;
        String fileType=FILE_TYPE;
        List<Path> results=new ArrayList<>();

        try {
            // 使用 Files.walkFileTree 跳过无法访问的目录
            long startTime = System.currentTimeMillis();
            Files.walkFileTree(rootDir, EnumSet.noneOf(FileVisitOption.class), maxDepth,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            // 打印当前目录路径
                            List<Path> filePaths = searchFiles(dir, fileName, fileType);
                            if(CollectionUtil.isNotEmpty(filePaths)){
                                results.addAll(filePaths);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            // 当访问文件失败时（如权限不足），打印警告并继续
                            if (exc instanceof AccessDeniedException) {
                                System.err.println("警告：无法访问目录（权限不足）: " + file);
                                return FileVisitResult.SKIP_SUBTREE;
                            }

                            String dirName = file.getFileName().toString();
                            if (!dirName.equals("$recycle.bin") &&
                                    !dirName.equals("system volume information") &&
                                    dirName.indexOf("window")<0&&
                                    dirName.indexOf("Program")<0&&
                                    !dirName.equals("temp")) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });

            long endTime = System.currentTimeMillis();
            System.out.println(rootDir+"遍历完成，耗时: " + (endTime - startTime) + " 毫秒");
        } catch (IOException e) {
            // 处理IO异常
            System.err.println("访问文件系统时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        return results;



    }



    /**
     * 根据路径、文件名和文件类型搜索文件
     *
     * @param path 搜索的根路径
     * @param fileName 要搜索的文件名（包含匹配）
     * @param fileType 文件类型（扩展名，例如 ".txt"）
     * @return 匹配的文件路径列表
     * @throws IOException 当访问文件系统时出现问题
     */
    public static List<Path> searchFiles(Path path, String fileName, String fileType) throws IOException {
        // 参数验证
        if (path == null) {
            throw new IllegalArgumentException("搜索路径不能为null");
        }
        List<Path> result = new ArrayList<>();
        // 使用并行流来提高搜索效率
        try {
            result = Files.walk(path,1)
                    .parallel()
                    .filter(Files::isRegularFile)  // 只处理常规文件
                    .filter(file -> {
                        String name = file.getFileName().toString();
                        return name.contains(fileName) && name.endsWith(fileType);
                    })
                    // 添加异常处理
                    .filter(file -> {
                        try {
                            return Files.exists(file);
                        } catch (SecurityException e) {
                            // System.err.println("警告：无法访问文件（权限不足）: " + file);
                            return false;
                        }
                    })
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (java.nio.file.AccessDeniedException e) {
            // 特别处理访问被拒绝异常
            System.err.println("访问被拒绝: " + e.getMessage());
            // 回退到传统方法
            result.clear();
            walkFileTreeFallback(path, fileName, fileType, result);
        } catch (Exception e) {
            // 如果Files.walk出现问题，回退到原来的实现
            System.err.println("并行搜索失败，回退到传统方法: " + e.getMessage());
            result.clear();
            walkFileTreeFallback(path, fileName, fileType, result);
        }

        return result;
    }

    /**
     * 回退到传统Files.walkFileTree方法的实现
     *
     * @param path 搜索的根路径
     * @param fileName 要搜索的文件名
     * @param fileType 文件类型
     * @param result 结果列表
     * @throws IOException 当访问文件系统时出现问题
     */
    private static void walkFileTreeFallback(Path path, String fileName, String fileType, List<Path> result) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = file.getFileName().toString();
                // 检查文件名是否包含指定的fileName并且文件扩展名是否匹配
                if (name.contains(fileName) && name.endsWith(fileType)) {
                    result.add(file.toAbsolutePath());
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // 当访问文件失败时，跳过该文件并继续
                if (exc instanceof AccessDeniedException) {
                    // System.err.println("警告：无法访问文件（权限不足）: " + file);
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}