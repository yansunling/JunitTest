package com.javaBuild.po;

import com.dy.tmsp.ownCar.log.TmspShareDataLogService;
import com.dy.tmsp.ownCar.log.config.TmspShareDataLogConfig;
import com.dy.tmsp.ownCar.util.OilCardUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class {class_impl} implements {class_service} {

    @Autowired
    private {class_mapper} dataMapper;
    @Autowired
    private TmspShareDataLogService logService;




    @Transactional
    public void addData({class_name} param) {

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
        BeanUtils.copyProperties(before,after);
        //更新复制后数据
        BeanUtils.copyProperties(param, after, OilCardUtil.getNullPropertyNames(param));
        //更新数据
        dataMapper.updateById(after);
        //记录变更日志
        logService.addLog(before,after,TmspShareDataLogConfig.build());
    }

    @Transactional
    public void updateStatus({class_name} param) {
        {class_name} before = dataMapper.selectById(param.getSerial_no());
        //复制数据
        {class_name} after=new {class_name}();
        BeanUtils.copyProperties(before,after);
        //设置变更状态
        after.setUsage_status(param.getUsage_status());
        //更新状态
        dataMapper.updateById(after);
        //记录日志
        logService.addLog(before,after,TmspShareDataLogConfig.build());

     }



    @Transactional
    public void deleteData({class_name} param) {
        //获得变更前数据
        {class_name} before = dataMapper.selectById(param.getSerial_no());
        //删除审批人
        dataMapper.deleteById(param.getSerial_no());
        //记录日志
        logService.addLog(before,null,TmspShareDataLogConfig.build());
    }
}
