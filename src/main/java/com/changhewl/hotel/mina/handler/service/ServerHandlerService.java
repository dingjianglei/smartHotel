package com.changhewl.hotel.mina.handler.service;

import com.changhewl.hotel.command.CommandModel;
import com.changhewl.hotel.mina.handler.HandlerHelper;
import com.changhewl.hotel.model.StaticModel;
import com.changhewl.hotel.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * 服务器服务实现类
 * 服务器指后台服务负责连接客户端可设备
 * Created by  丁江磊 2015/5/24.
 */
@Slf4j
@Service
public class ServerHandlerService {

    /**
     * 根据收到的信息验证并生成实体对象
     * @param message
     * @param session
     * @return
     */
    public CommandModel instanceModel(Object message, IoSession session)
    {
        if ((message == null) || (session == null)) {
            return null;
        }
        InetSocketAddress remoteAddress = (InetSocketAddress)session.getRemoteAddress();
        log.info("received message:" + message.toString().getBytes());

        byte[] bytes = (byte[])(byte[])message;
        CommandModel model = new CommandModel(bytes);
        model.setIp(remoteAddress.getAddress().getHostAddress());
        log.info("接收到客户端发来的消息:{}", model.toString());
        int leng = model.getLength();
        int realLen = bytes.length - 4;
        if (leng != realLen) {
            log.info("数据包发送的数据长度(length={})与实际接收的值(length={})不陪配本次请求结束", Integer.valueOf(leng), Integer.valueOf(realLen));
            return null;
        }
        log.info("数据包发送的数据长度(length={})与实际接收的值(length={})完全匹配", Integer.valueOf(leng), Integer.valueOf(realLen));

        log.info("command key={}", model.getKey());
        byte zlFCS = com.changhewl.hotel.util.BytesUtils.chatOrders(model.getFcs())[0];
        byte calcFCS = model.getChecksum();
        if (zlFCS != calcFCS) {
            log.info("客户端发送指令FCS={},服务器计算FCS={}【二者不匹配】", Byte.valueOf(zlFCS), Byte.valueOf(calcFCS));
            return null;
        }
        log.info("客户端发送指令FCS={},服务器计算FCS={} 【二者匹配】", Byte.valueOf(zlFCS), Byte.valueOf(calcFCS));

        return model;
    }

    public void dealServiceOrder(CommandModel model, StaticModel staticModel)
    {
        HandlerHelper.responToClient(staticModel, model);
    }
    public void switchControlResp(CommandModel model, StaticModel staticModel) {
        String ipv6 = CommonUtil.join(model.getDataArray().subList(2, 10), ":");
        String port = (String)model.getDataArray().subList(10, 11).get(0);
        String status = (String)model.getDataArray().subList(11, 12).get(0);
        log.info("设备应答信息：ipv6={}、port={}、status={}", new Object[] { ipv6, port, status });
        HandlerHelper.responToClient(staticModel, model);
    }
}
