package com.word.dataSource.controller;

import com.alibaba.fastjson.JSONObject;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.po.CompAssetLevelClassPO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.exception.CIPRuntimeException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.runtime.CIPRuntime;
import com.yd.common.runtime.CIPRuntimeConfigure;
import com.yd.common.utils.CIPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.UUID;


@Slf4j
@Api("资产分类")
@RestController
@RequestMapping(value = "/asset/comp_asset_level_class", name = "资产分类")
public class CompAssetLevelClassController {



    @ApiOperation(value = "新增资产分类")
    @CJ_jcjs_esbMethodInfo(desc = "新增资产分类", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/addData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg addData(@RequestBody CompAssetLevelClassPO param) {
        log.info("新增资产分类参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }

    @ApiOperation(value = "修改资产分类")
    @CJ_jcjs_esbMethodInfo(desc = "修改资产分类", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/updateData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg updateData(@RequestBody CompAssetLevelClassPO param) {
        log.info("修改资产分类参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }

    @ApiOperation(value = "删除资产分类")
    @CJ_jcjs_esbMethodInfo(desc = "删除资产分类", author = "root", version = "v1.0")
    @RequestMapping(value = "/deleteData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CIPResponseMsg deleteData(@RequestBody CompAssetLevelClassPO param) {
        log.info("删除资产分类参数:"+ JSONObject.toJSONString(param));

        return AssetUtil.success();
    }

    @ApiOperation(value = "导入资产分类")
    @CJ_jcjs_esbMethodInfo(desc = "导入资产分类", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/importData")
    public CIPResponseMsg importData(HttpServletRequest request, HttpServletResponse response) {
        CIPResponseMsg msg = AssetUtil.success();
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);//下载模板的名字
        if(!multipartContent){
            try {
                String filePath=request.getServletContext().getRealPath("/WEB-INF/template/基础分类维护导入.xlsx");//模板文件的存放路径
                File templateFile = new File(filePath);
                if(templateFile.exists()){
                    //文件下载
                    CIPUtil.downloadFile(filePath,response,true);
                    return null;
                }
            } catch (Exception e) {
                throw new CIPRuntimeException(new CIPErrorCode(500,"亲,不好意思,下载模板出错了!"));
            }
        } else {

            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();
            File uploadFile=null;
            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String filename = file.getOriginalFilename();
                    uploadFile=new File(CIPRuntimeConfigure.getCip_temp_file_path()+"/"+ UUID.randomUUID().toString() + "_" + filename);
                    // 上传
                    try {
                        file.transferTo(uploadFile);
                        break;
                    } catch (Exception e) {
                        log.error("importData ",e);
                    }
                }
            }

        }
        return msg;
    }




}
