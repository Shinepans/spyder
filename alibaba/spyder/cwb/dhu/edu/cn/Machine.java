package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Machine extends Thread  {

	private int start;
	private int end;
	private Sheet sheet;
	
	

	private String ItemId = "";
	private String SellerId = ""; 
	private int PageNumber = 0;
	
	private String OutputFilePath = "";
	private String itemURL = "";
	
	private List<String> pageNumList = new ArrayList<String>();
	private List<String> donePageNumList = new ArrayList<String>();
	
	private final String getReviewsPageNum1_regEx = "\"lastPage\":\\d+";
	private final String regEx = "sellerId:\"\\d+\"";
	private final String itemRegEx = "itemId:\"\\d+\"";
	private final String regEx2 = "sellerId=\\d+&";
	private final String itemRegEx2 = "itemId=\\d+&";
	private  Pattern patternCommon = Pattern.compile("\\d+");
	
	public Machine(int start,int end, Sheet sheet) {
		this.start = start;
		this.end = end;
		this.sheet = sheet;
	}
	
	public  void execute() {
		try{
		for (int i = start; i < end; i++) {
			Row row = sheet.getRow(i);
			String id = i+"";//row.getCell(0).toString();
//			String brand = row.getCell(1).toString();
//			String category = row.getCell(2).toString();
			String url = row.getCell(5).toString();
			String commentNum = row.getCell(4).toString();
			if(Integer.valueOf(commentNum)==0)
				continue;
			String productName = row.getCell(0).toString();
			url = url.trim();
			if(!url.startsWith("https:")){
				itemURL = "https:" + url;
			} else{
				itemURL = url;
			}
			OutputFilePath = Step0InputInterface.OutputFilePathTRUE + id + "_" + productName+ "_";
			GetReviewsGo();
			Utils.appandLog("第" + i+"行URL已读取评论完毕");
		}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally{
			
		}
	}
	
	
	private void GetReviewsGo() {
		try{
		getReviewsPageNum1();
		FileWriter fw = null;
		String fileName = OutputFilePath + ItemId + ".dat";
		fw = new FileWriter(fileName); // 命名规则 
		for (int i = 1; i <= PageNumber; i++) {//
			pageNumList.add(String.valueOf(i));	
		}
		
		while(pageNumList.size()!=0){
			for (String pageNum  : pageNumList) {//
				generateXMLFileByWordName_LEARNER(Integer.valueOf(pageNum), fw);
			}
			cutHasDonePages();
		}
		fw.flush();
		fw.close();
		
		File f = new File(fileName);
		if (f.exists() && f.isFile() && f.length()==0){  //如果执行完結果為O，則从新再执行一遍
			GetReviewsGo();
		}
	}catch(Exception e){
		e.printStackTrace();
		System.err.println(e.getMessage());
	} finally{
		
	}
	}
	
	@SuppressWarnings("rawtypes")
	private void generateXMLFileByWordName_LEARNER(int pagenum, FileWriter fw)  {
		BufferedReader in = null;
		
		try{
//			System.out.println("第" + pagenum + "页");
		
		URL realUrl = new URL(
				"https://rate.tmall.com/list_detail_rate.htm?itemId="+ ItemId+"&sellerId="+SellerId+"&currentPage="
						+ pagenum);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		conn.connect();

		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {

			if (line.contains("\"rateList\":")) {
//				pageNumList.remove(String.valueOf(pagenum));
//				printPageNumList();
				donePageNumList.add(String.valueOf(pagenum));
				line = line.substring(line.indexOf("\"rateList\":"));
				line = line.substring(0, line.indexOf(",\"searchinfo\":"));
				line = "{" + line + "}";

				String rateListStr = (String) UtilsJsonHelper.toMap(line).get("rateList");
				System.out.println();

				JSONObject jsonObj = new JSONObject("{a:" + rateListStr + "}");

				Iterator it = jsonObj.keys();
				for (; it.hasNext();) {
					String str = it.next().toString();

					JSONArray array = new JSONArray(jsonObj.get(str).toString());
					for (int i = 0; i < array.length(); i++) {
						JSONObject jons = array.getJSONObject(i);
						if(jons.get("rateContent")!=null && jons.get("rateContent").toString().contains("<b>")){
							System.err.println(jons.get("rateDate") + "	" + jons.get("rateContent"));
						} else{
							String attributes = "";
							if(jons.get("attributes") != null){
								attributes = jons.get("attributes").toString();
							}
							
							String coustomLever = "-1";
							if(attributes.contains("tmall_vip_level")){
								coustomLever = attributes.substring(attributes.indexOf("tmall_vip_level") +16, attributes.indexOf("tmall_vip_level") +17);
								coustomLever = coustomLever.replace(",", "");
							} else{
								System.out.println("");
							}
//							System.out.println("发表评论时间：" + jons.get("rateDate") + "	交易结束（确认收货）时间：" + Utils.convert2String(Long.valueOf(jons.get("tradeEndTime").toString()),"") + "	" + jons.get("rateContent")+ "	服务评价：" + jons.get("serviceRateContent")+ "	" + jons.get("userVipLevel")+ "	" + jons.get("auctionSku")+ "	顾客天猫等级：" + coustomLever);
							fw.write("发表评论时间：" + jons.get("rateDate") + "	交易结束（确认收货）时间：" + Utils.convert2String(Long.valueOf(jons.get("tradeEndTime").toString()),"") + "	" + jons.get("rateContent")+ "	服务评价：" + jons.get("serviceRateContent")+ "	" + jons.get("userVipLevel")+ "	" + jons.get("auctionSku")+ "	顾客天猫等级：" + coustomLever + "\n");
						}
					}
				}
			}

		} 
		
			} catch(Exception e){
				System.err.println("错误原因:" + e);
				System.err.println("close connection:" + pagenum);
				generateXMLFileByWordName_LEARNER(pagenum, fw);//容错机制
			
		} finally{
			
		}

	}
	
	private  void getReviewsPageNum1() {
		try{
		GetItemDetailsGo();
		
		BufferedReader in = null;

		URL realUrl = new URL("https://rate.tmall.com/list_detail_rate.htm?itemId=" + ItemId + "&sellerId="
				+ SellerId + "&currentPage=1");
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		conn.connect();

		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		Integer pageNum = 0;
		Pattern pattern = Pattern.compile(getReviewsPageNum1_regEx);
		while ((line = in.readLine()) != null) {
//			System.out.println(line);
			Matcher matcher = pattern.matcher(line);
			boolean rs = matcher.find();
			if (rs) {
				pageNum = Integer.valueOf(getId(matcher.group()));
			}
		}
//		System.out.println("page num: " + pageNum);
		if(Integer.valueOf(pageNum)==0){ //TODO 
			 System.err.println("再来一遍");
			 getReviewsPageNum1();
		} 
		PageNumber = pageNum;
		
	}catch(Exception e){
		e.printStackTrace();
		System.err.println(e.getMessage());
	} finally{
		
	}
	}
	
	private void GetItemDetailsGo()  {
		try{

		BufferedReader in = null;
		try{
		URL realUrl = new URL(itemURL);
		URLConnection conn = realUrl.openConnection();
		conn.connect();

		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		String itemId = "";
		String sellerId = "";
		while ((line = in.readLine()) != null) {
//			System.out.println(line);
			if (line.contains("sellerId") && line.contains("itemId")) {
				// 编译正则表达式
				Pattern pattern = Pattern.compile(regEx);
				Pattern itemPattern = Pattern.compile(itemRegEx);
				Matcher matcher = pattern.matcher(line);
				Matcher itemMatcher = itemPattern.matcher(line);
				// 查找字符串中是否有匹配正则表达式的字符/字符串
				boolean rs = matcher.find();
				boolean itemRs = itemMatcher.find();

				if (rs) {
					sellerId = getId(matcher.group());
				}
				if (itemRs) {
					itemId = getId(itemMatcher.group());
				}

				Pattern pattern2 = Pattern.compile(regEx2);
				Pattern itemPattern2 = Pattern.compile(itemRegEx2);
				Matcher matcher2 = pattern2.matcher(line);
				Matcher itemMatcher2 = itemPattern2.matcher(line);
				while (matcher2.find()) {
					String sellerIdTemp = getId(matcher2.group());
					if (sellerId.isEmpty()) {
						sellerId = sellerIdTemp;
					} else {
						if (!sellerId.equals(sellerIdTemp)) {
							try {
								throw new Exception();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				while (itemMatcher2.find()) {
					String itemIdTemp = getId(itemMatcher2.group());
					if (itemId.isEmpty()) {
						itemId = itemIdTemp;
					} else {
						if (!itemId.equals(itemIdTemp)) {
							try {
								throw new Exception();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

				// System.out.println("get ids: " + itemId + " " + sellerId);
				ItemId = itemId;
				SellerId = sellerId;
			} else if (line.contains("J_AttrUL")){
				while ((line = in.readLine()) != null) {
					if(line.contains("</ul>"))
						break;
					String para = line.replaceAll("<li[^>]*?>", "").replaceAll("</li>", "").replace("&nbsp;", "");
					String result = StringEscapeUtils.unescapeHtml3(para).trim();
					if(!result.isEmpty() && result.contains(":"))
						System.out.println(result);
				}
			} else if(line.contains("TShop.Setup(")){
				String jsonStr = StringEscapeUtils.unescapeHtml3(line = in.readLine());
				System.out.println(jsonStr);
				JSONObject jsonObj = new JSONObject(jsonStr);
				if(jsonObj.has("itemDO")){
					JSONObject itemDO =  new JSONObject(jsonObj.get("itemDO").toString());
					if(itemDO.has("newProGroup")){//TODO 有些商品没有
						JSONArray newProGrouparray = new JSONArray(itemDO.get("newProGroup").toString());
						for (int i = 0; i < newProGrouparray.length(); i++) {
							JSONObject jons = newProGrouparray.getJSONObject(i);
							System.err.println(jons.get("groupName"));
							JSONArray attrsArray = new JSONArray(jons.get("attrs").toString());
							for (int attrsI = 0; attrsI < attrsArray.length(); attrsI++) {
								JSONObject attrsJons = attrsArray.getJSONObject(attrsI);
								System.out.print(attrsJons.get("name") + "	");
								System.out.println(attrsJons.get("value") + "	");
							}
						}
						System.out.println(newProGrouparray);
						
					}
				}
				
				if(jsonObj.has("valItemInfo")){
					Object valItemInfo = jsonObj.get("valItemInfo");
					System.out.println(valItemInfo);
					JSONObject skuList = new JSONObject(valItemInfo.toString());//).getJSONObject("skuList");
					JSONArray skuListarray = new JSONArray(skuList.get("skuList").toString());
					System.out.println(skuListarray);
				}
			}
		}

	}catch(IOException e){
		GetItemDetailsGo();
		}
		
		
	}catch(JSONException e){
		e.printStackTrace();
		System.err.println(e.getMessage());
	} finally{
		
	}
	}
	
	

	private String getId(String idSource) {
		Matcher m1 = patternCommon.matcher(idSource);
		if (m1.find())
			return m1.group();
		return "";
	}
	private void cutHasDonePages(){
		for (String pageNum  : donePageNumList) {//
			pageNumList.remove(pageNum);
		}
		printPageNumList();
		donePageNumList.clear();
	}
	
	private void printPageNumList(){
		for (String pageNum  : pageNumList) {//
			System.out.print(pageNum + " ");
		}
		System.out.println();
	}
	
	
	public String getOutputFilePath() {
		return OutputFilePath;
	}
	public void setOutputFilePath(String outputFilePath) {
		OutputFilePath = outputFilePath;
	}
	public String getItemURL() {
		return itemURL;
	}
	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}
	public List<String> getPageNumList() {
		return pageNumList;
	}
	public void setPageNumList(List<String> pageNumList) {
		this.pageNumList = pageNumList;
	}
	public List<String> getDonePageNumList() {
		return donePageNumList;
	}
	public void setDonePageNumList(List<String> donePageNumList) {
		this.donePageNumList = donePageNumList;
	}

	
	
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public String getSellerId() {
		return SellerId;
	}
	public void setSellerId(String sellerId) {
		SellerId = sellerId;
	}
	public int getPageNumber() {
		return PageNumber;
	}
	public void setPageNumber(int pageNumber) {
		PageNumber = pageNumber;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	@Override
	public void run() {
		System.err.println(start + "---------------------------------启动");
//		execute();
		try {
			Thread.sleep(500);//1秒=1000毫秒=1000000微秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(start + "---------------------------------结束");
		
	}
	
}
