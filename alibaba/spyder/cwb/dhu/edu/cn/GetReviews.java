package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author 曹文斌
 * @version V1.0
 * @Date 2016年10月
 * 
 */
public class GetReviews {
	
	
	static void printPageNumList(){
		for (String pageNum  : pageNumList) {//
			System.out.print(pageNum + " ");
		}
		System.out.println();
	}
	
	static String ItemId = "528445433975";
	
	static String SellerId = "2217893634"; 
	
	static int PageNumber = 0;
	
	static void cutHasDonePages(){
		for (String pageNum  : donePageNumList) {//
			pageNumList.remove(pageNum);
		}
		printPageNumList();
		donePageNumList.clear();
	}
	static List<String> pageNumList = new ArrayList<String>();
	static List<String> donePageNumList = new ArrayList<String>();

	public static void go() throws Exception{
		getReviewsPageNum1();
		
		FileWriter fw = null;
		fw = new FileWriter(Step3GetAllReviews.OutputFilePath + ItemId + ".dat"); // 命名规则 
		
		
		for (int i = 1; i <= PageNumber; i++) {//
			pageNumList.add(String.valueOf(i));	
		}
		
		while(pageNumList.size()!=0){
			
			for (String pageNum  : pageNumList) {//
				
				generateXMLFileByWordName_LEARNER(Integer.valueOf(pageNum), fw);
//				Thread t = Thread.currentThread();
//				t.sleep(6000);
			}
			cutHasDonePages();
		}
		

		fw.flush();
		fw.close();
	}


	@SuppressWarnings("rawtypes")
	public static void generateXMLFileByWordName_LEARNER(int pagenum, FileWriter fw)  {
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
//				System.out.println();

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
//								System.out.println("");
							}
//							System.out.println("发表评论时间：" + jons.get("rateDate") + "	交易结束（确认收货）时间：" + Utils.convert2String(Long.valueOf(jons.get("tradeEndTime").toString()),"") + "	" + jons.get("rateContent")+ "	服务评价：" + jons.get("serviceRateContent")+ "	" + jons.get("userVipLevel")+ "	" + jons.get("auctionSku")+ "	顾客天猫等级：" + coustomLever);
//							fw.write(jons.get("rateDate") + "	" + jons.get("rateContent")+ "	" + jons.get("userVipLevel") + "\n");
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
	
	
	 
	 
	
	 
	 public static void main(String[] args) {
//		  System.out.println(SimpleDateUtil.convert2long("2000-01-01 01:01:01",
//		    SimpleDateUtil.DATE_FORMAT));

		  System.out.println("enableTime" + Utils.convert2String(1481269799504l, Utils.TIME_FORMAT));
		  System.out.println("gmtCreateTime" + Utils.convert2String(1481269800000l, Utils.TIME_FORMAT));
//		  System.out.println("tradeEndTime" + convert2String(1480318216000l, TIME_FORMAT));
//		 String str = "{"api":{"descUrl":"//dsc.taobaocdn.com/i2/540/210/540216229552/TB1fl90OFXXXXbfXXXX8qtpFXlX.desc%7Cvar%5Edesc%3Bsign%5Ed88c5bfb5d6d3bc40d057d2d188a80ca%3Blang%5Egbk%3Bt%5E1481186715","fetchDcUrl":"//hdc1.alicdn.com/asyn.htm?pageId=1303938075&userId=134363478","httpsDescUrl":"//desc.alicdn.com/i2/540/210/540216229552/TB1fl90OFXXXXbfXXXX8qtpFXlX.desc%7Cvar%5Edesc%3Bsign%5Ed88c5bfb5d6d3bc40d057d2d188a80ca%3Blang%5Egbk%3Bt%5E1481186715"},"apiAddCart":"//cart.taobao.com/add_cart_item.htm?item_id=540216229552","apiBeans":"//count.taobao.com/counter3?keys=SM_368_dsr-134363478,ICCP_1_540216229552","apiBidCount":"//tbskip.taobao.com/json/show_bid_count.htm?itemNumId=540216229552&old_quantity=8378&date=1480685374000","apiItemViews":"//count.taobao.com/counter2?keys=ICVT_7_540216229552&inc=ICVT_7_540216229552&sign=857b4bffadd4aa7a3d57a3549820fad2b12","cartEnable":true,"changeLocationApi":"//mdskip.taobao.com/core/changeLocation.htm?tgTag=true&cartEnable=true&service3C=false&isSecKill=false&notAllowOriginPrice=false&offlineShop=false&isUseInventoryCenter=false&household=false&isRegionLevel=false&sellerUserTag=303108130&sellerUserTag4=576496079082750339&sellerUserTag3=144185694292574336&showShopProm=false&addressLevel=2&sellerUserTag2=18015687335215116&itemTags=587,907,1163,1227,1478,1483,1675,1803,2049,2059,2123,2187,2443,2507,2635,3019,3394,3851,3905,3974,4107,4166,4171,4363,4491,4550,4555,4614,4678,4811,5835,6401,6411,6603,7105,7371,7947,8641,9153,9665,10241,10818,11083,11329,11339,11531,11595,12491,12737,13121,13707,13771,15554,16321,17665,17793,17921,19841,20161,20609,21505,21697,21953,22081,22273,22337,24705,25282,27521,28353,28802,29697,29889,30273,30337,30401,30593,30657,30849,30977,31489,32577,34433,35458,35713,36161,36417,36929,37569,37633,39233,39745,41665,41922,43137,46849,46914,47169,48706,48898,49218,51329,51585,51841,51969,52033,53377,57026,57857,59010,59265,59329,60418,61249,62082,63042,80386,82306,101762,111490,112386,113602,116546,119426&isAreaSell=false&itemId=540216229552","cmCatId":"56516007","detail":{"addressLevel":2,"allowRate":true,"autoccUser":false,"canEditInItemDet":true,"cdn75":false,"defaultItemPrice":"699.00","double11StartTime":"","enableAliMedicalComponent":true,"globalSellItem":false,"goNewAuctionFlow":false,"is0YuanBuy":false,"isAliTelecomNew":false,"isAlicomMasterCard":false,"isAllowReport":true,"isAutoFinancing":false,"isAutoYushou":false,"isB2Byao":false,"isBundleItem":false,"isCarCascade":false,"isCarService":false,"isCarYuEBao":false,"isContractPhoneItem":false,"isCyclePurchase":false,"isDianZiMendian":false,"isDownShelf":false,"isEnableAppleSku":true,"isFullCarSell":false,"isH5NewLogin":true,"isHasPos":false,"isHasQualification":false,"isHiddenShopAction":false,"isHideAttentionBtn":false,"isHidePoi":false,"isHkDirectSale":false,"isHkItem":false,"isHkO2OItem":false,"isIFCShop":false,"isItemAllowSellerView":true,"isLadderGroupon":false,"isMeilihui":false,"isMemberShopItem":false,"isMenDianInventroy":false,"isNewAttraction":true,"isNewMedical":false,"isNextDayService":false,"isO2OStaging":false,"isOnePriceCar":false,"isOtcDrug":false,"isPreSellBrand":false,"isPrescriptionDrug":false,"isPurchaseMallVipBuyer":false,"isRegionLevel":false,"isSavingEnergy":false,"isShowContentModuleTitle":false,"isShowEcityBasicSign":false,"isShowEcityDesc":false,"isShowEcityVerticalSign":false,"isShowPreClosed":false,"isSkuColorShow":false,"isSkuMemorized":false,"isTeMai":false,"isVaccine":false,"isVitual3C":false,"loginBeforeCart":false,"mlhNewDesc":false,"pageType":"default","recommendBigMarkDownEndTime":"1477880000000","recommendBigMarkDownStartTime":"1478793600000","reviewListType":1,"show9sVideo":true,"showDiscountRecommend":false,"showFushiPoiInfo":true,"showSuperMarketBuy":false,"supermarketAndQianggou":false,"timeKillAuction":false,"tryReportDisable":false},"getProgressiveInfoApi":"//mdskip.taobao.com/core/getProgressiveInfo.do?platform_type=b2c&fromTryBeforeBuy=false&sellerId=134363478&platform=tmall&category=50011167&sellerPercent=3_100_1.60;6_0_4.50;9_0_6.00","idsMod":[],"initApi":"//mdskip.taobao.com/core/initItemDetail.htm?sellerPreview=false&isUseInventoryCenter=false&itemId=540216229552&tmallBuySupport=true&tryBeforeBuy=false&isRegionLevel=false&service3C=false&isAreaSell=false&isSecKill=false&cartEnable=true&cachedTimestamp=1481286011479&addressLevel=2&showShopProm=false&offlineShop=false&isApparel=true&queryMemberRight=true&isForbidBuyItem=false&isPurchaseMallPage=false&household=false","initCspuExtraApi":"//ext-mdskip.taobao.com/extension/initCspuExtra.htm","initExtensionApi":"//ext-mdskip.taobao.com/extension/initExtension.htm?brand=Meters+Bonwe%2F%C3%C0%CC%D8%CB%B9%B0%EE%CD%FE&categoryId=50011167&spuId=706263392&itemTags=587,907,1163,1227,1478,1483,1675,1803,2049,2059,2123,2187,2443,2507,2635,3019,3394,3851,3905,3974,4107,4166,4171,4363,4491,4550,4555,4614,4678,4811,5835,6401,6411,6603,7105,7371,7947,8641,9153,9665,10241,10818,11083,11329,11339,11531,11595,12491,12737,13121,13707,13771,15554,16321,17665,17793,17921,19841,20161,20609,21505,21697,21953,22081,22273,22337,24705,25282,27521,28353,28802,29697,29889,30273,30337,30401,30593,30657,30849,30977,31489,32577,34433,35458,35713,36161,36417,36929,37569,37633,39233,39745,41665,41922,43137,46849,46914,47169,48706,48898,49218,51329,51585,51841,51969,52033,53377,57026,57857,59010,59265,59329,60418,61249,62082,63042,80386,82306,101762,111490,112386,113602,116546,119426&showSpuMaintainer=false&sellerId=134363478&showBreadCrumb=true&showShopProm=false","initExtraApi":"//ext-mdskip.taobao.com/extension/initExtra.htm","isAliTripHK":false,"isAreaSell":false,"isDoubleElevenItem":true,"isHouseholdService":false,"isMeiz":false,"isMultiPoint":false,"isOnlyInMobile":false,"isService":false,"isSevenDaysRefundment":false,"isShowSizeHelper":false,"isShowSizeRecommend":false,"isTmallComboSupport":false,"isTripUser":false,"isWTContract":false,"itemDO":{"attachImgUrl":["//img.alicdn.com/imgextra/i3/134363478/TB2sv6LXF5N.eBjSZFGXXX50VXa_!!134363478.jpg","//img.alicdn.com/imgextra/i3/134363478/TB2GzfQXMCN.eBjSZFoXXXj0FXa_!!134363478.jpg","//img.alicdn.com/imgextra/i3/134363478/TB2S9hdb9iJ.eBjSspoXXcpMFXa_!!134363478.jpg"],"auctionStatus":"0","auctionType":"b","brand":"Meters Bonwe/&#32654;&#29305;&#26031;&#37030;&#23041;","brandId":"29504","brandSiteId":"0","brandSpecialSold":"false","categoryId":"50011167","cspuCategorySwitch":false,"encryptSellerId":"UvFv0vGxGMmc4","feature":"1","hasSku":true,"isBidden":false,"isCustomizedItem":false,"isDcAsyn":true,"isDefaultChooseTryBeforeBuy":false,"isElecCouponItem":false,"isEnterprisePath":false,"isInRepository":false,"isNewProGroup":true,"isOnline":true,"isSecondKillFromMobile":false,"isSecondKillFromPC":false,"isSecondKillFromPCAndWap":false,"isSupportTryBeforeBuy":false,"itemId":"540216229552","newProGroup":[{"attrs":[{"name":"适用场景","value":"其他休闲"},{"name":"适用对象","value":"青少年"}],"groupName":"其他"},{"attrs":[],"groupName":"工艺/流行"},{"attrs":[{"name":"上市年份季节","value":"2016年冬季"},{"name":"含绒量","value":"80%"},{"name":"材质成分","value":"聚酯纤维55% 棉36% 聚酰胺纤维(锦纶)9%"},{"name":"货号","value":"229102"},{"name":"销售渠道类型","value":"商场同款(线上线下都销售)"},{"name":"充绒量","value":"200g(含)-250g(不含)"},{"name":"品牌","value":"Meters Bonwe/美特斯邦威"},{"name":"厚薄","value":"常规"},{"name":"填充物","value":"灰鸭绒"},{"name":"基础风格","value":"青春流行"}],"groupName":"关键信息"},{"attrs":[{"name":"穿搭方式","value":"外穿"},{"name":"衣长","value":"常规"}],"groupName":"版型款式"}],"prov":"上海","quantity":2846,"reservePrice":"699.00","rootCatId":"30","sellProgressiveRate":"3_100_1.60;6_0_4.50;9_0_6.00","sellerNickName":"%E7%BE%8E%E7%89%B9%E6%96%AF%E9%82%A6%E5%A8%81%E5%AE%98%E6%96%B9%E7%BD%91%E5%BA%97","showCompanyPurchase":false,"spuId":"706263392","title":"美特斯邦威羽绒服男2016冬装新款229102专柜款李易峰同款情侣款","userId":"134363478","validatorUrl":"//store.taobao.com/tadget/shop_stats.htm","weight":"0"},"newSelectCityApi":"//mdskip.taobao.com/json/detailSelectCity.do?isAreaSell=false&itemId=540216229552","propertyPics":{";1627207:138213947;":["//img.alicdn.com/bao/uploaded/i4/134363478/TB2OxQ6bpOP.eBjSZFHXXXQnpXa_!!134363478.jpg"],";1627207:394470911;":["//img.alicdn.com/bao/uploaded/i2/134363478/TB2bVAua5GO.eBjSZFjXXcU9FXa_!!134363478.jpg"],";1627207:422768264;":["//img.alicdn.com/bao/uploaded/i3/134363478/TB2Vi_raMCN.eBjSZFoXXXj0FXa_!!134363478.jpg"],";1627207:461256361;":["//img.alicdn.com/bao/uploaded/i4/134363478/TB2B69QbmqJ.eBjy1zbXXbx8FXa_!!134363478.jpg"],";1627207:75426968;":["//img.alicdn.com/bao/uploaded/i2/134363478/TB2H3Awa4aK.eBjSZFAXXczFXXa_!!134363478.jpg"],"default":["//img.alicdn.com/bao/uploaded/i2/TB1iVIcNVXXXXbhaXXXXXXXXXXX_!!0-item_pic.jpg","//img.alicdn.com/bao/uploaded/i3/134363478/TB2d0iYcQ5M.eBjSZFrXXXPgVXa_!!134363478.jpg","//img.alicdn.com/bao/uploaded/i2/134363478/TB24YibalaM.eBjSZFMXXcypVXa_!!134363478.jpg","//img.alicdn.com/bao/uploaded/i4/134363478/TB2T_t6ak1M.eBjSZFOXXc0rFXa_!!134363478.jpg","//img.alicdn.com/bao/uploaded/i3/134363478/TB2cVOfahqK.eBjSZJiXXaOSFXa_!!134363478.jpg"]},"rateConfig":{"itemId":540216229552,"listType":1,"rateCloudDisable":false,"rateEnable":true,"rateNewChartDisable":false,"rateScoreCacheDisable":false,"rateScoreDisable":false,"rateSubjectDisable":false,"sellerId":134363478,"spuId":706263392,"tryReportDisable":false},"renderSystemServer":"//render.taobao.com","rstShopId":57301407,"selectCityApi":"//mdskip.taobao.com/core/selectCity.htm?isAreaSell=false&itemId=540216229552","selectRegionApi":"//mdskip.taobao.com/core/selectRegion.do?isAreaSell=false&itemId=540216229552","serviceIconList":[{"tagImage":"//img.alicdn.com/tps/i1/TB1RV6ZHXXXXXXIXFXXgBrbGpXX-36-36.png","tagLink":"#"}],"standingDate":0,"tag":{"isAsynDesc":true,"isBrandAttr":true,"isBrandSiteRightColumn":true,"isMedical":false,"isRightRecommend":true,"isShowEcityIcon":false,"isShowHouseIcon":false,"isShowMeiliXinde":false,"isShowTryReport":false,"isShowYuanchuanIcon":false},"tagsId":"587,907,1163,1227,1478,1483,1675,1803,2049,2059,2123,2187,2443,2507,2635,3019,3394,3851,3905,3974,4107,4166,4171,4363,4491,4550,4555,4614,4678,4811,5835,6401,6411,6603,7105,7371,7947,8641,9153,9665,10241,10818,11083,11329,11339,11531,11595,12491,12737,13121,13707,13771,15554,16321,17665,17793,17921,19841,20161,20609,21505,21697,21953,22081,22273,22337,24705,25282,27521,28353,28802,29697,29889,30273,30337,30401,30593,30657,30849,30977,31489,32577,34433,35458,35713,36161,36417,36929,37569,37633,39233,39745,41665,41922,43137,46849,46914,47169,48706,48898,49218,51329,51585,51841,51969,52033,53377,57026,57857,59010,59265,59329,60418,61249,62082,63042,80386,82306,101762,111490,112386,113602,116546,119426","tmallRateType":0,"tradeConfig":{"1":"//buy.taobao.com/auction/buy_now.jhtml","2":"//buy.tmall.com/order/confirm_order.htm","3":"//obuy.tmall.com/home/order/confirm_order.htm","4":"","5":"//buy.yao.95095.com/order/confirm_order.htm","7":"//tw.taobao.com/buy/auction/buy_now.jhtml"},"tradeParams":{},"tradeType":2,"url":{"BIDRedirectionitemDomain":"//paimai.taobao.com","buyBase":"//buy.taobao.com/auction","detailServer":"//detail.taobao.com","extMdskip":"//ext-mdskip.taobao.com","mallList":"//list.tmall.com","mdskip":"//mdskip.taobao.com","rate":"//rate.tmall.com","tbskip":"//tbskip.taobao.com","tgDetailDomain":"//detail.ju.taobao.com","tgDomain":"//ju.taobao.com","topUploadServerBaseUrl":"//upload.taobao.com","tradeBaseUrl":"//trade.taobao.com/trade","tradeForOldTmallBuy":"//stay.buy.tmall.com/order/confirm_order.htm","xCrossServer":"//mdetail.tmall.com"},"valCartInfo":{"cartUrl":"//cart.taobao.com/my_cart.htm?from=bdetail","itemId":"540216229552","statsUrl":"//go.mmstat.com/1.gif?logtype=2&category=cart_{loc}_50011167"},"valItemInfo":{"defSelected":[],"salesProp":{},"skuList":[{"names":"160/84A 檀香棕 ","pvs":"20509:6215318;1627207:422768264","skuId":"3202830226361"},{"names":"165/88A 檀香棕 ","pvs":"20509:3267942;1627207:422768264","skuId":"3202830226362"},{"names":"170/92A 檀香棕 ","pvs":"20509:3267943;1627207:422768264","skuId":"3202830226363"},{"names":"175/96A 檀香棕 ","pvs":"20509:3267944;1627207:422768264","skuId":"3202830226364"},{"names":"180/100A 檀香棕 ","pvs":"20509:3267945;1627207:422768264","skuId":"3202830226365"},{"names":"185/104B 檀香棕 ","pvs":"20509:3267951;1627207:422768264","skuId":"3202830226366"},{"names":"190/108B 檀香棕 ","pvs":"20509:381970941;1627207:422768264","skuId":"3202830226367"},{"names":"160/84A 松香绿 ","pvs":"20509:6215318;1627207:394470911","skuId":"3202830226368"},{"names":"165/88A 松香绿 ","pvs":"20509:3267942;1627207:394470911","skuId":"3202830226369"},{"names":"170/92A 松香绿 ","pvs":"20509:3267943;1627207:394470911","skuId":"3202830226370"},{"names":"175/96A 松香绿 ","pvs":"20509:3267944;1627207:394470911","skuId":"3202830226371"},{"names":"180/100A 松香绿 ","pvs":"20509:3267945;1627207:394470911","skuId":"3202830226372"},{"names":"185/104B 松香绿 ","pvs":"20509:3267951;1627207:394470911","skuId":"3202830226373"},{"names":"190/108B 松香绿 ","pvs":"20509:381970941;1627207:394470911","skuId":"3202830226374"},{"names":"160/84A 探戈红 ","pvs":"20509:6215318;1627207:461256361","skuId":"3202830226375"},{"names":"165/88A 探戈红 ","pvs":"20509:3267942;1627207:461256361","skuId":"3202830226376"},{"names":"170/92A 探戈红 ","pvs":"20509:3267943;1627207:461256361","skuId":"3202830226377"},{"names":"175/96A 探戈红 ","pvs":"20509:3267944;1627207:461256361","skuId":"3202830226378"},{"names":"180/100A 探戈红 ","pvs":"20509:3267945;1627207:461256361","skuId":"3202830226379"},{"names":"185/104B 探戈红 ","pvs":"20509:3267951;1627207:461256361","skuId":"3202830226380"},{"names":"190/108B 探戈红 ","pvs":"20509:381970941;1627207:461256361","skuId":"3202830226381"},{"names":"160/84A 海蓝黑 ","pvs":"20509:6215318;1627207:75426968","skuId":"3202830226382"},{"names":"165/88A 海蓝黑 ","pvs":"20509:3267942;1627207:75426968","skuId":"3202830226383"},{"names":"170/92A 海蓝黑 ","pvs":"20509:3267943;1627207:75426968","skuId":"3202830226384"},{"names":"175/96A 海蓝黑 ","pvs":"20509:3267944;1627207:75426968","skuId":"3202830226385"},{"names":"180/100A 海蓝黑 ","pvs":"20509:3267945;1627207:75426968","skuId":"3202830226386"},{"names":"185/104B 海蓝黑 ","pvs":"20509:3267951;1627207:75426968","skuId":"3202830226387"},{"names":"190/108B 海蓝黑 ","pvs":"20509:381970941;1627207:75426968","skuId":"3202830226388"},{"names":"160/84A 影黑 ","pvs":"20509:6215318;1627207:138213947","skuId":"3202830226389"},{"names":"165/88A 影黑 ","pvs":"20509:3267942;1627207:138213947","skuId":"3202830226390"},{"names":"170/92A 影黑 ","pvs":"20509:3267943;1627207:138213947","skuId":"3202830226391"},{"names":"175/96A 影黑 ","pvs":"20509:3267944;1627207:138213947","skuId":"3202830226392"},{"names":"180/100A 影黑 ","pvs":"20509:3267945;1627207:138213947","skuId":"3202830226393"},{"names":"185/104B 影黑 ","pvs":"20509:3267951;1627207:138213947","skuId":"3202830226394"},{"names":"190/108B 影黑 ","pvs":"20509:381970941;1627207:138213947","skuId":"3202830226395"}],"skuMap":{";20509:3267942;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226390","stock":87},";20509:3267942;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226369","stock":35},";20509:3267942;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226362","stock":15},";20509:3267942;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226376","stock":69},";20509:3267942;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226383","stock":64},";20509:3267943;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226391","stock":253},";20509:3267943;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226370","stock":169},";20509:3267943;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226363","stock":30},";20509:3267943;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226377","stock":265},";20509:3267943;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226384","stock":240},";20509:3267944;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226392","stock":263},";20509:3267944;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226371","stock":157},";20509:3267944;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226364","stock":16},";20509:3267944;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226378","stock":344},";20509:3267944;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226385","stock":265},";20509:3267945;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226393","stock":137},";20509:3267945;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226372","stock":46},";20509:3267945;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226365","stock":1},";20509:3267945;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226379","stock":179},";20509:3267945;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226386","stock":122},";20509:3267951;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226394","stock":21},";20509:3267951;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226373","stock":4},";20509:3267951;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226366","stock":0},";20509:3267951;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226380","stock":41},";20509:3267951;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226387","stock":22},";20509:381970941;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226395","stock":0},";20509:381970941;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226374","stock":0},";20509:381970941;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226367","stock":0},";20509:381970941;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226381","stock":0},";20509:381970941;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226388","stock":0},";20509:6215318;1627207:138213947;":{"price":"699.00","priceCent":69900,"skuId":"3202830226389","stock":1},";20509:6215318;1627207:394470911;":{"price":"699.00","priceCent":69900,"skuId":"3202830226368","stock":0},";20509:6215318;1627207:422768264;":{"price":"699.00","priceCent":69900,"skuId":"3202830226361","stock":0},";20509:6215318;1627207:461256361;":{"price":"699.00","priceCent":69900,"skuId":"3202830226375","stock":0},";20509:6215318;1627207:75426968;":{"price":"699.00","priceCent":69900,"skuId":"3202830226382","stock":0}}},"valLoginIndicator":"//buy.taobao.com/auction/buy.htm?from=itemDetail&id=540216229552","valMode":128,"valPointRate":0.5,"valPointTimes":1,"valTimeLeft":4162}";
//		  System.out.println(StringEscapeUtils.unescapeHtml4("通勤:&nbsp;&#38889;&#29256;"));
		 }
	 
	 /**
	  * 获取当前系统的日期
	  * 
	  * @return
	  */
	 public static long curTimeMillis() {
	  return System.currentTimeMillis();
	 }

	 

	 
	 
	 
	 public static void getReviewsPageNum1() throws Exception {
			GetItemDetails.go();
			
			BufferedReader in = null;

			URL realUrl = new URL("https://rate.tmall.com/list_detail_rate.htm?itemId=" + GetReviews.ItemId + "&sellerId="
					+ GetReviews.SellerId + "&currentPage=1");
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.connect();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			Integer pageNum = 0;
			Pattern pattern = Pattern.compile(getReviewsPageNum1_regEx);
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				Matcher matcher = pattern.matcher(line);
				boolean rs = matcher.find();
				if (rs) {
					pageNum = Integer.valueOf(GetItemDetails.getId(matcher.group()));
				}
			}
			System.out.println("page num: " + pageNum);
			if(Integer.valueOf(pageNum)==0){ //TODO 
				 System.err.println("再来一遍");
				 getReviewsPageNum1();
				
			} 
			GetReviews.PageNumber = pageNum;

		}

		static String getReviewsPageNum1_regEx = "\"lastPage\":\\d+";


}
