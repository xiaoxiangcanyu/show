//package src.xbrl;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class preCheck {
//
//
//	public static void checkInput(String base, String date,String startdate, String enddate) throws Exception{
//		Boolean inputCheck=true;
//
//
//		if(!base.matches("\\w{4}")){
//			utilities.printResult(">>>>>Base input invalid.\n");
//			inputCheck=false;
//		}
//		if(!date.matches("\\d{8}")){
//			utilities.printResult(">>>>>date input invalid.\n");
//			inputCheck=false;
//		}
//		/*
//		if(!cik.matches("\\d{10}")){
//			utilities.printResult(">>>>>cik input invalid.\n");
//			inputCheck=false;
//		}
//		*/
//		if(!startdate.matches("\\d{4}-\\d{2}-\\d{2}")){
//			utilities.printResult(">>>>>startdate input invalid.\n");
//			inputCheck=false;
//		}
//		if(!enddate.matches("\\d{4}-\\d{2}-\\d{2}")){
//			utilities.printResult(">>>>>enddate input invalid.\n");
//			inputCheck=false;
//		}
//
//		if(!inputCheck){
//			utilities.printResult(">>>>>Invalid input detected. Rerun this program and correct your input.");
//			System.exit(0);
//		}
//
//	}
//
//	public static void checkFileExist(ArrayList<String> xbrlIndex, HashMap<String, String> xbrlFiles) throws Exception {
//
//
//		Boolean fileexistCheck=true;
//		for(int i=0;i<xbrlIndex.size();i++){
//			String filename=xbrlFiles.get(xbrlIndex.get(i));
//			File file = new File(filename);
//			if(!file.exists()){
//					utilities.printResult(">>>>>Expected xbrl file not found. Filename:"+filename+".\n");
//					fileexistCheck=false;
//			}
//
//		}
//		if(!fileexistCheck){
//			utilities.printResult(">>>>>Expected xbrl files not detected. Place these files and rerun the program.");
//			System.exit(0);
//		}
//
//	}
//
//	public static void checkChinese(ArrayList<String> xbrlIndex, HashMap<String, String> xbrlFiles) throws Exception{
//
//		for(int i=0;i<xbrlIndex.size();i++){
//			String filename=xbrlFiles.get(xbrlIndex.get(i));
//			File file = new File(filename);
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			Integer linecount=1;
//			String line = reader.readLine();
//			while(line!=null){
//
//				if(utilities.isChinese(line)){
//					utilities.printResult(">>>>>Chinese character detected. Filename:"+filename+";Linenumber:"+linecount.toString()+".\n");
//				}
//
//			    line = reader.readLine();
//			    linecount++;
//			}
//			reader.close();
//		}
//
//	}
//
//	public static void tryParse(ArrayList<String> xbrlIndex, HashMap<String, String> xbrlFiles) throws Exception{
//		Boolean parseOkay=true;
//		for(int i=0;i<xbrlIndex.size();i++){
//			String filename=xbrlFiles.get(xbrlIndex.get(i));
//			try{
//				utilities.loadxml(filename);
//			}catch(Exception e){
//				System.out.println(e);
//				parseOkay=false;
//			}
//
//		}
//		if(!parseOkay){
//			utilities.printResult(">>>>>Xbrl files are not valid to parse. Correct these files and rerun the program.");
//			System.exit(0);
//		}
//
//	}
//
//
//}
