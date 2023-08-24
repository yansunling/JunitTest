package com.mock.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 上传照片的vo
 * @ClassName Market_customer_fileVO
 * @Author yanbin
 * @Date: 2021/5/14 17:02
 * @Version 1.0
 **/
@Data
public class Market_customer_fileVO implements Serializable {

    @CJ_column(name="文件名称")
    private String fileName = "" ;


    @CJ_column(name="文件序列号")
    private String fileSeqNo = "" ;

}
