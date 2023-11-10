package com.junit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarUtil {
	public static void main(String[] args) throws Exception {
		
		
		//定义一个jaroutputstream流
		JarOutputStream stream=new JarOutputStream(new FileOutputStream("E:/test.jar"));

		//jar中的每一个文件夹 每一个文件 都是一个jarEntry

		//如下表示在jar文件中创建一个文件夹bang bang下创建一个文件jj.txt
		JarEntry entry=new JarEntry("com/dy/appsdk/BOX_base_carVO.java");
		//表示将该entry写入jar文件中 也就是创建该文件夹和文件
      	stream.putNextEntry(entry);
      	
      	
		 // 新建文件输入流并对它进行缓冲
        String filePath=JarUtil.class.getClassLoader().getResource("").getPath()+ "java/crm/JavaPO.java";
        FileInputStream input = new FileInputStream(new File(filePath));
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
        	stream.write(b, 0, len);
        }
        

		

		/*//然后就是往entry中的jj.txt文件中写入内容
		stream.write("我日你".getBytes("utf-8"));

		//创建另一个entry1 同上操作 可以利用循环将一个文件夹中的文件都写入jar包中 其实很简单
		JarEntry entry1=new JarEntry("bang/bb.xml");
		stream.putNextEntry(entry1);
		stream.write("<xml>abc</xml>".getBytes("utf-8"));*/

		//最后不能忘记关闭流
        inBuff.close();
        input.close();
		stream.close();
		 
		
		
		
	}
}