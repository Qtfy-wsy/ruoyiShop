package com.zsTrade.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class HttpTest {
	public static String KEY_STORE_FILE="imp_client.p12";
	public static String KEY_STORE_PASS="nymeria";
	public static String TRUST_STORE_FILE="cvte.com.jks";
	public static String TRUST_STORE_PASS="123456";


	private static SSLContext sslContext;
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 * 
	 */
	public static String sendGet(String url, String param) {
		StringBuffer result = new StringBuffer(); 
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			// 打开和URL之间的连接
			if(connection instanceof HttpsURLConnection){
				((HttpsURLConnection)connection)
				.setSSLSocketFactory(getSSLContext().getSocketFactory());
			}

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				//System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应

			if(connection.getResponseCode()==200){
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			}else{
				in = new BufferedReader(new InputStreamReader(
						connection.getErrorStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				 result.append(line + "\n");  
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String sendGet(String url) {
		 StringBuffer result = new StringBuffer(); 
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			// 打开和URL之间的连接
			if(connection instanceof HttpsURLConnection){
				((HttpsURLConnection)connection)
				.setSSLSocketFactory(getSSLContext().getSocketFactory());
			}

			// 设置通用的请求属性
			connection.setRequestProperty(
	                "Accept",
	                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
	                        + "application/x-shockwave-flash, application/xaml+xml, "
	                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
	                        + "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
		//	Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			//for (String key : map.keySet()) {
				//System.out.println(key + "--->" + map.get(key));
			//}
			// 定义 BufferedReader输入流来读取URL的响应

			if(connection.getResponseCode()==200){
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			}else{
				return result.toString();
//				in = new BufferedReader(new InputStreamReader(
//						connection.getErrorStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				 result.append(line + "\n");  
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			if(conn instanceof HttpsURLConnection){
				((HttpsURLConnection)conn)
				.setSSLSocketFactory(getSSLContext().getSocketFactory());
			}
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			if(conn.getResponseCode()==200){
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
			}else{
				in = new BufferedReader(
						new InputStreamReader(conn.getErrorStream()));
			}
			String line="";
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}    

	public static SSLContext getSSLContext(){
		long time1=System.currentTimeMillis();
		if(sslContext==null){
			try {
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(getkeyStore(),KEY_STORE_PASS.toCharArray());
				KeyManager[] keyManagers = kmf.getKeyManagers();

				TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance("SunX509");
				trustManagerFactory.init(getTrustStore());
				TrustManager[]  trustManagers= trustManagerFactory.getTrustManagers();

				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keyManagers, trustManagers, new SecureRandom());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}
		long time2=System.currentTimeMillis();
		System.out.println("SSLContext 初始化时间："+(time2-time1));
		return sslContext;
	}


	public static KeyStore getkeyStore(){
		KeyStore keySotre=null;
		try {
			keySotre = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
			keySotre.load(fis, KEY_STORE_PASS.toCharArray());
			fis.close();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keySotre;
	}
	public static KeyStore getTrustStore() throws IOException{
		KeyStore trustKeyStore=null;
		FileInputStream fis=null;
		try {
			trustKeyStore=KeyStore.getInstance("JKS");
			fis = new FileInputStream(new File(TRUST_STORE_FILE));
			trustKeyStore.load(fis, TRUST_STORE_PASS.toCharArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			fis.close();
		}
		return trustKeyStore;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		Long time1=System.currentTimeMillis();
//		int k=0;
//		for(int i=0;i<100;++i){
//			String result=sendGet("https://test.com", "username=x1");
//			System.out.println(result);
//			++k;
//		}
		System.out.println(sendGet("http://10.2.10.41/HUADI_HDZT00A1_V-1-2.json"));
		Long time2=System.currentTimeMillis();
		System.out.println("平均耗费时间:"+(time2-time1));
	}
}