package com.word.asset.vo;



import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.asset.interfaces.MyNotEmpty;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "手机上报审批")
public class CompAssetStocktakingAuditVO {

    @MyNotEmpty(message = "流水号为空")
    @CJ_column(name = "流水号")
    private List<String> serial_no_list;


    @CJ_column(name = "备注")
    private String remark;








}
