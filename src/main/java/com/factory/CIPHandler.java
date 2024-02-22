package com.factory;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @program: costx
 * @description:
 * @author: wangpeng
 * @create: 2020-09-08 11:45
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface CIPHandler {
    @NotBlank
    String group();

    @NotNull
    String[] handlerType();
}
