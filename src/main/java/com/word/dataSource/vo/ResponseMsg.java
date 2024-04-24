package com.word.dataSource.vo;

import lombok.Data;

@Data
public class ResponseMsg<T> {

    public Object errorCode = 0;
    public String msg;
    public T data;

}
