package com.word.dataSource.vo;


import com.annotation.MyNotNull;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "资产异动参数对象")
public class CompAssetBaseInfoChangeVO {

    @CJ_column(name = "资产流水号集合")
    private List<String> serial_no_list;

    @CJ_column(name = "使用部门")
    private String use_org_id;

    @CJ_column(name = "使用人")
    private String user_id;

    @CJ_column(name = "存放区域")
    private String storage_area;

    @MyNotNull(message = "所属公司为空")
    @CJ_column(name = "所属公司")
    private String asset_company;

    @MyNotNull(message = "所属部门为空")
    @CJ_column(name = "所属部门")
    private String asset_dept;

    @MyNotNull(message = "负责人为空")
    @CJ_column(name = "负责人")
    private String resp_user_id;

}
