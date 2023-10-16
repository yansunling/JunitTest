package com.javaBuild.crmx.data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "客户联系人")
@TableName(value = "crm_store_key_man", autoResultMap = true)
public class CrmxStoreKeyManPO {

    @TableId
    @CJ_column(name = "联系人id")
    private String serial_no;




    @CJ_column(name = "所属客户id")
    private String customer_id;




    @CJ_column(name = "联系人名称")
    private String contact_name;




    @CJ_column(name = "性别",code = "contact_sex")
    private String contact_sex;




    @CJ_column(name = "行业")
    private String industry;




    @CJ_column(name = "联系人职务")
    private String contact_post;




    @CJ_column(name = "联系人手机")
    private String contact_mobile;




    @CJ_column(name = "联系人座机")
    private String contact_landline;




    @CJ_column(name = "联系人身份证号码")
    private String contactor_idcard;




    @CJ_column(name = "微信号")
    private String contact_wx;




    @CJ_column(name = "电子邮件")
    private String contact_mail;




    @CJ_column(name = "联系人QQ")
    private String contactor_qq;




    @CJ_column(name = "登记日期")
    private Timestamp register_date;




    @CJ_column(name = "联系人标签")
    private String contactor_label;




    @CJ_column(name = "联系人类型")
    private String contactor_type;




    @CJ_column(name = "是否主要联系人")
    private String contactor_flag;




    @CJ_column(name = "联系人籍贯省")
    private String cust_place_prov_code;




    @CJ_column(name = "联系人籍贯市")
    private String cust_place_city_code;




    @CJ_column(name = "联系人籍贯区")
    private String cust_place_area_code;




    @CJ_column(name = "备注")
    private String remark;










}
