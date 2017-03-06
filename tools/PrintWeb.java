package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import alibaba.spyder.cwb.dhu.edu.cn.Utils;

public class PrintWeb {

	public static void main(String[] args) throws IOException {
		String urlStr ="https://www.baidu.com/";
		print(urlStr, "UTF-8");
	}
	
	public static void print(String urlStr, String characterType) throws IOException {
		BufferedReader in = null;
		URL realUrl = new URL(urlStr);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		Utils.setCharacterType(conn,characterType);
		conn.connect();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),characterType));
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}

}
