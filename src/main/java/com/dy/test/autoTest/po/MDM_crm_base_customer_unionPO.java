package com.dy.test.autoTest.po;

import com.yd.common.data.CIPBasePO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MDM_crm_base_customer_unionPO extends CIPBasePO {


    /**
     *流水号
     */
    private String serial_no="";
    /**
     *联系人手机
     */
    private String link_tel="";
    /**
     *联系人座机
     */
    private String contact_landline="";
    /**
     *客户地址
     */
    private String address_details="";
    /**
     *交付方式
     */
    private String receive_type="";
    /**
     *结算方式
     */
    private String settle_type="";
    /**
     *产品类型
     */
    private String product_type="";
    /**
     *省编码
     */
    private Integer province_code=0 ;
    /**
     *市编码
     */
    private Integer city_code=0 ;
    /**
     *区编码
     */
    private Integer area_code=0 ;
    /**
     *省名称
     */
    private String prov_code_name="";
    /**
     *市名称
     */
    private String city_code_name="";
    /**
     *区名称
     */
    private String area_code_name="";
    /**
     *客户编码
     */
    private String customer_id="";
    /**
     *客户名称
     */
    private String customer_name="";
    /**
     *联系人
     */
    private String link_man="";
    /**
     * hr机构号
     */
    private String hr_org_id="";

    /**
     * 收派件标识
     */
    private String send_recv_flag;






    @Override
    public Object[] toKeys(){
        return new Object[]{
                serial_no
                  };
    }


}

