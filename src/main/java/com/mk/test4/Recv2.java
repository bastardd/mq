package com.mk.test4;

import java.io.IOException;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv2 {

    private final static String QUEUE_NAME = "queue_direct2";

    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {

        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");

        // 声明消费者预取的消息数量
        channel.basicQos(1);

        // 创建消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body);
				System.out.println("2接收的消息：" + message + "'");
				//返回确认状态	
	            channel.basicAck(envelope.getDeliveryTag(), false);
			}
        };
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
