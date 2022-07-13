package com.word.constansts;

public interface Cacheable<T> {
    String codeType();

    String codeName();

    default T getValue(){
        return (T)this;
    }
}
