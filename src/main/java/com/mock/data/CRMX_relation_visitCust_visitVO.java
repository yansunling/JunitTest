package com.mock.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * @Description 新增和修改
 * @ClassName CRMX_relation_visitCust_visitVO
 * @Author yanbin
 * @Date: 2021/3/26 11:15
 * @Version 1.0
 **/
@Data
public class CRMX_relation_visitCust_visitVO {

    @CJ_column(name="流水号")
    private String serialNo = "" ;

    @CJ_column(name="客户经理id")
    private String customerSalesmanId = "" ;

    @CJ_column(name="客户编码")
    private String customerId = "" ;

    @CJ_column(name="客户状态")
    private String customerStatus = "" ;

    @CJ_column(name="客户类别")
    private String customerType = "" ;

    @CJ_column(name="是否送礼")
    private String giftFlag = "" ;


    @CJ_column(name="礼品序列号")
    private String giftInfo = "" ;

    @CJ_column(name="拜访时长")
    private Integer timeLength = 0 ;

    @CJ_column(name="客户名称")
    private String customerName = "" ;

    @CJ_column(name="潜在/合作客户  客户性质")
    private String customerNature = "" ;

    @CJ_column(name="潜在/合作客户  客户性质")
    private String visitCustomerNature = "" ;

    @CJ_column(name="拜访事项")
    private String visitMatter = "" ;

    @CJ_column(name="拜访执行人")
    private String visitJoiner = "" ;

    @CJ_column(name="隶属部门")
    private String visitOrgId = "" ;

    @CJ_column(name="拜访单位")
    private String visitUnit = "" ;

    @CJ_column(name="拜访对象")
    private String customerVisitJoiner = "" ;
    @CJ_column(name="拜访对象岗位")
    private String customerVisitPosition = "" ;
    @CJ_column(name="拜访对象联系方式")
    private String customerVisitNumber = "" ;

    @CJ_column(name="拜访时间")
    private String visitTime = "" ;
    @CJ_column(name="拜访方式")
    private String visitWay = "" ;
    @CJ_column(name="拜访地点")
    private String visitAddress = "" ;
    @CJ_column(name="预计花费")
    private Double expectSpend = 0.0 ;
    @CJ_column(name="实际花费")
    private Double realitySpend = 0.0 ;
    @CJ_column(name="拜访具体事项")
    private String specificMatter = "" ;
    @CJ_column(name="预期效果")
    private String anticipateResult = "" ;
    @CJ_column(name="拜访成效")
    private String visitResult = "" ;
    @CJ_column(name="下一步工作计划")
    private String nextPlan = "" ;
    @CJ_column(name="需要上级协助")
    private String helpFlag = "" ;

    @CJ_column(name="领导点评")
    private String supManagerReview = "" ;
    @CJ_column(name="客户需求")
    private String customerNeed = "" ;
    @CJ_column(name="内容")
    private String content = "" ;

    @CJ_column(name="拜访原因")
    private String visitReson = "" ;

    @CJ_column(name="客户反馈")
    private String customerBack = "" ;

    @CJ_column(name="备注")
    private String remark = "" ;

    @CJ_column(name="销售进度")
    private String salesSchedule = "" ;

    @CJ_column(name="是否初始值")
    private String isInit = "" ;

    @CJ_column(name="销售计划流水号")
    private String planSerialNo = "" ;

    @CJ_column(name="销售名称")
    private String userName;
    @CJ_column(name="门头照片")
    private List<Market_customer_fileVO> files;
    @CJ_column(name="经度")
    private Double longitude;
    @CJ_column(name="纬度")
    private Double latitude;
    @CJ_column(name = "流失原因编码")
    private String loss_reason="";
    @CJ_column(name = "流失原因")
    private String loss_reason_name="";
    @CJ_column(name = "先走货物流")
    private String cargo_logistics="";
    @CJ_column(name = "流失备注")
    private String loss_remark="";
    @CJ_column(name="营销进度")
    private String sale_progress;


}
