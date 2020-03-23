package com.mk.test6;

import java.io.IOException;
import java.util.UUID;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class RpcClient {
	// 发送消息的队列名称
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	public static void main(String[] args) {
		Connection connection = null;
		Channel channel = null;
		try {
			//创建连接
			connection = ConnectionUtil.getConnection() ;
			//创建通道
			channel = connection.createChannel();
			// 创建回调队列
			String callbackQueue = channel.queueDeclare().getQueue();

			// 消费者从回调队列中接收服务端传送的消息
			QueueingConsumer consumer = new QueueingConsumer(channel);
			//监听队列
			channel.basicConsume(callbackQueue, true, consumer);

			// 创建带有correlationId的消息属性
			String correlationId = UUID.randomUUID().toString();
			AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().correlationId(correlationId)
					.replyTo(callbackQueue).build();
			String message = "hello rabbitmq";
			channel.basicPublish("", RPC_QUEUE_NAME, basicProperties, message.getBytes());
			System.out.println("客户端发送的消息: " + message + ", correaltionId = " + correlationId);

			// 接收回调消息
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String receivedCorrelationId = delivery.getProperties().getCorrelationId();
				if (correlationId.equals(receivedCorrelationId)) {
					System.out.println("客户端接收的回调消息：" + new String(delivery.getBody())
							+ ", correaltionId = " + correlationId);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}