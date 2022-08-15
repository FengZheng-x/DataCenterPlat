package com.xzz.common.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    /**
     * 校验异常处理器
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
