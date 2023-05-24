package com.dy.annotation;



public class AnnotationTest {
    public static void main(String[] args) {
        Class<User> clazz = User.class;
        // 获取类注解
        TaskDesc annotation = clazz.getAnnotation(TaskDesc.class);
        System.out.println(annotation.desc());
    }
}