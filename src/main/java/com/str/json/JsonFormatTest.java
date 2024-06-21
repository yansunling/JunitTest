package com.str.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.javaBuild.crmx.data.CrmxStoreCustVisitingPO;

public class JsonFormatTest {
    public static void main(String[] args) throws Exception{
        CrmxStoreCustVisitingPO customerPO=new CrmxStoreCustVisitingPO();
        customerPO.setCustomer_id("33");
        customerPO.setCargo_logistics("33");
        customerPO.setClaim_flag("33");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(customerPO);
        System.out.println(json);
    }

}
