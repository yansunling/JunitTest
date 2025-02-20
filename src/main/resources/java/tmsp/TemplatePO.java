package com.javaBuild.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yd.common.exttype.Money;
import com.yd.tmsp.constants.IS_NOT;
import lombok.Data;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@CJ_table(name = "{table_comment}")
@TableName(value = "{table_name}", autoResultMap = true)
public class {class_name} extends Model<{class_name}> {

{content}


    public static Map<String,String> getExcelTitle(){
        Map<String, String> titleMap = new LinkedHashMap<>();
{title}
        return titleMap;

    }
}
