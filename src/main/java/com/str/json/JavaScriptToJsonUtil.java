package com.str.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sanitized = stripComments(content);
        List<String> objectTexts = extractTopLevelObjects(sanitized);
        List<Map<String,String>> list=new ArrayList<>();
        for (String objectText : objectTexts) {
            list.add(parseObject(objectText));
        }
        return list;
    }

    private static String stripComments(String content) {
        StringBuilder builder = new StringBuilder(content.length());
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escaped = false;
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            char next = i + 1 < content.length() ? content.charAt(i + 1) : '\0';
            if (escaped) {
                builder.append(ch);
                escaped = false;
                continue;
            }
            if ((inSingleQuote || inDoubleQuote) && ch == '\\') {
                builder.append(ch);
                escaped = true;
                continue;
            }
            if (!inSingleQuote && !inDoubleQuote && ch == '/' && next == '/') {
                i += 2;
                while (i < content.length() && content.charAt(i) != '\n' && content.charAt(i) != '\r') {
                    i++;
                }
                if (i < content.length()) {
                    builder.append(content.charAt(i));
                }
                continue;
            }
            if (!inSingleQuote && !inDoubleQuote && ch == '/' && next == '*') {
                i += 2;
                while (i + 1 < content.length() && !(content.charAt(i) == '*' && content.charAt(i + 1) == '/')) {
                    i++;
                }
                i++;
                continue;
            }
            if (ch == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (ch == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    private static List<String> extractTopLevelObjects(String content) {
        List<String> objectTexts = new ArrayList<>();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escaped = false;
        int braceDepth = 0;
        int start = -1;
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if ((inSingleQuote || inDoubleQuote) && ch == '\\') {
                escaped = true;
                continue;
            }
            if (ch == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                continue;
            }
            if (ch == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                continue;
            }
            if (inSingleQuote || inDoubleQuote) {
                continue;
            }
            if (ch == '{') {
                if (braceDepth == 0) {
                    start = i;
                }
                braceDepth++;
            } else if (ch == '}') {
                braceDepth--;
                if (braceDepth == 0 && start >= 0) {
                    objectTexts.add(content.substring(start, i + 1));
                    start = -1;
                }
            }
        }
        return objectTexts;
    }

    private static Map<String, String> parseObject(String objectText) {
        Map<String, String> map = new HashMap<>();
        String body = objectText.substring(1, objectText.length() - 1);
        int index = 0;
        while (index < body.length()) {
            index = skipSeparators(body, index);
            if (index >= body.length()) {
                break;
            }
            int colonIndex = findTopLevelColon(body, index);
            if (colonIndex < 0) {
                break;
            }
            String key = normalizeToken(body.substring(index, colonIndex));
            index = colonIndex + 1;
            int valueEnd = findValueEnd(body, index);
            String value = normalizeToken(body.substring(index, valueEnd));
            map.put(key, value);
            index = valueEnd + 1;
        }
        return map;
    }

    private static int skipSeparators(String text, int index) {
        while (index < text.length()) {
            char ch = text.charAt(index);
            if (Character.isWhitespace(ch) || ch == ',') {
                index++;
                continue;
            }
            break;
        }
        return index;
    }

    private static int findTopLevelColon(String text, int start) {
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escaped = false;
        for (int i = start; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if ((inSingleQuote || inDoubleQuote) && ch == '\\') {
                escaped = true;
                continue;
            }
            if (ch == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                continue;
            }
            if (ch == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                continue;
            }
            if (!inSingleQuote && !inDoubleQuote && ch == ':') {
                return i;
            }
        }
        return -1;
    }

    private static int findValueEnd(String text, int start) {
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escaped = false;
        int braceDepth = 0;
        int bracketDepth = 0;
        int parenthesisDepth = 0;
        for (int i = start; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if ((inSingleQuote || inDoubleQuote) && ch == '\\') {
                escaped = true;
                continue;
            }
            if (ch == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                continue;
            }
            if (ch == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                continue;
            }
            if (inSingleQuote || inDoubleQuote) {
                continue;
            }
            if (ch == '{') {
                braceDepth++;
            } else if (ch == '}') {
                if (braceDepth == 0) {
                    return i;
                }
                braceDepth--;
            } else if (ch == '[') {
                bracketDepth++;
            } else if (ch == ']') {
                bracketDepth--;
            } else if (ch == '(') {
                parenthesisDepth++;
            } else if (ch == ')') {
                parenthesisDepth--;
            } else if (ch == ',' && braceDepth == 0 && bracketDepth == 0 && parenthesisDepth == 0) {
                return i;
            }
        }
        return text.length();
    }

    private static String normalizeToken(String token) {
        String trimmed = token.trim();
        if (trimmed.length() >= 2) {
            char first = trimmed.charAt(0);
            char last = trimmed.charAt(trimmed.length() - 1);
            if ((first == '\'' && last == '\'') || (first == '"' && last == '"')) {
                return trimmed.substring(1, trimmed.length() - 1);
            }
        }
        return trimmed;
    }
}
