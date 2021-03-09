package com.mqz.rabbitmq.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mqz
 * @description
 * @abount https://github.com/DemoMeng
 * @since 2020/12/4
 */
@Data
@Accessors(chain = true)
public class NotifyXbDTO implements Serializable {

    /** 队列id - uuid */
    private String messageId;

    /** 信保业务编号 */
    private String businessNo;

    /** 合同地址 */
    private String url;

    /** 模板编号 */
    private String templateNo;





}
