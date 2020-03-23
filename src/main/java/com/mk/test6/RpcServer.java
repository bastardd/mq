package com.mk.test6;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class RpcServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) {
        Connection connection = null;
        try {
        	//��������
            connection = ConnectionUtil.getConnection() ;
            //����ͨ��
            Channel channel = connection.createChannel();
            //��������
            channel.queueDeclare(RPC_QUEUE_NAME,false,false,false,null);
            
            //����������Ԥȡ����Ϣ����
            channel.basicQos(1);
            //����������
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //�����߼��� ����
            channel.basicConsume(RPC_QUEUE_NAME,false,consumer);

            while (true){
                //���ղ�������Ϣ
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println("����˽��գ� " + message);
               
                //ȷ���յ���Ϣ
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

                //ȡ����Ϣ��correlationId
                AMQP.BasicProperties properties = delivery.getProperties();
                String correlationId = properties.getCorrelationId();

                //���������������Ϣ��ͬ��correlationId����Ϣ����
                AMQP.BasicProperties replyProperties = new AMQP.BasicProperties().builder().correlationId(correlationId).build();
                //properties.getReplyTo():��ȡ�ص�������
                channel.basicPublish("",properties.getReplyTo(),replyProperties,"11111111".getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}