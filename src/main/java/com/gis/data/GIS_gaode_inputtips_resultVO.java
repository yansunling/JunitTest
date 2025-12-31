package com.gis.data;

import lombok.Data;
import java.util.List;

/**
 * 高德输入提示返回结果
 */
@Data
public class GIS_gaode_inputtips_resultVO {

    /**
     * 返回结果状态值，1成功，0失败
     */
    private String status;

    /**
     * 返回状态说明
     */
    private String info;

    /**
     * 提示信息列表
     */
    private List<GIS_gaode_tipVO> tips;
}
