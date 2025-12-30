package com.gis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gis.data.GIS_gaode_driving_resultVO;
import com.gis.data.GIS_gaode_geo_coder_resultVO;
import com.gis.data.GIS_gaode_geocodesVO;
import com.gis.data.GIS_gaode_poi_resultVO;
import com.gis.data.GIS_gaode_poiVO;
import com.gis.data.GIS_gaode_inputtips_resultVO;
import com.gis.data.GIS_gaode_tipVO;
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

    //高德POI关键字搜索接口（citylimit=true 强制限定城市范围）
    private static String gaodePoiUrl = "https://restapi.amap.com/v3/place/text?key={0}&keywords={1}&city={2}&citylimit=true";

    //高德输入提示接口（与高德地图搜索框同款，精度更高）
    private static String gaodeInputtipsUrl = "https://restapi.amap.com/v3/assistant/inputtips?key={0}&keywords={1}&city={2}&citylimit=true";

    //高德地理编码接口（带城市参数）
    private static String gaodeGeoUrlWithCity = "https://restapi.amap.com/v3/geocode/geo?key={0}&address={1}&city={2}";

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
     * 通过POI关键字搜索获取精确经纬度
     * 适用于搜索具体地点如商场、市场、写字楼等
     * @param keywords 关键词（如：新华服装市场）
     * @param city 城市名称（如：乌鲁木齐）
     * @return POI信息，包含经纬度
     */
    public static GIS_gaode_poiVO getGeoCoderByPoi(String keywords, String city) {
        MessageFormat format = new MessageFormat(gaodePoiUrl);
        String url = format.format(new String[]{gaodeKey, keywords, city});
        String resultStr = HttpUtil.get(url);
        log.info("高德POI搜索返回：{}", resultStr);
        GIS_gaode_poi_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_poi_resultVO.class);
        if (result != null && "1".equals(result.getStatus()) && CollectionUtil.isNotEmpty(result.getPois())) {
            // 遍历结果，找到城市匹配的POI
            for (GIS_gaode_poiVO poiVO : result.getPois()) {
                String poiCity = poiVO.getCityname();
                // 校验城市是否匹配
                if (StringUtils.isNotEmpty(poiCity) && poiCity.contains(city)) {
                    String location = poiVO.getLocation();
                    if (StringUtils.isNotEmpty(location)) {
                        String[] split = location.split(",");
                        poiVO.setLng(split[0]);
                        poiVO.setLat(split[1]);
                    }
                    return poiVO;
                }
            }
            log.error("POI搜索【{}】在城市【{}】没有匹配的结果", keywords, city);
        }
        log.error("POI搜索【{}】在城市【{}】没有结果", keywords, city);
        return null;
    }

    /**
     * 通过输入提示接口搜索（与高德地图搜索框同款，精度最高）
     * @param keywords 关键词
     * @param city 城市名称
     * @return 提示信息，包含经纬度
     */
    public static GIS_gaode_tipVO getGeoCoderByInputtips(String keywords, String city) {
        MessageFormat format = new MessageFormat(gaodeInputtipsUrl);
        String url = format.format(new String[]{gaodeKey, keywords, city});
        String resultStr = HttpUtil.get(url);
        log.info("高德输入提示返回：{}", resultStr);
        GIS_gaode_inputtips_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_inputtips_resultVO.class);
        if (result != null && "1".equals(result.getStatus()) && CollectionUtil.isNotEmpty(result.getTips())) {
            for (GIS_gaode_tipVO tipVO : result.getTips()) {
                // location可能是字符串"lng,lat"或空数组[]，需要判断
                Object location = tipVO.getLocation();
                if (location != null && location instanceof String) {
                    String locationStr = (String) location;
                    if (StringUtils.isNotEmpty(locationStr) && locationStr.contains(",")) {
                        String[] split = locationStr.split(",");
                        tipVO.setLng(split[0]);
                        tipVO.setLat(split[1]);
                        return tipVO;
                    }
                }
            }
        }
        log.error("输入提示搜索【{}】在城市【{}】没有结果", keywords, city);
        return null;
    }

    /**
     * 通过地理编码接口获取经纬度（带城市参数，精度更高）
     * @param address 地址
     * @param city 城市名称
     * @return 地理编码结果
     */
    public static GIS_gaode_geocodesVO getGeoCoderDataWithCity(String address, String city) {
        MessageFormat format = new MessageFormat(gaodeGeoUrlWithCity);
        String url = format.format(new String[]{gaodeKey, address, city});
        String resultStr = HttpUtil.get(url);
        log.info("高德地理编码（带城市）解析返回：{}", resultStr);
        GIS_gaode_geo_coder_resultVO result = JSONUtil.toBean(resultStr, GIS_gaode_geo_coder_resultVO.class);
        if (result != null && CollectionUtil.isNotEmpty(result.getGeocodes())) {
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
     * 智能获取地址经纬度（推荐使用）
     * 搜索顺序：输入提示 -> POI搜索 -> 地理编码（带城市）
     * @param keywords 关键词（如：小西门布料）
     * @param city 城市名称
     * @return 经纬度信息
     */
    public static GIS_gaode_geocodesVO getGeoCoderSmart(String keywords, String city) {
        // 1. 先尝试输入提示（精度最高）
        GIS_gaode_tipVO tipVO = getGeoCoderByInputtips(keywords, city);
        if (tipVO != null && StringUtils.isNotEmpty(tipVO.getLng())) {
            log.info("输入提示搜索成功：{}", tipVO.getName());
            GIS_gaode_geocodesVO result = new GIS_gaode_geocodesVO();
            result.setLng(tipVO.getLng());
            result.setLat(tipVO.getLat());
            result.setLocation(tipVO.getLng() + "," + tipVO.getLat());
            result.setDistrict(tipVO.getDistrict());
            return result;
        }

        // 2. 再尝试POI搜索
        GIS_gaode_poiVO poiVO = getGeoCoderByPoi(keywords, city);
        if (poiVO != null && StringUtils.isNotEmpty(poiVO.getLng())) {
            log.info("POI搜索成功：{}", poiVO.getName());
            GIS_gaode_geocodesVO result = new GIS_gaode_geocodesVO();
            result.setLng(poiVO.getLng());
            result.setLat(poiVO.getLat());
            result.setLocation(poiVO.getLocation());
            result.setCity(poiVO.getCityname());
            result.setDistrict(poiVO.getAdname());
            result.setProvince(poiVO.getPname());
            return result;
        }

        // 3. 最后使用地理编码（带城市参数）
        log.info("使用地理编码解析：{}", keywords);
        return getGeoCoderDataWithCity(keywords, city);
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
