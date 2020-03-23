package com.mk.test6;

import java.io.IOException;
import java.util.UUID;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class RpcClient {
	// ������Ϣ�Ķ�������
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	public static void main(String[] args) {
		Connection connection = null;
		Channel channel = null;
		try {
			//��������
			connection = ConnectionUtil.getConnection() ;
			//����ͨ��
			channel = connection.createChannel();
			// �����ص�����
			String callbackQueue = channel.queueDeclare().getQueue();

			// �����ߴӻص������н��շ���˴��͵���Ϣ
			QueueingConsumer consumer = new QueueingConsumer(channel);
			//��������
			channel.basicConsume(callbackQueue, true, consumer);

			// ��������correlationId����Ϣ����
			String correlationId = UUID.randomUUID().toString();
			AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().correlationId(correlationId)
					.replyTo(callbackQueue).build();
			String message = "hello rabbitmq";
			channel.basicPublish("", RPC_QUEUE_NAME, basicProperties, message.getBytes());
			System.out.println("�ͻ��˷��͵���Ϣ: " + message + ", correaltionId = " + correlationId);

			// ���ջص���Ϣ
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String receivedCorrelationId = delivery.getProperties().getCorrelationId();
				if (correlationId.equals(receivedCorrelationId)) {
					System.out.println("�ͻ��˽��յĻص���Ϣ��" + new String(delivery.getBody())
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