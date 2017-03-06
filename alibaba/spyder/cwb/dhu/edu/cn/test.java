package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		HttpURLConnection conn = null;
        try {
            URL realUrl = new URL("https%3A%2F%2Fsec.taobao.com%2Fquery.htm%3Faction%3DQueryAction%26event_submit_do_login%3Dok%26smApp%3Dtmallsearch%26smPolicy%3Dtmallsearch-product-anti_Spider-html-checklogin%26smCharset%3DGBK%26smTag%3DMTE2LjI0Ni4yLjI0NywsMjNhZmUyY2FmYWU3NDE5ZGI4NmM3NTA2ZjFjYWZjMzc%253D%26smReturn%3Dhttps%253A%252F%252Flist.tmall.com%252Fsearch_product.htm%253Ftbpm%253D3%2526spm%253Da220m.1000858.1000721.9.dOQCzn%2526cat%253D50025135%2526brand%253D109718%2526q%253D%2525CC%2525AB%2525C6%2525BD%2525C4%2525F1%2526sort%253Ds%2526style%253Dg%2526from%253Dsn_1_cat-qp%2526suggest%253D0_1%2526active%253D2%26smSign%3Da0IRdfJe%252FY7H8D5EzprPYQ%253D%253D");
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);  
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
//            conn.setRequestProperty("Referer","https://list.tmall.com/");
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null){
                    buffer.append(line);
                }
                String result = buffer.toString();
                //subscriber是观察者，在本代码中可以理解成发送数据给activity
//                subscriber.onNext(result);
            }
        }catch (Exception e){
//            subscriber.onError(e);
        }
	}

}
