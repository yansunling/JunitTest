package com.yd.tmsp.ownCar.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.exttype.Money;
import com.yd.tmsp.ownCar.validate.MyNotEmpty;
import com.yd.tmsp.ownCar.validate.MyPositiveMoney;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@CJ_table(name = "{table_comment}")
public class {class_name}  {

{content}


    public static Map<String,String> getExcelTitle(){
        Map<String, String> titleMap = new LinkedHashMap<>();
{title}
        return titleMap;

    }






}
