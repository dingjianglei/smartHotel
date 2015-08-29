import com.changhewl.hotel.util.BytesUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaClient001 {
	public static void main(String[] args) {
        args=new String[]{"FC000C0001112233445566010010FF94"};
        if(args!=null&&args.length>0){
            String orderStr=args[0];
            // 创建客户端连接器.
            NioSocketConnector connector = new NioSocketConnector();
            connector.getFilterChain().addLast( "logger", new LoggingFilter() );
            connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName("GBK")))); //设置编码过滤器
            connector.setHandler(new com.changhewl.hotel.mina.handler.ClientHandler());//设置事件处理器
            ConnectFuture cf = connector.connect(
                    new InetSocketAddress("192.168.10.104", 10000));//建立连接
            byte[] returnbytes= BytesUtils.chatOrders(orderStr);
            cf.awaitUninterruptibly();//等待连接创建完成
            IoBuffer buffer=IoBuffer.allocate(20,false);
            buffer.put(returnbytes);

            buffer.flip();
            cf.getSession().write(buffer);//发送消息
            cf.getSession().close(true);
            cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开
            connector.dispose();
        }

	  }

}