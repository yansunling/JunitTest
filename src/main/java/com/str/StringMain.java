package com.str;

public class StringMain {
    public static void main(String[] args) {
        String errorMsg="%s带饭";
        errorMsg=String.format(errorMsg,"测试");
        System.out.println(errorMsg);
    }
}
