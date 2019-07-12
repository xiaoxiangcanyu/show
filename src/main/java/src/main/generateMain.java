//package src.main;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import xbrl.utilities;
//import xbrl.utilitiesCSVFile;
//
//import java.io.*;
//import java.util.*;
//
//public class generateMain {
//	public static List<Element> schemaElements;
//	public static HashMap<String,String> typetransfer=new HashMap<String,String>();
//	public static HashMap<String,String> sgtransfer=new HashMap<String,String>();
//	public static HashMap<String,String> prefixtransfer=new HashMap<String,String>();
//	public static HashMap<String,String> labeltransfer=new HashMap<String,String>();
//	public static ArrayList<String> allowLowcase=new ArrayList<String>();
//	public static HashMap<String,HashMap<String,String>> elementValidate= new HashMap<String,HashMap<String,String>>();
//	public static Boolean checkFlag=true;
//	public static String elementlistfile="elementlist.csv";
//	public static String base;
//	public static String date;
//	public static String targetNamespace;
//	public static HashMap<String,String> xbrlFiles=new HashMap<String,String>();
//	public static HashMap<String,Integer> columnIndex=new HashMap<String,Integer>();
//
//	public static void main(String[] args) throws Exception {
//
//
//		Scanner sc = new Scanner(System.in);
//		Boolean testmode=false;
//		if(testmode){
//			//make sure it matches with the tested element.csv
//			base="sohu";
//			date="20131231";
//			targetNamespace="http://www.ku6.com/20131231";
//		}else{
//
//	        System.out.print(">>>>>Please input the base(XXXX):");
//			base=sc.nextLine();
//	        System.out.print(">>>>>Please input the date(YYYYMMDD):");
//			date=sc.nextLine();
//	        System.out.print(">>>>>Please input the targetNamespace(http://xxxxxx/YYYYMMDD):");
//			targetNamespace=sc.nextLine();
//		}
//
//
//		schemaElements=utilities.getSchemaElements();
//		utilities.setFolder(System.getProperty("user.dir"));
//		utilities.cleanReport();
//		setColumnindex();
//		setAllowLowcase();
//		checkElementlist();
//		if(!checkFlag){
//	        System.out.print(">>>>>Skip elementlist errors(y/n):");
//			String checkskip=sc.nextLine();
//			if(!checkskip.equals("y")){
//				System.exit(1);
//			}
//		}
//
//
//		//set xbrlFiles
//		String xbrlFilepath=System.getProperty("user.dir");
//		xbrlFiles.put("schema", xbrlFilepath+"\\"+base+"-"+date+".xsd");
//		xbrlFiles.put("labxml", xbrlFilepath+"\\"+base+"-"+date+"_lab.xml");
//
//		utilities.printResult(">>>>>Please make sure there is no line break in the header.");
//		utilities.printResult(">>>>>Please make sure there is no empty line in the bottom.");
//
//        System.out.print(">>>>>Skip generation of schema, label and presentation(y/n):");
//		String skip=sc.nextLine();
//		if(skip.equals("y") || skip.equals("n")){
//			if(skip.equals("y")){
//				generateDefinitionFileFromPresentation();
//			}else{
//				generateSchemaFile();
//				generateLabelFile();
//				generatePresentationFile();
//		        System.out.print(">>>>>Continue to generate defintion file based on presentionfile(y/n):");
//				String definition=sc.nextLine();
//
//				if(definition.equals("y") || definition.equals("n")){
//					if(definition.equals("y")){
//						generateDefinitionFileFromPresentation();
//					}
//				}else{
//					utilities.printResult("Invalid input.");System.exit(0);
//				}
//			}
//		}else{
//			utilities.printResult("Invalid input.");System.exit(0);
//		}
//
//		sc.close();
//
//		utilities.printResult(">>>>>Please use xwand to validate generated xbrl files.");
//	}
//
//
//	 // check linebreak
//	public static void checkCsvfile() throws IOException{
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//		lineCount++;
//		int sequence=1;//used for validation of linebreak
//		String line = dr.readLine();
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			if(!roletypeDetected){
//				if(lineSplit==null || lineSplit.size()==0){
//					utilities.printResult("Line break detected. Line number:"+lineCount);
//					utilities.printResult("Correct the file and rerun the program.");
//					System.exit(0);
//				}
//				else{
//					if(!utilities.isInteger(lineSplit.get(0))){
//						utilities.printResult("Line break detected. Line number:"+lineCount);
//						utilities.printResult("Correct the file and rerun the program.");
//						System.exit(0);
//					}
//					else if(Integer.valueOf(lineSplit.get(0))!=sequence){
//						utilities.printResult("Line break detected. Line number:"+lineCount);
//						utilities.printResult("Correct the file and rerun the program.");
//						System.exit(0);
//					}
//					sequence++;
//				}
//			}else{
//				if(lineSplit==null || lineSplit.size()==0){
//					utilities.printResult("Line break detected. Line number:"+lineCount);
//					utilities.printResult("Correct the file and rerun the program.");
//					utilities.printResult(line);
//					System.exit(0);
//				}
//				else{
//					if(!lineSplit.get(0).matches("\\d{5}(.*)")){
//						utilities.printResult("Line break detected. Line number:"+lineCount);
//						utilities.printResult("Correct the file and rerun the program.");
//						utilities.printResult(line);
//						System.exit(0);
//					}
//				}
//			}
//			line = dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void checkElementName(int lineCount,String standardlabel,String elementname) throws IOException{
//
//		String[] textSplit=standardlabel.split(" ");
//		String namefromlabel="";
//		for(int kk=0;kk<textSplit.length;kk++){
//			String segment=textSplit[kk].replaceAll("[^0-9a-zA-Z]","");
//			if(!segment.isEmpty()){
//				namefromlabel=namefromlabel+segment.substring(0,1).toUpperCase()+segment.substring(1, segment.length()).toLowerCase();
//			}
//		}
//		if(!elementname.equals(namefromlabel)){
//			utilities.printResult("standard label inconsistent with element name. line number:"+lineCount);
//			checkFlag=false;
//		}
//	}
//
//	public static void checkElementAttribute(int lineCount,String elementname,String standardlabel,HashMap<String,String> elementAttribute) throws IOException{
//
//		if(elementAttribute.get("type").equals("xbrli:monetaryItemType")&&elementAttribute.get("balance").isEmpty()){
//			utilities.printResult("invalid element attribute type detected. line number:"+lineCount);
//			checkFlag=false;
//		}
//
//		if(!standardlabel.contains("[")&&!elementAttribute.get("abstract").equals("false")){
//			utilities.printResult("invalid element attribute abstract detected. line number:"+lineCount);
//			checkFlag=false;
//		}
//
//		Iterator iter = elementValidate.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			String elementKey = (String) entry.getKey();
//			if(elementname.contains(elementKey)){
//				HashMap<String,String> elementValidateConfig = (HashMap<String, String>) entry.getValue();
//				if(!elementAttribute.get("label").contains(elementValidateConfig.get("label"))){
//					utilities.printResult("invalid element attribute label detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//				if(!elementAttribute.get("type").equals(elementValidateConfig.get("type"))){
//					utilities.printResult("invalid element attribute type detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//				if(!elementAttribute.get("substitutionGroup").equals(elementValidateConfig.get("substitutionGroup"))){
//					utilities.printResult("invalid element attribute substitutionGroup detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//				if(!elementAttribute.get("abstract").equals(elementValidateConfig.get("abstract"))){
//					utilities.printResult("invalid element attribute abstract detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//				if(elementAttribute.get("balance")!=null){
//					utilities.printResult("invalid element attribute balance detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//				if(!elementAttribute.get("periodType").equals("duration")){
//					utilities.printResult("invalid element attribute periodType detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//			}
//		}
//	}
//
//
//	public static void checkGaap(int lineCount,String elementname) throws IOException, DocumentException{
//
//		for(int i=0;i<schemaElements.size();i++){
//			Element schemaElement=schemaElements.get(i);
//			String gaapName=schemaElement.attributeValue("name");
//			//utilities.printResult(gaapName);
//			if(gaapName.equals(elementname)){
//				checkFlag=false;
//				utilities.printResult("element name duplicate with gaap. Line number:"+lineCount);
//			}
//		}
//	}
//
//	public static void checkLabelConsistencyCharles() throws IOException {
//		//charles
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//		lineCount++;
//
//
//		HashMap<String, String[]> elementStandardLabel = new HashMap<String,String[]>();
//		HashMap<String, String[]> elementroleLabel = new HashMap<String,String[]>();
//		HashMap<String, String[]> elementDoc = new HashMap<String,String[]>();
//		String line = dr.readLine();
//		while(line!= null){
//
//			Boolean roletypeDetected=isELR(line);
//			if(!roletypeDetected){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//				String preferredlabelrole=lineSplit.get(columnIndex.get("preferredlabelrole")-1);
//				String preferredlabel=lineSplit.get(columnIndex.get("preferredlabel")-1);
//				String CSVdoc=lineSplit.get(columnIndex.get("documentation")-1);
//
//
//
//				if(elementStandardLabel.get(elementname)==null){
//					elementStandardLabel.put(elementname, new String[]{standardlabel,lineCount.toString()});
//				}else{
//					String standardlabelTC=elementStandardLabel.get(elementname)[0];
//					if(!standardlabel.equals(standardlabelTC)){
//						//do something, report errors
//						utilities.printResult("inconsitent standard label detected.Line number:"+lineCount+";First appearance is at line "+elementStandardLabel.get(elementname)[1]);
//
//					}
//				}
//				if(elementDoc.get(elementname)==null){
//					elementDoc.put(elementname, new String[]{CSVdoc,lineCount.toString()});
//				}else{
//
//					String docTC=elementDoc.get(elementname)[0];
//					if(!docTC.equals(CSVdoc)){
//						utilities.printResult(">>>>>inconsitent documentation detected. Line number:"+lineCount+"; First appearacne is at Line "+elementDoc.get(elementname)[1]);
//					}
//				}
//
//
//				if(elementroleLabel.get(elementname+preferredlabelrole)==null){
//					elementroleLabel.put(elementname+preferredlabelrole, new String[]{preferredlabel,lineCount.toString()});
//				}else{
//					String preferredlabelTC=elementroleLabel.get(elementname+preferredlabelrole)[0];
//					if(!preferredlabelTC.equals(preferredlabel)){
//						utilities.printResult(">>>>>inconsitent preferred label detected. Line number:"+lineCount+"; First appearacne is at Line "+elementroleLabel.get(elementname+preferredlabelrole)[1]);
//					}
//				}
//
//
//
//			}
//			line = dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//	}
//
//	public static void checkLabelDuplicateCharles() throws IOException{
//		//charles
//
//		//utilities.printResult("check if the function is running");
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//
//		//utilities.printResult(line);
//		lineCount++;
//
//		HashMap<String, String[]> tong= new HashMap<String,String[]>();
//		String line=dr.readLine();
//
//		while(line!= null){
//			//utilities.printResult(line);
//			Boolean roletypeDetected=isELR(line);
//			if(!roletypeDetected){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//
//				if(tong.get(standardlabel)==null){
//					tong.put(standardlabel, new String[]{elementname,lineCount.toString()});
//				}else{
//					String elementTC=tong.get(standardlabel)[0];
//					if(!elementname.equals(elementTC)){
//						//do something, report errors
//						utilities.printResult("duplicate standard label detected.Line number:"+lineCount+";First appearance is at line "+tong.get(standardlabel)[1]);
//
//					}
//				}
//
//			}
//
//			line=dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//
//
//	}
//
//	public static void checkStandardLabelCharles() throws IOException{
//		//charles
//
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//		lineCount++;
//
//
//		HashMap<String, String[]> tong = new HashMap<String,String[]>();
//
//
//		String line = dr.readLine();
//		while(line!= null){
//
//			Boolean roletypeDetected=isELR(line);
//			if(!roletypeDetected){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//
//				if(tong.get(standardlabel)==null){
//					tong.put(standardlabel, new String[]{elementname,lineCount.toString()});
//				}else{
//					String elementnameTC=tong.get(standardlabel)[0];
//					if(!elementname.equals(elementnameTC)){
//						//do something, report errors
//						utilities.printResult("inconsitent standard label detected.Line number:"+lineCount+";First appearance is at line "+tong.get(standardlabel)[1]);
//
//					}
//				}
//			}
//
//			line = dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//
//
//
//
//
//	public static void checkAbstractCharles() throws IOException{
//		//charles
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//		lineCount++;
//
//
//
//		Boolean Flag=false;
//		String line=dr.readLine();
//		while(line!= null){
//
//			if(Flag){
//				//do something to check standard label and presentation label
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//				String preferredlabel=lineSplit.get(columnIndex.get("preferredlabel")-1);
//
//				//utilities.printResult(standardlabel);
//				//utilities.printResult(preferredlabel);
//
//				if(!standardlabel.equals(preferredlabel)){
//
//					utilities.printResult("Elr inconsistency Pre and Std detected. Line number:"+lineCount);
//
//				}
//
//				Flag=false;
//			}
//
//			Boolean elrDetected=isELR(line);
//			if(elrDetected){
//				Flag=true;
//
//			}
//			line = dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//
//
//	}
//
//	public static void checkElrDuplicateCharles() throws IOException{
//		//charles
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//
//		lineCount++;
//		String line=dr.readLine();
//
//		ArrayList<String[]>idList=new ArrayList<String[]>();
//		ArrayList<String[]>narritivelist=new ArrayList<String[]>();
//
//		while(line!= null){
//			//utilities.printResult(line);
//			Boolean roletypeDetected=isELR(line);
//			if(roletypeDetected){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//				String elr=lineSplit.get(0);
//
//				//utilities.printResult(elr);
//
//
//
//				 String[] nameArray=elr.split("-");
//
//				 String ID=nameArray[0];
//
//				 //utilities.printResult(idList);
//
//
//				 if(idList.size()==0){
//					 idList.add(new String[]{ID,lineCount.toString()});
//				 }else{
//					 for(int i=0;i<idList.size();i++){
//						 String idTC=idList.get(i)[0];
//						 if(idTC.equals(ID)){
//								utilities.printResult("duplicate Elr ID detected.Line number:"+lineCount+";first appearance: "+idList.get(i)[1]);
//								break;
//						 }else{
//							 idList.add(new String[]{ID,lineCount.toString()});
//							 break;
//						 }
//					 }
//
//				 }
//
//
//				 //utilities.printResult(idList);
//
//
//
//				 //utilities.printResult(line);
//				 String narritive=nameArray[2];
//
//				 //utilities.printResult(narritive);
//
//				 for(int i=0;i<narritivelist.size();i++){
//					 String narritiveTC=narritivelist.get(i)[2];
//					 if(narritiveTC.equals(narritive)){
//							utilities.printResult("duplicate Elr narritive detected.Line number:"+lineCount+";first appearance: "+narritivelist.get(i)[2]);
//
//						 break;
//					 }else{
//						 narritivelist.add(new String[]{narritive,lineCount.toString()});
//						 break;
//					 }
//				 }
//
//			}
//
//
//			line=dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//	}
//
//	public static void checkChinese(int lineCount,String columnName,String columnContent) throws IOException{
//
//		if(utilities.isChinese(columnContent)){
//			utilities.printResult("chinese character detected in "+columnName+". Line number:"+lineCount);
//			checkFlag=false;
//		}
//
//	}
//
//	public static void checkSpace(int lineCount,String columnName,String columnContent) throws IOException{
//
//		if(columnContent.contains("[")){
//			int charIndex=columnContent.indexOf("[");
//
//			if((charIndex-3)<0){
//				utilities.printResult("space issue detected in "+columnName+". Line number:"+lineCount);
//				checkFlag=false;
//			}
//
//			if((charIndex-3)>0 && columnContent.substring(charIndex-2, charIndex-1).equals(" ")&&!columnContent.substring(charIndex-3, charIndex-1).equals("  ")){
//				utilities.printResult("space issue detected in "+columnName+". Line number:"+lineCount);
//				checkFlag=false;
//			}
//		}
//
//		if(columnContent.startsWith(" ") || columnContent.endsWith(" ") || columnContent.contains("  ")){
//			utilities.printResult("space issue detected in "+columnName+". Line number:"+lineCount);
//			checkFlag=false;
//		}
//
//	}
//
//
//
//	public static void checkElementlist() throws Exception{
//
//		checkCsvfile();
//		checkLabelConsistencyCharles();
//		//utilities.printResult("charles run finished -1");
//		checkLabelDuplicateCharles();
//		//utilities.printResult("charles run finished -2");
//		checkStandardLabelCharles();
//		//utilities.printResult("charles run finished -3");
//		checkAbstractCharles();
//		//utilities.printResult("charles run finished -4");
//		checkElrDuplicateCharles();
//		//utilities.printResult("charles run finished -5");
//
//
//
//
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		Integer lineCount=1;
//		dr.readLine();//skip header
//		lineCount++;
//
//
//		String line = dr.readLine();
//		while(line!= null){
//			Boolean elrDetected=isELR(line);
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			if(!elrDetected){
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String prefix=lineSplit.get(columnIndex.get("prefix")-1);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//				String preferredlabel=lineSplit.get(columnIndex.get("preferredlabel")-1);
//				String documentation=lineSplit.get(columnIndex.get("documentation")-1);
//				String preferredlabelrole=lineSplit.get(columnIndex.get("preferredlabelrole")-1);
//
//				//check element name
//				if(prefix.equals(base)){checkElementName(lineCount,standardlabel,elementname);}
//
//				//check gaap
//				if(prefix.equals(base)){checkGaap(lineCount,elementname);}
//
//				//check element attribute
//				HashMap<String,String> elementAttribute=new HashMap<String,String>();
//				String type=lineSplit.get(columnIndex.get("type")-1);
//				String substitutionGroup=lineSplit.get(columnIndex.get("SubstitutionGroup")-1);
//				String balance=lineSplit.get(columnIndex.get("Balance")-1);
//				String periodType=lineSplit.get(columnIndex.get("PeriodType")-1);
//				String abstractAttr=lineSplit.get(columnIndex.get("Abstract")-1);
//				elementAttribute.put("type",type);
//				elementAttribute.put("substitutionGroup",substitutionGroup);
//				elementAttribute.put("balance",balance);
//				elementAttribute.put("periodType",periodType);
//				elementAttribute.put("abstract",abstractAttr.toLowerCase());
//				if(prefix.equals(base)){checkElementAttribute(lineCount,elementname,standardlabel,elementAttribute);}
//
//				checkCase(lineCount,standardlabel);
//				checkChinese(lineCount,"preferredlabel",preferredlabel);
//				checkChinese(lineCount,"documentation",documentation);
//				checkChinese(lineCount,"standardlabel",standardlabel);
//				checkSpace(lineCount,"preferredlabel",preferredlabel);
//				checkSpace(lineCount,"documentation",documentation);
//				checkSpace(lineCount,"standardlabel",standardlabel);
//
//			}else{
//				String elr=lineSplit.get(0);
//				checkSpace(lineCount,"elr",elr);
//			}
//
//			line = dr.readLine();
//			lineCount++;
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generatePresentationFile() throws Exception{
//
//		utilities.printResult("presentation file being generated.");
//
//		List<Element> schemaElements= utilities.getSchemaElements(xbrlFiles);
//
//		//Set Input & Output
//		String presentationfile=base+"-"+date+"_pre.xml";
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("link:linkbase");
//        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        root.addAttribute("xsi:schemaLocation", "http://www.xbrl.org/2003/linkbase http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd");
//        root.addNamespace("link", "http://www.xbrl.org/2003/linkbase");
//        root.addNamespace("xbrli", "http://www.xbrl.org/2003/instance");
//        root.addNamespace("xlink", "http://www.w3.org/1999/xlink");
//
//        //construct roleref
//        FileInputStream f = new FileInputStream("elementlist.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();
//		String line=dr.readLine();
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//			if(roletypeDetected){
//				String roleURI=getRoleuriIdDefinition(line)[0];
//				String id=getRoleuriIdDefinition(line)[1];
//				Element roleRef=root.addElement("link:roleRef");
//				roleRef.addAttribute("roleURI", roleURI);
//		        roleRef.addAttribute("xlink:type","simple");
//		        roleRef.addAttribute("xlink:href", base+"-"+date+".xsd#"+id);
//
//			}
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//        //construct presentationlink
//		Element presentationLink = null;
//
//		f = new FileInputStream(elementlistfile);
//		dr = new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//
//		HashMap<String,ArrayList<String>> processedElementLabels=new HashMap<String,ArrayList<String>>();
//
//		line = dr.readLine();
//		int locCount=1;
//		int abstractChildrenOrderCount=1;
//		int tableChildrenOrderCount=1;
//		int axisChildrenOrderCount=1;
//		int domainChildrenOrderCount=1;
//		int lineitemsChildrenOrderCount=1;
//		Element locAbstract = null;
//		Element locTable = null;
//		Element locAxis = null;
//		Element locDomain = null;
//		Element locLineitems = null;
//		Boolean notableDetected=false;
//		Boolean lineitemsChildrenFlag=false;
//
//
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			if(roletypeDetected){
//
//
//				String roleURI=getRoleuriIdDefinition(line)[0];
//		        presentationLink=root.addElement("link:presentationLink");
//		        presentationLink.addAttribute("xlink:type", "extended");
//		        presentationLink.addAttribute("xlink:role", roleURI);
//		        //reset
//				locCount=1;
//				abstractChildrenOrderCount=1;
//				tableChildrenOrderCount=1;
//				axisChildrenOrderCount=1;
//				domainChildrenOrderCount=1;
//				lineitemsChildrenOrderCount=1;
//				locAbstract = null;
//				locTable = null;
//				locAxis = null;
//				locDomain = null;
//				locLineitems = null;
//				lineitemsChildrenFlag=false;
//				notableDetected=false;
//				processedElementLabels.clear();
//
//
//			}
//
//			if(!roletypeDetected){
//
//				String prefix=lineSplit.get(columnIndex.get("prefix")-1).trim();
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String elementid=prefix+"_"+elementname;
//				String type=lineSplit.get(columnIndex.get("elementname")-1);
//				String SubstitutionGroup=lineSplit.get(columnIndex.get("SubstitutionGroup")-1);
//				String preferredlabelrole=lineSplit.get(columnIndex.get("preferredlabelrole")-1);
//
//				ArrayList<String> elementLabels=new ArrayList<String>();
//				if(processedElementLabels.get(elementname)!=null){
//					elementLabels=processedElementLabels.get(elementname);
//				}
//
//				Element loc=presentationLink.addElement("link:loc");
//
//				String elementlabel="";
//				if(elementLabels.size()==0){
//					elementlabel=elementname;
//				}else{
//					elementlabel=elementname+"_"+elementLabels.size();
//				}
//
//				loc.addAttribute("xlink:type", "locator");
//				loc.addAttribute("xlink:href", prefixtransfer.get(prefix)+"#"+prefix+"_"+elementname);
//				loc.addAttribute("xlink:label", elementlabel);
//				loc.addAttribute("xlink:title", elementlabel);
//
//				elementLabels.add(preferredlabelrole);
//				processedElementLabels.remove(elementname);
//				processedElementLabels.put(elementname,elementLabels);
//
//				//Abstract
//				if(locCount==1){
//					locAbstract=loc;
//
//				}
//
//
//				//NoTable detected
//				if(locCount==2 && !utilities.isTable(schemaElements, elementid)){
//					notableDetected=true;
//				}
//
//				if(notableDetected){
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locAbstract.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(abstractChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        abstractChildrenOrderCount++;
//				}
//
//				//Table
//				if(utilities.isTable(schemaElements, elementid)){
//					locTable=loc;
//					//utilities.printResult("table debug:"+loc);
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locAbstract.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(abstractChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        abstractChildrenOrderCount++;
//			    }
//
//				//Axis
//				if(utilities.isAxis(schemaElements, elementid)){
//					locAxis=loc;
//					//utilities.printResult("debug axis:"+loc);
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locTable.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(tableChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        tableChildrenOrderCount++;
//				}
//
//				//Domain
//				if(utilities.isDomain(schemaElements, xbrlFiles, elementid)){
//					locDomain=loc;
//
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locAxis.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(axisChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        axisChildrenOrderCount++;
//				}
//
//				//Member
//				if(utilities.isMember(schemaElements, xbrlFiles, elementid)){
//
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel="";
//			        String order="";
//			        if(locDomain!=null){
//			        	fromlabel= locDomain.attributeValue("xlink:label");
//			        	order=String.valueOf(domainChildrenOrderCount)+".0";
//			        	domainChildrenOrderCount++;
//			        }else{
//			        	fromlabel= locAxis.attributeValue("xlink:label");
//			        	order=String.valueOf(axisChildrenOrderCount)+".0";
//			        	axisChildrenOrderCount++;
//			        }
//
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", order);
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//
//				}
//
//				//LineItems children
//				if(lineitemsChildrenFlag){
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locLineitems.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(lineitemsChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        lineitemsChildrenOrderCount++;
//			     }
//
//				//lineitems
//				if(utilities.isLineitems(elementid)){
//					locLineitems=loc;
//			        Element presentationArc=presentationLink.addElement("link:presentationArc");
//			        String fromlabel= locTable.attributeValue("xlink:label");
//			        String title="From "+fromlabel+" to " + elementlabel;
//			        presentationArc.addAttribute("xlink:type", "arc");
//			        presentationArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/parent-child");
//			        presentationArc.addAttribute("xlink:from", fromlabel);
//			        presentationArc.addAttribute("xlink:to", elementlabel);
//			        presentationArc.addAttribute("xlink:title", title);
//			        presentationArc.addAttribute("use", "optional");
//			        presentationArc.addAttribute("priority", "0");
//			        presentationArc.addAttribute("order", String.valueOf(tableChildrenOrderCount)+".0");
//			        presentationArc.addAttribute("preferredLabel", labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        lineitemsChildrenFlag=true;
//			        tableChildrenOrderCount++;
//				}
//
//				locCount++;
//
//			}
//
//			line = dr.readLine();
//		}
//
//		dr.close();
//		f.close();
//
//
//
//
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(presentationfile)), format);
//        writer.write(document);
//        writer.close();
//
//        utilities.printResult("Presentation file generated.");
//	}
//
//	public static void generateDefinitionFileFromPresentation() throws Exception{
//
//		utilities.printResult("definiton file being generated.");
//
//		List<Element> schemaElements= utilities.getSchemaElements(xbrlFiles);
//
//		//Set Input & Output
//
//		String presentationfile=base+"-"+date+"_pre.xml";
//		String definitionfile=base+"-"+date+"_def.xml";
//
//		//parsing presentation
//		Element prexmlroot= utilities.loadxml(presentationfile);
//		List<Element> rolerefList=prexmlroot.elements("roleRef");
//		List<Element> presentationLinks=prexmlroot.elements("presentationLink");
//
//		//Construct defintion
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("link:linkbase");
//        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        root.addAttribute("xsi:schemaLocation", "http://www.xbrl.org/2003/linkbase http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd");
//        root.addNamespace("link", "http://www.xbrl.org/2003/linkbase");
//        root.addNamespace("xbrli", "http://www.xbrl.org/2003/instance");
//        root.addNamespace("xlink", "http://www.w3.org/1999/xlink");
//        root.addNamespace("p0", "http://xbrl.org/2005/xbrldt");
//
//        //construct roleref based on presentation---pending
//		for(int i=0;i<presentationLinks.size();i++){
//			Element presentationLink=presentationLinks.get(i);
//			List<Element> presentationLocs=presentationLink.elements("loc");
//			Boolean rolerefGenerated=false;
//			for(int jj=0;jj<presentationLocs.size();jj++){
//				Element locElement=presentationLocs.get(jj);
//				String elementid=locElement.attributeValue("href").split("#")[1];
//				String elementSG=utilities.getElementAttribute(schemaElements, elementid).get("sg");
//
//				if(utilities.isTable(schemaElements, elementid)&&!rolerefGenerated){
//					//table detected, generate roleref
//					String roleURI=presentationLink.attributeValue("role");
//
//					for(int mm=0;mm<rolerefList.size();mm++){
//						if(rolerefList.get(mm).attributeValue("roleURI").equals(roleURI)){
//							Element roleRef=root.addElement("link:roleRef");
//							roleRef.addAttribute("roleURI", rolerefList.get(mm).attributeValue("roleURI"));
//					        roleRef.addAttribute("xlink:type",rolerefList.get(mm).attributeValue("type"));
//					        roleRef.addAttribute("xlink:href", rolerefList.get(mm).attributeValue("href"));
//						}
//					}
//					rolerefGenerated=true;
//				}
//			}
//		}
//
//		//construct arcroleref
//		setArcroleref(root);
//
//		//construct definitionlink based on presentation---pending
//		Element definitionLink = null;
//		for(int i=0;i<presentationLinks.size();i++){
//			Element presentationLink=presentationLinks.get(i);
//			List<Element> presentationLocs=presentationLink.elements("loc");
//			List<Element> presentationArcs=presentationLink.elements("presentationArc");
//
//			HashMap<String,String> prelabelHref=new HashMap<String,String>();
//			Boolean definitionlinkGenerated=false;
//			for(int jj=0;jj<presentationLocs.size();jj++){
//				Element locElement=presentationLocs.get(jj);
//				String elementid=locElement.attributeValue("href").split("#")[1];
//				if(utilities.isTable(schemaElements, elementid)&&!definitionlinkGenerated){
//
//					//table detected, generate definitionLink
//					String roleURI=presentationLink.attributeValue("role");
//
//			        definitionLink=root.addElement("link:definitionLink");
//			        definitionLink.addAttribute("xlink:type", "extended");
//			        definitionLink.addAttribute("xlink:role", roleURI);
//
//
//					Integer lineitemsOrder=2;//initialize the lineitemsorder count
//					//sort the presentation arcs
//					ArrayList<Element> presentationArcsSort=new ArrayList<Element>();
//					for(int km=0;km<presentationArcs.size();km++){
//						Element presentationArcInsert=presentationArcs.get(km);
//						Float order=Float.valueOf(presentationArcInsert.attributeValue("order"));
//						Integer insertIndex=presentationArcsSort.size();
//						if(presentationArcsSort.size()==0){
//							presentationArcsSort.add(presentationArcInsert);
//						}else{
//							for(int kn=0;kn<presentationArcsSort.size();kn++){
//								Float orderTC=Float.valueOf(presentationArcsSort.get(kn).attributeValue("order"));
//								if(order<=orderTC){
//									insertIndex=kn;break;
//								}
//							}
//							presentationArcsSort.add(insertIndex,presentationArcInsert);
//						}
//					}
//
//			        //construct locs
//			        ArrayList<String> hrefList=new ArrayList<String>();
//			        String abstractLabel="";
//			        for(int g=0;g<presentationLocs.size();g++){
//			        	String locLabel=presentationLocs.get(g).attributeValue("label");
//			        	String locHref=presentationLocs.get(g).attributeValue("href");
//			        	String elementId=locHref.split("#")[1];
//
//			        	Boolean hasFather=false;
//			        	for(int h=0;h<presentationArcs.size();h++){
//			        		if(presentationArcs.get(h).attributeValue("to").equals(locLabel)){
//			        			hasFather=true;
//			        		}
//			        	}
//
//			        	if(hasFather){
//			        		Boolean duplicateFound=false;
//			        		for(int k=0;k<hrefList.size();k++){
//			        			if(presentationLocs.get(g).attributeValue("href").equals(hrefList.get(k))){
//			        				duplicateFound=true;
//			        			}
//			        		}
//			        		//duplicate member keeped
//			        		if(!duplicateFound || utilities.isMember(schemaElements, xbrlFiles, elementId)){
//								Element loc=definitionLink.addElement("link:loc");
//								loc.addAttribute("xlink:type", "locator");
//								loc.addAttribute("xlink:href", presentationLocs.get(g).attributeValue("href"));
//								loc.addAttribute("xlink:label", presentationLocs.get(g).attributeValue("label"));
//								loc.addAttribute("xlink:title", presentationLocs.get(g).attributeValue("title"));
//
//								if(utilities.isDomain(schemaElements,xbrlFiles,elementId)){
//									//construct another domain for domain-default arc
//									loc=definitionLink.addElement("link:loc");
//									loc.addAttribute("xlink:type", "locator");
//									loc.addAttribute("xlink:href", presentationLocs.get(g).attributeValue("href"));
//									loc.addAttribute("xlink:label", presentationLocs.get(g).attributeValue("label")+"_1");
//									loc.addAttribute("xlink:title", presentationLocs.get(g).attributeValue("title")+"_1");
//					        	}
//
//								hrefList.add(presentationLocs.get(g).attributeValue("href"));
//			        		}
//
//			        	}else{
//			        		abstractLabel=locLabel;
//			        	}
//			        }
//
//
//			        presentationArcs=presentationArcsSort;//use sorted arcs for validation
//			        //construct Arcs
//					HashMap<String,String> loclabelHref=new HashMap<String,String>();
//					for(int xx=0;xx<presentationLocs.size();xx++){
//						Element prelocElement=presentationLocs.get(xx);
//						loclabelHref.put(prelocElement.attributeValue("label"),prelocElement.attributeValue("href").split("#")[1]);
//					}
//
//			        ArrayList<String> fromtoList=new ArrayList<String>();
//			        for(int g=0;g<presentationArcs.size();g++){
//			        	String fromLabel=presentationArcs.get(g).attributeValue("from");
//			        	String toLabel=presentationArcs.get(g).attributeValue("to");
//			        	String order=presentationArcs.get(g).attributeValue("order");
//			        	String fromElement=loclabelHref.get(fromLabel);
//			        	String toElement=loclabelHref.get(toLabel);
//
//			        	if(!fromLabel.equals(abstractLabel)){
//			        		Boolean fromtoduplciateFound=false;
//			        		for(int xk=0;xk<fromtoList.size();xk++){
//			        			if(fromtoList.get(xk).equals(fromElement+toElement)){
//			        				fromtoduplciateFound=true;
//			        			}
//			        		}
//			        		if(!fromtoduplciateFound){
//
//			        			String arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//
//			        			//from is table, to is lineitems
//			        			if(utilities.isTable(schemaElements, fromElement) && utilities.isLineitems(toElement)){
//			        				String temp=fromLabel;
//			        				fromLabel=toLabel;
//			        				toLabel=temp;
//			        				order="1.0";
//			        				arcrole="http://xbrl.org/int/dim/arcrole/all";
//			        			}
//
//						        //toElement is axis
//						        if(utilities.isAxis(schemaElements, toElement)){
//						        	arcrole="http://xbrl.org/int/dim/arcrole/hypercube-dimension";
//						        }
//
//						        //toElement is domain
//						        if(utilities.isDomain(schemaElements, xbrlFiles, toElement)){
//						        	arcrole="http://xbrl.org/int/dim/arcrole/dimension-domain";
//						        }
//
//						        //toElement is member
//						        if(utilities.isMember(schemaElements, xbrlFiles, toElement)){
//						        	if(utilities.isAxis(schemaElements, fromElement)){
//						        		arcrole="http://xbrl.org/int/dim/arcrole/dimension-domain";
//						        	}else{
//						        		arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//						        	}
//
//						        }
//
//						        //fromElement is lineitems
//						        if(utilities.isLineitems(fromElement)){
//						        	order=String.valueOf(lineitemsOrder)+".0";
//						        	arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//						        	lineitemsOrder++;
//						        }
//
//
//						        if(arcrole.isEmpty()){
//						        	utilities.printResult("");
//						        }
//
//						        Element definitionArc=definitionLink.addElement("link:definitionArc");
//						        definitionArc.addAttribute("xlink:type", "arc");
//						        definitionArc.addAttribute("xlink:from", fromLabel);
//						        definitionArc.addAttribute("xlink:to", toLabel);
//						        definitionArc.addAttribute("xlink:title", "From "+fromLabel+" to "+toLabel);
//						        definitionArc.addAttribute("use", "optional");
//						        definitionArc.addAttribute("priority", "0");
//						        definitionArc.addAttribute("order", order);
//						        definitionArc.addAttribute("xlink:arcrole", arcrole);
//
//			        			//from is table, to is lineitems
//			        			if(utilities.isTable(schemaElements, fromElement) && utilities.isLineitems(toElement)){
//			    			        definitionArc.addAttribute("p0:contextElement", "segment");
//			    			        definitionArc.addAttribute("p0:closed", "true");
//			        			}
//
//						        //toElement is domain
//						        if(utilities.isDomain(schemaElements, xbrlFiles, toElement)){
//
//
//						        	order=String.valueOf(Float.valueOf(order)+1);
//
//						        	//domain
//						        	definitionArc=definitionLink.addElement("link:definitionArc");
//							        definitionArc.addAttribute("xlink:type", "arc");
//							        definitionArc.addAttribute("xlink:from", fromLabel);
//							        definitionArc.addAttribute("xlink:to", toLabel+"_1");
//							        definitionArc.addAttribute("xlink:title", "From "+fromLabel+" to "+toLabel+"_1");
//							        definitionArc.addAttribute("use", "optional");
//							        definitionArc.addAttribute("priority", "0");
//							        definitionArc.addAttribute("order", order);
//							        definitionArc.addAttribute("xlink:arcrole", "http://xbrl.org/int/dim/arcrole/dimension-default");
//						        }
//
//
//						        fromtoList.add(fromElement+toElement);
//			        		}
//
//			        	}
//
//			        }
//
//					definitionlinkGenerated=true;
//				}
//			}
//
//		}
//
//
//
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(definitionfile)), format);
//        writer.write(document);
//        writer.close();
//
//        utilities.printResult("Definition file generated.");
//	}
//
//	public static void generateLabelFile() throws Exception{
//
//		//Set Input & Output
//		String labelfile=base+"-"+date+"_lab.xml";
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("link:linkbase");
//        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        root.addAttribute("xsi:schemaLocation", "http://www.xbrl.org/2003/linkbase http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd");
//        root.addNamespace("link", "http://www.xbrl.org/2003/linkbase");
//        root.addNamespace("xbrli", "http://www.xbrl.org/2003/instance");
//        root.addNamespace("xlink", "http://www.w3.org/1999/xlink");
//
//        //construct roleref
//        setRoleref(root);
//
//        //construct labellink
//        Element labelLink=root.addElement("link:labelLink");
//        labelLink.addAttribute("xlink:type", "extended");
//        labelLink.addAttribute("xlink:role", "http://www.xbrl.org/2003/role/link");
//
//        //build loc, label, labelarc
//		FileInputStream f = new FileInputStream("elementlist.csv");
//		BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//		dr.readLine();
//
//		setPrefixTransfer();
//		setLabelTransfer();
//
//		ArrayList<String> processedElements=new ArrayList<String>();
//		HashMap<String,ArrayList<String>> processedElementLabels=new HashMap<String,ArrayList<String>>();
//		HashMap<String, String> elementStandardlabel=new HashMap<String,String>();
//		String line = dr.readLine();
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//
//			if(!roletypeDetected){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String prefix=lineSplit.get(columnIndex.get("prefix")-1).trim();
//				String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//				String preferredlabel=lineSplit.get(columnIndex.get("preferredlabel")-1);
//				String standardlabel=lineSplit.get(columnIndex.get("standardlabel")-1);
//				String documentation=lineSplit.get(columnIndex.get("documentation")-1);
//				String preferredlabelrole=lineSplit.get(columnIndex.get("preferredlabelrole")-1);
//
//				Boolean elementlabelprocessed=false;
//				ArrayList<String> processedLabels=new ArrayList<String>();
//
//				if(processedElementLabels.get(elementname)!=null){
//					processedLabels=processedElementLabels.get(elementname);
//					for(int mm=0;mm<processedLabels.size();mm++){
//						if(processedLabels.get(mm).equals(preferredlabelrole)){
//							elementlabelprocessed=true;
//						}
//					}
//				}
//
//				if(!elementlabelprocessed){
//					String id=prefix+"_"+elementname;
//
//					//construct loc
//					Boolean elementprocessed=false;
//					for(int nn=0;nn<processedElements.size();nn++){
//						if(processedElements.get(nn).equals(elementname)){
//							elementprocessed=true;
//						}
//					}
//					if(!elementprocessed){
//
//						Element loc=labelLink.addElement("link:loc");
//						loc.addAttribute("xlink:type", "locator");
//						loc.addAttribute("xlink:href", prefixtransfer.get(prefix)+"#"+id);
//						loc.addAttribute("xlink:label", elementname);
//						loc.addAttribute("xlink:title", elementname);
//						processedElements.add(elementname);
//
//						elementStandardlabel.put(elementname,standardlabel);
//
//						if(prefix.equals(base)){
//							//construct label documentation
//					        Element label=labelLink.addElement("link:label");
//
//					        String labelName="";
//					        if(processedLabels.size()==0){
//					        	labelName="label_"+elementname;
//					        }else{
//					        	labelName="label_"+elementname+"_"+processedLabels.size();
//					        }
//
//					        label.addAttribute("xlink:type", "resource");
//					        label.addAttribute("xlink:label", labelName);
//					        label.addAttribute("xlink:role","http://www.xbrl.org/2003/role/documentation");//pending
//					        label.addAttribute("xlink:title", labelName);
//					        label.addAttribute("xml:lang", "en-US");
//					        label.addAttribute("id", labelName);
//					        label.setText(documentation);///pending
//
//					        //construct labelArc documentation
//					        Element labelArc=labelLink.addElement("link:labelArc");
//					        String title="From "+elementname+" to "+labelName;
//					        labelArc.addAttribute("xlink:type", "arc");
//					        labelArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/concept-label");
//					        labelArc.addAttribute("xlink:from", elementname);
//					        labelArc.addAttribute("xlink:to", labelName);
//					        labelArc.addAttribute("xlink:title", title);
//					        labelArc.addAttribute("use", "optional");
//					        labelArc.addAttribute("priority", "0");
//					        labelArc.addAttribute("order", "1.0");
//
//					        //Record processing
//					        processedLabels.add(elementname+"documentation");
//						}
//					}
//
//					//construct label
//			        Element label=labelLink.addElement("link:label");
//			        String labelName="label_"+elementname+"_"+processedLabels.size();
//			        label.addAttribute("xlink:type", "resource");
//			        label.addAttribute("xlink:label", labelName);
//			        label.addAttribute("xlink:role",labeltransfer.get(preferredlabelrole)+preferredlabelrole);
//			        label.addAttribute("xlink:title", labelName);
//			        label.addAttribute("xml:lang", "en-US");
//			        label.addAttribute("id", labelName);
//			        if(preferredlabelrole.equals("label")){
//			        	label.setText(standardlabel);
//			        }else{
//			        	label.setText(preferredlabel);
//			        }
//
//
//			        //construct labelArc
//			        Element labelArc=labelLink.addElement("link:labelArc");
//			        String title="From "+elementname+" to "+labelName;
//			        labelArc.addAttribute("xlink:type", "arc");
//			        labelArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/concept-label");
//			        labelArc.addAttribute("xlink:from", elementname);
//			        labelArc.addAttribute("xlink:to", labelName);
//			        labelArc.addAttribute("xlink:title", title);
//			        labelArc.addAttribute("use", "optional");
//			        labelArc.addAttribute("priority", "0");
//			        labelArc.addAttribute("order", "1.0");
//
//			        //Record processing
//			        processedLabels.add(preferredlabelrole);
//			        processedElementLabels.remove(elementname);
//			        processedElementLabels.put(elementname,processedLabels);
//
//				}
//
//			}
//			line = dr.readLine();
//		}
//
//		//detect element where no label role is detected
//		Iterator iter = processedElementLabels.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			String elementname = (String) entry.getKey();
//			ArrayList<String> roles = (ArrayList<String>) entry.getValue();
//
//			Boolean standardroleAdded=false;
//			for(int kk=0;kk<roles.size();kk++){
//				if(roles.get(kk).equals("label")){
//					standardroleAdded=true;
//				}
//			}
//			if(!standardroleAdded){
//				//construct label
//		        Element label=labelLink.addElement("link:label");
//
//		        String labelName="";
//		        if(roles.size()==0){
//		        	labelName="label_"+elementname;
//		        }else{
//		        	labelName="label_"+elementname+"_"+roles.size();
//		        }
//
//		        label.addAttribute("xlink:type", "resource");
//		        label.addAttribute("xlink:label", labelName);
//		        label.addAttribute("xlink:role",labeltransfer.get("label")+"label");
//		        label.addAttribute("xlink:title", labelName);
//		        label.addAttribute("xml:lang", "en-US");
//		        label.addAttribute("id", labelName);
//		        label.setText(elementStandardlabel.get(elementname));//pending
//
//		        //construct labelArc
//		        Element labelArc=labelLink.addElement("link:labelArc");
//		        String title="From "+elementname+" to "+labelName;
//		        labelArc.addAttribute("xlink:type", "arc");
//		        labelArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/concept-label");
//		        labelArc.addAttribute("xlink:from", elementname);
//		        labelArc.addAttribute("xlink:to", labelName);
//		        labelArc.addAttribute("xlink:title", title);
//		        labelArc.addAttribute("use", "optional");
//		        labelArc.addAttribute("priority", "0");
//		        labelArc.addAttribute("order", "1.0");
//			}
//
//		}
//
//		dr.close();
//		f.close();
//
//
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(labelfile)), format);
//        writer.write(document);
//        writer.close();
//
//        utilities.printResult("Label file generated.");
//	}
//
//	public static void generateSchemaFile() throws Exception{
//
//		//Set Input & Output
//		String schemafile=base+"-"+date+".xsd";
//		String labelfile=base+"-"+date+"_lab.xml";
//		String presentationfile=base+"-"+date+"_pre.xml";
//		String definitionfile=base+"-"+date+"_def.xml";
//
//		//Construct schema
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("xsd:schema");
//        root.addAttribute("targetNamespace", targetNamespace);
//        root.addAttribute("elementFormDefault","qualified");
//        root.addNamespace(base,targetNamespace);
//        setNamespace(root);
//
//
//        //construct annotation
//        Element annotation=root.addElement("xsd:annotation");
//        Element appinfo=annotation.addElement("xsd:appinfo");
//
//        //build presentation linkbaseRef
//        Element definitionLink=appinfo.addElement("link:linkbaseRef");
//        definitionLink.addAttribute("xlink:type", "simple");
//        definitionLink.addAttribute("xlink:href", definitionfile);
//        definitionLink.addAttribute("xlink:role","http://www.xbrl.org/2003/role/definitionLinkbaseRef");
//        definitionLink.addAttribute("xlink:arcrole","http://www.w3.org/1999/xlink/properties/linkbase");
//
//        //build presentation linkbaseRef
//        Element presentationlink=appinfo.addElement("link:linkbaseRef");
//        presentationlink.addAttribute("xlink:type", "simple");
//        presentationlink.addAttribute("xlink:href", presentationfile);
//        presentationlink.addAttribute("xlink:role","http://www.xbrl.org/2003/role/presentationLinkbaseRef");
//        presentationlink.addAttribute("xlink:arcrole","http://www.w3.org/1999/xlink/properties/linkbase");
//
//        //build label linkbaseRef
//        Element labellink=appinfo.addElement("link:linkbaseRef");
//        labellink.addAttribute("xlink:type", "simple");
//        labellink.addAttribute("xlink:href", labelfile);
//        labellink.addAttribute("xlink:role","http://www.xbrl.org/2003/role/labelLinkbaseRef");
//        labellink.addAttribute("xlink:arcrole","http://www.w3.org/1999/xlink/properties/linkbase");
//
//
//
//        //build roleTypes
//		FileInputStream f = new FileInputStream(elementlistfile);
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();
//		String line=dr.readLine();
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//			if(roletypeDetected){
//				String roleURI=getRoleuriIdDefinition(line)[0];
//				String id=getRoleuriIdDefinition(line)[1];
//				String defintion=getRoleuriIdDefinition(line)[2];
//
//				String targetNamespacePrefix=targetNamespace.substring(0,targetNamespace.lastIndexOf("/"));
//
//				Element roleType=appinfo.addElement("link:roleType");
//				roleType.addAttribute("roleURI", roleURI);
//				roleType.addAttribute("id", id);
//				roleType.addElement("link:definition").addText(defintion);
//				roleType.addElement("link:usedOn").addText("link:presentationLink");
//				roleType.addElement("link:usedOn").addText("link:calculationLink");
//				roleType.addElement("link:usedOn").addText("link:definitionLink");
//			}
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//        //build imports
//		setImport(root);
//
//        //build elements
//		f = new FileInputStream(elementlistfile);
//		dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();
//
//
//		setTypeTransfer();
//		setSgTransfer();
//
//
//		ArrayList<String> processedElements=new ArrayList<String>();
//		line=dr.readLine();
//		while(line!= null){
//			Boolean roletypeDetected=isELR(line);
//
//			if(!roletypeDetected){
//
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String prefix=lineSplit.get(columnIndex.get("prefix")-1).trim();
//
//				if(prefix.equals(base)){
//
//					String elementname=lineSplit.get(columnIndex.get("elementname")-1);
//					Boolean elementprocessed=false;
//					for(int mm=0;mm<processedElements.size();mm++){
//						if(processedElements.get(mm).equals(elementname)){
//							elementprocessed=true;
//						}
//					}
//					if(!elementprocessed){
//
//						String type=lineSplit.get(columnIndex.get("type")-1);
//						String SubstitutionGroup=lineSplit.get(columnIndex.get("SubstitutionGroup")-1);
//						String Balance=lineSplit.get(columnIndex.get("Balance")-1);
//						String PeriodType=lineSplit.get(columnIndex.get("PeriodType")-1);
//						String Abstract=lineSplit.get(columnIndex.get("Abstract")-1);
//						String id=prefix+"_"+elementname;
//						Element element=root.addElement("xsd:element");
//						element.addAttribute("name", elementname);
//						element.addAttribute("id", id);
//						element.addAttribute("type", type);
//						element.addAttribute("substitutionGroup", SubstitutionGroup);
//						//element.addAttribute("type", typetransfer.get(type)+":"+type);
//						//element.addAttribute("substitutionGroup", sgtransfer.get(SubstitutionGroup)+":"+SubstitutionGroup);
//
//
//						element.addAttribute("abstract", Abstract.toLowerCase());
//						element.addAttribute("nillable", "true");
//						if(Balance.equals("debit") || Balance.equals("credit")){
//							element.addAttribute("xbrli:balance", Balance);
//						}
//						element.addAttribute("xbrli:periodType", PeriodType);
//						processedElements.add(elementname);
//					}
//				}
//			}
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(schemafile)), format);
//        writer.write(document);
//        writer.close();
//
//        utilities.printResult("Schema file generated.");
//	}
//
//	public static void setColumnindex(){
//
//		columnIndex.put("preferredlabel",4);
//		columnIndex.put("prefix",5);
//		columnIndex.put("elementname",6);
//		columnIndex.put("standardlabel",7);
//		columnIndex.put("type",8);
//		columnIndex.put("SubstitutionGroup",9);
//		columnIndex.put("Balance",10);
//		columnIndex.put("PeriodType",11);
//		columnIndex.put("Abstract",12);
//		columnIndex.put("documentation",13);
//		columnIndex.put("preferredlabelrole",14);
//
//	}
//
//	public static void setAllowLowcase() throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/AllowLowcase.txt");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		String line=dr.readLine();
//		String[] lineSplit=line.split(",");
//
//		for(int i=0;i<lineSplit.length;i++){
//			allowLowcase.add(lineSplit[i]);
//		}
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void setElementValidate() throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/elementValidate.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			String elementname=lineSplit.get(0);
//			HashMap<String,String> validateConfig=new HashMap<String,String>();
//
//			validateConfig.put("label",lineSplit.get(1));
//			validateConfig.put("type",lineSplit.get(2));
//			validateConfig.put("substitutionGroup",lineSplit.get(3));
//			validateConfig.put("abstract",lineSplit.get(6));
//
//			elementValidate.put(elementname, validateConfig);
//
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//
//	public static void setNamespace(Element root) throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/schemaNamespace.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//		    root.addNamespace(lineSplit.get(0), lineSplit.get(1));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void setImport(Element root) throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/schemaImport.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//            Element importElement=root.addElement("xsd:import");
//            importElement.addAttribute("namespace",lineSplit.get(0));
//            importElement.addAttribute("schemaLocation", lineSplit.get(1));
//
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void setArcroleref(Element root) throws Exception{
//		Element arcroleref;
//        FileInputStream f = new FileInputStream("generateConfig/definitionArcroleref.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			arcroleref=root.addElement("link:arcroleRef");
//			arcroleref.addAttribute("arcroleURI", lineSplit.get(0));
//			arcroleref.addAttribute("xlink:type", lineSplit.get(1));
//			arcroleref.addAttribute("xlink:href", lineSplit.get(2));
//
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void setRoleref(Element root) throws Exception{
//        Element roleref;
//        FileInputStream f = new FileInputStream("generateConfig/presentationRoleref.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//	        roleref=root.addElement("link:roleRef");
//	        roleref.addAttribute("roleURI", lineSplit.get(0));
//	        roleref.addAttribute("xlink:type",lineSplit.get(1));
//	        roleref.addAttribute("xlink:href",lineSplit.get(2));
//
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static void setSgTransfer() throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/substitutiongroupTransfer.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			sgtransfer.put(lineSplit.get(0), lineSplit.get(1));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//
//	}
//
//	public static void setTypeTransfer() throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/typeTransfer.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			typetransfer.put(lineSplit.get(0), lineSplit.get(1));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//
//	}
//
//	public static void setPrefixTransfer() throws Exception{
//		prefixtransfer.put(base,base+"-"+date+".xsd");
//
//        FileInputStream f = new FileInputStream("generateConfig/prefixTransfer.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			prefixtransfer.put(lineSplit.get(0), lineSplit.get(1));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//	public static void setLabelTransfer() throws Exception{
//
//        FileInputStream f = new FileInputStream("generateConfig/labelTransfer.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			labeltransfer.put(lineSplit.get(0), lineSplit.get(1));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//
//	}
//
//	public static Boolean isELR(String line){
//
//		Boolean roletypeDetected=true;
//		ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//		if(lineSplit.size()!=0){
//			String lineSegment=lineSplit.get(0).replace("\"", "");
//			if(lineSegment.length()<5){
//				roletypeDetected=false;
//			}else{
//				roletypeDetected=lineSegment.substring(0,5).matches("\\d{5}");
//			}
//		}
//		return roletypeDetected;
//	}
//
//	public static String[] getRoleuriIdDefinition(String line){
//		ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//		String definition=lineSplit.get(0);
//
//		String defintionFirstCut=definition.substring(definition.indexOf("-")+1, definition.length());
//		String defintionSecondCut=defintionFirstCut.substring(defintionFirstCut.indexOf("-")+1, defintionFirstCut.length());
//		String idRaw=defintionSecondCut.replace("/", "").replace("."," ").replace("  "," ").replace("-","").replace("(", "").replace(")", "").replace(",", "").replace("'","").trim();
//		String[] idSegments=idRaw.split(" ");
//		String id="";
//
//		for(int k=0;k<idSegments.length;k++){
//			String idSegment=idSegments[k];
//			if(idSegment.length()>=1){
//				String idSegmentFirst=idSegment.substring(0, 1).toUpperCase();
//				String idSegmentLeft=idSegment.substring(1, idSegment.length()).toLowerCase();
//				id=id+idSegmentFirst+idSegmentLeft;
//			}
//		}
//		String targetNamespacePrefix=targetNamespace.substring(0,targetNamespace.lastIndexOf("/"));
//		String roleURI=targetNamespacePrefix+"/role/"+id;
//
//		return new String[]{roleURI,id,definition};
//	}
//
//
//
//
//	public static void checkCase(int lineCount,String standardlabel) throws IOException{
//
//		String[] textSplit=standardlabel.split(" ");
//		for(int kk=0;kk<textSplit.length;kk++){
//			String segment=textSplit[kk].replaceAll("[^0-9a-zA-Z]","");
//			if(!segment.isEmpty() && !utilities.find(allowLowcase, segment)){
//				String firstChar=segment.substring(0,1);
//				String firstCharTC=segment.substring(0,1).toUpperCase();
//				if(!firstChar.equals(firstCharTC)){
//					utilities.printResult("standard label case error detected. line number:"+lineCount);
//					checkFlag=false;
//				}
//			}
//		}
//	}
//
//}
