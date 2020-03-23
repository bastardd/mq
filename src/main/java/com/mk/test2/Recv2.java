package com.mk.test2;

import java.io.IOException;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv2 {

    private final static String QUEUE_NAME = "queue_work";

    public static void main(String[] argv) throws Exception {

        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //声明消费者预取的消费数量
        channel.basicQos(1);
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
        	@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
        		String mess = new String(body);
				System.out.println("2接收到的消息："+mess);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
        };		
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}