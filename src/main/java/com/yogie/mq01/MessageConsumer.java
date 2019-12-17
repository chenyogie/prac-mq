package com.yogie.mq01;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: prac-mq
 * @Date: 2019/12/17 22:00
 * @Author: Chenyogie
 * @Description:
 */
public class MessageConsumer {
    private static final String QUEUE_NAME = "queue_name";
    private static final String IP_ADDRESS = "192.168.1.4";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS,PORT)
        };
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        //创建连接
        Connection conn = factory.newConnection(addresses);
        //创建信道
        Channel channel = conn.createChannel();
        //设置客户端最多接收未被ack的消息的个数
        channel.basicQos(5);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                //接收
                System.out.println(new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);
        //等待回调函数执行完毕之后，关闭资源
        TimeUnit.SECONDS.sleep(10);
        channel.close();
        conn.close();
    }
}
