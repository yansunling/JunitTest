package com.groovy;

import com.alibaba.fastjson.JSON;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroovyTest {
    public static void main(String[] args) throws Exception{
        //查找并创建指定脚本引擎
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        /**
         * 初始化Bindings
         * bindings 中的对象，会被当做脚本全局对象
         */
      /*  Bindings bindings = engine.createBindings();
        // 绑定参数
        bindings.put("date", new Date());
        bindings.put("option", new DefaultFunction());*/

        /**
         * 定义groovy脚本内容
         * grovvy中的字符串可以识别${}和$占位符
         */
        final String scriptContent = "def execute(输入){" +
                "    println(输入.其他费用);" +
                "    输入.其他费用=20;"+
                " println(输入.运费);"+

                "    "+
                "     switch (输入.运费<100) return 50 else return 10" +
                "}";

            // 执行脚本
            engine.eval(scriptContent);
            // 获取执行结果
            Invocable invocable = (Invocable) engine;
            // 方法入参
             Map<String,Object> param=new HashMap<>();
            param.put("其他费用",50);
            param.put("费用合计",150);
            param.put("运费",100);
            /**
             * 第一个参数：脚本方法名称
             * 第二个参数：脚本方法入参
             */
        Object result = invocable.invokeFunction("execute", param);
        System.out.println("---------------------------------------");
            System.out.println("result is: " + result);


    }
}
