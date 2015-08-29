package com.changhewl.hotel.deviceservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseDeviceService implements Runnable{
	//通过构造函数处理传递参数
	public CloseDeviceService(){
		
	}
	public void run() {
		log.info("CloseDeviceService处理业务");
	}

}
