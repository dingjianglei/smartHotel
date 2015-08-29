package java.post.hotel;

import org.apache.http.util.TextUtils;

import java.post.hotel.OrderConfig.ORDER;

public class Utils {

	/**
	 * �������ֽ�תΪ����ֵ,���ģʽ
	 * 
	 * @param cachebyte
	 * @return
	 */
	public static int getLength(byte[] cachebyte) {
		int value1 = cachebyte[0];
		int value2 = cachebyte[1];
		if (value1 < 0) {
			value1 = 256 + value1;
		}

		if (value2 < 0) {
			value2 = 256 + value2;
		}

		return value1 * 256 + value2;
	}

	/**
	 * С�˽���
	 * 
	 * @param cachebyte
	 * @return
	 */
	public static int getSmallLength(byte[] cachebyte) {
		int value1 = cachebyte[1];
		int value2 = cachebyte[0];
		if (value1 < 0) {
			value1 = 256 + value1;
		}

		if (value2 < 0) {
			value2 = 256 + value2;
		}

		return value1 * 256 + value2;
	}

	/**
	 * ����mac��ַ(��2����)
	 * 
	 * @param mac
	 * @return
	 */
	public static String checkMac(String mac) {
		long num = Long.parseLong(mac, 16) + 2;
		String newm = Long.toHexString(num);
		if (newm.length() < 12) {
			for (int i = newm.length(); i <= 12; i++) {
				newm = "0" + newm;
			}
		} else if (newm.length() > 12) {
			newm = newm.substring(newm.length() - 12, newm.length());
		}
		return newm;
	}

	/**
	 * ��mac��ַ����2����
	 * 
	 * @param mac
	 * @return
	 */
	public static String unCheckMac(String mac) {
		long num = Long.parseLong(mac, 16) - 2;
		String newm = Long.toHexString(num);
		if (newm.length() < 12) {
			for (int i = newm.length(); i <= 12; i++) {
				newm = "0" + newm;
			}
		} else if (newm.length() > 12) {
			newm = newm.substring(newm.length() - 12, newm.length());
		}
		return newm;
	}

	/**
	 * ���У�鴦��
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] formatbyte(byte[] data, String mac,
			int index) {
		if (TextUtils.isEmpty(mac))
			return null;
		byte[] msg = UtilsConfig.uniteBytes(intToBytes(index),
				UtilsConfig.chatOrders(mac), data);

		// �ϲ����Ⱥ�����
		msg = UtilsConfig.uniteBytes(intToBytes(msg.length), msg);
		return UtilsConfig.uniteBytes(new byte[] { (byte) ORDER.HEADER }, msg,
				new byte[] { getXX(msg) });
	}
	
	

	/**
	 * 
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] intToBytes(int n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0x000000ff);
		b[0] = (byte) ((n & 0x0000ff00) >> 8);
		return b;
	}

	/**
	 * �������ֵ
	 * 
	 * @param data
	 * @return
	 */
	public static byte getXX(byte[] data) {
		int value = data[0];
		for (int i = 1; i < data.length; i++) {
			value = value ^ data[i];
		}
		return (byte) value;
	}
}
