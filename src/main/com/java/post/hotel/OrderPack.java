package java.post.hotel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ������
 * 
 * @author Administrator
 * 
 */
public class OrderPack {
	private int order;
	private int length;
	private int pid;
	private String mac;
	private int dir;
	private int d0;
	private int d1;

	private byte[] data;
	private OrderInputStream orderInputStream;

	public OrderPack(byte[] data) {
		this.data = data;
	}

	public void unPack() {
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		orderInputStream = new OrderInputStream(input);
		try {
			this.order = orderInputStream.read();
			byte[] lengthByte = new byte[2];
			orderInputStream.read(lengthByte);
			this.length = Utils.getLength(lengthByte);

			lengthByte = new byte[2];
			orderInputStream.read(lengthByte);
			this.pid = Utils.getLength(lengthByte);

			lengthByte = new byte[6];
			orderInputStream.read(lengthByte);
			this.mac = UtilsConfig.bytesToHex(lengthByte);

			this.dir = orderInputStream.read();
			this.d0 = orderInputStream.read();
			this.d1 = orderInputStream.read();

		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {

		}
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac
	 *            the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the dir
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * @return the d0
	 */
	public int getD0() {
		return d0;
	}

	/**
	 * @param d0
	 *            the d0 to set
	 */
	public void setD0(int d0) {
		this.d0 = d0;
	}

	/**
	 * @return the d1
	 */
	public int getD1() {
		return d1;
	}

	/**
	 * @param d1
	 *            the d1 to set
	 */
	public void setD1(int d1) {
		this.d1 = d1;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * ������ǰ�����豸
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
	public List<DeviceVo> getCurrentDevice() throws IOException {
		int nums = orderInputStream.read();
		List<DeviceVo> lists = new ArrayList<DeviceVo>();
		for (int i = 0; i < nums; i++) {
			DeviceVo device = new DeviceVo();
			device.setIeee(UtilsConfig.bytesToHex(orderInputStream.read(8)));
			device.setPort(orderInputStream.read());
			device.setType(orderInputStream.readInt2());
			lists.add(device);
		}
		return lists;
	}

	public DeviceVo getPowerStatus() throws IOException {
		DeviceVo device = new DeviceVo();
		device.setIeee(UtilsConfig.bytesToHex(orderInputStream.read(8)));
		device.setPort(orderInputStream.read());
		// 0x01��
		device.setPower(orderInputStream.read() == 0x01);
		return device;
	}

	public ResultOrder getResult() throws IOException {
		ResultOrder device = new ResultOrder();
		device.setD0(d0);
		device.setD1(d1);
		device.setIeee(UtilsConfig.bytesToHex(orderInputStream.read(8)));
		device.setPort(orderInputStream.read());
		// 0x01 ��������ʧ��
		device.setResult(orderInputStream.read());
		return device;
	}

	public void close() {
		// TODO Auto-generated method stub
		if (orderInputStream == null)
			return;
		try {
			orderInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderInputStream = null;
	}

	public ResultOrder getPower() throws IOException {
		ResultOrder device = new ResultOrder();
		device.setD0(d0);
		device.setD1(d1);
		device.setIeee(UtilsConfig.bytesToHex(orderInputStream.read(8)));
		device.setPort(orderInputStream.read());
		// 0x01 ��������ʧ��
		device.setPower(orderInputStream.read() == 0x1);
		return device;
	}

	public boolean getResultByte() throws IOException {
		return orderInputStream.read() == 0x0;
	}

	/**
	 * ���ش�����
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
	public List<ResultOrder> getListResult() throws IOException {
		List<ResultOrder> orders = new ArrayList<ResultOrder>();
		int size = orderInputStream.read();
		for (int i = 0; i < size; i++) {
			ResultOrder device = new ResultOrder();
			device.setD0(d0);
			device.setD1(d1);
			device.setIeee(UtilsConfig.bytesToHex(orderInputStream.read(8)));
			device.setPort(orderInputStream.read());
			// 0x01 ��������ʧ��
			device.setResult(orderInputStream.read());
			orders.add(device);
		}
		return orders;
	}
}
