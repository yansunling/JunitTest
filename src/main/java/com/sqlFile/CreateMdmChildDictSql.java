package com.sqlFile;

import com.yd.utils.common.StringUtils;

public class CreateMdmChildDictSql {
    public static void main(String[] args) {
        String name="事故上报,事故瞒报,时效备案,质量上报,差错上报,异常货物处理,价格备案,工作失误,违规操作,工作配合";
        String[] list = name.split(",");
        StringBuffer sql=new StringBuffer();
        String domainId="error_subtype";
        String codeValue="15";
        for(int i=0;i<list.length;i++){
            String codeType = codeValue+StringUtils.apppendPre((i+1) + "", 2, '0');
            String value=list[i].trim();
            sql.append("INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES" +
                    " ('tmsp','"+domainId+"', '"+codeType+"', '"+value+"','"+codeValue+"',"+(i+1)+",'"+value+"', now(), now(), 'T1113','T1113');\n");

        }
        System.out.println(sql.toString());
    }
}
