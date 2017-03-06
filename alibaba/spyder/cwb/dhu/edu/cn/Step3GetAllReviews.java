package alibaba.spyder.cwb.dhu.edu.cn;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Step3GetAllReviews {

	static String OutputFilePath = "";
	
	static String itemURL = "";
	
	public static void main(String[] args) throws Exception {
		go();
	}
	
	public static void go() throws Exception {
		
		//-------------------------------------excel数据源		
		Workbook workbook = UtilsExcel.getWeebWork(Step0InputInterface.urlSourceFilePath);
//		System.out.println("总表页数为：" + workbook.getNumberOfSheets());// 获取表页数
		Sheet sheet = workbook.getSheetAt(0);
		// Sheet sheet = workbook.getSheetAt(1);
		int rownum = sheet.getLastRowNum();// 获取总行数
		for (int i = 0; i <= rownum; i++) {//第一行为表头 i=130 //TODO <<< 根据日志获取i的初始值
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
			
//			OutputFilePath = InputInterface.OutputFilePathTRUE + brand + "_" + category + "_" + id + "_";
			OutputFilePath = Step0InputInterface.OutputFilePathTRUE + id + "_" + productName+ "_";
			GetReviews.go();
			Utils.appandLog("第" + i+"行URL已读取评论完毕");
		}
	}
}
