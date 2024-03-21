package com.str;

public class StringMain {
    public static void main(String[] args) {
        String errorMsg="%s带饭";
        errorMsg=String.format(errorMsg,"测试");
        System.out.println(errorMsg);

        String sql = "(SELECT\n" +
                "\tmain.*,\n" +
                "\tcount( plus2user.user_id ) AS user_count \n" +
                "FROM\n" +
                "\tauth.auth_resource_role_plus main\n" +
                "\tLEFT JOIN auth.auth_resource_role_plus2user plus2user ON main.company_id = plus2user.company_id \n" +
                "\tAND main.name_space_id = plus2user.name_space_id \n" +
                "\tAND main.role_plus_id = plus2user.role_plus_id\n" +
                "\tGROUP BY main.company_id, main.name_space_id, main.role_plus_id)";
        System.out.println(sql);


    }
}
