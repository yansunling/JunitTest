package com.javaBuild.tmsp.api.data;

import com.dy.components.annotations.CJ_column;
import com.other.annotation.MyNotEmpty;
import lombok.Data;

@Data
public class TmspUnloadTaskStockVO {
    @MyNotEmpty
    @CJ_column(name = "任务流水号")
    private String task_serial_no;


    @MyNotEmpty
    @CJ_column(name = "交接单号")
    private String doc_id;


    @MyNotEmpty
    @CJ_column(name = "运单号")
    private String order_id;

    @MyNotEmpty
    @CJ_column(name = "库位")
    private String stock_pos_id;



}
