package com.str.string;

import java.time.LocalDateTime;

public class JSONTest {
    public static void main(String[] args) {

        /*List<String> values=new ArrayList<>();
        values.add("444");
//        values.add("444");
        String queryString="{\n" +
                "\t\"bool\": {\n" +
                "\t\t\"should\": [\n" +
                "\t\t\t{\"terms\":{\"send_customer_id\":?}},\n" +
                "\t\t\t{\"terms\":{\"recv_customer_id\":?}}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        String join = StringUtils.join(values, "\",\"");
        queryString=queryString.replaceAll("\\?", join);

        System.out.println(queryString);*/



        LocalDateTime currentDate = LocalDateTime .now();
        LocalDateTime  targetDate = LocalDateTime.of(2024,1,1,6,0,0);
        if (currentDate.isAfter(targetDate)) {
            System.out.println("------2222");
        }





    }
}
