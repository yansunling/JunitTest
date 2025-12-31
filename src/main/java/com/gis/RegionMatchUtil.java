package com.gis;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 区域匹配工具类
 * 根据经纬度匹配所属片区（基于本地JSON数据）
 */
public class RegionMatchUtil {

    private static List<JSONObject> regionList = null;

    /**
     * 加载区域数据，按level降序排序（level越大优先匹配）
     * @param jsonFilePath JSON文件路径
     */
    public static void loadRegionData(String jsonFilePath) {
        String jsonStr = FileUtil.readUtf8String(jsonFilePath);
        // 预处理 MongoDB 导出格式，转换为标准 JSON
        jsonStr = convertMongoToJson(jsonStr);
        JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
        regionList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            regionList.add(jsonArray.getJSONObject(i));
        }
        // 按level降序排序，level越大优先匹配
        regionList.sort((a, b) -> {
            Integer levelA = a.getInt("level", 0);
            Integer levelB = b.getInt("level", 0);
            return levelB.compareTo(levelA);
        });
        System.out.println("加载区域数据完成，共 " + regionList.size() + " 个片区（已按level降序排序）");
    }

    /**
     * 将 MongoDB 导出格式转换为标准 JSON
     * 处理 NumberInt(), NumberLong(), ISODate(), ObjectId() 等
     */
    private static String convertMongoToJson(String mongoStr) {
        // NumberInt(123) -> 123
        mongoStr = mongoStr.replaceAll("NumberInt\\((\\d+)\\)", "$1");
        // NumberLong(123) -> 123
        mongoStr = mongoStr.replaceAll("NumberLong\\((\\d+)\\)", "$1");
        // ISODate("...") -> "..."
        mongoStr = mongoStr.replaceAll("ISODate\\(\"([^\"]+)\"\\)", "\"$1\"");
        // ObjectId("...") -> "..."
        mongoStr = mongoStr.replaceAll("ObjectId\\(\"([^\"]+)\"\\)", "\"$1\"");
        return mongoStr;
    }

    /**
     * 根据经纬度获取所属片区
     * @param lng 经度
     * @param lat 纬度
     * @return 匹配的区域信息，无匹配返回null
     */
    public static JSONObject getRegionByLocation(double lng, double lat) {
        return getRegionByLocation(lng, lat, null);
    }

    /**
     * 根据经纬度和业务类型获取所属片区
     * @param lng 经度
     * @param lat 纬度
     * @param busiType 业务类型（如 "4"），传null不过滤
     * @return 匹配的区域信息，无匹配返回null
     */
    public static JSONObject getRegionByLocation(double lng, double lat, String busiType) {
        if (regionList == null || regionList.isEmpty()) {
            System.out.println("请先调用 loadRegionData 加载区域数据");
            return null;
        }

        for (JSONObject region : regionList) {
            // 按业务类型过滤
            if (busiType != null) {
                String regionBusiType = region.getStr("busi_type_code");
                if (!busiType.equals(regionBusiType)) {
                    continue;
                }
            }

            JSONObject borderPoint = region.getJSONObject("border_point");
            if (borderPoint == null) {
                continue;
            }
            JSONArray coordinates = borderPoint.getJSONArray("coordinates");
            if (coordinates == null || coordinates.isEmpty()) {
                continue;
            }

            // GeoJSON多边形的coordinates是三维数组：[[[lng,lat],[lng,lat],...]]
            JSONArray polygon = coordinates.getJSONArray(0);
            if (isPointInPolygon(lng, lat, polygon)) {
                return region;
            }
        }
        return null;
    }

    /**
     * 判断点是否在多边形内（射线法，改进版）
     * @param lng 经度 (x)
     * @param lat 纬度 (y)
     * @param polygon 多边形坐标数组
     * @return true=在多边形内
     */
    private static boolean isPointInPolygon(double lng, double lat, JSONArray polygon) {
        if (polygon == null || polygon.size() < 3) {
            return false;
        }

        int n = polygon.size();
        int intersectCount = 0;

        for (int i = 0; i < n; i++) {
            JSONArray p1 = polygon.getJSONArray(i);
            JSONArray p2 = polygon.getJSONArray((i + 1) % n);

            double x1 = p1.getDouble(0);
            double y1 = p1.getDouble(1);
            double x2 = p2.getDouble(0);
            double y2 = p2.getDouble(1);

            // 检查点是否在边的纬度范围内
            if (lat > Math.min(y1, y2) && lat <= Math.max(y1, y2)) {
                // 计算射线与边的交点的经度
                if (y1 != y2) {
                    double xIntersect = (lat - y1) * (x2 - x1) / (y2 - y1) + x1;
                    // 如果交点在点的右边，计数+1
                    if (lng < xIntersect) {
                        intersectCount++;
                    }
                }
            }
        }

        // 奇数个交点表示在多边形内
        return intersectCount % 2 == 1;
    }

    /**
     * 根据经纬度获取片区ID和名称
     * @param lng 经度
     * @param lat 纬度
     * @return [region_id, region_name]，无匹配返回null
     */
    public static String[] getRegionInfo(double lng, double lat) {
        return getRegionInfo(lng, lat, null);
    }

    /**
     * 根据经纬度和业务类型获取片区ID和名称
     * @param lng 经度
     * @param lat 纬度
     * @param busiType 业务类型（如 "4"），传null不过滤
     * @return [region_id, region_name]，无匹配返回null
     */
    public static String[] getRegionInfo(double lng, double lat, String busiType) {
        JSONObject region = getRegionByLocation(lng, lat, busiType);
        if (region != null) {
            return new String[]{
                    region.getStr("_id"),
                    region.getStr("region_name")
            };
        }
        return null;
    }

    /**
     * 调试方法：获取所有匹配的区域（用于排查问题）
     * @param lng 经度
     * @param lat 纬度
     * @return 所有匹配的区域列表
     */
    public static List<JSONObject> getAllMatchedRegions(double lng, double lat) {
        List<JSONObject> matched = new ArrayList<>();
        if (regionList == null || regionList.isEmpty()) {
            return matched;
        }

        for (JSONObject region : regionList) {
            JSONObject borderPoint = region.getJSONObject("border_point");
            if (borderPoint == null) {
                continue;
            }
            JSONArray coordinates = borderPoint.getJSONArray("coordinates");
            if (coordinates == null || coordinates.isEmpty()) {
                continue;
            }

            JSONArray polygon = coordinates.getJSONArray(0);
            if (isPointInPolygon(lng, lat, polygon)) {
                matched.add(region);
            }
        }
        return matched;
    }

    /**
     * 调试方法：打印所有匹配的区域
     */
    public static void printAllMatchedRegions(double lng, double lat) {
        List<JSONObject> matched = getAllMatchedRegions(lng, lat);
        System.out.println("坐标 (" + lng + ", " + lat + ") 匹配到 " + matched.size() + " 个区域：");
        for (JSONObject region : matched) {
            System.out.println("  - " + region.getStr("_id") + " | " +
                    region.getStr("region_name") + " | level=" + region.getInt("level", 0));
        }
    }

    /**
     * 根据经纬度获取最近的片区（基于距离计算）
     * @param lng 经度
     * @param lat 纬度
     * @param busiType 业务类型
     * @return 最近的区域信息
     */
    public static JSONObject getNearestRegion(double lng, double lat, String busiType) {
        if (regionList == null || regionList.isEmpty()) {
            return null;
        }

        JSONObject nearestRegion = null;
        double minDistance = Double.MAX_VALUE;

        for (JSONObject region : regionList) {
            // 按业务类型过滤
            if (busiType != null) {
                String regionBusiType = region.getStr("busi_type_code");
                if (!busiType.equals(regionBusiType)) {
                    continue;
                }
            }

            JSONObject borderPoint = region.getJSONObject("border_point");
            if (borderPoint == null) {
                continue;
            }
            JSONArray coordinates = borderPoint.getJSONArray("coordinates");
            if (coordinates == null || coordinates.isEmpty()) {
                continue;
            }

            JSONArray polygon = coordinates.getJSONArray(0);

            // 先判断是否在多边形内
            if (isPointInPolygon(lng, lat, polygon)) {
                // 在多边形内，计算到边界的最小距离（用负数表示在内部）
                double dist = distanceToPolygon(lng, lat, polygon);
                // 在内部的优先，距离越小越优先
                if (dist < minDistance) {
                    minDistance = dist;
                    nearestRegion = region;
                }
            }
        }

        return nearestRegion;
    }

    /**
     * 计算点到多边形边界的最小距离
     */
    private static double distanceToPolygon(double lng, double lat, JSONArray polygon) {
        double minDist = Double.MAX_VALUE;
        int n = polygon.size();

        for (int i = 0; i < n; i++) {
            JSONArray p1 = polygon.getJSONArray(i);
            JSONArray p2 = polygon.getJSONArray((i + 1) % n);

            double x1 = p1.getDouble(0);
            double y1 = p1.getDouble(1);
            double x2 = p2.getDouble(0);
            double y2 = p2.getDouble(1);

            double dist = pointToSegmentDistance(lng, lat, x1, y1, x2, y2);
            if (dist < minDist) {
                minDist = dist;
            }
        }
        return minDist;
    }

    /**
     * 计算点到线段的距离
     */
    private static double pointToSegmentDistance(double px, double py, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return Math.sqrt((px - x1) * (px - x1) + (py - y1) * (py - y1));
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        double nearestX = x1 + t * dx;
        double nearestY = y1 + t * dy;

        return Math.sqrt((px - nearestX) * (px - nearestX) + (py - nearestY) * (py - nearestY));
    }

    /**
     * 调试：打印最近的区域（在多边形内的，按距离边界排序）
     */
    public static void printNearestRegions(double lng, double lat, String busiType, int topN) {
        if (regionList == null || regionList.isEmpty()) {
            return;
        }

        List<Object[]> results = new ArrayList<>();

        for (JSONObject region : regionList) {
            if (busiType != null) {
                String regionBusiType = region.getStr("busi_type_code");
                if (!busiType.equals(regionBusiType)) {
                    continue;
                }
            }

            JSONObject borderPoint = region.getJSONObject("border_point");
            if (borderPoint == null) continue;
            JSONArray coordinates = borderPoint.getJSONArray("coordinates");
            if (coordinates == null || coordinates.isEmpty()) continue;

            JSONArray polygon = coordinates.getJSONArray(0);
            boolean inside = isPointInPolygon(lng, lat, polygon);
            double dist = distanceToPolygon(lng, lat, polygon);

            results.add(new Object[]{region, inside, dist});
        }

        // 按距离排序
        results.sort((a, b) -> Double.compare((Double) a[2], (Double) b[2]));

        System.out.println("坐标 (" + lng + ", " + lat + ") 最近的 " + topN + " 个区域：");
        for (int i = 0; i < Math.min(topN, results.size()); i++) {
            Object[] r = results.get(i);
            JSONObject region = (JSONObject) r[0];
            boolean inside = (Boolean) r[1];
            double dist = (Double) r[2];
            System.out.println("  " + (i + 1) + ". " + region.getStr("_id") + " | " +
                    region.getStr("region_name") + " | level=" + region.getInt("level", 0) +
                    " | 距离=" + String.format("%.6f", dist) + " | " + (inside ? "在内部" : "在外部"));
        }
    }
}
