package com.mz.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author tongzhou
 * @date 2018-04-19 15:21
 **/
public class JMSProvider {
    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 10;

    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);

    public static Session getSession() throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    public void createProducer() {
        try {
            Session session = getSession();
            Destination destination = session.createQueue("queue");
            MessageProducer messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage = session.createTextMessage();
            for (int i = 0; i < 10; i++) {
                textMessage.setText("producer - " + i);
                messageProducer.send(textMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void createConsumer() {
        try {
            Session session = getSession();
            Destination destination = session.createQueue("queue");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            while (true) {
                Message receive = messageConsumer.receive();
                //String jmsType = receive.getJMSType();
                System.out.println("收到的消息" + " - " + ((TextMessage) receive).getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JMSProvider().createProducer();
        new JMSProvider().createConsumer();

    }

}
