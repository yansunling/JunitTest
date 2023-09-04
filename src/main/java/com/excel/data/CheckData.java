package com.excel.data;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class CheckData {
    private String user_id;
    private String user_name;
    private String img;
    public static List<String> title= Arrays.asList("user_id","user_name","img");
}
