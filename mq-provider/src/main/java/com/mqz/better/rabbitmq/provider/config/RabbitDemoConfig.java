package com.mqz.better.rabbitmq.provider.config;

import com.mqz.better.rabbitmq.common.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/14 5:25 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Configuration
@Slf4j
public class RabbitDemoConfig {


    /**
     * demo队列
     */
    @Bean(Constant.DEMO_QUEUE)
    public Queue createDemoQueue3() {
        return new Queue(Constant.DEMO_QUEUE,true);
    }

    @Bean(Constant.DEMO_EXCHANGE)
    public TopicExchange createExchange3(){
        return new TopicExchange(Constant.DEMO_EXCHANGE,true,false);
    }
    @Bean
    public Binding createDemoQueueBinding3(@Qualifier(Constant.DEMO_QUEUE) Queue queue,
                                          @Qualifier(Constant.DEMO_EXCHANGE) TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(Constant.DEMO_ROUTING_KEY);
    }


}
