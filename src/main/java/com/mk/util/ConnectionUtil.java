package com.mk.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

    public static Connection getConnection() throws Exception {
        //�������ӹ���
        ConnectionFactory factory = new ConnectionFactory();
        //���÷����ַ
        factory.setHost("localhost");
        //�˿�
        factory.setPort(5672);
        //�����˺���Ϣ���û��������롢vhost
        factory.setVirtualHost("vhost01");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // ͨ�����̻�ȡ����
        Connection connection = factory.newConnection();
        return connection;
    }
}