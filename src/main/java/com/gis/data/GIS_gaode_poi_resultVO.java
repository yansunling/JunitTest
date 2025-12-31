package com.gis.data;

import lombok.Data;
import java.util.List;

/**
 * 高德POI搜索返回结果
 */
@Data
public class GIS_gaode_poi_resultVO {

    /**
     * 返回结果状态值，1成功，0失败
     */
    private String status;

    /**
     * 返回状态说明
     */
    private String info;

    /**
     * 搜索方案数目
     */
    private Integer count;

    /**
     * POI信息列表
     */
    private List<GIS_gaode_poiVO> pois;
}
