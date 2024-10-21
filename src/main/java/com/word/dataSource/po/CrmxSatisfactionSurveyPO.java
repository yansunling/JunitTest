package com.word.dataSource.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

@Data
@CJ_table(name = "满意度调查表")
@TableName(value = "crm_satisfaction_survey", autoResultMap = true)
public class CrmxSatisfactionSurveyPO {




    @CJ_column(name = "运单号")
    private String order_id;




    @CJ_column(name = "手机号")
    private String phone_no;




    @CJ_column(name = "客户编码")
    private String customer_id;




    @CJ_column(name = "使用物流频率")
    private String use_logistics_frequency;




    @CJ_column(name = "选择驮龙物流的原因")
    private String reason_tuolong;




    @CJ_column(name = "驮龙物流信息查询感受")
    private String query_experience;




    @CJ_column(name = "运输时效满意程度")
    private String timeliness_satisfaction;




    @CJ_column(name = "时效是否准时")
    private String is_on_time;




    @CJ_column(name = "到件是否通知")
    private String notification_arrival;




    @CJ_column(name = "到件是否通知建议")
    private String notification_arrival_suggestion;




    @CJ_column(name = "异常问题及出现频率")
    private String exception_frequency;




    @CJ_column(name = "异常问题及出现频率建议")
    private String exception_frequency_suggestion;




    @CJ_column(name = "问题/投诉处理满意程度")
    private String problem_satisfaction;




    @CJ_column(name = "问题/投诉处理满意程度建议")
    private String problem_satisfaction_suggestion;




    @CJ_column(name = "不满意方面")
    private String dissatisfaction;




    @CJ_column(name = "总体满意度")
    private String overall_satisfaction;




    @CJ_column(name = "总体满意度建议")
    private String overall_satisfaction_suggestions;




    @CJ_column(name = "其他方面建议")
    private String other_suggestions;












}
