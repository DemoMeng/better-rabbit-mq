package com.mqz.better.rabbitmq.provider.config;

import com.mqz.better.rabbitmq.common.constants.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/11 3:49 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Configuration
public class RabbitDeadConfig {


    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     */
    @Bean(Constant.DEAD_EXCHANGE)
    public DirectExchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(Constant.DEAD_EXCHANGE).durable(true).build();
    }

    /**
     * 声明一个死信队列.
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     */
    @Bean(Constant.DEAD_QUEUE)
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", Constant.DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key",Constant.DEAD_ROUTING_KEY);
        args.put("x-message-ttl",5000);//过期时间
        return QueueBuilder.durable(Constant.DEAD_QUEUE).withArguments(args).build();
    }

    /**
     * 定义死信队列转发队列.
     * @return the queue
     */
    @Bean(Constant.DEAD_QUEUE_RE)
    public Queue redirectQueue() {
        return QueueBuilder.durable(Constant.DEAD_QUEUE_RE).build();
    }

    /**
     * 死信路由通过 绑定键绑定到死信队列上.
     * @return the binding
     */
    @Bean
    public Binding createDeadQueueBinding(@Qualifier(Constant.DEAD_QUEUE) Queue queue,
                                          @Qualifier(Constant.DEAD_EXCHANGE) DirectExchange topicExchange){//共用同一个交换机
        return BindingBuilder.bind(queue).to(topicExchange).with(Constant.DEAD_ROUTING_KEY);
    }

    /**
     * 绑定转发队列
     */
    @Bean
    public Binding redirectBinding(@Qualifier(Constant.DEAD_QUEUE_RE) Queue queue,
                                          @Qualifier(Constant.DEAD_EXCHANGE) DirectExchange topicExchange){//共用同一个交换机
        return BindingBuilder.bind(queue).to(topicExchange).with(Constant.DEAD_ROUTING_KEY);
    }




}
