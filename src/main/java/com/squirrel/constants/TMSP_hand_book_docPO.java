package com.squirrel.constants;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_field;
import com.dy.components.annotations.CJ_table;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import com.yd.common.exttype.Money;
import com.yd.common.exttype.Volume;
import com.yd.common.exttype.Weight;

import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_table(name = "接货短驳约车单")
@TableName(value = "tmsp_hand_book_doc", autoResultMap = true)
public class TMSP_hand_book_docPO extends Model<TMSP_hand_book_docPO> {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no= IdUtil.simpleUUID();


    @CJ_column(name = "订单号")
    private String pre_order_id="";


    @CJ_column(name = "约车单号")

    private String book_doc_id;


    @CJ_column(name = "短驳交接单号")
    private String doc_id;


    @CJ_column(name = "调度单号")
    private String trans_doc_id;




    @CJ_column(name = "派生根约车单号")
    private String root_doc_id;


    @CJ_column(name = "约车机构")
    private String book_org_id;


    @CJ_column(name = "约车时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp book_time;


    @CJ_column(name = "接货开始时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp send_bg_time;


    @CJ_column(name = "接货结束时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp send_ed_time;


    @CJ_column(name = "品名")
    private String goods_name;


    @CJ_column(name = "包装")
    private String pack_type;


    @CJ_column(name = "约车人")
    private String book_user_id;


    @CJ_column(name = "期望到达站点（接货目的地）")
    private String plan_reach_orgid;


    @CJ_column(name = "运单数",type = MysqlColumnTypeEnum.INT)
    private int order_count;


    @CJ_column(name = "件数",type = MysqlColumnTypeEnum.INT)
    private int goods_count;


    @CJ_column(name = "重量",type = MysqlColumnTypeEnum.INT)
    private Weight goods_weight;


    @CJ_column(name = "体积",type = MysqlColumnTypeEnum.DECIMAL)
    private Volume goods_cube;

    @CJ_column(name = "接货约车单分摊车价",type = MysqlColumnTypeEnum.BIGINT)
    private Money receive_split_car_price;


    @CJ_column(name = "退回原因")
    private String return_remark;


    @CJ_column(name = "退回时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp return_time=Timestamp.valueOf("1970-01-01 00:00:00");


    @CJ_column(name = "退回操作人")
    private String return_user_id;


    @CJ_column(name = "完结时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp close_time=Timestamp.valueOf("1970-01-01 00:00:00");


    @CJ_column(name = "完结操作人")
    private String close_user_id;


    @CJ_column(name = "完结操作机构")
    private String close_org_id;





    @CJ_column(name = "约车到达确认时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp arrive_time;


    @CJ_column(name = "约车到达确认人")
    private String arrive_user_id;


    @CJ_column(name = "约车到达确认机构")
    private String arrive_org_id;




    @Version
    @CJ_column(name = "版本号",type = MysqlColumnTypeEnum.INT)
    private int version;


    @CJ_field(name = "约车申请备注")
    private String book_remark;


    @CJ_column(name = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改人")
    private String update_user_id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp update_time;

    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String create_user_id;

    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp create_time;




    public Object[] toKeys(){
        return new Object[]{
                serial_no
                  };
    }

}

