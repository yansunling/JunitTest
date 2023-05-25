package com.javaBuild.constant;

import com.word.constansts.CRMX_DENSITY_TYPE;

public class DenistyTypeTest {
    public static void main(String[] args) {
        CRMX_DENSITY_TYPE[] values = CRMX_DENSITY_TYPE.values();
        StringBuffer sb=new StringBuffer();
        for(CRMX_DENSITY_TYPE value:values){
            String densityName = value.densityName();
            String[] split = densityName.split("-");
            if(split.length==2){
                sb.append(" when t.goods_weight/t.goods_cube>="+split[0]+" and t.goods_weight/t.goods_cube<"+split[1]+" then '"+value.bubbleName()+"' \n");
            }
        }
        System.out.println(sb.toString());

    }
}
