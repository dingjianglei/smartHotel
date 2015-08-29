package com.changhewl.hotel.mq;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import com.changhewl.hotel.mq.listener.SmartQueueListener;
import com.changhewl.hotel.mq.model.ContrlModel;


/**
 * MQ工具类
 * @Title: MQUtils.java 
 * @Package com.changhewl.hotel.mq 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 丁江磊
 * @date 2015年5月9日 下午10:39:35 
 * @version V1.0
 */
@Slf4j
public class MQUtils {
	private static ActiveMQConnectionFactory connectionFactory;
	public static Connection connection;
	
	/**队列生成者：key=队列名 */
	private static Map<String, CopyOnWriteArrayList<MessageProducer>> producerMap = new HashMap<String, CopyOnWriteArrayList<MessageProducer>>();
	
	public static void init() throws Exception {
		try {
			connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://218.244.129.250:61617)");
			connectionFactory.setCopyMessageOnSend(false);
			connectionFactory.setSendTimeout(6000);
			connection = connectionFactory.createQueueConnection();
			connection.start();
			initMessageProducer();
			initMessageConsumer();
			log.info("MQ服务器启动成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("系统初始化异常", e);
			System.exit(0);
		} 
	}
	
	/**
	 * 初始化MQ生产者
	 * @throws JMSException
	 */
	public static void initMessageProducer() throws JMSException {
		 String [] producerList={"DEVICE_QUEUE"};
		 int ps=1;
		for(String queueName : producerList) {
			
			CopyOnWriteArrayList<MessageProducer> list = new CopyOnWriteArrayList<MessageProducer>();
			for(int i = 0; i < ps; i ++) {
			    Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	            Queue queue = session.createQueue(queueName);
	            
			    list.add(session.createProducer(queue));
			}
			producerMap.put(queueName, list);	
		}
	}
	
	/**
	 * 初始化MQ消费者
	 * @throws Exception
	 */
	private static void initMessageConsumer() throws Exception {

            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("DEVICE_QUEUE");
            
            MessageListener listener = (MessageListener) new SmartQueueListener();
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(listener);
	}	
	
	/**
	 * 发送消息到清分队列
	 * @param contrlModel
	 */
	public static void sendMessageToQueue(String queueName, String msg) {
		try { 
			log.debug("发送消息到队列【{}】：{}", queueName, msg);
			TextMessage message = new ActiveMQTextMessage();
			message.setText(msg);
		 
			CopyOnWriteArrayList<MessageProducer> list = producerMap.get(queueName);
			list.get(getIndex(list.size())).send(message);
		} catch (JMSException e) {
			log.error("发送消息到队列失败", e); 
		}
	}
	/**
	 * 发送消息到清分队列
	 * @param contrlModel
	 */
	public static void sendMessageToQueue(String queueName, ContrlModel contrlModel) {
		try {
			String msg = contrlModel.toXML();
			sendMessageToQueue(queueName, msg);
		} catch (Exception e) {
			log.error("发送消息到队列失败", e); 
		}
	}	
	private static AtomicInteger onUseIndex = new AtomicInteger(0);
	
	private static int getIndex(int listSize) {
        int index = onUseIndex.incrementAndGet();
        if (index >= listSize) {
            index = 0;
            onUseIndex.set(0);
        }
        return index;
    }
	
	public static void destory() {
		try {
			if(connection != null) {
				connection.stop();
				connection.close();
			}
		} catch (JMSException e) {
			log.error("MQ连接关闭异常", e);
		}
	}
}