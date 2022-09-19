package com.word.clazzType;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.asset.data.CompAssetApplyRecordPO;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "资产申请记录表")
@TableName(value = "comp_asset_apply_record", autoResultMap = true)
public class RequestTypeData extends CIPBasePO {
    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;
    @CJ_column(name = "记录")
    private List<CompAssetApplyRecordPO> recordPOList;

    @CJ_column(name = "类型")
    private CompAssetApplyRecordPO type;




    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }
}
