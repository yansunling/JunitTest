package com.yd.tmsp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.yd.tmsp.constants.LAST_CITY;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 业务配置相关的常量集合，方便与运维、实施对接个性化配置的维护工作以及代码可读性操作
 */
public class TMSPSettingConstantForBmsp {





    /**
     * 个性化的机构配置,因为不经常变化，目前代码中写死，以后有需要酌情考虑是否配置到数据库中
     */
    //TODO org_rebuild
    public enum SETTING_ORG{
        SETTING_ORG_3305000105_ORDER_CYCLE(
                "250108030301","湖州织里镇祥瑞物流园营业部",
                "order_cycle","机构开单周期,用于次元号流水号生成",
                "4","每天凌晨4天后开始"),
        SETTING_ORG_330782010201_TRANSFER_CITY_SORT(
                "250108010401","义乌江东区北下朱营业部",
                        "transfer_city_sort","中转城市靠前设置",
                        "532901,530300,530600,532601,533102,532532","曲靖市,昭通市,河口瑶族自治县,文山市,大理市,瑞丽市"),
        SETTING_ORG_33060002_SMS_SELF_PACK_FEE(
                "250108010204","店口联合体",
                "sms_self_pack_fee","店口自提短信提包费定制,TAPD ID 1000167",
                "250110010301,250110020601,250110010302","昆明方向定制:昆明金马村,昆明螺蛳湾,昆明王家营");
        private String orgId;
        private String orgName;
        private String settingKey;
        private String settingKeyName;
        private String settingValue;
        private String settingValueName;
        SETTING_ORG(String orgId,String orgName,String settingKey,String settingKeyName,String settingValue,String settingValueName){
            this.orgId = orgId;
            this.orgName = orgName;
            this.settingKey = settingKey;
            this.settingKeyName = settingKeyName;
            this.settingValue = settingValue;
            this.settingValueName = settingValueName;
        }
        public String orgId() {
            return this.orgId;
        }
        public String settingKey() {
            return this.settingKey;
        }
        public String settingKeyName() {
            return this.settingKeyName;
        }
        public String orgName() {
            return this.orgName;
        }
        public String settingValue() {
            return this.settingValue;
        }
        public String settingValueName() {
            return this.settingValueName;
        }

        //是否包含逗号隔开的值
        public boolean containValue(String value){
            return CollUtil.contains(StrUtil.split(this.settingValue, StrUtil.C_COMMA),value);
        }
    }

    /**
     * 全局配置,因为不经常变化，目前代码中写死，以后有需要酌情考虑是否配置到数据库中
     */
    public enum SETTING_GLOBAL{
        SETTING_GLOBAL_BIG_CUST_REMOVE_PAY_TYPE(
                "big_cust_remove_pay_type","大客户类型的机构开单时，移除现付",
                "2501130301,2501130302,2501130303,33078202","上海大客户项目组,江苏大客户项目组,浙北大客户项目组,浙南大客户项目组"),
        SETTING_GLOBAL_CREDIT_CUST(
                "credit_cust","不验证黑名单客户的机构集合",
                        "250108010204","店口联合体"),
        SETTING_GLOBAL_SEND_CAR_LOCK(
                "send_car_org","下单约车时，锁定派车部门",
                "250108030401","苏州常熟市虞山镇营业部");
        private String settingKey;
        private String settingKeyName;
        private String settingValue;
        private String settingValueName;
        SETTING_GLOBAL(String settingKey,String settingKeyName,String settingValue,String settingValueName){
            this.settingKey = settingKey;
            this.settingKeyName = settingKeyName;
            this.settingValue = settingValue;
            this.settingValueName = settingValueName;
        }
        public String settingKey() {
            return this.settingKey;
        }
        public String settingKeyName() {
            return this.settingKeyName;
        }
        public String settingValue() {
            return this.settingValue;
        }
        public String settingValueName() {
            return this.settingValueName;
        }

        //是否包含逗号隔开的值
        public boolean containValue(String value){
            return CollUtil.contains(StrUtil.split(this.settingValue, StrUtil.C_COMMA),value);
        }
    }





    /**
     * 东晋弘事业部
     */
    public final static HashMap<String,String> DJH_DEPT_MAP=new HashMap<String,String>(){
//        {
//            put("33078209","义乌青口点");//义乌青口点
//            put("33078210","义乌福田点");//义乌福田点
//            put("33078213","义乌西站点");//义乌西站点
//            put("33030008","温州苍平点");//温州苍平点
//            put("33030006","温州潘桥点");//温州潘桥点
//            put("33030007","温州柳市点");//温州柳市点
//            put("3303000501","温州西站点");//温州西站点
//            put("33030010","台州路桥点");//台州路桥点
//            put("33030011","台州牧屿点");//台州牧屿点
//            put("33030012","台州玉环点");//台州玉环点
//            put("33030013","台州天台点");//台州天台点
//            put("3101000701","上海南翔点");//上海南翔点
//            put("3101000702","上海北郊点");//上海北郊点
//            put("3101000703","上海闵行点");//上海闵行点
//            put("3206000401","江苏海门点");//江苏海门点
//            put("3206000402","江苏常州点");//江苏常州点
//            put("3206000403","江苏无锡点");//江苏无锡点
//            put("3206000404","江苏苏州点");//江苏苏州点
//            put("3206000405","江苏丹阳点");//江苏丹阳点
//            put("3304000906","嘉兴东点");//嘉兴东点
//        }
    };


    //目的城市昆明=530100 中转大理=532901 曲靖=530300 大理曲靖做派送
    public static Map<String, Map<String,String>> transferDirect = new HashMap<>();
    public static Map<String,String> transOrgMap = new HashMap<>();
    public static Map<String, Set<String>> orgTransferMap = new HashMap<>();//

    static {

        transOrgMap.put("532901","250110020401");//大理下关营业部
        transOrgMap.put("530300","250110020501");//曲靖麒麟营业部
        transOrgMap.put("53290101","250110020401");//大理下关营业部

        transferDirect.put(LAST_CITY.C_LAST_CITY_530100, transOrgMap);

        orgTransferMap.put("250110020401", Sets.newHashSet("532901", "53290101"));
    }


}
