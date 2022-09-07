USE bigdata;

DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user
(
    `user_id`     INT         NOT NULL AUTO_INCREMENT COMMENT '用户ID（自增主键）',
    `username`    VARCHAR(20) NOT NULL COMMENT '用户名（唯一约束）',
    `password`    VARCHAR(32) NOT NULL COMMENT '加密后的密码',
    `salt`        VARCHAR(36) NOT NULL COMMENT '盐值',
    `phone`       VARCHAR(20) NOT NULL COMMENT '电话号码',
    `email`       VARCHAR(30) NOT NULL COMMENT '电子邮箱地址',
    `gender`      TINYINT(1)  NOT NULL COMMENT '性别',
    `avatar`      VARCHAR(50) NOT NULL COMMENT '头像',
    `address`     VARCHAR(50) NOT NULL COMMENT '地址',
    `is_delete`   TINYINT(1)  NOT NULL COMMENT '是否删除',
    `create_time` DATETIME    NOT NULL COMMENT '创建时间',
    `update_time` DATETIME    NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`user_id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_sku`;

CREATE TABLE `t_sku`
(
    `skuId`       BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'skuId',
    `item_id`     BIGINT       NOT NULL COMMENT '商品ID',
    `item_name`   VARCHAR(32)  NOT NULL COMMENT '商品名称',
    `max`         INT(11)      NOT NULL COMMENT '购物车单种sku允许最大个数',
    `price`       BIGINT(20)   NOT NULL COMMENT 'sku单价',
    `properties`  VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'sku规格',
    `shop_id`     BIGINT       NOT NULL COMMENT '店铺ID',
    `shop_name`   VARCHAR(32)  NOT NULL COMMENT '店铺名',
    `status`      INT(11)      NOT NULL COMMENT 'sku状态：-1 下架; 0 删除; 1 在架',
    `stock`       INT(11)      NOT NULL COMMENT 'sku库存',
    `create_time` DATETIME     NOT NULL COMMENT '日志-创建时间',
    `update_time` DATETIME     NOT NULL COMMENT '日志-最后修改时间',
    PRIMARY KEY (`skuId`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order`
(
    `order_id`     INT            NOT NULL AUTO_INCREMENT COMMENT '订单 ID',
    `code`         VARCHAR(200)   NOT NULL COMMENT '订单代码',
    `type`         INT            NOT NULL COMMENT '订单类型',
    `amount`       DECIMAL(10, 2) NOT NULL COMMENT '订单数量',
    `payment_type` INT            NOT NULL COMMENT '支付方式',
    `status`       INT            NOT NULL COMMENT '订单状态',
    `postage`      DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '邮费',
    `weight`       INT            NOT NULL DEFAULT 0 COMMENT '重量（单位：克）',
    `create_time`  DATETIME       NOT NULL COMMENT '日志-创建时间',
    `update_time`  DATETIME       NOT NULL COMMENT '日志-最后修改时间',
    PRIMARY KEY (`order_id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_order_sku`;

CREATE TABLE `t_order_sku`
(
    `id`           INT            NOT NULL AUTO_INCREMENT COMMENT '订单商品 ID',
    `order_id`     INT            NOT NULL COMMENT '订单 ID',
    `sku_id`     INT            NOT NULL COMMENT 'SKU ID',
    `price`         VARCHAR(200)   NOT NULL COMMENT '商品价格',
    `num`         INT            NOT NULL COMMENT '商品数量',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_order_pay`;

CREATE TABLE `t_order_pay`
(
    `id`           INT            NOT NULL AUTO_INCREMENT COMMENT '订单支付 ID',
    `order_id`     INT            NOT NULL  COMMENT '订单 ID',
    `pay_id`     INT            NOT NULL  COMMENT '支付 ID',
    `pay_status`     INT            NOT NULL  COMMENT '支付状态',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `t_shop_cart`;

CREATE TABLE `t_shop_cart`
(
    `id`          BIGINT     NOT NULL AUTO_INCREMENT COMMENT '购物车ID（自增主键）',
    `user_id`     BIGINT     NOT NULL COMMENT '用户ID',
    `sku_id`      BIGINT     NOT NULL COMMENT '商品ID',
    `count`       INT        NOT NULL DEFAULT 0 COMMENT '商品数量',
    `create_time` DATETIME   NOT NULL COMMENT '创建时间',
    `update_time` DATETIME   NOT NULL COMMENT '最后修改时间',
    `selected`    TINYINT(1) NOT NULL DEFAULT 0 COMMENT '勾选状态',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;