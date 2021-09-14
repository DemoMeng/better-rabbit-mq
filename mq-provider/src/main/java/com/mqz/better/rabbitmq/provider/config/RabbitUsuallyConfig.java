package com.mqz.better.rabbitmq.provider.config;

import com.mqz.better.rabbitmq.common.constants.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/10 4:00 下午
 * @Description  初始化创建队列和交换机
 * @About： https://github.com/DemoMeng
 */
@Configuration
public class RabbitUsuallyConfig {

    /**
     * 创建队列和交换机，此处不应该创建 ConnectionFactory 和 RabbitAdmin，应该在 application.properties 中设置用户名、密码、host、端口、虚拟主机即可。
     */


//    @Bean
//    public ConnectionFactory connectionFactory(){
//        return new CachingConnectionFactory();
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(){
//        return new RabbitAdmin(connectionFactory());
//    }

    /**
     *
     * 交换机用同一个
     */
//    @Bean(Constant.DEMO_EXCHANGE)
//    public Exchange createExchange(){
//        return new TopicExchange(Constant.DEMO_EXCHANGE,true,false);
//    }




    /**
     * usually队列
     */
    @Bean(Constant.usually_queue)
    public Queue createQueue1() {
        return new Queue(Constant.usually_queue,true);
    }
    @Bean(Constant.usually_exchange)
    public TopicExchange createExchange2(){
        return new TopicExchange(Constant.usually_exchange,true,false);
    }
    @Bean
    public Binding queueBinding2(@Qualifier(Constant.usually_queue) Queue queue,
                                 @Qualifier(Constant.usually_exchange) TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(Constant.usually_routing_key);
    }



    /**
     * 序列化机制为JSON
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }



}
