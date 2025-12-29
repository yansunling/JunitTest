package com.gis.data;

import lombok.Data;

/**
* @author
* @since
* @Description 
*
*/
@Data
public class GIS_gaode_driving_route_pathVO {

	/**
	 * 方案距离，单位：米
	 */
	private String distance;

	/**
	 * 预计行驶时间	单位：秒
	 */
	private String duration;

	/**
	 * 导航策略
	 */
	private String strategy;

	/**
	 * 0 代表限行已规避或未限行，即该路线没有限行路段
	 * 1 代表限行无法规避，即该线路有限行路段
	 */
	private String restriction;

	
}


