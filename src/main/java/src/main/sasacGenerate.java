//package src.main;
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
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//public class sasacGenerate {
//
//	public static HashMap<String,String> typeDecimal=new HashMap<String,String>();
//	public static HashMap<String,String> typeWeight=new HashMap<String,String>();
//	public static HashMap<String,String> typeelementUnit=new HashMap<String,String>();
//	public static HashMap<String,String[]> typevalueTransform=new HashMap<String,String[]>();
//	public static HashMap<String,String> elementidType=new HashMap<String,String>();
//	public static HashMap<String,String[]> tablecolMapping=new HashMap<String,String[]>();
//	public static String typeDecimalCfg,typeelementUnitlCfg,typeValuetransferCfg;
//	public static String contextCurrent="";
//	public static ArrayList<String> tableList=new ArrayList<String>();
//	public static HashMap<String,String> elementcontextValue=new HashMap<String,String>();
//
//
//	public static void main(String[] args) throws Exception {
//
//		utilities.setFolder(System.getProperty("user.dir"));
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//		utilities.printResult("************************");
//		utilities.printResult("Report Time: "+df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
//
//		initPara();
//
//		//create document root
//		utilities.printResult("Start generating instance file.");
//
//		//Construct instance
//		String instancefilebefore="standard_instance.xml";
//		String instancefileafter="generated_instance.xml";
//
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(instancefilebefore));
//	    Element root=document.getRootElement();
//
//
//		//construct nontuple elements
//
//		generateNontuple(root);
//		generateTuple(root);
//
//
//		utilities.printResult("Finish generating instance file.");
//	    utilities.printResult("Validation Results:");
//
//		//validate generated instance
//		validateDuplicate(root);
//		removeEmptyCells(root);
//		validateFiles();
//
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileOutputStream(instancefileafter), format);
//	    writer.write(document);
//	    writer.close();
//
//	}
//
//
//	public static void validateFiles() throws IOException{
//
//		ArrayList<String> talbeListClean=(ArrayList<String>) utilities.removeDuplicate(tableList);
//
//		ArrayList<String> csvFiles=new ArrayList<String>();
//		ArrayList<String> csvFilesFullname=new ArrayList<String>();
//		utilities.getAllfiles(".\\sasacCsv",csvFilesFullname);
//
//
//		for(int i=0;i<csvFilesFullname.size();i++){
//			csvFiles.add(csvFilesFullname.get(i).split("\\\\")[csvFilesFullname.get(i).split("\\\\").length-1].split("\\.")[0]);
//		}
//
//		for(int i=0;i<talbeListClean.size();i++){
//			if(!utilities.find(csvFiles, talbeListClean.get(i))){
//				utilities.printResult("No csv found for "+ talbeListClean.get(i));
//			}
//		}
//
//		for(int i=0;i<csvFiles.size();i++){
//			if(!utilities.find(talbeListClean, csvFiles.get(i))){
//				utilities.printResult("No mapping found for "+ csvFiles.get(i));
//			}
//		}
//
//
//
//	}
//
//	public static void validateDuplicate(Element root) throws IOException{
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
//				//if(elementID.equals("sasacAnnualAverageNumberOfInserviceEmployeesFrom_2014_01_01_To_2014_12_31")) utilities.printResult("target detected");
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
//
//	public static int countNonEmptyElements(Element root) throws IOException{
//		List<Element> childElements=root.elements();
//		int Count=0;
//		for(int i=0;i<childElements.size();i++){
//			Element currentElement=childElements.get(i);
//			String elementValue=currentElement.getText();
//
//			if(!elementValue.equals("#EmptyCell#")){
//				Count++;
//			}
//		}
//		return Count;
//	}
//
//	public static void removeEmptyCells(Element root) throws IOException{
//		ArrayList<Element> instanceElements=new ArrayList<Element>();
//		utilities.getAllElements(root, instanceElements);
//
//		for(int i=0;i<instanceElements.size();i++){
//			Element currentElement=instanceElements.get(i);
//			if(currentElement.attribute("contextRef")!=null){
//				String elementValue=currentElement.getText();
//				if(elementValue.equals("#EmptyCell#")){
//					//System.out.println("code invoked");
//					currentElement.getParent().remove(currentElement);
//				}
//			}
//		}
//	}
//
//
//	public static void initPara() throws Exception{
//
//		utilities.printResult("Initializing parameters");
//	    //initial cfg parameters
//		setTypeDecimal();
//		//utilities.printResult("TypeDecimal size: "+typeDecimal.size());
//		setTypeelementUnit();
//		//utilities.printResult("TypeelementUnit size: "+typeelementUnit.size());
//		setTypeWeight();
//		//utilities.printResult("TypeWeight size: "+typeWeight.size());
//		setTypeValuetransfer();
//		//utilities.printResult("Typevaluetransfer size: "+typevalueTransform.size());
//		setElementType();
//		//utilities.printResult("elementType size: "+elementidType.size());
//		setTuplemapping();
//		//utilities.printResult("tupleMapping size: "+ tablecolMapping.size());
//		utilities.printResult("Parameters initialized");
//	}
//
//	public static void generateTuple(Element root) throws Exception{
//
//
//
//			FileInputStream f = new FileInputStream("sasacCfg/tuple_elment_mapping_general.csv");
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			dr.readLine();//skip the first line
//			String line=dr.readLine();
//			while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String tablename=lineSplit.get(0);
//				tableList.add(tablename);
//				int rowstartno=Integer.parseInt(lineSplit.get(1));
//				int colstartno=Integer.parseInt(lineSplit.get(2));
//				String totalsign=lineSplit.get(3);
//
//				String[] subtotallist;
//				if(lineSplit.get(4).isEmpty()){
//					subtotallist=new String[]{};
//				}
//				else{
//					subtotallist=lineSplit.get(4).split("#");
//				}
//				String subtotaltuple=lineSplit.get(5);
//				String detailtuple=lineSplit.get(6);
//				utilities.printResult("generating tuple table:" + tablename);
//				generateTupleTable(root,tablename,rowstartno,colstartno,totalsign,subtotallist,subtotaltuple,detailtuple);
//
//				line = dr.readLine();
//			}
//			dr.close();
//			f.close();
//
//	}
//
//
//
//	public static void generateTupleTable(Element root, String tableName, int rowstartno, int colstartno, String totalsign, String[] subtotallist, String subtotaltuple, String detailtuple) throws IOException, ParseException{
//
//		File file = new File("sasacCsv/"+tableName+".csv");
//
//		if(file.exists()){
//
//		Element subTuple = null;
//
//        FileInputStream f = new FileInputStream("sasacCsv/"+tableName+".csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		int rowCount=1;
//		String line=dr.readLine();
//		//System.out.println(line);
//		while(line!= null){
//			if(rowCount>=rowstartno){
//				//utilities.printResult(rowCount);
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//				//identify the nature of the row
//				String identifierValue=lineSplit.get(colstartno-1).replace(" ", "");
//				identifierValue=identifierValue.replace("��", "");
//				//System.out.println(identifierValue);
//
//				//if(!identifierValue.isEmpty()){
//
//					//utilities.printResult(identifierValue);
//					if(identifierValue.equals(totalsign) && !totalsign.isEmpty()){
//						//utilities.printResult("total sign detected");
//						for(int colCount=colstartno-1;colCount<lineSplit.size();colCount++){
//							String tablecol=tableName+(colCount+1);
//							if(tablecolMapping.get(tablecol)!=null){
//								String elementName=tablecolMapping.get(tablecol)[0];
//								String secondelementName=tablecolMapping.get(tablecol)[3];
//								String totalElementSign=tablecolMapping.get(tablecol)[4];
//								String subtotalElementSign=tablecolMapping.get(tablecol)[5];
//								String detailElementSign=tablecolMapping.get(tablecol)[6];
//								String context=tablecolMapping.get(tablecol)[7];
//								String secondcontext=tablecolMapping.get(tablecol)[8];
//								if(!elementName.isEmpty()){
//
//									generateElement(root,elementName,secondelementName,lineSplit.get(colCount),totalElementSign,context);
//								}
//							}
//						}
//					}else if(utilities.find(subtotallist, identifierValue)){
//
//						//utilities.printResult("subtotal detected");
//						//utilities.printResult(subtotallist.length);
//
//						if(subTuple!=null && countNonEmptyElements(subTuple)<2){
//							root.remove(subTuple);
//						}
//
//						subTuple=root.addElement(subtotaltuple);
//						for(int colCount=colstartno-1;colCount<lineSplit.size();colCount++){
//							String tablecol=tableName+(colCount+1);
//							if(tablecolMapping.get(tablecol)!=null){
//								String elementName=tablecolMapping.get(tablecol)[1];
//								String secondelementName=tablecolMapping.get(tablecol)[3];
//								String totalElementSign=tablecolMapping.get(tablecol)[4];
//								String subtotalElementSign=tablecolMapping.get(tablecol)[5];
//								String detailElementSign=tablecolMapping.get(tablecol)[6];
//								String context=tablecolMapping.get(tablecol)[7];
//								String secondcontext=tablecolMapping.get(tablecol)[8];
//								if(!elementName.isEmpty()){
//									generateElement(subTuple,elementName,secondelementName,lineSplit.get(colCount),subtotalElementSign,context);
//								}
//							}
//						}
//					}else{
//						//utilities.printResult("detail detected");
//						Element detailTuple;
//						if(subTuple==null){
//							detailTuple=root.addElement(detailtuple);
//						}else{
//							detailTuple=subTuple.addElement(detailtuple);
//						}
//
//						for(int colCount=colstartno-1;colCount<lineSplit.size();colCount++){
//
//							String tablecol=tableName+(colCount+1);
//							if(tablecolMapping.get(tablecol)!=null){
//								String elementName=tablecolMapping.get(tablecol)[2];
//								String secondelementName=tablecolMapping.get(tablecol)[3];
//								String totalElementSign=tablecolMapping.get(tablecol)[4];
//								String subtotalElementSign=tablecolMapping.get(tablecol)[5];
//								String detailElementSign=tablecolMapping.get(tablecol)[6];
//								String context=tablecolMapping.get(tablecol)[7];
//								String secondcontext=tablecolMapping.get(tablecol)[8];
//								if(!secondcontext.isEmpty()){
//									if((rowCount-rowstartno)%2!=0){context=secondcontext;}
//								}
//								if(!elementName.isEmpty()){
//										//if(context.isEmpty()) utilities.printResult("context empty");
//										generateElement(detailTuple,elementName,secondelementName,lineSplit.get(colCount),detailElementSign,context);
//								}
//							}
//						}
//
//
//						//utilities.printResult(String.valueOf(detailTuple.elements().size()));
//						//utilities.printResult(String.valueOf(countNonEmptyElements(detailTuple)));
//
//						if(countNonEmptyElements(detailTuple) <2){
//							if(subTuple==null){
//								root.remove(detailTuple);
//							}else{
//								subTuple.remove(detailTuple);
//							}
//						}
//				}
//
//			//}
//			}
//
//
//
//			line = dr.readLine();
//			rowCount++;
//		}
//
//
//
//
//		if(subTuple!=null && countNonEmptyElements(subTuple)<2){
//			root.remove(subTuple);
//		}
//
//		dr.close();
//		f.close();
//
//		}
//
//	}
//
//	public static void generateElement(Element root,String elementName, String secondelementname, String originalValue,String sign,String context) throws IOException, ParseException{
//
//		String elementId=elementName.split(":")[0]+"_"+elementName.split(":")[1];
//		String elementType=elementidType.get(elementId);
//
//		String decimals;
//		if(typeDecimal.get(elementType+elementId)!=null){
//			decimals=typeDecimal.get(elementType+elementId);
//			//System.out.println(decimals);
//		}else{
//			decimals=typeDecimal.get(elementType);
//		}
//
//		String unit;
//		if(typeelementUnit.get(elementType+elementId)!=null){
//			unit=typeelementUnit.get(elementType+elementId);
//		}else{
//			unit=typeelementUnit.get(elementType);
//		}
//
//		String weight;
//		if(typeWeight.get(elementType+elementId)!=null){
//			weight=typeWeight.get(elementType+elementId);
//			//System.out.println(weight);
//		}else{
//			weight=typeWeight.get(elementType);
//		}
//
//		String value;
//
//		if(typevalueTransform.get(elementType+originalValue)!=null && !typevalueTransform.get(elementType+originalValue)[1].equals("Y")){
//			value=typevalueTransform.get(elementType+originalValue)[0];
//			//utilities.printResult(value);
//		}else if(!secondelementname.isEmpty()){
//			value=getTypeDefaultValue(elementType);
//			//generate second element
//			generateElement(root,secondelementname,"",originalValue,sign,context);
//		}else{
//			value=originalValue;
//		}
//
//
//
//		//if(value!=null && !value.isEmpty() && !value.equals("����") && !value.equals("--")){//skip empty cell & invalid cells
//
//		if(value!=null && !value.equals("����") && !value.equals("--")){// invalid cells
//
//
//
//			Element dataElement=root.addElement(elementName);
//
//			if(value.isEmpty()){
//
//				value="#EmptyCell#";
//
//			}else{
//
//				if(decimals!=null && !decimals.isEmpty()){
//
//					double weightDouble=1;
//
//					if(weight!=null){
//						weightDouble=Double.parseDouble(weight);
//						//System.out.println(elementName+":"+weightDouble);
//					}
//
//					value=value.replace(",", "");
//					dataElement.addAttribute("decimals", decimals);
//					if(!decimals.equals("INF")){
//						if(sign.equals("-1") && Double.parseDouble(value)!=0){
//							value=String.format("%."+decimals+"f", -Double.parseDouble(value)*weightDouble);
//						}else{
//							value=String.format("%."+decimals+"f", Double.parseDouble(value)*weightDouble);
//						}
//					}
//				}
//
//				if(elementType.equals("xbrli:dateItemType")){
//					 //System.out.println(value);
//					 SimpleDateFormat formatterSlash = new SimpleDateFormat("yyyy/MM/dd");
//					 SimpleDateFormat formatterDash = new SimpleDateFormat("yyyy-MM-dd");
//					 if(value.contains("/")){
//						 value=formatterDash.format(formatterSlash.parse(value));
//					 }
//					 if(value.contains("-")){
//						 value=formatterDash.format(formatterDash.parse(value));
//					 }
//					//convert date
//				}
//			}
//
//			if(unit!=null && !unit.isEmpty()) dataElement.addAttribute("unitRef", unit);
//
//			dataElement.addAttribute("contextRef", context);
//
//			dataElement.setText(value.trim());
//
//		}
//	}
//
//	public static String getTypeDefaultValue(String elementType) throws IOException{
//        FileInputStream f = new FileInputStream("sasacCfg/value_transform.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			if(elementType.equals(lineSplit.get(0)) && lineSplit.get(3).equals("Y")){
//				return lineSplit.get(2);
//			}
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//		return "";
//	}
//
//	public static void generateNontuple(Element root) throws Exception{
//		 String currentTable="";
//		 FileInputStream f = new FileInputStream("sasacCfg/non-tuple_elment_mapping.csv");
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			dr.readLine();//skip the first line
//			String line=dr.readLine();
//			while(line!= null){
//				ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//				String tableName=lineSplit.get(0);
//				tableList.add(tableName);
//				int rowNo=Integer.parseInt(lineSplit.get(1));
//				int columnNo=Integer.parseInt(lineSplit.get(2));
//				String originalValue=getNontupleValue(tableName,rowNo,columnNo);
//				String elementName=lineSplit.get(3);
//				String sign=lineSplit.get(4);
//				String context=lineSplit.get(5);
//				if(!tableName.equals(currentTable)){
//					utilities.printResult("generating nontuple table: "+tableName);
//					currentTable=tableName;
//				}
//				//utilities.printResult("generating nontuple table: "+tableName+". Row: "+rowNo+". Col: "+columnNo);
//
//				if(!originalValue.equals("#FileNotFound#")){
//					generateElement(root,elementName,"",originalValue,sign,context);
//				}
//
//				line = dr.readLine();
//			}
//			dr.close();
//			f.close();
//	}
//
//	public static String getNontupleValue(String tableName,int rowNo, int colNo) throws Exception{
//
//		File file = new File("sasacCsv/"+tableName+".csv");
//
//			if(file.exists()){
//
//	        FileInputStream f = new FileInputStream("sasacCsv/"+tableName+".csv");
//			BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//			int rowCount=1;
//			String line=dr.readLine();
//			while(line!= null){
//				if(rowCount==rowNo){
//					ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//					return lineSplit.get(colNo-1);
//				}
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
//	public static void setTypeDecimal() throws Exception{
//        FileInputStream f = new FileInputStream("sasacCfg/type-element-decimal_conf.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			typeDecimal.put(lineSplit.get(0)+lineSplit.get(1),lineSplit.get(2));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//	public static void setTypeWeight() throws Exception{
//        FileInputStream f = new FileInputStream("sasacCfg/type-element-weight_conf.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			typeWeight.put(lineSplit.get(0)+lineSplit.get(1),lineSplit.get(2));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//
//
//	public static void setTypeelementUnit() throws Exception{
//        FileInputStream f = new FileInputStream("sasacCfg/type-element-unit_conf.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			typeelementUnit.put(lineSplit.get(0)+lineSplit.get(1), lineSplit.get(2));
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//	public static void setTypeValuetransfer() throws Exception{
//        FileInputStream f = new FileInputStream("sasacCfg/value_transform.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			typevalueTransform.put(lineSplit.get(0)+lineSplit.get(1), new String[]{lineSplit.get(2),lineSplit.get(3)});
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//
//	public static void setElementType() throws Exception{
//
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(".\\sasacTaxonomy",taxonomyFiles);
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
//	public static void setTuplemapping() throws IOException{
//		int lineCount=1;
//        FileInputStream f = new FileInputStream("sasacCfg/tuple_elment_mapping_detail.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip the first line
//		String line=dr.readLine();
//		while(line!= null){
//			lineCount++;
//			//System.out.println(lineCount);
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			tablecolMapping.put(lineSplit.get(0)+lineSplit.get(1), new String[]{lineSplit.get(2),lineSplit.get(3),lineSplit.get(4),lineSplit.get(5),lineSplit.get(6),lineSplit.get(7),lineSplit.get(8),lineSplit.get(9),lineSplit.get(10)});
//			line = dr.readLine();
//		}
//		dr.close();
//		f.close();
//	}
//
//}
