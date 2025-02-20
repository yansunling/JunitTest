package com.str.string;

import java.io.*;

public class DeleteParagraph {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\yansunling\\Desktop\\example.txt"; // 文件路径
        String targetString = "test"; // 要删除的字符串

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            boolean inParagraph = false;
            boolean deleteCurrentParagraph = false;
            StringBuilder currentParagraph = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // 空行表示段落结束
                    if (!deleteCurrentParagraph) {
                        writer.write(currentParagraph.toString());
                        writer.newLine();
                    }
                    writer.write(line); // 写入空行
                    writer.newLine();

                    // 重置段落状态
                    currentParagraph.setLength(0);
                    deleteCurrentParagraph = false;
                    inParagraph = false;
                } else {
                    inParagraph = true;
                    currentParagraph.append(line).append(System.lineSeparator());

                    if (line.contains(targetString)) {
                        deleteCurrentParagraph = true;
                    }
                }
            }

            // 处理文件末尾的段落
            if (inParagraph && !deleteCurrentParagraph) {
                writer.write(currentParagraph.toString());
            }

            // 替换原文件
            File originalFile = new File(filePath);
            File tempFile = new File(filePath + ".tmp");
            if (originalFile.delete()) {
                tempFile.renameTo(originalFile);
            }

            System.out.println("Paragraphs containing '" + targetString + "' have been deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
