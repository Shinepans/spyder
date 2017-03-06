package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetProdutUrlsFromFile {
	static String regEx = "href=\"//detail.tmall.com/item.htm?id=";

	public static void main(String[] args) throws IOException {
// String url ="https://adidas.tmall.com/?spm=a1z10.1-b-s.w11827627-14889047702.3.1bJYhv&search=y&keyword=%C5%DC%B2%BD%D0%AC";
		String genotypePath = "E:\\workspace\\Spyder\\data\\html\\阿迪达斯-Adidas-阿迪达斯-天猫Tmall.com-上天猫，就够了.htm";
		FileReader genotypeFr = new FileReader(genotypePath);
		BufferedReader genotypeBr = new BufferedReader(genotypeFr);
		String line = "";
		Pattern pattern2 = Pattern.compile(regEx);
		
//		
//		BufferedReader in = null;
//		URL realUrl = new URL(genotypePath);
//		URLConnection conn = realUrl.openConnection();
//		conn.connect();
//		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		
		while ((line = genotypeBr.readLine()) != null) {
			System.out.println(line);
			Matcher matcher2 = pattern2.matcher(line);
			while (matcher2.find()) {
				String sellerIdTemp = matcher2.group();
				System.out.println(sellerIdTemp);
				
			}
		}
		
		genotypeBr.close();
		genotypeFr.close();
	}
}



//-------------------------------------txt数据源
/*FileReader phenotypeFr = null;
BufferedReader phenotypeBr = null;
try {
	phenotypeFr = new FileReader(urlSourceFilePath);
	phenotypeBr = new BufferedReader(phenotypeFr);
	
	String line ="";
	while ((line = phenotypeBr.readLine()) != null) {
		System.err.println(line);
		String[] multi_phenos = line.split("\\s+");
		if(multi_phenos.length == 4){
			String brand = multi_phenos[0];
			String category = multi_phenos[1];
			String note = multi_phenos[2];
			String url = multi_phenos[3];
			itemURL = url.trim();
			OutputFilePath = Util.OutputFilePathTRUE + brand + "_" + category + "_";
			Url.go();
			System.err.println("---OK---");
		}
	}
	
} catch (FileNotFoundException e) {
	e.printStackTrace();
}*/