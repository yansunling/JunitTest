package com.str;

public class StringMain {
    public static void main(String[] args) {
        String str=" orgTransferMap.put(\"250110020401\", Sets.newHashSet(\"532901\", \"53290101\"));";
        str = str.replaceAll("\"" + "250110020401", "\"55");
        System.out.println(str);


    }




}
