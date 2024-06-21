package com.other.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdviceTest {

    @After("execution(* com.factory.C*.*(..))||execution(* com.factory.D*.*(..))")
    public void releaseResource(JoinPoint point) {
        System.out.println("@After：模拟释放资源...");
        System.out.println("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@After：被织入的目标对象为：" + point.getTarget());

        Object[] args = point.getArgs();
        if(args!=null&&args.length>0){
            Object firstArgs = args[0];
            if(firstArgs instanceof String){
                System.out.println(firstArgs+"-------");
            }else{
                String json = JSONObject.toJSONString(firstArgs);
                System.out.println(json);
            }
        }
    }


    @Around("execution(* com.factory.C*.food(..))")
    public Object getReturnTest(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        System.out.println("around args----"+JSONObject.toJSONString(args));
        Object result = point.proceed(args);
        System.out.println("around result----"+JSONObject.toJSONString(result));
        return "吃老鼠";
    }






}
