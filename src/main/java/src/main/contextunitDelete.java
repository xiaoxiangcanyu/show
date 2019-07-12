//package src.main;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import org.xml.sax.Locator;
//import org.xml.sax.helpers.LocatorImpl;
//import xbrl.utilities;
//import xmlparse.DocumentFactoryWithLocator;
//import xmlparse.SAXReaderEnhanced;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class contextunitDelete {
//
//	public static String filename;
//
//	public static void setFilename(String file){
//		filename=file;
//	}
//
//	public static void clean() throws DocumentException, IOException{
//
//
//		String xbrlFilepath=System.getProperty("user.dir")+"\\";
//
//		//handle user input
//		String instanceFile=xbrlFilepath+filename;
//		String outputFile=xbrlFilepath+filename.replace("full-", "");
//
//		//parsing file
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(instanceFile));
//	    Element root=document.getRootElement();
//
//		//print initial results
//		System.out.println("Number of contexts: "+root.elements("context").size());
//		System.out.println("Number of units: "+root.elements("unit").size());
//		System.out.println("Start deleting extra contexts/units");
//
//		//extracting used context & unit
//		ArrayList<String> contextIdList=new ArrayList<String>();
//		ArrayList<String> unitIdList=new ArrayList<String>();
//
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//				contextIdList.add(elementUnderTest.attributeValue("contextRef"));
//			}
//			if(elementUnderTest.attributeValue("unitRef")!=null){
//				unitIdList.add(elementUnderTest.attributeValue("unitRef"));
//			}
//
//		}
//
//		utilities.removeDuplicate(contextIdList);
//		utilities.removeDuplicate(unitIdList);
//
//
//		//deleting extra context
//		List<Element> contextList=root.elements("context");
//
//		for(int i=0;i<contextList.size();i++){
//			Element contextElement=contextList.get(i);
//			String contextId=contextElement.attributeValue("id");
//			if(!utilities.find(contextIdList, contextId)){
//				root.remove(contextElement);
//			}
//		}
//
//		//deleting extra unit
//		List<Element> unitList=root.elements("unit");
//
//		for(int i=0;i<unitList.size();i++){
//			Element unitElement=unitList.get(i);
//			String unitId=unitElement.attributeValue("id");
//			if(!utilities.find(unitIdList, unitId)){
//				root.remove(unitElement);
//			}
//		}
//
//		//GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileOutputStream(outputFile), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Finish deleteing extra contexts/units");
//	    System.out.println("Number of contexts: "+root.elements("context").size());
//	    System.out.println("Number of units: "+root.elements("unit").size());
//
//
//
//	}
//
//
//	public static void main(String[] args) throws Exception {
//
//		Scanner sc = new Scanner(System.in);
//
//
//        System.out.print(">>>>>Please input the folder name(XXXX):");
//		String folder=sc.nextLine();
//		utilities.setFolder(folder);
//
//		String xbrlFilepath=System.getProperty("user.dir")+"\\"+folder;
//
//		//handle user input
//		String instanceFile=xbrlFilepath+"\\input.xml";
//		String outputFile=xbrlFilepath+"\\output.xml";
//
//		//parsing file
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(instanceFile));
//	    Element root=document.getRootElement();
//
//		//print initial results
//		System.out.println("Number of contexts: "+root.elements("context").size());
//		System.out.println("Number of units: "+root.elements("unit").size());
//		System.out.println("Start deleting extra contexts/units");
//
//		//extracting used context & unit
//		ArrayList<String> contextIdList=new ArrayList<String>();
//		ArrayList<String> unitIdList=new ArrayList<String>();
//
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//				contextIdList.add(elementUnderTest.attributeValue("contextRef"));
//			}
//			if(elementUnderTest.attributeValue("unitRef")!=null){
//				unitIdList.add(elementUnderTest.attributeValue("unitRef"));
//			}
//
//		}
//
//		utilities.removeDuplicate(contextIdList);
//		utilities.removeDuplicate(unitIdList);
//
//
//		//deleting extra context
//		List<Element> contextList=root.elements("context");
//
//		for(int i=0;i<contextList.size();i++){
//			Element contextElement=contextList.get(i);
//			String contextId=contextElement.attributeValue("id");
//			if(!utilities.find(contextIdList, contextId)){
//				root.remove(contextElement);
//			}
//		}
//
//		//deleting extra unit
//		List<Element> unitList=root.elements("unit");
//
//		for(int i=0;i<unitList.size();i++){
//			Element unitElement=unitList.get(i);
//			String unitId=unitElement.attributeValue("id");
//			if(!utilities.find(unitIdList, unitId)){
//				root.remove(unitElement);
//			}
//		}
//
//		//GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileOutputStream(outputFile), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Finish deleteing extra contexts/units");
//	    System.out.println("Number of contexts: "+root.elements("context").size());
//	    System.out.println("Number of units: "+root.elements("unit").size());
//
//	}
//}
