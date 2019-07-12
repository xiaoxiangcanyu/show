//package src.main;
//
//import xbrl.utilities;
//
//import java.io.File;
//
//public class yhjMass {
//
//
//	public static void main(String[] args) throws Exception {
//
//		//输入文件夹的路径
//		File rootDir = new File(System.getProperty("user.dir"));
//		//
//		String[] fileList =  rootDir.list();
//		for (int i = 0; i < fileList.length; i++) {
//			if(fileList[i].startsWith("csv")){
//
//				yhjGenerate yhj=new yhjGenerate();
//				yhj.initializeFolder(fileList[i]);
//				yhj.generateInstance();
//			}
//		}
//
//
//		fileList =  rootDir.list();
//		for (int i = 0; i < fileList.length; i++) {
//			if(fileList[i].endsWith("xml")&&fileList[i].startsWith("full")){
//				utilities.printResult(">>>>>Cleaning extra unit/context: "+fileList[i]);
//				contextunitDelete deletetool=new contextunitDelete();
//				deletetool.setFilename(fileList[i]);
//				deletetool.clean();
//				utilities.printResult("Extra unit/context cleaned. "+fileList[i].replace("full-", ""));
//			}
//		}
//
//	}
//
//}
