package com.javaBuild.tmsp.api.data;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_field;
import com.dy.components.annotations.CJ_structure;
import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_structure(name = "公共附件表")
public class TMSP_share_fileVO {



    @CJ_column(name = "附件id")
    private String file_id;

    @CJ_column(name = "附件名称")
    private String file_name;





}

