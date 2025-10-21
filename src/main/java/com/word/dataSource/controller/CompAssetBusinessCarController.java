package com.word.dataSource.controller;


import com.alibaba.fastjson.JSON;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.str.random.CompAssetBusinessCarPO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.exception.CIPRuntimeException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.runtime.CIPRuntimeConfigure;
import com.yd.utils.common.ExcelSheetParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/asset/comp_asset_business_car")
public class CompAssetBusinessCarController {



    @CJ_jcjs_esbMethodInfo(desc = "商务车新增", author = "T1113", version = "v1.0")
    @RequestMapping(value="/addData")
    public CIPResponseMsg addData(@RequestBody CompAssetBusinessCarPO param){
        log.info("addData param:"+ JSON.toJSONString(param));

        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "商务车修改", author = "T1113", version = "v1.0")
    @RequestMapping(value="/updateData")
    public CIPResponseMsg updateData(@RequestBody CompAssetBusinessCarPO param){
        log.info("updateData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "商务车删除", author = "T1113", version = "v1.0")
    @RequestMapping(value="/deleteData")
    public CIPResponseMsg deleteData(@RequestBody CompAssetBusinessCarPO params){
        log.info("deleteData param:"+ JSON.toJSONString(params));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "商务车导入", author = "T1113", version = "v1.0")
    @RequestMapping(value = "/importData")
    public CIPResponseMsg importData(HttpServletRequest request,HttpServletResponse response){
        CIPResponseMsg msg = AssetUtil.success();
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);//下载模板的名字
        if(!multipartContent){
            try {
                //生成模板文件
                response.reset(); //非常重要
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition","attachment;filename="+new String("商务车导入模板".getBytes("utf-8"), "iso8859-1")+".xlsx");
                OutputStream out=response.getOutputStream();
                response.setCharacterEncoding("UTF-8");
                SXSSFWorkbook wb= ExcelSheetParser.createWorkBook("sheet1", CompAssetBusinessCarPO.getExcelTitle(),null);
                wb.write(out);
                out.flush();
                out.close();
                return null;
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
