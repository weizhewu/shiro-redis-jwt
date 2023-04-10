package com.waltz.springshirostudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.waltz","com.waltz.springshirostudy.exception"})
@MapperScan(basePackages = {"com.waltz.springshirostudy.mapper"})
@ServletComponentScan
public class SpringShiroStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringShiroStudyApplication.class, args);
    }

}
