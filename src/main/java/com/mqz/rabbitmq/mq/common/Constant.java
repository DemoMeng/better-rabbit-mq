package com.mqz.rabbitmq.mq.common;

/**
 * 青网科技集团 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/7/9 10:50 上午
 * @Description
 * @About： https://github.com/DemoMeng
 */
public class Constant {

    //延迟投递-交换机
    public final static String lazy_exchange = "xbNotify-exchange";
    //延迟投递-路由
    public final static String lazy_routing_key = "xbNotify.callback";
    //延迟投递-队列
    public final static String lazy_queue = "xbNotify-queue";




}
