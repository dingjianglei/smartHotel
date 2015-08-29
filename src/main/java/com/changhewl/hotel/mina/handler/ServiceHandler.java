package com.changhewl.hotel.mina.handler;

import com.changhewl.hotel.command.CommandModel;
import com.changhewl.hotel.mina.BaseIoHandlerAdapter;
import com.changhewl.hotel.mina.handler.service.ClientHandlerService;
import com.changhewl.hotel.mina.handler.service.DeviceHandlerService;
import com.changhewl.hotel.mina.handler.service.ServerHandlerService;
import com.changhewl.hotel.model.*;
import com.changhewl.hotel.util.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/***
 * 请求指令处理类
 * 负责对所以会话进行记录，包括设备主机、APP、微信 与服务端主机的会话信息 并将当前会话存储到Map集合中
 * 对应设备主机的注册需要永久存储 通过REDIS存储到服务器端（注:和session不同，session只能存储到当前服务器内存中）
 * 对与客户端发来的指令，此处仅进行转发操作
 * 对应设备主机给以的反馈，如果有必要需要将改指令转发到客户端(注:客户端不一定在线)
 * 设备主机的心跳有助于会话的恢复
 */
@Slf4j
@Service
public class ServiceHandler extends BaseIoHandlerAdapter {
  @Autowired
  private ClientHandlerService clientHandlerService;
  @Autowired
  private DeviceHandlerService deviceHandlerService;
  @Autowired
  private ServerHandlerService serverHandlerService;
  @Override
  public void messageReceived(IoSession session, Object message)throws Exception {
      CommandModel model=serverHandlerService.instanceModel(message,session);
      StaticModel staticModel=new StaticModel();
      if(model==null){
          return;
      }
      staticModel.setMac(model.getMac());
      staticModel.setAddress(model.getIp());
      staticModel.setSessionId(session.getId());

      RemoteModel remoteModel=new RemoteModel(model);
      boolean isClient=isClientRequest(model.getKey());
      if(isClient){
          staticModel.setSessionFlag(SessionFlag.USER.getFlag());
      }else{
          staticModel.setSessionFlag(SessionFlag.REMOTE.getFlag());
      }

      log.info("isClient={}",isClient);
      log.info("<<<<<<<<<<<<<<<<<<<<<<<开始判断并设置信息到Map>>>>>>>>>>>>>>>>>>>>>>>>>>");
      if(sessionMap.get(session.getId())==null){
          //存储当前会话 StaticMoel
          sessionMap.put(session.getId(),staticModel);
          if(isClient){
              List<IoSession> sessionList=userMap.get(staticModel.getKey());
              //保存用户SESSION
              if(sessionList==null){
                  sessionList=new ArrayList<IoSession>();
              }
              sessionList.add(session);
              userMap.put(staticModel.getKey(),sessionList);
          }else{
              //保存主机SESSION
              remoteHostMap.put(staticModel.getKey(),session);
          }
      }

      log.info("<<<<<<<<<<<<<<<<<<<<<<<结束判断并设置信息到Map>>>>>>>>>>>>>>>>>>>>>>>>>>");

      //客户端发来的指令
      if(isClient){
          log.info("收到客户端发来消息：{}",model.toString());
          log.info("服务器收到【{}】消息",(model.getKey()));
          //调用方法转发指令到主机
          clientHandlerService.dealClientOrder(remoteModel,session,model.getDataBytes());
      }else{//主机请求指令、心跳、和反馈指令
          //获取客户端所有在线回话
          List<IoSession> sessionList=userMap.get(staticModel.getKey());
          if(CommandEnums.PTOS_S3_0101.getCode().equals(model.getKey())){
              //主机一般只注册一次 如果主机断开后通过心跳来回复本次会话
              log.info("服务器收到【{}】消息",CommandEnums.PTOS_S3_0101.getDescription());
              HandlerHelper.sendOrder(model, session, "04", (new String[]{"01", "01", "00"}));
              log.info("【服务端向设备发送的指令成功】");
              //存储当前会话
              remoteHostMap.put(remoteModel.getKey(), session);
              boolean reusltCache = RedisClient.set(remoteModel.getKey(), remoteModel);
              if(reusltCache){
                  log.info("主机注册信息存储成功(主机信息=【{}】)",remoteModel.toString());
              }else{
                  log.info("主机注册存储失败(主机信息=【{}】)",remoteModel.toString());
              }

          }else if(CommandEnums.PTOS_S3_0102.getCode().equals(model.getKey())){
              HandlerHelper.responToClient(staticModel, model);
              deviceHandlerService.dealHeartBeat(model,remoteModel,session);
          }else if(CommandEnums.PTOS_S2_0010.getCode().equals(model.getKey())){
              log.info("服务器收到【{}】消息",CommandEnums.PTOS_S2_0010.getDescription());
              if(model.getDataArray().get(2).equals("00")){
                  log.info("客户端IP=[]请求开放网络成功",model.getIp());
              }else{
                  log.info("用户请求开放网络命令【Mac={}】失败",model.getMac());
                  userMap.get(model.getMac() + "_USER").remove(session);
              }
          }else if(CommandEnums.PTOS_S3_0110.getCode().equals(model.getKey())) {
              deviceHandlerService.nodeRegisterReport(model,sessionList);
          } else if (CommandEnums.PTOS_S2_1001.getCode().equals(model.getKey())) {
              log.info("服务器收到【{}】消息", CommandEnums.PTOS_S2_1001.getDescription());
              this.serverHandlerService.switchControlResp(model, staticModel);
          } else if (CommandEnums.PTOS_S3_1002.getCode().equals(model.getKey())) {
              this.serverHandlerService.switchControlResp(model, staticModel);
          } else if (CommandEnums.PTOS_S4_1003.getCode().equals(model.getKey())) {
              log.info("服务器收到【{}】消息", CommandEnums.PTOS_S4_1003.getDescription());
              this.serverHandlerService.switchControlResp(model, staticModel);
          } else {
              boolean isDefined = false;
              for (CommandEnums enums : CommandEnums.values()) {
                  if (StringUtils.equals(enums.getCode(), model.getKey())) {
                      log.info("服务器收到【{}】消息", enums.getDescription());
                      isDefined = true;
                      break;
                  }
              }
              log.info("服务器收到KEY=【{}】的消息，该消息{},服务器将该消息直接转发到客户端", model.getKey(), (isDefined == true) ? "在服务端明确定义" : "未在服务端明确定义");

              HandlerHelper.responToClient(staticModel, model);
          }
      }

  }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        InetSocketAddress remoteAddress=(InetSocketAddress)session.getRemoteAddress();
        log.info("LoginConnect Server opend Session ID={}",session.getId());
        log.info("接收来自客户端IP={}的连接",remoteAddress.getAddress());
    }

  @Override
  public void sessionIdle(IoSession session, IdleStatus status)
      throws Exception {
      log.info("sessionIdle:={}",session.getId(),status);
  }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("cause.printStackTrace():{}",cause);
        HandlerHelper.sessionDestory(session);
        session.close(true);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("当前session关闭 sessionId={}",session.getId());
        HandlerHelper.sessionDestory(session);
        session.close(true);
    }

    private boolean isClientRequest(String key){
        boolean isClient=false;
/*        ClientCommandEnums[] enumses= ClientCommandEnums.values();
        for(ClientCommandEnums curenum:enumses){
            if(StringUtils.equals(curenum.getCode(),key)){
                isClient=true;
                break;
            }
        }*/
        String[] infos=key.split("_");
        //根据方向判断是否为客户端请求
        if(StringUtils.equals(infos[0],"01")||StringUtils.equals(infos[0],"04")){
            isClient=true;
        }
        return isClient;
    }
}