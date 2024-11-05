package com.javaBuild.constant;

public class CreateSqlByNumber {
    public static void main(String[] args) {

        for(int i=0;i<10;i++){
            String sql="INSERT INTO crm.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES ('sale_rate', '"+i*10+"', '"+i*10+"%', now(), now(), 'T1113');";
            System.out.println(sql);
        }
    }
}
