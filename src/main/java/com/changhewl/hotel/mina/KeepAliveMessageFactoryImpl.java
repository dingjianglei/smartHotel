package com.changhewl.hotel.mina;

/**
 * Created by Administrator on 2015/5/16 0016.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * @ClassName KeepAliveMessageFactoryImpl
 * @Description 内部类，实现KeepAliveMessageFactory（心跳工厂）
 * @author cruise
 *
 */
@Slf4j
public  class KeepAliveMessageFactoryImpl implements
        KeepAliveMessageFactory {
    /** 心跳包内容 */
    private static final String HEARTBEATREQUEST = "0x11";
    private static final String HEARTBEATRESPONSE = "0x12";
    @Override
    public boolean isRequest(IoSession session, Object message) {
        log.info("请求心跳包信息: " + message);
        if (message.equals(HEARTBEATREQUEST))
            return true;
        return false;
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
//          log.info("响应心跳包信息: " + message);
//          if(message.equals(HEARTBEATRESPONSE))
//              return true;
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        log.info("请求预设信息: " + HEARTBEATREQUEST);
        /** 返回预设语句 */
        return HEARTBEATREQUEST;
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        log.info("响应预设信息: " + HEARTBEATRESPONSE);
        /** 返回预设语句 */
        return HEARTBEATRESPONSE;
//          return null;
    }


}