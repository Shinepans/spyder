package browser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
 

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
 
 
 

public class SearchEngine {
 public static String UrlString = "http://hk.rd.yahoo.com/homeb/search/t1/*-http://hk.search.yahoo.com/search?";
 public static int Port = 80;
 
 
 public String search(String text) throws ClientProtocolException, IOException{  
  DefaultHttpClient httpClient = new DefaultHttpClient();
 
  String queryString = "";
  text = URLEncoder.encode(text, "UTF-8");
 
  queryString = queryString + "p=" + text + "&fr=FP-tab-web-t&ei=UTF-8&meta=rst%3Dhk";
 

  
  
  String url = UrlString + queryString;
  
  HttpGet req = new HttpGet(url);
 
  ResponseHandler<String> responseHandler = new BasicResponseHandler();
  String responseBody = httpClient.execute(req, responseHandler);
  //System.out.println(responseBody);
  /*
  StringBuilder buffer = new StringBuilder(responseBody);
  int index1 = 0;
  int index2 = 0;
  while((index1 = buffer.indexOf("<script>"))>0){
   index2 = buffer.indexOf("</script>");
   if(index2>0){
    index2 = index2 + 9;
    buffer.delete(index1, index2);    
   }
  }
   
  responseBody = buffer.toString(); 
  
  System.out.println(responseBody);
  */
  
  return responseBody;
  
 
  
  
  
 
 }
 
 
}
