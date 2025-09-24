package com.str.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptToJsonUtil {

    public static void main(String[] args) throws Exception{
       /* String filePath = "C:\\Users\\yansunling\\Desktop/1.json";
        String json = FileUtil.readAsString(new File(filePath));*/


        String json = "{field:'ck',checkbox:true},\n" +
                "\t\t\t{field:'brand_name',name:'品牌',ctrlType:'easyui-validatebox',hidden:false,width:'90px',styler:cellStyler},\n" +
                "\t\t\t{field:'brand',name:'品牌',ctrlType:'easyui-validatebox',hidden:true,width:'90px',styler:cellStyler},\n" +
                "\t\t\t{field:'order_id',name:'运单号',ctrlType:'easyui-validatebox',hidden:false,width:'95px',styler:cellStyler},\n" +
                "\t\t\t{field:'delivery_no',name:'次运号',ctrlType:'easyui-validatebox',hidden:false,width:'130px',styler:cellStyler},\n" +
                "\t\t\t{field:'send_cust_name',name:'发货客户名称',ctrlType:'easyui-validatebox',hidden:false,width:'120px'},\n" +
                "\t\t\t{field:'recv_cust_name',name:'收货人',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'trans_handle_code_name',name:'库存状态',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'goods_count',name:'开单件数',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'inboud_goods_count',name:'库存件数',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'stock_pos_id',name:'库位',ctrlType:'easyui-validatebox',hidden:false,width:'80px'},\n" +
                "\t\t\t{field:'sub_stock_pos_id',name:'二级库位',ctrlType:'easyui-validatebox',hidden:false,width:'80px'},\n" +
                "\t\t\t{field:'receive_type_name',name:'交货方式',ctrlType:'easyui-validatebox',hidden:false,width:'110px'},\n" +
                "\t\t\t{field:'transfer_city',name:'中转城市',ctrlType:'easyui-validatebox',hidden:false,width:'130px'},\n" +
                "\t\t\t{field:'goods_name_item',name:'品名',ctrlType:'easyui-validatebox',hidden:false,width:'110px'},\n" +
                "\t\t\t{field:'goods_cube',name:'体积/m³',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'goods_weight',name:'重量/kg',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'send_org_id_name',name:'装车部门',ctrlType:'easyui-validatebox',hidden:false,width:'130px'},\n" +
                "\t\t\t{field:'self_arrive_fee',name:'到付运费',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'cod_amount',name:'代收货款',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'recv_phone_number',name:'收货人联系方式',ctrlType:'easyui-validatebox',hidden:false,width:'120px'},\n" +
                "\t\t\t{field:'storage_remark',name:'对内备注',ctrlType:'easyui-validatebox',hidden:false,width:'210px'},\n" +
                "\t\t\t{field:'external_remark',name:'对外备注',ctrlType:'easyui-validatebox',hidden:false,width:'210px'},\n" +
                "\t\t\t{field:'print_status_name',name:'打印状态',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'src_ticket_arrive',name:'打印来源',ctrlType:'easyui-validatebox',hidden:true,width:'70px'},\n" +
                "\t\t\t{field:'agency_identity_name',name:'代理人',ctrlType:'easyui-validatebox',hidden:false,width:'80px'},\n" +
                "\t\t\t{field:'agency_identity_id',name:'代理人身份证号',ctrlType:'easyui-validatebox',hidden:false,width:'140px'},\n" +
                "\t\t\t{field:'doc_id',name:'交接单号',ctrlType:'easyui-validatebox',hidden:false,width:'120px'},\n" +
                "\t\t\t{field:'vehicle_law_name',name:'车牌号/车厢号',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'product_type_name',name:'产品类型',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'recv_address',name:'收货地址',ctrlType:'easyui-validatebox',hidden:false,width:'200px'},\n" +
                "\t\t\t{field:'consignee_identity_id',name:'收货人身份证',ctrlType:'easyui-validatebox',hidden:false,width:'140px'},\n" +
                "\t\t\t{field:'order_pay_type_name',name:'付款方式',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'pay_type_for_name',name:'到达联-付款方式',ctrlType:'easyui-validatebox',hidden:false,width:'115px'},\n" +
                "\t\t\t{field:'store_amount_arrive',name:'仓储费',ctrlType:'easyui-validatebox',hidden:false,width:'60px'},\n" +
                "\t\t\t{field:'handbag_start_fee',name:'提包费',ctrlType:'easyui-validatebox',hidden:false,width:'60px'},\n" +
                "\t\t\t{field:'reach_pay_fee',name:'开单到付费用合计',ctrlType:'easyui-validatebox',hidden:false,width:'115px'},\n" +
                "\t\t\t{field:'delivery_start_fee',name:'送货费(始发)',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'delivery_expense_fee',name:'送货费(终到)',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'transfer_over_fee',name:'中转短驳费(终到)',ctrlType:'easyui-validatebox',hidden:false,width:'115px'},\n" +
                "\t\t\t{field:'forklift_over_fee',name:'叉车费(终到)',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'handbag_over_fee',name:'提包费(终到)',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'printer',name:'打印人',ctrlType:'easyui-validatebox',hidden:true,width:'70px'},\n" +
                "\t\t\t{field:'printer_id_name',name:'打印人',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t{field:'print_time',name:'打印时间',ctrlType:'easyui-datetimebox',hidden:false,width:'150px'},\n" +
                "\t\t\t{field:'print_org_id_name',name:'打印部门',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t{field:'departure_time',name:'装车时间',ctrlType:'easyui-datetimebox',hidden:false,width:'150px'},\n" +
                "\t\t\t{field:'arrive_time',name:'到达时间',ctrlType:'easyui-datetimebox',hidden:false,width:'150px'},\n" +
                "\t\t\t{field:'in_time',name:'入库时间',ctrlType:'easyui-datetimebox',hidden:false,width:'150px'},\n" +
                "\n" +
                "\t\t\t// 隐藏字段\n" +
                "\t\t\t{field:'print_status',name:'打印状态',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'make',name:'派车单制作时间',ctrlType:'easyui-datebox',hidden:true,width:'90px'},\n" +
                "\t\t\t{field:'driver_name',name:'司机名称',ctrlType:'easyui-validatebox',hidden:true,width:'60px'},\n" +
                "\t\t\t{field:'driver_mobile',name:'司机电话',ctrlType:'easyui-validatebox',hidden:true,width:'60px'},\n" +
                "\t\t\t{field:'vehicle_law_id',name:'牌照号',ctrlType:'easyui-validatebox',hidden:true,width:'60px'},\n" +
                "\t\t\t{field:'reach_pay',name:'提付款',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'printer_id',name:'打印人ID',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'print_org_id',name:'打印部门',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'reach_fee_total',name:'应收合计',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'order_reach_pay',name:'提付款(始发)',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'return_tip_flag',name:'回单标识',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'order_status',name:'订单状态',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'forklift_amount',name:'叉车费(始发)',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'forklift_amount_arrive',name:'叉车费(到达)',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'bag_amounbag_amountt_arrive',name:'提包费(到达)',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'lose_no_guarantee_name',name:'保丢不保损',ctrlType:'easyui-validatebox',hidden:true}";

        jsToList(json);


    }

    public static List<Map<String,String>> jsToList(String content){
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(content);
        List<Map<String,String>> list=new ArrayList<>();
        while  (matcher.find()) {
            String objStr = matcher.group(1);
            System.out.println(objStr);
            String[] strs = objStr.split(",");
            Map<String,String> map=new HashMap<>();
            for(String str:strs){
                String[] temps = str.split(":");
                if(temps.length==2){
                    map.put(temps[0].replaceAll("'",""),temps[1].replaceAll("'",""));
                }
            }
            list.add(map);
        }
        System.out.println(JSON.toJSONString(list));
        System.out.println("\n\n\n");
        return list;
    }



}
