package com.my.execel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkbookUtil {


	
	public static void createWorkBook(XSSFWorkbook wb, String sheetName, Map<String, String> excelTitle, Map<Integer,String[]> selectValue, List<Map<String, Object>> value){
		XSSFSheet sheet = wb.createSheet(sheetName);
		createTitle(wb,sheet,excelTitle);
		
		CellStyle textStyle = wb.createCellStyle();

		DataFormat format = wb.createDataFormat();

		textStyle.setDataFormat(format.getFormat("@"));

		for(int j=0;j<excelTitle.size();j++){
			sheet.setDefaultColumnStyle(j, textStyle);
		}
		if(selectValue!=null){
			int length=100;
			for(int i=1;i<length;i++){
				sheet.createRow(i);
				Set<Integer> keys = selectValue.keySet();
				for(Integer key:keys){
					CellRangeAddressList addressList = new CellRangeAddressList(i,i,key,key);
//					DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectValue.get(key));  
					//绑定下拉框和作用区域  
//					XSSFDataValidation data_validation = new XSSFDataValidation(regions,constraint);  
//					XSSFDataValidation a=new XSSFDataValidation(constraint, regions, ctDataValidation)
					
					
					XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
					XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(selectValue.get(key));
					XSSFDataValidation validation =  (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
					//对sheet页生效  
					sheet.addValidationData(validation); 
				}
			}
		}
		if(value==null) {
			return;
		}
		Object[] title =  excelTitle.keySet().toArray();
		int j = 1;
		for (Map<String, Object> map : value) {
			int i = 0;
			// 创建一行
			Row row = sheet.createRow(j);
			// 填充单元格
			for (Object s : title) {
				Cell cell = row.createCell(i);
				cell.setCellType(CellType.STRING);

				Object obj = map.get(s.toString());
				String cellValue = "";
				if (obj != null) {
					cellValue = obj.toString();
				}
				cell.setCellValue(cellValue);
				sheet.setColumnWidth(i, 20 * 256);
				i++;
			}
			j++;
		}
	}
	
	
	public static void createTitle(XSSFWorkbook wb, Sheet sheet, Map<String, String> excelTitle){
		Object[] title =  excelTitle.keySet().toArray();
		int i = 0;
		// 创建一行
		Row row = sheet.createRow(0);
		// 填充标题
		for (Object s : title) {
			Cell cell = row.createCell(i);
			cell.setCellType(CellType.STRING);

			cell.setCellStyle(getTitleStyle(wb));
			cell.setCellValue(excelTitle.get(s));
			sheet.setColumnWidth(i, 20*256);
			i++;
		}
	}
	
	public static CellStyle getTitleStyle(Workbook workbook) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);

		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		titleStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());


		
		return titleStyle;
	}
	
	public static void main(String[] args) throws Exception {

	}
	
}
