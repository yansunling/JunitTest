package com.dy.test.doc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LinkedBlockingQueueTest {

    public static void main(String[] args) {


        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());

                return null;
            }
        };




    }

}
