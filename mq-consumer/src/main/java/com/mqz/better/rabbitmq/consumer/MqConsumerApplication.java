package com.mqz.better.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 需要配置扫描到mars的包，否则无法读取到swagger配置
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.mqz.better.rabbitmq.consumer.**",
        "com.mqz.mars.**"
})
public class MqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApplication.class, args);
    }

}
