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
     * double check
     *   配置手动确认，和yml配置一样可以省略，为了解决：
     *
     *   2021-09-10 11:01:06.629 ERROR 29350 --- [155.180.57:5672] o.s.a.r.c.CachingConnectionFactory:
     *      Shutdown Signal: channel error; protocol method: #method<channel.close>
     *      (reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'type' for exchange 'xbNotify-exchange' in vhost '/': received 'topic' but current is ''x-delayed-message'', class-id=40, method-id=10)
     *
     *
     *
     */
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        return factory;
//    }



    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }


    // 延迟投递----开始------

    @Bean
    public TopicExchange lazyTopicExchange(){
        Map<String, Object> pros = new HashMap<>();
        //设置交换机支持延迟消息推送
        //pros.put("x-delayed-message", "topic");
        TopicExchange exchange = new TopicExchange(Constant.lazy_exchange, true, false, pros);
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Queue lazyQueue(){
        return new Queue(Constant.lazy_queue, true);
    }

    @Bean
    public Binding lazyBinding(){
        return BindingBuilder.bind(lazyQueue()).to(lazyTopicExchange()).with(Constant.lazy_routing_key);
    }
    // 延迟投递----结束------






}
