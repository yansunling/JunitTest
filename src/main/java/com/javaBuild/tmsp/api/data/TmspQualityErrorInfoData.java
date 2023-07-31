package com.javaBuild.tmsp.api.data;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.exttype.Volume;
import com.yd.common.exttype.Weight;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "品质差错申请参数")
public class TmspQualityErrorInfoData {

    @CJ_column(name = "差错上报流程编号")
    private String serial_no;
    @CJ_column(name = "责任大区")
    private String region_id;

    @CJ_column(name = "运单号")
    private String order_id;


    @CJ_column(name = "异常件数")
    private String exception_num;


    @CJ_column(name = "差错类型")
    private String error_type;


    @CJ_column(name = "差错小类")
    private String error_subtype;


    @CJ_column(name = "车皮号")
    private String car_no;


    @CJ_column(name = "实际重量")
    private String actual_weight;


    @CJ_column(name = "实际体积")
    private String actual_volume;

    @CJ_column(name = "开单重量")
    private String goods_weight;
    @CJ_column(name = "重量差值")
    private String weight_diff;
    @CJ_column(name = "开单体积")
    private String goods_cube;
    @CJ_column(name = "体积差值")
    private String cube_diff;


    @CJ_column(name = "事件描述")
    private String event_description;


    @CJ_column(name = "附件明细")
    private List<TMSP_share_filePO> file_list;







}
