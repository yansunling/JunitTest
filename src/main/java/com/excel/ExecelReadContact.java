package com.excel;

import com.excel.data.ContactMan;
import com.excel.data.CrmLineSalesman;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecelReadContact {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\联络单审批人.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ContactMan> importDataList = CJExcelUtil.initImportExcelDatas(ContactMan.titleMap, listResult, ContactMan.class);

        Map<String, String> refMap = getRefMap();

        List<String> list=new ArrayList<>();
        importDataList.forEach(item->{

            item.setOrg_id(refMap.get(item.getOrg_name()));

            String[] contactClassNames =  item.getContact_class_name().split("、");
            List<String> contactClassNameList=new ArrayList<>();
            List<String> contactClassIdList=new ArrayList<>();

            for(String str:contactClassNames){
                contactClassNameList.add(str);
                if(StringUtils.isBlank(refMap.get(str))){
                    throw new RuntimeException(str+"没匹配");
                }
                contactClassIdList.add(refMap.get(str));
            }

            item.setContact_class(StringUtils.join(",",contactClassIdList.toArray()));
            item.setContact_class_name(StringUtils.join(",",contactClassNameList.toArray()));

            String[] approvers =  item.getApprover_name().split("、");
            List<String> approverList=new ArrayList<>();
            List<String> approverNameIdList=new ArrayList<>();
            for(String str:approvers){
                String empId = refMap.get(str);
                if(StringUtils.isBlank(empId)){
                    throw new RuntimeException(str+"没匹配");
                }
                approverList.add(empId);
                approverNameIdList.add(str+"("+empId+")");

            }
            item.setApprover(StringUtils.join(",",approverList.toArray()));
            item.setApprover_name(StringUtils.join(",",approverNameIdList.toArray()));


            String[] notices =  item.getNotice_name().split("、");
            List<String> noticeList=new ArrayList<>();
            List<String> noticeNameList=new ArrayList<>();
            for(String str:notices){
                String empId = refMap.get(str);
                if(StringUtils.isBlank(empId)){
                    throw new RuntimeException(str+"没匹配");
                }
                noticeList.add(empId);
                noticeNameList.add(str+"("+empId+")");

            }
            item.setNotice_id(StringUtils.join(",",noticeList.toArray()));
            item.setNotice_name(StringUtils.join(",",noticeNameList.toArray()));
            String sql="INSERT INTO tmsp.tmsp_contact_form_approval_config(serial_no, org_id, approver, approver_name, notice_id, notice_name, direct_train, direct_train_name, config_status, update_user_id, update_time, create_user_id, create_time, contact_class, contact_class_name) VALUES" +
                    " (uuid_short(), "+item.getOrg_id()+", '"+item.getApprover()+"', '"+item.getApprover_name()+"', '"+item.getNotice_id()+"', '"+item.getNotice_name()+"', '', '', '1', 'T1113', now(), 'T1113', now(), '"+item.getContact_class()+"', '"+item.getContact_class_name()+"');";
            System.out.println(sql);
            list.add(sql);
        });

        File outfile=new File("C:\\Users\\yansunling\\Desktop\\customer.sql");
        FileUtils.writeLines(outfile,"utf-8",list);
    }

    private static Map<String,String> getRefMap(){
        Map<String,String> map=new HashMap<>();
        map.put("乌东分拨场","350101040301");
        map.put("南郊分拨场","350101040302");
        map.put("金马村分拨场","350101050301");
        map.put("跑马山作业组","35010105030105");
        map.put("改貌分拨场","350101060301");
        map.put("西南商贸城分拨点","35010106030103");
        map.put("直达车类","0");
        map.put("运费类","1");
        map.put("中转类","2");
        map.put("配送类","3");
        map.put("送货费类","4");
        map.put("卸货费类","5");
        map.put("转货类","6");
        map.put("返货类","7");
        map.put("坏账类","8");
        map.put("邹玉坤","T0060");
        map.put("黄文忠","T0070");
        map.put("何客燕","T0151");
        map.put("陈荣胜","T0152");
        map.put("赵稳","T0157");
        map.put("周能","T0164");
        map.put("申本群","T0166");
        map.put("徐住桂","T0169");
        map.put("王荣平","T0170");
        map.put("邹金蓉","T0276");
        map.put("剡建平","T0390");
        map.put("聂争旋","T0405");
        map.put("赵海霞","T0425");
        map.put("邓加娥","T0433");
        map.put("敖智英","T0547");
        map.put("陈晓霞","T0572");
        map.put("扇炳中","T0579");
        map.put("任永慧","T0581");
        map.put("刘丽琴","T0959");
        map.put("李丽","T1004");
        map.put("郭琳","T1005");
        map.put("王萧萧","T1329");
        map.put("李丽","T1653");
        map.put("马巧云","T1910");
        map.put("米丽花","T2714");
        map.put("周龙","T2903");
        return map;
    }


}
