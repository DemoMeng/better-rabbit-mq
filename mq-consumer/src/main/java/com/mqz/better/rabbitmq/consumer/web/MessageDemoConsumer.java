package com.mqz.better.rabbitmq.consumer.web;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 青网科技集团 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/10 4:11 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */


@Component
@Slf4j
public class MessageDemoConsumer {

    /**
     *  @RabbitListener 和@RabblitHandler组合来监听队列，当然@RabbitListener 也可以加在方法上。
     *  我们这里是创建了两个方法用来监听同一个队列，具体调用哪个方法是通过匹配方法的入参来决定的，
     *  自定义类型的消息需要标注@Payload，类要实现序列化接口
     */

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = Constant.DEMO_QUEUE, durable = "true"),
                    exchange = @Exchange(value = Constant.DEMO_EXCHANGE, type = "topic"),
                    key = "demo.callback"
            )
    )
    public void onMessage(Message message, Channel channel) throws IOException {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //手工ack
        channel.basicAck(deliveryTag,true);
        System.out.println("receive--1: " + new String(message.getBody()));
    }

    @RabbitHandler
    public void onUserMessage(@Payload MessageDTO dto, Channel channel, 
                              @Headers Map<String,Object> headers) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ack
        channel.basicAck(deliveryTag,true);
        System.out.println("receive--11: " + dto.toString());
    }




}
