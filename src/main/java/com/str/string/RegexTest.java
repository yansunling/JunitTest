package com.str.string;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) throws Exception{
        String content = "本次出行共计收费50元，其中落地费30元。";
        String regex = "(?<=落地费)\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String result = matcher.group();
            System.out.println("落地费金额是：" + result);
        } else {
            System.out.println("未匹配到落地费金额。");
        }
        AtomicInteger ai=new AtomicInteger(0);


        ExecutorService executorService= Executors.newFixedThreadPool(50);

        CountDownLatch countDownLatch = new CountDownLatch(50);

        for(int i=0;i<50;i++){
            executorService.submit(new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(countDownLatch.getCount());
                    countDownLatch.countDown();
                    return null;
                }
            }));
        }
        countDownLatch.await();

        System.out.println("last_num:"+ai.intValue());
        executorService.shutdown();

    }
}
