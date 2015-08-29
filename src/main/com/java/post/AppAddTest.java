package java.post;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class AppAddTest {

	public static final String ADD_URL = "http://apitest.21jieyan.com/validate/code";

	public static void appadd() {

		try {
			// ��������
			URL url = new URL(ADD_URL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			// POST����
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			Gson gson = new Gson();
			Phone phone = new Phone();
			phone.setPhone("15994849375");
			out.writeBytes(gson.toJson(phone));
			out.flush();
			out.close();

			int responseCode = connection.getResponseCode();
			System.out.println("responseCode" + responseCode);

			// ��ȡ��Ӧ
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			reader.close();
			// �Ͽ�����
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String msg = "12345";
		char num='c';
		System.out.println(msg.charAt(0)=='0');
	}

	public static String byte2bits(byte b) {
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	// ���������ַ�ת�����ֽ�

	public static byte bit2byte(String bString) {
		byte result = 0;
		for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
			result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
		}
		return result;
	}
}