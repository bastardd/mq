package com.mk.test2;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String QUEUE_NAME = "queue_work";

    public static void main(String[] argv) throws Exception {
        // ��ȡ������
        Connection connection = ConnectionUtil.getConnection();
       //��ȡͨ��
        Channel channel = connection.createChannel();

        // ��������
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 20; i++) {
            // ��Ϣ����
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("����: '" + message + "'");

            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }
}
