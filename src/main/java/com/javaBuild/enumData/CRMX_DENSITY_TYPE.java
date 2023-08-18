package com.javaBuild.enumData;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;


@CJ_domain(name = "密度区间")
public enum CRMX_DENSITY_TYPE {

    DENSITY_TYPE_1("1","0-77","13以上"),
    DENSITY_TYPE_2("2","77-91","11-13"),
    DENSITY_TYPE_3("3","91-111","9-11"),
    DENSITY_TYPE_4("4","111-142","7-9"),
    DENSITY_TYPE_5("5","142-200","5-7"),
    DENSITY_TYPE_6("6","200-250","4-5"),
    DENSITY_TYPE_7("7","250-334","3-4"),
    DENSITY_TYPE_8("8","334-500","2-3"),
    DENSITY_TYPE_9("9","500-1000","1-2"),
    DENSITY_TYPE_10("10","1000-999999","0-1"),


    ;

    @EnumValue
    private String code_type;
    private String density_name;
    private String bubble_name;




    CRMX_DENSITY_TYPE(String code_type, String density_name, String bubble_name){
        this.code_type = code_type;
        this.density_name = density_name;
        this.bubble_name = bubble_name;


    }
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.bubble_name;
    }

    public String densityName() {
        return density_name;
    }

    public String bubbleName() {
        return bubble_name;
    }


    public static void main(String[] args) {
        CRMX_DENSITY_TYPE[] values = CRMX_DENSITY_TYPE.values();
        StringBuffer sb=new StringBuffer();
        String column="main.last_month_bubble";
        sb.append("if("+column+" is not null,(case ");

        for(int i=values.length-1;i>=0;i--) {
            String bubbleName = values[i].bubble_name;
            System.out.println(bubbleName);
            String[] split = bubbleName.split("-");
            if (split.length == 2) {
                sb.append(" when  "+column+">=").append(split[0])
                        .append(" and  "+column+"<").append(split[1]).append(" then '" + bubbleName + "' \n");
            } else {
                sb.append(" else '" + bubbleName + "' end ");
            }
        }
        sb.append("),'')");
        System.out.println(sb.toString());
    }
}
