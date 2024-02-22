package com.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.utils.ParamsJSRValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: costx
 * @description:
 * @author: wangpeng
 * @create: 2020-09-08 11:47
 **/
@Component
@Slf4j
public class CIPHandlerConfig implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static final List<String> packageNames = CollUtil.newArrayList("com.factory");

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory slb) throws BeansException {
        CIPHandlerContext context = new CIPHandlerContext();
        Set<Class<?>> classes=CollUtil.newHashSet();
        for (String packageName : packageNames) {
            Set<Class<?>> classzes = ClassScanner.scanPackageByAnnotation(packageName, CIPHandler.class);
            classes.addAll(classzes);
        }
        classes.add(Cat.class);
        for (Class<?> aClass : classes) {
            CIPHandler handler = aClass.getAnnotation(CIPHandler.class);
            CIPResponseMsg msg= ParamsJSRValidator.validate(handler,CIPResponseMsg.class);
            if (ObjectUtil.isNotNull(msg)) {
                continue;
            }
            String group = handler.group();
            String[] handlerTypes = handler.handlerType();
            for (String handlerType : handlerTypes) {
                String key = StrBuilder.create().append(group).append(StrUtil.C_UNDERLINE).append(handlerType).toString();
                context.add(aClass, key);
            }
        }

        slb.registerSingleton("context", context);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
    public static <T> T getBean(Class<T> clazz){
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(JSON.toJSONString(beanDefinitionNames));
        Object cat = context.getBean("cat");
        return context.getBean(clazz);
    }
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context=ac;
    }
}
