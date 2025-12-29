package com.gis;

import com.gis.data.GIS_gaode_geocodesVO;

public class AddressSendRegionTest {
    public static void main(String[] args) {
        GIS_gaode_geocodesVO geoCoderData = GaoDeCoderUtil.getGeoCoderData("俊发服装商城");

        System.out.println(geoCoderData.getLng()+","+geoCoderData.getLat());

    }

}
