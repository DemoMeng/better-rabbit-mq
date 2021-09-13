package com.mqz.better.rabbitmq.provider;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableRabbit
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.mqz.better.rabbitmq.provider.**",
        "com.mqz.mars.**"
})
public class MqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqProviderApplication.class, args);
    }

}
