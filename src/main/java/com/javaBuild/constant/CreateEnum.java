package com.javaBuild.constant;

public class CreateEnum {
    public static void main(String[] args) {
        String name="错误上报、错误下发、标签补发、报废处置、损坏未修复、损坏已修复、信息已修正、丢失已找回、丢失未找回";
        String[] list = name.split("、");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();

        String enumStart="EXCEPT_CLEAR_";
        if(!enumStart.endsWith("_")){
            enumStart+="_";
        }

        String domainId="except_clear";
        for(int i=0;i<list.length;i++){
            Integer key=i+1;
            String value=list[i];
            sb.append(enumStart+key+"(\""+key+"\",\""+value+"\"),\n");
            sql.append("INSERT INTO comp.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                    " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");
        }
        System.out.println(sb.toString());
        System.out.println(sql.toString());
    }
}
