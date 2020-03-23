package com.mk.test4;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "hello direct";
        //info表示routing key
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
        System.out.println("发送" + message + "'");

        channel.close();
        connection.close();
    }
}
