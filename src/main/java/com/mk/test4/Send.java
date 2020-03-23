package com.mk.test4;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {
        // ��ȡ����
        Connection connection = ConnectionUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();

        // ����exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // ��Ϣ����
        String message = "hello direct";
        //info��ʾrouting key
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
        System.out.println("����" + message + "'");

        channel.close();
        connection.close();
    }
}
