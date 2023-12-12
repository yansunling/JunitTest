package com.squirrel.tmsp_hand_book_doc.desc;


import com.squirrel.AbstractStateContext;
import com.squirrel.AbstractStateDesc;
import com.squirrel.AbstractStateEvent;
import com.squirrel.tmsp_hand_book_doc.event.BookDocStatusEvent;
import com.squirrel.tmsp_hand_book_doc.machine.BookDocStatusMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

public class BookDocStatusDesc implements AbstractStateDesc {
    /**
    * 初始状态
    */
    private String fromStatus;
    /**
    * 迁移后当前状态
    */
    private String status;
    /**
    * 事件
    */
    private BookDocStatusEvent event;
    /**
    * 状态机实例引用
    */
    private BookDocStatusMachine machine;
    /**
    * 上下文实例引用
    */
    private AbstractStateContext ctx;
    /**
    * 检查状态是否合法标识
    */
    private boolean isRight;
    @Override
    public String getFromStatus() {
        return this.fromStatus;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public BookDocStatusEvent getEvent() {
        return this.event;
    }

    @Override
    public BookDocStatusMachine getMachine() {
        return this.machine;
    }

    @Override
    public AbstractStateContext getCtx() {
        return this.ctx;
    }

    @Override
    public boolean getIsRight() {
        return isRight;
    }

    @Override
    public void setFromStatus(Object status) {
        this.fromStatus = (String) status;
    }

    @Override
    public void setStatus(Object status) {
        this.status = (String) status;
    }

    @Override
    public void setEvent(AbstractStateEvent event) {
        this.event = (BookDocStatusEvent) event;
    }

    @Override
    public void setMachine(UntypedStateMachine machine) {
        this.machine = (BookDocStatusMachine) machine;
    }


    @Override
    public void setCtx(AbstractStateContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }
}
