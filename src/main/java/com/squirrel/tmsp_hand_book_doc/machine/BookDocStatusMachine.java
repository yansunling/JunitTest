package com.squirrel.tmsp_hand_book_doc.machine;


import com.squirrel.AbstractStateContext;
import com.squirrel.AbstractStateMachine;
import com.squirrel.constants.BOOK_DOC_STATUS;
import com.squirrel.tmsp_hand_book_doc.event.BookDocStatusEvent;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.annotation.*;

/**
  自动生成，请勿编辑
**/
@StateMachineParameters(stateType = String.class, eventType = BookDocStatusEvent.class,
//StateMachineContext 自定义上下文，用来传递数据
contextType = AbstractStateContext.class)
@States({
            //已约车待派车
        @State(name = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_1) , 

            //已派车待到达
        @State(name = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3) , 

            //已退回
        @State(name = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_2) ,

            //已完结
        @State(name = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_4) 

    })
@Transitions({
      @Transit(from = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_1, to = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_2, on =BookDocStatusEvent.E_BACK ) ,

      @Transit(from = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_1, to = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3, on =BookDocStatusEvent.E_SAVE ) , 

      @Transit(from = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3, to = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_4, on =BookDocStatusEvent.E_RECEIVE_FAIL ) , 

      @Transit(from = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3, to = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_4, on =BookDocStatusEvent.E_RECEIVE_SUCESS ) , 

      @Transit(from = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3, to = BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_4, on =BookDocStatusEvent.E_CONFIRM_ARRIVE ) 


})
public class BookDocStatusMachine extends AbstractStateMachine {
    public BookDocStatusMachine(ApplicationContext context){
        super(context);
    }

}
