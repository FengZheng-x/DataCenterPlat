package com.xzz.common.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    /**
     * 正常
     */
    NORMAL(1, "NORMAL"),

    /**
     * 已删除
     */
    DELETED(0, "DELETED"),

    /**
     * 已下架
     */
    PROHIBIT(-1, "PROHIBIT");

    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
