package com.str.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TMSP_claims_oa_record_FileData {


    /**
     * 图片链接
     */
    private String file_id;

    /**
     * 名称
     */
    private String file_name;

    public TMSP_claims_oa_record_FileData() {
    }

    public TMSP_claims_oa_record_FileData(String file_id, String file_name) {
        this.file_id = file_id;
        this.file_name = file_name;
    }
}
