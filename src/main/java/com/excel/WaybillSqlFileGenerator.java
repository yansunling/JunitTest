package com.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 读取桌面运单号.xlsx的Sheet1，并按桌面1.sql模板生成SQL文件。
 */
public class WaybillSqlFileGenerator {

    private static final Path EXCEL_PATH = Paths.get("C:\\Users\\yansunling\\Desktop\\运单号.xlsx");
    private static final Path TEMPLATE_PATH = Paths.get("src", "main", "resources", "fee", "1.sql");
    private static final Path OUTPUT_PATH = Paths.get("C:\\Users\\yansunling\\Desktop\\运单号_sheet1.sql");

    private static final String SHEET_NAME = "Sheet1";
    private static final String ORDER_ID_HEADER = "运单号";
    private static final String FREIGHT_HEADER = "运费";

    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    public static void main(String[] args) throws Exception {
        validateInputFiles();

        String template = new String(Files.readAllBytes(TEMPLATE_PATH), StandardCharsets.UTF_8);
        StringBuilder sqlBuilder = new StringBuilder();

        try (InputStream inputStream = Files.newInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                throw new IllegalArgumentException("未找到工作表: " + SHEET_NAME);
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            if (headerRow == null) {
                throw new IllegalArgumentException("Excel表头为空");
            }

            int orderIdColumn = findColumnIndex(headerRow, ORDER_ID_HEADER);
            int freightColumn = findColumnIndex(headerRow, FREIGHT_HEADER);

            int rowCount = 0;
            for (int rowIndex = headerRow.getRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String orderId = readCellText(row.getCell(orderIdColumn));
                if (orderId.isEmpty()) {
                    continue;
                }

                String freightInFen = readFreightInFen(row.getCell(freightColumn));
                sqlBuilder.append(renderTemplate(template, orderId, freightInFen)).append(System.lineSeparator());
                rowCount++;
            }

            Files.write(OUTPUT_PATH, sqlBuilder.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("已生成SQL条数: " + rowCount);
            System.out.println("输出文件: " + OUTPUT_PATH);
        }
    }

    private static void validateInputFiles() {
        if (!Files.exists(EXCEL_PATH)) {
            throw new IllegalArgumentException("Excel文件不存在: " + EXCEL_PATH);
        }
        if (!Files.exists(TEMPLATE_PATH)) {
            throw new IllegalArgumentException("SQL模板不存在: " + TEMPLATE_PATH);
        }
    }

    private static int findColumnIndex(Row headerRow, String headerName) {
        for (Cell cell : headerRow) {
            if (headerName.equals(readCellText(cell))) {
                return cell.getColumnIndex();
            }
        }
        throw new IllegalArgumentException("未找到表头列: " + headerName);
    }

    private static String readCellText(Cell cell) {
        if (cell == null) {
            return "";
        }
        return DATA_FORMATTER.formatCellValue(cell).trim();
    }

    private static String readFreightInFen(Cell cell) {
        String freightText = readCellText(cell);
        if (freightText.isEmpty()) {
            return "0";
        }
        BigDecimal freight = new BigDecimal(freightText);
        return freight.multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .toPlainString();
    }

    private static String renderTemplate(String template, String orderId, String freightInFen) {
        return template.replace("运单号", orderId)
                .replace("运费", freightInFen);
    }
}
