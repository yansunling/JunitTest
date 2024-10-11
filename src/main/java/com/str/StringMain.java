package com.str;

public class StringMain {
    public static void main(String[] args) {

        String column="serial_no, rank_month, customer_id, customer_type, send_recv_flag, business_region_id, last_city_name, product_type, this_month_ton_price, this_month_bubble, this_month_line_amount, this_month_goods_cube, this_month_goods_weight,region_type,create_time,this_month_ticket_num";
        String column2="uuid_short(),rank_month, customer_id, customer_type, send_recv_flag, business_region_id, last_city_name, product_type, ton_price, bubble, line_amount, goods_cube, goods_weight, #{param.region_type},now(),ticket_num";
        column=column.replaceAll("\n","");
        column2=column2.replaceAll(", 2 ","(temp)");
        column2=column2.replaceAll(",null,","(null)");
        column2=column2.replaceAll("\n","");

        String[] columns = column.split(",");
        String[] column2s = column2.split(",");



        StringBuffer sb=new StringBuffer();

        for(int i=0;i<columns.length;i++){
            column2s[i]=column2s[i].replaceAll("\\(temp\\)",", 2 ");
            column2s[i]=column2s[i].replaceAll("\\(null\\)",",null,");
            String temp="";
            if(column2s[i].indexOf(" as ")<0){
                temp=column2s[i].trim()+" as "+columns[i].trim()+",\n";
            }else{
                temp=column2s[i].trim()+",\n";
            }
            sb.append(temp.trim()).append("\n");

        }

        String substring = sb.substring(0, sb.length() - 2);
        System.out.println(substring);


    }




}
