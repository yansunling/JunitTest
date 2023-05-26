package com.word.dataSource.data;


import com.baomidou.mybatisplus.annotation.*;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

@Data
@CJ_table(name = "资产申请记录表")
@TableName(value = "comp_asset_apply_record", autoResultMap = true)
public class CompAssetApplyRecordPO extends CIPBasePO {
    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;




    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }
}
