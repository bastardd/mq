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
        	//创建连接
            connection = ConnectionUtil.getConnection() ;
            //创建通道
            Channel channel = connection.createChannel();
            //创建队列
            channel.queueDeclare(RPC_QUEUE_NAME,false,false,false,null);
            
            //声明消费者预取的消息数量
            channel.basicQos(1);
            //创建消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //消费者监听 队列
            channel.basicConsume(RPC_QUEUE_NAME,false,consumer);

            while (true){
                //接收并处理消息
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println("服务端接收： " + message);
               
                //确认收到消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

                //取出消息的correlationId
                AMQP.BasicProperties properties = delivery.getProperties();
                String correlationId = properties.getCorrelationId();

                //创建具有与接收消息相同的correlationId的消息属性
                AMQP.BasicProperties replyProperties = new AMQP.BasicProperties().builder().correlationId(correlationId).build();
                //properties.getReplyTo():获取回调队列名
                channel.basicPublish("",properties.getReplyTo(),replyProperties,"11111111".getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}