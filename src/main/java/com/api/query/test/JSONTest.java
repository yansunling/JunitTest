package com.api.query.test;

import com.alibaba.fastjson.JSONObject;
import com.api.query.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONTest {
    public static void main(String[] args) {
        QUERY_base_where_paramPO po=new QUERY_base_where_paramPO();

        Map<String,Object> map=new HashMap<>();
        List<QUERY_base_where_paramPO> params=new ArrayList<>();
        params.add(new QUERY_base_where_paramPO());

        List<QUERY_base_tablePO> tables=new ArrayList<>();
        tables.add(new QUERY_base_tablePO());

        List<QUERY_base_wherePO> wheres=new ArrayList<>();
        wheres.add(new QUERY_base_wherePO());

        List<QUERY_base_columnsPO> colums=new ArrayList<>();
        colums.add(new QUERY_base_columnsPO());



        QUERY_base_jumpPO jumps=new QUERY_base_jumpPO();
        List<QUERY_base_jump_paramPO> jump_paramPOS=new ArrayList<>();
        jump_paramPOS.add(new QUERY_base_jump_paramPO());
        jumps.setParams(jump_paramPOS);

        List<QUERY_base_jumpPO> jumpPOList=new ArrayList<>();
        jumpPOList.add(jumps);

        map.put("register",new QUERY_base_registerPO());
//        map.put("demand",new QUERY_base_demandPO());
        map.put("params",params);
        map.put("colums",colums);
        map.put("tables",tables);
        map.put("wheres",wheres);
        map.put("condition",new Object());


//        map.put("jumps",jumpPOList);
//        map.put("type","add");
        System.out.println(JSONObject.toJSONString(map));

    }
}
