package dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用于测试
 */
@Data
@Accessors(chain = true)
public class RedisTestDTO implements Serializable {
    private long itemId;

    private int count;
}
