package com.factory;

import com.word.asset.vo.CompAssetBaseInfoChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Cat implements AnimalFactory {

    @Autowired
    private Dog dog;

    @Override
    public String food() {
        return "吃鱼";
    }

    @Override
    public String animal() {
        return "猫";
    }

    public void catEat(CompAssetBaseInfoChangeVO changeVO){
        System.out.println(changeVO.getAsset_company());
        dog.animal();
    }

    public void eat(String changeVO){
        System.out.println(changeVO);
        dog.animal();
    }

}