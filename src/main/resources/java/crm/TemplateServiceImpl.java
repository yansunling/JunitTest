package com.javaBuild.po;


import com.alibaba.fastjson.JSONObject;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.error.auto.GlobalErrorInfoEnum;
import com.local.crmx.customer.vo.CRMX_base_customer_market_importData;
import com.yd.common.runtime.CIPRuntimeOperator;
import com.yd.crmx.common.service.CrmxCommonService;
import com.yd.crmx.share.data.CRMX_share_data_logConfig;
import com.yd.crmx.share.service.CRMX_share_data_logService;
import com.yd.crmx.util.CJExcelUtil;
import com.yd.crmx.validate.MyValidationUtil;
import com.yd.utils.common.ExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        //更新数据
        dataMapper.insert(param);
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
        //获得变更后数据
        {class_name} after = new {class_name}();
        BeanUtils.copyProperties(before,after);
        //更新复制后数据
        BeanUtils.copyProperties(param, after, CrmxCommonUtil.getNullPropertyNames(param));
        //更新数据
        dataMapper.updateById(after);
        //记录变更日志
        CRMX_share_data_logConfig config = CRMX_share_data_logConfig.build();
        logService.addChangeLog(before,after,config);

    }

    @Transactional
    public void deleteData(List<{class_name}> params) {
        for({class_name} param:params){
            //获得变更前数据
            {class_name} before = dataMapper.selectById(param.getSerial_no());
            //删除数据
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
    @Override
    @Transactional
    public void importData(File file, CIPRuntimeOperator operator) {
        //读取文件
        ExcelReader excelReader = new ExcelReader(file);
        try {
            excelReader.openFile();//打开文件
            List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
            int size = listResult.size();
            if (size == 0)
                return;
            StringBuffer errorMsg = new StringBuffer();
            List<String> jsonList = new ArrayList<>();
            //获得对象数据
            List<{class_name}> importDataList = CJExcelUtil.initImportExcelDatas({class_name}.getExcelTitle(), listResult, {class_name}.class);
            for (int i = 0; i < importDataList.size(); i++) {
                try {
            {class_name} importData = importDataList.get(i);
                    String json = JSONObject.toJSONString(importData);
                    if (jsonList.contains(json)) {
                        throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CRMX_UI_3700026);
                    }
                    jsonList.add(json);
                    MyValidationUtil.validateEntity(importData);

                    addData(importData);
                } catch (Exception e) {
                    log.info("addData error", e);
                    errorMsg.append("第" + (i + 2) + "行" + e.getMessage() + "<br/>");
                }
            }
            //有异常统一抛出
            if (StringUtils.isNotBlank(errorMsg.toString())) {
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CRMX_IMPORT_100420, errorMsg);
            }
        } catch (Exception e) {
            log.info("importData error", e);
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CRMX_IMPORT_100420, e.getMessage());
        } finally {
            try {
                excelReader.closeFile();//关闭文件(一定要确保文件的关闭，否则出错，在文件删除前)
            } catch (Exception e) {

            }
            boolean b = file.delete();//最后上传的文件要删除
            if (!b) {
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.CRMX_IMPORT_100420, "文件删除失败！");
            }
        }
    }



}
