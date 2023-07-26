package com.javaBuild.constant;

public class CreateEnum {
    public static void main(String[] args) {
        String name="现金，银行卡";
        String[] list = name.split("，");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();
        String domainId="tmsp_oa_pay_way";
        for(int i=0;i<list.length;i++){
            Integer key=i;
            String value=list[i].trim();
//            sql.append("INSERT INTO comp.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
//                    " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");

            sql.append("INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES" +
                    " ('tmsp','"+domainId+"', '"+key+"', '"+value+"','"+key+"',"+i+",'', now(), now(), 'T1113','T1113');\n");

        }
        System.out.println(sb.toString());
        System.out.println(sql.toString());
    }
}
