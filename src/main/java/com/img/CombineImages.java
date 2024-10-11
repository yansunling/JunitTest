package com.img;

import com.sun.tools.javac.util.List;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CombineImages {
    public static void main(String[] args) throws IOException {
        // 假设你有一个List<BufferedImage>，这里我们创建一个示例列表
        List<BufferedImage> images = List.of(
                ImageIO.read(new File("C:\\Users\\yansunling\\Desktop\\1.png")),
                ImageIO.read(new File("C:\\Users\\yansunling\\Desktop\\0.png"))
        );

        // 计算拼接后图片的宽度和高度
        int totalWidth = 0;
        int maxHeight = 0;
        for (BufferedImage image : images) {
            totalWidth += image.getWidth();
            maxHeight = Math.max(maxHeight, image.getHeight());
        }

        // 创建一个新的BufferedImage对象，用于存放拼接后的图片
        BufferedImage combinedImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = combinedImage.getGraphics();

        // 水平拼接图片
        int currentX = 0;
        for (BufferedImage image : images) {
            g.drawImage(image, currentX, 0, null);
            currentX += image.getWidth();
        }

        // 释放资源
        g.dispose();

        // 保存拼接后的图片
        ImageIO.write(combinedImage, "png", new File("C:\\Users\\yansunling\\Desktop\\3.png"));
    }
}
