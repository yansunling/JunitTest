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
@CJ_table(name = "客户拜访信息")
@TableName(value = "crm_store_cust_visiting", autoResultMap = true)
public class CrmxStoreCustVisitingPO {

    @TableId
    @CJ_column(name = "拜访流水号")
    private String serial_no;




    @CJ_column(name = "销售人员id")
    private String cust_salesman_id;




    @CJ_column(name = "客户id")
    private String customer_id;




    @CJ_column(name = "客户状态")
    private String cust_status;




    @CJ_column(name = "客户类型")
    private String cust_type;




    @CJ_column(name = "访问开始日期")
    private Timestamp visit_date;




    @CJ_column(name = "是否送礼")
    private String gift_flag;




    @CJ_column(name = "礼品信息")
    private String gift_info;




    @CJ_column(name = "拜访时长")
    private Integer time_length;




    @CJ_column(name = "客户需求")
    private String cust_need;




    @CJ_column(name = "拜访事项")
    private String visit_matter;




    @CJ_column(name = "销售员建议")
    private String visit_result;




    @CJ_column(name = "下一步计划")
    private String next_plan;




    @CJ_column(name = "拜访参与人")
    private String visit_joiner;




    @CJ_column(name = "拜访执行人工号")
    private String visit_joiner_id;




    @CJ_column(name = "参与人联系方式")
    private String joiner_phone_number;




    @CJ_column(name = "客户参与人")
    private String cust_visit_joiner;




    @CJ_column(name = "统计月")
    private Integer statistic_month;




    @CJ_column(name = "统计年")
    private Integer statistic_year;




    @CJ_column(name = "执行状态")
    private String exec_status;




    @CJ_column(name = "是否需要上级协助")
    private String help_flag;




    @CJ_column(name = "协助内容")
    private String help_content;




    @CJ_column(name = "领导点评")
    private String sup_manager_review;




    @CJ_column(name = "拜访方式")
    private String visit_way;




    @CJ_column(name = "内容")
    private String content;




    @CJ_column(name = "拜访原因")
    private String visit_reson;




    @CJ_column(name = "客户反馈")
    private String cust_back;




    @CJ_column(name = "拜访备注")
    private String remark;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作人")
    private String op_user_id;




    @CJ_column(name = "操作人名称")
    private String op_user_name;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作时间")
    private Timestamp update_time;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String creator;




    @CJ_column(name = "创建人名称")
    private String creator_name;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;





    @CJ_column(name = "销售进度")
    private String sales_schedule;




    @CJ_column(name = "是否初始值")
    private String is_init;




    @CJ_column(name = "销售计划流水号")
    private String plan_serial_no;




    @CJ_column(name = "客户性质")
    private String visit_cust_nature;




    @CJ_column(name = "拜访单位")
    private String visit_unit;




    @CJ_column(name = "拜访时间")
    private String visit_time;




    @CJ_column(name = "拜访地址")
    private String visit_address;




    @CJ_column(name = "预计花费")
    private Double expect_spend;




    @CJ_column(name = "实际花费")
    private Double reality_spend;




    @CJ_column(name = "拜访具体事项")
    private String specific_matter;




    @CJ_column(name = "拜访具体事项")
    private String anticipate_result;




    @CJ_column(name = "隶属部门")
    private String visit_org_id;




    @CJ_column(name = "拜访对象岗位")
    private String cust_visit_position;




    @CJ_column(name = "拜访对象联系方式")
    private String cust_visit_number;




    @CJ_column(name = "查看标识")
    private String see_flag;




    @CJ_column(name = "计划编码")
    private String plan_id;




    @CJ_column(name = "创建拜访计划来源 app or pc")
    private String source_flag;




    @CJ_column(name = "经度")
    private Double longitude;




    @CJ_column(name = "纬度")
    private Double latitude;




    @CJ_column(name = "是否已完善客户")
    private String perfect_cust_flag;




    @CJ_column(name = "流失原因编码")
    private String loss_reason;




    @CJ_column(name = "流失原因")
    private String loss_reason_name;




    @CJ_column(name = "现走货物流")
    private String cargo_logistics;




    @CJ_column(name = "流失原因")
    private String loss_remark;




    @CJ_column(name = "营销进度")
    private String sale_progress;




    @CJ_column(name = "客户需求点")
    private String cust_need_point;




    @CJ_column(name = "理赔是否未赔付")
    private String claim_flag;




    @CJ_column(name = "微信拜访附件")
    private String wechat_file;

    @CJ_column(name = "门头照")
    private String file_seq_no;








}
