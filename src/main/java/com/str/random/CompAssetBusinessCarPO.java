package com.str.random;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.other.annotation.MyNotEmpty;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@CJ_table(name = "商务车台账表")
@TableName(value = "comp_asset_business_car", autoResultMap = true)
public class CompAssetBusinessCarPO extends CIPBasePO {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;


    @TableUniqueKey
    @MyNotEmpty(message = "车牌号为空")
    @CJ_column(name = "车牌号")
    private String vehicle_law;




    @CJ_column(name = "归属单位")
    private String org_id;




    @CJ_column(name = "厂牌型号")
    private String brand_model;




    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @CJ_column(name = "保险到期日期")
    private Timestamp traffic_insurance_date;




    @CJ_column(name = "ETC")
    private String etc_card;




    @CJ_column(name = "车架号/车辆识别代码")
    private String vehicle_frame_no;




    @CJ_column(name = "发动机号")
    private String engine_no;




    @CJ_column(name = "车价")
    private String vehicle_price;




    @CJ_column(name = "驾驶人")
    private String driver_name;




    @CJ_column(name = "本部")
    private String headquarters;




    @CJ_column(name = "档案存放")
    private String file_storage;




    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @CJ_column(name = "登记日期")
    private Timestamp register_date;




    @CJ_column(name = "备注")
    private String remark;










    public static Map<String,String> getExcelTitle(){
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("vehicle_law","车牌号");
        titleMap.put("org_id","归属单位");
        titleMap.put("brand_model","厂牌型号");
        titleMap.put("traffic_insurance_date","保险到期日期");
        titleMap.put("etc_card","ETC");
        titleMap.put("vehicle_frame_no","车架号/车辆识别代码");
        titleMap.put("engine_no","发动机号");
        titleMap.put("vehicle_price","车价");
        titleMap.put("driver_name","驾驶人");
        titleMap.put("headquarters","本部");
        titleMap.put("file_storage","档案存放");
        titleMap.put("register_date","登记日期");
        titleMap.put("remark","备注");

        return titleMap;
    }

    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }

}
