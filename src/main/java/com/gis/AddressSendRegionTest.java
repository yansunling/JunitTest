package com.gis;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gis.data.GIS_gaode_geocodesVO;
import com.gis.data.GIS_gaode_poiVO;
import com.gis.data.GIS_gaode_tipVO;

public class AddressSendRegionTest {

    private static final String REGION_LOCATION_URL = "https://kp.tuolong56.com/gis/api/gis_region_region_info/location.do?actionId=gis_region_region_info_location";

    private static final String COOKIE = "Hm_lvt_6f6dcdf1cf6beff0de1b03f3e959c679=1762828829,1763597762; JSESSIONID=022E49BD90967AED5D93C493C9D76596; auth_sso_sessionid=2bde1ee7b81147b0b1ac7c1495c1476e; cip_sso_token=2bde1ee7b81147b0b1ac7c1495c1476e; jwt_sso_token=5huNjESrf8jSYrRv5KTe5h4fxKavk9sHFWlGzFf3q1tnalJrSmq49SNBmvFjxOjKgEQmq1SzVaK5ehpcCkdPpnDpcLS7Ua3xJR5xw1n4lpz5vTAvjZiI7evQb1THBeSoiP4YtKorqAvRM8IJYCHJrrnjSYWhGd01za3LDannog4eRutYuromiWtYUuMiXPuYTf5hqooVgpxe51hEXAThT8d2jxoRR5Nh2XyGVqiQ7hC81xo1CySJ2PjDKSiyUCL19mVsRUAZ8YqJZKONuSBW9R4kf3h6n1JwwSh6mv2wxTJgPlycS083guQvWRpHvVDpfFGV1rWYrJKXuVT1N7lypwJuntt; mpp_cip_sessionid=4f3dc452-d764-4352-857d-e7a6f44cff2f-1766980712598; net_org_id=350101050301; query_cip_sessionid=e168aa62-1e14-474a-b4b8-31cbae355884-1766988435878; auth_sessionid=20589b23864c4f6bb641ae465accc7dc; auth_cip_sessionid=77f20a78-c5ff-4cf5-bde5-2f1c090cab4e-1766990111006; orgid=";

    public static void main(String[] args) {

        String keywords = "新疆维吾尔自治区乌鲁木齐市天山区俊发商城2楼233号";
        String city = "乌鲁木齐";

        System.out.println("\n========== 输入提示方式（高德搜索框同款）==========");
//        GIS_gaode_tipVO tipData = GaoDeCoderUtil.getGeoCoderByInputtips(keywords, city);

        GIS_gaode_tipVO tipData=new GIS_gaode_tipVO();

        tipData.setLng("102.97658176482209");
        tipData.setLat("25.11371150322667");

        if (tipData != null) {
            System.out.println("提示名称: " + tipData.getName());
            System.out.println("提示地址: " + tipData.getAddress());
            System.out.println("所在区域: " + tipData.getDistrict());
            System.out.println("输入提示结果: " + tipData.getLng() + "," + tipData.getLat());

            // 使用获取的经纬度调用远程区域定位接口
            System.out.println("\n========== 远程区域定位接口 ==========");
            JSONObject regionResult = getRegionLocation(tipData.getLng(), tipData.getLat(), "4");
            if (regionResult != null) {
                System.out.println("region_id: " + regionResult.getStr("region_id"));
                System.out.println("region_name: " + regionResult.getStr("region_name"));
            }

           /* // 使用本地JSON数据匹配区域
            System.out.println("\n========== 本地区域匹配 ==========");
            RegionMatchUtil.loadRegionData("C:\\Users\\yansunling\\Desktop\\gis.gis_region_region_info.json");

            // 高德坐标（GCJ02）转GPS坐标（WGS84）
            double gcjLng = Double.parseDouble(tipData.getLng());
            double gcjLat = Double.parseDouble(tipData.getLat());
            double[] gpsCoord = CoordinateTransformUtil.gcj02ToWgs84(gcjLng, gcjLat);
            double lng = gpsCoord[0];
            double lat = gpsCoord[1];
            System.out.println("高德坐标(GCJ02): " + gcjLng + "," + gcjLat);
            System.out.println("GPS坐标(WGS84): " + lng + "," + lat);

            // 传入 busi_type="4" 与远程接口保持一致
            String[] regionInfo = RegionMatchUtil.getRegionInfo(lng, lat, "4");
            if (regionInfo != null) {
                System.out.println("region_id: " + regionInfo[0]);
                System.out.println("region_name: " + regionInfo[1]);
            } else {
                System.out.println("未匹配到片区");
            }

            // 调试：打印所有匹配的区域
            System.out.println("\n========== 调试：所有匹配区域 ==========");
            RegionMatchUtil.printAllMatchedRegions(lng, lat);

            // 调试：打印最近的10个区域（按距离排序）
            System.out.println("\n========== 调试：最近的区域（按距离排序） ==========");
            RegionMatchUtil.printNearestRegions(lng, lat, "4", 10);*/
        }

    }

    /**
     * 调用区域定位接口，获取区域信息
     * @param lng 经度
     * @param lat 纬度
     * @param busiType 业务类型
     * @return 区域信息（包含region_id和region_name），无结果返回null
     */
    public static JSONObject getRegionLocation(String lng, String lat, String busiType) {
        JSONObject param = new JSONObject();
        param.set("busi_type", busiType);
        param.set("lat", lat);
        param.set("lng", lng);

        String resultStr = HttpRequest.post(REGION_LOCATION_URL)
                .header("Cookie", COOKIE)
                .header("Content-Type", "application/json")
                .body(param.toString())
                .execute()
                .body();

        JSONObject result = JSONUtil.parseObj(resultStr);
        if (result.getInt("errorCode") == 0) {
            JSONArray dataArray = result.getJSONArray("data");
            if (dataArray != null && !dataArray.isEmpty()) {
                return dataArray.getJSONObject(0);
            }
        }
        System.out.println("区域定位失败: " + result.getStr("msg"));
        return null;
    }
}
