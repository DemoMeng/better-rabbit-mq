package com.mqz.better.rabbitmq.consumer.web;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/11 4:20 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Component
@Slf4j
public class MessageDeadRequeueConsumer {

    @RabbitListener(
        bindings = @QueueBinding(//数据是否持久化
            value = @Queue(value = Constant.DEAD_QUEUE_RE,durable = "true"),
            exchange = @Exchange(name = Constant.DEAD_EXCHANGE, durable = "true",type = "x-dead-letter-exchange"),
            key= Constant.DEAD_ROUTING_KEY
        )
    )
    public void onDo(Message message,
                     @Headers Map<String, Object> headers,
                     Channel channel) throws IOException {
        //模拟业务处理
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("【死信-重入队列】收到消息，开始消费");
        log.info("【死信-重入队列】  参数:{}",message.toString());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        log.info("【死信-重入队列】:deliveryTag:{}",deliveryTag);
        channel.basicAck(deliveryTag,false);
        log.info("【死信-重入队列】消费完成");
    }
}
