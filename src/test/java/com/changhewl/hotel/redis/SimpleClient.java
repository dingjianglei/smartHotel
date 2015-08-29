package com.changhewl.hotel.redis;


import com.changhewl.hotel.model.RemoteModel;
import com.changhewl.hotel.model.UserDO;
import com.changhewl.hotel.util.RedisClient;

import java.util.Date;

public class SimpleClient {
    @org.junit.Test
    public void save(){
        RemoteModel model=new RemoteModel();
        model.setMac("cc-bb-cc");
        model.setTp("001");
        model.setIp("192.168.190.129");
        model.setCreateTime(new Date());
        boolean reusltCache = RedisClient.set(model.getKey(), model);
        if(reusltCache){
            System.out.println("OK");
        }
    }
    @org.junit.Test
    public void userCache(){
        //向缓存中保存对象
        UserDO zhuoxuan = new UserDO();
        zhuoxuan.setUserId("113445");
        zhuoxuan.setSex(1);
        zhuoxuan.setUname("卓轩");
        zhuoxuan.setUnickname("zhuoxuan");
        zhuoxuan.setUemail("zhuoxuan@mogujie.com");
        //调用方法处理
        boolean reusltCache = RedisClient.set("zhuoxuan", zhuoxuan);
        if (reusltCache) {
            System.out.println("向缓存中保存对象成功。");
        }else{
            System.out.println("向缓存中保存对象失败。");
        }
    }

    @org.junit.Test
    public void getUserInfo(){
        UserDO zhuoxuan = RedisClient.get("zhuoxuan",UserDO.class);
        if(zhuoxuan != null){
            System.out.println("从缓存中获取的对象，" + zhuoxuan.getUname() + "@" + zhuoxuan.getUemail());
        }
    }
}