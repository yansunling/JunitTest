package com.javaBuild.constant;

public class CreateMdmEnum {
    public static void main(String[] args) {
        String name=">5000、5000>X>=4000、4000>X>=3000、3000>X>=2000、2000>X>=1000、1000>X>=500、500>X>=300、300>X>=100、100>X>=50、50>X>=30、30>X>=10、10>X>=5、5>X>=3、3>X>=1、1>X";
        String[] list = name.split("、");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();
        StringBuffer cipSql=new StringBuffer();

        String enumStart="weight_range";
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



        for(int i=1;i<16;i++){
            System.out.println("@CJ_column(name = \"货物重量"+i+"\")\n" +
                    "    private Double weight_total"+i+";\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    @CJ_column(name = \"客户数量"+i+"\")\n" +
                    "    private Integer cust_num"+i+";\n\n");

        }





    }
}
