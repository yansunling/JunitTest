package com.javaBuild.constant;

public class CreateMdmEnum {
    public static void main(String[] args) {
        String name="修改基本信息、修改地址、修改联系人、删除地址、删除联系人、新增地址、新增联系人";
        String[] list = name.split("、");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();
        StringBuffer cipSql=new StringBuffer();

        String enumStart="UPDATE_TYPE";
        String domainId=enumStart.toLowerCase();
        if(!enumStart.endsWith("_")){
            enumStart+="_";
        }


        for(int i=0;i<list.length;i++){
            Integer key=i+1;
            String value=list[i];
            sb.append(enumStart+key+"(\""+key+"\",\""+value+"\"),\n");
            sql.append("INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order, create_time, update_time, creator) VALUES" +
                    " ('tmsp','"+domainId+"', '"+key+"', '"+value+"', '"+key+"',0, now(), now(), 'T1113');\n");
            cipSql.append("INSERT INTO cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                    " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");
        }
        System.out.println(sb.toString());
        System.out.println(sql.toString());
        System.out.println(cipSql.toString());
    }
}
