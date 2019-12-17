package com.yogie.mq01;

import com.rabbitmq.client.*;

/**
 * @program: prac-mq
 * @Date: 2019/12/17 21:45
 * @Author: Chenyogie
 * @Description:
 */
public class MessageProducer {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_name";
    private static final String IP_ADDRESS = "192.168.1.4";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //创建连接
        Connection conn = factory.newConnection();
        //创建信道
        Channel channel = conn.createChannel();
        //创建一个direct类型、持久化、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,null);
        ///创建一个持久化、非排他、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将交换机与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "Hello World!";
        channel.basicPublish(
                EXCHANGE_NAME,
                ROUTING_KEY,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        channel.close();
        conn.close();
    }

}
