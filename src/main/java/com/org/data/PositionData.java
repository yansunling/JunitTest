package com.org.data;

import com.alibaba.druid.util.StringUtils;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class PositionData {

    private String position_id;

    private String position_name;

    private String rank;

    private String rank_name;

    private String org_name;

    private String org_id;

    private String job_category;


    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("org_name","机构名称");
        titleMap.put("position_name","岗位名称");
        titleMap.put("rank_name","职级");

    }

    @Override
    public int hashCode() {
        return Objects.hash(position_name);
    }

    @Override
    public boolean equals(Object obj) {
        PositionData other = (PositionData) obj;
        return StringUtils.equals(other.getPosition_name(),this.position_name);
    }



}
