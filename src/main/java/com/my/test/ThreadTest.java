package com.my.test;

import com.mchange.v2.lang.ThreadUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) throws Exception{
        Thread a = new Thread(()->{
            try {
                for(int i=0;i<1000;i++){
                    System.out.println(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName()+" done");
        });

        Thread b = new Thread(()->{
            try {
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName()+" done");
        });

        a.setName("a");
        b.setName("b");

        a.start();
        b.start();


        List<Thread> threads = getThreads();
        threads.forEach(System.out::println);
        for (Thread thread : threads) {
            // 杀掉名称为a的线程
            if (Objects.equals("a",thread.getName())){
                // 不推荐 stop,最好是中断,如果需要强制停掉,stop也尚可
//        thread.stop();
                thread.interrupt();
                System.out.println("kill thread for name 'a'");
            }
        }

        System.out.println("exec done");
       Thread.sleep(10000L);
    }

    public static List<Thread> getThreads() throws Exception {
        Method getThreads = Thread.class.getDeclaredMethod("getThreads");
        getThreads.setAccessible(true);
        Thread[] threads = (Thread[]) getThreads.invoke(null);
        return Arrays.asList(threads);
    }

}

