package com.factory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("dog")
public class Dog implements AnimalFactory {

    @Override
    public String food() {
        return "吃骨头";
    }

    @Override
    public String animal() {
        return "狗";
    }
}
