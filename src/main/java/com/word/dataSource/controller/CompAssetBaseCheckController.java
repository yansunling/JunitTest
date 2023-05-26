package com.word.dataSource.controller;


import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.data.CompAssetStocktakingTaskPO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Api("资产台账结果对比")
@RestController
@RequestMapping(value = "/asset/comp_asset_base_check", name = "资产台账结果对比")
public class CompAssetBaseCheckController {










    @ApiOperation(value = "导入对比资产")
    @CJ_jcjs_esbMethodInfo(desc = "导入对比资产", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/importData")
    public CIPResponseMsg importData(HttpServletRequest request, HttpServletResponse response){
        CIPResponseMsg msg = AssetUtil.success();

        return msg;
    }

    @ApiOperation(value = "导出资产对比结果")
    @CJ_jcjs_esbMethodInfo(desc = "导出资产对比结果", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/exportData")
    public void exportData(@RequestBody CompAssetStocktakingTaskPO taskPO, HttpServletRequest request, HttpServletResponse response) throws Exception{


    }









}
