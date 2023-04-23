package com.word.constansts;

public interface EnumService<T> {
    String codeType();

    String codeName();

    String nameToCode(String name);
}
