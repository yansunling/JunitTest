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

public class DispatchOrderFinal {

    private static final int IMAGE_WIDTH = 2000;
    private static final int CELL_PADDING = 4;
    private static final int FONT_SIZE_TITLE = 32;
    private static final int FONT_SIZE_GROUP = 26;
    private static final int FONT_SIZE_TABLE = 18;
    private static final int FONT_SIZE_NOTE = 16;

    private static int FONT_ROW_HEIGHT;

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
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D tempG = tempImg.createGraphics();
        tempG.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        FONT_ROW_HEIGHT = tempG.getFontMetrics().getHeight();
        tempG.dispose();

        List<DispatchGroup> groups = new ArrayList<>();

        // 测试数据：库位、备注、地址都可能换行
        List<DispatchItem> items1 = new ArrayList<>();
        items1.add(new DispatchItem(
                "1", "3821805", "602704859", "15T19U1049-571K",
                "张佳豪", "13906282556", "台州南转运场",
                "水泵", "571", "7550", "15.1",
                "571纸", "送货", "信封",
                "呈贡区奥斯迪小商品加工基地A区10栋1楼很长很长很长",
                "白天\n分时段", "A库-01\nB区-02", "优先送货\n易碎品\n注意防潮"
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

        int[] colWidths = {
                60, 90, 110, 180, 75, 130,
                120,80, 60, 85, 85, 80,
                80, 55, 280, 55, 150, 150
        };

        int headerHeight = 80;
        int groupTitleHeight = 45;
        int tableHeaderHeight = 45;
        int totalRowHeight = 45;
        int noteHeight = 50;
        int margin = 50;

        // 整体高度计算
        int totalHeight = headerHeight + margin;
        for (DispatchGroup g : groups) {
            int sum = 0;
            for (DispatchItem item : g.items) {
                sum += calcRealRowHeight(item, colWidths);
            }
            int groupHeight = groupTitleHeight + tableHeaderHeight + sum + totalRowHeight;
            totalHeight += groupHeight + margin;
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
            y = drawGroup(g2d, group, y, colWidths, groupTitleHeight, tableHeaderHeight, totalRowHeight);
            y += margin;
        }

        drawNote(g2d, y, noteHeight);
        g2d.dispose();

        File out = new File("dispatch_final.png");
        ImageIO.write(image, "png", out);
        System.out.println("生成完成：" + out.getAbsolutePath());
    }

    // ========== 核心：整行高度 = 所有单元格中最大换行数 ==========
    private static int calcRealRowHeight(DispatchItem item, int[] widths) {
        String[] texts = {
                item.seq, item.carNo, item.waybillNo, item.subWaybillNo,
                item.receiver, item.receiverPhone, item.lastStation,
                item.goodsName, item.qty, item.weight, item.volume,
                item.packageType, item.deliveryType, item.returnType,
                item.address, item.timeSlot, item.seat, item.remark
        };
        int maxLines = 1;
        for (int i = 0; i < texts.length; i++) {
            int lines = countLines(texts[i], widths[i]);
            if (lines > maxLines) maxLines = lines;
        }
        return FONT_ROW_HEIGHT * maxLines + CELL_PADDING * 2;
    }

    // 计算单个单元格行数（支持 \n 手动换行 + 自动换行）
    private static int countLines(String text, int width) {
        if (StringUtils.isBlank(text)) return 1;
        FontMetrics fm = new Canvas().getFontMetrics(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        int avail = width - 2 * CELL_PADDING;
        int lines = 1;
        int current = 0;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                lines++;
                current = 0;
                continue;
            }
            int w = fm.charWidth(c);
            if (current + w > avail) {
                lines++;
                current = w;
            } else {
                current += w;
            }
        }
        return lines;
    }

    private static int drawGroup(Graphics2D g2d, DispatchGroup group, int y, int[] ws,
                                 int gH, int hH, int tH) {
        // 计算本组真实总高度
        int itemH = 0;
        for (DispatchItem it : group.items) itemH += calcRealRowHeight(it, ws);
        int totalGroupH = gH + hH + itemH + tH+20;

        g2d.setColor(COLOR_BORDER_MAIN);
        g2d.drawRoundRect(20, y, IMAGE_WIDTH - 40, totalGroupH, 8, 8);
        g2d.setColor(COLOR_BLACK);

        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_GROUP));
        g2d.setColor(COLOR_GROUP_TITLE);
        g2d.drawString(group.groupName, 35, y + gH / 2 + g2d.getFontMetrics().getAscent() / 2);
        g2d.setColor(COLOR_BLACK);
        y += gH;

        String[] heads = {
                "序号", "车皮号", "运单号", "次运号", "收货人", "收货人电话",
                "上一站", "品名", "件数", "重量(kg)", "体积(m3)", "包装",
                "交货方式", "回单", "客户地址", "时段", "库位", "送货备注"
        };
        drawHeaderRow(g2d, heads, ws, y, hH);
        y += hH;

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        for (DispatchItem it : group.items) {
            int rh = calcRealRowHeight(it, ws);
            String[] row = {
                    it.seq, it.carNo, it.waybillNo, it.subWaybillNo,
                    it.receiver, it.receiverPhone, it.lastStation,
                    it.goodsName, it.qty, it.weight, it.volume,
                    it.packageType, it.deliveryType, it.returnType,
                    it.address, it.timeSlot, it.seat, it.remark
            };
            drawDataRow(g2d, row, ws, y, rh);
            y += rh;
        }

        drawTotalRow(g2d, group, ws, y, tH);
        y += tH;
        return y;
    }

    private static void drawHeaderRow(Graphics2D g2d, String[] hs, int[] ws, int y, int h) {
        g2d.setColor(COLOR_HEADER_BG);
        g2d.fillRect(30, y, IMAGE_WIDTH - 60, h);
        g2d.setColor(COLOR_BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TABLE));
        int x = 30;
        for (int i = 0; i < hs.length; i++) {
            g2d.drawRect(x, y, ws[i], h);
            center(g2d, hs[i], x, y, ws[i], h);
            x += ws[i];
        }
    }

    private static void drawDataRow(Graphics2D g2d, String[] row, int[] ws, int y, int h) {
        int x = 30;
        for (int i = 0; i < row.length; i++) {
            g2d.drawRect(x, y, ws[i], h);
            String txt = row[i];
            if (txt == null) txt = "";
            List<Integer> list= Arrays.asList(3,6,11,14,15,16,17);
            if (list.contains(i)) { // 地址/时段/库位/备注 左对齐换行
                wrapLeft(g2d, txt, x, y, ws[i], h);
            } else {
                center(g2d, txt, x, y, ws[i], h);
            }
            x += ws[i];
        }
    }

    private static void drawTotalRow(Graphics2D g2d, DispatchGroup g, int[] ws, int y, int h) {
        g2d.setColor(COLOR_TOTAL_BG);
        g2d.fillRect(30, y, IMAGE_WIDTH - 60, h);
        g2d.setColor(COLOR_BLACK);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TABLE));

        int x = 30;
        // 合并：序号~品名
        int wTotal = ws[0]+ws[1]+ws[2]+ws[3]+ws[4]+ws[5]+ws[6]+ws[7];
        g2d.drawRect(x, y, wTotal, h);
        center(g2d, "总计", x, y, wTotal, h);
        x += wTotal;

        // 件数
        g2d.drawRect(x, y, ws[8], h);
        center(g2d, String.valueOf(g.totalQty), x, y, ws[8], h);
        x += ws[8];
        // 重量
        g2d.drawRect(x, y, ws[9], h);
        center(g2d, String.valueOf(g.totalWeight), x, y, ws[9], h);
        x += ws[9];
        // 体积
        g2d.drawRect(x, y, ws[10], h);
        center(g2d, String.format("%.3f", g.totalVolume), x, y, ws[10], h);
        x += ws[10];

        // 合并：包装~送货备注
        int wFee = 0;
        for (int i = 11; i < ws.length; i++) wFee += ws[i];
        g2d.drawRect(x, y, wFee, h);
        center(g2d, "到付费用合计: " + g.totalFee, x, y, wFee, h);
    }

    // 工具：居中 / 左换行 / 右对齐
    private static void center(Graphics2D g, String s, int x, int y, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (w - fm.stringWidth(s)) / 2;
        int ty = y + h / 2 + fm.getAscent() / 2 - fm.getDescent() / 2;
        g.drawString(s, tx, ty);
    }

    private static void right(Graphics2D g, String s, int x, int w, int y, int h) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + w - fm.stringWidth(s) - CELL_PADDING;
        int ty = y + h / 2 + fm.getAscent() / 2 - fm.getDescent() / 2;
        g.drawString(s, tx, ty);
    }

    private static void wrapLeft(Graphics2D g, String str, int x, int y, int w, int h) {
        if (StringUtils.isBlank(str)) return;
        FontMetrics fm = g.getFontMetrics();
        int avail = w - 2 * CELL_PADDING;
        List<String> lines = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == '\n') {
                lines.add(cur.toString());
                cur.setLength(0);
                continue;
            }
            if (fm.stringWidth(cur.toString() + c) > avail) {
                lines.add(cur.toString());
                cur.setLength(0);
            }
            cur.append(c);
        }
        if (StringUtils.isNotBlank(lines.toString())) lines.add(cur.toString());
        int total = lines.size() * fm.getHeight();
        int cy = y + (h - total) / 2 + fm.getAscent();
        for (String line : lines) {
            g.drawString(line, x + CELL_PADDING, cy);
            cy += fm.getHeight();
        }
    }

    private static void drawHeader(Graphics2D g, String title, String date, int h) {
        g.setStroke(new BasicStroke(3));
        g.drawLine(20, 20, IMAGE_WIDTH - 20, 20);
        g.setStroke(new BasicStroke(1));
        g.setFont(new Font("微软雅黑", Font.BOLD, FONT_SIZE_TITLE));
        FontMetrics fm = g.getFontMetrics();
        int tx = (IMAGE_WIDTH - fm.stringWidth(title + "  " + date)) / 2;
        int ty = h / 2 + fm.getAscent() / 2;
        g.drawString(title, tx, ty);
        g.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_TABLE));
        g.setColor(COLOR_GRAY);
        g.drawString(date, tx + fm.stringWidth(title) + 10, ty);
        g.setColor(COLOR_BLACK);
    }

    private static void drawNote(Graphics2D g, int y, int h) {
        g.setFont(new Font("微软雅黑", Font.PLAIN, FONT_SIZE_NOTE));
        g.setColor(COLOR_GRAY);
        String s = "注：派车单承运人对上述代收款及货物安全负全部责任，签字确认生效！";
        FontMetrics fm = g.getFontMetrics();
        int tx = (IMAGE_WIDTH - fm.stringWidth(s)) / 2;
        int ty = y + h / 2 + fm.getAscent() / 2;
        g.drawString(s, tx, ty);
    }
}
