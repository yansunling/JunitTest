package com.gis.data;

import lombok.Data;

/**
 * 高德POI信息
 */
@Data
public class GIS_gaode_poiVO {

    /**
     * POI唯一标识
     */
    private String id;

    /**
     * POI名称
     */
    private String name;

    /**
     * POI类型
     */
    private String type;

    /**
     * 经纬度坐标(lng,lat)，逗号分隔
     */
    private String location;

    /**
     * 地址
     */
    private String address;

    /**
     * POI所在省份
     */
    private String pname;

    /**
     * POI所在城市
     */
    private String cityname;

    /**
     * POI所在区县
     */
    private String adname;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;
}
