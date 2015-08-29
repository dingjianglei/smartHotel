package com.changhewl.hotel.mq.model;

import lombok.Data;

import com.changhewl.hotel.util.AliasUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.KXml2Driver;
/**
 * 配置修改通知MODEL
 * @Title: TopicModel.java 
 * @Package com.changhewl.hotel.mq.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 丁江磊
 * @date 2015年5月9日 下午10:28:27 
 * @version V1.0
 */
@Data
public class TopicModel {
	public static final XStream xstream = new XStream(new KXml2Driver());
	private String type;
	static {
		AliasUtil.aliasFieldUseUpperCase(TopicModel.class, xstream);
		xstream.alias("TOPICMODEL", TopicModel.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}
}
