package com.squirrel.tmsp_hand_book_doc.event;


import com.squirrel.AbstractStateEvent;
import com.yd.utils.common.StringUtils;

public enum  BookDocStatusEvent implements AbstractStateEvent {

        BACK("com.squirrel.test.SquirelTest-test","退回"),

        CONFIRM_ARRIVE("com.dy.tmsp.hand.service.impl.TMSP_hand_book_docServiceImpl-confirmArrive","确认约车到达(短驳)"),

        RECEIVE_FAIL("com.dy.tmsp.hand.service.impl.TMSP_hand_book_docServiceImpl-recieveFail","接货失败(接货)"),

        RECEIVE_SUCESS("com.dy.tmsp.hand.service.impl.TMSP_hand_book_docServiceImpl-recieveSucess","接货成功(接货)"),

        SAVE("com.dy.tmsp.hand.service.impl.TMSP_hand_trans_docServiceImpl-saveData","派车"),

    ;

        public static final String E_BACK="BACK";

        public static final String E_CONFIRM_ARRIVE="CONFIRM_ARRIVE";

        public static final String E_RECEIVE_FAIL="RECEIVE_FAIL";

        public static final String E_RECEIVE_SUCESS="RECEIVE_SUCESS";

        public static final String E_SAVE="SAVE";

     


    private String methodName;
    private String name;
    BookDocStatusEvent(String methodName,String name) {

        this.methodName = methodName;
        this.name=name;

    }


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getCode(){
        return this.name();
    }



    /**
     * 通过方法名获取event枚举实例
     */
    @Override
    public Object getByStr(String methodName) {
        if (StringUtils.isBlank(methodName)) {
            return null;
        }
         for (BookDocStatusEvent event :BookDocStatusEvent.values()) {
            if (methodName.equals(event.getMethodName())) {
                return event;
            }
        }
        return null;
    }


}

