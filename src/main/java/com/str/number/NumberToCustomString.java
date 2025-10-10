package com.str.number;

public class NumberToCustomString {
    // 字符序列：0-9 -> a-z -> A-Z（共62个字符）
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RADIX = CHARACTERS.length(); // 基数为62

    /**
     * 将整数转换为指定长度的自定义字符串（符合9→a→z→A规则）
     * @param number 输入整数（非负整数）
     * @param length 输出字符串长度（如6）
     * @return 转换后的字符串，若超出最大范围则返回null
     */
    public static String convert(long number, int length) {
        // 校验输入
        if (number < 0) {
            throw new IllegalArgumentException("输入整数必须是非负的");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        // 计算最大可表示的数值（62^length - 1）
        long maxValue = (long) (Math.pow(RADIX, length) - 1);
        if (number > maxValue) {
            System.out.println("超出最大可表示范围（最大数值为：" + maxValue + "）");
            return null;
        }

        // 转换逻辑：类似进制转换，将number转为62进制，再映射到字符集
        char[] result = new char[length];
        long num = number;

        // 从最后一位开始计算（右侧为低位）
        for (int i = length - 1; i >= 0; i--) {
            // 取余得到当前位的索引
            int index = (int) (num % RADIX);
            result[i] = CHARACTERS.charAt(index);
            // 整除进入下一位（高位）
            num = num / RADIX;
        }

        return new String(result);
    }

    // 测试示例
    public static void main(String[] args) {
        int length = 6;

        // 测试关键转换点
//        System.out.println("整数9对应的6位字符串：" + convert(9, length));      // 000009
//        System.out.println("整数10对应的6位字符串：" + convert(10, length));    // 00000a
        for(int i=0;i<62;i++){
            System.out.println("整数"+i+"对应的6位字符串：" + convert(i, length));    // 00000z
        }


//        System.out.println("整数36对应的6位字符串：" + convert(36, length));    // 00000A
//        System.out.println("整数61对应的6位字符串：" + convert(61, length));    // 00000Z
//        System.out.println("整数62对应的6位字符串：" + convert(62, length));    // 000010
//        System.out.println("整数123对应的6位字符串：" + convert(123, length));  // 00001z

        // 测试较大的数
//        System.out.println("整数100000对应的6位字符串：" + convert(352424, length));
    }
}

