package com.javaBuild.po;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.yd.utils.common.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class {class_impl} implements {class_service} {

    @Autowired
    private {class_mapper} dataMapper;
    @Autowired
    private CRMX_share_data_logService logService;
    @Autowired
    private CrmxCommonService commonService;



    @Transactional
    public void addData({class_name} param) {

        //记录变更日志
        CRMX_share_data_logConfig config = CRMX_share_data_logConfig.build()
                .setOperateType(CRMX_share_data_logService.C_OPERATETYPE_ADD)
                .setBusi_table_name("{table_name}")
                .setBusi_doc_id(param.getSerial_no())
                .setLog_after_content(JSONObject.toJSONString(param))
                .setLog_content("");
        logService.addLog(config);

    }

    @Transactional
    public void updateData({class_name} param) {


        //获得变更前数据
        {class_name} before = dataMapper.selectById(param.getSerial_no());

        //记录变更日志
        CRMX_share_data_logConfig config = CRMX_share_data_logConfig.build();
        logService.addChangeLog(before,param,config);

    }

    @Transactional
    public void deleteData({class_name} param) {
        //获得变更前数据
        {class_name} before = dataMapper.selectById(param.getSerial_no());
        //删除审批人
        dataMapper.deleteById(param.getSerial_no());
        //记录变更日志
        CRMX_share_data_logConfig config = CRMX_share_data_logConfig.build()
                .setOperateType(CRMX_share_data_logService.C_OPERATETYPE_DELETE)
                .setBusi_table_name("{table_name}")
                .setBusi_doc_id(param.getSerial_no())
                .setLog_pre_content(JSONObject.toJSONString(before))
                .setLog_content("");
        logService.addLog(config);
    }
}
