package com.javaBuild.po;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dy.components.foundation.comm.facade.exception.CIPErrorCode;
import com.dy.components.foundation.comm.facade.exception.CIPRuntimeException;
import com.dy.tmsp.ownCar.service.TmspOwnVehicleRepairService;
import com.yd.common.data.CIPResponseMsg;
import com.yd.tmsp.ownCar.util.OilCardUtil;
import com.yd.common.utils.CIPUtil;
import com.yd.tmsp.ownCar.oa.TmspOwnOaCommonData;
import com.yd.tmsp.ownCar.po.TmspOwnVehicleRepairPO;
import com.yd.utils.common.ExcelSheetParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;


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
    @RequestMapping(value = "/importData")
    public CIPResponseMsg importData(HttpServletRequest request,HttpServletResponse response){
        String templateName=request.getParameter("templateName");//下载模板的名字
        //参数中带了下载模板的名字则进行模板下载,否则就是处理文件导入的逻辑
        if(!StringUtils.isEmpty(templateName)){
        try{
            //生成模板文件
            response.reset(); //非常重要
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition","attachment;filename="+templateName);
            OutputStream out=response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            SXSSFWorkbook wb=ExcelSheetParser.createWorkBook("sheet1",{class_name}.getExcelTitle(),null);
            wb.write(out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e){
             throw new CIPRuntimeException(new CIPErrorCode(500,"亲,不好意思,下载模板出错了!"));
        }
        }else{
            File file=CIPUtil.uploadFile(request,response);
            dataService.importData(file);
        }
        return OilCardUtil.success();
    }
}
