package com.other.array;

import com.alibaba.fastjson.JSONObject;
import com.other.annotation.User;

import java.util.*;

public class CompareTest {
    public static void main(String[] args) {
        List<User> list=new ArrayList<>();
        User user=new User(13);
        User user2=new User(2);
        User user3=new User(14);
        User user5=new User(17);

        list.add(user);
        list.add(user2);
        list.add(user3);
        list.add(user5);
        Map<Integer,String> temp=new HashMap<>();

        list.sort(Comparator.comparing(User::getAge));//升序
        list.sort(Comparator.comparing(User::getAge).reversed());//降序


        System.out.println(JSONObject.toJSONString(list));


        list.sort(Comparator.comparing(o ->o.getAge()));//升序







    }
}
