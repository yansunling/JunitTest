package com.gis.data;

import lombok.Data;

/** 
* @author
* @since
* @Description 
*
*/
@Data
public class GIS_gaode_geocodesVO {

	/**
	 * 经纬度坐标(lng, lat)，逗号分隔
	 */
	private String location;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省份名
	 */
	private String province;
	/**
	 * 城市名
	 */
	private String city;
	/**
	 * 城市编码
	 */
	private String citycode;
	/**
	 * 区
	 */
	private String district;
	/**
	 * 街道
	 */
	private String street;
	/**
	 * 门牌
	 */
	private String number;
	/**
	 * 区域编码
	 */
	private String adcode;

	private String lng;

	private String lat;

}
