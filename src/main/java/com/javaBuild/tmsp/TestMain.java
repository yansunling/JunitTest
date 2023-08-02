package com.javaBuild.tmsp;

import com.yd.common.cipher.CIPDesUtils;
import com.yd.utils.common.StringUtils;

public class TestMain {
    public static void main(String[] args) {
        String decrypt = CIPDesUtils.decrypt("FvDxV4UIIyI=", 1441194498741l);
        System.out.println(decrypt);
    }
}
