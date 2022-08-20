package com.xzz.dcp.common.enums;

import lombok.Getter;

@Getter
public enum UserEnum {
    /**
     * 用户被删除
     */
    DELETED(0, "Deleted"),

    /**
     * 用户未被删除
     */
    NORMAL(1, "Normal");

    private final Integer code;

    private final String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
