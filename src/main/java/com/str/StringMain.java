package com.str;

import com.google.common.base.Strings;

import java.text.DecimalFormat;

public class StringMain {
    public static void main(String[] args) {
        String errorMsg="%s带饭";
        errorMsg=String.format(errorMsg,"测试");
        System.out.println(errorMsg);

//        Double t=2.3;
        String t="3";
        System.out.println(String.format("%.2f",10.0));

        String message = String.format("Value: %d, Float: %.2f, Hex: %x, Bool: %b, Char: %c",
                5,
                12222222223.456,
                255,
                true,
                'A');
        System.out.println(message);


        double number = 123.001789;

        // 创建 DecimalFormat 对象，指定格式模式
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(number);

        System.out.println("格式化后的数字: " + formattedNumber);



    }
}
