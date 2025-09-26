package com.word.createWord;

import com.word.dataSource.controller.CompAssetLevelClassController;
import com.word.doc.POIMergeDocUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class WorkApiQueryMerge {


    public static void main(String[] args) throws Exception {

        List<String> queryList = Arrays.asList("comp_asset_level_class_list","comp_asset_level_class_third_class","comp_asset_level_class_second_class","comp_asset_level_class_first_class");
        boolean esbFlag=true;
        WordCreateByClass.createApi(esbFlag, CompAssetLevelClassController.class);
        WordCreateByQuery.createQuery(esbFlag,queryList);
        String  apiDoc="C:/Users/yansunling/Desktop/api/api.docx";

        String directoryPath = "C:/Users/yansunling/Desktop/api/main";
        List<String> fileList=new ArrayList<>();
        Stream<Path> stream = Files.walk(Paths.get(directoryPath));
            // 遍历所有文件路径并打印
        stream.filter(Files::isRegularFile)  // 只保留文件（排除目录）
                    .forEach(path -> fileList.add(path.toAbsolutePath().toString()));
        POIMergeDocUtil.mergeDoc(fileList.toArray(new String[0]),apiDoc);
        WordCreateByClass.openWps(Arrays.asList(apiDoc));

    }



}
