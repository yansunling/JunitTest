package com.str.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptToJson {

    public static void main(String[] args) throws Exception{
       /* String filePath = "C:\\Users\\yansunling\\Desktop/1.json";
        String json = FileUtil.readAsString(new File(filePath));*/


        String json = "{field:'vehicle_law_id',name:'车牌号',ctrlType:'easyui-validatebox',hidden:false,width:'120px',align:'left'},\n" +
                "\t\t\t\t{field:'stock_all_serial_no',name:'库存流水号',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'stock_lock_flag',name:'库存锁定',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'delivery_no',name:'次运号',ctrlType:'easyui-validatebox',hidden:false,width:'110px'},\n" +
                "\t\t\t\t{field:'cust_goods_name',name:'品名(客户)',ctrlType:'easyui-validatebox',hidden:false,width:'50px'},\n" +
                "\t\t\t\t{field:'goods_count_sum',name:'库存件数',totalFlag:'Y',ctrlType:'easyui-numberbox',hidden:false,sorter:sort_int},\n" +
                "\t\t\t\t{field:'goods_weight_sum',name:'重量/kg',totalFlag:'Y',ctrlType:'easyui-validatebox',hidden:false,sorter:sort_int},\n" +
                "\t\t\t\t{field:'goods_cube_sum',name:'体积/m³',totalFlag:'Y',ctrlType:'easyui-numberbox',hidden:false,sorter:sort_int},\n" +
                "\t\t\t\t{field:'stock_pos_id',name:'库位',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t\t{field:'fee_actual_1000',name:'运费',ctrlType:'easyui-validatebox',totalFlag:'Y',hidden:false,width:'50px'},\n" +
                "\t\t\t\t{field:'fee_actual_1004',name:'保价费',ctrlType:'easyui-validatebox',totalFlag:'Y',hidden:false,width:'50px'},\n" +
                "\t\t\t\t{field:'order_id',name:'运单号',totalFlag:'Y',ctrlType:'easyui-validatebox',hidden:false,width:'75px'},\n" +
                "\t\t\t\t{field:'ticket_org_name',name:'始发网点',ctrlType:'easyui-validatebox',hidden:false,width:'150px'},\n" +
                "\t\t\t\t{field:'send_cust_name',name:'发货客户名称',ctrlType:'easyui-validatebox',hidden:false,width:'75px'},\n" +
                "\t\t\t\t{field:'product_type_name',name:'产品类型',ctrlType:'easyui-combobox',hidden:false,width:'75px'},\n" +
                "\t\t\t\t{field:'goods_count_judge',name:'库存件数',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'goods_count',name:'交接件数',ctrlType:'easyui-numberbox',hidden:true},\n" +
                "\t\t\t\t{field:'goods_weight',name:'重量/kg',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'goods_cube',name:'体积/m³',ctrlType:'easyui-numberbox',hidden:true},\n" +
                "\t\t\t\t{field:'version',name:'版本号',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'last_city',name:'目的城市',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'last_city_name',name:'目的城市',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t\t{field:'stock_id',name:'库存单id',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'is_special_cargo_name',name:'是否异形货',ctrlType:'easyui-validatebox',hidden:false,width:'70px'},\n" +
                "\t\t\t\t{field:'recv_cust_name',name:'收货人名称',ctrlType:'easyui-validatebox',hidden:false,width:'75px'},\n" +
                "\t\t\t\t{field:'transfer_city_name',name:'中转城市',ctrlType:'easyui-validatebox',hidden:false,width:'60px',formatter:function(value){\n" +
                "\t\t\t\t\t\treturn (value==\"0\" || value==\"\")? \"无\":value\n" +
                "\t\t\t\t\t}},\n" +
                "\t\t\t\t{field:'send_org_id_name',name:'上一站',ctrlType:'easyui-validatebox',hidden:false,width:'100px'},\n" +
                "\t\t\t\t{field:'load_date',name:'装车日期',ctrlType:'easyui-datebox',hidden:false,width:'75px'},\n" +
                "\t\t\t\t{field:'stock_status_name',name:'库存状态',ctrlType:'easyui-validatebox',hidden:false,width:'55px'},\n" +
                "\t\t\t\t{field:'is_stock',name:'是否在库库存',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t\t{field:'cargo_stock_pos_id',name:'分流库位',ctrlType:'easyui-validatebox',hidden:false,width:'55px'}";


        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(json);
        List<Map<String,String>> list=new ArrayList<>();
        while  (matcher.find()) {
            String objStr = matcher.group(1);
            System.out.println(objStr);

            String[] strs = objStr.split(",");
            Map<String,String> map=new HashMap<>();
            for(String str:strs){
                String[] temps = str.split(":");
                for(String temp:temps){
                    temp=temp.replaceAll("'","");
                }
                map.put(temps[0],temps[1]);
            }
            list.add(map);
        }




        /*json = json.replaceAll("'", "");
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map config = objectMapper.readValue(json, Map.class);
            System.out.println(config);
        } catch (IOException e) {
            e.printStackTrace();
        }*/




    }

}
