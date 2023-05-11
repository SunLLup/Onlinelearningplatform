package com.lyj.yygh.vod;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.lyj.yygh.vod.mapper")
@ComponentScan(basePackages = "com.lyj")
public class ServiceVodApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplicationMain.class,args);
    }
}
