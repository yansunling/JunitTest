package com.word.asset;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotNull;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "资产信息")
@TableName(value = "comp_asset_base_info", autoResultMap = true)
public class CompAssetBaseInfoPO extends CIPBasePO {



    @CJ_column(name = "流水号")
    private String serial_no;
    @MyNotNull(message = "资产编号为空")
    @CJ_column(name = "原资产编号")
    private String original_asset_num;

    @MyNotNull(message = "资产状态为空")
    @CJ_column(name = "资产状态")
    private String asset_status;


    @CJ_column(name = "车牌号")
    private String vehicle_law;

    @MyNotNull(message = "资产科目为空")
    @CJ_column(name = "资产科目")
    private String asset_subject;

    @MyNotNull(message = "明细分类为空")
    @CJ_column(name = "明细分类")
    private String asset_classify;

    @MyNotNull(message = "资产名称为空")
    @CJ_column(name = "资产名称")
    private String asset_name;


    @CJ_column(name = "品牌")
    private String brand;


    @CJ_column(name = "型号规格")
    private String model;


    @CJ_column(name = "计量单位")
    private String measure_unit;

    @MyNotNull(message = "资产来源为空")
    @CJ_column(name = "资产来源")
    private String asset_source;


    @CJ_column(name = "供应商")
    private String supplier;


    @CJ_column(name = "联系供应商")
    private String contact_supplier;


    @CJ_column(name = "使用部门")
    private String use_org_id;

    @CJ_column(name = "使用人")
    private String user_id;


    @CJ_column(name = "存放区域")
    private String storage_area;

    @MyNotNull(message = "所属公司为空")
    @CJ_column(name = "所属公司")
    private String asset_company;

    @MyNotNull(message = "所属部门为空")
    @CJ_column(name = "所属部门")
    private String asset_dept;

    @MyNotNull(message = "负责人为空")
    @CJ_column(name = "负责人")
    private String resp_user_id;


    @CJ_column(name = "资产净值")
    private String asset_value;




    @CJ_column(name = "备注")
    private String remark;


    @CJ_column(name = "附件id")
    private String file_id;





    @Override
    public Object[] toKeys() {
        return new Object[]{file_id};
    }




}
