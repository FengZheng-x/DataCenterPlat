package com.xzz.common.exception;

import com.xzz.common.enums.ResponseEnum;
import org.hibernate.exception.ConstraintViolationException;

public class ValidateException extends ConstraintViolationException {

    public ValidateException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage(), null, null);
    }
}
