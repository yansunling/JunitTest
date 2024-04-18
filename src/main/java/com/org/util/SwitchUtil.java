package com.org.util;

import com.excel.CJExcelUtil;
import com.org.data.OrgData;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SwitchUtil {


    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public  void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public void init(){
        System.out.println("333");
    }

    private static Map<String,String> oldMap=new HashMap<>();

    private static Map<String,String> newMap=new HashMap<>();

    static {
        oldMap.put("总裁","2501");
        oldMap.put("杭州大区","25010902");
        oldMap.put("杭州大区办公室","2501090205");
        oldMap.put("杭州营销二组","250109020102");
        oldMap.put("杭州营销六组","250109020106");
        oldMap.put("杭州营销三组","250109020103");
        oldMap.put("杭州营销四组","250109020104");
        oldMap.put("杭州营销五组","250109020105");
        oldMap.put("杭州营销一组","250109020101");
        oldMap.put("杭州营销中心","2501090201");
        oldMap.put("杭州运输组","2501090204");
        oldMap.put("杭州北转运中心","250109020301");
        oldMap.put("杭州铁路乌兰装卸组","25010902030105");
        oldMap.put("杭州铁路昆贵装卸组","25010902030106");
        oldMap.put("杭州转运中心","2501090203");
        oldMap.put("嘉兴东转运场","250109020303");
        oldMap.put("开放市场货物调度组","25010908");
        oldMap.put("开放市场委员会","250109");
        oldMap.put("开放市场委员会办公室","25010905");
        oldMap.put("开放市场业务开发二组","25010907");
        oldMap.put("开放市场业务开发一组","25010906");
        oldMap.put("上海大区","25010901");
        oldMap.put("上海大区办公室","2501090101");
        oldMap.put("上海嘉定营业部","250109010303");
        oldMap.put("上海闵行营业部","250109010301");
        oldMap.put("上海网点销售部","2501090103");
        oldMap.put("上海营销二组","250109010202");
        oldMap.put("上海营销三组","250109010203");
        oldMap.put("上海营销一组","250109010201");
        oldMap.put("上海营销中心","2501090102");
        oldMap.put("上海运输组","2501090105");
        oldMap.put("北郊转运中心","250109010401");
        oldMap.put("上海铁路昆明装卸组","25010901040101");
        oldMap.put("上海铁路贵阳装卸组","25010901040102");
        oldMap.put("上海铁路乌市装卸组","25010901040103");
        oldMap.put("南翔转运场","250109010402");
        oldMap.put("上海转运中心","2501090104");
        oldMap.put("常州营销中心","2501090307");
        oldMap.put("常州营销组","250109030701");
        oldMap.put("丹阳营销组","250109030703");
        oldMap.put("泰州营销组","250109030702");
        oldMap.put("苏南大区","25010903");
        oldMap.put("苏南大区办公室","2501090301");
        oldMap.put("苏南运输组","2501090305");
        oldMap.put("常州转运场","250109030404");
        oldMap.put("苏南转运中心","2501090304");
        oldMap.put("苏州转运场","250109030401");
        oldMap.put("无锡转运场","250109030403");
        oldMap.put("苏州营销组","250109030203");
        oldMap.put("无锡营销二组","250109030201");
        oldMap.put("无锡营销一组","250109030204");
        oldMap.put("无锡营销中心","2501090302");
        oldMap.put("镇江丹阳营业部","2501090306");
        oldMap.put("慈溪白沙营业部","250109040508");
        oldMap.put("慈溪匡堰营业部","250109040503");
        oldMap.put("慈溪胜山营业部","250109040501");
        oldMap.put("宁波北营业部","250109040502");
        oldMap.put("宁波营销二组","250109040510");
        oldMap.put("宁波营销三组","250109040511");
        oldMap.put("宁波营销一组","250109040509");
        oldMap.put("宁波营销中心","2501090405");
        oldMap.put("宁波镇海营业部","250109040507");
        oldMap.put("余姚营销组","250109040505");
        oldMap.put("宁波北转运场","250109040601");
        oldMap.put("宁波汽运转运场","250109040603");
        oldMap.put("宁波转运中心","2501090406");
        oldMap.put("余姚西转运场","250109040602");
        oldMap.put("北八区收货点","250109040309");
        oldMap.put("北二区收货点","250109040307");
        oldMap.put("北五区收货点","250109040308");
        oldMap.put("东升路收货点","250109040305");
        oldMap.put("建材市场收货点","250109040310");
        oldMap.put("柯东收货点","250109040304");
        oldMap.put("南区收货点","250109040306");
        oldMap.put("绍兴市场网点管理部","2501090403");
        oldMap.put("绍兴线路管理部","2501090407");
        oldMap.put("绍兴线路管理二组","250109040702");
        oldMap.put("绍兴线路管理三组","250109040703");
        oldMap.put("绍兴线路管理四组","250109040704");
        oldMap.put("甘肃兰州卸货点","25010904070401");
        oldMap.put("甘肃临夏卸货点","25010904070402");
        oldMap.put("宁夏银川卸货点","25010904070403");
        oldMap.put("青海西宁卸货点","25010904070404");
        oldMap.put("绍兴线路管理五组","250109040705");
        oldMap.put("绍兴线路管理一组","250109040701");
        oldMap.put("广西南宁卸货点","25010904070101");
        oldMap.put("广西柳州卸货点","25010904070102");
        oldMap.put("广西桂林卸货点","25010904070103");
        oldMap.put("广西宾阳卸货点","25010904070104");
        oldMap.put("广西凭祥卸货点","25010904070105");
        oldMap.put("广西东兴卸货点","25010904070106");
        oldMap.put("广西玉林卸货点","25010904070107");
        oldMap.put("广西龙邦卸货点","25010904070108");
        oldMap.put("广西垌中卸货点","25010904070109");
        oldMap.put("广西西贡卸货点","25010904070110");
        oldMap.put("绍兴袍江营业部","250109040202");
        oldMap.put("绍兴嵊州营业部","250109040201");
        oldMap.put("绍兴营销二组","250109040206");
        oldMap.put("绍兴营销一组","250109040205");
        oldMap.put("绍兴营销中心","2501090402");
        oldMap.put("萧山传化营业部","250109040207");
        oldMap.put("萧山党山营业部","250109040203");
        oldMap.put("柯东汽运转运场","250109040402");
        oldMap.put("柯南汽运转运场","250109040405");
        oldMap.put("柯西汽运转运场","250109040403");
        oldMap.put("钱清转运场","250109040401");
        oldMap.put("绍兴运输组","250109040404");
        oldMap.put("绍兴转运中心","2501090404");
        oldMap.put("甬绍大区","25010904");
        oldMap.put("甬绍大区办公室","2501090401");
        oldMap.put("大客户项目部","250113");
        oldMap.put("大客户项目部办公室","25011301");
        oldMap.put("大客户营销中心","25011302");
        oldMap.put("杭州大客户营销部","2501130203");
        oldMap.put("江苏大客户营销部","2501130202");
        oldMap.put("上海大客户营销部","2501130201");
        oldMap.put("大客户运作中心","25011303");
        oldMap.put("杭州大客户运作部","2501130303");
        oldMap.put("杭州大客户运作二组","250113030302");
        oldMap.put("杭州大客户运作一组","250113030301");
        oldMap.put("江苏大客户运作部","2501130302");
        oldMap.put("上海大客户现场组","250113030101");
        oldMap.put("上海大客户运作部","2501130301");
        oldMap.put("上海大客户运作二组","250113030103");
        oldMap.put("上海大客户运作一组","250113030102");
        oldMap.put("资源采购中心","25011304");
        oldMap.put("甘藏大区","25011102");
        oldMap.put("拉萨西转运场","2501110201");
        oldMap.put("兰州东转运场","2501110202");
        oldMap.put("西北市场委员会","250111");
        oldMap.put("西北市场委员会办公室","25011103");
        oldMap.put("新疆事业部","25011101");
        oldMap.put("新疆事业部办公室","2501110103");
        oldMap.put("恒通分拨点","250111010504");
        oldMap.put("南郊分拨场","250111010502");
        oldMap.put("南郊分拨场装卸组","25011101050201");
        oldMap.put("南郊分拨场运输组","25011101050202");
        oldMap.put("派送网点管理部","250111010503");
        oldMap.put("乌东分拨场","250111010501");
        oldMap.put("乌东分拨场运输组","25011101050104");
        oldMap.put("乌东分拨场装卸组","25011101050106");
        oldMap.put("乌东分拨场装卸一组","2501110105010601");
        oldMap.put("乌东分拨场装卸二组","2501110105010602");
        oldMap.put("乌东分拨场装卸三组","2501110105010603");
        oldMap.put("新疆事业部分拨中心","2501110105");
        oldMap.put("长胜分拨点","250111010505");
        oldMap.put("乌市经营二部","250111010402");
        oldMap.put("乌市义乌市场组","25011101040201");
        oldMap.put("乌市台州市场组","25011101040202");
        oldMap.put("乌市温州市场组","25011101040203");
        oldMap.put("乌市经营三部","250111010403");
        oldMap.put("乌市苏中市场组","25011101040301");
        oldMap.put("乌市湖州市场组","25011101040303");
        oldMap.put("乌市经营四部","250111010404");
        oldMap.put("乌市经营一部","250111010401");
        oldMap.put("乌市小型专业市场组","25011101040101");
        oldMap.put("乌市外贸市场组","25011101040102");
        oldMap.put("乌市二线市场组","25011101040103");
        oldMap.put("新疆事业部营销中心","2501110104");
        oldMap.put("贵州大区","25011007");
        oldMap.put("贵州大区办公室","2501100703");
        oldMap.put("改貌分拨场","250110070201");
        oldMap.put("贵州铁路装卸一组","25011007020101");
        oldMap.put("贵州铁路装卸二组","25011007020102");
        oldMap.put("西南商贸城分拨点","25011007020103");
        oldMap.put("贵州大区客服组","250110070202");
        oldMap.put("贵州大区运输组","250110070203");
        oldMap.put("贵州大区外发组","25011007020301");
        oldMap.put("贵州大区配送组","25011007020302");
        oldMap.put("贵州分拨中心","2501100702");
        oldMap.put("贵州营销二部","250110070102");
        oldMap.put("六盘水营业部","25011007010201");
        oldMap.put("毕节营业部","25011007010202");
        oldMap.put("兴义营业部","25011007010203");
        oldMap.put("遵义营业部","25011007010204");
        oldMap.put("凯里营业部","25011007010205");
        oldMap.put("安顺营业部","25011007010206");
        oldMap.put("贵州营销一部","250110070101");
        oldMap.put("贵阳营销一组","25011007010101");
        oldMap.put("贵阳营销二组","25011007010102");
        oldMap.put("贵阳营销三组","25011007010103");
        oldMap.put("贵州营销中心","2501100701");
        oldMap.put("西南市场委员会","250110");
        oldMap.put("西南市场委员会办公室","25011005");
        oldMap.put("云南公路大区","25011002");
        oldMap.put("云南公路大区办公室","2501100201");
        oldMap.put("跑马山分拨场","250110020601");
        oldMap.put("云南公路分拨中心","2501100206");
        oldMap.put("云南公路客服组","250110020603");
        oldMap.put("云南公路运输组","250110020602");
        oldMap.put("大理营业部","250110020706");
        oldMap.put("曲靖营业部","250110020707");
        oldMap.put("云南公路大区营销二组","250110020702");
        oldMap.put("云南公路大区营销六组","250110020708");
        oldMap.put("云南公路大区营销三组","250110020703");
        oldMap.put("云南公路大区营销四组","250110020704");
        oldMap.put("云南公路大区营销五组","250110020705");
        oldMap.put("云南公路大区营销一组","250110020701");
        oldMap.put("云南公路营销中心","2501100207");
        oldMap.put("云南铁路大区","25011001");
        oldMap.put("云南铁路大区办公室","2501100101");
        oldMap.put("金马村分拨场","250110010301");
        oldMap.put("云南铁路上海装卸组","25011001030101");
        oldMap.put("云南铁路义乌装卸组","25011001030102");
        oldMap.put("云南铁路杭州装卸组","25011001030103");
        oldMap.put("云南铁路甬绍装卸组","25011001030104");
        oldMap.put("王家营分拨场","250110010302");
        oldMap.put("云南铁路分拨中心","2501100103");
        oldMap.put("云南铁路运输组","250110010303");
        oldMap.put("云南铁路外发组","25011001030301");
        oldMap.put("云南铁路配送组","25011001030302");
        oldMap.put("云南铁路大区营销二组","250110010202");
        oldMap.put("云南铁路大区营销三组","250110010203");
        oldMap.put("云南铁路大区营销一组","250110010201");
        oldMap.put("云南铁路营销中心","2501100102");
        oldMap.put("湖州大区","25010803");
        oldMap.put("湖州大区办公室","2501080301");
        oldMap.put("湖州网点管理部","2501080304");
        oldMap.put("湖州西营销组","250108030407");
        oldMap.put("湖州营销二组","250108030409");
        oldMap.put("湖州营销一组","250108030408");
        oldMap.put("嘉兴桐乡营业部","250108030402");
        oldMap.put("嘉兴桐乡营业部（乌市）","250108030406");
        oldMap.put("嘉兴许村营业部","250108030403");
        oldMap.put("湖州营销中心","2501080302");
        oldMap.put("湖州西转运场","250108030304");
        oldMap.put("湖州运输组","250108030303");
        oldMap.put("湖州转运中心","2501080303");
        oldMap.put("织里转运场","250108030301");
        oldMap.put("金华特区","25010805");
        oldMap.put("金华营销中心","2501080501");
        oldMap.put("金华营销组","250108050101");
        oldMap.put("金华南转运场","250108050201");
        oldMap.put("金华转运中心","2501080502");
        oldMap.put("苏中大区","25010804");
        oldMap.put("苏中大区办公室","2501080401");
        oldMap.put("南通营销二组","250108040902");
        oldMap.put("南通营销三组","250108040903");
        oldMap.put("南通营销一组","250108040901");
        oldMap.put("苏中营销中心","2501080409");
        oldMap.put("苏州常熟营业部","250108040904");
        oldMap.put("苏州常熟营业二部","25010804090401");
        oldMap.put("叠石桥转运场","250108041003");
        oldMap.put("苏中运输组","250108041002");
        oldMap.put("苏中转运中心","2501080410");
        oldMap.put("通州转运场","250108041001");
        oldMap.put("台州大区","25010802");
        oldMap.put("台州大区办公室","2501080201");
        oldMap.put("台州十里铺营业部","250108020210");
        oldMap.put("台州营销二组","250108020211");
        oldMap.put("台州营销三组","250108020212");
        oldMap.put("台州营销一组","250108020207");
        oldMap.put("台州营销中心","2501080202");
        oldMap.put("台州玉环营业部","250108020209");
        oldMap.put("台州康洋营业部","250108020404");
        oldMap.put("台州康洋营业部装卸组","25010802040401");
        oldMap.put("台州跃鑫营业部","250108020405");
        oldMap.put("台州运输组","250108020403");
        oldMap.put("台州转运中心","2501080204");
        oldMap.put("通泰转运场","250108020401");
        oldMap.put("通泰装卸一组","25010802040101");
        oldMap.put("通泰装卸二组","25010802040102");
        oldMap.put("永源转运场","250108020402");
        oldMap.put("温州大区","25010807");
        oldMap.put("温州大区办公室","2501080701");
        oldMap.put("温州柳市营业部","250108070205");
        oldMap.put("温州潘桥营业部","250108070202");
        oldMap.put("温州塘下营业部","250108070208");
        oldMap.put("温州营销二组","250108070206");
        oldMap.put("温州营销三组","250108070207");
        oldMap.put("温州营销一组","250108070201");
        oldMap.put("温州营销中心","2501080702");
        oldMap.put("温州永强营业部","250108070209");
        oldMap.put("温州西转运场","250108070301");
        oldMap.put("温州转运中心","2501080703");
        oldMap.put("义乌大区","25010801");
        oldMap.put("义乌大区办公室","2501080101");
        oldMap.put("义乌北下朱营业部","250108010401");
        oldMap.put("义乌公路港营业部","250108010404");
        oldMap.put("义乌青口营业部","250108010402");
        oldMap.put("义乌青口装卸组","25010801040201");
        oldMap.put("义乌网点管理部","2501080104");
        oldMap.put("金华永康营业部","250108010202");
        oldMap.put("义乌营销二组","250108010205");
        oldMap.put("义乌营销一组","250108010201");
        oldMap.put("义乌营销中心","2501080102");
        oldMap.put("诸暨大唐营业部","250108010203");
        oldMap.put("诸暨店口营业部","250108010204");
        oldMap.put("义乌运输组","2501080106");
        oldMap.put("义乌西转运中心","250108010301");
        oldMap.put("义乌西铁路白班装卸一组","25010801030101");
        oldMap.put("义乌西铁路白班装卸二组","25010801030102");
        oldMap.put("义乌西铁路晚班装卸组","25010801030103");
        oldMap.put("义乌转运中心","2501080103");
        oldMap.put("永康东转运场","250108010302");
        oldMap.put("诸暨店口营业部（乌市）","2501080107");
        oldMap.put("专业市场委员会","250108");
        oldMap.put("专业市场委员会办公室","25010806");
        oldMap.put("国际联运部","250112");
        oldMap.put("杭州四季青营业部","2501020303");
        oldMap.put("湖州安吉营业部","2501020308");
        oldMap.put("湖州南浔营业部","2501020307");
        oldMap.put("嘉兴丁桥营业部","2501020301");
        oldMap.put("嘉兴王店营业部","2501020302");
        oldMap.put("苏州盛泽营业部","2501020306");

        newMap.put("总裁","3501");
        newMap.put("杭绍甬大区","350121");
        newMap.put("杭绍甬大区办公室","35012101");
        newMap.put("杭州营销二组","3501210203");
        newMap.put("杭州营销六组","3501210205");
        newMap.put("杭州营销三组","3501210202");
        newMap.put("杭州营销四组","3501210206");
        newMap.put("杭州营销五组","3501210204");
        newMap.put("杭州营销一组","3501210201");
        newMap.put("杭州营销中心","35012102");
        newMap.put("杭州北转运场","3501210301");
        newMap.put("杭州北转运场","3501210301");
        newMap.put("杭州铁路乌兰作业组","350121030101");
        newMap.put("杭州铁路昆贵作业组","350121030102");
        newMap.put("杭州营运中心","35012103");
        newMap.put("嘉兴东转运场","3501220502");
        newMap.put("杭绍甬大区","350121");
        newMap.put("杭绍甬大区","350121");
        newMap.put("杭绍甬大区办公室","35012101");
        newMap.put("杭绍甬大区","350121");
        newMap.put("杭绍甬大区","350121");
        newMap.put("沪苏大区","350123");
        newMap.put("沪苏大区办公室","35012301");
        newMap.put("上海北方营销组","350123020201");
        newMap.put("上海南方营销组","350123020202");
        newMap.put("上海南方营销组","350123020202");
        newMap.put("上海线路管理一组","350123020204");
        newMap.put("上海北方营销组","350123020201");
        newMap.put("上海北方营销组","350123020201");
        newMap.put("上海营销管理部","3501230202");
        newMap.put("上海北郊转运场","3501230301");
        newMap.put("上海北郊转运场","3501230301");
        newMap.put("上海铁路昆明作业组","350123030102");
        newMap.put("上海铁路贵阳作业组","350123030103");
        newMap.put("上海铁路乌市作业组","350123030101");
        newMap.put("沪苏大区","350123");
        newMap.put("上海北郊转运场","3501230301");
        newMap.put("常州营销组","350123020801");
        newMap.put("常州营销组","350123020801");
        newMap.put("镇江丹阳营业部","350123020805");
        newMap.put("泰州营销组","350123020803");
        newMap.put("沪苏大区","350123");
        newMap.put("沪苏大区办公室","35012301");
        newMap.put("无锡南转运场","3501230305");
        newMap.put("常州转运场","3501230304");
        newMap.put("沪苏营运中心","35012303");
        newMap.put("苏州西转运场","3501230309");
        newMap.put("无锡南转运场","3501230305");
        newMap.put("苏州营销组","350123020804");
        newMap.put("无锡营销组","350123020802");
        newMap.put("无锡营销组","350123020802");
        newMap.put("无锡营销组","350123020802");
        newMap.put("镇江丹阳营业部","350123020805");
        newMap.put("慈溪白沙营业部","3501210404");
        newMap.put("慈溪匡堰营业部","3501210403");
        newMap.put("慈溪胜山营业部","3501210405");
        newMap.put("宁波线路管理一组","3501210502");
        newMap.put("宁波线路管理三组","3501210503");
        newMap.put("宁波线路管理二组","3501210504");
        newMap.put("慈溪营销组","3501210406");
        newMap.put("宁波营销中心","35012104");
        newMap.put("杭绍甬大区","350121");
        newMap.put("余姚线路管理一组","3501210501");
        newMap.put("宁波北转运场","3501210602");
        newMap.put("宁波汽运转运场","3501210603");
        newMap.put("宁波营运中心","35012106");
        newMap.put("余姚西转运场","3501210601");
        newMap.put("北八区收货点","3501210704");
        newMap.put("北二区收货点","3501210702");
        newMap.put("北五区收货点","3501210703");
        newMap.put("东升路收货点","3501210701");
        newMap.put("建材市场收货点","3501210706");
        newMap.put("柯东收货点","3501210707");
        newMap.put("南区收货点","3501210705");
        newMap.put("绍兴市场网点管理部","35012107");
        newMap.put("绍兴线路管理部","35012108");
        newMap.put("绍兴线路管理二组","3501210803");
        newMap.put("绍兴线路管理三组","3501210802");
        newMap.put("绍兴线路管理四组","3501210805");
        newMap.put("甘肃兰州卸货点","350121080503");
        newMap.put("甘肃临夏卸货点","350121080502");
        newMap.put("宁夏银川卸货点","350121080501");
        newMap.put("青海西宁卸货点","350121080504");
        newMap.put("绍兴线路管理五组","3501210804");
        newMap.put("绍兴线路管理一组","3501210801");
        newMap.put("广西南宁卸货点","350121080103");
        newMap.put("广西柳州卸货点","350121080106");
        newMap.put("广西桂林卸货点","350121080107");
        newMap.put("广西宾阳卸货点","350121080105");
        newMap.put("广西凭祥卸货点","350121080102");
        newMap.put("广西东兴卸货点","350121080101");
        newMap.put("广西玉林卸货点","350121080109");
        newMap.put("广西爱店卸货点","350121080108");
        newMap.put("广西垌中卸货点","350121080104");
        newMap.put("广西西贡卸货点","350121080110");
        newMap.put("绍兴袍江营业部","3501210904");
        newMap.put("绍兴嵊州营业部","3501210901");
        newMap.put("绍兴营销二组","3501210903");
        newMap.put("绍兴营销一组","3501210902");
        newMap.put("绍兴营销中心","35012109");
        newMap.put("杭绍甬大区","350121");
        newMap.put("萧山党山营业部","3501210905");
        newMap.put("柯东汽运转运场","3501211001");
        newMap.put("柯南汽运转运场","3501211002");
        newMap.put("柯西汽运转运场","3501211003");
        newMap.put("钱清转运场","3501211005");
        newMap.put("绍兴运输组","3501211004");
        newMap.put("绍兴营运中心","35012110");
        newMap.put("杭绍甬大区","350121");
        newMap.put("杭绍甬大区办公室","35012101");
        newMap.put("沪苏KA管理部","35012304");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏KA管理部","35012304");
        newMap.put("杭州营销四组","3501210206");
        newMap.put("江苏项目开发组","3501230406");
        newMap.put("上海项目开发组","3501230403");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("杭州营销四组","3501210206");
        newMap.put("杭州营销四组","3501210206");
        newMap.put("杭州营销四组","3501210206");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏项目运作组","3501230407");
        newMap.put("沪苏大区","350123");
        newMap.put("甘藏营运中心","35015004");
        newMap.put("拉萨西分拨场","3501500402");
        newMap.put("兰州东分拨场","3501500401");
        newMap.put("新疆大区","350150");
        newMap.put("新疆大区办公室","35015001");
        newMap.put("新疆大区","350150");
        newMap.put("新疆大区办公室","35015001");
        newMap.put("恒通分拨点","350150020101");
        newMap.put("南郊分拨场","3501500202");
        newMap.put("南郊分拨场","3501500202");
        newMap.put("南郊分拨场","3501500202");
        newMap.put("新疆大区","350150");
        newMap.put("乌东分拨场","3501500201");
        newMap.put("新疆运输组","3501500203");
        newMap.put("乌东分拨场","3501500201");
        newMap.put("新疆铁路作业一组","350150020102");
        newMap.put("新疆铁路作业二组","350150020104");
        newMap.put("新疆铁路作业三组","350150020103");
        newMap.put("新疆营运中心","35015002");
        newMap.put("长胜分拨点","350150020105");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("湖嘉线路管理三组","350122060203");
        newMap.put("南通线路管理三组","35012405");
        newMap.put("湖嘉线路管理三组","350122060203");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("新疆营销组","35015003");
        newMap.put("新疆营销组","35015003");
        newMap.put("新疆营销组","35015003");
        newMap.put("新疆营销组","35015003");
        newMap.put("新疆营销组","35015003");
        newMap.put("贵州大区","350152");
        newMap.put("贵州大区办公室","35015201");
        newMap.put("改貌分拨场","3501520201");
        newMap.put("贵州铁路作业一组","350152020102");
        newMap.put("贵州铁路作业二组","350152020103");
        newMap.put("西南商贸城分拨点","350152020101");
        newMap.put("贵州客服组","3501520101");
        newMap.put("贵州运输组","3501520202");
        newMap.put("贵州中转组","350152020204");
        newMap.put("贵州配送组","350152020205");
        newMap.put("贵州营运中心","35015202");
        newMap.put("贵州营销中心","35015203");
        newMap.put("贵州六盘水营业部","3501520301");
        newMap.put("贵州毕节营业部","3501520305");
        newMap.put("贵州兴义营业部","3501520302");
        newMap.put("贵州遵义营业部","3501520307");
        newMap.put("贵州凯里营业部","3501520303");
        newMap.put("贵州安顺营业部","3501520304");
        newMap.put("贵州营销中心","35015203");
        newMap.put("金温台线路管理二部","35012007");
        newMap.put("南通线路管理二组","35012406");
        newMap.put("湖嘉线路管理二组","350122060202");
        newMap.put("贵州营销中心","35015203");
        newMap.put("沪苏大区","350123");
        newMap.put("沪苏大区","350123");
        newMap.put("云南大区","350151");
        newMap.put("云南大区办公室","35015101");
        newMap.put("跑马山作业组","350151020205");
        newMap.put("跑马山作业组","350151020205");
        newMap.put("云南客服组","3501510101");
        newMap.put("云南运输组","3501510201");
        newMap.put("云南大理营业部","3501510301");
        newMap.put("云南曲靖营业部","3501510302");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("云南营销组","3501510303");
        newMap.put("南通线路管理一组","35012407");
        newMap.put("南通线路管理一组","35012407");
        newMap.put("湖嘉线路管理一组","350122060201");
        newMap.put("湖嘉线路管理一组","350122060201");
        newMap.put("云南营销中心","35015103");
        newMap.put("云南大区","350151");
        newMap.put("云南大区办公室","35015101");
        newMap.put("金马村分拨场","3501510202");
        newMap.put("云南铁路沪苏作业组","350151020203");
        newMap.put("云南铁路义乌作业组","350151020201");
        newMap.put("云南铁路杭州作业组","350151020202");
        newMap.put("云南铁路甬绍作业组","350151020204");
        newMap.put("云南大区","350151");
        newMap.put("金马村分拨场","3501510202");
        newMap.put("云南运输组","3501510201");
        newMap.put("云南中转组","350151020106");
        newMap.put("云南配送组","350151020107");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("南通线路管理一组","35012407");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("云南营销中心","35015103");
        newMap.put("湖嘉大区","350122");
        newMap.put("湖嘉大区办公室","35012201");
        newMap.put("湖州织里营业区","35012202");
        newMap.put("湖嘉营销一组","350122060101");
        newMap.put("湖嘉线路管理三组","350122060203");
        newMap.put("湖嘉营销三组","350122060103");
        newMap.put("嘉兴桐乡营业部","3501220301");
        newMap.put("嘉兴桐乡（乌市）营业部","3501220302");
        newMap.put("嘉兴许村营业部","3501220303");
        newMap.put("湖嘉营销中心","35012206");
        newMap.put("湖州西转运场","3501220402");
        newMap.put("湖州织里转运场","3501220202");
        newMap.put("湖州织里转运场","3501220202");
        newMap.put("湖州织里转运场","3501220202");
        newMap.put("金华南转运场","3501200906");
        newMap.put("金华南转运场","3501200906");
        newMap.put("金华南转运场","3501200906");
        newMap.put("金华南转运场","3501200906");
        newMap.put("金华南转运场","3501200906");
        newMap.put("苏中大区","350124");
        newMap.put("苏中大区","350124");
        newMap.put("南通线路管理二组","35012406");
        newMap.put("南通线路管理三组","35012405");
        newMap.put("南通线路管理一组","35012407");
        newMap.put("苏中大区","350124");
        newMap.put("苏州常熟营业部","35012408");
        newMap.put("苏州常熟营业二部","35012409");
        newMap.put("南通叠石桥转运场","35012410");
        newMap.put("苏中大区","350124");
        newMap.put("苏中大区","350124");
        newMap.put("南通通州转运场","35012411");
        newMap.put("金温台大区","350120");
        newMap.put("金温台大区办公室","35012001");
        newMap.put("台州十里铺营业部","350120020201");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("金温台线路管理二部","35012007");
        newMap.put("金温台线路管理三部","35012008");
        newMap.put("金温台线路管理二部","35012007");
        newMap.put("台州玉环营业部","350120020202");
        newMap.put("台州康洋营业部","350120090302");
        newMap.put("台州康洋作业组","350120090301");
        newMap.put("台州跃鑫营业部","350120090303");
        newMap.put("台州通泰转运场","3501200903");
        newMap.put("台州通泰转运场","3501200903");
        newMap.put("台州通泰转运场","3501200903");
        newMap.put("台州通泰作业一组","350120090304");
        newMap.put("台州通泰作业二组","350120090305");
        newMap.put("台州永源转运场","3501200902");
        newMap.put("金温台大区","350120");
        newMap.put("金温台大区办公室","35012001");
        newMap.put("金温台大区","350120");
        newMap.put("温州网点管理组","3501200203");
        newMap.put("温州塘下营业部","350120020301");
        newMap.put("金温台营销管理部","35012003");
        newMap.put("金温台营销管理部","35012003");
        newMap.put("金温台营销管理部","35012003");
        newMap.put("金温台营销管理部","35012003");
        newMap.put("温州永强营业部","350120020302");
        newMap.put("温州西转运场","3501200905");
        newMap.put("温州西转运场","3501200905");
        newMap.put("金温台大区","350120");
        newMap.put("金温台大区办公室","35012001");
        newMap.put("义乌北下朱营业部","350120020102");
        newMap.put("义乌公路港营业部","350120020101");
        newMap.put("义乌青口营业部","350120020104");
        newMap.put("义乌青口作业组","350120020103");
        newMap.put("义乌市场网点管理组","3501200201");
        newMap.put("金华永康营业部","350120020404");
        newMap.put("金温台线路管理二部","35012007");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("金温台线路管理一部","35012006");
        newMap.put("诸暨大唐营业部","350120020401");
        newMap.put("诸暨店口营业部","350120020402");
        newMap.put("义乌西转运场","3501200901");
        newMap.put("义乌西转运场","3501200901");
        newMap.put("义乌西铁路白班作业一组","350120090102");
        newMap.put("义乌西铁路白班作业二组","350120090103");
        newMap.put("义乌西铁路晚班作业组","350120090101");
        newMap.put("义乌西转运场","3501200901");
        newMap.put("永康东转运场","3501200904");
        newMap.put("诸暨店口（乌市）营业部","350120020403");
        newMap.put("湖嘉大区","350122");
        newMap.put("湖嘉大区","350122");
        newMap.put("湖嘉外贸物流部","3501220603");
        newMap.put("杭州四季青营业部","3501220304");
        newMap.put("湖州安吉营业部","3501220401");
        newMap.put("湖州南浔营业部","3501220201");
        newMap.put("嘉兴丁桥营业部","3501220501");
        newMap.put("嘉兴王店营业部","3501220503");
        newMap.put("苏州盛泽营业部","3501220203");
        newMap.put("金温台线路管理部","35012004");
        newMap.put("金温台外贸物流部","35012005");
        newMap.put("新疆客服组","3501500101");
        newMap.put("金温台营运中心","35012009");
        newMap.put("云南营运中心","35015102");
        newMap.put("苏南营销管理部","3501230208");
        newMap.put("上海外贸物流组","350123020203");
        newMap.put("湖州西营业区","35012204");
        newMap.put("嘉兴东营业区","35012205");
        newMap.put("湖嘉营销管理部","3501220601");
        newMap.put("湖嘉线路管理部","3501220602");
        newMap.put("湖嘉客服组","3501220604");
        newMap.put("宁波线路管理四组","3501210506");
        newMap.put("宁波线路管理五组","3501210505");
        newMap.put("宁波营销组","3501210402");
        newMap.put("余姚营销组","3501210401");
        newMap.put("宁波铁路昆贵作业组","350121060202");
        newMap.put("宁波铁路乌市作业组","350121060201");
        newMap.put("重庆卸货点","350121080405");
        newMap.put("四川成都卸货点","350121080403");
        newMap.put("四川南充卸货点","350121080401");
        newMap.put("四川宜宾卸货点","350121080402");
        newMap.put("四川泸州卸货点","350121080404");
        newMap.put("绍兴铁路昆贵作业组","350121100502");
        newMap.put("绍兴铁路乌市作业组","350121100501");
        newMap.put("湖嘉营销二组","350122060102");
        newMap.put("湖嘉线路管理四组","350122060204");
        newMap.put("湖嘉线路管理五组","350122060205");
        newMap.put("湖嘉线路管理六组","350122060206");
        newMap.put("湖嘉直属营业区","35012203");
        newMap.put("沪苏营销中心","35012302");
        newMap.put("金华网点管理组","3501200204");
        newMap.put("台州网点管理组","3501200202");
        newMap.put("贵州营销组","3501520306");
        newMap.put("宁波线路管理部","35012105");


    }


    @SneakyThrows
    public static List<OrgData> readExcel(String filePath){
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        listResult = listResult.subList(1, listResult.size());

        List<OrgData> importDataList = CJExcelUtil.initImportExcelDatas(OrgData.titleMap, listResult, OrgData.class);
        Map<String,List<OrgData>> mapRef=new HashMap<>();
        for(int i=0;i<importDataList.size();i++){
            OrgData importData=importDataList.get(i);
            if(StringUtils.isBlank(importData.getOldOrgId())){
                continue;
            }
            if(StringUtils.isBlank(importData.getNewOrgId())){
                importData.setNewOrgId(importDataList.get(i-1).getNewOrgId());
            }
            List<OrgData> orgDataList = mapRef.get(importData.getNewOrgId());
            if(CollectionUtil.isEmpty(orgDataList)){
                orgDataList=new ArrayList<>();
            }
            if(StringUtils.isBlank(importData.getNewRegionId())){

                importData.setNewRegionId(newMap.get(importData.getNewRegionName()));
                importData.setNewDistrictId(newMap.get(importData.getNewDistrictName()));
                //设置旧大小区ID
                importData.setOldRegionId(oldMap.get(importData.getOldRegionName()));
                importData.setOldDistrictId(oldMap.get(importData.getOldDistrictName()));

            }
            orgDataList.add(importData);
            mapRef.put(importData.getNewOrgId(),orgDataList);
        }
        List<OrgData> returnList=new ArrayList<>();
        mapRef.forEach((key,oldOrgList)->{
            OrgData orgData=new OrgData();
            BeanUtils.copyProperties(oldOrgList.get(0),orgData);

            List<String> oldOrgIds = oldOrgList.stream().map(OrgData::getOldOrgId).collect(Collectors.toList());
            List<String> oldOrgNames = oldOrgList.stream().map(OrgData::getOldOrgName).collect(Collectors.toList());

            Set<String> oldDistrictIds = oldOrgList.stream().map(OrgData::getOldDistrictId).collect(Collectors.toSet());
            Set<String> oldDistrictNames = oldOrgList.stream().map(OrgData::getOldDistrictName).collect(Collectors.toSet());
            Set<String> oldRegionIds = oldOrgList.stream().map(OrgData::getOldRegionId).collect(Collectors.toSet());
            Set<String> oldRegionNames = oldOrgList.stream().map(OrgData::getOldRegionName).collect(Collectors.toSet());

            orgData.setOldOrgId("'"+StringUtils.join("','",oldOrgIds.toArray())+"'");
            orgData.setOldOrgList("'"+StringUtils.join("|",oldOrgIds.toArray())+"'");
            orgData.setOldSingleOrg("'"+oldOrgIds.get(0)+"'");
            orgData.setOldOrgName("'"+StringUtils.join("','",oldOrgNames.toArray())+"'");
            orgData.setNewOrgId("'"+orgData.getNewOrgId()+"'");
            orgData.setNewOrgName("'"+orgData.getNewOrgName()+"'");
            orgData.setNewRegionId("'"+orgData.getNewRegionId()+"'");
            orgData.setNewDistrictId("'"+orgData.getNewDistrictId()+"'");
            orgData.setNewRegionName("'"+orgData.getNewRegionName()+"'");
            orgData.setNewDistrictName("'"+orgData.getNewDistrictName()+"'");
            orgData.setOldDistrictId("'"+StringUtils.join("','",oldDistrictIds.toArray())+"'");
            orgData.setOldDistrictName("'"+StringUtils.join("','",oldDistrictNames.toArray())+"'");
            orgData.setOldRegionId("'"+StringUtils.join("','",oldRegionIds.toArray())+"'");
            orgData.setOldRegionName("'"+StringUtils.join("','",oldRegionNames.toArray())+"'");

            returnList.add(orgData);
        });
        return returnList;

    }



    public static boolean containsChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                return true;
            }
        }
        return false;
    }
    public static String matchColumn(String column,String newTable,String type,boolean concat){
        if(concat){
            return "update "+newTable+" set "+column+" = CONCAT("+column+",',','<新机构"+type+">') where "+column+"  REGEXP '<老机构"+type+"集合>';";
        }
        String areaRegex = "big_area|region";
        Pattern pattern = Pattern.compile(areaRegex);
        // 创建Matcher对象
        String sql="update "+newTable+" set "+column+" = '<新机构"+type+">' where "+column+" in('<老机构"+type+">');";
        Matcher matcher = pattern.matcher(column);
        if(matcher.find()){
            sql="update "+newTable+" set "+column+" = '<新机构大区"+type+">' where "+column+" in('<老机构大区"+type+">');";
        }
        String smallRegex = "small_area|district_id";
        Pattern smallPattern = Pattern.compile(smallRegex);
        Matcher smallMatcher = smallPattern.matcher(column);
        if(smallMatcher.find()){
            sql="update "+newTable+" set "+column+" = '<新机构小区"+type+">' where "+column+" in('<老机构小区"+type+">');";
        }
        return sql;
    }



    public static boolean deleteFolder(File folder) {
        if (folder == null) {
            return false;
        }
        if (!folder.exists()) {
            return false;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    // 递归删除子文件夹
                    deleteFolder(f);
                } else {
                    // 删除文件
                    f.delete();
                }
            }
        }
        // 删除空文件夹
        return folder.delete();
    }
    public static boolean  matchTables(String input){
        if(input.startsWith("dtcx.s202")||input.startsWith("dctx.s202")){
            return true;
        }
        return  false;
    }

    public static String replaceName(String item,OrgData orgData){
        if(item.indexOf("<老机构名称>")>0&&StringUtils.equals(orgData.getNewOrgName(),orgData.getOldOrgName())){
            return "";
        }
        if(item.indexOf("<老机构大区名字>")>0&&StringUtils.equals(orgData.getNewRegionName(),orgData.getOldRegionName())){
            return "";
        }
        if(item.indexOf("<老机构大区ID>")>0&&StringUtils.equals(orgData.getNewRegionId(),orgData.getOldRegionId())){
            return "";
        }
        if(item.indexOf("<老机构小区ID>")>0&&StringUtils.equals(orgData.getNewDistrictId(),orgData.getOldDistrictId())){
            return "";
        }
        if(item.indexOf("<老机构小区名字>")>0&&StringUtils.equals(orgData.getOldDistrictName(),orgData.getOldDistrictName())){
            return "";
        }
        item=item.replaceAll("'<新机构ID>'",orgData.getNewOrgId());
        item=item.replaceAll("'<老机构ID>'",orgData.getOldOrgId());
        item=item.replaceAll("'<老机构ID单个>'",orgData.getOldSingleOrg());
        item=item.replaceAll("'<老机构ID集合>'",orgData.getOldOrgList());
        item=item.replaceAll("'<新机构名称>'",orgData.getNewOrgName());
        item=item.replaceAll("'<老机构名称>'",orgData.getOldOrgName());
        item=item.replaceAll("'<新机构大区名称>'",orgData.getNewRegionName());
        item=item.replaceAll("'<新机构小区名称>'",orgData.getNewDistrictName());
        item=item.replaceAll("'<新机构小区ID>'",orgData.getNewDistrictId());
        item=item.replaceAll("'<新机构大区ID>'",orgData.getNewRegionId());
        item=item.replaceAll("'<老机构大区ID>'",orgData.getOldRegionId());
        item=item.replaceAll("'<老机构大区名称>'",orgData.getOldOrgName());
        item=item.replaceAll("'<老机构小区ID>'",orgData.getOldDistrictId());
        item=item.replaceAll("'<老机构小区名称>'",orgData.getOldDistrictName());
        if(item.indexOf("<老机构大区ID单个>")>0){
            String oldRegionId = orgData.getOldRegionId();
            String[] oldRegions = oldRegionId.split(",");
            item=item.replaceAll("'<老机构大区ID单个>'",oldRegions[0]);
        }
        if(item.indexOf("<替换老机构ID集合>")>0){
            String oldOrg = orgData.getOldOrgId();
            String[] strs = oldOrg.split(",");
            StringBuffer sb=new StringBuffer();
            for(String str:strs){
                sb.append(item.replaceAll("'<替换老机构ID集合>'",str)).append("\n");
            }
            item=sb.toString();
        }
        if(item.indexOf("<替换老机构名称集合>")>0){
            String oldOrg = orgData.getOldOrgName();
            String[] strs = oldOrg.split(",");
            StringBuffer sb=new StringBuffer();
            for(String str:strs){
                sb.append(item.replaceAll("'<替换老机构名称集合>'",str)).append("\n");
            }
            item=sb.toString();
        }

        return item;

    }











}
