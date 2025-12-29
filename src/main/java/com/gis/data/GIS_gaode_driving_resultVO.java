package com.gis.data;

import lombok.Data;

/**
* @author
* @since
* @Description 
*
*/
@Data
public class GIS_gaode_driving_resultVO {

	/**
	 * 结果状态值， 成功返回1
	 */
	private String status;

	/**
	 * 返回状态说明
	 */
	private String info;

	/**
	 * 路径规划方案总数
	 */
	private int count;

	/**
	 * 规划方案列表
	 */
	private GIS_gaode_driving_routeVO route;
}


