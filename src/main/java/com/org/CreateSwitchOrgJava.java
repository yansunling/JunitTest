package com.org;


import com.excel.CJExcelUtil;
import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j

public class CreateSwitchOrgJava  {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = readExcel(excelFilePath);
        String directoryPath = "E:\\tmsp-upload";
//        String directoryPath = "E:\\myGit\\tmsp\\tmsp-api\\src\\main\\java\\com\\dy\\tmsp\\arriveapp\\data\\TmspArriveAppBatchSigninOutVo.java";
        orgDataList.forEach(item->{
            replaceInFiles(new File(directoryPath),item);
        });

    }

    public static void replaceInFiles(File directory,OrgData orgData) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        replaceInFiles(file,orgData);
                    } else {
                        try {
                            replaceInFile(file,orgData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            try {
                replaceInFile(directory,orgData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void replaceInFile(File file,OrgData orgData) throws IOException {
        String fileExtension = getFileExtension(file);
        List<String> typeList = Arrays.asList("java","js","xml","html");
        if(!typeList.contains(fileExtension)){
            return;
        }
        if(file.getName().startsWith(".")){
            log.info("---error----"+file.getName());
            return;
        }
        log.info(file.getName());
        Path filePath = Paths.get(file.getAbsolutePath());
        // 读取文件内容为字符串（UTF-8编码）
        String content = new String(Files.readAllBytes(filePath), "UTF-8");
        // 替换所有 "123" 为 "444"
        content = content.replaceAll(orgData.getOldOrgId(),orgData.getNewOrgId());
        // 将修改后的内容写回原文件
        Files.write(filePath, content.getBytes("UTF-8"));

    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }









    @SneakyThrows
    public static List<OrgData> readExcel(String filePath) {
        File file = new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        listResult = listResult.subList(1, listResult.size());

        List<OrgData> importDataList = CJExcelUtil.initImportExcelDatas(OrgData.titleMap, listResult, OrgData.class);
        Map<String, List<OrgData>> mapRef = new HashMap<>();
        for (int i = 0; i < importDataList.size(); i++) {
            OrgData importData = importDataList.get(i);
            if (StringUtils.isBlank(importData.getOldOrgId())) {
                continue;
            }
            if (StringUtils.isBlank(importData.getNewOrgId())) {
                importData.setNewOrgId(importDataList.get(i - 1).getNewOrgId());
            }
            List<OrgData> orgDataList = mapRef.get(importData.getNewOrgId());
            if (CollectionUtil.isEmpty(orgDataList)) {
                orgDataList = new ArrayList<>();
            }
            if (StringUtils.isBlank(importData.getNewRegionId())) {

                importData.setNewRegionId(SwitchUtil.newMap.get(importData.getNewRegionName()));
                importData.setNewDistrictId(SwitchUtil.newMap.get(importData.getNewDistrictName()));
                //设置旧大小区ID
                importData.setOldRegionId(SwitchUtil.newMap.get(importData.getOldRegionName()));
                importData.setOldDistrictId(SwitchUtil.newMap.get(importData.getOldDistrictName()));

            }
            orgDataList.add(importData);
            mapRef.put(importData.getNewOrgId(), orgDataList);
        }

        return importDataList;

    }


}
