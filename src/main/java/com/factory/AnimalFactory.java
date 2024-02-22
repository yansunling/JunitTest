package com.factory;


import org.springframework.stereotype.Service;

//@Service
public abstract class AnimalFactory<T> {
    public abstract String food(T t);

    public String animal(T t){
//        AnimalFactory obj = (AnimalFactory)CIPHandlerConfig.getBean(clazz);
//        String animal = obj.food(t);
        return food(t);

    };
}
