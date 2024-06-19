package com.word.dataSource.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

@Data
@CJ_table(name = "联络单审批表")
@TableName(value = "tmsp_contact_form_approval", autoResultMap = true)
public class TmspContactFormApprovalPO extends Model<TmspContactFormApprovalPO> {





    @CJ_column(name = "联络单流水号")
    private String apply_serial_no;





    @CJ_column(name = "审批意见")
    private String operate_content;











}
