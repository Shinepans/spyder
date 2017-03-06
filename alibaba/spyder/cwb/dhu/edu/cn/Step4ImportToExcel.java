package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Step4ImportToExcel {
	
	static String excelPath = "E:\\workspace\\Spyder\\data\\特步男鞋.xlsx";

	public static void main(String[] args) throws Exception {
		FileOutputStream outStream = new FileOutputStream(excelPath);
		Workbook workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		
		String genotypePath ="E:\\workspace\\Spyder\\data\\评论数据库\\特步男鞋（10个商品，19800条评论）\\";
		 File root = new File(genotypePath);
		    File[] files = root.listFiles();
		    FileReader genotypeFr = null;
    		BufferedReader genotypeBr = null;
    		int j = 0;
		    for(File file:files){     
		        if(! file.isDirectory()){
		    		genotypeFr = new FileReader(file.getAbsolutePath());
		    		genotypeBr = new BufferedReader(genotypeFr);
		    		int i =0;
		    		String line ="";
		    		while ((line=genotypeBr.readLine()) != null) {
		    			i++;
		    			String review = line.substring(19);
		    			String date = line.substring(0, 19);
//		    			System.out.println(date + " " + review);
		    			Row dataRow = sheet.createRow(j+i);
		    			dataRow.createCell(0).setCellValue(review);
//		    			fudannlp.nlp.cwb.dhu.edu.cn.GetKeywords.GetKeyword(review,102);
		    			dataRow.createCell(1).setCellValue(date);
		    		}
		    		j+=i;
//		    		System.out.println(file.getName() + "  " +  i + "条评论");
		    		genotypeBr.close();
		    		genotypeFr.close();
		    		((SXSSFSheet) sheet).flushRows();
		        }
		        }
			workbook.write(outStream);
			workbook.close();
		    System.out.println("一共 " + j + "条评论");
		
	}

}
