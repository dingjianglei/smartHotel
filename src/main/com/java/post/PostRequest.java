package java.post;

import java.net.CookieStore;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PostRequest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int a = 3;
		switch (a) {
		case 1:
			break;
		default:
			System.out.println(1);
			break;
		}
	}

	/**
	 * ����·��post
	 * 
	 * @author Administrator
	 * 
	 */
	class PostRequestThread extends Thread {

		private final String httpUrl;
		private final int connectType;
		private ArrayList<NameValuePair> params;
		@SuppressWarnings("deprecation")
		private DefaultHttpClient client;

		private HttpPost httpRequest = null;
		private HttpResponse httpResponse = null;

		public PostRequestThread(String url, ArrayList<NameValuePair> params,
				int connecType) {
			this.httpUrl = url;
			this.connectType = connecType;
			this.params = params;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				/* ����HttpPost���� */
				httpRequest = new HttpPost(httpUrl);
				String encodedType = HTTP.DEFAULT_CONTENT_CHARSET;
				// �����������·������ͬ
				if (httpUrl.endsWith(".action")) {
					encodedType = "GBK";
					// if (null != JSESSIONID) {
					// httpRequest.setHeader("cookie", "JSESSIONID="
					// + JSESSIONID);
					// }
					HttpEntity entity = new UrlEncodedFormEntity(params,
							encodedType);
					httpRequest.setEntity(entity);
				} else {
					// encodedType = HTTP.UTF_8;
					// if (null != HSESSION) {
					// httpRequest.setHeader("cookie", "HSESSION=" + HSESSION);
					// }
					HttpEntity entity = new UrlEncodedFormEntity(params);
					httpRequest.setEntity(entity);
				}
				// ȡ��HTTP response
				client = new DefaultHttpClient();
				// ����ʱ
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
				// ��ȡ��ʱ
				client.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, 8000);

				httpResponse = client.execute(httpRequest);
				int responseCode = httpResponse.getStatusLine().getStatusCode();
				// ��״̬��Ϊ200
				if (responseCode == 200) {
					// CookieStore mCookieStore = client.getCookieStore();
					// List<Cookie> cookies = mCookieStore.getCookies();
					// for (int i = 0; i < cookies.size(); i++) {
					// // �����Ƕ�ȡCookie['JSESSIONID']��ֵ���ھ�̬�����У���֤ÿ�ζ���ͬһ��ֵ
					// if ("JSESSIONID".equals(cookies.get(i).getName())) {
					// JSESSIONID = cookies.get(i).getValue();
					// break;
					// }
					// if ("HSESSION".equals(cookies.get(i).getName())) {
					// HSESSION = cookies.get(i).getValue();
					// break;
					// }
					// }
					// ȡ����Ӧ�ִ�
					String result = EntityUtils.toString(
							httpResponse.getEntity(), "UTF-8");
				} else {
					switch (responseCode) {
					case 400:
						// �������
						break;
					case 401:
						// δ��Ȩ
						break;
					case 403:
						// ��ֹ����
						break;
					case 404:
						// �ļ�δ�ҵ�
						break;
					case 500:
						// ����������
						break;
					default:
						// �������
						break;
					}

				}
			} catch (SocketTimeoutException e1) {
				e1.printStackTrace();
			} catch (HttpHostConnectException e2) {
				e2.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// ���������쳣����
			} finally {
				client.close();
				client = null;
			}
		}
	}

}
