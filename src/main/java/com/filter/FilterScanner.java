package com.filter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;

@WebListener
public class FilterScanner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取 ServletContext
        ServletContext servletContext = sce.getServletContext();

        // 获取所有 Filter 注册信息
        Map<String, ? extends FilterRegistration> filterRegistrations = servletContext.getFilterRegistrations();

        // 遍历并打印 Filter 名称
        filterRegistrations.forEach((name, registration) -> {
            System.out.println("Filter Name: " + name);
            System.out.println("Filter Class: " + registration.getClassName());
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 清理资源（如果需要）
    }
}
