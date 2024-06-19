package com.javaBuild.tmsp.api.data;

import com.annotation.MyNotEmpty;
import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class TmspUnloadVO {

    @MyNotEmpty
    @CJ_column(name = "作业组流水号")
    private List<String> group_list;



    @CJ_column(name = "任务流水号")
    private String serial_no;


}
