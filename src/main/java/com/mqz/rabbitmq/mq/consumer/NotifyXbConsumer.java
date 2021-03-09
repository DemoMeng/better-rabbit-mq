package com.mqz.rabbitmq.mq.consumer;

import com.mqz.rabbitmq.web.NotifyXbDTO;
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
 * @description
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Component
@Slf4j
public class NotifyXbConsumer {



    private static final String SUCCESS = "SUCCESS";

    /**
     * @param dto
     * @param headers
     * @param channel
     * @throws IOException
     */
    @RabbitListener(
            bindings = @QueueBinding(//数据是否持久化
                    value = @Queue(value = "xbNotify-queue",durable = "true"),
                    exchange = @Exchange(name = "xbNotify-exchange",
                            durable = "true",type = "topic"),
                    key="xbNotify.callback"
            )
    )
    @RabbitHandler
    public void onDo(@Payload NotifyXbDTO dto, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        log.info("----收到消息，开始消费-----");
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        channel.basicAck(deliveryTag,false);
        log.info("--------消费完成--------");
    }
}
