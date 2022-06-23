package com.my.logTest;

import com.word.data.TMSP_claims_oa_bearData;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class LogTest {





    public static void main(String[] args) throws Exception{

        TMSP_claims_oa_bearData bearData=new TMSP_claims_oa_bearData();
        System.out.println(Optional.ofNullable(bearData).isPresent());



    }
}
