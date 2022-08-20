package com.xzz.dcp.service.ex;

import com.xzz.dcp.common.enums.ResponseEnum;

import javax.validation.ConstraintViolationException;

/**
 * 自定义校验异常，可以被本类异常处理器catch
 * 优先本类的处理器，如果没有本类的，往上一层。
 */
public class ValidateException extends ConstraintViolationException {

    public ValidateException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage(), null);
    }

}
