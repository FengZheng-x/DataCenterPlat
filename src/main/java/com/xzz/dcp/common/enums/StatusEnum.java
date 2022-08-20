package com.xzz.dcp.common.enums;

import lombok.Getter;

/**
 * 状态枚举
 */
@Getter
public enum StatusEnum {

    /**
     * 正常
     */
    NORMAL(1, "Normal"),

    /**
     * 已删除
     */
    DELETED(0, "Deleted"),

    /**
     * 下架
     */
    PROHIBIT(-1, "Prohibit");

    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
