package com.javaBuild.tmsp.api.data;

import com.other.annotation.MyNotEmpty;
import com.dy.components.annotations.CJ_column;

import lombok.Data;

import java.util.List;

@Data
public class TmspUnloadTaskVO {
    @MyNotEmpty
    @CJ_column(name = "业务流水号")
    private String busi_doc_id;
    @MyNotEmpty
    @CJ_column(name = "作业组流水号")
    private List<String> group_list;




}
