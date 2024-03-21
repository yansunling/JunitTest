package com.javaBuild.po;

import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.dy.tmsp.oaClaims.oaData.WorkFlowDtsResponseData;
import com.dy.tmsp.oaClaims.util.WorkFlowUtil;
import com.dy.tmsp.ownCar.log.TmspShareDataLogService;
import com.dy.tmsp.ownCar.log.config.TmspShareDataLogConfig;
import com.dy.tmsp.ownCar.service.TmspOaService;
import com.dy.tmsp.ownCar.service.TmspOwnVehicleRepairService;
import com.yd.tmsp.ownCar.util.OilCardUtil;
import com.error.auto.GlobalErrorInfoEnum;
import com.yd.bmsp.constants.FEE_ITEM;
import com.yd.common.runtime.CIPRuntime;
import com.yd.common.runtime.CIPRuntimeOperator;
import com.yd.tmsp.TMSPConstant;
import com.yd.tmsp.constants.IS_NOT;
import com.yd.tmsp.constants.OA_TYPE;
import com.yd.tmsp.constants.TMSP_OA_INSURANCE_STATUS;
import com.yd.tmsp.ownCar.data.TmspEmpEntData;
import com.yd.tmsp.ownCar.mapper.TmspOwnOaRecordDetailMapper;
import com.yd.tmsp.ownCar.mapper.TmspOwnOaRecordMapper;
import com.yd.tmsp.ownCar.mapper.TmspOwnVehicleRepairMapper;
import com.yd.tmsp.ownCar.oa.*;
import com.yd.tmsp.ownCar.po.TmspOwnOaRecordDetailPO;
import com.yd.tmsp.ownCar.po.TmspOwnOaRecordPO;
import com.yd.tmsp.ownCar.po.TmspOwnVehicleInsurancePO;
import com.yd.tmsp.ownCar.validate.MyValidationUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;


@Slf4j
@Service
public class {class_impl} implements {class_service} {

    @Autowired
    private {class_mapper} dataMapper;
    @Autowired
    private TmspShareDataLogService logService;

    @Autowired
    private TmspOaService oaService;
    @Autowired
    private TmspOwnOaRecordDetailMapper recordDetailMapper;



    @Transactional
    public void addData({class_name} param) {
        //设置流水号
        String serialNo = OilCardUtil.getRedisDayNo("CLYH", 4);
        param.setSerial_no(serialNo);
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
    public void deleteData({class_name} param) {
        //获得变更前数据
        {class_name} before = dataMapper.selectById(param.getSerial_no());
        //删除审批人
        dataMapper.deleteById(param.getSerial_no());
        //记录日志
        logService.addLog(before,null,TmspShareDataLogConfig.build());
    }


    @Override
    public void applyData(TmspOwnOaCommonData oaCommonData) {
        //获得车辆保险
        String[] serialNoList = oaCommonData.getSerial_no().split(",");
        List<{class_name}> dataList = dataMapper.selectList(Wrappers.<{class_name}>lambdaQuery()
        .in({class_name}::getSerial_no, serialNoList));
        //年审费用
        TMSP_OA_FEE_DETAIL fee = TMSP_OA_FEE_DETAIL.REPAIR_FEE;
        IS_NOT isForklift = dataList.get(0).getIs_forklift();
        //叉车年审费
        if(IS_NOT.IS_NOT_1==isForklift){
        fee = TMSP_OA_FEE_DETAIL.FORKLIFT_REPAIR_FEE;
        }
        //生成费用明细
        List<TmspOwnOaCommonDetailData> detailDataList=new ArrayList<>();
        //oa明细记录
        List<TmspOwnOaRecordDetailPO> oaRecordDetailList=new ArrayList<>();
        //oa记录流水号
        String recordSerialNo= IdUtil.simpleUUID();

        Set<String> deptNames=new HashSet<>();
        //生成报销明细
        dataList.forEach(item->{
        detailDataList.add(new TmspOwnOaCommonDetailData(item.getOrg_id(),item.getRepair_amount()));
        //添加明细数据
        TmspOwnOaRecordDetailPO recordDetailPO=new TmspOwnOaRecordDetailPO();
        recordDetailPO.setApply_amount(item.getRepair_amount());
        recordDetailPO.setOrg_id(item.getOrg_id());
        recordDetailPO.setVehicle_law_id(item.getVehicle_law_id());
        recordDetailPO.setBusi_doc_id(item.getSerial_no());
        recordDetailPO.setRecord_serial_no(recordSerialNo);
        oaRecordDetailList.add(recordDetailPO);
        //设置借支费用承担部门
        deptNames.add(logService.getCacheValue(TMSPConstant.DEPT_QUERY_CACHE,item.getOrg_id()));

        });
        //流程请求
        WorkFlowDtsResponseData responseData = oaService.createBorrowApply(oaCommonData, detailDataList, fee);
        try {
        TmspOaRecordData oaRecordData = new TmspOaRecordData(OA_TYPE.BORROW);
        //复制oa数据
        BeanUtils.copyProperties(responseData,oaRecordData);
        oaRecordData.setRecord_serial_no(recordSerialNo);
        //设置费用承担部门
        oaRecordData.setDept(StringUtils.join(",",deptNames.toArray()));
        //记录支付的请求数据
        oaService.insertLoanInfo(oaCommonData,oaRecordData);
        //插入record记录
        oaService.insertOaRecord(oaCommonData,oaRecordData,fee);
        //插入record记录明细
        recordDetailMapper.insertBatchDefault(oaRecordDetailList);
        //获得用户
        CIPRuntimeOperator user = CIPRuntime.getOperateSubject();
        //更新审批
        dataMapper.update(null, Wrappers.<{class_name}>lambdaUpdate()
        .set({class_name}::getOa_apply_time,new Timestamp(System.currentTimeMillis()))
        .set({class_name}::getOa_apply_user_id,user.getSubject_id())
        .set({class_name}::getLoan_process_number,responseData.getLcbh())
        .set({class_name}::getOa_flag,IS_NOT.IS_NOT_1)
        .setSql("approval_amount=repair_amount")
        .set({class_name}::getOa_status, TMSP_OA_INSURANCE_STATUS.OA_INSURANCE_STATUS_1)
        .in({class_name}::getSerial_no,serialNoList));
        } catch (Exception e) {
            log.info("applyData:"+ JSON.toJSONString(oaCommonData),e);
            //删除oa记录
            WorkFlowUtil.deleteWorkFlow(responseData.getRequestid(), CIPRuntime.getOperateSubject());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,e.getMessage());
        }
      }

    @Override
    public void repayData(TmspOwnOaRepayData param) {
        //获得用户
        CIPRuntimeOperator user = CIPRuntime.getOperateSubject();
        String[] serialNoList = param.getSerial_no().split(",");
        //查询保险明细
        List<{class_name}> dataList = dataMapper.selectList(Wrappers.<{class_name}>lambdaQuery()
        .in({class_name}::getSerial_no, serialNoList));
        //维修费用
        TMSP_OA_FEE_DETAIL fee = TMSP_OA_FEE_DETAIL.FUEL_FEE;
        {class_name} dataPO = dataList.get(0);
        IS_NOT isForklift = dataPO.getIs_forklift();
        param.setFee_item(FEE_ITEM.FEE_ITEM_A0201);
        //叉车维修费用
        if (IS_NOT.IS_NOT_1 == isForklift) {
            fee = TMSP_OA_FEE_DETAIL.FORKLIFT_FUEL_FEE;
            param.setFee_item(FEE_ITEM.FEE_ITEM_A0701);
        }
        //oa记录流水号
        String recordSerialNo = IdUtil.simpleUUID();
        //oa明细记录
        List<{class_name}> oaRecordDetailList = new ArrayList<>();
        //费用承担部门
        Set<String> deptNames = new HashSet<>();
        //生成oaRecord明细
        dataList.forEach(item -> {
        //添加明细数据
        TmspOwnOaRecordDetailPO recordDetailPO = new TmspOwnOaRecordDetailPO();
        recordDetailPO.setApply_amount(item.getApply_amount());
        recordDetailPO.setOrg_id(item.getOrg_id());
        recordDetailPO.setVehicle_law_id(item.getVehicle_law_id());
        recordDetailPO.setBusi_doc_id(item.getSerial_no());
        recordDetailPO.setRecord_serial_no(recordSerialNo);
        oaRecordDetailList.add(recordDetailPO);
        deptNames.add(logService.getCacheValue(TMSPConstant.DEPT_QUERY_CACHE, item.getOrg_id()));
        });
        //设置关联流程
        List<TmspBorrowData> detailRows = param.getDetail_rows();
        List<String> requestList = detailRows.stream().map(TmspBorrowData::getRequest_id).collect(Collectors.toList());
        param.setLink_request_id(StringUtils.join(",",requestList.toArray()));
        //3.生成OA申请流
        WorkFlowDtsResponseData responseData = oaService.createRepayApply(param, fee);
        try {
            TmspOaRecordData oaRecordData = new TmspOaRecordData(OA_TYPE.REPAY);
            //复制oa数据
            BeanUtils.copyProperties(responseData, oaRecordData);
            oaRecordData.setRecord_serial_no(recordSerialNo);
            //设置费用承担部门
            oaRecordData.setDept(StringUtils.join(",", deptNames.toArray()));
            //记录支付的请求数据
            oaService.insertLoanInfo(param, oaRecordData);
            //插入bmsp记录
            String repayId = oaService.insertRepayBmsp(param, responseData);
            //设置还借支流水号
            oaRecordData.setBusi_doc_id(repayId);
            //插入record记录
            oaService.insertOaRecord(param,oaRecordData,fee);
            //插入record记录明细
            recordDetailMapper.insertBatchDefault(oaRecordDetailList);
            //更新审批
            dataMapper.update(null, Wrappers.<{class_name}>lambdaUpdate()
            .set({class_name}::getOa_apply_time, new Timestamp(System.currentTimeMillis()))
            .set({class_name}::getOa_apply_user_id, user.getSubject_id())
            .set({class_name}::getRepayment_process_number, responseData.getLcbh())
            .set({class_name}::getOa_flag, IS_NOT.IS_NOT_1)
            .setSql("approval_amount=apply_amount")
            .setSql("archived_amount=0")
            .set({class_name}::getOa_status, TMSP_OA_STATUS_ALL.OA_STATUS_ALL_APPROVAL)
            .in({class_name}::getSerial_no, serialNoList));
        } catch (Exception e) {
            log.info("applyData:" + JSON.toJSONString(param), e);
            //删除oa记录
            WorkFlowUtil.deleteWorkFlow(responseData.getRequestid(), CIPRuntime.getOperateSubject());
            throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public void payData(TmspOwnOaCommonData oaCommonData) {
            //获得车辆保险
            String[] serialNoList = oaCommonData.getSerial_no().split(",");
            List<{class_name}> dataList = dataMapper.selectList(Wrappers.<{class_name}>lambdaQuery()
            .in({class_name}::getSerial_no, serialNoList));
            //年审费用
            TMSP_OA_FEE_DETAIL fee = TMSP_OA_FEE_DETAIL.REPAIR_FEE;

            IS_NOT isForklift = dataList.get(0).getIs_forklift();
            //叉车年审费
            if(IS_NOT.IS_NOT_1==isForklift){
                fee = TMSP_OA_FEE_DETAIL.FORKLIFT_REPAIR_FEE;
            }
            //生成费用明细
            List<TmspOwnOaCommonDetailData> detailDataList=new ArrayList<>();
            //oa明细记录
            List<TmspOwnOaRecordDetailPO> oaRecordDetailList=new ArrayList<>();
            //oa记录流水号
            String recordSerialNo= IdUtil.simpleUUID();
            //生成报销明细
            dataList.forEach(item->{
                detailDataList.add(new TmspOwnOaCommonDetailData(item.getOrg_id(),item.getApply_amount()));
                //添加明细数据
                TmspOwnOaRecordDetailPO recordDetailPO=new TmspOwnOaRecordDetailPO();
                recordDetailPO.setApply_amount(item.getApply_amount());
                recordDetailPO.setOrg_id(item.getOrg_id());
                recordDetailPO.setVehicle_law_id(item.getVehicle_law_id());
                recordDetailPO.setBusi_doc_id(item.getSerial_no());
                recordDetailPO.setRecord_serial_no(recordSerialNo);
                oaRecordDetailList.add(recordDetailPO);
            });
            //流程请求
            WorkFlowDtsResponseData responseData = oaService.createPayApply(oaCommonData, detailDataList, fee);
            try {
                TmspOaRecordData oaRecordData = new TmspOaRecordData(OA_TYPE.PAY);
                //复制oa数据
                BeanUtils.copyProperties(responseData,oaRecordData);
                oaRecordData.setRecord_serial_no(recordSerialNo);
                //记录支付的请求数据
                oaService.insertLoanInfo(oaCommonData,oaRecordData);
                //插入record记录
                oaService.insertOaRecord(oaCommonData,oaRecordData,fee);
                //插入record记录明细
                recordDetailMapper.insertBatchDefault(oaRecordDetailList);
                //获得用户
                CIPRuntimeOperator user = CIPRuntime.getOperateSubject();
                //更新审批
                dataMapper.update(null, Wrappers.<{class_name}>lambdaUpdate()
                .set({class_name}::getOa_apply_time,new Timestamp(System.currentTimeMillis()))
                .set({class_name}::getOa_apply_user_id,user.getSubject_id())
                .set({class_name}::getExpense_process_number,responseData.getLcbh())
                .set({class_name}::getOa_flag,IS_NOT.IS_NOT_1)
                .setSql("approval_amount=apply_amount")
                .set({class_name}::getOa_status, TMSP_OA_STATUS_ALL.OA_STATUS_ALL_APPROVAL)
                .in({class_name}::getSerial_no,serialNoList));
            } catch (Exception e) {
                log.info("payData:"+ JSON.toJSONString(oaCommonData),e);
                //删除oa记录
                WorkFlowUtil.deleteWorkFlow(responseData.getRequestid(), CIPRuntime.getOperateSubject());
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,e.getMessage());
            }
       }


    @Override
    @Transactional
    public void importData(File file){
            //读取文件
            try{
                ExcelReader reader=ExcelUtil.getReader(file.getPath());
                List<Map<String, Object>>listResult=reader.readAll();
                int size=listResult.size();
                if(size==0){
                return;
            }
            //获得对象数据
            List<{class_name}> importDataList=OilCardUtil.initImportExcelDatas({class_name}.getExcelTitle(),listResult,{class_name}.class);
            log.info("importData:"+JSON.toJSONString(importDataList));
            StringBuffer errorMsg=new StringBuffer();
            for(int i=0;i<importDataList.size();i++){
                {class_name} item=importDataList.get(i);
            try{
                MyValidationUtil.validateEntity(item);//全部校验
                //校验车牌好
                TmspOwnVehicleInsurancePO vehiclePO=oaService.findVehicleId(item.getVehicle_law_id());
                if(vehiclePO==null){
                errorMsg.append("第"+(i+2)+"行车牌号填写错误，请核实后导入<br/>");
                continue;
            }
            Calendar calendar=Calendar.getInstance();
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            String thisMonth=DateUtils.format(calendar.getTime(),"yyyy-MM");
            calendar.add(Calendar.MONTH,-1);
            String lastMonth=DateUtils.format(calendar.getTime(),"yyyy-MM");
            if(day>5){
                if(!StringUtils.equalsIgnoreCase(item.getRepair_date(),thisMonth)){
                errorMsg.append("已超过当月5日，第"+(i+2)+"行的维修日期只能填写当月，格式为yyyy-MM，请核实后导入！<br/>");
                continue;
                }
            }else{
            if(!StringUtils.equalsIgnoreCase(item.getRepair_date(),thisMonth)&&!StringUtils.equalsIgnoreCase(item.getRepair_date(),lastMonth)){
                errorMsg.append("第"+(i+2)+"行的维修日期只能填写当月/上月，格式为yyyy-MM，请核实后导入！<br/>");
                continue;
                }
            }
            item.setIs_forklift(vehiclePO.getIs_forklift());
            item.setOrg_id(vehiclePO.getOrg_id());
            //校验经办人
            if(StringUtils.isNotBlank(item.getInsurer())){
            List<TmspEmpEntData> emp=oaService.findEmp(item.getInsurer());
            if(CollectionUtil.isEmpty(emp)){
                errorMsg.append("第"+(i+2)+"行经办人填写错误，请核实后导入！<br/>");
                continue;
            }
            if(emp.size()!=1){
                errorMsg.append("第"+(i+2)+"行经办人可能存在重名的情况，请按照格式名字(工号) 进行填写后导入！<br/>");
                continue;
            }
                item.setInsurer(emp.get(0).getEmp_id());
            }
                addData(item);
            }catch(Exception e){
                log.info("importData {class_name} error:"+JSON.toJSONString(item),e);
                //添加异常
                errorMsg.append("第"+(i+2)+"行"+e.getMessage()+"<br/>");
            }
        }
            //统一抛出异常
            if(StringUtils.isNotBlank(errorMsg.toString())){
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,errorMsg);
                }
            }catch(Exception e){
                log.info("importData subOilCard error",e);
                throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,e.getMessage());
            }finally{
                boolean b=file.delete();//最后上传的文件要删除
                if(!b){
                    throw new GlobalErrorInfoException(GlobalErrorInfoEnum.TMSP_UI_ERROR,"文件删除失败！");
                }
         }
     }

}
