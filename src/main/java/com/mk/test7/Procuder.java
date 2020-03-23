package com.mk.test7;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mk.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {
    	//��ȡ����
        Connection connection = RabbitMQUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();
        //�������У�����������һ�����У���������Ѿ����ڣ���ʹ���������
        channel.queueDeclare("queue001", false, false, false, null);
        //��Ҫ���͵���Ϣ
        String content = "Hello RabbitCluster!";
        
        //������Ϣ
        channel.basicPublish("", "queue001", null, content.getBytes());

        channel.close();
        connection.close();

        System.out.println("���ݷ��ͳɹ�");
    }
}
