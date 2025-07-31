package com.str.data;

import com.dy.components.annotations.CJ_column;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yd.tmsp.common.jackson.FormatTimeStampSerializer;
import com.yd.tmsp.common.jackson.NetOrgSerializer;
import com.yd.tmsp.constants.TRANS_WAY;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TMSP_claims_trans_InfoData {

    @CJ_column(name = "装车部门")
    @JsonSerialize(using = NetOrgSerializer.class)
    private String send_org_id;
    @CJ_column(name = "装车时间")
    @JsonSerialize(using = FormatTimeStampSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp load_date;
    @CJ_column(name = "到达部门")
    @JsonSerialize(using = NetOrgSerializer.class)
    private String arr_org_id;
    @CJ_column(name = "到达时间")
    @JsonSerialize(using = FormatTimeStampSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp arrive_time;
    @CJ_column(name = "干线运输方式")
    private TRANS_WAY trans_doc_type;




}
