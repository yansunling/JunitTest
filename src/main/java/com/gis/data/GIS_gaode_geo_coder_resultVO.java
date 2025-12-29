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
public class GIS_gaode_geo_coder_resultVO {

	/**
	 * 结果状态值， 成功返回1
	 */
	private String status;

	/**
	 * 返回状态说明
	 */
	private String info;

	/**
	 * 返回结果数目
	 */
	private int count;
	
	/**
	 * 返回结果
	 */
	private List<GIS_gaode_geocodesVO> geocodes;


}


