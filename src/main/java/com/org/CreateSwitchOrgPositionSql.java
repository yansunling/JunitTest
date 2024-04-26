package com.org;


import com.excel.CJExcelUtil;
import com.org.data.OrgData;
import com.org.data.PositionData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import io.swagger.models.auth.In;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchOrgPositionSql implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Qualifier("dataSource")
    @Autowired
    private YDDriverManagerDataSource ydDriverManagerDataSource;

    @Test
    public void test() throws Exception {
        String filePath = "C:/Users/yansunling/Desktop/position.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        listResult = listResult.subList(1, listResult.size());

        List<PositionData> importDataList = CJExcelUtil.initImportExcelDatas(PositionData.titleMap, listResult, PositionData.class);

        String sql="select id_name as position_name,job_category from hcm.hcm_org_position where pos_status='1'";

        List<PositionData> oldPosition = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(PositionData.class));
        Map<String,String> categoryMap=new HashMap<>();
        oldPosition.forEach(item->{
            categoryMap.put(item.getPosition_name(),item.getJob_category());
        });



        Map<String,String> rank=new HashMap<>();
        rank.put("员工","1");
        rank.put("主管","2");
        rank.put("经理","3");
        rank.put("高级经理","4");
        rank.put("总监","5");
        rank.put("高级总监","6");
        rank.put("副总","7");
        rank.put("副总裁","7");
        rank.put("总裁","8");
        rank.put("董事长","9");
        Map<String,List<PositionData>> orgRel=new HashMap<>();
        Map<String,PositionData> positionRel=new HashMap<>();
        Map<String, String> newMap = SwitchUtil.newMap;



        for(PositionData item:importDataList){
            if(StringUtils.isBlank(item.getOrg_name())){
                continue;
            }
            item.setOrg_id(newMap.get(item.getOrg_name()));
            item.setRank(rank.get(item.getRank_name()));
            String category = categoryMap.get(item.getPosition_name());
            if(StringUtils.isBlank(category)){
                if(item.getPosition_name().indexOf("经理")>=0
                        ||item.getPosition_name().indexOf("总监")>=0
                        ||item.getPosition_name().indexOf("主任")>=0){
                    category="3";
                }else{
                    category="1";
                }

            }
            item.setJob_category(category);
            PositionData positionData = positionRel.get(item.getPosition_name());
            if(positionData==null){
                positionRel.put(item.getPosition_name(),item);
            }
            List<PositionData> orgPosition = orgRel.get(item.getOrg_id());
            if(orgPosition==null){
                orgPosition=new ArrayList<>();
            }
            if(!orgPosition.contains(item)){
                orgPosition.add(item);
            }
            orgRel.put(item.getOrg_id(),orgPosition);
        }



        Map<String,Integer> idMap=new HashMap<>();
        Integer start=1000000;
        Set<String> keySet = positionRel.keySet();

        List<String> fileList=new ArrayList<>();

        for(String key:keySet){
            idMap.put(key,start);
            PositionData positionData = positionRel.get(key);
            positionData.setPosition_id(start+"");
            fileList.add("INSERT ignore  INTO hcm.hcm_org_position_rule(serial_no, position_id, position_name, staffing_num, entry_num, position_status, job_category, rank, position_nature, source, is_delete, remark, version, update_user_id, update_time, create_user_id, create_time) VALUES ('"+start+"', '"+start+"', '"+positionData.getPosition_name()+"', 0,0, '1', '"+positionData.getJob_category()+"','"+positionData.getRank()+"', '', '', '0', '', 0, 'T1113', now(), 'T1113', now());");
            start++;
        }
        fileList.add("\n\n\n");
        Map<String, String> orgMap = getOrg();
        orgRel.forEach((orgId,list)->{
            list.forEach(item->{
                if(StringUtils.isBlank(orgId)){
                    System.out.println(orgId+";"+list.get(0).getOrg_name());
                }

                fileList.add("INSERT ignore INTO hcm.hcm_org_position_rel_rule(serial_no, position_id, position_name, org_id, org_name, rank, nature, staffing_num, entry_num, remark, update_user_id, update_time, create_user_id, create_time) VALUES (UUID_SHORT(), '"+idMap.get(item.getPosition_name())+"','"+item.getPosition_name()+"', '"+orgId+"', '"+orgMap.get(orgId)+"', '"+item.getRank()+"', '', 0, 0, '', 'T1113', now(), 'T1113', now());");
            });


        });

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm_org_position_rule.sql");
        FileUtils.writeLines(allFile,"utf-8",fileList);


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
