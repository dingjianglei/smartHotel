package com.changhewl.hotel.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

/**
 * xstream转换工具类
 *
 * @author tanshuai
 */
@Slf4j
@SuppressWarnings("unchecked")
public class XstreamUtil {
	public static String toXML(Object obj, XStream xstream) {
		StringWriter sw = new StringWriter();
		CompactWriter writer = new CompactWriter(sw);
		try {
			xstream.marshal(obj, writer);
		} finally {
			writer.close();
		}
		String str = sw.toString(); 
		return str.replaceAll("__", "_").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
	} 

	/**
	 * 根据xml生成对象
	 */
	public static <T> T fromXML(String xml, XStream xstream) {
		return (T) xstream.fromXML(xml.toUpperCase());
	}

	/**
	 * 根据xml填充指定的对象
	 *
	 * @param xml
	 *            字符串
	 * @param bean
	 *            填充到该实例
	 */
	public static <T> T fillByXml(String xml, T bean, XStream xstream) {
		if (bean == null || StringUtils.isBlank(xml)) {
			return null;
		}

		T resBean = (T) xstream.fromXML(xml.toUpperCase(), bean);
		return resBean;
	}
	
	/**
	 * 解析XML格式字符串
	 * 
	 * @param resXml XML格式字符串
	 * @return resCode 响应码
	 */ 
	public static String parseXml(String resXml) {
		String resCode = ""; // 银行响应码
		if (StringUtils.isNotEmpty(resXml)) {
			Map<String, Object> resMap = XmlUtil.parseXml(resXml);
			HashMap<String, Object> bodys = (HashMap<String, Object>) resMap.get("BODYS"); // 获取<BODYS>标签中内容
			resCode = bodys.get("RESCODE").toString();
			log.info("银行响应码【{}】", resCode);
			log.info("银行响应描述【{}】", bodys.get("ERRCODE").toString());
		}
		return resCode;
	}
}
