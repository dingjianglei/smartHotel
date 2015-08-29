package com.changhewl.hotel.mq.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.changhewl.hotel.mq.model.ContrlModel;

/**
 * 消息监听抽象类
 * @Title: MSListener.java 
 * @Package com.changhewl.hotel.mq.listener 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 丁江磊
 * @date 2015年5月10日 上午11:54:46 
 * @version V1.0
 */
public abstract class MSListener {
	public final static int MAIN_PROCESS_THREAD_NUM=20;
	public abstract void processMQMessage(ContrlModel model);
	protected static ExecutorService mainProcessThreadPool = Executors.newFixedThreadPool(MAIN_PROCESS_THREAD_NUM);
	private static AtomicInteger onUseIndex = new AtomicInteger(0);
	public static int getIndex() {
		int index = onUseIndex.getAndIncrement();
		if (index >= MAIN_PROCESS_THREAD_NUM) {
			index = 0;
			onUseIndex.set(0);
		}
		return index;
	}
}
