package com.mk.test7;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mk.util.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = RabbitMQUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //创建消息队列，当队列存在时直接使用
        channel.queueDeclare("testqueue", false, false, false, null);

        //创建一个消息消费者
        DefaultConsumer consumer =  new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				 	String messageBody = new String(body);
			        System.out.println("消费者接收到： " + messageBody);
			        //签收消息，确认消息
			        //第一个参数，envelope.getDeliveryTag()获取这个消息的TagId，是一个整数
			        //第二个参数，false只确认签收当前的消息，true时，表示签收该消费者所有未签收的消息
			        channel.basicAck(envelope.getDeliveryTag(), false);
			}
        	
        };
        channel.basicConsume("testqueue", false, consumer);
        //在消费者中不能关闭channel和connection
    }
}


