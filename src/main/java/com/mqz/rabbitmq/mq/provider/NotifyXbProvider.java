package com.mqz.rabbitmq.mq.provider;

import com.mqz.rabbitmq.web.NotifyXbDTO;
import lombok.extern.slf4j.Slf4j;
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
public class NotifyXbProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
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
     * 通知信保接口
     * @param dto
     */
    public void send(NotifyXbDTO dto){
        //设置投递回调
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(dto.getMessageId());
        rabbitTemplate.convertAndSend("xbNotify-exchange",
                "xbNotify.callback",
                dto,
                correlationData);
    }


}
