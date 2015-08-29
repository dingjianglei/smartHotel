package com.changhewl.hotel.mq.model;

import java.util.TimeZone;

import lombok.Data;

import com.changhewl.hotel.util.AliasUtil;
import com.changhewl.hotel.util.DateUtil;
import com.changhewl.hotel.util.XstreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.KXml2Driver;

/**
 * 控制对象
 * @Title: ContrlModel.java 
 * @Package com.changhewl.hotel.mq.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 丁江磊
 * @date 2015年5月9日 下午10:28:38 
 * @version V1.0
 */
@Data
public class ContrlModel implements Cloneable {
	public static final XStream xstream = new XStream(new KXml2Driver());
	/** 业务类型 */
	private BusiType busiType;
	/** 业务名称 */
	private String serverName;

	static {
		AliasUtil.aliasFieldUseUpperCase(ContrlModel.class, xstream);
		xstream.registerConverter(new DateConverter(DateUtil.dateFormatStr1, null, TimeZone.getDefault()));
		xstream.alias("CONTRLMODEL", ContrlModel.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	

	public String toXML() {
		return XstreamUtil.toXML(this, xstream);
	}

	public void setBusiType(BusiType busiType) {
		this.busiType = busiType;
		this.serverName = busiType.getServerName();
	}

	@Override
	public ContrlModel clone() throws CloneNotSupportedException {
		return (ContrlModel) super.clone();
	}

}
