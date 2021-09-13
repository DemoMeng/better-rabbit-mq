package com.mqz.better.rabbitmq.provider.web;

import com.mqz.better.rabbitmq.common.model.dto.MessageDTO;
import com.mqz.better.rabbitmq.provider.provider.MessageDeadLetterProvider;
import com.mqz.better.rabbitmq.provider.provider.MessageDemoProvider;
import com.mqz.better.rabbitmq.provider.provider.MessageProvider;
import com.mqz.better.rabbitmq.provider.provider.MessageUsuallyProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author mqz
 * @description
 * @abount https://github.com/DemoMeng
 * @since 2021/3/9
 */
@RestController
@RequestMapping(value = "/web/")
@Api(tags = "web入口")
public class WebController {

    @Resource
    private MessageProvider messageProvider;
    @Resource
    private MessageUsuallyProvider messageUsuallyProvider;
    @Resource
    private MessageDemoProvider messageDemoProvider;
    @Resource
    private MessageDeadLetterProvider messageDeadLetterProvider;

    @PostMapping(value = "go")
    @ApiOperation(value = "延迟投递消息-发送")
    public String go(@RequestBody MessageDTO dto){
        MessageDTO d = new MessageDTO()
                .setMessageId(UUID.randomUUID().toString())
                .setBusinessNo(dto.getBusinessNo())
                .setTemplateNo(dto.getTemplateNo())
                .setUrl(dto.getUrl());
        messageProvider.send(d);
        return "【延迟投递消息】messageId:"+d.getMessageId();
    }


    @PostMapping(value = "usually/go")
    @ApiOperation(value = "常规消息-发送")
    public String usuallyGo(@RequestBody MessageDTO dto){
        MessageDTO d = new MessageDTO()
                .setMessageId(UUID.randomUUID().toString())
                .setBusinessNo(dto.getBusinessNo())
                .setTemplateNo(dto.getTemplateNo())
                .setUrl(dto.getUrl());
        messageUsuallyProvider.send(d);
        return "【常规消息】messageId:"+d.getMessageId();
    }


    @PostMapping(value = "usually/go1")
    @ApiOperation(value = "常规消息1-发送")
    public String usuallyGo1(@RequestBody MessageDTO dto){
        MessageDTO d = new MessageDTO()
                .setMessageId(UUID.randomUUID().toString())
                .setBusinessNo(dto.getBusinessNo())
                .setTemplateNo(dto.getTemplateNo())
                .setUrl(dto.getUrl());
        messageDemoProvider.send(d);
        return "【常规消息1】messageId:"+d.getMessageId();
    }

    @PostMapping(value = "dead/go")
    @ApiOperation(value = "死信队列-模拟发送")
    public String deadGo(@RequestBody MessageDTO dto){
        MessageDTO d = new MessageDTO()
                .setMessageId(UUID.randomUUID().toString())
                .setBusinessNo(dto.getBusinessNo())
                .setTemplateNo(dto.getTemplateNo())
                .setUrl(dto.getUrl());
        messageDeadLetterProvider.send(d);
        return "【死信队列】messageId:"+d.getMessageId();
    }

}
