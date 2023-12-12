package com.squirrel.test;


import com.squirrel.constants.BOOK_DOC_STATUS;
import com.squirrel.tmsp_hand_book_doc.desc.BookDocStatusDesc;
import com.squirrel.tmsp_hand_book_doc.engine.BookDocStatusEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SquirelTest implements ApplicationContextAware {


    @Autowired
    private BookDocStatusEngine bookDocStatusEngine;

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Test
    public  void test() throws Exception{
        BookDocStatusDesc nextState=bookDocStatusEngine.checkAndReturnNoDb(BOOK_DOC_STATUS.BOOK_DOC_STATUS_1);
        System.out.println(nextState.getStatus());

    }


}
