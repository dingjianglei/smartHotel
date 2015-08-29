package com.changhewl.hotel.mina.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ClientHandler extends IoHandlerAdapter {
  @Override
  public void messageReceived(IoSession session, Object message)
      throws Exception {
        log.info("客户端收到消息:{}",message.toString());
  }

  @Override
  public void sessionClosed(IoSession session) throws Exception {
    // TODO Auto-generated method stub
    super.sessionClosed(session);
  }

  @Override
  public void sessionIdle(IoSession session, IdleStatus status)
      throws Exception {
    // TODO Auto-generated method stub
    super.sessionIdle(session, status);
  }

  @Override
  public void messageSent(IoSession session, Object message) throws Exception {
 
    log.info("客户端发送的消息是："+message.toString());		
    //super.messageSent(session, message);
  }

  @Override
  public void sessionCreated(IoSession session) throws Exception {
    
    super.sessionCreated(session);
  }

  @Override
  public void sessionOpened(IoSession session) throws Exception {
    super.sessionOpened(session);
  }  
  
}
