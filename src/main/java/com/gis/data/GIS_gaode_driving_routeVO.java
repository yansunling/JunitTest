package com.gis.data;

import lombok.Data;

import java.util.List;

/**
* @author
* @since
* @Description 
*
*/
@Data
public class GIS_gaode_driving_routeVO {

	/**
	 * 起点经纬度
	 */
	private String origin;

	/**
	 * 终点经纬度
	 */
	private String destination;

	/**
	 * 算路方案详情
	 */
	private List<GIS_gaode_driving_route_pathVO> paths;

	
}


