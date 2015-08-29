import com.changhewl.hotel.mina.ByteArrayCodecFactory;
import com.changhewl.hotel.mina.handler.ClientHandler;
import com.changhewl.hotel.server.InitServer;
import com.changhewl.hotel.util.BytesUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class MinaClient {
    public static void main(String[] args){
        //FC000b000120F41B90F22A01010489
        //FC000c000120F41B90F22A010010FF64
        InitServer.init();
        String orderStr="FC0014000120F41B90F22A01120200124B000725354B088e";
        orderStr=orderStr.replaceAll(" ","");
        ByteArrayCodecFactory byteArrayCodecFactory=InitServer.getBean("byteArrayCodecFactory");
        // 创建一个socket连接
        NioSocketConnector connector=new NioSocketConnector();
        // 获取过滤器链

        connector.getFilterChain().addLast("encode", new ProtocolCodecFilter(byteArrayCodecFactory));

        // 消息核心处理器
        connector.setHandler(new ClientHandler());
        // 设置链接超时时间
        connector.setConnectTimeoutCheckInterval(30);
        // 连接服务器，知道端口、地址
        ConnectFuture cf = connector.connect(new InetSocketAddress(InitServer.SERVICE_IP,InitServer.SERVICE_PORT));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
        byte[] returnbytes= BytesUtils.chatOrders(orderStr);
        IoBuffer buffer=IoBuffer.allocate(1024,false);
        buffer.put(returnbytes);

        buffer.flip();
        cf.getSession().write(buffer);//发送消息

        cf.getSession().getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
