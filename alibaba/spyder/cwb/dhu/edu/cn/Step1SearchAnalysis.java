package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Step1SearchAnalysis {

	static String contentReg = "\\d+\\.\\d+";
	static String yuechengjiao = "\\d+[\\.]?\\d*\\s*[万]?\\s*笔";
	static String pingjiashuliang = ">\\s*\\d+\\s*<";// >1703<
	static String productTitleReg = "href=\"//[^\"]+\"";
	static String pageNumReg = "1\\s*/\\s*\\d+";
	
	public static void main(String[] args) throws Exception {
		for (Entry<String, String> entry : Step0InputInterface.fileURLMap.entrySet()) {
			String fileName = entry.getKey();
			String urlStr = entry.getValue();
			// System.out.println(fileName + " " + urlStr);
			FileOutputStream outStream = new FileOutputStream("./" + fileName);/*./指项目根目录*/
			Workbook workbook = new SXSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			int counter = 1;
			int pageSize = getPageSize(getSearchURL(0, urlStr));Utils.appandLog(fileName+" 一共"+pageSize+"页");			
			for (int k = 0; k < pageSize; k++) {
				BufferedReader in = null;
				URL realUrl = new URL(getSearchURL(k * 60, urlStr));
				URLConnection conn = realUrl.openConnection();
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("Accept-Charset", "GBK");
				conn.setRequestProperty("contentType", "GBK");
				conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=GBK");
				conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
				conn.connect();

				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));// 
				String line;
				int i = 0;

				String price = "";
				String productName = "";
				String productURL = "";
				String storeName = "";
				String storeURL = "";
				String ycjsl = "";// 月成交数量
				String pjsl = "";// 评价数量
				while ((line = in.readLine()) != null) {
					if (line.contains("class=\"productPrice\"")) {
						i = 1;
					} else if (line.contains("class=\"productTitle\"")) {
						i = 2;
					} else if (line.contains("class=\"productShop\"")) {
						i = 3;
					} else if (line.contains("class=\"productStatus\"")) {
						i = 4;
					}

					switch (i) {
					case 1:
						if (line.contains("<em title=\"")) {
							String str = line.substring(line.indexOf("<em title=\"") + 11);
							Pattern itemPattern = Pattern.compile(contentReg);
							Matcher itemMatcher = itemPattern.matcher(str);
							boolean itemRs = itemMatcher.find();
							if (itemRs) {
								price = itemMatcher.group(0);
								System.out.print("	商品价格：" + price);
							}
						}
						break;
					case 2:
						if (line.startsWith("<a")) {
							productURL = Utils.match(line, "a", "href").get(0);
							productName = removeTage(in.readLine());
							System.out.print("	商品名称：" + productName);
						}
						break;
					case 3:
						if (line.contains("class=\"productShop-name\"")) {
							storeURL = Utils.match(line, "a", "href").get(0);
						}
						if (line.contains("店")) {
							storeName = removeTage(line);
							System.out.print("	店铺名称：" + storeName);
						}
						break;
					case 4:
						if (line.contains("月成交")) {
							Pattern itemPattern = Pattern.compile(yuechengjiao);
							Matcher itemMatcher = itemPattern.matcher(line);
							boolean itemRs = itemMatcher.find();
							if (itemRs) {
								ycjsl = itemMatcher.group(0);
							} else {
								ycjsl = "0";
							}
							System.out.print("	月成交：" + ycjsl);
						}

						if (line.contains("评价")) {
							Pattern itemPattern = Pattern.compile(pingjiashuliang);
							Matcher itemMatcher = itemPattern.matcher(line);
							boolean itemRs = itemMatcher.find();
							if (itemRs) {
								pjsl = itemMatcher.group(0).replaceAll(">", "").replaceAll("<", "");
								System.out.println("	评价数量：" + pjsl);
							}

							Row dataRow = sheet.createRow(counter);
							dataRow.createCell(0).setCellValue(productName);
							dataRow.createCell(1).setCellValue(storeName);
							dataRow.createCell(2).setCellValue(price);
							dataRow.createCell(3).setCellValue(ycjsl);
							dataRow.createCell(4).setCellValue(pjsl);
							dataRow.createCell(5).setCellValue(productURL);
							dataRow.createCell(6).setCellValue(storeURL);
							dataRow.createCell(7).setCellValue(counter);//本次搜索结果中的排名
							counter++;
							dataRow.createCell(6).setCellValue("");//商品编号 TODO
							((SXSSFSheet) sheet).flushRows();
						}
						break;
					default:
						break;
					}
				}
			}
			workbook.write(outStream);
			workbook.close();
			Utils.appandLog(fileName + "  一共 " + (counter - 1) + "件商品");
		}
	}

	public static int getPageSize(String url) throws IOException {
		int pageNum = 0;
		BufferedReader in = null;
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("Accept-Charset", "GBK");
		conn.setRequestProperty("contentType", "GBK");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=GBK");
		conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
		 conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
//		 conn.setRequestMethod("GET");
         conn.setUseCaches(false);

		conn.connect();

		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
																						
		String line;
		int i = 0;

		while ((line = in.readLine()) != null) {

			if (line.contains("ui-page-s-len")) {
				i++;
				Pattern itemPattern = Pattern.compile(pageNumReg);
				Matcher itemMatcher = itemPattern.matcher(line);
				boolean itemRs = itemMatcher.find();
				if (itemRs) {
					pageNum = Integer.valueOf(itemMatcher.group(0).split("/")[1].trim()).intValue();
				}
			}
		}
		if (i == 1) {
			return pageNum;
		} else {
			throw new IOException();
		}
	}

	public static String removeTage(String source) {
		source = source.replaceAll("<[^<]*?>", "").replaceAll("\\.", "").trim();
		return source;
	}

	public static String getSearchURL(int size, String url) {
		return url.replace("http:::", String.valueOf(size));
	}
}
