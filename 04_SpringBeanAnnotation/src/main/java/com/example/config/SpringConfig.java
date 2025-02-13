package com.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.example")
@PropertySource("classpath:jdbc.properties")
// 配置需要扫描的 mapper 接口目录
@MapperScan("com.example.mapper")
public class SpringConfig {

}
