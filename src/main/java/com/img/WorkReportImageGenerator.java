package com.img;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 装卸收班播报图片生成工具（适配iPhone+高清晰度+动态高度+检查内容靠左+结果红色+表格下移+宽度对齐+长文本自动换行（修复空白问题））
 */
@Slf4j
public class WorkReportImageGenerator {
    // iPhone主流宽度（视网膜屏适配，750px为iPhone常用宽度）
    private static final int IMAGE_WIDTH = 750;
    // 字体设置（适配手机端显示，加大字体更清晰）
    private static final Font BASE_FONT = loadEmbeddedFont();
    // 字体设置（适配手机端显示，加大字体更清晰）
    private static final Font TITLE_FONT = getDerivedFont(BASE_FONT, Font.BOLD, 28f);
    private static final Font NORMAL_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 18f);
    private static final Font TABLE_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 16f);
    // 颜色设置
    private static final Color BLACK = Color.BLACK;
    private static final Color WHITE = Color.WHITE;
    private static final Color RED = Color.RED; // 结果为否/有时的文字颜色
    // 淡蓝色表头背景（#afd6fc）
    private static final Color HEADER_COLOR = new Color(175, 214, 252);
    // 行高和间距设置（适配手机端）
    private static final int BASIC_ROW_HEIGHT = 40;
    private static final int TABLE_ROW_HEIGHT = 35;
    private static final int MARGIN_TOP = 40;
    private static final int MARGIN_HORIZONTAL = 30; // 左右统一水平间距
    private static final int SPACING = 20;
    private static final int EXTRA_SPACING = 30; // 检查项表格额外下移的间距
    // 表格总宽度（左右间距外的有效宽度，统一两类表格）
    private static final int TABLE_TOTAL_WIDTH = IMAGE_WIDTH - MARGIN_HORIZONTAL * 2;
    // 文本绘制的最大宽度（整体收班说明的显示宽度）
    private static final int TEXT_MAX_WIDTH = IMAGE_WIDTH - MARGIN_HORIZONTAL * 2;
    // 单元格文字对齐方式
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    // 表格文字靠左的内边距（避免贴边）
    private static final int LEFT_PADDING = 10;

    public static void main(String[] args) {
        generateWorkReportImage("C:/Users/yansunling/Desktop/装卸收班播报_iphone.png");
        System.out.println("iPhone适配版图片生成完成！");
    }
    @SneakyThrows
    private static Font loadEmbeddedFont() {
        //String fontPath="C:/Users/yansunling/Desktop/msyh.ttc";

        String fontPath =WorkReportImageGenerator.class.getClassLoader().getResource("").getPath()+"fonts/msyhl.ttc";;



        File fontFile = new File(fontPath);
        // 关键：加载.ttc时指定索引，选择不同样式
        // 索引0：微软雅黑常规
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        return font;

    }


    private static Font getDerivedFont(Font baseFont, int style, float size) {
        Font derivedFont = baseFont.deriveFont(style, size);
        return    derivedFont;

    }

    /**
     * 生成装卸收班播报图片（动态计算高度+高清晰度）
     * @param outputPath 图片输出路径（含文件名）
     */
    public static void generateWorkReportImage(String outputPath) {
        // 定义基本信息（包含长文本的整体收班说明）
        String[] basicInfos = {
                "收班日期：2025-12-10",
                "机构：改貌分拨场",
                "提交人：张恩全",
                "完结时间：2025-12-10 22:27",
                "整体收班情况说明：正常"
        };

        // 1. 先计算所有内容的总高度（包含额外间距+换行高度，避免内容重叠）
        int totalHeight = calculateTotalHeight(basicInfos);
        // 为了适配视网膜屏，创建2倍尺寸的图片（提高清晰度）
        int imageWidth = IMAGE_WIDTH * 2;
        int imageHeight = totalHeight * 2;

        // 2. 创建BufferedImage，白色背景（使用TYPE_INT_RGB保证色彩）
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 关键：开启抗锯齿+插值优化，提高文字和线条的清晰度
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        // 缩放画布（2倍缩放，适配视网膜屏，解决清晰度低的问题）
        g2d.scale(2, 2);

        // 填充白色背景
        g2d.setColor(WHITE);
        g2d.fillRect(0, 0, IMAGE_WIDTH, totalHeight);
        g2d.setColor(BLACK);

        // 3. 绘制内容（按顺序绘制，使用动态y坐标）
        int y = MARGIN_TOP; // 起始y坐标

        // 3.1 绘制标题（居中）
        g2d.setFont(TITLE_FONT);
        String title = "装卸收班播报";
        int titleX = (IMAGE_WIDTH - g2d.getFontMetrics().stringWidth(title)) / 2;
        g2d.drawString(title, titleX, y);
        y += BASIC_ROW_HEIGHT + SPACING;

        // 3.2 绘制基本信息（自动处理长文本换行，修复空白问题）
        g2d.setFont(NORMAL_FONT);
        for (String info : basicInfos) {
            // 直接调用多行绘制方法，所有文本从同一左间距开始，自动换行
            int lines = drawMultiLineText(g2d, info, MARGIN_HORIZONTAL, y, TEXT_MAX_WIDTH, BASIC_ROW_HEIGHT);
            y += lines * BASIC_ROW_HEIGHT;
        }
         // 增加额外间距，让检查项表格下移

        // 3.3 绘制检查项表格（统一总宽度，左边界=MARGIN_HORIZONTAL）
        int table1X = MARGIN_HORIZONTAL;
        int table1Col1Width = 80; // 序号列宽度
        int table1Col2Width = TABLE_TOTAL_WIDTH - table1Col1Width; // 内容列宽度（总宽度-序号列）
        // 表格标题
        g2d.setFont(NORMAL_FONT);
        g2d.drawString("收班检查项", table1X, y);
        y += SPACING;
        // 表格头部：序号（居中），检查内容及结果（靠左）
        drawTableCell(g2d, table1X, y, table1Col1Width, TABLE_ROW_HEIGHT, "序号", true, ALIGN_CENTER, null);
        drawTableCell(g2d, table1X + table1Col1Width, y, table1Col2Width, TABLE_ROW_HEIGHT, "检查内容及结果", true, ALIGN_LEFT, null);
        y += TABLE_ROW_HEIGHT;
        // 表格内容：序号（居中），检查内容及结果（靠左+结果颜色区分）
        String[][] checkItems = {
                {"1", "车皮是否清扫：是"},
                {"2", "车皮是否锁门：否"}, // 测试：否→红色
                {"3", "卸车结束是否操作：是"},
                {"4", "车辆是否装车完毕：是"},
                {"5", "当日有无工伤事件：有"}, // 测试：有→红色
                {"6", "当日有无重大货损：无"},
                {"7", "多货少货是否核实：是"},
                {"8", "托盘是否入库：否"}, // 测试：否→红色
                {"9", "盘库是否完成：是"},
                {"10", "库房货物摆放是否合理：是"},
                {"11", "工具设备是否归位：有"}, // 测试：有→红色
                {"12", "库房清洁是否完成：是"}
        };
        for (String[] item : checkItems) {
            drawTableCell(g2d, table1X, y, table1Col1Width, TABLE_ROW_HEIGHT, item[0], false, ALIGN_CENTER, null);
            // 处理检查内容的文字颜色（拆分内容和结果，判断是否为红）
            drawColoredContent(g2d, table1X + table1Col1Width, y, table1Col2Width, TABLE_ROW_HEIGHT, item[1]);
            y += TABLE_ROW_HEIGHT;
        }
        y += SPACING;

        // 3.4 绘制库存情况表格（统一总宽度+左边界，与检查项表格对齐）
        int table2X = MARGIN_HORIZONTAL; // 与检查项表格相同的左边界
        // 库存表格列宽度（按比例分配总宽度，5列）
        int table2Col1Width = TABLE_TOTAL_WIDTH / 5; // 类型列（1/5）
        int table2Col2Width = TABLE_TOTAL_WIDTH / 5; // 票数列（1/5）
        int table2Col3Width = TABLE_TOTAL_WIDTH / 5; // 件数列（1/5）
        int table2Col4Width = TABLE_TOTAL_WIDTH / 5; // 吨数列（1/5）
        int table2Col5Width = TABLE_TOTAL_WIDTH - (table2Col1Width + table2Col2Width + table2Col3Width + table2Col4Width); // 方数列（剩余宽度）
        // 表格标题
        g2d.setFont(NORMAL_FONT);
        g2d.drawString("收班库存情况", table2X, y);
        y += SPACING;
        // 表格头部（全部居中）
        drawTableCell(g2d, table2X, y, table2Col1Width, TABLE_ROW_HEIGHT, "类型", true, ALIGN_CENTER, null);
        drawTableCell(g2d, table2X + table2Col1Width, y, table2Col2Width, TABLE_ROW_HEIGHT, "票数", true, ALIGN_CENTER, null);
        drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width, y, table2Col3Width, TABLE_ROW_HEIGHT, "件数", true, ALIGN_CENTER, null);
        drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width + table2Col3Width, y, table2Col4Width, TABLE_ROW_HEIGHT, "吨数", true, ALIGN_CENTER, null);
        drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width + table2Col3Width + table2Col4Width, y, table2Col5Width, TABLE_ROW_HEIGHT, "方数", true, ALIGN_CENTER, null);
        y += TABLE_ROW_HEIGHT;
        // 表格内容（全部居中）
        String[][] stockItems = {
                {"自提", "117票", "10736件", "438.0吨", "1399.303方"},
                {"送货", "72票", "5670件", "162.0吨", "565.584方"},
                {"中转", "13票", "171件", "4.4吨", "15.98方"},
                {"大票直送", "18票", "3437件", "246.2吨", "625.886方"}
        };
        for (String[] item : stockItems) {
            drawTableCell(g2d, table2X, y, table2Col1Width, TABLE_ROW_HEIGHT, item[0], false, ALIGN_CENTER, null);
            drawTableCell(g2d, table2X + table2Col1Width, y, table2Col2Width, TABLE_ROW_HEIGHT, item[1], false, ALIGN_CENTER, null);
            drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width, y, table2Col3Width, TABLE_ROW_HEIGHT, item[2], false, ALIGN_CENTER, null);
            drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width + table2Col3Width, y, table2Col4Width, TABLE_ROW_HEIGHT, item[3], false, ALIGN_CENTER, null);
            drawTableCell(g2d, table2X + table2Col1Width + table2Col2Width + table2Col3Width + table2Col4Width, y, table2Col5Width, TABLE_ROW_HEIGHT, item[4], false, ALIGN_CENTER, null);
            y += TABLE_ROW_HEIGHT;
        }

        // 4. 释放资源
        g2d.dispose();

        // 5. 保存图片（PNG格式，无损压缩，清晰度高）
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("图片保存失败：" + e.getMessage());
        }
    }

    /**
     * 计算图片的总高度（包含额外间距+换行高度，动态计算，避免内容重叠）
     * @param basicInfos 基本信息数组
     * @return 总高度
     */
    private static int calculateTotalHeight(String[] basicInfos) {
        // 创建临时Graphics2D用于计算文字宽度（避免创建真实图片）
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D tempG2d = tempImage.createGraphics();
        tempG2d.setFont(NORMAL_FONT);

        int height = MARGIN_TOP; // 标题上边距
        height += BASIC_ROW_HEIGHT + SPACING; // 标题高度+间距

        // 计算基本信息的高度（包含换行）
        for (String info : basicInfos) {
            List<String> lines = splitTextToLines(tempG2d, info, TEXT_MAX_WIDTH);
            height += lines.size() * BASIC_ROW_HEIGHT;
        }

        height += SPACING + EXTRA_SPACING; // 额外间距
        // 检查项表格高度（标题+表头+12行内容）
        height += BASIC_ROW_HEIGHT + SPACING + TABLE_ROW_HEIGHT + (TABLE_ROW_HEIGHT * 12) + SPACING;
        // 库存表格高度（标题+表头+4行内容）
        height += BASIC_ROW_HEIGHT + SPACING + TABLE_ROW_HEIGHT + (TABLE_ROW_HEIGHT * 4);
        // 底部预留间距
        height += SPACING * 2;

        tempG2d.dispose();
        return height;
    }

    /**
     * 将长文本按指定宽度拆分成多行
     * @param g2d Graphics2D对象
     * @param text 要拆分的文本
     * @param maxWidth 最大宽度
     * @return 拆分后的行列表
     */
    private static List<String> splitTextToLines(Graphics2D g2d, String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        FontMetrics fm = g2d.getFontMetrics();
        int currentIndex = 0;
        int textLength = text.length();

        while (currentIndex < textLength) {
            // 找到当前行能容纳的最后一个字符索引
            int endIndex = currentIndex;
            int currentWidth = 0;
            while (endIndex < textLength) {
                char c = text.charAt(endIndex);
                int charWidth = fm.charWidth(c);
                if (currentWidth + charWidth > maxWidth) {
                    break;
                }
                currentWidth += charWidth;
                endIndex++;
            }

            // 如果是最后一个字符，直接添加
            if (endIndex >= textLength) {
                lines.add(text.substring(currentIndex));
                break;
            }

            // 否则，回退到最近的空格（避免单词被拆分）
            int spaceIndex = text.lastIndexOf(' ', endIndex);
            if (spaceIndex > currentIndex) {
                lines.add(text.substring(currentIndex, spaceIndex));
                currentIndex = spaceIndex + 1; // 跳过空格
            } else {
                // 没有空格，直接拆分
                lines.add(text.substring(currentIndex, endIndex));
                currentIndex = endIndex;
            }
        }

        return lines;
    }

    /**
     * 绘制多行文本，返回绘制的行数
     * @param g2d Graphics2D对象
     * @param text 要绘制的文本
     * @param x 起始x坐标
     * @param y 起始y坐标（第一行的基线）
     * @param maxWidth 最大宽度
     * @param lineHeight 行高
     * @return 绘制的行数
     */
    private static int drawMultiLineText(Graphics2D g2d, String text, int x, int y, int maxWidth, int lineHeight) {
        List<String> lines = splitTextToLines(g2d, text, maxWidth);
        int currentY = y;
        for (String line : lines) {
            g2d.drawString(line, x, currentY);
            currentY += lineHeight;
        }
        return lines.size();
    }

    /**
     * 绘制表格单元格（支持居中/靠左对齐，淡蓝色表头+高清晰度）
     * @param g2d Graphics2D对象
     * @param x 单元格起始x坐标
     * @param y 单元格起始y坐标（文字基线）
     * @param width 单元格宽度
     * @param height 单元格高度
     * @param text 单元格文字
     * @param isHeader 是否为表头
     * @param align 对齐方式（ALIGN_CENTER/ALIGN_LEFT）
     * @param color 文字颜色（null则使用默认黑色）
     */
    private static void drawTableCell(Graphics2D g2d, int x, int y, int width, int height, String text, boolean isHeader, int align, Color color) {
        // 1. 绘制单元格背景
        if (isHeader) {
            g2d.setColor(HEADER_COLOR); // 淡蓝色表头
        } else {
            g2d.setColor(WHITE); // 内容白色背景
        }
        // 单元格的实际位置：y是文字基线，所以单元格顶部是y - height
        g2d.fillRect(x, y - height, width, height);

        // 2. 绘制单元格边框（黑色，细线条）
        g2d.setColor(BLACK);
        g2d.drawRect(x, y - height, width, height);

        // 3. 绘制单元格文字（支持居中/靠左对齐，自定义颜色）
        g2d.setFont(TABLE_FONT);
        // 设置文字颜色（默认黑色）
        g2d.setColor(color == null ? BLACK : color);
        FontMetrics fm = g2d.getFontMetrics();
        int textX;
        // 根据对齐方式计算文字x坐标
        if (align == ALIGN_LEFT) {
            // 靠左：x + 内边距，避免文字贴边
            textX = x + LEFT_PADDING;
        } else {
            // 居中：默认逻辑
            textX = x + (width - fm.stringWidth(text)) / 2;
        }
        // 垂直居中计算：基线位置 = 单元格顶部 + (高度 + 字体高度)/2 - 字体下沉量
        int textY = (y - height) + (height + fm.getHeight()) / 2 - fm.getDescent();
        g2d.drawString(text, textX, textY);
        // 恢复默认黑色（避免影响后续绘制）
        g2d.setColor(BLACK);
    }

    /**
     * 绘制带颜色的检查内容（拆分内容和结果，结果为否/有时显示红色）
     * @param g2d Graphics2D对象
     * @param x 单元格起始x坐标
     * @param y 单元格起始y坐标
     * @param width 单元格宽度
     * @param height 单元格高度
     * @param content 检查内容（如：车皮是否清扫：是）
     */
    private static void drawColoredContent(Graphics2D g2d, int x, int y, int width, int height, String content) {
        // 1. 绘制单元格背景和边框（白色背景+黑色边框）
        g2d.setColor(WHITE);
        g2d.fillRect(x, y - height, width, height);
        g2d.setColor(BLACK);
        g2d.drawRect(x, y - height, width, height);

        // 2. 拆分内容和结果（按：分割）
        String[] parts = content.split("：", 2);
        String label = parts[0] + "：";
        String result = parts.length > 1 ? parts[1] : "";

        // 3. 绘制标签（黑色，靠左）
        g2d.setFont(TABLE_FONT);
        g2d.setColor(BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int labelX = x + LEFT_PADDING;
        int textY = (y - height) + (height + fm.getHeight()) / 2 - fm.getDescent();
        g2d.drawString(label, labelX, textY);

        // 4. 绘制结果（判断是否为红色：否/有 → 红色，否则黑色）
        Color resultColor = (result.equals("否") || result.equals("有")) ? RED : BLACK;
        g2d.setColor(resultColor);
        int resultX = labelX + fm.stringWidth(label);
        g2d.drawString(result, resultX, textY);

        // 恢复默认黑色
        g2d.setColor(BLACK);
    }
}