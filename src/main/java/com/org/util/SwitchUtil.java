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


    private static JdbcTemplate jdbcTemplateYL;


    @Autowired
    public  void setJdbcTemplate(JdbcTemplate jdbcTemplateYL) {
        this.jdbcTemplateYL = jdbcTemplateYL;
    }
    @PostConstruct
    public void init(){
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(orgSql);
        maps.forEach(item -> {
            newMap.put(item.get("org_name") + "",item.get("org_id") + "");
        });
    }

    public static Map<String,String> newMap=new HashMap<>();



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
            if(StringUtils.isBlank(importData.getOldOrgName())){
                continue;
            }
            List<OrgData> orgDataList = mapRef.get(importData.getNewOrgId());
            if(CollectionUtil.isEmpty(orgDataList)){
                orgDataList=new ArrayList<>();
            }
            if(StringUtils.isBlank(importData.getNewRegionId())){

                importData.setNewRegionId(newMap.get(importData.getNewRegionName()));
                importData.setNewDistrictId(newMap.get(importData.getNewDistrictName()));
                //设置旧大小区ID
                importData.setOldRegionId(newMap.get(importData.getOldRegionName()));
                importData.setOldDistrictId(newMap.get(importData.getOldDistrictName()));

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
            orgData.setOldOrgNameList("'"+StringUtils.join("|",oldOrgNames.toArray())+"'");
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
        String areaRegex = "big_area|region|大区";
        Pattern pattern = Pattern.compile(areaRegex);
        // 创建Matcher对象
        String sql="update "+newTable+" set "+column+" = '<新机构"+type+">' where "+column+" in('<老机构"+type+">');";
        Matcher matcher = pattern.matcher(column);
        if(matcher.find()){
            sql="update "+newTable+" set "+column+" = '<新机构大区"+type+">' where "+column+" in('<老机构大区"+type+">');";
        }
        String smallRegex = "small_area|district_id|小区";
        Pattern smallPattern = Pattern.compile(smallRegex);
        Matcher smallMatcher = smallPattern.matcher(column);
        if(smallMatcher.find()){
            sql="update "+newTable+" set "+column+" = '<新机构小区"+type+">' where "+column+" in('<老机构小区"+type+">');";
        }
        return sql;
    }


    public static String matchColumn(String column,String newTable,String type){

        // 创建Matcher对象
        String sql="update "+newTable+" set "+column+" = '<新机构"+type+">' where "+column+" in('<老机构"+type+">');";

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
        item=item.replaceAll("'<老机构名称集合>'",orgData.getOldOrgNameList());
        item=item.replaceAll("'<新机构名称>'",orgData.getNewOrgName());
        item=item.replaceAll("'<老机构名称>'",orgData.getOldOrgName());
        item=item.replaceAll("'<新机构大区名称>'",orgData.getNewRegionName());
        item=item.replaceAll("'<新机构小区名称>'",orgData.getNewDistrictName());
        item=item.replaceAll("'<新机构小区ID>'",orgData.getNewDistrictId());
        item=item.replaceAll("'<新机构大区ID>'",orgData.getNewRegionId());
        item=item.replaceAll("'<老机构大区ID>'",orgData.getOldRegionId());
        item=item.replaceAll("'<老机构大区名称>'",orgData.getOldRegionName());
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



    public static String replaceSql(String column, String newTable, String type) {

        String[] split = newTable.split("\\.");
        String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + split[1] + "' and  table_schema='" + split[0] + "';";

        List<String> columnList = jdbcTemplateYL.queryForList(columnsSql, String.class);
        if(column.indexOf("`")<0){
            column="`"+column+"`";
        }

        String join ="`"+StringUtils.join("`,`", columnList.toArray())+"`";
        String newJoin = join.replace(column, "'<新机构" + type + ">'");
        String sql = "replace into  " + newTable + " select " + newJoin + " from "+newTable+" where " + column + " in('<老机构" + type + ">');";




        String areaRegex = "big_area|region|大区";
        Pattern pattern = Pattern.compile(areaRegex);
        // 创建Matcher对象

        Matcher matcher = pattern.matcher(column);
        if (matcher.find()) {
            String regionJoin = join.replaceAll(column, "'<新机构大区" + type + ">'");
            sql = "replace into  " + newTable + " select " + regionJoin + " from "+newTable+" where " + column + " in('<老机构大区" + type + ">');";
        }
        String smallRegex = "small_area|district_id|小区";
        Pattern smallPattern = Pattern.compile(smallRegex);
        Matcher smallMatcher = smallPattern.matcher(column);
        if (smallMatcher.find()) {
            String regionJoin = join.replaceAll(column, "'<新机构小区" + type + ">'");
            sql = "replace into  " + newTable + " select " + regionJoin + " from "+newTable+" where " + column + " in('<老机构小区" + type + ">');";
        }
        return sql;
    }







}
