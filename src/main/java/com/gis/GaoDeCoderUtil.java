package com.gis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gis.data.GIS_gaode_driving_resultVO;
import com.gis.data.GIS_gaode_geo_coder_resultVO;
import com.gis.data.GIS_gaode_geocodesVO;
import com.yd.tmsp.common.gis.GeoCoderUtil;
import com.yd.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author fukaijian
 * @since 2018年8月9日
 * @Description 调用高德的接口解析地址
 *
 */
@Component
public class GaoDeCoderUtil {

    static Logger log = LoggerFactory.getLogger(GeoCoderUtil.class);

    private static String gaodeKey = "acc989b97e9618c93909ff24b9a5a7fc";

    //高德地理编码接口
    private static String gaodeGeoUrl = "https://restapi.amap.com/v3/geocode/geo?key={0}&address={1}";

    //高德驾车路线规划接口
    private static String gaodeDrivingUrl = "https://restapi.amap.com/v5/direction/driving?key={0}&origin={1}&destination={2}";

/*    @Value("#{properties['gaode.key']}")
    public void setBaiduKey(String gaodeKey) {
        GeoCoderUtil.gaodeKey = gaodeKey;
    }*/


    /**
     * 调用驾车路线规划api接口获取驾车距离
     * @param startLngLat 起点经纬度，逗号分隔
     * @param endLngLat 终点经纬度，逗号分隔
     * @return 距离，单位米
     */
    public static Double getDrivingDistance(String startLngLat, String endLngLat){
        //经纬度为空不计算
        if(StringUtils.equals(startLngLat,"null,null")|| StringUtils.equals(endLngLat,"null,null")){
            return null;
        }

        GIS_gaode_driving_resultVO resultVO = getDrivingRoute(startLngLat, endLngLat);
        if("0".equals(resultVO.getStatus())){
            log.error("调用路线规划接口失败，{}", resultVO.getInfo());
            return null;
        }
        if(resultVO.getCount() <= 0){
            log.error("路线规划没有结果，{}->{}", startLngLat, endLngLat);
            return null;
        }
        String distance = resultVO.getRoute().getPaths().get(0).getDistance();
        return Double.valueOf(distance);
    }

    /**
     * 调用地理编码api接口获取地址结果
     * @param address
     * @return
     */
    public static Pair<String, String> getLnglat(String address){
        GIS_gaode_geo_coder_resultVO resultVO = getGeoCoder(address);
        if("0".equals(resultVO.getStatus())){
            log.error("地址【{}】解析失败：{}", address, resultVO.getInfo());
            return Pair.of("", "");
        }
        if(resultVO.getCount() <= 0){
            log.error("地址【{}】解析没有结果", address);
            return Pair.of("", "");
        }
        String lnglat = resultVO.getGeocodes().get(0).getLocation();
        String[] lnglatSplit = lnglat.split(",");
        return Pair.of(lnglatSplit[0], lnglatSplit[1]);
    }

    /**
     * 调用地理编码api接口获取地址结果
     * @param address
     * @return
     */
    private static GIS_gaode_geo_coder_resultVO getGeoCoder(String address){
        MessageFormat format = new MessageFormat(gaodeGeoUrl);
        String url = format.format(new String[]{gaodeKey, address});
        String resultStr = HttpUtil.get(url);
        log.info("高德地理编码解析返回：{}", resultStr);
        GIS_gaode_geo_coder_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_geo_coder_resultVO.class);
        return result;
    }

    public static GIS_gaode_geocodesVO getGeoCoderData(String address){
        MessageFormat format = new MessageFormat(gaodeGeoUrl);
        String url = format.format(new String[]{gaodeKey, address});
        String resultStr = HttpUtil.get(url);
        log.info("高德地理编码解析返回：{}", resultStr);
        GIS_gaode_geo_coder_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_geo_coder_resultVO.class);
        if(result!=null&& CollectionUtil.isNotEmpty(result.getGeocodes())){
            GIS_gaode_geocodesVO geocodesVO = result.getGeocodes().get(0);
            String location = geocodesVO.getLocation();
            String[] split = location.split(",");
            geocodesVO.setLng(split[0]);
            geocodesVO.setLat(split[1]);
            return geocodesVO;

        }


        return null;
    }


    /**
     * 调用驾车路线规划api接口获取驾车路线规划结果
     * 当前使用默认的算路策略（高德推荐，同高德地图APP默认），不一定是距离最短
     * @param startLngLat 起点经纬度，逗号分隔
     * @param endLngLat 终点经纬度，逗号分隔
     * @return
     */
    private static GIS_gaode_driving_resultVO getDrivingRoute(String startLngLat, String endLngLat){
        MessageFormat format = new MessageFormat(gaodeDrivingUrl);
        String url = format.format(new String[]{gaodeKey, startLngLat, endLngLat});
        log.info("高德驾车路线规划返回URL：{}", url);

        String resultStr = HttpUtil.get(url);
        log.info("高德驾车路线规划返回：{}", resultStr);
        GIS_gaode_driving_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_driving_resultVO.class);
        return result;
    }
}
