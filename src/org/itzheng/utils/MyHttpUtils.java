package org.itzheng.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpUtils {
	/**
	 * 发送post请求
	 * 
	 * @param httpUrl 请求地址
	 * @param body
	 * @return
	 * @throws IOException
	 */
	public static String post(String httpUrl, String body) throws IOException {
//		System.out.println("" + info);
		// 1, 得到URL对象
		URL url = new URL(httpUrl);
		// 2, 打开连接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 3, 设置提交类型
		conn.setRequestMethod("POST");
		// 4, 设置允许写出数据,默认是不允许 false
		conn.setDoOutput(true);
		conn.setDoInput(true);// 当前的连接可以从服务器读取内容, 默认是true
		conn.setConnectTimeout(5 * 1000);
		conn.addRequestProperty("content-type", "application/json");
		// 5, 获取向服务器写出数据的流
		java.io.OutputStream os = conn.getOutputStream();
		// 参数是键值队 , 不以"?"开始
		os.write(body.getBytes());
		// os.write("googleTokenKey=&username=admin&password=5df5c29ae86331e1b5b526ad90d767e4".getBytes());
		os.flush();
		// 6, 获取响应的数据
		// 得到服务器写回的响应数据
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String str = br.readLine();
//		System.out.println("响应内容为:  " + str);
		return str;
	}

}
