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
 * 今日考勤日报UI绘制（适配iPhone @2x，750px宽度）
 */
public class AttendanceReportUI {

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
    private int width = 750;
    private int height;

    private String title;
    private String date;
    private int totalCount;      // 应到
    private int normalCount;     // 正常
    private int absentCount;     // 未出勤
    private List<String> lateEarlyList;
    private List<String> absentList;

    public AttendanceReportUI(String title, String date, int totalCount, int normalCount, int absentCount,
                              List<String> lateEarlyList, List<String> absentList) {
        this.title = title;
        this.date = date;
        this.totalCount = totalCount;
        this.normalCount = normalCount;
        this.absentCount = absentCount;
        this.lateEarlyList = lateEarlyList;
        this.absentList = absentList;
        this.height = calculateHeight();
    }

    private int calculateHeight() {
        int headerHeight = 160;
        int statsCardHeight = 160;
        int itemHeight = 90;
        int listHeaderHeight = 80;
        int gap = 30;
        int bottomPadding = 50;

        int height = headerHeight + gap + statsCardHeight;

        // 未出勤人员列表
        if (absentList != null && !absentList.isEmpty() && !(absentList.size() == 1 && absentList.get(0).isEmpty())) {
            height += gap + listHeaderHeight + absentList.size() * itemHeight;
        }

        // 出勤半天人员列表
        if (lateEarlyList != null && !lateEarlyList.isEmpty() && !(lateEarlyList.size() == 1 && lateEarlyList.get(0).isEmpty())) {
            height += gap + listHeaderHeight + lateEarlyList.size() * itemHeight;
        }

        return height + bottomPadding;
    }

    /**
     * 判断列表是否有效（非空且包含有效数据）
     */
    private boolean isValidList(List<String> list) {
        return list != null && !list.isEmpty() && !(list.size() == 1 && list.get(0).isEmpty());
    }

    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 背景
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, width, height);

        int y = 0;
        y = drawHeader(g2d, y);
        y = drawStatisticsCard(g2d, y + 30);

        // 先未出勤人员（如果有）
        if (isValidList(absentList)) {
            y = drawAbsentList(g2d, y + 30);
        }

        // 再出勤半天（如果有）
        if (isValidList(lateEarlyList)) {
            drawHalfDayList(g2d, y + 30);
        }
    }

    private int drawHeader(Graphics2D g2d, int y) {
        int headerHeight = 160;

        g2d.setColor(HEADER_BG);
        g2d.fillRect(0, y, width, headerHeight);

        g2d.setColor(HEADER_TEXT);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 44));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(title, (width - fm.stringWidth(title)) / 2, y + 70);

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        fm = g2d.getFontMetrics();
        g2d.drawString(date, (width - fm.stringWidth(date)) / 2, y + 120);

        return y + headerHeight;
    }

    private int drawStatisticsCard(Graphics2D g2d, int y) {
        int cardHeight = 160;
        int margin = 30;
        int cardWidth = width - margin * 2;

        // 卡片背景
        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, cardHeight, 30, 30));

        // 分隔线
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        int cellWidth = cardWidth / 3;
        g2d.drawLine(margin + cellWidth, y + 20, margin + cellWidth, y + cardHeight - 20);
        g2d.drawLine(margin + cellWidth * 2, y + 20, margin + cellWidth * 2, y + cardHeight - 20);

        // 三个统计项
        drawStatItem(g2d, margin, y, cellWidth, cardHeight, String.valueOf(totalCount), "应到", BLUE, "people");
        drawStatItem(g2d, margin + cellWidth, y, cellWidth, cardHeight, String.valueOf(normalCount), "正常", GREEN, "check");
        drawStatItem(g2d, margin + cellWidth * 2, y, cellWidth, cardHeight, String.valueOf(absentCount), "未出勤", RED, "cross");

        return y + cardHeight;
    }

    private void drawStatItem(Graphics2D g2d, int x, int y, int w, int h, String value, String label, Color color, String iconType) {
        int centerX = x + w / 2;

        // 图标在上方
        int iconSize = 36;
        drawIcon(g2d, iconType, centerX - iconSize / 2, y + 20, iconSize, color);

        // 数字
        g2d.setColor(color);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(value, centerX - fm.stringWidth(value) / 2, y + 100);

        // 标签
        g2d.setColor(TEXT_SECONDARY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        fm = g2d.getFontMetrics();
        g2d.drawString(label, centerX - fm.stringWidth(label) / 2, y + 135);
    }

    /**
     * 绘制图标（模仿阿里矢量图标风格）
     */
    private void drawIcon(Graphics2D g2d, String type, int x, int y, int size, Color color) {
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

    /**
     * 三人头像图标（放大版）
     */
    private void drawPeopleIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        int offsetY = 10;  // 整体下移

        // 左边的人
        int head1 = size / 4;
        g2d.fillOval(x - 2, y + 6 + offsetY, head1, head1);
        g2d.fillArc(x - 4, y + head1 + 4 + offsetY, head1 + 6, (int)(size * 0.45), 0, 180);

        // 中间的人（最大，最前面）
        int head2 = (int)(size * 0.35);
        g2d.fillOval(x + size / 2 - head2 / 2, y + offsetY, head2, head2);
        g2d.fillArc(x + size / 2 - head2 / 2 - 4, y + head2 - 4 + offsetY, head2 + 8, (int)(size * 0.6), 0, 180);

        // 右边的人
        int head3 = size / 4;
        g2d.fillOval(x + size - head3 + 2, y + 6 + offsetY, head3, head3);
        g2d.fillArc(x + size - head3 - 2, y + head3 + 4 + offsetY, head3 + 6, (int)(size * 0.45), 0, 180);
    }

    /**
     * 绿色实心圆圈+白色打勾图标
     */
    private void drawCheckIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        // 实心圆背景
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Float(x, y, size, size));

        // 白色打勾
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int[] xPoints = {x + size / 4, x + size * 2 / 5, x + size * 3 / 4};
        int[] yPoints = {y + size / 2, y + size * 2 / 3, y + size / 3};
        g2d.drawPolyline(xPoints, yPoints, 3);
    }

    /**
     * 红色实心圆圈+白色X图标
     */
    private void drawCrossIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        // 实心圆背景
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Float(x, y, size, size));

        // 白色X
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int p = size / 4;
        g2d.drawLine(x + p, y + p, x + size - p, y + size - p);
        g2d.drawLine(x + size - p, y + p, x + p, y + size - p);
    }

    /**
     * 警告三角图标
     */
    private void drawWarningIcon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // 三角形
        int[] xPoints = {x + size / 2, x + 2, x + size - 2};
        int[] yPoints = {y + 2, y + size - 2, y + size - 2};
        g2d.drawPolygon(xPoints, yPoints, 3);

        // 感叹号
        g2d.setStroke(new BasicStroke(size / 5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(x + size / 2, y + size / 3, x + size / 2, y + size * 3 / 5);
        g2d.fillOval(x + size / 2 - size / 10, y + size * 7 / 10, size / 5, size / 5);
    }

    private int drawHalfDayList(Graphics2D g2d, int y) {
        int margin = 30;
        int cardWidth = width - margin * 2;
        int headerHeight = 80;
        int itemHeight = 90;
        int totalHeight = headerHeight + lateEarlyList.size() * itemHeight;

        // 卡片背景
        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        // 边框
        g2d.setColor(ORANGE);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        // 标题
        drawIcon(g2d, "warning", margin + 30, y + 24, 32, ORANGE);
        g2d.setColor(ORANGE);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 28));
        g2d.drawString("出勤半天", margin + 76, y + 52);

        // 分隔线
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(margin + 20, y + headerHeight, margin + cardWidth - 20, y + headerHeight);

        // 人员列表
        for (int i = 0; i < lateEarlyList.size(); i++) {
            int itemY = y + headerHeight + i * itemHeight;
            drawEmployeeItem(g2d, margin, itemY, cardWidth, itemHeight, lateEarlyList.get(i), "0.5", ORANGE);

            if (i < lateEarlyList.size() - 1) {
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(margin + 30, itemY + itemHeight, margin + cardWidth - 30, itemY + itemHeight);
            }
        }

        return y + totalHeight;
    }

    private int drawAbsentList(Graphics2D g2d, int y) {
        int margin = 30;
        int cardWidth = width - margin * 2;
        int headerHeight = 80;
        int itemHeight = 90;
        int totalHeight = headerHeight + absentList.size() * itemHeight;

        // 卡片背景
        g2d.setColor(CARD_BG);
        g2d.fill(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        // 边框
        g2d.setColor(RED);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new RoundRectangle2D.Float(margin, y, cardWidth, totalHeight, 30, 30));

        // 标题
        drawIcon(g2d, "cross", margin + 30, y + 24, 32, RED);
        g2d.setColor(RED);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 28));
        g2d.drawString("未出勤人员", margin + 76, y + 52);

        // 分隔线
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(margin + 20, y + headerHeight, margin + cardWidth - 20, y + headerHeight);

        // 人员列表
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

    private void drawEmployeeItem(Graphics2D g2d, int x, int y, int w, int h, String name, String status, Color statusColor) {
        int padding = 40;

        // 姓名
        g2d.setColor(TEXT_PRIMARY);
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        g2d.drawString(name, x + padding, y + h / 2 + 10);

        // 状态标签
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        FontMetrics fm = g2d.getFontMetrics();
        int tagWidth = fm.stringWidth(status) + 32;
        int tagHeight = 44;
        int tagX = x + w - padding - tagWidth;
        int tagY = y + (h - tagHeight) / 2;

        // 标签边框（先画边框再填充背景，确保边框可见）
        g2d.setColor(statusColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new RoundRectangle2D.Float(tagX, tagY, tagWidth, tagHeight, 10, 10));

        // 标签背景
        g2d.setColor(new Color(statusColor.getRed(), statusColor.getGreen(), statusColor.getBlue(), 25));
        g2d.fill(new RoundRectangle2D.Float(tagX + 1, tagY + 1, tagWidth - 2, tagHeight - 2, 8, 8));

        // 标签文字
        g2d.setColor(statusColor);
        g2d.drawString(status, tagX + 16, y + h / 2 + 8);
    }

    /**
     * 保存为图片（无预览，直接保存）
     */
    public void saveAsImage(String filePath) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        draw(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("图片已保存到: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 测试数据
        String title = "今日考勤日报";
        String date = "2026年1月4日星期日";
        int totalCount = 128;      // 应到
        int normalCount = 112;     // 正常
        int absentCount = 8;       // 未出勤

        // 人员列表（可以传空列表 Arrays.asList() 表示无人员）
        List<String> lateEarlyList = Arrays.asList("王五", "周八");
        List<String> absentList = Arrays.asList("赵六", "郑十", "钱一一","王五", "周八","王五", "周八","赵六", "郑十", "钱一一","王五", "周八","王五", "周八");

        // 直接保存图片，无预览
        AttendanceReportUI report = new AttendanceReportUI(title, date, totalCount, normalCount, absentCount, lateEarlyList, absentList);
        report.saveAsImage("C:\\Users\\yansunling\\Desktop\\attendance_report.png");
    }
}
