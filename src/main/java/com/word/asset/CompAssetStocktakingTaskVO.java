package com.word.asset;



import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotEmpty;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "盘点任务参数对象")
public class CompAssetStocktakingTaskVO {

    @MyNotEmpty(message = "任务编号为空")
    @CJ_column(name = "任务编号")
    private String task_num;


    @MyNotEmpty(message = "任务名称为空")
    @CJ_column(name = "任务名称")
    private String task_name;


    @MyNotEmpty(message = "盘点公司为空")
    @CJ_column(name = "盘点公司")
    private String asset_company;


    @MyNotEmpty(message = "盘点部门为空")
    @CJ_column(name = "盘点部门")
    private List<String> asset_dept_list;

    @MyNotEmpty(message = "任务说明为空")
    @CJ_column(name = "任务说明")
    private String remark;


}
