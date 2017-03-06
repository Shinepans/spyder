package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Step2DelDuplicate {

	static String urlSourceFilePath = "E:\\workspace\\Spyder\\data\\source.xlsx";
	final static int colNum = 0; // TODO

	public static void main(String[] args) throws IOException {

		Workbook workbook = UtilsExcel.getWeebWork(urlSourceFilePath);
		Sheet sheet = workbook.getSheetAt(0);
		int rownum = sheet.getLastRowNum();// 获取总行数
		for (int i = 1; i <= rownum; i++) {// 第一行为表头
			Row row = sheet.getRow(i);
			String id = row.getCell(colNum).toString();

			for (int j = i + 1; j <= rownum; j++) {
				Row rowj = sheet.getRow(j);
				String idj = rowj.getCell(colNum).toString();
				if (id != null && idj != null && id.trim().equals(idj.trim())) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						cell.setCellValue("");
					}

				}
			}
		}
		
		OutputStream os = null;
		try{
		os = new FileOutputStream(new File(urlSourceFilePath));
		workbook.write(os);
		}catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                workbook.close();
            }
        }

	}

}
