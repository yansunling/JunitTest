package com.img;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.Arrays;

/**
 * 考勤日报图片生成工具类
 */
public class AttendanceReportUtil {

    // 颜色定义
    private static final Color HEADER_BG = new Color(66, 133, 244);
    private static final Color HEADER_TEXT = Color.WHITE;
    private static final Color BG_COLOR = new Color(245, 245, 245);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(51, 51, 51);
    private static final Color TEXT_SECONDARY = new Color(153, 153, 153);
    private static final Color ORANGE = new Color(255, 152, 0);
    private static final Color GREEN = new Color(76, 175, 80);
    private static final Color RED = new Color(244, 67, 54);
    private static final Color BLUE = new Color(33, 150, 243);
    private static final Color BORDER_COLOR = new Color(238, 238, 238);

    // iPhone @2x (375pt * 2 = 750px)
    private static final int WIDTH = 750;

    private AttendanceReportUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 生成考勤日报图片
     *
     * @param filePath      保存路径
     * @param title         标题
     * @param date          日期
     * @param totalCount    应到人数
     * @param normalCount   正常人数
     * @param absentCount   未出勤人数
     * @param absentList    未出勤人员列表
     * @param halfDayList   出勤半天人员列表
     */
    public static void generateReport(String filePath, String title, String date,
                                       int totalCount, int normalCount, int absentCount,
                                       List<String> absentList, List<String> halfDayList) {
        int height = calculateHeight(absentList, halfDayList);
        BufferedImage image = new BufferedImage(WIDTH, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 背景
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, WIDTH, height);

        int y = 0;
        y = drawHeader(g2d, y, title, date);
        y = drawStatisticsCard(g2d, y + 30, totalCount, normalCount, absentCount);

        // 先未出勤人员（如果有）
        if (isValidList(absentList)) {
            y = drawAbsentList(g2d, y + 30, absentList);
        }

        // 再出勤半天（如果有）
        if (isValidList(halfDayList)) {
            drawHalfDayList(g2d, y + 30, halfDayList);
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("图片已保存到: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculateHeight(List<String> absentList, List<String> halfDayList) {
        int headerHeight = 160;
        int statsCardHeight = 160;
        int itemHeight = 90;
        int listHeaderHeight = 80;
        int gap = 30;
        int bottomPadding = 50;

        int height = headerHeight + gap + statsCardHeight;

        if (isValidList(absentList)) {
            height += gap + listHeaderHeight + absentList.size() * itemHeight;
        }

        if (isValidList(halfDayList)) {
            height += gap + listHeaderHeight + halfDayList.size() * itemHeight;
        }

        return height + bottomPadding;
    }

    private static boolean isValidList(List<String> list) {
        return list != null && !list.isEmpty() && !(list.size() == 1 && list.get(0).isEmpty());
    }

    private static int drawHeader(Graphics2D g2d, int y, String title, String date) {
        int headerHeight = 160;

        g2d.setColor(HEADER_BG);
        g2d.fillRect(0, y, WIDTH, headerHeight);

        g2d.setColor(HEADER_TEXT);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 44));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(title, (WIDTH - fm.stringWidth(title)) / 2, y + 70);

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        fm = g2d.getFontMetrics();
        g2d.drawString(date, (WIDTH - fm.stringWidth(date)) / 2, y + 120);

        return y + headerHeight;
    }

    private static int drawStatisticsCard(Graphics2D g2d, int y, int totalCount, int normalCount, int absentCount) {
        int cardHeight = 160;
        int margin = 30;
        int cardWidth = WIDTH - margin * 2;

        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, cardHeight, 30, 30));

        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        int cellWidth = cardWidth / 3;
        g2d.drawLine(margin + cellWidth, y + 20, margin + cellWidth, y + cardHeight - 20);
        g2d.drawLine(margin + cellWidth * 2, y + 20, margin + cellWidth * 2, y + cardHeight - 20);

        drawStatItem(g2d, margin, y, cellWidth, cardHeight, String.valueOf(totalCount), "应到", BLUE, "people");
        drawStatItem(g2d, margin + cellWidth, y, cellWidth, cardHeight, String.valueOf(normalCount), "正常", GREEN, "check");
        drawStatItem(g2d, margin + cellWidth * 2, y, cellWidth, cardHeight, String.valueOf(absentCount), "未出勤", RED, "cross");

        return y + cardHeight;
    }

    private static void drawStatItem(Graphics2D g2d, int x, int y, int w, int h, String value, String label, Color color, String iconType) {
        int centerX = x + w / 2;

        int iconSize = 36;
        drawIcon(g2d, iconType, centerX - iconSize / 2, y + 20, iconSize, color);

        g2d.setColor(color);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(value, centerX - fm.stringWidth(value) / 2, y + 100);

        g2d.setColor(TEXT_SECONDARY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        fm = g2d.getFontMetrics();
        g2d.drawString(label, centerX - fm.stringWidth(label) / 2, y + 135);
    }

    private static void drawIcon(Graphics2D g2d, String type, int x, int y, int size, Color color) {
        switch (type) {
            case "people":
                drawPeopleIcon(g2d, x, y, size, color);
                break;
            case "check":
                drawCheckIcon(g2d, x, y, size, color);
                break;
            case "cross":
                drawCrossIcon(g2d, x, y, size, color);
                break;
            case "warning":
                drawWarningIcon(g2d, x, y, size, color);
                break;
        }
    }

    private static void drawPeopleIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        int offsetY = 10;

        int head1 = size / 4;
        g2d.fillOval(x - 2, y + 6 + offsetY, head1, head1);
        g2d.fillArc(x - 4, y + head1 + 4 + offsetY, head1 + 6, (int)(size * 0.45), 0, 180);

        int head2 = (int)(size * 0.35);
        g2d.fillOval(x + size / 2 - head2 / 2, y + offsetY, head2, head2);
        g2d.fillArc(x + size / 2 - head2 / 2 - 4, y + head2 - 4 + offsetY, head2 + 8, (int)(size * 0.6), 0, 180);

        int head3 = size / 4;
        g2d.fillOval(x + size - head3 + 2, y + 6 + offsetY, head3, head3);
        g2d.fillArc(x + size - head3 - 2, y + head3 + 4 + offsetY, head3 + 6, (int)(size * 0.45), 0, 180);
    }

    private static void drawCheckIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Float(x, y, size, size));

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int[] xPoints = {x + size / 4, x + size * 2 / 5, x + size * 3 / 4};
        int[] yPoints = {y + size / 2, y + size * 2 / 3, y + size / 3};
        g2d.drawPolyline(xPoints, yPoints, 3);
    }

    private static void drawCrossIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Float(x, y, size, size));

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int p = size / 4;
        g2d.drawLine(x + p, y + p, x + size - p, y + size - p);
        g2d.drawLine(x + size - p, y + p, x + p, y + size - p);
    }

    private static void drawWarningIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int[] xPoints = {x + size / 2, x + 2, x + size - 2};
        int[] yPoints = {y + 2, y + size - 2, y + size - 2};
        g2d.drawPolygon(xPoints, yPoints, 3);

        g2d.setStroke(new BasicStroke(size / 5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(x + size / 2, y + size / 3, x + size / 2, y + size * 3 / 5);
        g2d.fillOval(x + size / 2 - size / 10, y + size * 7 / 10, size / 5, size / 5);
    }

    private static int drawAbsentList(Graphics2D g2d, int y, List<String> absentList) {
        int margin = 30;
        int cardWidth = WIDTH - margin * 2;
        int headerHeight = 80;
        int itemHeight = 90;
        int totalHeight = headerHeight + absentList.size() * itemHeight;

        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        g2d.setColor(RED);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        drawIcon(g2d, "cross", margin + 30, y + 24, 32, RED);
        g2d.setColor(RED);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 28));
        g2d.drawString("未出勤人员", margin + 76, y + 52);

        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(margin + 20, y + headerHeight, margin + cardWidth - 20, y + headerHeight);

        for (int i = 0; i < absentList.size(); i++) {
            int itemY = y + headerHeight + i * itemHeight;
            drawEmployeeItem(g2d, margin, itemY, cardWidth, itemHeight, absentList.get(i), "0", RED);

            if (i < absentList.size() - 1) {
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(margin + 30, itemY + itemHeight, margin + cardWidth - 30, itemY + itemHeight);
            }
        }

        return y + totalHeight;
    }

    private static int drawHalfDayList(Graphics2D g2d, int y, List<String> halfDayList) {
        int margin = 30;
        int cardWidth = WIDTH - margin * 2;
        int headerHeight = 80;
        int itemHeight = 90;
        int totalHeight = headerHeight + halfDayList.size() * itemHeight;

        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        g2d.setColor(ORANGE);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        drawIcon(g2d, "warning", margin + 30, y + 24, 32, ORANGE);
        g2d.setColor(ORANGE);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 28));
        g2d.drawString("出勤半天", margin + 76, y + 52);

        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(margin + 20, y + headerHeight, margin + cardWidth - 20, y + headerHeight);

        for (int i = 0; i < halfDayList.size(); i++) {
            int itemY = y + headerHeight + i * itemHeight;
            drawEmployeeItem(g2d, margin, itemY, cardWidth, itemHeight, halfDayList.get(i), "0.5", ORANGE);

            if (i < halfDayList.size() - 1) {
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(margin + 30, itemY + itemHeight, margin + cardWidth - 30, itemY + itemHeight);
            }
        }

        return y + totalHeight;
    }

    private static void drawEmployeeItem(Graphics2D g2d, int x, int y, int w, int h, String name, String status, Color statusColor) {
        int padding = 40;

        g2d.setColor(TEXT_PRIMARY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        g2d.drawString(name, x + padding, y + h / 2 + 10);

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        FontMetrics fm = g2d.getFontMetrics();
        int tagWidth = fm.stringWidth(status) + 32;
        int tagHeight = 44;
        int tagX = x + w - padding - tagWidth;
        int tagY = y + (h - tagHeight) / 2;

        g2d.setColor(statusColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new RoundRectangle2D.Float(tagX, tagY, tagWidth, tagHeight, 10, 10));

        g2d.setColor(new Color(statusColor.getRed(), statusColor.getGreen(), statusColor.getBlue(), 25));
        g2d.fill(new RoundRectangle2D.Float(tagX + 1, tagY + 1, tagWidth - 2, tagHeight - 2, 8, 8));

        g2d.setColor(statusColor);
        g2d.drawString(status, tagX + 16, y + h / 2 + 8);
    }

    public static void main(String[] args) {
        // 使用示例
        AttendanceReportUtil.generateReport(
            "C:\\Users\\yansunling\\Desktop\\attendance_report.png",
            "今日考勤日报",
            "2026年1月4日星期日",
            128,  // 应到
            112,  // 正常
            8,    // 未出勤
            Arrays.asList("赵六", "郑十", "钱一一"),  // 未出勤人员
            Arrays.asList("王五", "周八")             // 出勤半天人员
        );
    }
}
