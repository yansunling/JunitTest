package com.word.dataSource.data;



import com.annotation.MyNotEmpty;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;

import lombok.Data;

@Data
@CJ_table(name = "资产申请参数")
public class CompAssetApplyRecordVO {

    @MyNotEmpty(message = "资产流水号为空")
    @CJ_column(name = "资产流水号")
    private String asset_serial_no;


    @MyNotEmpty(message = "存放区域为空")
    @CJ_column(name = "存放区域")
    private String storage_area;



    @MyNotEmpty(message = "任务名称为空")
    @CJ_column(name = "负责人为空")
    private String resp_user_id;


    @MyNotEmpty(message = "申请原因为空")
    @CJ_column(name = "申请原因为空")
    private String apply_reason;




}
