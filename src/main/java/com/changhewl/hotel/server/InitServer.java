package com.changhewl.hotel.server;

import com.changhewl.hotel.mq.listener.CloseDeviceQuneuService;
import com.changhewl.hotel.mq.listener.MSListener;
import com.changhewl.hotel.mq.model.BusiType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

import java.io.InputStream;

/**
 * 服务器应用初始化类
 */
@Slf4j
public class InitServer {
    private final static String CONFIG_FILE = "config.properties";
    public static PropertiesConfiguration commonConfig = null;
    public static ClassPathXmlApplicationContext applicationContext;
    /**服务器地址**/
    public static String SERVICE_IP;
    /**服务器端口**/
    public static int SERVICE_PORT;
    /**设备端口**/
    public static int DEVICE_PORT;
    public static boolean IS_OPEN_HEART=true;
	public static MSListener getMQProcessService(BusiType busiType) {
		MSListener mqListener=null;
			if(busiType.equals(BusiType.CLOSE_DEVICE)){
				mqListener=new CloseDeviceQuneuService();
			}
		return mqListener;
	}

    // 加载系统参数配置
    public static void loadCommonProperties() throws  Exception{
        ClassLoader cl = InitServer.class.getClassLoader();
        InputStream inputStream = null;
        try {
            log.info("开始加载系统参数配置");
            inputStream = cl.getResourceAsStream(CONFIG_FILE);
            commonConfig = new PropertiesConfiguration();
            commonConfig.load(inputStream);
            SERVICE_IP=commonConfig.getString("SERVICE_IP");
            SERVICE_PORT=commonConfig.getInt("SERVICE_PORT");
            DEVICE_PORT=commonConfig.getInt("DEVICE_PORT");
            IS_OPEN_HEART=commonConfig.getBoolean("IS_OPEN_HEART");
        }finally {
            log.info("加载系统参数配置完成");
        }
    }
    /**
     * 根据Spring中配置的NO获取对象实例
     *
     * @param beanNO
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanNO) {
        return (T) InitServer.applicationContext.getBean(beanNO);
    }


    public static void init(){
        try{
            loadCommonProperties();
            log.info("开始加载spring");
            applicationContext =	new ClassPathXmlApplicationContext("applicationContext.xml");
            log.info("spring加载成功ApplicationId={},ApplicationName",applicationContext.getId(),applicationContext.getApplicationName());
        }catch (Exception e){
            log.info("加载spring异常{}",e);
        }

    }
}
