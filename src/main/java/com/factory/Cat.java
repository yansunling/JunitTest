package com.factory;

import com.factory.data.CatData;
import com.word.dataSource.vo.CompAssetBaseInfoChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CIPHandler(group = "tmsp",
        handlerType = "cat")
public class Cat extends AnimalFactory<CatData> {



    @Override
    public String food(CatData data) {
        return "吃鱼";
    }




}