package com.squirrel;

import com.yd.common.mybatis.CJBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;
import org.squirrelframework.foundation.util.ReflectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractStateMachine extends AbstractUntypedStateMachine {
    protected ApplicationContext context;
    public AbstractStateMachine(ApplicationContext context){
        this.context = context;
    }

    private Logger logger = LoggerFactory.getLogger(AbstractStateMachine.class);
    @Override
    protected void afterTransitionCompleted(Object fromState, Object toState, Object event, Object context) {
        if (context instanceof AbstractStateContext && toState instanceof String && null!=context) {
            AbstractStateContext stateMachineContext = (AbstractStateContext)context;
            Map<String, Object> toUpdateSateMap = new HashMap<>();
            toUpdateSateMap.put(stateMachineContext.getStatusFieldName(), toState);
            if(CJBaseMapper.class.isAssignableFrom(stateMachineContext.getDaoClass())){
                ((CJBaseMapper)(this.context.getBean(stateMachineContext.getDaoClass()))).updateByMapDefault(toUpdateSateMap,stateMachineContext.getSerialNo());
            }else{
                Method method= BeanUtils.findDeclaredMethodWithMinimalParameters(stateMachineContext.getDaoClass(),"updateByMapDefault");
                ReflectUtils.invoke(method,this.context.getBean(stateMachineContext.getDaoClass()),new Object[]{toUpdateSateMap,stateMachineContext.getSerialNo()});
            }
            logger.warn("from {},to{},on {} at {} persist success",fromState,toState,event,context);
        }else {
            logger.warn("from {},to{},on {} at {} after transition completed",fromState,toState,event,context);
        }

    }


}


