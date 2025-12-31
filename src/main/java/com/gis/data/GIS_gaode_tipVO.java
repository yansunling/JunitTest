package com.gis.data;

import lombok.Data;

/**
 * 高德输入提示信息
 */
@Data
public class GIS_gaode_tipVO {

    /**
     * 提示ID
     */
    private String id;

    /**
     * 提示名称
     */
    private String name;

    /**
     * 所属区域
     */
    private String district;

    /**
     * 区域编码
     */
    private String adcode;

    /**
     * 经纬度坐标，可能是字符串"lng,lat"或空数组[]
     */
    private Object location;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 类型编码
     */
    private String typecode;

    /**
     * 城市
     */
    private String city;

    /**
     * 经度（解析后）
     */
    private String lng;

    /**
     * 纬度（解析后）
     */
    private String lat;
}
