package com.mk.test7;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mk.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {
    	//获取连接
        Connection connection = RabbitMQUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //创建队列，声明并创建一个队列，如果队列已经存在，则使用这个队列
        channel.queueDeclare("queue001", false, false, false, null);
        //需要发送的消息
        String content = "Hello RabbitCluster!";
        
        //发送消息
        channel.basicPublish("", "queue001", null, content.getBytes());

        channel.close();
        connection.close();

        System.out.println("数据发送成功");
    }
}
