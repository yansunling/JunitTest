package com.word.dataSource.controller;

import com.alibaba.fastjson.JSONObject;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.data.CompAssetApplyRecordPO;
import com.word.dataSource.data.CompAssetApplyRecordVO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j

@RestController
@RequestMapping(value = "/asset/comp_asset_apply_record", name = "资产申请")
public class CompAssetApplyRecordController {




    @CJ_jcjs_esbMethodInfo(desc = "资产申请领用", author = "T1113", version = "v1.0",alias = "asset_receive_data")
    @RequestMapping(value = "/receiveData", method = RequestMethod.POST)
    public CIPResponseMsg receiveData(@RequestBody CompAssetApplyRecordVO param) {

        return AssetUtil.success();
    }

    @ApiOperation(value = "申请退还")
    @CJ_jcjs_esbMethodInfo(desc = "资产申请退还", author = "T1113", version = "v1.0",alias = "asset_return_data")
    @RequestMapping(value = "/returnData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg returnData(@RequestBody CompAssetApplyRecordVO param) {
        log.info("申请退还参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }

    @ApiOperation(value = "申请通过")
    @CJ_jcjs_esbMethodInfo(desc = "资产申请通过", author = "T1113", version = "v1.0",alias = "asset_pass_data")
    @RequestMapping(value = "/passData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg passData(@RequestBody CompAssetApplyRecordPO param) {

        return AssetUtil.success();
    }

    @ApiOperation(value = "申请驳回")
    @CJ_jcjs_esbMethodInfo(desc = "资产申请驳回", author = "T1113", version = "v1.0" ,alias = "asset_reject_data")
    @RequestMapping(value = "/rejectData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg rejectData(@RequestBody CompAssetApplyRecordPO param) {
        log.info("申请驳回参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }


}
