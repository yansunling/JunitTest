package com.dy.test.execl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DownFileUtil {

	public static void main(String[] args) throws Exception{
		String fileName = "E:/test.xlsx";
		OutputStream os=new FileOutputStream(fileName);
		List<String> cell=new ArrayList<>();
		cell.add("1");
		cell.add("2");
		cell.add("3");

		List<List<String>> data=new ArrayList<>();
		data.add(cell);


		List<List<String>> headers=new ArrayList<>();
		List<String> head=new ArrayList<>();
		head.add("测试");
		headers.add(head);
		headers.add(head);
		headers.add(head);






		// 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
		LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
		// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//		EasyExcel.write(os).registerWriteHandler(loopMergeStrategy).sheet("模板").doWrite(data);






		// 头的策略
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		// 背景设置为红色
		headWriteCellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());




		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();


		HorizontalCellStyleStrategy horizontalCellStyleStrategy =
				new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);



		ExcelWriter excelWriter = EasyExcel.write(os).registerWriteHandler(horizontalCellStyleStrategy).build();
		// 这里注意 如果同一个sheet只要创建一次
		WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
		writeSheet.setHead(headers);




		// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
		for (int i = 0; i < 5; i++) {


			excelWriter.write(data, writeSheet);
		}

		/// 千万别忘记finish 会帮忙关闭流
		excelWriter.finish();




	}


}
