package com.mqz.better.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 版权所有  copyright© 蒙大拿
 *
 * @author mqz
 * @description
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.mqz.better.rabbitmq.consumer.**",
        "com.mqz.mars.**" //需要配置扫描到mars的包，否则无法读取到swagger配置
})
public class MqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApplication.class, args);
    }

}
