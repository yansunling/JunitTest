package com.img;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆到达预警图片生成工具（适配iPhone+高清晰度+动态高度）
 */
@Slf4j
public class VehicleArrivalImageGenerator {
    // iPhone主流宽度（视网膜屏适配，750px为iPhone常用宽度）
    private static final int IMAGE_WIDTH = 750;
    // 字体设置
    private static final Font BASE_FONT = loadEmbeddedFont();
    private static final Font TITLE_FONT = getDerivedFont(BASE_FONT, Font.BOLD, 20f);
    private static final Font SECTION_TITLE_FONT = getDerivedFont(BASE_FONT, Font.BOLD, 17f);
    private static final Font NORMAL_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 14f);
    private static final Font SMALL_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 13f);
    private static final Font TABLE_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 12f);
    private static final Font TABLE_HEADER_FONT = getDerivedFont(BASE_FONT, Font.PLAIN, 11f);
    // 颜色设置
    private static final Color BLACK = Color.BLACK;                     // 纯黑
    private static final Color WHITE = Color.WHITE;
    private static final Color ICON_BLUE = new Color(64, 158, 255);     // #409eff
    private static final Color LABEL_GRAY = new Color(51, 51, 51);      // #333 更深灰
    private static final Color VALUE_BLACK = Color.BLACK;               // 纯黑
    private static final Color PAGE_BG = new Color(245, 247, 250);      // #f5f7fa
    private static final Color CARD_SHADOW = new Color(0, 0, 0, 13);    // rgba(0,0,0,0.05)
    private static final Color BORDER_COLOR = new Color(232, 232, 232); // #e8e8e8
    private static final Color HEADER_LINE = new Color(240, 240, 240);  // #f0f0f0
    private static final Color TABLE_HEADER_BG = new Color(248, 249, 250); // #f8f9fa
    private static final Color TOTAL_ROW_BG = new Color(240, 249, 255); // #f0f9ff
    private static final Color COUNTDOWN_BG = new Color(255, 242, 232); // #fff2e8
    private static final Color COUNTDOWN_TEXT = new Color(180, 100, 20);// 深橙色
    private static final Color DEPT_NAME_COLOR = Color.BLACK;           // 纯黑
    // 行高和间距设置
    private static final int ROW_HEIGHT = 28;
    private static final int TABLE_ROW_HEIGHT = 28;
    private static final int MARGIN_TOP = 16;
    private static final int MARGIN_HORIZONTAL = 16;
    private static final int SPACING = 20;
    private static final int CARD_PADDING = 18;
    private static final int CARD_RADIUS = 12;

    public static void main(String[] args) {
        // 构建测试数据
        ArrivalData data = buildTestData();
        generateVehicleArrivalImage(data, "C:/Users/yansunling/Desktop/2.png");
        System.out.println("车辆到达预警图片生成完成！");
    }

    /**
     * 构建测试数据
     */
    private static ArrivalData buildTestData() {
        ArrivalData data = new ArrivalData();
        data.setTitle("云南大区（中骁·直达车）2026车辆到达预警");

        List<TransferStation> stations = new ArrayList<>();

        // 台州永源转运场
        TransferStation station1 = new TransferStation();
        station1.setName("台州永源转运场");
        station1.setManager("王正飞");
        station1.setVehicle("浙JD5898（17.5平板）");
        station1.setLoadDate("2026/1/12");
        station1.setDepartTime("2026/1/12 23:08");
        station1.setExpectedArrival("2026/1/14 12:00");
        station1.setRemainingTime("XX小时XX分");
        List<CargoInfo> cargo1 = new ArrayList<>();
        cargo1.add(new CargoInfo("自提", "107", "3.74", "13.679"));
        cargo1.add(new CargoInfo("送货", "852", "26.61", "131.964"));
        cargo1.add(new CargoInfo("中转", "153", "3.28", "10.07"));
        cargo1.add(new CargoInfo("合计", "1112", "33.63", "155.713"));
        station1.setCargoList(cargo1);
        stations.add(station1);

        // 湖州织里转运场
        TransferStation station2 = new TransferStation();
        station2.setName("湖州织里转运场");
        station2.setManager("郑奎");
        station2.setVehicle("皖CF4353（17.5平板）");
        station2.setLoadDate("2026/1/12");
        station2.setDepartTime("2026/1/12 3:19");
        station2.setExpectedArrival("2026/1/14 10:00");
        station2.setRemainingTime("XX小时XX分");
        List<CargoInfo> cargo2 = new ArrayList<>();
        cargo2.add(new CargoInfo("自提", "137", "3.84", "8.74"));
        cargo2.add(new CargoInfo("送货", "113", "22.38", "130.24"));
        cargo2.add(new CargoInfo("中转", "57", "6.15", "29.55"));
        cargo2.add(new CargoInfo("合计", "307", "32.37", "168.53"));
        station2.setCargoList(cargo2);
        stations.add(station2);

        data.setStations(stations);
        return data;
    }

    @SneakyThrows
    private static Font loadEmbeddedFont() {
        String fontPath = VehicleArrivalImageGenerator.class.getClassLoader().getResource("").getPath() + "fonts/msyh.ttc";
        File fontFile = new File(fontPath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        return font;
    }

    private static Font getDerivedFont(Font baseFont, int style, float size) {
        return baseFont.deriveFont(style, size);
    }

    /**
     * 生成车辆到达预警图片
     */
    public static void generateVehicleArrivalImage(ArrivalData data, String outputPath) {
        // 1. 计算总高度
        int totalHeight = calculateTotalHeight(data);
        int imageWidth = IMAGE_WIDTH * 2;
        int imageHeight = totalHeight * 2;

        // 2. 创建BufferedImage
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 开启抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.scale(2, 2);

        // 填充页面背景色（浅灰色）
        g2d.setColor(PAGE_BG);
        g2d.fillRect(0, 0, IMAGE_WIDTH, totalHeight);

        int y = MARGIN_TOP;

        // 3. 绘制标题（黑色文字，居中，无背景）
        y = drawTitle(g2d, data.getTitle(), y);

        // 4. 绘制每个转运场卡片
        for (TransferStation station : data.getStations()) {
            y = drawStationCard(g2d, station, y);
        }

        // 释放资源
        g2d.dispose();

        // 保存图片
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("图片保存失败：" + e.getMessage());
        }
    }

    /**
     * 计算图片总高度
     */
    private static int calculateTotalHeight(ArrivalData data) {
        int height = MARGIN_TOP;
        height += 50; // 标题区域
        height += SPACING;

        for (TransferStation station : data.getStations()) {
            height += calculateStationCardHeight(station);
            height += SPACING;
        }

        height += MARGIN_TOP;
        return height;
    }

    /**
     * 计算单个转运场卡片高度
     */
    private static int calculateStationCardHeight(TransferStation station) {
        int height = CARD_PADDING; // 上内边距
        height += 30; // 标题行（部门名+状态）
        height += 10; // 分割线下边距
        height += ROW_HEIGHT; // 负责人、车辆行
        height += 10;
        height += ROW_HEIGHT; // 装车日期、发车时间行
        height += 10;
        height += ROW_HEIGHT; // 预计到达行
        height += 10;
        height += 36; // 倒计时区域
        height += 15;
        height += 20; // 装载货量标题
        height += 8;
        height += TABLE_ROW_HEIGHT; // 表头
        height += TABLE_ROW_HEIGHT * station.getCargoList().size(); // 表格内容
        height += CARD_PADDING; // 下内边距
        return height;
    }

    /**
     * 绘制标题（黑色文字，居中，无背景）
     */
    private static int drawTitle(Graphics2D g2d, String title, int y) {
        g2d.setFont(TITLE_FONT);
        g2d.setColor(BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (IMAGE_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, y + fm.getAscent());

        return y + 50;
    }

    /**
     * 绘制转运场卡片
     */
    private static int drawStationCard(Graphics2D g2d, TransferStation station, int startY) {
        int cardX = MARGIN_HORIZONTAL;
        int cardWidth = IMAGE_WIDTH - MARGIN_HORIZONTAL * 2;
        int cardHeight = calculateStationCardHeight(station);

        // 绘制卡片阴影效果
        g2d.setColor(CARD_SHADOW);
        g2d.fillRoundRect(cardX + 2, startY + 4, cardWidth, cardHeight, CARD_RADIUS, CARD_RADIUS);

        // 绘制卡片背景（白色圆角）
        g2d.setColor(WHITE);
        g2d.fillRoundRect(cardX, startY, cardWidth, cardHeight, CARD_RADIUS, CARD_RADIUS);

        int y = startY + CARD_PADDING;
        int contentX = cardX + CARD_PADDING;
        int contentWidth = cardWidth - CARD_PADDING * 2;

        // 绘制卡片头部：部门名称 + 状态标签
        g2d.setFont(SECTION_TITLE_FONT);
        g2d.setColor(DEPT_NAME_COLOR);
        g2d.drawString(station.getName(), contentX, y + 18);

        // 绘制"待到达"标签
        String statusText = "待到达";
        g2d.setFont(SMALL_FONT);
        FontMetrics fm = g2d.getFontMetrics();
        int statusWidth = fm.stringWidth(statusText) + 16;
        int statusHeight = 22;
        int statusX = cardX + cardWidth - CARD_PADDING - statusWidth;
        g2d.setColor(ICON_BLUE);
        g2d.fillRoundRect(statusX, y + 2, statusWidth, statusHeight, 4, 4);
        g2d.setColor(WHITE);
        g2d.drawString(statusText, statusX + 8, y + 16);

        y += 30;

        // 绘制分割线
        g2d.setColor(HEADER_LINE);
        g2d.drawLine(contentX, y, contentX + contentWidth, y);
        y += 10;

        // 绘制负责人和车辆（两列布局）
        int halfWidth = contentWidth / 2;

        drawLabelValue(g2d, contentX, y, "user", "负责人", station.getManager());
        drawLabelValue(g2d, contentX + halfWidth, y, "truck", "车辆", station.getVehicle());
        y += ROW_HEIGHT + 10;

        // 绘制装车日期和发车时间
        drawLabelValue(g2d, contentX, y, "calendar", "装车日期", station.getLoadDate());
        drawLabelValue(g2d, contentX + halfWidth, y, "clock", "发车时间", station.getDepartTime());
        y += ROW_HEIGHT + 10;

        // 绘制预计到达
        drawLabelValue(g2d, contentX, y, "flag", "预计到达", station.getExpectedArrival());
        y += ROW_HEIGHT + 10;

        // 绘制倒计时区域（橙色背景）
        int countdownHeight = 32;
        g2d.setColor(COUNTDOWN_BG);
        g2d.fillRoundRect(contentX, y, contentWidth, countdownHeight, 8, 8);

        // 绘制沙漏图标
        drawIcon(g2d, contentX + 10, y + 9, "hourglass", COUNTDOWN_TEXT);

        g2d.setFont(SMALL_FONT);
        g2d.setColor(COUNTDOWN_TEXT);
        String countdownText = "距离预计到达还有 ";
        g2d.drawString(countdownText, contentX + 30, y + 21);

        // 加粗显示时间
        g2d.setFont(getDerivedFont(BASE_FONT, Font.BOLD, 13f));
        fm = g2d.getFontMetrics();
        int textWidth = g2d.getFontMetrics(SMALL_FONT).stringWidth(countdownText);
        g2d.drawString(station.getRemainingTime(), contentX + 30 + textWidth, y + 21);

        y += countdownHeight + 15;

        // 绘制装载货量标题
        drawIcon(g2d, contentX, y + 2, "boxes", ICON_BLUE);
        g2d.setFont(getDerivedFont(BASE_FONT, Font.BOLD, 13f));
        g2d.setColor(DEPT_NAME_COLOR);
        g2d.drawString("装载货量", contentX + 20, y + 14);

        y += 20 + 8;

        // 绘制表格
        y = drawCargoTable(g2d, station.getCargoList(), contentX, y, contentWidth);

        return startY + cardHeight + SPACING;
    }

    /**
     * 绘制标签和值（带图标）
     */
    private static void drawLabelValue(Graphics2D g2d, int x, int y, String iconType, String label, String value) {
        // 绘制图标
        drawIcon(g2d, x, y + 2, iconType, ICON_BLUE);

        // 绘制标签
        g2d.setFont(NORMAL_FONT);
        g2d.setColor(LABEL_GRAY);
        g2d.drawString(label, x + 24, y + 14);

        // 绘制值
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        g2d.setColor(VALUE_BLACK);
        g2d.drawString(value, x + 24 + labelWidth + 4, y + 14);
    }

    /**
     * 绘制图标（蓝色）
     */
    private static void drawIcon(Graphics2D g2d, int x, int y, String type, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(1.5f));
        int size = 14;

        switch (type) {
            case "user":
                // 人形图标
                int headR = 3;
                g2d.drawOval(x + size / 2 - headR, y, headR * 2, headR * 2);
                g2d.drawArc(x + 2, y + 7, size - 4, 8, 0, 180);
                break;
            case "truck":
                // 车辆图标
                g2d.drawRect(x + 1, y + 4, 8, 6);
                g2d.drawRect(x + 9, y + 6, 4, 4);
                g2d.fillOval(x + 2, y + 10, 3, 3);
                g2d.fillOval(x + 8, y + 10, 3, 3);
                break;
            case "calendar":
                // 日历图标
                g2d.drawRect(x + 1, y + 2, size - 2, size - 3);
                g2d.drawLine(x + 1, y + 6, x + size - 1, y + 6);
                g2d.drawLine(x + 4, y, x + 4, y + 3);
                g2d.drawLine(x + size - 4, y, x + size - 4, y + 3);
                break;
            case "clock":
                // 时钟图标
                g2d.drawOval(x + 1, y + 1, size - 2, size - 2);
                g2d.drawLine(x + size / 2, y + size / 2, x + size / 2, y + 4);
                g2d.drawLine(x + size / 2, y + size / 2, x + size - 4, y + size / 2);
                break;
            case "flag":
                // 旗帜/终点图标
                g2d.drawLine(x + 3, y + 1, x + 3, y + size - 1);
                g2d.drawRect(x + 3, y + 1, 8, 6);
                g2d.drawLine(x + 7, y + 1, x + 7, y + 7);
                g2d.drawLine(x + 3, y + 4, x + 11, y + 4);
                break;
            case "hourglass":
                // 沙漏图标
                g2d.drawLine(x + 2, y, x + size - 2, y);
                g2d.drawLine(x + 2, y + size - 1, x + size - 2, y + size - 1);
                g2d.drawLine(x + 3, y + 1, x + size / 2, y + size / 2);
                g2d.drawLine(x + size - 3, y + 1, x + size / 2, y + size / 2);
                g2d.drawLine(x + 3, y + size - 2, x + size / 2, y + size / 2);
                g2d.drawLine(x + size - 3, y + size - 2, x + size / 2, y + size / 2);
                break;
            case "boxes":
                // 箱子图标
                g2d.drawRect(x + 1, y + 4, 6, 6);
                g2d.drawRect(x + 7, y + 4, 6, 6);
                g2d.drawRect(x + 4, y, 6, 5);
                break;
            default:
                g2d.fillOval(x + 4, y + 4, 6, 6);
                break;
        }
        g2d.setStroke(new BasicStroke(1f));
    }

    /**
     * 绘制货物表格
     */
    private static int drawCargoTable(Graphics2D g2d, List<CargoInfo> cargoList, int x, int y, int width) {
        int[] colWidths = {(int) (width * 0.25), (int) (width * 0.25), (int) (width * 0.25), (int) (width * 0.25)};
        String[] headers = {"交货方式", "件数", "重量(T)", "体积(F)"};

        // 绘制表头
        g2d.setColor(TABLE_HEADER_BG);
        g2d.fillRect(x, y, width, TABLE_ROW_HEIGHT);
        g2d.setColor(BORDER_COLOR);
        g2d.drawRect(x, y, width, TABLE_ROW_HEIGHT);

        g2d.setFont(TABLE_HEADER_FONT);
        g2d.setColor(LABEL_GRAY);
        int headerX = x;
        for (int i = 0; i < headers.length; i++) {
            drawCenteredText(g2d, headers[i], headerX, y, colWidths[i], TABLE_ROW_HEIGHT);
            if (i < headers.length - 1) {
                g2d.setColor(BORDER_COLOR);
                g2d.drawLine(headerX + colWidths[i], y, headerX + colWidths[i], y + TABLE_ROW_HEIGHT);
                g2d.setColor(LABEL_GRAY);
            }
            headerX += colWidths[i];
        }

        y += TABLE_ROW_HEIGHT;

        // 绘制表格内容
        for (int row = 0; row < cargoList.size(); row++) {
            CargoInfo cargo = cargoList.get(row);
            boolean isTotal = "合计".equals(cargo.getType());

            // 绘制行背景
            if (isTotal) {
                g2d.setColor(TOTAL_ROW_BG);
            } else {
                g2d.setColor(WHITE);
            }
            g2d.fillRect(x, y, width, TABLE_ROW_HEIGHT);
            g2d.setColor(BORDER_COLOR);
            g2d.drawRect(x, y, width, TABLE_ROW_HEIGHT);

            // 绘制单元格内容
            String[] values = {cargo.getType(), cargo.getCount(), cargo.getWeight(), cargo.getVolume()};
            int cellX = x;
            g2d.setFont(TABLE_FONT);
            for (int i = 0; i < values.length; i++) {
                if (isTotal) {
                    g2d.setColor(ICON_BLUE);
                } else {
                    g2d.setColor(VALUE_BLACK);
                }
                drawCenteredText(g2d, values[i], cellX, y, colWidths[i], TABLE_ROW_HEIGHT);
                if (i < values.length - 1) {
                    g2d.setColor(BORDER_COLOR);
                    g2d.drawLine(cellX + colWidths[i], y, cellX + colWidths[i], y + TABLE_ROW_HEIGHT);
                }
                cellX += colWidths[i];
            }

            y += TABLE_ROW_HEIGHT;
        }

        return y;
    }

    /**
     * 绘制居中文字
     */
    private static void drawCenteredText(Graphics2D g2d, String text, int x, int y, int width, int height) {
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(text, textX, textY);
    }

    // ==================== 数据模型 ====================

    @Data
    public static class ArrivalData {
        private String title;
        private List<TransferStation> stations;
    }

    @Data
    public static class TransferStation {
        private String name;
        private String manager;
        private String vehicle;
        private String loadDate;
        private String departTime;
        private String expectedArrival;
        private String remainingTime;
        private List<CargoInfo> cargoList;
    }

    @Data
    public static class CargoInfo {
        private String type;
        private String count;
        private String weight;
        private String volume;

        public CargoInfo(String type, String count, String weight, String volume) {
            this.type = type;
            this.count = count;
            this.weight = weight;
            this.volume = volume;
        }
    }
}
