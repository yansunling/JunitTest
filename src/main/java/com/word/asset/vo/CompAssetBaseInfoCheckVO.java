package com.word.asset.vo;


import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.asset.interfaces.MyNotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@CJ_table(name = "结果任务参数")
public class CompAssetBaseInfoCheckVO {

    @CJ_column(name = "所属机构集合")
    private List<String> org_list=new ArrayList<>();

    @MyNotNull(message = "任务名称为空")
    @CJ_column(name = "任务名称")
    private String task_name;

    @MyNotNull(message = "任务说明为空")
    @CJ_column(name = "任务说明")
    private String remark;



}
