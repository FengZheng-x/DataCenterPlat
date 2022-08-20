package com.xzz.dcp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching // 启用缓存
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true) // 暴露代理
// MapperScan 注解指定当前项目中的 Mapper 接口路径的位置，在项目启动的时候会自动加载所有的接口文件
@MapperScan("com.xzz.dcp.mapper")
public class DCPApplication {
    public static void main(String[] args) {
        SpringApplication.run(DCPApplication.class, args);
    }
}
