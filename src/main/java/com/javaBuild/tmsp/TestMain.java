package com.javaBuild.tmsp;

import com.yd.utils.common.StringUtils;

public class TestMain {
    public static void main(String[] args) {
        int start=1;
        String mainCode = StringUtils.apppendPre(start + "", 2, '0');
        System.out.println(mainCode);

    }
}
