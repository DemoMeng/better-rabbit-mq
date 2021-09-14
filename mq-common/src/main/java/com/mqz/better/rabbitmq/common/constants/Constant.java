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




    // usually
    public final static String usually_exchange = "usually-exchange";
    public final static String usually_routing_key = "usuallyNotify.callback";
    public final static String usually_queue = "usually-queue";


    // demo
    public final static String DEMO_QUEUE = "demo-queue";
    public final static String DEMO_ROUTING_KEY = "demo.callback";
    public final static String DEMO_EXCHANGE = "demo-exchange";


    //死信
    public final static String DEAD_QUEUE = "dead-queue";
    public final static String DEAD_EXCHANGE = "dead-exchange";
    public final static String DEAD_ROUTING_KEY = "dead.routing.key";
    public final static String DEAD_QUEUE_RE = "dead-queue-re";


    //ttl
    public final static String TTL_QUEUE = "ttl-queue";
    public final static String TTL_EXCHANGE = "ttl-exchange";
    public final static String TTL_ROUTING_KEY = "ttl.routing_key";



}
