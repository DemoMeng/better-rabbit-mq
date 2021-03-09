package com.mqz.rabbitmq.web;

import com.mqz.rabbitmq.mq.provider.NotifyXbProvider;
import org.springframework.web.bind.annotation.GetMapping;
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
public class WebController {

    @Resource
    private NotifyXbProvider notifyXbProvider;

    @GetMapping(value = "go")
    public String go(){
        NotifyXbDTO d = new NotifyXbDTO()
                .setMessageId(UUID.randomUUID().toString())
                .setBusinessNo("001")
                .setTemplateNo("123")
                .setUrl("https://www.baidu.com");
        notifyXbProvider.send(d);
        return "OK:"+System.currentTimeMillis();
    }

}
