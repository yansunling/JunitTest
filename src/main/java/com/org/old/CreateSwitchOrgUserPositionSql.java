package com.org.old;


import com.alibaba.fastjson.JSON;
import com.org.util.SwitchUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class CreateSwitchOrgUserPositionSql implements ApplicationContextAware {
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
    @SneakyThrows
    public void test() throws Exception {
        Map<String, String> position = getPosition();
        String filePath = "C:/Users/yansunling/Desktop/user_position.xlsx";
        ExcelReader excelReader = new ExcelReader(new File(filePath));
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<String> sqlList=new ArrayList<>();
        Map<String, String> newMap = SwitchUtil.newMap;
        Map<String,String> rank=new HashMap<>();
        rank.put("员工","1");
        rank.put("主管","2");
        rank.put("经理","3");
        rank.put("高级经理","4");
        rank.put("总监","5");
        rank.put("高级总监","6");
        rank.put("副总","7");
        rank.put("总裁","8");
        rank.put("董事长","9");

        for(Object[] obj:listResult){
            if(obj[0]==null){
                continue;
            }
            String positionId = position.get(obj[1]);
            String orgId=newMap.get(obj[2]+"");
            String userId=obj[0]+"";
            String rankId = rank.get(obj[3]);
            if(StringUtils.isBlank(positionId)||StringUtils.isBlank(orgId)||StringUtils.isBlank(rankId)){
                throw new RuntimeException("机构错误obj:"+ JSON.toJSONString(obj));
            }
            sqlList.add("update hcm.hcm_emp_ent set position_id='"+positionId+"',position='"+positionId+"',dept='"+orgId+"',rank='"+rankId+"'  where emp_id='"+userId+"';");
        }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm_org_positon.sql");
        FileUtils.writeLines(allFile, "utf-8", sqlList);


    }

    private Map<String,String> getPosition(){
        Map<String,String> map=new HashMap<>();
        map.put("总裁","1000000");
        map.put("副总裁","1000001");
        map.put("总裁办公室主任","1000002");
        map.put("大区总经理","1000003");
        map.put("营销高级总监","1000004");
        map.put("财务本部高级总监","1000005");
        map.put("营运中心高级总监","1000006");
        map.put("线路部总监","1000007");
        map.put("总裁办公室副主任","1000008");
        map.put("采购工程部总监","1000009");
        map.put("线路组总监","1000010");
        map.put("转运场总监","1000011");
        map.put("营销总监","1000012");
        map.put("大区副总经理","1000013");
        map.put("储备总监","1000014");
        map.put("营运中心总监","1000015");
        map.put("营业区总监","1000016");
        map.put("线路部高级经理","1000017");
        map.put("线路组高级经理","1000018");
        map.put("储备高级经理","1000019");
        map.put("营运中心高级经理","1000020");
        map.put("网点组高级经理","1000021");
        map.put("大区办公室主任","1000022");
        map.put("运输管理组高级经理","1000023");
        map.put("转运场高级经理","1000024");
        map.put("财务组高级经理","1000025");
        map.put("财务管理部高级经理","1000026");
        map.put("核算部高级经理","1000027");
        map.put("营销高级经理","1000028");
        map.put("营业部高级经理","1000029");
        map.put("招聘管理部高级经理","1000030");
        map.put("数据分析部高级经理","1000031");
        map.put("总裁办公室高级经理","1000032");
        map.put("分拨场高级经理","1000033");
        map.put("运作组高级经理","1000034");
        map.put("产品部高级经理","1000035");
        map.put("业务研发部高级经理","1000036");
        map.put("运维部高级经理","1000037");
        map.put("实施部高级经理","1000038");
        map.put("基础研发部高级经理","1000039");
        map.put("营销经理","1000040");
        map.put("市场管理部经理","1000041");
        map.put("品质管理部经理","1000042");
        map.put("场站管理部经理","1000043");
        map.put("转运场经理","1000044");
        map.put("税务管理部经理","1000045");
        map.put("财务组经理","1000046");
        map.put("数据分析部经理","1000047");
        map.put("运作组经理","1000048");
        map.put("客户服务部经理","1000049");
        map.put("运输组经理","1000050");
        map.put("作业组经理","1000051");
        map.put("配送组经理","1000052");
        map.put("营业部经理","1000053");
        map.put("客服组经理","1000054");
        map.put("分拨点经理","1000055");
        map.put("中转组经理","1000056");
        map.put("线路部经理","1000057");
        map.put("储备经理","1000058");
        map.put("网点部经理","1000059");
        map.put("线路组经理","1000060");
        map.put("分拨场经理","1000061");
        map.put("营运中心经理","1000062");
        map.put("网点组经理","1000063");
        map.put("采购工程部经理","1000064");
        map.put("网点管理部经理","1000065");
        map.put("薪酬绩效管理部经理","1000066");
        map.put("产品部经理","1000067");
        map.put("培训管理部经理","1000068");
        map.put("行政后勤管理部经理","1000069");
        map.put("作业组主管","1000070");
        map.put("采购专员","1000071");
        map.put("营业员","1000072");
        map.put("核算会计","1000073");
        map.put("营销助理","1000074");
        map.put("项目客服","1000075");
        map.put("财务会计","1000076");
        map.put("配载员","1000077");
        map.put("收货员","1000078");
        map.put("需求分析师","1000079");
        map.put("场站管理专员","1000080");
        map.put("操作工","1000081");
        map.put("统计员","1000082");
        map.put("驾驶员","1000083");
        map.put("装卸工","1000084");
        map.put("客服专员","1000085");
        map.put("货区管理员","1000086");
        map.put("调度专员","1000087");
        map.put("收款员","1000088");
        map.put("值班员","1000089");
        map.put("营销专员","1000090");
        map.put("出纳","1000091");
        map.put("叉车司机","1000092");
        map.put("到达负责人","1000093");
        map.put("线路助理","1000094");
        map.put("项目专员","1000095");
        map.put("总裁办助理","1000096");
        map.put("行政专员","1000097");
        map.put("实施工程师","1000098");
        map.put("运维工程师","1000099");
        map.put("研发工程师","1000100");
        map.put("行政司机","1000101");
        map.put("税务会计","1000102");
        map.put("品质管理员","1000103");
        map.put("线路专员","1000104");
        map.put("数据分析专员","1000105");
        map.put("法务专员","1000106");
        map.put("市场管理专员","1000107");
        map.put("叉车修理工","1000108");
        map.put("培训专员","1000109");
        map.put("客户服务专员","1000110");
        map.put("薪酬专员","1000111");
        map.put("网点管理专员","1000112");
        map.put("运输管理专员","1000113");
        map.put("保洁员","1000114");
        map.put("招聘专员","1000115");
        map.put("品质管理专员","1000116");
        return map;
    }


}
