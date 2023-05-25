package com.javaBuild.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "{table_comment}")
@TableName(value = "{table_name}", autoResultMap = true)
public class {class_name} extends CIPBasePO {

{content}




    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }
}
