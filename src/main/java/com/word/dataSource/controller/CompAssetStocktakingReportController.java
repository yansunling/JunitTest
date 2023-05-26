package com.word.dataSource.controller;

import com.alibaba.fastjson.JSONObject;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.vo.CompAssetStocktakingAuditVO;
import com.word.dataSource.vo.CompAssetStocktakingExceptionClearVO;
import com.word.dataSource.vo.CompAssetStocktakingReportVO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j

@RestController
@RequestMapping(value = "/asset/comp_asset_stocktaking_report", name = "盘点上报")
public class CompAssetStocktakingReportController {


    @CJ_jcjs_esbMethodInfo(desc = "生成异常编号", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/generateExceptNum")
    public CIPResponseMsg generateExceptNum() {

        return AssetUtil.success();
    }


    @CJ_jcjs_esbMethodInfo(desc = "盘点正常上报", author = "T1113", version = "v1.0",alias = "asset_report_normal")
    @RequestMapping(value = "/reportNormal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg reportNormal(@RequestBody CompAssetStocktakingReportVO param) {

        return AssetUtil.success();
    }


    @CJ_jcjs_esbMethodInfo(desc = "盘点异常上报", author = "T1113", version = "v1.0",alias = "asset_report_except")
    @RequestMapping(value = "/reportExcept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg reportExcept(@RequestBody CompAssetStocktakingReportVO param) {
        log.info("盘点异常上报参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }


    @CJ_jcjs_esbMethodInfo(desc = "驳回盘点上报", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/rejectReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg rejectReport(@RequestBody CompAssetStocktakingAuditVO param) {

        return AssetUtil.success();
    }

    @CJ_jcjs_esbMethodInfo(desc = "通过盘点上报", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/passReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg passReport(@RequestBody CompAssetStocktakingAuditVO param) {
        log.info("通过盘点上报上报参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }

    @CJ_jcjs_esbMethodInfo(desc = "异常核销", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/exceptClear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg exceptClear(@RequestBody CompAssetStocktakingExceptionClearVO param) {
        log.info("异常核销参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }




}
