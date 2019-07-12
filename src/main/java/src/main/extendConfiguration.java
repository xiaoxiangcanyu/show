//package src.main;
//
//import xbrl.utilities;
//import xbrl.utilitiesCSVFile;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//public class extendConfiguration {
//
//	public static void main(String[] args) throws IOException {
//
//		utilities.setFolder(System.getProperty("user.dir"));
//		utilities.cleanReportSimple();
//
//		FileInputStream f = new FileInputStream("mapping.csv");
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		utilities.printResultSimple(dr.readLine());
//		lineCount++;
//
//
//		String line = dr.readLine();
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			String tableName=lineSplit.get(0);
//			String rowNo=lineSplit.get(1);
//			String columnNo=lineSplit.get(2);
//			String elementQName=lineSplit.get(3);
//			String sign=lineSplit.get(4);
//			String contextID=lineSplit.get(5);
//			String repeatTimes=lineSplit.get(6);
//
//			if(repeatTimes.isEmpty()){
//				utilities.printResultSimple(line);
//			}else{
//				int repeat=Integer.parseInt(repeatTimes);
//				for(int i=0;i<repeat;i++){
//					String lineGenerated="";
//					lineGenerated=tableName+","+String.valueOf(Integer.parseInt(rowNo)+i)+","+columnNo+","+elementQName+","+sign+","+contextID+String.valueOf(i+1)+",";
//					//System.out.println(lineGenerated);
//					utilities.printResultSimple(lineGenerated);
//				}
//			}
//
//
//			line = dr.readLine();
//			lineCount++;
//		}
//
//		dr.close();
//		f.close();
//
//	}
//
//}
