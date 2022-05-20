//package com.mqz.better.rabbitmq.consumer.clusterConfig;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
//import javax.annotation.Resource;
//
///**
// * 版权所有  copyright© 蒙大拿
// *
// * @author mqz
// * @date 2022/5/19
// * @about https://www.gitee.com/DemoMeng
// * @description
// *
// * 单机、集群切换配置
// *
// */
//@Configuration
//@Slf4j
//public class RabbitMqClusterConfig {
//
//
//    // 读取springboot 自动配置中读取application.yml 的rabbitmq配置
//    @Resource
//    private RabbitProperties rabbitProperties;
//
//    @Bean("customContainerFactory")
//    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory
//            connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(10);
//        factory.setMaxConcurrentConsumers(20);
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }
//
//    //单机节点
//    @Bean
//    @ConditionalOnProperty(name = "spring.rabbitmq.mode", havingValue = "single")
//    public ConnectionFactory connectionFactorySingle(@Value("${spring.rabbitmq.host}") String host, @Value("${spring.rabbitmq.port}") int port) {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost(),rabbitProperties.getPort());
//        connectionFactory.setUsername(rabbitProperties.getUsername());
//        connectionFactory.setPassword(rabbitProperties.getPassword());
//        connectionFactory.setVirtualHost("/");
//        connectionFactory.setPublisherReturns(rabbitProperties.isPublisherReturns());
//        return connectionFactory;
//    }
//    //集群节点
//    @Bean
//    @ConditionalOnProperty(name = "spring.rabbitmq.mode", havingValue = "cluster")
//    public ConnectionFactory connectionFactoryCluster(@Value("${spring.rabbitmq.addresses}") String addresses) {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setAddresses(rabbitProperties.getAddresses());
//        cachingConnectionFactory.setUsername(rabbitProperties.getUsername());
//        cachingConnectionFactory.setPassword(rabbitProperties.getPassword());
//        cachingConnectionFactory.setVirtualHost("/");
//        cachingConnectionFactory.setPublisherReturns(rabbitProperties.isPublisherReturns());
//        log.info("集群连接工厂设置完成，连接地址{}"+rabbitProperties.getAddresses());
//        log.info("集群连接工厂设置完成，连接用户{}"+rabbitProperties.getUsername());
//        return cachingConnectionFactory;
//    }
//
//    // RabbitConfig已经配置
////    @Bean
////    //必须是prototype类型
////    @Scope("prototype")
////    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
////        RabbitTemplate template = new RabbitTemplate(connectionFactory);
////        return template;
////    }
//
//
//
//
//}
