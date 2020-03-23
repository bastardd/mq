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

        //��ȡ����
        Connection connection = RabbitMQUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();

        //������Ϣ���У������д���ʱֱ��ʹ��
        channel.queueDeclare("testqueue", false, false, false, null);

        //����һ����Ϣ������
        DefaultConsumer consumer =  new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				 	String messageBody = new String(body);
			        System.out.println("�����߽��յ��� " + messageBody);
			        //ǩ����Ϣ��ȷ����Ϣ
			        //��һ��������envelope.getDeliveryTag()��ȡ�����Ϣ��TagId����һ������
			        //�ڶ���������falseֻȷ��ǩ�յ�ǰ����Ϣ��trueʱ����ʾǩ�ո�����������δǩ�յ���Ϣ
			        channel.basicAck(envelope.getDeliveryTag(), false);
			}
        	
        };
        channel.basicConsume("testqueue", false, consumer);
        //���������в��ܹر�channel��connection
    }
}


