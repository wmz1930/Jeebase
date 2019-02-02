package com.jeebase.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: SpringbootApplication
 * @Description: 指定项目为springboot，由此类当作程序入口，自动装配 web 依赖的环境
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:01:44
 */
@SpringBootApplication
@MapperScan("com.jeebase.*.*.mapper")
@ComponentScan(basePackages = "com.jeebase.*.*.service")
@ComponentScan(basePackages = "com.jeebase.*.*.aspect")
@ComponentScan(basePackages = "com.jeebase.*.*.component")
@ComponentScan(basePackages = "com.jeebase.*.*.controller")
@EnableCaching
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
