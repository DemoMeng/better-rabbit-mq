package com.mqz.better.rabbitmq.provider.provider;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mqz
 * @description 信保通知消息产生者
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Component
@Slf4j
public class MessageProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
            log.info("消息成功投递触发了【提供者】的回调。。。");
            //返回成功，表示消息被正常投递
            if (ack) {
                log.info("信息投递成功，messageId:{}",messageId);
            } else {
                log.error("消费信息失败，messageId:{} 原因:{}",messageId,cause);
                //TODO 重新执行
            }
        }
    };

    /**
     *
     * @param dto
     */
    public void send(MessageDTO dto){
        //设置投递回调
        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(dto.getMessageId());

        //消息延迟投递，需要配置rabbit延迟投递配置，参考RabbitConfig，正常投递不需要配置！！
        //发送消息时指定 header 延迟时间
        rabbitTemplate.convertAndSend(
                Constant.lazy_exchange,
                Constant.lazy_routing_key,
                dto,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //设置消息持久化
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //message.getMessageProperties().setHeader("x-delay", "6000");
                        message.getMessageProperties().setDelay(10000);//延迟10秒
                        return message;
                    }
                },
                correlationData

        );
    }


}
