package com.mqz.better.rabbitmq.provider.provider;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 青网科技集团 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/9/10 4:08 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Component
@Slf4j
public class MessageDemoProvider {



    @Resource
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
            log.info("【常规消息demo】消息成功投递触发了【提供者】的回调。。。");
            //返回成功，表示消息被正常投递
            if (ack) {
                log.info("【常规消息demo】信息投递成功，messageId:{}",messageId);
            } else {
                log.error("【常规消息demo】消费信息失败，messageId:{} 原因:{}",messageId,cause);
                //TODO 重新执行
            }
        }
    };

    /***
     * 【消息发送确认2】消息从交换器发送到对应队列
     * 如果消息从交换器发送到对应队列失败时触发
     * 这个方法可以不用重写，因为交换器和队列是在代码中绑定的，若回调了这个方法则说明交换器和队列绑定的问题
     * */
    private final RabbitTemplate.ReturnsCallback returnsCallback = new RabbitTemplate.ReturnsCallback(){

        @Override
        public void returnedMessage(ReturnedMessage returnedMessage) {
            log.info("【常规消息demo】消息经交换器发送到队列失败触发，回调参数：{}",returnedMessage.toString());
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
        //正常的消息投递
        rabbitTemplate.convertAndSend(
                Constant.DEMO_EXCHANGE,
                Constant.DEMO_ROUTING_KEY,
                dto,
                correlationData);
    }

}
