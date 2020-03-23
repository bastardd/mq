package com.mk.test5;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        // ��ȡ����
        Connection connection = ConnectionUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();

        // ����exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // ��Ϣ����
        String message = "Hello World!!";
        channel.basicPublish(EXCHANGE_NAME, "lazy.123", null, message.getBytes());
        System.out.println("���ͣ�" + message + "'");

        channel.close();
        connection.close();
    }
}
