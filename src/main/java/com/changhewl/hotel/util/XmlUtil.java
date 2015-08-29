package com.changhewl.hotel.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 解析XML报文
 * @Title: XmlUtil.java 
 * @Package com.changhewl.hotel.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 丁江磊
 * @date 2015年5月9日 下午10:27:49 
 * @version V1.0
 */
@Slf4j
public class XmlUtil {
    /**
     * 转换成MAP
     */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXml(String xml) {
		if (StringUtils.isBlank(xml)) {
			return Collections.emptyMap();
		}
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();

            HashMap<String, Object> map;

            if (root.isTextOnly()) {
            	map = new HashMap<String, Object>(1);
            	map.put(root.getName(), parseElement(root));
			} else {
				map = (HashMap<String, Object>) parseElement(root);
			}

            document = null;
            root = null;
            return map;
        } catch (DocumentException e) {
        	log.error("银行响应消息XML转换成MAP失败", e);
        }
        return Collections.emptyMap();
    }
	
	public static Object parseElement(Element ele) {
		if (ele.isTextOnly()) {
			return ele.getTextTrim();
		} else {
			@SuppressWarnings("unchecked")
			List<Element> list = ele.elements();
			HashMap<String, Object> map = new HashMap<String, Object>(list.size());
			for (Element element : list) {
				map.put(element.getName(), parseElement(element));
			}
			return map;
		}
	}
}
/**
 * 业务场景分析
 * 1.智能设备【接收指令】【硬件设备实时提交数据到服务器后台】
 * 2.APP、微信 可发送指令控制硬件设备【发送到服务器--->服务器发送到硬件设备】
 * 
 * MQ点对点
 * MQ发布控制多台设备
 * 
 * ***/
