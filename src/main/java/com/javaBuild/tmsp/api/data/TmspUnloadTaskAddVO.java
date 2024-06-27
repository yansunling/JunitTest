package com.javaBuild.tmsp.api.data;

import com.dy.components.annotations.CJ_column;
import com.other.annotation.MyNotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class TmspUnloadTaskAddVO {
    @MyNotEmpty
    @CJ_column(name = "业务流水号")
    private String busi_doc_id;
    @MyNotEmpty
    @CJ_column(name = "作业组流水号")
    private List<String> group_list;

    @MyNotEmpty
    @CJ_column(name = "类型")
    private String plan_type;

    @MyNotEmpty
    @CJ_column(name = "车牌号")
    private String vehicle_law_id;





}
