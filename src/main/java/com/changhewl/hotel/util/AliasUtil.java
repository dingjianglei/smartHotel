package com.changhewl.hotel.util;

import java.io.File;
import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;

/**
 * xstream别名注册帮助类
 *
 * @author tanshuai
 * @date 2013-3-20 下午6:08:43
 * @version 1.0
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class AliasUtil {
	/**
	 * 注册列名，使用字段名称的大写
	 *
	 * @param clazz
	 *            需要转换的类
	 * @param xstream
	 *            注册到该xstream
	 */
	public static void aliasFieldUseUpperCase(Class clazz, XStream xstream) {
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			xstream.aliasField(fields[i].getName().toUpperCase(), clazz, fields[i].getName());
		}
	}
	 
	/**
	 * 注册属性，使用字段名称的大写
	 *
	 * @param clazz
	 *            需要转换的类
	 * @param xstream
	 *            注册到该xstream
	 */
	public static void aliasAttributeUseUpperCase(Class clazz, XStream xstream) {
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			xstream.aliasAttribute(clazz, fields[i].getName(), fields[i].getName().toUpperCase());
		}
	}

	/**
	 * 注册属性和列名，使用字段名称的大写
	 *
	 * @param clazz
	 *            需要转换的类
	 * @param xstream
	 *            注册到该xstream
	 * @param fieldNames
	 *            需要注册为列名的字段名称
	 */
	public static void aliasAttributeAndFieldUseUpperCase(Class clazz, XStream xstream, String... fieldNames) {
		Field[] fields = clazz.getDeclaredFields();
		String fieldName;
		flag: for (int i = 0; i < fields.length; i++) {
			fieldName = fields[i].getName();
			for (int j = 0; j < fieldNames.length; j++) {
				if (fieldNames[j].equals(fieldName)) {
					xstream.aliasField(fieldName.toUpperCase(), clazz, fieldName);
					continue flag;
				}
			}
			xstream.aliasAttribute(clazz, fieldName, fieldName.toUpperCase());
		}
	}

	/**
	 * 注册业务域
	 *
	 * @param packName
	 *            业务域所在包名
	 * @param xstream
	 *            注册到该xstream
	 */
	public static void aliasBusiFields(String packName, XStream xstream) {
		String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		filePath = filePath.concat(packName.replace(".", "/"));
//		logger.info("业务域文件夹路径：{}", filePath);
//		logger.info("业务域包名：{}", packName);
		packName = packName.concat(".");

		File directory = new File(filePath);
		try {
			if (directory.exists() && directory.isDirectory()) {
				File[] lists = directory.listFiles();
				Class clazz;
				String className;
				for (File file : lists) {
					if(file.isDirectory()) {
						continue;
					}
					className = StringUtils.removeEnd(packName.concat(file.getName()), ".class");
					clazz = Class.forName(className);
//					logger.info("注册{}", className);
					xstream.alias("TEMP", clazz);
					AliasUtil.aliasFieldUseUpperCase(clazz, xstream);
				}
			}
		} catch (ClassNotFoundException e) {
			log.error("注册业务域异常", e);
		}
	}
}
