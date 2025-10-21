package com.word.createWord;

import com.str.random.CompAssetBusinessCarPO;
import com.word.createWord.query.DocConfigData;
import com.word.dataSource.controller.CompAssetBusinessCarController;
import com.word.dataSource.controller.CompAssetInsuranceRemindController;
import com.word.dataSource.controller.CompAssetLevelClassController;
import com.word.doc.POIMergeDocUtil;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class WorkApiQueryMerge {


    public static void main(String[] args) throws Exception {
        List<DocConfigData> queryOperateList =Arrays.asList(
                new DocConfigData(Arrays.asList("comp_asset_business_car_list","comp_asset_operate_log_class"),CompAssetBusinessCarController.class),
                new DocConfigData(Arrays.asList("comp_asset_insurance_remind_list"),CompAssetInsuranceRemindController.class)
                );
        boolean esbFlag=false;
        boolean deleteFlag=false;
        List<List<String>> fileList=new ArrayList<>();
       for(DocConfigData obj:queryOperateList){
           List<String> tempList=new ArrayList<>();
           tempList.addAll(WordCreateByQuery.createQuery(esbFlag,obj.getQueryList()));
           tempList.addAll(WordCreateByClass.createApi(esbFlag, obj.getClazz(), deleteFlag));
           fileList.add(tempList);
       }
        String  apiDoc="C:/Users/yansunling/Desktop/api/api.docx";
        List<String> files=new ArrayList<>();
        fileList.forEach(item->{
            files.addAll(item);
        });
        POIMergeDocUtil.mergeDoc(files.toArray(new String[0]),apiDoc);
        WordCreateByClass.openWps(Arrays.asList(apiDoc));



    }



}
