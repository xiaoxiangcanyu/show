//package src.main;
//
////commmit ts
//
//import org.dom4j.Document;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import org.xml.sax.Locator;
//import org.xml.sax.helpers.LocatorImpl;
//import xbrl.utilities;
//import xbrl.utilitiesCSVFile;
//import xmlparse.DocumentFactoryWithLocator;
//import xmlparse.SAXReaderEnhanced;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//public class yhjGenerate {
//
//	static Boolean restrictmode=false;
//	public static String csvFolder="";
//	public static HashMap<String,String> elementidType=new HashMap<String,String>();
//	public static ArrayList<String> tableList=new ArrayList<String>();
//	public static ArrayList<String> tableListLabel=new ArrayList<String>();
//	public static HashMap<String,String> elementcontextValue=new HashMap<String,String>();
//
//	public static String companyYear=null;
//	public static String companyCode=null;
//    public static String companyName=null;
//	public static String companyUnitMonetary=null;
//	public static String companyUnitPershare=null;
//	public static int companyDecimal =0;
//    public static int companyDecimal2=0;
//	public static int companyDecimal1=0;
//	public static int companyWeight=0;
//	public static String filename=null;
//	public static String labelReportFull="";
//	public static String labelReportFile="";
//
//	public void initializeFolder(String foldername){
//		csvFolder=foldername;
//		elementidType=new HashMap<String,String>();
//		tableList=new ArrayList<String>();
//		tableListLabel=new ArrayList<String>();
//		elementcontextValue=new HashMap<String,String>();
//
//		companyYear=null;
//		companyCode=null;
//	    companyName=null;
//		companyUnitMonetary=null;
//		companyUnitPershare=null;
//		companyDecimal =0;
//	    companyDecimal2=0;
//		companyDecimal1=0;
//		companyWeight=0;
//		filename=null;
//	}
//
//	public static void removeDuplicateLabel() throws IOException{
//		utilities.printResult("Cleaning label report");
//		ArrayList<String> lineList= new ArrayList<String>();
//		File file = new File(labelReportFull);
//		if(file.exists()){
//
//	        FileInputStream f = new FileInputStream(labelReportFull);
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			dr.readLine();//skip the first column
//			String line=dr.readLine();
//
//			while(line!= null){
//				if(!utilities.find(lineList, line)){
//					lineList.add(line);
//					File fileww = new File(labelReportFile);
//					if(!fileww.exists()){
//					   fileww.createNewFile();
//					}
//					FileWriter fwxx = new FileWriter(fileww.getAbsoluteFile(),true);
//					BufferedWriter bwxx = new BufferedWriter(fwxx);
//					bwxx.write(line+"\r\n");
//					bwxx.close();
//					fwxx.close();
//				}
//				line=dr.readLine();
//
//			}
//			dr.close();
//			f.close();
//
//		}
//		utilities.printResult("label report cleaned");
//	}
//
//
//
//	public static void initializeLabelTables() throws IOException
//	{
//
//		File file1 = new File("yhjCfg/labelname.csv");
//
//		if(file1.exists()){
//
//	    FileInputStream f = new FileInputStream("yhjCfg/labelname.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first row
//		String line=dr.readLine();
//		while(line!= null){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String value=lineSplit.get(0);
//				tableListLabel.add(value);
//				line=dr.readLine();
//		}
//		}
//
//
//	 }
//
//	public static String checkRawfiles() throws Exception{
//		utilities.printResult("1>Performing csv file check");
//		Boolean checkPass=checkFiles();//�ļ�У��
//	    if(!checkPass) {
//	    	utilities.printResult("1>csv file check failed");
//	    	if(restrictmode) return "fail";
//	    }else{
//	    	utilities.printResult("1>csv file check passed");
//	    }
//
//		utilities.printResult("2>Performing table header check");
//		Boolean acheckPass=checkHeader();
//		if(acheckPass){
//			utilities.printResult("2>table header check passed");
//		}else{
//			utilities.printResult("2>table header check failed");
//			if(restrictmode) return "fail";
//		}
//
//		utilities.printResult("3>Performing duplicate item check");
//		Boolean checkPassDuplicate=true;
//	    for(int i=0;i<tableList.size();i++){
//			if(!checkDuplicate(tableList.get(i))) checkPassDuplicate=false;
//	    }
//		if(checkPassDuplicate){
//			utilities.printResult("3>duplicate item check passed");
//		}else{
//			utilities.printResult("3>duplicate item check failed");
//			if(restrictmode) return "fail";
//		}
//
//		return "pass";
//	}
//
//	public static String generateInstance() throws Exception {
//
//		utilities.setFolder(System.getProperty("user.dir"));
//
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		utilities.printResult("************************");
//		utilities.printResult("csvFolder: "+csvFolder);
//		utilities.printResult("Report Time: "+df.format(new Date()));
//
//
//	    //initialize element type
//	    initializeElementType();
//
//		//initialize configuartion
//	    initializeConfiguration();
//	    companyDecimal=companyDecimal1+companyDecimal2;
//
//	    //clean label report
//	    initializeLabelReport();
//
//	    //validate raw files
//	    String checkResult=checkRawfiles();
//	    if(restrictmode&&checkResult.equals("fail")) return "";
//
////
//		//create document root
//		utilities.printResult("Start generating instance file.");
//		String instancefilebefore="standard_instance.xml";
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(instancefilebefore));
//	    Element root=document.getRootElement();
//
//
//	    //change identifier
//		List<Element> contextList=root.elements("context");
//		for(int i=0;i<contextList.size();i++){
//			Element contextElm=contextList.get(i);
//			contextElm.element("entity").element("identifier").setText(companyCode);
//
//		}
//
//	    //generate facts
//	    for(int i=0;i<tableList.size();i++){
//			generateTableFacts(root, tableList.get(i));
//	    }
//
//
//		utilities.printResult("Finish generating instance file.");
//
//		//remove duplicate in label report
//		removeDuplicateLabel();
//
//
//		//validate duplicate facts
//		removeDuplicateFact(root);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    String instancefileafter="full-"+filename+".xml";
//
//	    XMLWriter writer = new XMLWriter(new FileOutputStream(instancefileafter), format);
//	    writer.write(document);
//	    writer.close();
//
//	    return "";
//	}
//
//
//	private static void changeIdentifier(Element root) {
//
//
//	}
//
//
//	public static void generateSingleFact(Element root,String elementName,String context,String unit,int decimal,String value,String label,String tableName) throws IOException, ParseException{
//
//
//			Element dataElement=root.addElement(elementName);
//
//			if(!label.isEmpty()){
//				dataElement.addAttribute("nafmii_model:labelName", label);
//				File file = new File(labelReportFull);
//				if(!file.exists()){
//				   file.createNewFile();
//				}
//				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				bw.write(companyCode+",");
//				bw.write(companyName+",");
//				bw.write(companyYear+",");
//				bw.write(tableName+",");
//				bw.write(label+",");
//				bw.write(elementName+"\r\n");
//				bw.close();
//				fw.close();
//			}
//
//
//			if(elementName.split(":").length<2){
//				utilities.printResult("nonstandard element name found in mapping: "+elementName);
//			}
//			String type=elementidType.get(elementName.split(":")[0]+"_"+elementName.split(":")[1]);
//			if(type==null){
//				utilities.printResult("element not found in taxnomies: "+elementName);
//			}
//			//weight transform & unit set
//			if(type.equals("xbrli:monetaryItemType")){
//				//unit transform
//				if(value.isEmpty()) value="0";
//				 DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//��ʽ������
//				value=decimalFormat.format(Double.parseDouble(value.replace("(", "").replace(")", "").replace(",", ""))*companyWeight);
//				dataElement.addAttribute("unitRef", unit);
//				dataElement.addAttribute("decimals", String.valueOf(decimal));
//			}
//
//			if(type.equals("num:perShareItemType")){
//
//				dataElement.addAttribute("unitRef", companyUnitPershare);
//				dataElement.addAttribute("decimals", String.valueOf(decimal));
//			}
//
//			if(type.equals("xbrli:dateItemType")){
//				value=utilities.formatDate(utilities.parseDate(value), "yyyy-MM-dd");
//			}
//
//			dataElement.addAttribute("contextRef", context);
//
//			dataElement.setText(value.replaceAll(",",""));
//
//	}
//
//
//public static boolean checkDuplicate(String tableName) throws Exception{
//
//		boolean checkPass=true;
//
//		File file = new File(csvFolder+"/"+tableName+".csv");
//		List<String> list = new ArrayList<String>();
//			if(file.exists()){
//
//	        FileInputStream f = new FileInputStream(csvFolder+"/"+tableName+".csv");
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			int rowCount=1;
//			String line=dr.readLine();
//			while(line!= null){
//
//					ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//					if(tableName.equals("�ϲ�������Ȩ��䶯��-������")||tableName.equals("�ϲ�������Ȩ��䶯��-������"))
//                       {
//						if(rowCount>=2)
//						{
//						 String itemName=lineSplit.get(0);
//						 if(!list.contains(itemName))
//						 {
//							 if(!itemName.isEmpty())  list.add(itemName);
//						 }
//						 else
//						 {
//							 checkPass=false;
//							 utilities.printResult("Company name:"+companyName+"; duplicate item detected. table name: "+tableName+"; item name:"+itemName);
//						 }
//						}
//	                   }
//					else
//					{
//						String itemName=lineSplit.get(0);
//						 if(!list.contains(itemName))
//						 {
//							 if(!itemName.isEmpty()) list.add(itemName);
//						 }
//						 else
//						 {
//							 checkPass=false;
//							 utilities.printResult("Company name:"+companyName+"; duplicate item detected. table name: "+tableName+"; item name:"+itemName);
//						 }
//					}
//
//				line = dr.readLine();
//				rowCount++;
//			}
//			dr.close();
//			f.close();
//
//		}
//
//		return checkPass;
//	}
//
//
//    public static boolean checkHeader() throws IOException
//    {
//    	boolean checkPass=true;
//	    List<String> list = new ArrayList<String>();
//		File file = new File("yhjCfg/Integrity check.csv");
//		if(file.exists()){
//        FileInputStream f = new FileInputStream("yhjCfg/Integrity check.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String tableName=lineSplit.get(0);
//				//System.out.println(tableName);
//				if(!tableName.equals("������Ϣ")&&checkHeaderTable(tableName)){
//					checkPass=false;
//				}
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//		return checkPass;
//
//		}
//		return checkPass;
//
//    }
//
//    public static boolean checkHeaderTable(String tableName) throws IOException
//    {
//    	boolean checkPass=true;
//         File file = new File(csvFolder+"/"+tableName+".csv");
//
//		ArrayList<Integer> list1 =new ArrayList<Integer>();
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream(csvFolder+"/"+tableName+".csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//
//		String line=dr.readLine();
//		ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//		String B1=lineSplit.get(1).substring(0, 4);
//		String C1=lineSplit.get(2).substring(0, 4);
//		if(Integer.parseInt(B1)<Integer.parseInt(C1)){
//			checkPass=false;
//		}
//
//		dr.close();
//		f.close();
//			if(!checkPass){
//				utilities.printResult("Company:"+companyName+";"+"exception noted in header.tableName:"+tableName);
//			}else{
//				//utilities.printResult("Company:"+name+";"+"no exception noted in header.tableName:"+tableName);
//			}
//
//		 }
//
//		return checkPass;
//
//    }
//
//    public static void initializeLabelReport() throws IOException
//	{
//
//	    labelReportFull="full-"+companyCode+"-"+companyYear+"-"+companyName+"-��ǩͳ��.csv";
//	    labelReportFile=companyCode+"-"+companyYear+"-"+companyName+"-��ǩͳ��.csv";
//		File f1 = new File(labelReportFull);
//		FileWriter fw1 =  new FileWriter(f1);
//		fw1.write("");
//		fw1.close();
//		File f2 = new File(labelReportFile);
//		FileWriter fw2 =  new FileWriter(f2);
//		fw2.write("");
//		fw2.close();
//
//	 }
//
//
//
//
//	public static boolean checkFiles() throws IOException
//	{
//		Boolean checkPass=true;
//		String filePath=csvFolder;
//		File root = new File(filePath);
//	    File[] files = root.listFiles();
//	    List<String> list = new ArrayList<String>();
//
//		File file1 = new File("yhjCfg/Integrity check.csv");
//		//weight-transform
//		if(file1.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/Integrity check.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				tableList.add(valueTC);
//				if(rowCount>=2)
//				{
//				//System.out.println(valueTC);
//				list.add(valueTC+".csv");
//				}
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//		}
//	    List<String> list1 = new ArrayList<String>();
//	    for(File file:files){
//	     if(!file.isDirectory()){
//	    	 list1.add(file.getName());
//	       if(!list.contains(file.getName()))
//	       {
//	    	   if(!file.getName().equals("������Դ�ο�.csv"))
//	    	   {
//	    		   checkPass=false;
//	    		   utilities.printResult("Company:"+companyName+";"+file.getName()+"δƥ�䵽��Ϣ");
//	    		   list1.remove(file.getName());
//	    	   }
//	    	   else
//	    	   {
//	    		   list1.remove(file.getName());
//	    	   }
//
//	       }
//
//	       }
//
//
//	     }
//	   if(list1.size()<list.size())
//	   {
//		   for(int i=0;i<list.size();i++)
//		   {
//			  if(!list1.contains(list.get(i)))
//			  {
//				  checkPass=false;
//				  utilities.printResult("Company:"+companyName+";"+list.get(i)+"ȱʧ");
//			  }
//
//		   }
//	   }
//	   return checkPass;
//
//	 }
//
//
//    public static void getCoverParameters(String value, String rno,String lno) throws IOException
//    {
//    	File file = new File(csvFolder+"/������Ϣ.csv");
//		String a2=null;
//		String a1=null;
//
//
//		ArrayList<Integer> list =new ArrayList<Integer>();
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream(csvFolder+"/������Ϣ.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//    	  if(value.equals("����")&&(Integer.parseInt(rno)==rowCount))
//    	   {
//    		 int lno1 =Integer.parseInt(lno);
//    		  String a0 =lineSplit.get(lno1-1);
//    		  companyUnitMonetary =getCurrencyMonetary(a0);//����
//    		  companyUnitPershare=getCurrencyPershare(a0);//����2
//    	   }
//    	  if(value.equals("������")&&(Integer.parseInt(rno)==rowCount))
//    	  {
//    		  int lno1 =Integer.parseInt(lno);
//     		   String a =lineSplit.get(lno1-1);
//     		    list =getWeight(a);
//     		    companyWeight=list.get(0);
//     		    companyDecimal2 = list.get(1);//������
//    	  }
//    	  if(value.equals("��ȷ��")&&(Integer.parseInt(rno)==rowCount))
//    	  {
//    		  int lno1 =Integer.parseInt(lno);
//     		   a1 =lineSplit.get(lno1-1);
//     		  companyDecimal1 =getDecimal(a1);//��ȷ��
//    	  }
//    	  if(value.equals("��˾��֯��������")&&(Integer.parseInt(rno)==rowCount))
//    	  {
//    		  int lno1 =Integer.parseInt(lno);
//    		   companyCode =lineSplit.get(lno1-1);
//
//    	  }
//    	  if(value.equals("��˾����")&&(Integer.parseInt(rno)==rowCount))
//    	  {
//    		  int lno1 =Integer.parseInt(lno);
//		       companyName =lineSplit.get(lno1-1);
//
//    	  }
//    	  if(value.equals("�������")&&(Integer.parseInt(rno)==rowCount))
//    	  {
//    	   int lno1 =Integer.parseInt(lno);
//		    companyYear=lineSplit.get(lno1-1);
//    	  }
//    	  filename =companyCode+"-"+companyYear+"-"+companyName;  //ʵ���ĵ�����
//    	  line = dr.readLine();
//    	  rowCount++;
//		  }
//		dr.close();
//		f.close();
//		}
//    }
//
//
//
//    public static ArrayList<Integer> getWeight(String value) throws IOException
//    {
//    	int a=0;
//    	int a3=0;
//     ArrayList<Integer> list=new ArrayList<Integer>();
//
//		File file = new File("yhjCfg/weight-transform.csv");
//		//weight-transform
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/weight-transform.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				if(valueTC.equals(value))
//				{
//					String a1=lineSplit.get(1).replace(",","");
//					a = Integer.parseInt(a1);
//					String a2=lineSplit.get(2).replace(",","");
//					a3 =Integer.parseInt(a2);
//					list.add(a);
//					list.add(a3);
//				}
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//
//	return list;
//
//    }
//
//    public static Integer getDecimal(String value) throws IOException
//    {
//    	int a=0;
//     ArrayList<String[]> mapping=new ArrayList<String[]>();
//
//		File file = new File("yhjCfg/decimal.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/decimal.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				if(valueTC.equals(value))
//				{
//					String a1=lineSplit.get(1);
//					a = Integer.parseInt(a1);
//				}
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	return a;
//
//    }
//    public static String getCurrencyMonetary(String value) throws IOException
//    {
//    	String a=null;
//     ArrayList<String[]> mapping=new ArrayList<String[]>();
//
//		File file = new File("yhjCfg/currency.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/currency.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				if(valueTC.equals(value))
//				{
//					a=lineSplit.get(1);
//				}
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	return a;
//
//    }
//
//    public static String getCurrencyPershare(String value) throws IOException
//    {
//    	String a=null;
//     ArrayList<String[]> mapping=new ArrayList<String[]>();
//
//		File file = new File("yhjCfg/currency.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/currency.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				if(valueTC.equals(value))
//				{
//					a=lineSplit.get(2);
//				}
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	return a;
//
//    }
//	private static void initializeConfiguration() throws IOException {
//
//
//		initializeLabelTables();
//
//
//		File file = new File("yhjCfg/cover page location.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/cover page location.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		dr.readLine();//skip the first column
//		String line=dr.readLine();
//		while(line!= null){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String valueTC=lineSplit.get(0);
//				String rno=lineSplit.get(1);
//				String lno=lineSplit.get(2);
//				getCoverParameters(valueTC, rno,lno);
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	}
//
//
//
//
//
//
//	public static String generateTableFacts(Element root,String tableName) throws Exception{
//
//		File file = new File(csvFolder+"/"+tableName+".csv");
//
//			if(file.exists()){
//
//	        FileInputStream f = new FileInputStream(csvFolder+"/"+tableName+".csv");
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			int rowCount=1;
//			dr.readLine();//skip the table header
//			String line=dr.readLine();
//			while(line!= null){
//
//					ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//					String itemName=lineSplit.get(0);
//					String item = processData(itemName.replace(" ", ""));//ȥ�����е�����
//					Boolean dataDetected=true;
//					if(lineSplit.get(1).isEmpty()&&lineSplit.get(2).isEmpty()) dataDetected=false;
//
//					if(!item.isEmpty()&&dataDetected){
//
//						ArrayList<String[]> matchedMapping=mapElement(tableName,item);
//
//						if(matchedMapping.size()==0){
//							utilities.printResult("ReadyMappingCheck not found.Company:"+companyName+";Table: "+tableName+";Item: "+item);
//						}
//
//						for(int i=0;i<matchedMapping.size();i++){
//							String elementname=matchedMapping.get(i)[1];
//							String colno=matchedMapping.get(i)[0];
//							String contextid=matchedMapping.get(i)[2];
//
//							String value=lineSplit.get(Integer.parseInt(colno)-1);
//
//
//							String label="";
//							if(utilities.find(tableListLabel, tableName)){
//								label=lineSplit.get(3);
//								if(elementname.equals("ifrs-full:CashAndCashEquivalents")&&item.contains("�ڳ�")) label="";
//							}
//
//							if(!value.isEmpty()){
//								generateSingleFact(root,elementname,contextid,companyUnitMonetary,companyDecimal,value,label,tableName);
//							}
//						}
//
//					}
//
//				line = dr.readLine();
//				rowCount++;
//			}
//			dr.close();
//			f.close();
//
//		}
//
//		return "#FileNotFound#";
//	}
//
//
//private static String processData(String itemName) throws IOException {
//
//		ArrayList<String[]> mapping=new ArrayList<String[]>();
//
//		File file = new File("yhjCfg/data process.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/data process.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				itemName=itemName.replace(lineSplit.get(0), "");
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	return itemName;
//	}
//
//
//
//
//	private static ArrayList<String[]> mapElement(String tableName, String itemName) throws IOException {
//
//		ArrayList<String[]> mapping=new ArrayList<String[]>();
//
//		File file = new File("yhjCfg/Element ReadyMappingCheck.csv");
//
//		if(file.exists()){
//
//        FileInputStream f = new FileInputStream("yhjCfg/Element ReadyMappingCheck.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		while(line!= null){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String tableNameTC=lineSplit.get(0);
//				String itemNameTC=lineSplit.get(1);
//				if((tableName+itemName).equals(tableNameTC+itemNameTC)){
//					//elementname,colno,contextid
//					mapping.add(new String[]{lineSplit.get(2),lineSplit.get(3),lineSplit.get(4)});
//				}
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	return mapping;
//	}
//
//
//	public static void initializeElementType() throws Exception{
//
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(".\\yhjTaxonomy",taxonomyFiles);
//		//utilities.printResult(taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			String filename=taxonomyFiles.get(i);
//			Element root= utilities.loadxml(filename);
//			ArrayList<Element> allElements=new ArrayList<Element>();
//			utilities.getAllElements(root, allElements);
//			for(int j=0;j<allElements.size();j++){
//				Element elementCurrent=allElements.get(j);
//				if(elementCurrent.attributeValue("id")!=null){
//					elementidType.put(elementCurrent.attributeValue("id"), elementCurrent.attributeValue("type"));
//				}
//			}
//		}
//	}
//
//	public static void removeDuplicateFact(Element root) throws IOException{
//		List<Element> instanceElements=root.elements();
//		for(int i=0;i<instanceElements.size();i++){
//			Element currentElement=instanceElements.get(i);
//			if(currentElement.attribute("contextRef")!=null){
//
//				String elementName=currentElement.getNamespacePrefix()+currentElement.getName();
//				String elementContext=currentElement.attribute("contextRef").getValue();
//				String elementID=elementName+elementContext;
//				String elementValue=currentElement.getText();
//
//				if(elementcontextValue.get(elementID)==null){
//					elementcontextValue.put(elementID, elementValue);
//					//utilities.printResult("insert code invoked. Element name: "+elementName+";Element context: "+elementContext+";Element value: "+elementValue);
//				}else if(elementcontextValue.get(elementID).equals(elementValue)){
//					//utilities.printResult("delete code invoked. Element name: "+elementName+";Element context: "+elementContext+";Element value: "+elementValue);
//					//System.out.println(root.elements().size());
//					root.remove(currentElement);
//					//System.out.println(root.elements().size());
//				}else{
//					//utilities.printResult(elementcontextValue.get(elementID));
//					utilities.printResult("Duplicate detected. Element name: "+elementName+";Element context: "+elementContext+";Duplicate Element value: "+elementValue+";First Element value: "+elementcontextValue.get(elementID));
//				}
//			}
//		}
//	}
//
//}
