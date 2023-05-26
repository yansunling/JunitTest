package com.word.dataSource.vo;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class TMSP_claims_hand_docVO {

    @CJ_column(name = "交接单号")
    private String doc_id;

    @CJ_column(name = "交接单号明细")
    private List<TMSP_claims_hand_doc_detailPO> detail_list;
    @CJ_column(name = "盘点任务")
    private CompAssetStocktakingTaskVO taskVO;

}
