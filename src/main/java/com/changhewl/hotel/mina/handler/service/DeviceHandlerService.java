package com.changhewl.hotel.mina.handler.service;

import com.changhewl.hotel.command.CommandModel;
import com.changhewl.hotel.mina.handler.HandlerHelper;
import com.changhewl.hotel.mina.handler.ServiceHandler;
import com.changhewl.hotel.model.CommandEnums;
import com.changhewl.hotel.model.RemoteModel;
import com.changhewl.hotel.util.BytesUtils;
import com.changhewl.hotel.util.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备处理实体类
 * 设备主要指 设备连接的主机
 * 处理主机发送过来的指令
 * Created by 丁江磊 on 2015/5/24.
 */
@Slf4j
@Service
public class DeviceHandlerService {
    /***
     * 处理设备发送的心跳指令
     * @param model
     * @param remoteModel
     * @param session
     */
    public void dealHeartBeat(CommandModel model,RemoteModel remoteModel,IoSession session){ {
        int deviceNum=Integer.valueOf(BytesUtils.decodeByteToHexString(model.getDataBytes()[14]),16);
        log.info("服务器收到【{}】消息,服务器本次传递了【{}】个设备信息", CommandEnums.PTOS_S3_0102.getDescription(),deviceNum);
        RemoteModel myRemoteModel=RedisClient.get(remoteModel.getKey(),RemoteModel.class);
        if(myRemoteModel==null){
            log.info("服务器收到未注册主机心跳指令");
            return;
        }
        if(ServiceHandler.remoteHostMap.get(remoteModel.getKey())==null){
            ServiceHandler.remoteHostMap.put(remoteModel.getKey(), session);
        }
        if(deviceNum>0){
            //FLG + LENGTH +TP +MAC +DIR =12 FCS=1 指令=2 设备个数=1
            int deviceInfoNum=(model.getDataBytes().length-12-1-2-1)/deviceNum;
            List<String> deviceInfoList=new ArrayList<String>();
            int num=0;
            StringBuffer sb=new StringBuffer();
            //getDataArray 包含 2 byte 指令 和 1 byte设备个数
            for(int i=0;i<model.getDataArray().size();i++){
                if(i>2){
                    sb.append(model.getDataArray().get(i));
                    num++;
                    if(num%deviceInfoNum==0){
                        deviceInfoList.add(sb.toString());
                        sb.setLength(0);
                        sb.append(model.getDataArray().get(i));
                    }
                }
            }
            for(String str:deviceInfoList){
                String ip=str.substring(0, 16);
                String port=str.substring(16,18);
                String deviceType=null;

                if(str.length()>18){//说明设备信息是IP:Port
                    deviceType=str.substring(18,22);
                }
                log.info("设备信息(IP={}，PORT={} 设备类型={})",ip,port,deviceType);
            }
        }
        //给服务端响应
        HandlerHelper.sendOrder(model, session, "04", (new String[]{"01", "02"}));
        log.info("【服务端向设备响应心跳指令发送成功】");
    }
    }

    /**
     * 节点注册上报方法实现
     * @param model
     * @param sessionList
     */
    public void nodeRegisterReport(CommandModel model,List<IoSession> sessionList){
        try{
            log.info("服务器收到【{}】消息",CommandEnums.PTOS_S3_0110.getDescription());
            StringBuffer ipsb=new StringBuffer();
            String port="";
            String deviceId="";
            String [] orderDatas=new String[12];//2+8+1
            orderDatas[0]="01";
            orderDatas[1]="10";
            for(int i=0;i<model.getDataArray().size();i++){
                if(i>2&&i<10){//大于2的信息为 IEEE+PORT+DeviceID
                    ipsb.append(model.getDataArray().get(i));
                }else if(i==10){
                    port=model.getDataArray().get(i);
                }else if(i>2&&i<=12){
                    deviceId+=model.getDataArray().get(i);
                }
                if(i>2){
                    orderDatas[i-1]=model.getDataArray().get(i);
                }
            }
            orderDatas[11]="00";
            log.info("收到节点上报信息:(IP={},PORT={},DEVICEID={})",ipsb.toString(),port,deviceId);
            //请考虑异常情况 反馈给服务端验证码
            for(IoSession session:sessionList){
                HandlerHelper.sendOrder(model,session,"04",orderDatas);
            }
        }catch (Exception e){
              log.error("(nodeRegisterReport方法)发送指令到客户端程序异常{}",e);
        }


    }
}
