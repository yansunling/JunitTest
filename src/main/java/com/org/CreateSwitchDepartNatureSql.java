package com.org;


import com.excel.CJExcelUtil;
import com.org.data.PositionData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchDepartNatureSql implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }


    @Test
    public void test() throws Exception {
        String filePath = "C:/Users/yansunling/Desktop/depart_nature.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        Set<String> fileList=new LinkedHashSet<>();

        Map<String,String> map=new HashMap<>();
        map.put("职能","1");
        map.put("出发","2");
        map.put("出发所辖职能","3");
        map.put("到达","4");
        map.put("到达所辖职能","5");
        map.put("综合","6");

        Map<String, String> newMap = SwitchUtil.newMap;
        for(Object[] objects:listResult){
            if(objects[0]!=null){
                String departNature=map.get(objects[1]);
                String orgId=newMap.get(objects[0]);
                fileList.add("update hcm.hcm_org_info set depart_nature='"+departNature+"' where org_id='"+orgId+"';");
            }
        }

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm_depart_nature.sql");
        FileUtils.writeLines(allFile,"utf-8",fileList);


    }

    private Map<String,String> getPositionId(){
        Map<String,String> position=new LinkedHashMap<>();
        position.put("总裁","1000000");
        position.put("副总裁","1000001");
        position.put("总裁办公室主任","1000002");
        position.put("大区总经理","1000003");
        position.put("营销高级总监","1000004");
        position.put("财务本部高级总监","1000005");
        position.put("营运中心高级总监","1000006");
        position.put("线路部总监","1000007");
        position.put("总裁办公室副主任","1000008");
        position.put("采购工程部总监","1000009");
        position.put("线路组总监","1000010");
        position.put("转运场总监","1000011");
        position.put("营销总监","1000012");
        position.put("大区副总经理","1000013");
        position.put("储备总监","1000014");
        position.put("营运中心总监","1000015");
        position.put("营业区总监","1000016");
        position.put("线路部高级经理","1000017");
        position.put("线路组高级经理","1000018");
        position.put("储备高级经理","1000019");
        position.put("营运中心高级经理","1000020");
        position.put("网点组高级经理","1000021");
        position.put("大区办公室主任","1000022");
        position.put("运输管理组高级经理","1000023");
        position.put("转运场高级经理","1000024");
        position.put("财务组高级经理","1000025");
        position.put("财务管理部高级经理","1000026");
        position.put("核算部高级经理","1000027");
        position.put("营销高级经理","1000028");
        position.put("营业部高级经理","1000029");
        position.put("招聘管理部高级经理","1000030");
        position.put("数据分析部高级经理","1000031");
        position.put("总裁办公室高级经理","1000032");
        position.put("分拨场高级经理","1000033");
        position.put("运作组高级经理","1000034");
        position.put("产品部高级经理","1000035");
        position.put("业务研发部高级经理","1000036");
        position.put("运维部高级经理","1000037");
        position.put("实施部高级经理","1000038");
        position.put("基础研发部高级经理","1000039");
        position.put("营销经理","1000040");
        position.put("市场管理部经理","1000041");
        position.put("品质管理部经理","1000042");
        position.put("场站管理部经理","1000043");
        position.put("转运场经理","1000044");
        position.put("税务管理部经理","1000045");
        position.put("财务组经理","1000046");
        position.put("数据分析部经理","1000047");
        position.put("运作组经理","1000048");
        position.put("客户服务部经理","1000049");
        position.put("运输组经理","1000050");
        position.put("作业组经理","1000051");
        position.put("配送组经理","1000052");
        position.put("营业部经理","1000053");
        position.put("客服组经理","1000054");
        position.put("分拨点经理","1000055");
        position.put("中转组经理","1000056");
        position.put("线路部经理","1000057");
        position.put("储备经理","1000058");
        position.put("网点部经理","1000059");
        position.put("线路组经理","1000060");
        position.put("分拨场经理","1000061");
        position.put("营运中心经理","1000062");
        position.put("网点组经理","1000063");
        position.put("采购工程部经理","1000064");
        position.put("网点管理部经理","1000065");
        position.put("薪酬绩效管理部经理","1000066");
        position.put("产品部经理","1000067");
        position.put("培训管理部经理","1000068");
        position.put("行政后勤管理部经理","1000069");
        position.put("作业组主管","1000070");
        position.put("采购专员","1000071");
        position.put("营业员","1000072");
        position.put("核算会计","1000073");
        position.put("营销助理","1000074");
        position.put("项目客服","1000075");
        position.put("财务会计","1000076");
        position.put("配载员","1000077");
        position.put("收货员","1000078");
        position.put("需求分析师","1000079");
        position.put("场站管理专员","1000080");
        position.put("操作工","1000081");
        position.put("统计员","1000082");
        position.put("驾驶员","1000083");
        position.put("装卸工","1000084");
        position.put("客服专员","1000085");
        position.put("货区管理员","1000086");
        position.put("调度专员","1000087");
        position.put("收款员","1000088");
        position.put("值班员","1000089");
        position.put("营销专员","1000090");
        position.put("出纳","1000091");
        position.put("叉车司机","1000092");
        position.put("到达负责人","1000093");
        position.put("线路助理","1000094");
        position.put("项目专员","1000095");
        position.put("总裁办助理","1000096");
        position.put("行政专员","1000097");
        position.put("实施工程师","1000098");
        position.put("运维工程师","1000099");
        position.put("研发工程师","1000100");
        position.put("行政司机","1000101");
        position.put("税务会计","1000102");
        position.put("品质管理员","1000103");
        position.put("线路专员","1000104");
        position.put("数据分析专员","1000105");
        position.put("法务专员","1000106");
        position.put("市场管理专员","1000107");
        position.put("叉车修理工","1000108");
        position.put("培训专员","1000109");
        position.put("客户服务专员","1000110");
        position.put("薪酬专员","1000111");
        position.put("网点管理专员","1000112");
        position.put("运输管理专员","1000113");
        position.put("保洁员","1000114");
        position.put("招聘专员","1000115");
        position.put("品质管理专员","1000116");
        return position;
    }





    private Map<String,String> getOrg(){
        Map<String,String> org=new LinkedHashMap<>();
        org.put("350101","总裁");
        org.put("35010101","湖嘉大区");
        org.put("3501010101","湖嘉大区办公室");
        org.put("3501010102","湖嘉营销中心");
        org.put("350101010201","湖嘉营销管理部");
        org.put("35010101020101","湖嘉营销一组");
        org.put("35010101020102","湖嘉营销二组");
        org.put("35010101020103","湖嘉营销三组");
        org.put("350101010202","湖嘉线路管理部");
        org.put("35010101020201","湖嘉线路管理一组");
        org.put("35010101020202","湖嘉线路管理二组");
        org.put("35010101020203","湖嘉线路管理三组");
        org.put("35010101020204","湖嘉线路管理四组");
        org.put("35010101020205","湖嘉线路管理五组");
        org.put("35010101020206","湖嘉线路管理六组");
        org.put("350101010203","湖嘉外贸物流部");
        org.put("350101010204","湖嘉客服组");
        org.put("3501010103","湖嘉直属营业区");
        org.put("350101010301","杭州四季青营业部");
        org.put("350101010302","嘉兴许村营业部");
        org.put("350101010303","嘉兴桐乡营业部");
        org.put("350101010304","嘉兴桐乡（乌市）营业部");
        org.put("3501010104","湖州西营业区");
        org.put("350101010401","湖州西转运场");
        org.put("350101010402","湖州安吉营业部");
        org.put("3501010105","湖州织里营业区");
        org.put("350101010501","湖州南浔营业部");
        org.put("350101010502","湖州织里转运场");
        org.put("350101010503","苏州盛泽营业部");
        org.put("3501010106","嘉兴东营业区");
        org.put("350101010601","嘉兴东转运场");
        org.put("350101010602","嘉兴丁桥营业部");
        org.put("350101010603","嘉兴王店营业部");
        org.put("35010102","沪苏大区");
        org.put("3501010201","沪苏大区办公室");
        org.put("3501010202","沪苏营销中心");
        org.put("350101020201","上海营销管理部");
        org.put("35010102020101","上海线路管理一组");
        org.put("35010102020102","上海南方营销组");
        org.put("35010102020103","上海北方营销组");
        org.put("35010102020104","上海外贸物流组");
        org.put("350101020202","苏南营销管理部");
        org.put("35010102020201","苏州营销组");
        org.put("35010102020202","常州营销组");
        org.put("35010102020203","泰州营销组");
        org.put("35010102020204","无锡营销组");
        org.put("35010102020205","镇江丹阳营业部");
        org.put("3501010203","沪苏KA管理部");
        org.put("350101020301","江苏项目开发组");
        org.put("350101020302","上海项目开发组");
        org.put("350101020303","沪苏项目运作组");
        org.put("3501010204","沪苏营运中心");
        org.put("350101020401","上海北郊转运场");
        org.put("35010102040101","上海铁路昆明作业组");
        org.put("35010102040102","上海铁路贵阳作业组");
        org.put("35010102040103","上海铁路乌市作业组");
        org.put("350101020402","苏州西转运场");
        org.put("350101020403","无锡南转运场");
        org.put("350101020404","常州转运场");
        org.put("35010103","金温台大区");
        org.put("3501010301","金温台大区办公室");
        org.put("3501010302","金温台网点管理部");
        org.put("350101030201","金华网点管理组");
        org.put("35010103020101","金华永康营业部");
        org.put("35010103020102","诸暨大唐营业部");
        org.put("35010103020103","诸暨店口营业部");
        org.put("35010103020104","诸暨店口（乌市）营业部");
        org.put("35010103020105","金华兰溪营业部");
        org.put("350101030202","台州网点管理组");
        org.put("35010103020201","台州玉环营业部");
        org.put("35010103020202","台州玉环作业组");
        org.put("35010103020203","台州十里铺营业部");
        org.put("350101030203","温州网点管理组");
        org.put("35010103020301","温州塘下营业部");
        org.put("35010103020302","温州永强营业部");
        org.put("350101030204","义乌市场网点管理组");
        org.put("35010103020401","义乌北下朱营业部");
        org.put("35010103020402","义乌青口营业部");
        org.put("35010103020403","义乌青口作业组");
        org.put("35010103020404","义乌公路港营业部");
        org.put("3501010303","金温台线路管理一部");
        org.put("3501010304","金温台线路管理二部");
        org.put("3501010305","金温台线路管理三部");
        org.put("3501010306","金温台营销管理部");
        org.put("3501010307","金温台线路管理部");
        org.put("350101030701","辽宁沈阳卸货点");
        org.put("350101030702","黑龙江哈尔滨卸货点");
        org.put("3501010308","金温台外贸物流部");
        org.put("3501010309","金温台营运中心");
        org.put("350101030901","金华南转运场");
        org.put("350101030902","义乌西转运场");
        org.put("350101030903","永康东转运场");
        org.put("35010103090201","义乌西铁路白班作业一组");
        org.put("35010103090202","义乌西铁路白班作业二组");
        org.put("35010103090203","义乌西铁路晚班作业组");
        org.put("350101030904","温州西转运场");
        org.put("350101030905","台州通泰转运场");
        org.put("35010103090501","台州通泰作业一组");
        org.put("35010103090502","台州通泰作业二组");
        org.put("35010103090503","台州康洋营业部");
        org.put("35010103090504","台州康洋作业组");
        org.put("35010103090505","台州跃鑫营业部");
        org.put("350101030906","台州永源转运场");
        org.put("35010104","新疆大区");
        org.put("3501010401","新疆大区办公室");
        org.put("350101040101","新疆客服组");
        org.put("3501010402","新疆营销组");
        org.put("3501010403","新疆营运中心");
        org.put("350101040301","乌东分拨场");
        org.put("35010104030101","恒通分拨点");
        org.put("35010104030102","长胜分拨点");
        org.put("35010104030103","新疆铁路作业一组");
        org.put("35010104030104","新疆铁路作业二组");
        org.put("35010104030105","新疆铁路作业三组");
        org.put("350101040302","南郊分拨场");
        org.put("350101040303","新疆运输组");
        org.put("3501010404","甘藏营运中心");
        org.put("350101040401","拉萨西分拨场");
        org.put("350101040402","兰州东分拨场");
        org.put("35010105","云南大区");
        org.put("3501010501","云南大区办公室");
        org.put("350101050101","云南客服组");
        org.put("3501010502","云南营销中心");
        org.put("350101050201","云南营销组");
        org.put("350101050202","云南大理营业部");
        org.put("350101050203","云南曲靖营业部");
        org.put("3501010503","云南营运中心");
        org.put("350101050301","金马村分拨场");
        org.put("35010105030101","云南铁路沪苏作业组");
        org.put("35010105030102","云南铁路义乌作业组");
        org.put("35010105030103","云南铁路杭州作业组");
        org.put("35010105030104","云南铁路甬绍作业组");
        org.put("35010105030105","跑马山作业组");
        org.put("350101050302","云南运输组");
        org.put("35010105030201","云南中转组");
        org.put("35010105030202","云南配送组");
        org.put("35010106","贵州大区");
        org.put("3501010601","贵州大区办公室");
        org.put("350101060101","贵州客服组");
        org.put("3501010602","贵州营销中心");
        org.put("350101060201","贵州营销组");
        org.put("350101060202","贵州安顺营业部");
        org.put("350101060203","贵州毕节营业部");
        org.put("350101060204","贵州凯里营业部");
        org.put("350101060205","贵州六盘水营业部");
        org.put("350101060206","贵州兴义营业部");
        org.put("350101060207","贵州遵义营业部");
        org.put("3501010603","贵州营运中心");
        org.put("350101060301","改貌分拨场");
        org.put("35010106030101","贵州铁路作业一组");
        org.put("35010106030102","贵州铁路作业二组");
        org.put("35010106030103","西南商贸城分拨点");
        org.put("350101060302","贵州运输组");
        org.put("35010106030201","贵州中转组");
        org.put("35010106030202","贵州配送组");
        org.put("35010107","苏中大区");
        org.put("3501010701","南通线路管理一组");
        org.put("3501010702","南通线路管理二组");
        org.put("3501010703","南通线路管理三组");
        org.put("3501010704","苏州常熟营业部");
        org.put("3501010705","苏州常熟营业二部");
        org.put("3501010706","南通通州转运场");
        org.put("3501010707","南通叠石桥转运场");
        org.put("3501010708","苏中客服组");
        org.put("35010108","杭绍甬大区");
        org.put("3501010801","杭绍甬大区办公室");
        org.put("3501010802","杭州营销中心");
        org.put("350101080201","杭州营销一组");
        org.put("350101080202","杭州营销二组");
        org.put("350101080203","杭州营销三组");
        org.put("350101080204","杭州营销四组");
        org.put("350101080205","杭州营销五组");
        org.put("350101080206","杭州营销六组");
        org.put("3501010803","杭州营运中心");
        org.put("350101080301","杭州北转运场");
        org.put("35010108030101","杭州铁路昆贵作业组");
        org.put("35010108030102","杭州铁路乌兰作业组");
        org.put("3501010804","绍兴市场网点管理部");
        org.put("350101080401","柯东收货点");
        org.put("350101080402","东升路收货点");
        org.put("350101080403","南区收货点");
        org.put("350101080404","北二区收货点");
        org.put("350101080405","北五区收货点");
        org.put("350101080406","北八区收货点");
        org.put("350101080407","建材市场收货点");
        org.put("3501010805","绍兴线路管理部");
        org.put("350101080501","绍兴线路管理一组");
        org.put("350101080502","绍兴线路管理二组");
        org.put("350101080503","绍兴线路管理三组");
        org.put("350101080504","绍兴线路管理四组");
        org.put("350101080505","绍兴线路管理五组");
        org.put("35010108050501","重庆卸货点");
        org.put("35010108050502","四川成都卸货点");
        org.put("35010108050503","四川南充卸货点");
        org.put("35010108050504","四川宜宾卸货点");
        org.put("35010108050505","四川泸州卸货点");
        org.put("35010108050101","广西南宁卸货点");
        org.put("35010108050102","广西柳州卸货点");
        org.put("35010108050103","广西桂林卸货点");
        org.put("35010108050104","广西宾阳卸货点");
        org.put("35010108050105","广西凭祥卸货点");
        org.put("35010108050106","广西东兴卸货点");
        org.put("35010108050107","广西玉林卸货点");
        org.put("35010108050108","广西爱店卸货点");
        org.put("35010108050109","广西垌中卸货点");
        org.put("35010108050110","广西西贡卸货点");
        org.put("35010108050401","甘肃兰州卸货点");
        org.put("35010108050402","甘肃临夏卸货点");
        org.put("35010108050403","宁夏银川卸货点");
        org.put("35010108050404","青海西宁卸货点");
        org.put("3501010806","绍兴营销中心");
        org.put("350101080601","绍兴嵊州营业部");
        org.put("350101080602","绍兴袍江营业部");
        org.put("350101080603","萧山党山营业部");
        org.put("350101080604","绍兴营销一组");
        org.put("350101080605","绍兴营销二组");
        org.put("3501010807","绍兴营运中心");
        org.put("350101080701","钱清转运场");
        org.put("350101080702","绍兴运输组");
        org.put("35010108070101","绍兴铁路昆贵作业组");
        org.put("35010108070102","绍兴铁路乌市作业组");
        org.put("350101080703","柯东汽运转运场");
        org.put("350101080704","柯西汽运转运场");
        org.put("350101080705","柯南汽运转运场");
        org.put("3501010808","宁波线路管理部");
        org.put("3501010809","宁波营销中心");
        org.put("3501010810","宁波营运中心");
        org.put("350101080801","宁波线路管理一组");
        org.put("350101080802","宁波线路管理二组");
        org.put("350101080803","宁波线路管理三组");
        org.put("350101080804","宁波线路管理四组");
        org.put("350101080805","宁波线路管理五组");
        org.put("350101080806","余姚线路管理一组");
        org.put("350101080901","慈溪胜山营业部");
        org.put("350101080902","慈溪匡堰营业部");
        org.put("350101080903","慈溪白沙营业部");
        org.put("350101080904","慈溪营销组");
        org.put("350101080905","宁波营销组");
        org.put("350101080906","余姚营销组");
        org.put("350101081001","宁波北转运场");
        org.put("35010108100101","宁波铁路昆贵作业组");
        org.put("35010108100102","宁波铁路乌市作业组");
        org.put("350101081002","余姚西转运场");
        org.put("350101081003","宁波汽运转运场");
        org.put("35010109","总裁办公室");
        org.put("3501010901","人才发展部");
        org.put("35010110","采购工程部");
        org.put("35010111","外联部");
        org.put("35010112","人力行政本部");
        org.put("3501011201","薪酬绩效管理部");
        org.put("3501011202","招聘管理部");
        org.put("3501011203","培训管理部");
        org.put("3501011204","行政后勤管理部");
        org.put("35010113","IT信息本部");
        org.put("3501011301","产品部");
        org.put("3501011302","基础研发部");
        org.put("3501011303","业务研发部");
        org.put("3501011304","实施部");
        org.put("3501011305","运维部");
        org.put("35010114","财务本部");
        org.put("3501011401","财务管理部");
        org.put("3501011402","税务管理部");
        org.put("3501011403","核算部");
        org.put("3501011404","资金部");
        org.put("350101140101","绍兴财务组");
        org.put("350101140102","新疆财务组");
        org.put("350101140103","云南财务组");
        org.put("350101140104","贵州财务组");
        org.put("35010115","业务本部");
        org.put("3501011501","数据分析部");
        org.put("3501011502","市场管理部");
        org.put("3501011503","网点管理部");
        org.put("3501011504","运输管理部");
        org.put("3501011505","场站管理部");
        org.put("3501011506","品质管理部");
        org.put("3501011507","客户服务部");




        return org;
    }





}
