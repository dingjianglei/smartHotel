package com.changhewl.hotel.mina.handler;

import com.changhewl.hotel.command.CommandModel;
import com.changhewl.hotel.mina.BaseIoHandlerAdapter;
import com.changhewl.hotel.model.SessionFlag;
import com.changhewl.hotel.model.StaticModel;
import com.changhewl.hotel.util.BytesUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HandlerHelper
{
    private static final Logger log = LoggerFactory.getLogger(HandlerHelper.class);

    public static void sendOrder(CommandModel model, IoSession session, String direction, String[] datas)
    {
        List dataArray = new ArrayList();
        if (datas != null) {
            for (String data : datas) {
                dataArray.add(data);
            }
        }

        if (direction != null) {
            model.setDirection(direction);
        }

        if (datas != null) {
            model.setDataArray(dataArray);
        }

        int length = 9 + dataArray.size();
        String order = model.compentOrder(length);
        log.info("服务端向设备发送的指令:【{}】", order);
        byte[] orderBytes = BytesUtils.chatOrders(order);

        IoBuffer buffer = IoBuffer.allocate(1024, false).setAutoExpand(true);
        buffer.put(orderBytes);

        buffer.flip();

        session.write(buffer);
    }
    public static void sendOrder(IoSession session, byte[] orderBytes) {
        IoBuffer buffer = IoBuffer.allocate(1024, false).setAutoExpand(true);
        buffer.put(orderBytes);

        buffer.flip();

        session.write(buffer);
    }

    public static void sessionDestory(IoSession session) {
        try{
            StaticModel staticModel = (StaticModel)BaseIoHandlerAdapter.sessionMap.get(Long.valueOf(session.getId()));
            if (staticModel == null) {
                log.info("sessionId={} StaticModel is null", Long.valueOf(session.getId()));
                return;
            }

            if (SessionFlag.USER.getFlag().equals(staticModel.getSessionFlag()))
            {
                List<IoSession> list=BaseIoHandlerAdapter.userMap.get(SessionFlag.USER.getFlag() + "_MAC_(" + staticModel.getMac() + ")");
                if(list!=null&&list.size()>0){
                    list.remove(session);
                }
            }else{
                BaseIoHandlerAdapter.remoteHostMap.remove(SessionFlag.REMOTE.getFlag() + "_MAC_(" + staticModel.getMac() + ")");
                BaseIoHandlerAdapter.sessionMap.remove(session.getId());
            }
        }catch(Exception e){
            log.error("执行sessionDestory方法异常{}",e);
        }finally {
            session.close(true);
        }

    }

    public static void responToClient(StaticModel staticModel, CommandModel model)
    {
        staticModel.setReverse(true);
        List<IoSession> clientSessionList = ServiceHandler.userMap.get(staticModel.getKey());
        if(clientSessionList!=null) {
            log.info("本次有【{}】个客户端在线",clientSessionList.size());
            for (IoSession userSession : clientSessionList) {
                if (userSession != null) {
                    sendOrder(userSession, model.getDataBytes());
                    log.info("服务端向客户端发送指令:【{}】", model);
                } else {
                    log.info("MAC=【{}】用户不在线", staticModel.getMac());
                }
            }
        }
    }
}