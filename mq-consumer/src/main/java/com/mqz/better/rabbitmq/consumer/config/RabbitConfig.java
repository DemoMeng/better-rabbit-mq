package com.mqz.better.rabbitmq.consumer.config;

import com.mqz.better.rabbitmq.common.constants.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mqz
 * @description rabbitMq 消息队列序列化配置
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Configuration
public class RabbitConfig {


    /**
     *  【1】 double check ： spring-boot-starter-amqp默认是自动签收信息的方式，消费结果签收了两次，我们代码里面是写的手动签收，但是系统还有一次自动签收，所以就想到了是不是需要配置一下让rabbitmq手动签署，就不会触发自动签收的功能
     *
     *  【2】配置手动确认，和yml配置一样可以省略，为了解决：
     *
     *   2021-09-10 11:01:06.629 ERROR 29350 --- [155.180.57:5672] o.s.a.r.c.CachingConnectionFactory:
     *      Shutdown Signal: channel error; protocol method: #method<channel.close>
     *      (reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'type' for exchange 'xbNotify-exchange' in vhost '/': received 'topic' but current is ''x-delayed-message'', class-id=40, method-id=10)
     *
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 代码设置消费方确认机制，因为spring-boot-starter-amqp默认是自动签收信息的方式，消费了两次消息
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }






}
