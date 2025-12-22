package com.str.string;

import lombok.SneakyThrows;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MongonTest {
    private static Map<String,String> map=new HashMap<>();
    static {
        map.put("D012133","D013215");
        map.put("D012138","D013213");
        map.put("D012291","D013183");
        map.put("D012181","D013280");
        map.put("D012238","D013175");
        map.put("D012280","D013151");
        map.put("D012127","D013221");
        map.put("D012215","D013271");
        map.put("D012226","D013232");
        map.put("D012194","D013281");
        map.put("D012195","D013282");
        map.put("D012278","D013187");
        map.put("D012126","D013275");
        map.put("D012197","D013256");
        map.put("D012221","D013173");
        map.put("D012264","D013147");
        map.put("D012235","D013197");
        map.put("D012270","D013236");
        map.put("D012237","D013166");
        map.put("D012188","D013307");
        map.put("D012228","D013229");
        map.put("D012267","D013157");
        map.put("D012209","D013245");
        map.put("D012253","D013277");
        map.put("D012157","D013283");
        map.put("D012256","D013272");
        map.put("D012130","D013218");
        map.put("D012189","D013284");
        map.put("D012245","D013279");
        map.put("D012250","D013200");
        map.put("D012273","D013239");
        map.put("D012240","D013196");
        map.put("D012292","D013184");
        map.put("D012162","D013250");
        map.put("D012168","D013264");
        map.put("D012217","D013274");
        map.put("D012198","D013252");
        map.put("D012295","D013178");
        map.put("D012274","D013188");
        map.put("D012193","D013257");
        map.put("D012236","D013168");
        map.put("D012218","D013172");
        map.put("D012231","D013169");
        map.put("D012252","D013202");
        map.put("D012184","D013306");
        map.put("D012202","D013246");
        map.put("D012169","D013293");
        map.put("D012175","D013248");
        map.put("D012287","D013210");
        map.put("D012155","D013289");
        map.put("D012147","D013268");
        map.put("D012178","D013298");
        map.put("D012187","D013266");
        map.put("D012283","D013148");
        map.put("D012244","D013171");
        map.put("D012206","D013193");
        map.put("D012288","D013209");
        map.put("D012266","D013177");
        map.put("D012224","D013190");
        map.put("D012203","D013278");
        map.put("D012134","D013214");
        map.put("D012142","D013226");
        map.put("D012289","D013182");
        map.put("D012192","D013259");
        map.put("D012154","D013288");
        map.put("D012242","D013233");
        map.put("D012131","D013217");
        map.put("D012239","D013165");
        map.put("D012277","D013174");
        map.put("D012210","D013244");
        map.put("D012213","D013207");
        map.put("D012276","D013189");
        map.put("D012259","D013167");
        map.put("D012176","D013286");
        map.put("D012214","D013242");
        map.put("D012129","D013219");
        map.put("D012258","D013163");
        map.put("D012135","D013212");
        map.put("D012232","D013199");
        map.put("D012249","D013203");
        map.put("D012243","D013234");
        map.put("D012128","D013220");
        map.put("D012230","D013201");
        map.put("D012212","D013243");
        map.put("D012160","D013301");
        map.put("D012234","D013162");
        map.put("D012272","D013237");
        map.put("D012293","D013181");
        map.put("D012185","D013296");
        map.put("D012139","D013205");
        map.put("D012191","D013297");
        map.put("D012163","D013253");
        map.put("D012153","D013287");
        map.put("D012275","D013192");
        map.put("D012222","D013191");
        map.put("D012285","D013149");
        map.put("D012172","D013258");
        map.put("D012123","D013270");
        map.put("D012124","D013231");
        map.put("D012171","D013285");
        map.put("D012296","D013144");
        map.put("D012260","D013158");
        map.put("D012233","D013198");
        map.put("D012132","D013216");
        map.put("D012143","D013224");
        map.put("D012216","D013228");
        map.put("D012207","D013247");
        map.put("D012201","D013262");
        map.put("D012180","D013265");
        map.put("D012186","D013251");
        map.put("D012281","D013152");
        map.put("D012183","D013292");
        map.put("D012173","D013260");
        map.put("D012179","D013254");
        map.put("D012150","D013261");
        map.put("D012200","D013249");
        map.put("D012286","D013180");
        map.put("D012190","D013255");
        map.put("D012144","D013223");
        map.put("D012282","D013150");
        map.put("D012255","D013273");
        map.put("D012225","D013227");
        map.put("D012294","D013179");
        map.put("D011124","D013179");
        map.put("D012161","D013267");
        map.put("D012262","D013176");
        map.put("D012158","D013295");
        map.put("D012268","D013153");
        map.put("D012257","D013276");
        map.put("D012125","D013230");
        map.put("D012229","D013170");
        map.put("D012227","D013241");
        map.put("D012159","D013235");
        map.put("D012145","D013206");
        map.put("D012156","D013290");
        map.put("D012271","D013238");
        map.put("D012251","D013304");
        map.put("D012220","D013186");
        map.put("D012164","D013302");
        map.put("D012140","D013269");
        map.put("D012196","D013294");
        map.put("D012174","D013305");
        map.put("D012269","D013160");
        map.put("D012246","D013204");
        map.put("D012265","D013154");
        map.put("D012177","D013299");
        map.put("D012182","D013303");
        map.put("D012137","D013211");
        map.put("D012241","D013161");
        map.put("D012211","D013291");
        map.put("D012165","D013300");
        map.put("D012146","D013225");
        map.put("D012279","D013185");
    }



    @SneakyThrows
    public static void main(String[] args) {
        String filePath="C:/Users/yansunling/Desktop/gis.gis_region_region_info.json";
        String newPath="C:/Users/yansunling/Desktop/gis.gis_region_region_info_new.json";
        String json = FileUtil.readAsString(new File(filePath));

        Set<String> keySet = map.keySet();
        for(String key:keySet){
            String value = map.get(key);
            json=json.replace(key,value);

        }
        FileUtil.writeAsString(new File(newPath),json);


    }
}
