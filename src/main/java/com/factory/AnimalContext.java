package com.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.dy.components.logs.error.spring.GlobalErrorInfoDynamic;

import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

@Service
public class AnimalContext<T> {
    // 扫描包
    private static final String packageName = "com.factory";
    // 扫描对象
    private static final Class<?> selectClass = AnimalFactory.class;

    private static <T> Class<?> getT(Class<T> clases) {
        Set<Class<?>> classz = ClassUtil.scanPackageBySuper(packageName, selectClass);
        for (Class<?> aClass : classz) {
            Type t = aClass.getGenericSuperclass();
            Class<T> clazz = getClass(t);
            if (clazz == clases) {
                return aClass;
            }
        }
        return null;
    }

    private static <T> Class<T> getClass(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        for (Type p : ((ParameterizedType) type).getActualTypeArguments()) {
            if (p instanceof Class) return (Class<T>) p;
            return getClass(p);
        }
        return null;
    }



    private static <T> Object getAttribute(Class<T> t) {

        Class<?> clazz = getT(t);
        if (ObjectUtil.isNull(clazz)) {
            return null;
        }
        Object obj = CIPHandlerConfig.getBean(clazz);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }

        return obj;
    }

    /**
     * <pre>
     * 1、开单          BMSP_should_tmsp_order_billVO               &#13;
     * 2、到达联打印     BMSP_should_tmsp_order_arrPrintVO           &#13;
     * 2、始发外发       BMSP_should_tmsp_order_externalVO           &#13;
     * 3、改单
     * 4、改单校验
     * 5、开单
     * 6、开单作废校验
     * 7、开单作废
     * 8、派送APP交接核销
     * 9、到达联收款
     * 10、应收对账单收款
     * 11、派送APP收款
     * 12、应收单收款
     * 13、预收单收款
     * 14、收款刷新
     * </pre>
     *
     * @param t
     * @return
     */
    public String should(T t) throws Exception {
        AnimalFactory<T> service = (AnimalFactory<T>) getAttribute(t.getClass());
        if (ObjectUtil.isNull(service)) {
            return "error";
        }
        return service.food(t);

    }
}
