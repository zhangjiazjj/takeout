package org.zj.takeout;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//启动日志
@Slf4j
//启动类注解
@SpringBootApplication
//开启Mapper扫描注解
@MapperScan("org.zj.takeout.mapper")
//开启servlet过滤器
@ServletComponentScan
public class TakeoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeoutApplication.class,args);
        log.info("项目启动成功");
    }
}
