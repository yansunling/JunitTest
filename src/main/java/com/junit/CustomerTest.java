package com.junit;


import com.other.redis.CRMXSerialNoUtils;
import com.yd.common.busi.builder.BusiNoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CustomerTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Test
    public void getCustomerID() throws Exception{
        String customerId;

            customerId = BusiNoUtils.getCodeNo("crm_customer_id");
            if (null == customerId || customerId.length() > 7) {
                customerId = CRMXSerialNoUtils.getSerial(CRMXSerialNoUtils.CRM_CUSTOMER_ID);
            }



        System.out.println(customerId);

    }


}
