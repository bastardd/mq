package com.mk.test1;

import java.io.IOException;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

//消费者
public class Recv {

    private final static String QUEUE_NAME = "queue_hello";

    public static void main(String[] argv) throws Exception {
        //获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();      
        //创建队列，声明并创建一个队列，如果已经存在，则使用这个队列
        //1、队列名称
        //2、是否持久化
        //3、是否是独占模式 ps:独占：当前队列只用于当前连接
        //4、是否自动删除消息队列中的消息
        //5、q其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, 
					BasicProperties properties, byte[] body)
					throws IOException {
				String mess = new String(body);
				System.out.println("接收到消息"+mess);
			}
        };
        //创建一个消费者监听器
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
