package alibaba.spyder.cwb.dhu.edu.cn;


import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 如何通过HttpURLConnection得到http 302的跳转地址
 * 
 * @author javaniu
 * 
 */
public class Http302 {

	public static void main(String[] args) {
		try {
			String url = "http://www.javaniu.com/302.htm";
			System.out.println("访问地址:" + url);
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl
					.openConnection();
			conn.setRequestMethod("GET");
			// 必须设置false，否则会自动redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			conn.addRequestProperty("Accept-Charset", "UTF-8;");
			conn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
			conn.addRequestProperty("Referer", "http://javaniu.com/");
			conn.connect();
			String location = conn.getHeaderField("Location");

			serverUrl = new URL(location);
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");

			conn.addRequestProperty("Accept-Charset", "UTF-8;");
			conn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
			conn.addRequestProperty("Referer", "http://javaniu.com/");
			conn.connect();
			System.out.println("跳转地址:" + location);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

