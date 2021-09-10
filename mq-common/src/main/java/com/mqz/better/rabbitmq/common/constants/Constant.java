package com.mqz.better.rabbitmq.common.constants;

/**
 *  版权所有 © Copyright 2012<br>
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




    public final static String usually_exchange = "usually-exchange";
    public final static String usually_routing_key = "usuallyNotify.callback";
    public final static String usually_queue = "usually-queue";


    public final static String DEMO_QUEUE = "demo-queue";
    public final static String DEMO_ROUTING_KEY = "demo.callback";
    public final static String DEMO_EXCHANGE = "demo-exchange";





}
