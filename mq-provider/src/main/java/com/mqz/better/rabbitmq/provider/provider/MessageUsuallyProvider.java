package com.mqz.better.rabbitmq.provider.provider;

import com.mqz.better.rabbitmq.common.constants.Constant;
import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mqz
 * @description 信保通知消息产生者
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Component
@Slf4j
public class MessageUsuallyProvider {
    @Resource
    private RabbitTemplate rabbitTemplate;

    // 消息发送到交换器Exchange后触发回调
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
            log.info("【常规消息】消息成功投递触发了【提供者】的回调。。。");
            //返回成功，表示消息被正常投递
            if (ack) {
                log.info("【常规消息】信息投递成功，messageId:{}",messageId);
            } else {
                log.error("【常规消息】消费信息失败，messageId:{} 原因:{}",messageId,cause);
                //TODO 重新执行
            }
        }
    };


    // 如果消息从交换器发送到对应队列失败时触发
    private final RabbitTemplate.ReturnsCallback returnsCallback = new RabbitTemplate.ReturnsCallback(){

        @Override
        public void returnedMessage(ReturnedMessage returnedMessage) {
            log.info("【常规消息】消息经交换器发送到队列失败触发，回调参数：{}",returnedMessage);
        }
    };



    /**
     *
     * @param dto
     */
    public void send(MessageDTO dto){
        //设置投递回调
        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        rabbitTemplate.setReturnsCallback(this.returnsCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(dto.getMessageId());
          //正常的消息投递
        rabbitTemplate.convertAndSend(
                Constant.usually_exchange,
                Constant.usually_routing_key,
                dto,
                correlationData);
    }


}
