package com.str.random;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataUtil {
    // 常用字符串字典
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
    private static List<String> exceptColumns = Arrays.asList("update_user_id","update_time","create_user_id","create_time","version","op_user_id",
            "creator","serial_no","serial_no_list","check_time","rule_serial_no","delete_flag","alter_type");

    // 生成随机值并填充对象
    public static <T> T generateRandomObject(Class<T> clazz) {
        try {
            // 创建对象实例
            T instance = clazz.getDeclaredConstructor().newInstance();
            // 获取所有字段（包括私有字段）
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                // 跳过静态字段和final字段
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if(exceptColumns.contains(field.getName())){
                    continue;
                }
                // 设置字段可访问
                field.setAccessible(true);
                // 根据字段类型生成随机值
                Object randomValue = generateRandomValue(field.getType());
                // 为字段赋值
                field.set(instance, randomValue);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("生成随机对象失败", e);
        }
    }

    // 根据类型生成随机值
    private static Object generateRandomValue(Class<?> type) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // 基本类型及包装类处理
        if (type == int.class || type == Integer.class) {
            return random.nextInt(1, 1000); // 1-999的随机整数
        } else if (type == long.class || type == Long.class) {
            return random.nextLong(1, 1000000); // 1-999999的随机长整数
        } else if (type == double.class || type == Double.class) {
            return BigDecimal.valueOf(random.nextDouble(0, 10000))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue(); // 保留两位小数
        } else if (type == float.class || type == Float.class) {
            return (float) BigDecimal.valueOf(random.nextDouble(0, 1000))
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue(); // 保留一位小数
        } else if (type == boolean.class || type == Boolean.class) {
            return random.nextBoolean(); // 随机布尔值
        } else if (type == String.class) {
            return generateRandomString(random, 5, 15); // 5-15位随机字符串
        } else if (type == Date.class) {
            // 生成近10年内的随机日期
            long start = System.currentTimeMillis() - 10L * 365 * 24 * 60 * 60 * 1000;
            long end = System.currentTimeMillis();
            return new Date(random.nextLong(start, end));
        } else if (type == BigDecimal.class) {
            return BigDecimal.valueOf(random.nextDouble(0, 100000))
                    .setScale(2, RoundingMode.HALF_UP); // 金额类通常保留两位小数
        } else if (type.isEnum()) {
            // 枚举类型：随机选择一个枚举值
            Object[] enumConstants = type.getEnumConstants();
            return enumConstants[random.nextInt(enumConstants.length)];
        } else if (type== Timestamp.class) {
            // 生成近10年内的随机日期
            long start = System.currentTimeMillis() - 10L * 365 * 24 * 60 * 60 * 1000;
            long end = System.currentTimeMillis();
            return new Timestamp(random.nextLong(start, end));
        } else if (type == List.class) {
            // 列表类型：生成包含3-5个随机字符串的列表
            List<String> list = new ArrayList<>();
            int size = random.nextInt(3, 6);
            for (int i = 0; i < size; i++) {
                list.add(generateRandomString(random, 3, 8));
            }
            return list;
        }
        // 可以继续扩展其他类型...

        // 未知类型返回null或默认值
        return null;
    }

    // 生成随机字符串
    private static String generateRandomString(ThreadLocalRandom random, int minLen, int maxLen) {
        int length = random.nextInt(minLen, maxLen + 1);
        StringBuilder sb = new StringBuilder(length);
        String allChars = LETTERS + NUMBERS ;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allChars.length());
            sb.append(allChars.charAt(index));
        }
        return sb.toString();
    }
}
