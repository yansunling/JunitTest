package com.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: costx
 * @description:
 * @author: wangpeng
 * @create: 2020-09-08 11:52
 **/
public class CIPHandlerContext<T> {

    private final Map<String, Class<T>> classMap = new HashMap<>();

    public void add(Class<T> t, String... key) {
        classMap.put(key(key), t);
    }

    public T getObject(String group, String handlerType) {
        Assert.notBlank(group, "处理组别不能为空,请检查参数");
        Assert.notBlank(handlerType, "处理类型不能为空,请检查参数");
        String key = key(group, handlerType);
        if (classMap.containsKey(key)) {
            return CIPHandlerConfig.getBean(classMap.get(key));
//            return ApplicationUtils.getBeanByClass(classMap.get(key));
        }
        return null;
    }

    private static String key(String... key) {
        StrBuilder sb = StrBuilder.create();
        for (String s : key) {
            sb.append(s).append(StrUtil.C_UNDERLINE);
        }
        return sb.subString(0, sb.length() - 1);
    }
}
