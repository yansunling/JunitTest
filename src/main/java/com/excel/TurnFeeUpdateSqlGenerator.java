package com.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * 读取Excel文件生成中转费/送货费更新SQL
 * Excel列对应关系：
 * - 运单号 -> order_id
 * - 实际中转费 -> refer_turn_fee / real_turn_fee
 * - 实际送货费 -> refer_profile_fee / real_profile_fee
 */
public class TurnFeeUpdateSqlGenerator {

    // Excel文件路径

    // 输出SQL文件路径
    private static final String OUTPUT_SQL_PATH = "C:\\Users\\yansunling\\Desktop\\turn_fee_update.sql";

    // Excel列名配置（根据实际Excel列名修改）
    private static final String COL_ORDER_ID = "运单号";
    private static final String COL_TURN_FEE = "实际中转费";
    private static final String COL_PROFILE_FEE = "实际送货费";

    public static void main(String[] args) throws Exception {
        String EXCEL_PATH = "C:\\Users\\yansunling\\Desktop\\23.xlsx";
        File file = new File(EXCEL_PATH);
        if (!file.exists()) {
            System.err.println("Excel文件不存在: " + EXCEL_PATH);
            return;
        }

        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);

        // 读取表头，确定列索引
        Row headerRow = sheet.getRow(0);
        int orderIdCol = -1;
        int turnFeeCol = -1;
        int profileFeeCol = -1;

        System.out.println("=== Excel表头列名 ===");
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            String colName = getCellStringValue(cell);
            System.out.println("列" + i + ": " + colName);

            if (COL_ORDER_ID.equals(colName)) {
                orderIdCol = i;
            } else if (COL_TURN_FEE.equals(colName)) {
                turnFeeCol = i;
            } else if (COL_PROFILE_FEE.equals(colName)) {
                profileFeeCol = i;
            }
        }
        System.out.println("=====================\n");

        if (orderIdCol < 0) {
            System.err.println("未找到[" + COL_ORDER_ID + "]列，请检查Excel表头");
            workbook.close();
            return;
        }
        if (turnFeeCol < 0) {
            System.err.println("未找到[" + COL_TURN_FEE + "]列，请检查Excel表头");
            workbook.close();
            return;
        }

        StringBuilder sql1 = new StringBuilder();  // tmsp_send_order_turn
        StringBuilder sql2 = new StringBuilder();  // tmsp_send_turn_fee

        int dataCount = 0;
        for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }

            String orderId = getCellStringValue(row.getCell(orderIdCol));
            if (orderId == null || orderId.trim().isEmpty()) {
                continue;
            }
            orderId = orderId.trim();

            // 获取中转费（乘以100）
            BigDecimal turnFee = getCellNumericValue(row.getCell(turnFeeCol)).multiply(new BigDecimal("100"));
            // 获取送货费（乘以100，如果列存在）
            BigDecimal profileFee = BigDecimal.ZERO;
            if (profileFeeCol >= 0) {
                profileFee = getCellNumericValue(row.getCell(profileFeeCol)).multiply(new BigDecimal("100"));
            }

            // 生成SQL语句（费用已乘以100）
            String sql1Line = String.format(
                    "update tmsp.tmsp_send_order_turn set refer_turn_fee=%s,refer_profile_fee=%s where order_id='%s';",
                    turnFee.stripTrailingZeros().toPlainString(), profileFee.stripTrailingZeros().toPlainString(), orderId
            );
            String sql2Line = String.format(
                    "update tmsp.tmsp_send_turn_fee set real_turn_fee=%s,real_profile_fee=%s where order_id='%s';",
                    turnFee.stripTrailingZeros().toPlainString(), profileFee.stripTrailingZeros().toPlainString(), orderId
            );

            sql1.append(sql1Line).append("\n");
            sql2.append(sql2Line).append("\n");
            dataCount++;
        }

        workbook.close();

        // 输出到控制台
        System.out.println("=== tmsp_send_order_turn 更新语句 ===");
        System.out.println(sql1.toString());
        System.out.println("=== tmsp_send_turn_fee 更新语句 ===");
        System.out.println(sql2.toString());

        // 写入文件
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_SQL_PATH))) {
            writer.println("-- tmsp_send_order_turn 更新语句");
            writer.print(sql1.toString());
            writer.println();
            writer.println("-- tmsp_send_turn_fee 更新语句");
            writer.print(sql2.toString());
        }

        System.out.println("共生成 " + dataCount + " 条数据的SQL语句");
        System.out.println("SQL已保存到: " + OUTPUT_SQL_PATH);
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            // 处理数字类型，避免科学计数法
            double numValue = cell.getNumericCellValue();
            if (numValue == Math.floor(numValue)) {
                return String.valueOf((long) numValue);
            }
            return String.valueOf(numValue);
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {
            try {
                return cell.getStringCellValue();
            } catch (Exception e) {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return null;
    }

    private static BigDecimal getCellNumericValue(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else if (cellType == Cell.CELL_TYPE_STRING) {
            String strVal = cell.getStringCellValue();
            if (strVal != null && !strVal.trim().isEmpty()) {
                try {
                    return new BigDecimal(strVal.trim());
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            }
            return BigDecimal.ZERO;
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {
            try {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            } catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }
}
