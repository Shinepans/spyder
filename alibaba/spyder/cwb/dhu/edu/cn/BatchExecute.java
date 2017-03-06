package alibaba.spyder.cwb.dhu.edu.cn;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class BatchExecute {

	final static int machineNum = 100; //同时执行任务机器的最大数量
	final static int taskNum = 1;	   //分配每台机器的任务数量
	public static void main(String[] args) {
		try{
			Workbook workbook = UtilsExcel.getWeebWork(Step0InputInterface.urlSourceFilePath);
//				System.out.println("总表页数为：" + workbook.getNumberOfSheets());// 获取表页数
			Sheet sheet = workbook.getSheetAt(0);// Sheet sheet = workbook.getSheetAt(1);
			int rownum = sheet.getLastRowNum();// 获取总行数
//			for(int round=1; round<=((rownum/(machineNum*taskNum))+1);round++){
//				
//			}
			
			//Method 1
//			for(int k=0;k<machineNum;k++){
//				Machine machine = new Machine(k*taskNum,(k+1)*taskNum,sheet);
//				machine.start();  
//			}
			
			//Method 2
			/**
			 * 如果当前需要执行的任务超过池大小，那么多出的任务处于等待状态，直到有空闲下来的线程执行任务，如果当前需要执行的任务小于池大小，空闲的线程也不会去销毁。
			 * */
			ExecutorService executorService2 = Executors.newFixedThreadPool(machineNum);  
			for(int t=0;t<rownum;t++){
				Machine machine = new Machine(t*taskNum,(t+1)*taskNum,sheet);
				executorService2.execute(machine);
			}
			
		}catch(IOException e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally{
			System.out.println("over");
		}
	}
}
