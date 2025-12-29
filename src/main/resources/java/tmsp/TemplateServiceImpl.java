package com.javaBuild.po;

import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.dy.tmsp.ownCar.log.TmspShareDataLogService;
import com.dy.tmsp.ownCar.log.config.TmspShareDataLogConfig;
import com.error.auto.GlobalErrorInfoEnum;
import com.yd.tmsp.ownCar.util.OilCardUtil;
import com.yd.tmsp.ownCar.validate.MyValidationUtil;
import com.yd.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class {class_impl} implements {class_service} {

    @Autowired
    private {class_mapper} dataMapper;
    @Autowired
    private TmspShareDataLogService logService;


    @Transactional
    public void addData({class_name} param) {
        //设置流水号
        param.setSerial_no(IdUtil.simpleUUID());

        {status_column}

        //插入记录
        dataMapper.insertDefault(param);
        //记录变更日志
        logService.addLog(null,param, TmspShareDataLogConfig.build().setLog_content(""));

    }

    @Transactional
    public void updateData({class_name} param) {
        //获得变更前数据
        {class_name} before = dataMapper.selectById(param.getSerial_no());
        //获得变更后数据
        {class_name} after = new {class_name}();
        BeanUtils.copyProperties(before,after,"update_time","update_user_id");
        //更新复制后数据
        BeanUtils.copyProperties(param, after, OilCardUtil.getNullPropertyNames(param));
        //更新数据
        dataMapper.updateById(after);
        //记录变更日志
        logService.addLog(before,after,TmspShareDataLogConfig.build());
    }

    @Transactional
    public void batchUpdate(List<{class_name}> params) {
        params.forEach(item->{
            updateData(item);
        });
    }



    @Transactional
    public void deleteData(List<{class_name}> params) {
        params.forEach(param->{
            //获得变更前数据
            {class_name} before = dataMapper.selectById(param.getSerial_no());
            //删除记录
            dataMapper.deleteById(param.getSerial_no());
            //记录日志
            logService.addLog(before,null,TmspShareDataLogConfig.build());
        });
    }





    @Override
    @Transactional
    public void importData(File file){
            //读取文件
            try{
                ExcelReader reader=ExcelUtil.getReader(file.getPath());
                List<Map<String, Object>>listResult=reader.readAll();
                int size=listResult.size();
                if(size==0){
                return;
            }
            //获得对象数据
            List<{class_name}> importDataList=OilCardUtil.initImportExcelDatas({class_name}.getExcelTitle(),listResult,{class_name}.class);
            log.info("importData:"+JSON.toJSONString(importDataList));
            StringBuffer errorMsg=new StringBuffer();
            for(int i=0;i<importDataList.size();i++){
                {class_name} item=importDataList.get(i);
            try{
                    MyValidationUtil.validateEntity(item);//全部校验
                    addData(item);
                }catch(Exception e){
                    log.info("importData {class_name} error:"+JSON.toJSONString(item),e);
                    //添加异常
                    errorMsg.append("第"+(i+2)+"行"+e.getMessage()+"<br/>");
                }
            }
            //统一抛出异常
            if(StringUtils.isNotBlank(errorMsg.toString())){
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,errorMsg);
              }
            }catch(Exception e){
                log.info("importData subOilCard error",e);
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,e.getMessage());
            }finally{
                boolean b=file.delete();//最后上传的文件要删除
                if(!b){
                    throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,"文件删除失败！");
                }
         }
     }

}
