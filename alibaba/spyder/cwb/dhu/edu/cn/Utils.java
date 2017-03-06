package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Utils {

	static  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	/**
	 * 输出商业有用信息(有用即可)
	 * 
	 * @param info
	 *            有用信息
	 */
	static void appandInfo(String info){
		 try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter("./information.dat", true);
            writer.write(df.format(new Date())+ "	"+info +"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	static void appandLog(String log){
		 try {
             //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter("./log.dat", true);
             writer.write(df.format(new Date())+ "	"+log +"\n");// new Date()为获取当前系统时间
             writer.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
	}
	
	static void appandErrorLog(String log){
		 try {
          FileWriter writer = new FileWriter("./errorlog.dat", true);
          writer.write(Utils.df.format(new Date())+ "	"+log +"\n");// new Date()为获取当前系统时间
           writer.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
	}
	
	/**
	 * 获取指定HTML标签的指定属性的值
	 * 
	 * @param source
	 *            要匹配的源文本
	 * @param element
	 *            标签名称
	 * @param attr
	 *            标签的属性名称
	 * @return 属性值列表
	 */
	public static List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}
	
	 /**
	  * 将长整型数字转换为日期格式的字符串
	  * 
	  * @param time
	  * @param format
	  * @return
	  */
	 public static String convert2String(long time, String format) {
	  if (time > 0l) {
	   if (StringUtils.isBlank(format))
	    format = TIME_FORMAT;

	   SimpleDateFormat sf = new SimpleDateFormat(format);
	   Date date = new Date(time);

	   return sf.format(date);
	  }
	  return "";
	 }
	 
	 		// 长日期格式
			 public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		
		 /**
		  * 将日期格式的字符串转换为长整型
		  * 
		  * @param date
		  * @param format
		  * @return
		  */
		 public static long convert2long(String date, String format) {
		  try {
		   if (StringUtils.isNotBlank(date)) {
		    if (StringUtils.isBlank(format))
		     format = TIME_FORMAT;

		    SimpleDateFormat sf = new SimpleDateFormat(format);
		    return sf.parse(date).getTime();
		   }
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return 0l;
		 }

		 public static void setCharacterType(URLConnection conn, String type){
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Accept-Charset", type);
			conn.setRequestProperty("contentType", type);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset="+type);
			conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
		 }

}
