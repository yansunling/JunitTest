package com.word;

public class CreateEnum {
    public static void main(String[] args) {
        String name="损失、损坏、报废、出售、出租、捐赠、退租";
        String[] list = name.split("、");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();

        String enumStart="DISPOSAL_";
        String domainId="disposal_type";
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
