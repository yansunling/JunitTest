package com.str.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data

public class User {
    @JsonSerialize(using = CustomSerializer.class)
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
