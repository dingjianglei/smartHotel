package java.post.hotel;

public class HotelHost {

	public final static String IP = "218.244.129.250";
	public final static String MAC = "20F41B90F308";
	public final static int port = 10000;
	public static SocketThread socketThread;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		socketThread = new SocketThread();
		socketThread.initSocket();
		socketThread.start();
	}

}
