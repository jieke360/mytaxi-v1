package com.mytaxi.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author @DaiChao
 * @date 2011-6-29
 */
public class URLConnectionClient {

	private HttpURLConnection hConn;
	private URL url;

	/**
	 * 构造方法（绑定URL和请求属性）
	 * 
	 * @param url
	 * @param prop
	 * @throws IOException
	 */
	URLConnectionClient(String url, URLReqProps prop) throws IOException {
		this.setUrl(new URL(url));
		setRequestProperties(prop);
	}

	/**
	 * 构造方法（绑定URL和请求属性）
	 * 
	 * @param url
	 * @param prop
	 * @throws IOException
	 */
	URLConnectionClient(URL url, URLReqProps prop) throws IOException {
		this.setUrl(url);
		setRequestProperties(prop);
	}

	/**
	 * 构造方法（绑定请求属性）
	 * 
	 * @param prop
	 * @throws IOException
	 */
	public URLConnectionClient(URLReqProps prop) throws IOException {
		setRequestProperties(prop);
	}

	/**
	 * 设置请求属性
	 * 
	 * @param prop
	 * @throws IOException
	 */
	private void setRequestProperties(URLReqProps prop) throws IOException {
		if (getUrl() == null) {
			String address = prop.getAddress();
			setUrl(new URL(address));
			System.out.println("@Authority: " + getUrl().getAuthority());
			//如果是https，此处会触发握手
			/*System.out.println("@Content: " + url.getContent());*/
			System.out.println("@DefaultPort: " + getUrl().getDefaultPort());
			System.out.println("@File: " + getUrl().getFile());
			System.out.println("@Host: " + getUrl().getHost());
			System.out.println("@Path: " + getUrl().getPath());
			System.out.println("@Port: " + getUrl().getPort());
			System.out.println("@Protocol: " + getUrl().getProtocol());
			System.out.println("@Query: " + getUrl().getQuery());
			System.out.println("@Ref: " + getUrl().getRef());
			System.out.println("@UserInfo: " + getUrl().getUserInfo());
		}

		String protocol = getUrl().getProtocol();
		if (protocol.equals("http")) {
			hConn = (HttpURLConnection) getUrl().openConnection();
		} else {
			hConn = (HttpsURLConnection) getUrl().openConnection();
		}
		setProperties(prop);
	}

	public void setSSLSocketFac(SSLSocketFactory sslSockFac) {
		if (hConn instanceof HttpsURLConnection) {
			((HttpsURLConnection) hConn).setSSLSocketFactory(sslSockFac);
		}
	}

	private void setProperties(URLReqProps prop) throws ProtocolException {

		String variable_1 = null;
		int variable_2 = 0;

		hConn.setDoInput(prop.isDoInput());
		hConn.setDoOutput(prop.isDoOutput());
		hConn.setUseCaches(prop.isUseCaches());

		// 设置请求方法
		variable_1 = prop.getRequestMethod();
		if (variable_1 != null) {
			hConn.setRequestMethod(variable_1);
		}

		// 设置连接超时
		variable_2 = prop.getConnectTimeout();
		if (variable_2 != 0) {
			hConn.setConnectTimeout(variable_2);
		}

		// 设置读取超时
		variable_2 = prop.getReadTimeout();
		if (variable_2 != 0) {
			hConn.setReadTimeout(variable_2);
		}

		Set<Entry<String, String>> propMap = prop.getRequestProperties()
				.entrySet();
		for (Entry<String, String> entry : propMap) {
			hConn.setRequestProperty(entry.getKey(), entry.getValue());
		}
		System.out.println(hConn.getRequestProperties());
	}

	/**
	 * 打开TCP连接
	 * 
	 * @throws IOException
	 */
	public void openConnection() throws IOException {
		hConn.connect();
	}

	/**
	 * 获取报文内容长度(会触发打开连接)
	 * 
	 * @return
	 */
	public int getContentLength() {
		int length = hConn.getContentLength();
		return length;
	}

	/**
	 * 获取所有响应头属性(会触发打开连接)
	 * 
	 * @return
	 */
	public Map<String, List<String>> getResHeads() {
		return hConn.getHeaderFields();
	}

	/**
	 * 获取响应头属性(会触发打开连接)
	 * 
	 * @param name
	 * @return
	 */
	public String getResHeadProperty(String name) {
		return hConn.getHeaderField(name);
	}

	/**
	 * 获取输入流
	 * 
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		return hConn.getInputStream();
	}

	/**
	 * 获取输出流(会触发打开连接)
	 * 
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException {
		return hConn.getOutputStream();
	}

	/**
	 * 获取响应码
	 * 
	 * @throws IOException
	 */
	public int getResponseCode() throws IOException {
		return hConn.getResponseCode();
	}

	/**
	 * @throws IOException
	 * 
	 */
	public String getResponseInfo() throws IOException {
		return hConn.getResponseMessage();
	}

	/**
	 * 读入数据
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public byte[] readData(byte[] data) throws IOException {

		BufferedInputStream buffInput = new BufferedInputStream(
				getInputStream());
		buffInput.read(data);
		return data;
	}

	/**
	 * 写出数据(会触发打开连接)
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void writeData(byte[] data) throws IOException {
		BufferedOutputStream buffOutput = new BufferedOutputStream(
				getOutputStream());
		buffOutput.write(data);
	}

	/**
	 * 读取报文(会触发打开连接)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readMessage() throws IOException {
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				getInputStream(), "UTF-8"));
		StringBuilder strB = new StringBuilder();
		String temp;
		while ((temp = buffReader.readLine()) != null) {
			strB.append(temp);
			strB.append("\n");
		}
		buffReader.close();

		return strB.toString();
	}

	/**
	 * 写入报文（建立TCP连接）
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void writeMessage(String msg) throws IOException {
		BufferedWriter bufWrit = new BufferedWriter(new OutputStreamWriter(
				hConn.getOutputStream()));
		bufWrit.write(msg);
		bufWrit.flush();

	}

	/**
	 * 关闭连接
	 */
	public void disconnect() {
		/*
		 * try { if (outputStr != null) { outputStr.close(); } if (inputStr !=
		 * null) { inputStr.close(); } if (reader != null) { reader.close(); } }
		 * catch (IOException e) { outputStr = null; inputStr = null; reader =
		 * null; e.printStackTrace(); }
		 */
		hConn.disconnect();
	}

	public static void main(String[] args) throws Exception {
		System.out
				.println(new URL(
						"HTTPS://mybank.ccb.com.cn/app/B2CMainPlatV5?CCB_IBSVersion=V5&CUSTYPE=1&TXCODE=WSMLOG")
						.getProtocol());
		test2();

	}

	static void testGet1() throws Exception {
		URLReqProps prop = new URLReqProps();
		prop
				.setAddress("http://127.0.0.1:8081/webRequestWatching/servlet/ListAllData?para1=参数壹&para2=参数贰&para3=参数叁");
		prop.setDoOutput(true);
		prop.setRequestProperty("Content-Type", "text/html; charset=utf-8");
		prop.setRequestProperty("Accept", "text/html; charset=utf-8");
		URLConnectionClient conn = new URLConnectionClient(prop);

		/* conn.writeMessage("asdf"); */

		System.out.println(conn.getResponseCode() + conn.getResponseInfo());
		System.out.println(conn.readMessage());
		conn.disconnect();
	}

	static void testGet2() {
		try {
			URLReqProps prop = new URLReqProps();
			prop
					.setAddress("http://127.0.0.1:8081/webRequestWatching/servlet/ListAllData");
			prop.setDoOutput(true);
			prop.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			prop.setRequestProperty("Accept", "text/html; charset=utf-8");
			URLConnectionClient conn = new URLConnectionClient(prop);
			conn.openConnection();

			conn.writeMessage("para1=参数壹&para2=参数贰&para3=参数叁");

			System.out.println(conn.getResponseCode());

			System.out.println(conn.readMessage());
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void testPost1() {
		try {
			URLReqProps prop = new URLReqProps();
			prop
					.setAddress("http://127.0.0.1:8081/webRequestWatching/servlet/ListAllData?para1=参数壹&para2=参数贰&para3=参数叁");
			prop.setDoOutput(true);
			prop.setRequestMethod("POST");
			prop.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			prop.setRequestProperty("Accept", "text/html; charset=utf-8");
			URLConnectionClient conn = new URLConnectionClient(prop);
			conn.openConnection();

			/* conn.writeMessage(""); */

			System.out.println(conn.getResponseCode());

			System.out.println(conn.readMessage());
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void test2() throws Exception {
		URLReqProps prop = new URLReqProps();
		prop
				.setAddress("http://12.74.239.31/payment/paygate");
		prop.setDoOutput(true);
		prop.setRequestProperty("Content-Type", "text/html; charset=utf-8");
		prop.setRequestProperty("Accept", "text/html; charset=utf-8");
		URLConnectionClient conn = new URLConnectionClient(prop);
		conn.setSSLSocketFac(null);
		conn.openConnection();
		conn.writeMessage("asdf");
		conn.writeMessage("asdf");
		Thread.sleep(1000);
		conn.writeMessage("asdf");

		System.out.println(conn.getResponseCode());

		System.out.println(conn.readMessage());
		conn.disconnect();
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

}
