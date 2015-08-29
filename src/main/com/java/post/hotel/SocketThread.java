package java.post.hotel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.post.hotel.IOHandler.IOHandlerListener;
import java.post.hotel.OrderConfig.ORDER;

public class SocketThread extends Thread implements IOHandlerListener {

	private InputStream inputStream;
	private OutputStream outputStream;
	private Socket socket;
	private InetSocketAddress inetAddress;
	private IOHandler ioHandler;
	private int index = 5;
	private boolean hasregister = false;
	private boolean isrun = true;
	private ExecutorService executor = Executors.newScheduledThreadPool(10);

	/**
	 * ��ݻ������С
	 */
	private int length = 1024;

	public SocketThread() {
	}

	public void initSocket() {
		ioHandler = new IOHandler(this);
		inetAddress = new InetSocketAddress(HotelHost.IP, HotelHost.port);
	};

	@Override
	public void run() {
		socket = new Socket();
		try {
			// socket.setSoTimeout(5000);
			socket.connect(inetAddress);
			socket.setKeepAlive(true);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			close();
			return;
		}

		registerHost();

		while (isrun) {
			byte[] dataBuffer = new byte[length];
			try {
				int readLength = inputStream.read(dataBuffer, 0, length);
				if (readLength != -1) {
					System.out.println("read data " + readLength);
					OnReceiveData(dataBuffer, readLength);
				} else {
					System.out.println("error read data is -1");
					throw new IOException();
				}
			} catch (IOException e) {
				e.printStackTrace();
				isrun = false;
				break;
			}
		}

		close();
	}

	private void registerHost() {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				int time = 0;
				while (hasregister == false) {
					System.err.println("ע�᣺" + time + "��");
					writeByte((byte) 0x01, (byte) 0x01, OrderConfig.ORDER.DIR3,
							null);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// ����������Ϣ
	private boolean sendDataAtTime(byte[] data) {
		if (outputStream != null && data != null) {
			try {
				byte[] msg = Utils.formatbyte(data, HotelHost.MAC, index);
				System.out.println("�������� "
						+ UtilsConfig.decodeBytesToHexString(msg));
				outputStream.write(msg, 0, msg.length);
				outputStream.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void close() {
		if (socket != null) {
			try {
				socket.close();
				socket = null;

				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}

				if (outputStream != null) {
					outputStream.close();
					outputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void writeByte(byte order0, byte order1, int direction,
			byte[] data) {
		if (data == null)
			data = new byte[0];
		byte[] msg = UtilsConfig.uniteBytes(new byte[] { (byte) direction,
				order0, order1 }, data);
		sendDataAtTime(msg);
	}

	@Override
	public void getOnlineDeviceList(List<DeviceVo> onlineDevice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDevicePowerStatus(DeviceVo device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getResultOrder(ResultOrder result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOutNetDeviceList(List<DeviceVo> devices) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getIrResult(ResultOrder result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getListResultOrder(List<ResultOrder> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onToast(String string) {

	}

	public void OnReceiveData(byte[] dataBuffer, int readLength) {
		byte[] data = new byte[readLength];
		System.arraycopy(dataBuffer, 0, data, 0, readLength);
		System.out.println("�յ����� " + UtilsConfig.decodeBytesToHexString(data));
		ioHandler.handler(data);
	}

	@Override
	public void registerSuccess() {
		System.err.println("����ע��ɹ�");
		hasregister = true;
		rebackOnLineDevice();
		sendHeartData();
	}

	private void sendHeartData() {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (isrun) {
					rebackOnLineDevice();
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void rebackOnLineDevice() {
		byte[] msg = UtilsConfig.uniteBytes(new byte[] { ORDER.DIR2, 0x01,
				0x02, 0x02 }, UtilsConfig.chatOrders("1122334455667788"),
				new byte[] { (byte) 181, 0x00, 0x20 },
				UtilsConfig.chatOrders("3322334455667799"), new byte[] {
						(byte) 182, 0x01, 0x0c });
		sendDataAtTime(msg);
	}

	@Override
	public void rebackData(byte[] cachebyte) {
		cachebyte[11] = ORDER.DIR2;
		byte[] data = new byte[cachebyte.length - 1];
		System.arraycopy(cachebyte, 0, data, 0, data.length);
		sendDataAtTime(UtilsConfig.uniteBytes(data,
				new byte[] { Utils.getXX(data) }));
	}

	@Override
	public void registerFail() {
		System.err.println("����ע��ʧ��");
	}

}