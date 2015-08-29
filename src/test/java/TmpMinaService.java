import lombok.extern.slf4j.Slf4j;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;

@Slf4j
public class TmpMinaService {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
	    try {
			ClassPathXmlApplicationContext ct =	new ClassPathXmlApplicationContext("applicationContext.xml");
			if(ct==null){
				log.info("Mina服务端启动失败");
			}else{
				log.info("Mina服务端启动成功");
			}
		} catch (BeansException e) {
			log.error("Mina服务端启动异常:{}",e);
			e.printStackTrace();
		}
	}
}
