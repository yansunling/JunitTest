package com.javaBuild.crmx.data;

import com.dy.components.annotations.CJ_column;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javaBuild.crmx.data.constants.CRMXConstant;
import com.javaBuild.crmx.data.constants.MyCacheSerializer;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author ：guochao
 * @date ：Created in 2021/5/13 14:56
 * @description：wildgc
 * @modified By：
 * @version: v1
 */
@Data
public class CrmxStoreCustomerAppVO {


    @CJ_column(name = "客户编码")
    private String customer_id;

    @CJ_column(name = "客户名称")
    private String customer_name;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "所属部门",code = CRMXConstant.DEPT_QUERY_CACHE)
    private String org_id;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "客户区分   收/发货客户",code = "send_recv_flag")
    private String send_recv_flag;

    @CJ_column(name = "助记码")
    private String short_code;

    @CJ_column(name = "企业全称")
    private String comp_name;

    @CJ_column(name = "企业座机")
    private String other_contact;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "省编码",code = CRMXConstant.PROV_QUERY_CACHE)
    private String prov_code;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "市编码",code = CRMXConstant.CITY_QUERY_CACHE)
    private String city_code;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "区县编码",code = CRMXConstant.AREA_QUERY_CACHE)
    private String area_code;

    @CJ_column(name = "地址")
    private String address;



    @CJ_column(name = "联系人姓名")
    private String contactor;

    @CJ_column(name = "手机")
    private String cust_mobile;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "客户分类",code = "cust_classify")
    private String cust_classify;


    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "发货决定权",code = "delivery_decision")
    private String delivery_decision;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "发票标记",code = "invoice_flag")
    private String invoice_flag;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "发票类型",code = "invoice_type")
    private String invoice_type;


    @CJ_column(name = "货物编码")
    private String goods_id;

    @CJ_column(name = "货物品名")
    private String goods_name;




    @CJ_column(name = "主要线路名称")
    private String main_lines_name;


    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "客户来源",code = "cust_source")
    private String cust_source;


    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "销售人员",code = CRMXConstant.USER_QUERY_CACHE)
    private String cust_salesman_id;

    @CJ_column(name = "名片")
    List<Market_customer_fileVO> businessCards;

    @CJ_column(name = "门头照片")
    List<Market_customer_fileVO> storefrontImgs;


    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "中转城市",code = CRMXConstant.TRANSFER_QUERY_CACHE)
    private String transfer_code;


    @CJ_column(name = "客户地址集合")
    private List<CrmxStoreCustomerAddressAppVO> address_list;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "产品类型",code = "product_type")
    private String product_type;

    @JsonSerialize(using = MyCacheSerializer.class)
    @CJ_column(name = "交货方式",code="deliver_way")
    private String deliver_way;

    @CJ_column(name = "联系人集合")
    private List<CrmxStoreKeyManPO> keyman_list;

    @CJ_column(name = "跨区开发")
    private String spanned_flag;

    @CJ_column(name = "项目销售")
    private String project_sale;


    @CJ_column(name = "失效时间")
    private Timestamp project_expire_time;


    @CJ_column(name = "客户地址集合")
    private List<CrmxStoreMainLinePO> main_lines;

    @CJ_column(name = "项目销售名称")
    private String project_sale_name;





}
