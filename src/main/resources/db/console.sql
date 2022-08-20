USE bigdata;

DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user
(
    `userId`      INT AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    `password`    VARCHAR(32) NOT NULL COMMENT '用户密码',
    `phone`       VARCHAR(20) COMMENT '电话号码',
    `salt`        CHAR(36)    NOT NULL COMMENT '盐值',
    `email`       VARCHAR(30) COMMENT '电子邮箱',
    `gender`      INT COMMENT '性别:0-女，1-男',
    `avatar`      VARCHAR(50) COMMENT '头像',
    `address`     VARCHAR(255) COMMENT '地址',
    `is_delete`   INT COMMENT '是否删除：0-未删除，1-已删除',
    `create_time` DATETIME    NOT NULL COMMENT '日志-创建时间',
    `update_time` DATETIME    NOT NULL COMMENT '日志-最后修改时间',
    PRIMARY KEY (`userId`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_sku`;

CREATE TABLE `t_sku`
(
    `skuId`       BIGINT(20)                       NOT NULL AUTO_INCREMENT COMMENT 'skuId',
    `item_id`     BIGINT(20)                       NOT NULL COMMENT '商品ID',
    `item_name`   VARCHAR(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '商品名称',
    `max`         INT(11)                          NOT NULL COMMENT '购物车单种sku允许最大个数',
    `price`       BIGINT(20)                       NOT NULL COMMENT 'sku单价',
    `properties`  VARCHAR(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'sku规格',
    `shop_id`     BIGINT(20)                       NOT NULL COMMENT '店铺ID',
    `shop_name`   VARCHAR(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '店铺名',
    `status`      INT(11)                          NOT NULL COMMENT 'sku状态：-1 下架; 0 删除; 1 在架',
    `stock`       INT(11)                          NOT NULL COMMENT 'sku库存',
    `create_time` DATETIME                         NOT NULL,
    `update_time` DATETIME                         NOT NULL,
    PRIMARY KEY (`skuId`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;