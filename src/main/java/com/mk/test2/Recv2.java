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

        // ��ȡ������
        Connection connection = ConnectionUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();

        //����������Ԥȡ����������
        channel.basicQos(1);
        // ��������
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // ������е�������
        DefaultConsumer consumer = new DefaultConsumer(channel){
        	@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
        		String mess = new String(body);
				System.out.println("2���յ�����Ϣ��"+mess);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
        };		
        // �������У�false��ʾ�ֶ��������״̬��true��ʾ�Զ�
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}