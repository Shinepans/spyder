package alibaba.spyder.cwb.dhu.edu.cn;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 曹文斌
 * @version V1.0
 * @Date 2016年10月
 * 
 */
public class Step0InputInterface {
	
	/**
	 * 值得深入细致的地方： GetItemDetails中获得更多的商品信息
	 * 				GetRateList	       中获得更多的评论信息
	 * 				Json获取深层数据       获取数组里面数据
	 * */
		/**
		 *  "UTF-8";
		 * */
		static String charsetType="GBK";//天猫平台中文编码方式是GBK
	
		static Map<String, String> fileURLMap = new HashMap<String, String>();
		static {// TODO http:::
			fileURLMap.put("HSTYLS_品牌女装_连衣裙2.xlsx",
					"https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000721.18.SgK37v&cat=50025145&brand=8598007&s=http:::&q=%BA%AB%B6%BC%D2%C2%C9%E1&sort=s&style=g&from=sn_1_cat-qp&industryCatId=50025135#J_crumbs");
			fileURLMap.put("HSTYLS_品牌女装_毛衣.xlsx",
					"https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000721.9.jaZjSC&cat=50025784&brand=8598007&s=http:::&q=%BA%AB%B6%BC%D2%C2%C9%E1&sort=s&style=g&from=sn_1_cat-qp&industryCatId=50025135#J_crumbs");
			fileURLMap.put("HSTYLS_HSTYLS_品牌女装_针织衫.xlsx",
					"https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000721.5.bI2tIL&cat=50025783&brand=8598007&s=http:::&q=%BA%AB%B6%BC%D2%C2%C9%E1&sort=s&style=g&from=sn_1_cat-qp&industryCatId=50025135#J_crumbs");
			fileURLMap.put("HSTYLS_品牌女装_卫衣绒衫.xlsx",
					"https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000721.1.iSzfhl&cat=50025788&brand=8598007&s=http:::&q=%BA%AB%B6%BC%D2%C2%C9%E1&sort=s&style=g&from=sn_1_cat-qp&industryCatId=50025135#J_crumbs");
		}
		
		static String urlSourceFilePath = "./data\\HSTYLS_source.xlsx";
		static String OutputFilePathTRUE = "./data\\HSTYLSTest\\"; 
		
		static Boolean afterSearchGetUrls = false; // 通过搜索引擎获得URL后是否进一步获得评论。即Step1-Step2
		static Boolean afterStep2 = false; // 通过搜索引擎获得URL后是否进一步获得评论。即Step2-Step3
		static Boolean afterStep3 = true; // 通过搜索引擎获得URL后是否进一步获得评论。即Step2-Step4
		
}

