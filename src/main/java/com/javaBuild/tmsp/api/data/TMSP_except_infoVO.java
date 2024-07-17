package com.javaBuild.tmsp.api.data;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_structure;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
@CJ_structure(name = "异常信息")
public class TMSP_except_infoVO {


    @CJ_column(name = "异常ID")
    private String except_id;




    @CJ_column(name = "核销件数")
    private int clear_count;


    /**
     * 核销备注
     */
    @CJ_column(name = "核销备注")
    private String clear_remark;

    /**
     * 核销类型
     */
    @CJ_column(name = "核销类型")
    private String except_clear_type;

    /**
     * 附件流水号
     */
    @CJ_column(name = "附件")
    private List<TMSP_share_fileVO> files;

    @CJ_column(name = "fsm对应的appid")
    private String fsm_app_id;

}

