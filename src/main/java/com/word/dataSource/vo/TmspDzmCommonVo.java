package com.word.dataSource.vo;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class TmspDzmCommonVo {
    @CJ_column(name = "交接单号")
    private List<String> docIds;

    @CJ_column(name = "运单号")
    private List<String> orderIds;
    @CJ_column(name = "异常备注")
    private String abnormalCause;


}
