package com.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileToBase64 {
    public static void main(String[] args) {
        try {
            // 读取文件路径
            Path path = Paths.get("C:\\Users\\yansunling\\Desktop\\新建 XLSX 工作表.xlsx");

            // 将文件内容转换为字节数组
            byte[] fileContent = Files.readAllBytes(path);

            // 使用 Base64 编码器进行编码
            String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

            System.out.println("Base64 编码结果: " + base64Encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

