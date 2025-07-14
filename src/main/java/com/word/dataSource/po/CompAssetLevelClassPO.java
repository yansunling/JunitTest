package com.word.dataSource.po;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.other.annotation.MyNotEmpty;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@CJ_table(name = "资产分类级别")
@TableName(value = "comp_asset_level_class", autoResultMap = true)
public class CompAssetLevelClassPO extends CIPBasePO {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;




    @Version
    @CJ_column(name = "版本号")
    private Integer version;



    @MyNotEmpty(message = "一级分类为空")
    @CJ_column(name = "一级分类")
    private String first_class;



    @MyNotEmpty(message = "二级分类为空")
    @CJ_column(name = "二级分类")
    private String second_class;



    @MyNotEmpty(message = "三级分类为空")
    @CJ_column(name = "三级分类")
    private String third_class;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改人")
    private String update_user_id;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "修改时间")
    private Timestamp update_time;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String create_user_id;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;


    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }

    public static Map<String,String>   TITLE_MAP=new LinkedHashMap<>();

    static {
        TITLE_MAP.put("first_class","一级分类");
        TITLE_MAP.put("second_class","二级分类");
        TITLE_MAP.put("third_class","三级分类");
    }


}
