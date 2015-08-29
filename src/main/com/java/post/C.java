package java.post;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class C {

	public final static String ORDER_MH = "FC";
	public final static String ORDER_ML = "FD";

	public final static class A {
		public final static String CONNECTSTATE_CHANGE = "com.changhewulian.ble.miniair.connectstate";
	}

	public static ExecutorService executors = Executors.newCachedThreadPool();
	public static ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<String>();

	public static void main(String args[]) throws Exception {
		// Map<String, Integer> map = new HashMap<String, Integer>();
		// map.put("d", 2);
		// map.put("c", 1);
		// map.put("b", 1);
		// map.put("a", 3);
		//
		// List<Map.Entry<String, Integer>> infoIds = new
		// ArrayList<Map.Entry<String, Integer>>(
		// map.entrySet());
		//
		// // ����ǰ
		// for (int i = 0; i < infoIds.size(); i++) {
		// String id = infoIds.get(i).toString();
		// System.out.println(id);
		// }
		// d 2
		// c 1
		// b 1
		// a 3

		// ����
		// Collections.sort(infoIds, new Comparator<Map.Entry<String,
		// Integer>>() {
		// public int compare(Map.Entry<String, Integer> o1,
		// Map.Entry<String, Integer> o2) {
		// // return (o2.getValue() - o1.getValue());
		// return (o1.getKey()).toString().compareTo(o2.getKey());
		// }
		// });

		// �����
		// for (int i = 0; i < infoIds.size(); i++) {
		// String id = infoIds.get(i).toString();
		// System.out.println(id);
		// }

		// byte[] data = { 2, 1, 6, 3, 2, -32, -1, 18, 9, 77, 105, 110, 100, 80,
		// 117, 115, 104, 76, 105, 103, 104, 116, 101, 114, 0, 0, 2, 10 };

		// byte[] data = { 2, 1, 6, 3, 2, -32, -1 };
		// byte[] newData=new byte[2];
		// System.arraycopy(data, 0, newData, 0, 2);

		// byte[] data = { -4, 0, 21, 0, 1, 0, 12, 41, -76, -22, -20, 3, 1, 2,
		// 1,
		// 0, 18, 75, 0, 4, 44, -48, -22, 3, -54 };
		//
		// int v2 = 202;
		// int v1 = -1;
		// if ((v1 | v2) < 0){
		// System.out.println("error");
		// return;
		// }

		// System.out.println(((v2 << 8) + (v1 << 0)));

		// byte[] dda = { -4, -84, 4, 0, 1, 0, 0, 0, 0, 0, -1, 3, 0, 0, 0, -77,
		// -20 };
		// byte value = 0;
		// for (int i = 1; i < 15; i++) {
		// value += dda[i];
		// }
		// int crc = getAbsValue(dda[15]);

		// String message = "mm" + "\n" + "ssd";

		byte[] dda = { 38, -127, -61, 53, 11, 13, 12, 16, 15, 0, 0, 0, 97, -92 };
		int ch4 = dda[4];
		int ch3 = dda[3];
		int ch2 = dda[2];
		int ch1 = dda[1];
		// if ((ch1 | ch2 | ch3 | ch4) < 0)
		// throw new EOFException();

		String mmString = "FC0704023DEBAEC2";
		String mmString1 = "002d000120f41b90f6950301020300124b0007250ab20a010000124b0007250ab209010000124b0007250ab2080100";
		byte[] cachebyte = { 6, -43, 28, 86, 41, 125, 49, 1, 0 };
		int value = cachebyte[0];
		for (int i = 1; i < cachebyte.length; i++) {
			value = value ^ cachebyte[i];
		}
		byte xx = (byte) value;
		System.out.println(decodeByteToHexString(xx));

		Calendar calendar = Calendar.getInstance();
		long time = Long.valueOf("1436483017957");
		calendar.setTimeInMillis(time);

		byte[] mm = { -53, -49, -63, 0 };
		ch4 = mm[0] + 256;
		ch3 = mm[1] + 256;
		ch2 = mm[2] + 256;
		ch1 = mm[3];
		System.out
				.println(((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0)));
		long cvalue = ch4 + ch3 * 256 + ch2 * 256 * 256;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse("2015-01-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long BASETIME = cal.getTimeInMillis();

		System.out.println((cvalue * 1000) + " " + BASETIME);

		cal.setTimeInMillis(BASETIME + cvalue * 1000);
		System.out.println(df.format(cal.getTime()));

		calendar.setTimeInMillis(Long.valueOf("1420045774870"));
		// ������������ĸ�ʽ
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(UtilsNet.isMac("20F41B90F696"));
		
		getRegisterHotel();
	}

	/**
	 * ��ȡע������
	 */
	public static void getRegisterHotel() {
		String mac = "20F41B90F310";
		String msg = "fc000b00 01" + mac + "03 01 01";
		byte[] data = chatOrders(msg.replace(" ", ""));

		int value = data[0];
		for (int i = 1; i < data.length; i++) {
			value = value ^ data[i];
		}
		System.out.println(decodeBytesToHexString(uniteBytes(data,
				new byte[] { (byte) value })));
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static int getAbsValue(byte value) {
		int crc = value;
		if (crc < 0) {
			crc = 256 + crc;
		}
		return crc;
	}

	class Holder {
		int m;

		/**
		 * @return the m
		 */
		public int getM() {
			return m;
		}

		/**
		 * @param m
		 *            the m to set
		 */
		public void setM(int m) {
			this.m = m;
		}
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			// ���MD5ժҪ�㷨�� MessageDigest ����
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// ʹ��ָ�����ֽڸ���ժҪ
			mdInst.update(btInput);
			// �������
			byte[] md = mdInst.digest();
			// ������ת����ʮ����Ƶ��ַ���ʽ
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	public static byte[] intToBytes(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0x000000ff);
		b[2] = (byte) ((n & 0x0000ff00) >> 8);
		b[1] = (byte) ((n & 0x00ff0000) >> 16);
		b[0] = (byte) ((n & 0xff000000) >> 32);
		return b;
	}

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
}