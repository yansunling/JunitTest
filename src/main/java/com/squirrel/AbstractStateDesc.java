package com.squirrel;

import org.squirrelframework.foundation.fsm.UntypedStateMachine;


public interface AbstractStateDesc <T extends UntypedStateMachine,S,E extends AbstractStateEvent,C extends AbstractStateContext> {


    public S getFromStatus();
    public S getStatus();
    public E getEvent();
    public T getMachine();
    public C getCtx();
    public boolean getIsRight();
    public void setFromStatus(S status);
    public void setStatus(S status);
    public void setEvent(E event);
    public void setMachine(T machine);
    public void setCtx(C ctx);
    public void setIsRight(boolean isRight);
}


