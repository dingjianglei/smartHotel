package java.post;

import java.util.Calendar;

public class BleHack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf("1420884628000"));
		System.out.println(calendar.getTime().toGMTString());
	}
}
