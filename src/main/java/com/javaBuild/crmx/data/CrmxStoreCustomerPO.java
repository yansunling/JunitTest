package com.javaBuild.crmx.data;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "储备客户信息")
@TableName(value = "crm_store_customer", autoResultMap = true)
public class CrmxStoreCustomerPO {

    @TableId
    @CJ_column(name = "客户id")
    private String customer_id;











}
