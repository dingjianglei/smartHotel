package com.changhewl.hotel.mq.listener;

import lombok.extern.slf4j.Slf4j;

import com.changhewl.hotel.mq.model.ContrlModel;

@Slf4j
public class CloseDeviceQuneuService extends MSListener{

	@Override
	public void processMQMessage(ContrlModel model) {
		log.info("正在处理消息{}对应的业务",model);
	}
}
