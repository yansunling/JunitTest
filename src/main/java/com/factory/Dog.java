package com.factory;

import com.factory.data.DogData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@CIPHandler(group = "tmsp",
        handlerType = "dog")
public class Dog extends AnimalFactory<DogData> {

    @Override
    public String food(DogData dogData) {
        return "吃骨头";
    }


}
