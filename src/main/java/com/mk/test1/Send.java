package com.mk.test1;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

//生产者
public class Send {

    private final static String QUEUE_NAME = "queue_hello";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列，声明并创建一个队列，如果队列已经存在，则使用这个队列
        /**
         * 1.队列名字
         * 2.是否持久化   true:持久话   false:非持久话
         * 3.是否独占模式  true:独占模式  false:非独占
         * 4.是否自动删除队列中的消息   true:断开连接删除消息   false:断开连接不会删除消息
         * 5.其他额外参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "Hello World!";
        //发送消息
        /**
         * 1.交换机名
         * 2.队列名
         * 3.BasicProperties
         * 4.消息字节数组
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("发送: '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
