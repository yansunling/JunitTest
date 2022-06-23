package com.word;

public class CreateSqlByNumber {
    public static void main(String[] args) {

        for(int i=1;i<7;i++){
            String sql="INSERT INTO crm.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES ('rank_range', '"+i+"', '"+i+"月内', now(), now(), 'T1113');";
            System.out.println(sql);
        }
    }
}
