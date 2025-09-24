package com.word.dataSource.vo;

import com.other.annotation.MyNotEmpty;
import com.other.annotation.MyNotNull;
import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;

import com.word.constansts.ASSET_ALTER_TYPE;
import com.word.constansts.ASSET_STATUS;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "资产信息")
@TableName(value = "comp_asset_base_info", autoResultMap = true)
public class CompAssetBaseInfoPO extends CIPBasePO {



    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;


    @Version
    @CJ_column(name = "版本号")
    private Integer version;

    @TableUniqueKey
    @CJ_column(name = "资产编号")
    private String asset_num;


    @CJ_column(name = "原资产编号")
    private String original_asset_num="";


    @CJ_column(name = "资产状态")
    private String asset_status;


    @CJ_column(name = "车牌号")
    private String vehicle_law;

    @MyNotEmpty(message = "一级分类为空")
    @CJ_column(name = "一级分类")
    private String asset_subject;



    @MyNotEmpty(message = "二级分类为空")
    @CJ_column(name = "二级分类")
    private String asset_name;


    @CJ_column(name = "三级分类")
    private String third_class;


    @CJ_column(name = "资产名称")
    private String brand;


    @CJ_column(name = "型号规格")
    private String model;


    @CJ_column(name = "计量单位")
    private String measure_unit;

    @MyNotEmpty(message = "资产来源为空")
    @CJ_column(name = "资产来源")
    private String asset_source;


    @CJ_column(name = "供应商")
    private String supplier;


    @CJ_column(name = "联系供应商")
    private String contact_supplier;


    @CJ_column(name = "使用部门",code = "dept")
    private String use_org_id;

    @CJ_column(name = "使用人",code = "user")
    private String user_id;


    @CJ_column(name = "存放区域")
    private String storage_area;

    @MyNotEmpty(message = "所属公司为空")
    @CJ_column(name = "所属公司")
    private String asset_company;

    @MyNotEmpty(message = "所属部门为空")
    @CJ_column(name = "所属部门",code = "dept")
    private String asset_dept;

    @MyNotEmpty(message = "负责人为空")
    @CJ_column(name = "负责人",code = "user")
    private String resp_user_id;


    @CJ_column(name = "资产净值")
    private String asset_value;


    @CJ_column(name = "盘点时间")
    private Timestamp check_time;


    @CJ_column(name = "盘点状态")
    private String check_status;


    @CJ_column(name = "处置类型")
    private String disposal_type;


    @CJ_column(name = "备注")
    private String remark;


    @CJ_column(name = "附件id")
    private String file_id;


    @CJ_column(name = "对应规则流水号")
    private String rule_serial_no;


    @CJ_column(name = "删除标记")
    private String delete_flag;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改人")
    private String update_user_id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改时间")
    private Timestamp update_time;

    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String create_user_id;

    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;


    @CJ_column(name = "使用时间")
    private Timestamp use_time;

    @CJ_column(name = "图片地址")
    private String photo_url;

    @CJ_column(name = "操作来源")
    private String operate_source;

    @CJ_column(name = "操作类型")
    private String alter_type;


    //盘点完结时间
    private Timestamp stock_start_time;
    //盘点开始时间
    private Timestamp stock_complete_time;
    //盘点人
    private String stock_user_id;
    //盘点结果
    private String stocktaking_result;
    //异常类型
    private String except_type_name;
    //异常备注
    private String except_remark;
    //核销结果
    private String except_clear_name;
    //核销备注
    private String clear_remark;
    //异常处理人
    private String except_user_id;




    @Override
    public Object[] toKeys() {
        return new Object[]{file_id};
    }




}
