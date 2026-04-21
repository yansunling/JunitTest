package com.img;
import com.yd.utils.common.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DispatchOrderImageGenerator {

    private static final int IMAGE_WIDTH = 2000;
    private static final int CELL_PADDING = 4;
    private static final int FONT_SIZE_TITLE = 32;
    private static final int FONT_SIZE_GROUP = 26;
    private static final int FONT_SIZE_TABLE = 18;
    private static final int FONT_SIZE_NOTE = 16;

    private static final Color COLOR_BORDER_MAIN = new Color(0, 122, 255);
    private static final Color COLOR_HEADER_BG = new Color(230, 240, 250);
    private static final Color COLOR_TOTAL_BG = new Color(245, 245, 245);
    private static final Color COLOR_BLACK = new Color(30, 30, 30);
    private static final Color COLOR_GRAY = new Color(100, 100, 100);
    private static final Color COLOR_GROUP_TITLE = new Color(0, 100, 200);

    static class DispatchItem {
        String seq;
        String carNo;
        String waybillNo;
        String subWaybillNo;
        String receiver;
        String receiverPhone;
        String lastStation;
        String goodsName;
        String qty;
        String weight;
        String volume;
        String packageType;
        String deliveryType;
        String returnType;
        String address;
        String timeSlot;
        String seat;
        String remark;

        public DispatchItem(String seq, String carNo, String waybillNo, String subWaybillNo,
                            String receiver, String receiverPhone, String lastStation,
                            String goodsName, String qty, String weight, String volume,
                            String packageType, String deliveryType, String returnType,
                            String address, String timeSlot, String seat, String remark) {
            this.seq = seq;
            this.carNo = carNo;
            this.waybillNo = waybillNo;
            this.subWaybillNo = subWaybillNo;
            this.receiver = receiver;
            this.receiverPhone = receiverPhone;
            this.lastStation = lastStation;
            this.goodsName = goodsName;
            this.qty = qty;
            this.weight = weight;
            this.volume = volume;
            this.packageType = packageType;
            this.deliveryType = deliveryType;
            this.returnType = returnType;
            this.address = address;
            this.timeSlot = timeSlot;
            this.seat = seat;
            this.remark = remark;
        }
    }

    static class DispatchGroup {
        String groupName;
        List<DispatchItem> items;
        int totalQty;
        int totalWeight;
        double totalVolume;
        int totalFee;

        public DispatchGroup(String groupName, List<DispatchItem> items,
                             int totalQty, int totalWeight, double totalVolume, int totalFee) {
            this.groupName = groupName;
            this.items = items;
            this.totalQty = totalQty;
            this.totalWeight = totalWeight;
            this.totalVolume = totalVolume;
            this.totalFee = totalFee;
        }
    }

    public static void main(String[] args) throws IOException {
        List<DispatchGroup> groups = new ArrayList<>();

        List<DispatchItem> items1 = new ArrayList<>();
        items1.add(new DispatchItem(
                "1", "3821805", "602704859", "15T19U1049-571K",
                "张佳豪", "13906282556", "台州南转运场",
                "水泵", "571", "7550", "15.1",
                "571纸", "送货", "信封",
                "呈贡区奥斯迪小商品加工基地A区10栋1楼", "白天", "-", "-"
        ));
        groups.add(new DispatchGroup("派车单1", items1, 571, 7550, 15.100, 3775));

        List<DispatchItem> items2 = new ArrayList<>();
        items2.add(new DispatchItem(
                "1", "3821805", "602703120", "14T19U1049-178K",
                "罗涛", "15808853210", "台州南转运场",
                "泵头", "178", "4300", "13.27",
                "161纸+17木", "送货", "信封",
                "呈贡区奥斯迪D区4栋", "-", "-", "-"
        ));
        items2.add(new DispatchItem(
                "2", "3821805", "602705180", "15T19U1020-150K",
                "罗涛", "15808853210", "台州南转运场",
                "泵头", "150", "1575", "6.25",
                "150纸", "送货", "信封",
                "呈贡区奥斯迪D区4栋", "-", "-", "-"
        ));
        groups.add(new DispatchGroup("派车单2", items2, 328, 5875, 19.520, 2761));

        int rowHeight = 50;
        int headerHeight = 80;
        int groupTitleHeight = 45;
        int tableHeaderHeight = 45;
        int totalRowHeight = 45;
        int noteHeight = 50;
        int margin = 30;

        int totalHeight = headerHeight + margin;
        for (DispatchGroup group : groups) {
            totalHeight += groupTitleHeight + tableHeaderHeight
                    + group.items.size() * rowHeight + totalRowHeight + margin;
        }
        totalHeight += noteHeight + margin;

        BufferedImage image = new BufferedImage(IMAGE_WIDTH, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, IMAGE_WIDTH, totalHeight);
        g2d.setColor(COLOR_BLACK);

        drawHeader(g2d, "金马村分拨场派车单汇总", "2026年03月17日", headerHeight);

        int y = headerHeight + margin;
        for (DispatchGroup group : groups) {
            y = drawGroup(g2d, group, y, rowHeight, tableHeaderHeight, totalRowHeight, groupTitleHeight);
            y += margin;
        }

        drawNote(g2d, y, noteHeight);
        g2d.dispose();

        File output = new File("dispatch_order_final.png");
        ImageIO.write(image, "PNG", output);
        System.out.println("✅ 图片生成完成：" + output.getAbsolutePath());
    }



    private static void drawHeader(Graphics2D g2d, String title, String date, int height) {
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(20, 20, IMAGE_WIDTH - 20, 20);
        g2d.setStroke(new BasicStroke(1));

        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TITLE));
        FontMetrics fmTitle = g2d.getFontMetrics();
        int titleX = (IMAGE_WIDTH - fmTitle.stringWidth(title) - fmTitle.stringWidth(date) - 20) / 2;
        int titleY = height / 2 + fmTitle.getAscent() / 2;
        g2d.drawString(title, titleX, titleY);

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        g2d.setColor(COLOR_GRAY);
        int dateX = titleX + fmTitle.stringWidth(title) + 20;
        g2d.drawString(date, dateX, titleY);
        g2d.setColor(COLOR_BLACK);
    }

    private static int drawGroup(Graphics2D g2d, DispatchGroup group, int y,
                                 int rowHeight, int tableHeaderHeight, int totalRowHeight, int groupTitleHeight) {
        int groupContentHeight = groupTitleHeight + tableHeaderHeight
                + group.items.size() * rowHeight + totalRowHeight;
        g2d.setColor(COLOR_BORDER_MAIN);
        g2d.drawRoundRect(20, y, IMAGE_WIDTH - 40, groupContentHeight, 8, 8);
        g2d.setColor(COLOR_BLACK);

        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_GROUP));
        g2d.setColor(COLOR_GROUP_TITLE);
        g2d.drawString(group.groupName, 35, y + groupTitleHeight / 2 + g2d.getFontMetrics().getAscent() / 2);
        g2d.setColor(COLOR_BLACK);
        y += groupTitleHeight;

        String[] headers = {
                "序号", "车皮号", "运单号", "次运号", "收货人", "收货人电话",
                "上一站", "品名", "件数", "重量(kg)", "体积(m3)", "包装",
                "交货方式", "回单", "客户地址", "时段", "库位", "送货备注"
        };
        int[] colWidths = {
                60, 90, 110, 180, 75, 130,
                120,80, 60, 85, 85, 80,
                80, 55, 280, 55, 150, 150
        };
        drawTableHeader(g2d, headers, colWidths, y, tableHeaderHeight);
        y += tableHeaderHeight;

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        for (DispatchItem item : group.items) {
            String[] rowData = {
                    item.seq, item.carNo, item.waybillNo, item.subWaybillNo,
                    item.receiver, item.receiverPhone, item.lastStation,
                    item.goodsName, item.qty, item.weight, item.volume,
                    item.packageType, item.deliveryType, item.returnType,
                    item.address, item.timeSlot, item.seat, item.remark
            };
            drawNormalTableRow(g2d, rowData, colWidths, y, rowHeight);
            y += rowHeight;
        }

        drawMergedTotalRow(g2d, group, colWidths, y, totalRowHeight);
        y += totalRowHeight;
        return y;
    }

    private static void drawTableHeader(Graphics2D g2d, String[] headers, int[] colWidths, int y, int height) {
        g2d.setColor(COLOR_HEADER_BG);
        g2d.fillRect(30, y, IMAGE_WIDTH - 72, height);
        g2d.setColor(COLOR_BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TABLE));

        int x = 30;
        for (int i = 0; i < headers.length; i++) {
            g2d.drawRect(x, y, colWidths[i], height);
            drawCenteredString(g2d, headers[i], x, y, colWidths[i], height);
            x += colWidths[i];
        }
    }

    private static void drawNormalTableRow(Graphics2D g2d, String[] rowData, int[] colWidths, int y, int height) {
        int x = 30;
        List<Integer> list= Arrays.asList(3,6,11,14,16,17);
        for (int i = 0; i < rowData.length; i++) {
            g2d.drawRect(x, y, colWidths[i], height);
            if (rowData[i] != null && !rowData[i].isEmpty()) {
                if (list.contains(i)) {
                    drawLeftAlignedWrappedString(g2d, rowData[i], x, y, colWidths[i], height);
                } else {
                    drawCenteredString(g2d, rowData[i], x, y, colWidths[i], height);
                }
            }
            x += colWidths[i];
        }
    }

    private static void drawMergedTotalRow(Graphics2D g2d, DispatchGroup group, int[] colWidths, int y, int height) {
        g2d.setColor(COLOR_TOTAL_BG);
        g2d.fillRect(30, y, IMAGE_WIDTH - 60, height);
        g2d.setColor(COLOR_BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TABLE));

        int x = 30;
        // 1. 合并「序号→品名」前8列，总计文字居中
        int mergedTotalCols = 8;
        int mergedTotalWidth = 0;
        for (int i = 0; i < mergedTotalCols; i++) mergedTotalWidth += colWidths[i];
        g2d.drawRect(x, y, mergedTotalWidth, height);
        drawCenteredString(g2d, "总计", x, y, mergedTotalWidth, height);
        x += mergedTotalWidth;

        // 2. 件数、重量、体积（黑色加粗）
        g2d.drawRect(x, y, colWidths[8], height);
        drawCenteredString(g2d, String.valueOf(group.totalQty), x, y, colWidths[8], height);
        x += colWidths[8];

        g2d.drawRect(x, y, colWidths[9], height);
        drawCenteredString(g2d, String.valueOf(group.totalWeight), x, y, colWidths[9], height);
        x += colWidths[9];

        g2d.drawRect(x, y, colWidths[10], height);
        drawCenteredString(g2d, String.format("%.3f", group.totalVolume), x, y, colWidths[10], height);
        x += colWidths[10];

        // 3. 合并「包装→送货备注」列，显示到付费用（右对齐）
        int mergedFeeCols = 7; // 包装(11) → 送货备注(17)
        int mergedFeeWidth = 0;
        for (int i = 11; i < colWidths.length; i++) mergedFeeWidth += colWidths[i];
        g2d.drawRect(x, y, mergedFeeWidth, height);
        String feeText = "到付费用合计: " + group.totalFee;
        drawCenteredString(g2d, feeText, x, y, mergedFeeWidth, height);
    }

    private static void drawCenteredString(Graphics2D g2d, String text, int x, int y, int width, int height) {
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, textX, textY);
    }

    private static void drawLeftAlignedString(Graphics2D g2d, String text, int x, int y, int width, int height) {
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + CELL_PADDING;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, textX, textY);
    }

    private static void drawRightAlignedString(Graphics2D g2d, String text, int x, int y, int width, int height) {
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + width - fm.stringWidth(text) - CELL_PADDING;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, textX, textY);
    }

    private static void drawLeftAlignedWrappedString(Graphics2D g2d, String text, int x, int y, int width, int height) {
        if (text == null || text.isEmpty()) return;
        FontMetrics fm = g2d.getFontMetrics();
        int availableWidth = width - 2 * CELL_PADDING;
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (fm.stringWidth(currentLine.toString() + c) > availableWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }
            currentLine.append(c);
        }
        if (StringUtils.isNotBlank(currentLine.toString())) lines.add(currentLine.toString());

        int totalTextHeight = lines.size() * fm.getHeight();
        int startY = y + (height - totalTextHeight) / 2 + fm.getAscent();
        for (String line : lines) {
            g2d.drawString(line, x + CELL_PADDING, startY);
            startY += fm.getHeight();
        }
    }

    private static void drawNote(Graphics2D g2d, int y, int height) {
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_NOTE));
        g2d.setColor(COLOR_GRAY);
        String note = "注：派车单承运人对上述代收款及货物安全负全部责任，签字确认生效！";
        FontMetrics fm = g2d.getFontMetrics();
        int noteX = (IMAGE_WIDTH - fm.stringWidth(note)) / 2;
        int noteY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(note, noteX, noteY);
        g2d.setColor(COLOR_BLACK);
    }
}