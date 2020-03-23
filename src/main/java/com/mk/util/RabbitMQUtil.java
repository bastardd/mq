package com.mk.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {

    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.55");  //�����������ַ
        connectionFactory.setPort(5673);          //����������˿�
        connectionFactory.setUsername("admin");  
        connectionFactory.setPassword("123456"); 
        connectionFactory.setVirtualHost("testHA");   //��������
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException(e); 
        }
        return connection;
    }
}
