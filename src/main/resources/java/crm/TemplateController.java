package com.javaBuild.po;


import com.alibaba.fastjson.JSON;
import com.yd.common.data.CIPResponseMsg;
import com.yd.crmx.util.CrmxCommonUtil;
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
        log.info("{table_name} addData param:"+ JSON.toJSONString(param));
        dataService.addData(param);
        return CrmxCommonUtil.success();
    }
    @RequestMapping(value="/updateData")
    public CIPResponseMsg updateData(@RequestBody {class_name} param){
        log.info("{table_name} updateData param:"+ JSON.toJSONString(param));
        dataService.updateData(param);
        return CrmxCommonUtil.success();
    }
    @RequestMapping(value="/deleteData")
    public CIPResponseMsg deleteData(@RequestBody List<{class_name}> param){
        log.info("{table_name} deleteData param:"+ JSON.toJSONString(param));
        dataService.deleteData(param);
        return CrmxCommonUtil.success();
    }
    @RequestMapping(value="/importData")
    public CIPResponseMsg importData(HttpServletRequest request, HttpServletResponse response){
        CIPResponseMsg msg = new CIPResponseMsg();
        String templateName=request.getParameter("templateName");//下载模板的名字
        //参数中带了下载模板的名字则进行模板下载,否则就是处理文件导入的逻辑
        if(!StringUtils.isEmpty(templateName)){
        try {
                templateName = URLDecoder.decode(templateName,"utf-8");
                //生成模板文件
                response.reset(); //非常重要
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition","attachment;filename="+templateName);
                OutputStream out = response.getOutputStream();
                response.setCharacterEncoding("UTF-8");
                SXSSFWorkbook wb = ExcelSheetParser.createWorkBook(templateName, {class_name}.getExcelTitle(), null);
                wb.write(out);
            } catch (Exception e) {
                 throw new CIPRuntimeException(new CIPErrorCode(500,"亲,不好意思,下载模板出错了!"));
            }
        } else {
            File file = CIPUtil.uploadFile(request, response);
            dataService.importData(file, CIPRuntime.getOperateSubject());
            msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
            msg.msg = CIPErrorCode.CALL_SUCCESS.name;
            }
            return msg;
       }

}
