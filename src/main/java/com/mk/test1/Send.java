package com.mk.test1;

import com.mk.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

//������
public class Send {

    private final static String QUEUE_NAME = "queue_hello";

    public static void main(String[] argv) throws Exception {
        // ��ȡ������
        Connection connection = ConnectionUtil.getConnection();
        // �������д���ͨ��
        Channel channel = connection.createChannel();
        // �������У�����������һ�����У���������Ѿ����ڣ���ʹ���������
        /**
         * 1.��������
         * 2.�Ƿ�־û�   true:�־û�   false:�ǳ־û�
         * 3.�Ƿ��ռģʽ  true:��ռģʽ  false:�Ƕ�ռ
         * 4.�Ƿ��Զ�ɾ�������е���Ϣ   true:�Ͽ�����ɾ����Ϣ   false:�Ͽ����Ӳ���ɾ����Ϣ
         * 5.�����������
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // ��Ϣ����
        String message = "Hello World!";
        //������Ϣ
        /**
         * 1.��������
         * 2.������
         * 3.BasicProperties
         * 4.��Ϣ�ֽ�����
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("����: '" + message + "'");
        //�ر�ͨ��������
        channel.close();
        connection.close();
    }
}
