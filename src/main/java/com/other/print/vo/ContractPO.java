package com.other.print.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ContractPO {

    private String serial_no;

    private String content_html;


    private String content_value;

    private Timestamp create_time;

}
