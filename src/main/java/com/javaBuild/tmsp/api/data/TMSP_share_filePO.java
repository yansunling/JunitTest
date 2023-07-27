package com.javaBuild.tmsp.api.data;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_table(name = "公共附件表")
@TableName(value = "tmsp_share_file", autoResultMap = true)
public class TMSP_share_filePO {



    @CJ_column(name = "附件id")
    private String file_id;


    @CJ_column(name = "附件名称")
    private String file_name;





}

