package com.mqz.better.rabbitmq.provider.provider;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
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
public class MessageDelayProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 【消息发送确认1】 消息发送到交换器Exchange后触发回调
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
            log.info("【延迟消息】消息成功投递触发了【提供者】的回调。。。");
            //返回成功，表示消息被正常投递
            if (ack) {
                log.info("【延迟消息】信息投递成功，messageId:{}",messageId);
            } else {
                log.error("【延迟消息】消费信息失败，messageId:{} 原因:{}",messageId,cause);
                //TODO 重新执行
            }
        }
    };


    /***
     * 【消息发送确认2】消息从交换器发送到对应队列
     *
     * 如果消息从交换器发送到对应队列失败时触发
     * 这个方法可以不用重写，因为交换器和队列是在代码中绑定的，若回调了这个方法则说明交换器和队列绑定的问题
     * */
    private final RabbitTemplate.ReturnsCallback returnsCallback = new RabbitTemplate.ReturnsCallback(){
        @Override
        public void returnedMessage(ReturnedMessage returnedMessage) {
            log.info("【延迟消息】消息经交换器发送到队列失败触发，回调参数：{}",returnedMessage);
            //TODO 重新执行
        }

    };


    private final MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            //设置消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            //message.getMessageProperties().setHeader("x-delay", "6000");
            message.getMessageProperties().setDelay(10000);//延迟10秒，底层调的也是在header里加入x-delay
            return message;
        }
    };


    /**
     *  包含发送消息以及添加 confirm 监听、添加 return 监听。
     *  如果消费端要设置为手工 ACK ，那么生产端发送消息的时候一定发送 correlationData ，并且全局唯一，用以唯一标识消息
     */
    public void send(MessageDTO dto){
        //设置投递回调
        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        rabbitTemplate.setReturnsCallback(this.returnsCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(dto.getMessageId());

        //消息延迟投递，需要配置rabbit延迟投递配置，参考RabbitConfig，正常投递不需要配置！！
        //发送消息时指定 header 延迟时间
        rabbitTemplate.convertAndSend(
                Constant.lazy_exchange,
                Constant.lazy_routing_key,
                dto,
                messagePostProcessor,
                correlationData

        );
    }


}
