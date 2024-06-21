package com.other.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsTest {
    public static void toMapTest(List<String> list){
        Map<String,String> map = list.stream().limit(3).collect(Collectors.toMap(e -> e.substring(0,1),e -> e));
        Map<String,String> map1 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1),e->e,(a,b)-> {
            System.out.println(a);
            System.out.println(b);
            return b;}));

        Map<String,String> map2 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1), e->e,(a, b)-> b, HashMap::new));
        System.out.println(map.toString() + "\n" + map1.toString() + "\n" + map2.toString());


    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121");
//        toMapTest(list);

//        LongStream longStream = list.stream().mapToLong(a -> Long.parseLong(a));
//        longStream.forEach(System.out::println);

        //调用对象方法
        List<Long> collect =list.stream().map(Long::parseLong).collect(Collectors.toList());
        System.out.println(collect);

        //通过stream进行数据操作
        List<Integer> collect1 = list.stream().map(n -> Integer.parseInt(n)* 2).collect(Collectors.toList());
        System.out.println(collect1);



    }
}
