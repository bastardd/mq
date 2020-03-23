package com.mk.test4;

import java.io.IOException;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {

    private final static String QUEUE_NAME = "queue_direct1";

    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {

         // ��ȡ����
        Connection connection = ConnectionUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();

        // ��������
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // �󶨶��е�������(ָ���ö��н���ʲô���͵���Ϣ��bindingkey)
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

        // ����������Ԥȡ����Ϣ����
        channel.basicQos(1);

        // ����������
        DefaultConsumer consumer = new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body);
				System.out.println("1���յ���Ϣ��" + message + "'");
				//����ȷ��״̬	
	            channel.basicAck(envelope.getDeliveryTag(), false);
			}
        };
        // �������У��ֶ��������״̬
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}