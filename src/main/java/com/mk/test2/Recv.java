package com.mk.test2;

import java.io.IOException;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {

    private final static String QUEUE_NAME = "queue_work";

    public static void main(String[] argv) throws Exception {

        // ��ȡ������
        Connection connection = ConnectionUtil.getConnection();
        //��ȡͨ��
        Channel channel = connection.createChannel();
        
        //������Ԥȡ����������
        channel.basicQos(1);
        // ��������
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //���������߼�����
        DefaultConsumer consumer = new DefaultConsumer(channel){
        	/**
        	 * consumerTag  ͬһ���Ự�� consumerTag �ǹ̶��� �������˻Ự������
        	 * envelope ����ͨ���ö����ȡ��ǰ��Ϣ�ı�� ���͵Ķ���  ��������Ϣ
        	 * properties  ����Ϣһ���͵���������
        	 * body ��Ϣ����
        	 */
        	@Override
			public void handleDelivery(String consumerTag, Envelope envelope, 
					BasicProperties properties, byte[] body)
					throws IOException {
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		String mess = new String(body);
				System.out.println("1���յ�����Ϣ��"+mess);
				//�ֶ�����һ����ִȷ��
				/**
				 * 1.Ҫ��ִȷ�ϵ���Ϣ�ı��
				 * 2.�Ƿ�����ȷ��   true:����ȷ��   false:ֻȷ�ϵ�ǰ��Ϣ
				 */
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
        };	
        // �������У�false��ʾ�ֶ��������״̬��true��ʾ�Զ�
        channel.basicConsume(QUEUE_NAME, false, consumer);
       
    }
}