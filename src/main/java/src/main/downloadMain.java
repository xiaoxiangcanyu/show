//package src.main;
//
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import xbrl.utilities;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class downloadMain {
//
//	public static ArrayList<String> fileList=new ArrayList<String>();
//
//	public static void main(String[] args) throws Exception {
//		utilities.setFolder("downloadReport");
//		//parsing taxonomy files
//		String taxonomyFolder=System.getProperty("user.dir")+"\\taxonomy";
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(taxonomyFolder,taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			String filename=taxonomyFiles.get(i);
//			Element root= utilities.loadxml(filename);
//			ArrayList<Element> allElements=new ArrayList<Element>();
//			utilities.getAllElements(root, allElements);
//			for(int j=0;j<allElements.size();j++){
//				Element elementCurrent=allElements.get(j);
//				if(elementCurrent.getName().equals("import")){
//					String remoteFile=elementCurrent.attributeValue("schemaLocation");
//					utilities.printResult("**********************************");
//					utilities.printResult(""+filename);
//					parseRemoteFile(filename.substring(0, filename.lastIndexOf("\\")+1),remoteFile);
//				}
//			}
//		}
//	}
//
//	public static void parseRemoteFile(String remotePath, String remoteFile) throws DocumentException, IOException{
//
//		utilities.printResult("---------------------");
//		utilities.printResult("path:"+remotePath);
//		utilities.printResult("file:"+remoteFile);
//		utilities.printResult("filelist:"+fileList);
//
//		if(!utilities.find(fileList, remoteFile)){
//
//			Boolean fileExist=false;
//
//			if(remoteFile.contains("http") || remotePath.contains("http")){
//				String[] remoteFileArray=remoteFile.split("/");
//				String localFile=remoteFileArray[remoteFileArray.length-1];
//				if(!utilities.find(fileList, localFile)){
//					if(remotePath.contains("http") && !remoteFile.contains("http")){
//						utilities.downloadFile(remotePath+remoteFile,System.getProperty("user.dir")+"\\taxonomy\\"+remoteFileArray[remoteFileArray.length-1]);
//					}else{
//						utilities.downloadFile(remoteFile,System.getProperty("user.dir")+"\\taxonomy\\"+remoteFileArray[remoteFileArray.length-1]);
//						remotePath=remoteFile.substring(0, remoteFile.lastIndexOf("/")).replace("\\", "/")+"/";
//					}
//					fileList.add(localFile);
//					remoteFile=System.getProperty("user.dir")+"\\taxonomy\\"+remoteFileArray[remoteFileArray.length-1];
//				}else{
//					fileExist=true;
//				}
//			}else{
//				remoteFile=remotePath+remoteFile;
//				if(remoteFile.contains("/")){
//					remotePath=remoteFile.substring(0, remoteFile.lastIndexOf("/")+1);
//				}else{
//					remotePath=remoteFile.substring(0, remoteFile.lastIndexOf("\\")+1);
//				}
//			}
//
//			if(!fileExist){
//				utilities.printResult("parsed remoted file: "+remoteFile);
//				utilities.printResult("set remote path: "+remotePath);
//				Element root= utilities.loadxml(remoteFile);
//
//				ArrayList<Element> allElements=new ArrayList<Element>();
//				utilities.getAllElements(root, allElements);
//				for(int j=0;j<allElements.size();j++){
//					Element elementCurrent=allElements.get(j);
//					if(elementCurrent.getName().equals("import")){
//						parseRemoteFile(remotePath,elementCurrent.attributeValue("schemaLocation"));
//					}
//				}
//			}
//
//		}
//	}
//
//}
