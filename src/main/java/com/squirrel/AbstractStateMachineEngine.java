package com.squirrel;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.yd.common.cache.Cacheable;
import com.yd.common.exception.CIPRuntimeException;
import com.yd.common.exception.CIPServiceException;
import com.yd.common.mybatis.CJBaseMapper;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.utils.common.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.util.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractStateMachineEngine<T extends UntypedStateMachine, S, E extends AbstractStateEvent, C extends AbstractStateContext, P, D> implements ApplicationContextAware {


    protected ApplicationContext applicationContext;
    protected UntypedStateMachineBuilder stateMachineBuilder = null;
    protected Class<E> eventClazz = null;
    protected Class<P> poClazz = null;
    protected Class<D> daoClazz = null;

    @SuppressWarnings("unchecked")
    public AbstractStateMachineEngine() {
        //识别泛型参数
        Class<T> genericType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        eventClazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        poClazz = (Class<P>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[4];
        daoClazz = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[5];
        stateMachineBuilder = StateMachineBuilderFactory.create(genericType, ApplicationContext.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean checkCanAcceptNoDb(E trigger, Cacheable stateEnum) {
        //先检查调用栈
        trigger = getEvent(trigger);

        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }
        S initialState =(S)stateEnum.codeType();
        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);

        return stateMachine.canAccept(trigger);
    }

    public boolean checkCanAccept(E trigger, String fieldName, String... keys) {

        if (StringUtils.isBlank(fieldName)) {
            throw new CIPServiceException(new CIPErrorCode(70000002, "请传入fieldName"));
        }
        if (null == keys) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请传入keys"));
        }

        //先检查调用栈
        trigger = getEvent(trigger);

        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }
        BeanMap beanMap=getInitialState(fieldName, keys);
        S initialState=null;
        if(EnumUtil.isEnum(beanMap.get(fieldName))){
            initialState =(S) (((Cacheable)beanMap.get(fieldName)).codeType());
        }else{
            initialState = (S)beanMap.get(fieldName);
        }
        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);

        return stateMachine.canAccept(trigger);

    }

    public AbstractStateDesc fire(E trigger, AbstractStateDesc toReturn, String fieldName, String... keys) {

        if (StringUtils.isBlank(fieldName)) {
            throw new CIPServiceException(new CIPErrorCode(70000002, "请传入fieldName"));
        }
        if (null == keys) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请传入keys"));
        }
        trigger = getEvent(trigger);
        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }

        AbstractStateContext context =  new AbstractStateContext();
        context.setDaoClass(daoClazz);
        context.setStatusFieldName(fieldName);
        context.setKeys(keys);
        BeanMap beanMap=getInitialState(fieldName, keys);
        context.setSerialNo( (String) beanMap.get("serial_no"));
        S initialState=null;
        String stateDesc=null;
        if(EnumUtil.isEnum(beanMap.get(fieldName))){
            initialState =(S) (((Cacheable)beanMap.get(fieldName)).codeType());
            stateDesc=((Cacheable)beanMap.get(fieldName)).codeName();
        }else{
            initialState = (S)beanMap.get(fieldName);
            stateDesc=(String)beanMap.get(fieldName);
        }
        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);
        if (stateMachine.canAccept(trigger)) {

            //声明式事务
            DataSourceTransactionManager transactionManager = applicationContext.getBean("transactionManager", DataSourceTransactionManager.class);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                stateMachine.fire(trigger, context);
                transactionManager.commit(status);
                if (null != toReturn) {
                    toReturn.setFromStatus(initialState);
                    toReturn.setStatus(stateMachine.getCurrentState());
                    toReturn.setMachine(stateMachine);
                    toReturn.setEvent(trigger);
                }
                return toReturn;
            } catch (Exception ex) {
                transactionManager.rollback(status);
                throw ex;
            }

        } else {
            throw new CIPServiceException(new CIPErrorCode(70000001, "当前状态["+stateDesc+"]不允许做" + trigger.getName() + "操作"+",数据唯一标识为：" + StrUtil.join(",",keys)+"，错数码："));

        }
    }


    public AbstractStateDesc fireNoDb(E trigger, AbstractStateDesc toReturn, Cacheable stateEnum) {

        trigger = getEvent(trigger);
        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }
        S initialState =(S)stateEnum.codeType();
        String stateDesc=stateEnum.codeName();
        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);
        if (stateMachine.canAccept(trigger)) {

            //声明式事务
            DataSourceTransactionManager transactionManager = applicationContext.getBean("transactionManager", DataSourceTransactionManager.class);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                stateMachine.fire(trigger);
                transactionManager.commit(status);
                if (null != toReturn) {
                    toReturn.setFromStatus(initialState);
                    toReturn.setStatus(stateMachine.getCurrentState());
                    toReturn.setMachine(stateMachine);
                    toReturn.setEvent(trigger);
                }
                return toReturn;
            } catch (Exception ex) {
                transactionManager.rollback(status);
                throw ex;
            }

        } else {
            throw new CIPServiceException(new CIPErrorCode(70000001, "当前状态["+stateDesc+"]不允许做" + trigger.getName() + "操作"+"，错数码："));

        }
    }

    public AbstractStateDesc checkAndReturnNoDb(E trigger, AbstractStateDesc toReturn, Cacheable stateEnum) {
        trigger = getEvent(trigger);
        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }
        S initialState =(S)stateEnum.codeType();
        String stateDesc=stateEnum.codeName();
        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);
        if (stateMachine.canAccept(trigger)) {
            try {
                stateMachine.fire(trigger);
                if (null != toReturn) {
                    toReturn.setFromStatus(initialState);
                    toReturn.setStatus(stateMachine.getCurrentState());
                    toReturn.setMachine(stateMachine);
                    toReturn.setEvent(trigger);
                    toReturn.setIsRight(true);
                }
                return toReturn;
            } catch (Exception ex) {
                throw ex;
            }

        }  else {
            throw new CIPServiceException(new CIPErrorCode(70000001, "当前状态["+stateDesc+"]不允许做" + trigger.getName() + "操作"+"，错数码："));

        }
    }

    public AbstractStateDesc checkAndReturn(E trigger, AbstractStateDesc toReturn, String fieldName, String... keys) {

        if (StringUtils.isBlank(fieldName)) {
            throw new CIPServiceException(new CIPErrorCode(70000002, "请传入fieldName"));
        }
        if (null == keys) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请传入keys"));
        }
        trigger = getEvent(trigger);
        if (null == trigger) {
            throw new CIPServiceException(new CIPErrorCode(70000003, "请检查状态机event设计"));
        }
        BeanMap beanMap=getInitialState(fieldName, keys);
        S initialState=null;
        String stateDesc=null;
        if(EnumUtil.isEnum(beanMap.get(fieldName))){
            initialState =(S) (((Cacheable)beanMap.get(fieldName)).codeType());
            stateDesc=((Cacheable)beanMap.get(fieldName)).codeName();
        }else{
            initialState = (S)beanMap.get(fieldName);
            stateDesc=(String)beanMap.get(fieldName);
        }

        T stateMachine = stateMachineBuilder.newUntypedStateMachine(
                initialState,
                //暂时开启debug进行日志trace
                StateMachineConfiguration.create().enableAutoTerminate(true).enableAutoStart(true),
                //注入applicationContext
                applicationContext);


        if (stateMachine.canAccept(trigger)) {


            try {
                stateMachine.fire(trigger);
                if (null != toReturn) {
                    toReturn.setFromStatus(initialState);
                    toReturn.setStatus(stateMachine.getCurrentState());
                    toReturn.setMachine(stateMachine);
                    toReturn.setEvent(trigger);
                    toReturn.setIsRight(true);
                }
                return toReturn;
            } catch (Exception ex) {
                throw ex;
            }

        }  else {
            throw new CIPServiceException(new CIPErrorCode(70000001, "当前状态["+stateDesc+"]不允许做" + trigger.getName() + "操作"+"，错数码："));

        }
    }

    //方法调用深度 起始层
    private final static int START_TRACE = 4;
    //方法调用深度 结束层
    private final static int END_TRACE = 7;

    private E getEvent(E trigger) {
        for (int i = START_TRACE; i < END_TRACE; i++) {
            String className = Thread.currentThread().getStackTrace()[i].getClassName();
            String methodName = Thread.currentThread().getStackTrace()[i].getMethodName();
            if (null == trigger) {
                try {
                    Method m = eventClazz.getMethod("getByStr", String.class);
                    Object[] enventEnums = eventClazz.getEnumConstants();
                    try {
                        trigger = (E) m.invoke(enventEnums[0], className + "-" + methodName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (null != trigger) {
                        break;
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        return trigger;
    }

    private BeanMap getInitialState(String fieldName, String[] keys) {
        P po = null;
        if (CJBaseMapper.class.isAssignableFrom(daoClazz)) {
            po = (P) ((CJBaseMapper) (this.applicationContext.getBean(daoClazz))).selectByKey(keys);
        } else {
            Method method = BeanUtils.findDeclaredMethodWithMinimalParameters(daoClazz, "selectByKey");
            po = (P) ReflectUtils.invoke(method, this.applicationContext.getBean(daoClazz), keys);
        }
        if (null == po) {
            throw new CIPRuntimeException(CIPErrorCode.ERROR_RECORD_NOT_EXISTS);
        }
        BeanMap beanMap = BeanMap.create(po);
        return BeanMap.create(po);
    }

}
