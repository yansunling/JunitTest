package com.javaBuild.po;


import com.alibaba.fastjson.JSON;
import com.yd.common.data.CIPResponseMsg;
import com.dy.tmsp.ownCar.util.OilCardUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/actions/{table_name}")
public class {class_controller} {

    @Autowired
    private {class_service} dataService;

    @RequestMapping(value="/addData")
    public CIPResponseMsg addData(@RequestBody {class_name} param){
        log.info("addData param:"+ JSON.toJSONString(param));
        dataService.addData(param);
        return OilCardUtil.success();
    }
    @RequestMapping(value="/updateData")
    public CIPResponseMsg updateData(@RequestBody {class_name} param){
        log.info("updateData param:"+ JSON.toJSONString(param));
        dataService.updateData(param);
        return OilCardUtil.success();
    }
    @RequestMapping(value="/deleteData")
    public CIPResponseMsg deleteData(@RequestBody {class_name} param){
        log.info("deleteData param:"+ JSON.toJSONString(param));
        dataService.deleteData(param);
        return OilCardUtil.success();
    }
    @RequestMapping(value="/enableData")
    public CIPResponseMsg enableData(@RequestBody {class_name} param){
        log.info("enableData param:"+ JSON.toJSONString(param));
        dataService.updateStatus(param);
        return OilCardUtil.success();
    }
    @RequestMapping(value="/disableData")
    public CIPResponseMsg disableData(@RequestBody {class_name} param){
        log.info("disableData param:"+ JSON.toJSONString(param));
        dataService.updateStatus(param);
        return OilCardUtil.success();
    }
}
