package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetItemDetails {

	public static void main(String[] args) throws Exception {
		go();
	}

	static String regEx = "sellerId:\"\\d+\"";
	static String itemRegEx = "itemId:\"\\d+\"";
	static String regEx2 = "sellerId=\\d+&";
	static String itemRegEx2 = "itemId=\\d+&";
	static Pattern patternCommon = Pattern.compile("\\d+");

	public static String getId(String idSource) {
		Matcher m1 = patternCommon.matcher(idSource);
		if (m1.find())
			return m1.group();
		return "";
	}

	public static void go() throws JSONException {

		BufferedReader in = null;
		try{
		URL realUrl = new URL(Step3GetAllReviews.itemURL);
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
				GetReviews.ItemId = itemId;
				GetReviews.SellerId = sellerId;
			} else if (line.contains("J_AttrUL")){
				while ((line = in.readLine()) != null) {
					if(line.contains("</ul>"))
						break;
					String para = line.replaceAll("<li[^>]*?>", "").replaceAll("</li>", "").replace("&nbsp;", "");
					String result = StringEscapeUtils.unescapeHtml3(para).trim();
//					if(!result.isEmpty() && result.contains(":"))
//						System.out.println(result);
				}
			} else if(line.contains("TShop.Setup(")){
				String jsonStr = StringEscapeUtils.unescapeHtml3(line = in.readLine());
//				System.out.println(jsonStr);
				JSONObject jsonObj = new JSONObject(jsonStr);
				if(jsonObj.has("itemDO")){
					JSONObject itemDO =  new JSONObject(jsonObj.get("itemDO").toString());
					if(itemDO.has("newProGroup")){//TODO 有些商品没有
						JSONArray newProGrouparray = new JSONArray(itemDO.get("newProGroup").toString());
						for (int i = 0; i < newProGrouparray.length(); i++) {
							JSONObject jons = newProGrouparray.getJSONObject(i);
//							System.err.println(jons.get("groupName"));
							JSONArray attrsArray = new JSONArray(jons.get("attrs").toString());
							for (int attrsI = 0; attrsI < attrsArray.length(); attrsI++) {
								JSONObject attrsJons = attrsArray.getJSONObject(attrsI);
//								System.out.print(attrsJons.get("name") + "	");
//								System.out.println(attrsJons.get("value") + "	");
							}
						}
//						System.out.println(newProGrouparray);
						
					}
				}
				
				if(jsonObj.has("valItemInfo")){
					Object valItemInfo = jsonObj.get("valItemInfo");
//					System.out.println(valItemInfo);
					JSONObject skuList = new JSONObject(valItemInfo.toString());//).getJSONObject("skuList");
					JSONArray skuListarray = new JSONArray(skuList.get("skuList").toString());
//					System.out.println(skuListarray);
				}
			}
		}

	}catch(IOException e){
		go();
		}
	}
}
