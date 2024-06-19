package com.javaBuild.tmsp.api.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.tmsp.api.data.TmspUnloadDriverPlanPO;
import com.javaBuild.tmsp.api.data.TmspUnloadTaskVO;
import com.javaBuild.tmsp.api.data.TmspUnloadVO;
import com.word.util.OilCardUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/prv/api/unload")
public class TmspUnloadDriverPlanApiController {



    @CJ_jcjs_esbMethodInfo(desc = "司机预约", author = "T1113", alias = "tmsp_unload_driver_plan_addData")
    @RequestMapping(value="/addPlan")
    public CIPResponseMsg addPlan(@RequestBody TmspUnloadDriverPlanPO param){
        log.info("planAdd param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }

    @CJ_jcjs_esbMethodInfo(desc = "到车排班", author = "T1113", alias = "tmsp_unload_task_addTask")
    @RequestMapping(value="/addTask")
    public CIPResponseMsg addTask(@RequestBody TmspUnloadTaskVO param){
        log.info("addTask param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }

    @CJ_jcjs_esbMethodInfo(desc = "开始卸车", author = "T1113", alias = "tmsp_unload_task_startUnload")
    @RequestMapping(value="/startUnload")
    public CIPResponseMsg startUnload(@RequestBody TmspUnloadVO param){
        log.info("startUnload param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }

    @CJ_jcjs_esbMethodInfo(desc = "结束卸车", author = "T1113", alias = "tmsp_unload_task_endUnload")
    @RequestMapping(value="/endUnload")
    public CIPResponseMsg endUnload(@RequestBody TmspUnloadVO param){
        log.info("endUnload param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }






}
