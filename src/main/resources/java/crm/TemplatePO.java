package com.javaBuild.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@CJ_table(name = "{table_comment}")
@TableName(value = "{table_name}", autoResultMap = true)
public class {class_name} {

{content}



    public static Map<String, String> getExcelTitle() {
        Map<String, String> titleMap = new LinkedHashMap<>();
{excel_title}
        return titleMap;
    }

}
