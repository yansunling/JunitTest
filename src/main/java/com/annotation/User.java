package com.annotation;



@TaskDesc(desc = "The Class", uri = "com.sgl.annotation")
public class User {
    private String id;

    private int age;


    public User() {
    }

    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "{age=" + age +
                '}';
    }
}
