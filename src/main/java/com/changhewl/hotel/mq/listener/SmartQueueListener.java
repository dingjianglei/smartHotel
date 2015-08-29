package com.changhewl.hotel.mq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

import com.changhewl.hotel.mq.model.ContrlModel;
import com.changhewl.hotel.server.InitServer;
import com.changhewl.hotel.util.XstreamUtil;

/**
 * SMART_QUEUE队列监听
 * @Title: SmartQueueListener.java 
 * @Package com.changhewl.hotel.mq.listener 
 * @Description: 监听所有发送到SMART_QUEUE的消息 
 * @author 丁江磊
 * @date 2015年5月10日 上午12:04:42 
 * @version V1.0
 */
@Slf4j
public class SmartQueueListener implements MessageListener{

	public void onMessage(Message msg) {
		TextMessage message = (TextMessage) msg;
		try {
			String msgText = message.getText();
			log.info("收到[SMART_QUEUE队列消息]：{}", msgText);
			// 将XML消息转换为对象
			ContrlModel model = XstreamUtil.fromXML(msgText, ContrlModel.xstream);
			MSListener service = InitServer.getMQProcessService(model.getBusiType());
			if(service == null) {
				log.error("未知的业务指令【{}】", model.getBusiType());
				return ;
			}
			//开启线程处理业务指令
			new ProcessThread(service, model).start();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("处理SMART_QUEUE队列消息异常", e);
		}
	}
}
class ProcessThread extends Thread {
	
	private ContrlModel contrlModel;
	private MSListener service;
	
	public ProcessThread(MSListener service, ContrlModel contrlModel) {
		this.service = service;
		this.contrlModel = contrlModel;
	}
	
	public void run() {
		service.processMQMessage(contrlModel);
	}	
}
