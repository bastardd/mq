package com.mk.test5;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 消息内容
        String message = "Hello World!!";
        channel.basicPublish(EXCHANGE_NAME, "lazy.123", null, message.getBytes());
        System.out.println("发送：" + message + "'");

        channel.close();
        connection.close();
    }
}
