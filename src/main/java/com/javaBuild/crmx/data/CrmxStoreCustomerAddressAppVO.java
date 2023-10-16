package com.javaBuild.crmx.data;


import com.dy.components.annotations.CJ_column;
import com.yd.common.data.CIPBasePO;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CrmxStoreCustomerAddressAppVO extends CIPBasePO implements Serializable {

    private String serial_no = "" ;

    /** 
    * 客户编码
    */
    @NotNull
    private String customer_id = "" ;



    /** 
    * 客户名称
    */
    private String customer_name = "" ;



    /** 
    * 地址类型
    */
    private String address_type = "" ;



    /** 
    * 国家编码
    */
    private String country_code  ;



    /** 
    * 省编码
    */
    private String prov_code  ;
    private String prov_code_name;


    /** 
    * 市编码
    */
    private String city_code  ;
    private String city_code_name;


    /** 
    * 区编码
    */
    private String area_code  ;
    private String area_code_name;


    /** 
    * 区编码
    */
    private String bigarea_code = "" ;



    /** 
    * 片区编码
    */
    private int area_id = 0 ;



    /** 
    * 详细地址
    */
    private String address = "" ;



    /** 
    * 经度
    */
    private String longitude = "" ;



    /** 
    * 纬度
    */
    private String latitude = "" ;



    /** 
    * 地址标识
    */
    private String address_sign_type = "" ;



    /** 
    * 备注
    */
    private String remark = "" ;



    /** 
    * 删除标识
    */
    private String delete_flag = "0" ;

    /**
     *  专业市场模板id  默认0 非专业市场
     */
    private String market;

    private String market_name;

    private List<CRMX_market_address_columnVO> columnList;

    @CJ_column(name = "联系人名称")
    private String contact_name = "";

    @CJ_column(name = "联系人")
    private String contact_mobile = "";

    @CJ_column(name = "申请变更原因")
    private String apply_reason;

    @CJ_column(name = "是否审批")
    private String audit_flag;


    @CJ_column(name = "核实地址")
    private String examine_flag="0";

    @CJ_column(name = "送货片区")
    private String send_region_id;


    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }
}

