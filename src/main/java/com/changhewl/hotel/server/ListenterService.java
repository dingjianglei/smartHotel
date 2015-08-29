package com.changhewl.hotel.server;

import com.changhewl.hotel.mina.ByteArrayCodecFactory;
import com.changhewl.hotel.mina.KeepAliveMessageFactoryImpl;
import com.changhewl.hotel.mina.handler.KeepAliveRequestTimeoutHandlerImpl;
import com.changhewl.hotel.mina.handler.ServiceHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ListenterService {
	private static final int port = 10000;
    /** 30秒后超时 */
    private static final int IDELTIMEOUT = 30;
    /** 15秒发送一次心跳包 */
    private static final int HEARTBEATRATE = 15;
    @Autowired
    ServiceHandler serviceHandler;
    @Autowired
    ByteArrayCodecFactory byteArrayCodecFactory;
    @Autowired
    LoggingFilter  loggingFilter;
	public void start() throws IOException {
        log.info("-------------------启动服务端---------------------");
	  // 用于创建服务端的监听
	  IoAcceptor acceptor = new NioSocketAcceptor();
      Executor threadPool = Executors.newFixedThreadPool(1500);// 建立线程池
      acceptor.getFilterChain().addLast( "exector", new ExecutorFilter( threadPool ) );
	  acceptor.getFilterChain().addLast("logger", loggingFilter);
	  // 编码过滤器
	  acceptor.getFilterChain().addLast("encode",new ProtocolCodecFilter(byteArrayCodecFactory));
        // 设置事件处理类
      acceptor.setHandler(serviceHandler);



      if(InitServer.IS_OPEN_HEART){
          KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
          //下面注释掉的是自定义Handler方式
          KeepAliveRequestTimeoutHandler heartBeatHandler = new
                  KeepAliveRequestTimeoutHandlerImpl();
          KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
                  IdleStatus.BOTH_IDLE, heartBeatHandler);
          //设置是否forward到下一个filter
          heartBeat.setForwardEvent(true);
          //设置心跳频率
          heartBeat.setRequestInterval(HEARTBEATRATE);
          acceptor.getFilterChain().addLast("heartbeat", heartBeat);
      }

        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 60);
        acceptor.getSessionConfig().setUseReadOperation(true);
      // 设置地址和端口
	  acceptor.setDefaultLocalAddress(new InetSocketAddress(InitServer.SERVICE_IP,InitServer.SERVICE_PORT));
	  acceptor.bind();
	  log.info("-------------------服务端启动成功---------------------");
	 }
}
