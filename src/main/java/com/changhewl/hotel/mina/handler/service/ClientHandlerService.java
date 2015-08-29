package com.changhewl.hotel.mina.handler.service;

import com.changhewl.hotel.mina.handler.HandlerHelper;
import com.changhewl.hotel.mina.handler.ServiceHandler;
import com.changhewl.hotel.model.RemoteModel;
import com.changhewl.hotel.util.BytesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

/**
 * 客户端处理实现类
 * 此处客户端指 APP、微信
 * 处理客户端发来的指令
 * Created by 丁江磊 on 2015/5/24.
 */
@Slf4j
@Service
public class ClientHandlerService {
    /**
     * 处理客户端指令
     * @param remoteModel 远程主机MODEL
     * @param session 当前会话session
     * @param bytes 字节信息
     */
    public void dealClientOrder(RemoteModel remoteModel,IoSession session,byte [] bytes){
        try{
            //获取设备主机会话 session 发送指令
            IoSession remoteSession= ServiceHandler.remoteHostMap.get(remoteModel.getKey());
            if(remoteSession!=null){
                //存储用户会话信息
                // ServiceHandler.userMap.put(remoteModel.getMac() + "_USER", session);
                HandlerHelper.sendOrder(remoteSession, bytes);
                log.info("指令发送完成");
            }else{
                log.info("您连接的设备不在线");
                //00 : 你所连接的设备不在线
                HandlerHelper.sendOrder(session, BytesUtils.chatOrders("00"));
            }
        }catch (Exception e){
            log.error("发送指令到远程主机时异常{}",e);
        }
    }
}
