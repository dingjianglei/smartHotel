package com.changhewl.hotel.server;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务启动入口
 * Created by Administrator on 2015/5/17 0017.
 */
@Slf4j
public class HotelMain {
    public static void main(String[] args) {
        try{
            InitServer.init();
            ListenterService listenterService=InitServer.getBean("listenterService");
            listenterService.start();
            log.info(">>>>>>>>>>>>>>> 服务启动成功 <<<<<<<<<<<<<<<<<");
            String obj = new String();
            synchronized(obj) {
                obj.wait();
            }
        }catch (Exception e){
            log.error("服务启动异常:{}",e);
        }

    }
}
