package com.changhewl.hotel.mina;

import com.changhewl.hotel.model.StaticModel;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseIoHandlerAdapter extends IoHandlerAdapter{
    /**所有主机会话列表**/
    public static Map<Long,StaticModel> sessionMap=new Hashtable<Long, StaticModel>();
    /**存储远程主机会话**/
    public static Map<String,IoSession> remoteHostMap=new Hashtable<String, IoSession>();
    /**存储用户会话**/
    public static Map<String,List<IoSession>> userMap=new Hashtable<String, List<IoSession>>();
	public final static int MAIN_PROCESS_THREAD_NUM=20;
	protected static ExecutorService mainProcessThreadPool = Executors.newFixedThreadPool(MAIN_PROCESS_THREAD_NUM);
	private static AtomicInteger onUseIndex = new AtomicInteger(0);
	public static int getIndex() {
		int index = onUseIndex.getAndIncrement();
		if (index >= MAIN_PROCESS_THREAD_NUM) {
			index = 0;
			onUseIndex.set(0);
		}
		return index;
	}	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}
	
}
