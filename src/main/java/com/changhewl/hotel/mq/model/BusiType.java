package com.changhewl.hotel.mq.model;

/**
 * 业务类型
 * 
 */
public enum BusiType {
	OPEN_DEVICE("开启设备"),
	CLOSE_DEVICE("关闭设备");
	private final String serverName;

	private BusiType(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

}