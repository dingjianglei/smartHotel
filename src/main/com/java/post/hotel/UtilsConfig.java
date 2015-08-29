package java.post.hotel;
public class UtilsConfig {

	public final static int NETWORK_PHONE = 1;
	public final static int NO_NETWORK = 0;
	public final static int NETWORK_WIFI = 2;


	// ʵ������ʮ������ַ�ϲ�Ϊһ��8λ��С�ֽ�
	public static byte uniteByte(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static byte[] intToBytes(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0x000000ff);
		b[2] = (byte) ((n & 0x0000ff00) >> 8);
		b[1] = (byte) ((n & 0x00ff0000) >> 16);
		b[0] = (byte) ((n & 0xff000000) >> 32);
		return b;
	}

	/**
	 * �ϲ�����ֽ�����
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] uniteBytes(byte[]... data) {
		int length = 0;
		for (byte[] msg : data) {
			length += msg.length;
		}
		byte[] newData = new byte[length];
		int i = 0;
		for (byte[] msg : data) {
			System.arraycopy(msg, 0, newData, i, msg.length);
			i += msg.length;
		}

		return newData;
	}

	public static byte[] chatOrders(String c) {
		byte[] m = c.getBytes();
		return chatOrder(m);
	}

	public static byte[] chatOrder(byte[] m) {
		if (m.length % 2 == 0) {
			byte[] bytes = new byte[m.length / 2];
			for (int i = 0, j = 0; i < m.length; i += 2, j++) {
				bytes[j] = uniteByte(m[i], m[i + 1]);
			}
			return bytes;
		}
		return null;
	}

	/**
	 * ����ʮ������ַ�
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] decodeByte(byte src) {
		byte[] des = new byte[2];
		des[0] = (byte) (src & 0x0f);
		des[1] = (byte) ((src & 0xf0) >> 4);
		return des;
	}

	/**
	 * ����ʮ������ַ���16�����ַ�
	 * 
	 */
	public static String decodeByteToHexString(byte src) {
		byte[] des = new byte[2];
		des[1] = (byte) (src & 0x0f);
		des[0] = (byte) ((src & 0xf0) >> 4);
		return Integer.toHexString(des[0]) + Integer.toHexString(des[1]);
	}

	public static String decodeBytesToHexString(byte[] data) {
		String result = new String();
		for (byte dd : data) {
			result = result.concat(decodeByteToHexString(dd));
		}
		return result;
	}

	public static byte[] longToBytes(long num) {
		byte[] targets = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((num >>> offset) & 0xff);
		}
		return targets;
	}

	/**
	 * Convert byte array to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder sbuf = new StringBuilder();
		for (int idx = 0; idx < bytes.length; idx++) {
			int intVal = bytes[idx] & 0xff;
			if (intVal < 0x10)
				sbuf.append("0");
			sbuf.append(Integer.toHexString(intVal).toUpperCase());
		}
		return sbuf.toString();
	}

	/**
	 * Get utf8 byte array.
	 * 
	 * @param str
	 * @return array of NULL if error was found
	 */
	public static byte[] getUTF8Bytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (Exception ex) {
			return null;
		}
	}


	// ��JAVA�Դ�ĺ���
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}


	
}