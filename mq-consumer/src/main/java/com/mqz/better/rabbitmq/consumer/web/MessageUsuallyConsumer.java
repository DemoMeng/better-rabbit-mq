package com.mqz.better.rabbitmq.consumer.web;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author mqz
 * @description 常规消息消费
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Component
@Slf4j
public class MessageUsuallyConsumer {



    private static final String SUCCESS = "SUCCESS";

    /**
     * @param dto
     * @param headers
     * @param channel
     * @throws IOException
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = Constant.usually_queue,durable = "true"),//数据是否持久化
            exchange = @Exchange(name = Constant.usually_exchange, durable = "true",type = "topic"),
            key= Constant.usually_routing_key
        )
    )
    public void onDo(@Payload MessageDTO dto, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        log.info("----【常规消息】收到消息，开始消费-----");
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
//        if(dto.getTemplateNo().equals("123")){
//            throw new IOException("模拟异常抛出，看是否消费成功");
//        }
        log.info("【常规消息】 参数:{}",dto.toString());
        // 手工ack
        channel.basicAck(deliveryTag,true);
        log.info("--------【常规消息】消费完成--------");
    }
}
