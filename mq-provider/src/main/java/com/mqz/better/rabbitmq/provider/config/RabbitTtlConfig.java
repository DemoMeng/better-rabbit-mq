package com.mqz.better.rabbitmq.provider.config;

import com.mqz.better.rabbitmq.common.constants.Constant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/14 9:24 上午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Configuration
public class RabbitTtlConfig {

    /**
     * ttl队列
     */
    @Bean(Constant.TTL_QUEUE)
    public Queue createQueue() {
        return new Queue(Constant.TTL_QUEUE,true);
    }


    @Bean(Constant.TTL_EXCHANGE)
    public TopicExchange createExchange(){
        return new TopicExchange(Constant.TTL_EXCHANGE,true,false);
    }

    @Bean
    public Binding queueRoutingKeyExchangeBinding(@Qualifier(Constant.TTL_QUEUE) Queue queue,
                                                  @Qualifier(Constant.TTL_EXCHANGE) TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(Constant.TTL_ROUTING_KEY);
    }



}
