package com.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyFactoryService {
    @Autowired
    private Map<String, AnimalFactory> animalFactory;


    public String factoryMode(String type) {
        AnimalFactory animal = animalFactory.get(type);
        return animal.animal() + animal.food();
    }




}
