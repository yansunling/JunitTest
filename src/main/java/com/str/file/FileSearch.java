package com.str.file;

import cn.hutool.core.date.DateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearch {
    // 固定搜索条件
    private static final String FILE_NAME = "query";
    private static final String FILE_TYPE = "pptx";

    private static final int MAX_COUNT=40;
    
    // 高性能线程数
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 1000;
    
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


        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) sharedExecutor;
        while(true){
            if(results.size()>=MAX_COUNT){
                break;
            }
            int activeCount = threadPool.getActiveCount();
            if(activeCount>0){
                System.out.printf(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" 活跃线程: %d, 总线程: %d \n",activeCount, threadPool.getPoolSize());
                Thread.sleep(5000L);
            }else{
                break;
            }
        }
        // 计算耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
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
        System.out.println("  搜索耗时: " + duration/1000 + " 秒");
        System.out.println("  处理速度: " + (filesProcessed.get() * 1000 / Math.max(1, duration)) + " 文件/秒");


        System.out.println("-----------end---------------");

        // 关闭线程池
        sharedExecutor.shutdownNow();
        killJava();
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
            if (!directory.exists() || !directory.isDirectory() || results.size() >= MAX_COUNT) {
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
                if (results.size() >= MAX_COUNT) {
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
                            if (results.size() < MAX_COUNT) {
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
            if (results.size() >= MAX_COUNT) {
                return;
            }

            // 高并发处理子目录（每个目录一个任务）

            for (File subDir : subDirectories) {
                if (results.size() >= MAX_COUNT) {
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
        } catch (Exception e) {
            // 忽略访问权限等问题，继续搜索其他目录
        }
    }


    public static void killJava() {
        try {
            // 1. 获取当前操作系统类型
            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("当前操作系统：" + os);

            // 2. 根据系统类型执行不同命令，获取Java进程PID
            List<String> javaPids = getJavaProcessPids(os);
            if (javaPids.isEmpty()) {
                System.out.println("未找到正在运行的Java进程");
                return;
            }
            // 3. 强制终止所有Java进程
            killProcesses(os, javaPids);

        } catch (Exception e) {
            System.err.println("操作失败：" + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * 获取所有Java进程的PID
     */
    private static List<String> getJavaProcessPids(String os) throws IOException {
        List<String> pids = new ArrayList<>();
        Process process;

        if (os.contains("windows")) {
            // Windows系统：通过tasklist命令查找java进程
            process = Runtime.getRuntime().exec("tasklist /fi \"imagename eq java.exe\" /fo csv /nh");
        } else if (os.contains("linux") || os.contains("mac")) {
            // Linux/macOS系统：通过ps命令查找java进程
            process = Runtime.getRuntime().exec("ps -ef | grep java | grep -v grep");
        } else {
            throw new UnsupportedOperationException("不支持的操作系统");
        }

        // 解析命令输出，提取PID
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // 提取PID（Windows和Linux格式不同）
                if (os.contains("windows")) {
                    // Windows输出格式："java.exe","1234","Console","1","123,456 K"
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String pid = parts[1].replace("\"", "").trim();
                        pids.add(pid);
                    }
                } else {
                    // Linux/mac输出格式：user 1234 567 0 ... java ...
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        pids.add(parts[1]); // 第二个字段是PID
                    }
                }
            }
        }

        return pids;
    }

    /**
     * 强制终止指定PID的进程
     */
    private static void killProcesses(String os, List<String> pids) throws IOException {
        for (String pid : pids) {
            System.out.println("正在终止Java进程，PID：" + pid);
            Process killProcess;

            if (os.contains("windows")) {
                // Windows：强制终止进程（/f表示强制，/pid指定PID）
                killProcess = Runtime.getRuntime().exec("taskkill /f /pid " + pid);
            } else {
                // Linux/macOS：强制终止进程（-9表示强制信号）
                killProcess = Runtime.getRuntime().exec("kill -9 " + pid);
            }

            // 等待命令执行完成
            try {
                int exitCode = killProcess.waitFor();
                if (exitCode == 0) {
                    System.out.println("成功终止PID：" + pid);
                } else {
                    System.err.println("终止PID：" + pid + "失败，退出码：" + exitCode);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("终止进程时被中断：" + pid);
            }
        }
    }













}
