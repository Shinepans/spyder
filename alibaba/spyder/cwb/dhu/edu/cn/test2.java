package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test2 {

	public test2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {  
//    		StringBuffer buffer = new StringBuffer();
    		
            String url = "https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000721.9.dOQCzn&cat=50025135&brand=109718&q=%CC%AB%C6%BD%C4%F1&sort=s&style=g&from=sn_1_cat-qp&suggest=0_1&active=2#J_crumbs";  
            System.out.println("访问地址:" + url); 
            get302(url);
           /* //发送get请求
            URL serverUrl = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
            conn.setRequestMethod("GET");  
            //必须设置false，否则会自动redirect到重定向后的地址  
            conn.setInstanceFollowRedirects(false);
            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
            conn.addRequestProperty("Referer", "http://matols.com/");  
            conn.connect();  
            
            //判定是否会进行302重定向
            if (conn.getResponseCode() == 302) { 
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String location = conn.getHeaderField("Location");  
                String cookies = conn.getHeaderField("Set-Cookie");  
                
	            serverUrl = new URL(location);  
	            conn = (HttpURLConnection) serverUrl.openConnection();  
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Cookie", cookies);
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
	            conn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
	            conn.addRequestProperty("Referer", "http://matols.com/");  
	            conn.connect();  
	            System.out.println("跳转地址:" + location); 
            }*/
            
            //将返回的输入流转换成字符串  
           /* InputStream inputStream = conn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
  
            System.out.println(buffer.toString());*/
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

	}
	
	public static String get302(String url){
		  //发送get请求
		try {  
		 URL serverUrl = new URL(url);  
         HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
         conn.setRequestMethod("GET");  
         //必须设置false，否则会自动redirect到重定向后的地址  
         conn.setInstanceFollowRedirects(false);
         conn.addRequestProperty("Accept-Charset", "UTF-8;");  
         conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
         conn.addRequestProperty("Referer", "http://matols.com/");  
         conn.connect();  
         
         //判定是否会进行302重定向
         if (conn.getResponseCode() == 302) { 
             //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
             String location = conn.getHeaderField("Location");  
             String cookies = conn.getHeaderField("Set-Cookie");  
             
	            serverUrl = new URL(location);  
	            conn = (HttpURLConnection) serverUrl.openConnection();  
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Cookie", cookies);
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
	            conn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
	            conn.addRequestProperty("Referer", "http://matols.com/");  
	            conn.connect();  
	            System.out.println("跳转地址:" + location); 
        
	            get302(location); 
	            } else if (conn.getResponseCode() == 200) { 
	            	return conn.getHeaderField("Location"); 
	            }
	           
		 } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		return "";
	}

}
