package com.javaBase.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class CountdownLatchTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(3);
        int j=3;
        List<FutureTask<String>> list=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            /*Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (j * 1000));
                        System.out.println("子线程"+Thread.currentThread().getName()+"执行完成");
                        latch.countDown();//当前线程调用此方法，则计数减一
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };*/

            FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep((long) (j * 1000));
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完成");
                    latch.countDown();
                    return UUID.randomUUID().toString();
                }
            });
            list.add(futureTask);
            service.submit(futureTask);
        }

        try {
            System.out.println("主线程"+Thread.currentThread().getName()+"等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0


            for(FutureTask<String> task:list){
                System.out.println(task.get());
            }


            System.out.println("主线程"+Thread.currentThread().getName()+"开始执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
