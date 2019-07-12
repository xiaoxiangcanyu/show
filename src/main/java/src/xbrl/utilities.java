//package src.xbrl;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.xml.sax.InputSource;
//import org.xml.sax.Locator;
//import org.xml.sax.helpers.LocatorImpl;
//import xmlparse.DocumentFactoryWithLocator;
//import xmlparse.SAXReaderEnhanced;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class utilities {
//
//	public static String folder;
//	public static void setFolder(String folderName){
//		folder=folderName;
//	}
//
//	 //���ַ����з�������
//    public static Date parseDate(String dateStr) throws ParseException {
//        Date date=null;
//        String[] dateArray = dateStr.split("\\D+");     //+��ֹ����������ַ���һ��ʱ���½�������
//        int dateLen = dateArray.length;
//        int dateStrLen=dateStr.length();
//        if(dateLen>0){
//                String fDateStr="";
//                for(int i=0;i<dateLen;i++){
//                    //�����Ƿ�ֹʮλ��ʡ�Ե����
//                	if(dateArray[i].length()<2){
//                		fDateStr+=leftPad(dateArray[i],"0",2);
//                    }else{
//                    	fDateStr+=dateArray[i];
//                    }
//                }
//                date=parseDate(fDateStr,"yyyyMMdd");
//        }
//
//        return date;
//    }
//
//    //���ո����ĸ�ʽ���ַ�����ʽ������
//    public static String formatDate(Date date, String formatStr) {
//        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
//        return sdf.format(date);
//    }
//
//    //����
//    public static String leftPad(String str,String pad,int len){
//        String newStr=(str==null?"":str);
//        while(newStr.length()<len){
//            newStr=pad+newStr;
//        }
//        if(newStr.length()>len){
//            newStr=newStr.substring(newStr.length()-len);
//        }
//        return newStr;
//    }
//
//    //���ո����ĸ�ʽ���ַ�����������
//    public static Date parseDate(String dateStr, String formatStr) throws ParseException {
//        Date date = null;
//        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
//        date = sdf.parse(dateStr);
//        return date;
//    }
//
//	 public static void downloadFile(String remoteFilePath, String localFilePath)
//	    {
//	        URL urlfile = null;
//	        HttpURLConnection httpUrl = null;
//	        BufferedInputStream bis = null;
//	        BufferedOutputStream bos = null;
//	        File f = new File(localFilePath);
//	        try
//	        {
//	            urlfile = new URL(remoteFilePath);
//	            httpUrl = (HttpURLConnection)urlfile.openConnection();
//	            httpUrl.connect();
//	            bis = new BufferedInputStream(httpUrl.getInputStream());
//	            bos = new BufferedOutputStream(new FileOutputStream(f));
//	            int len = 2048;
//	            byte[] b = new byte[len];
//	            while ((len = bis.read(b)) != -1)
//	            {
//	                bos.write(b, 0, len);
//	            }
//	            bos.flush();
//	            bis.close();
//	            httpUrl.disconnect();
//	        }
//	        catch (Exception e)
//	        {
//	            e.printStackTrace();
//	        }
//	        finally
//	        {
//	            try
//	            {
//	                bis.close();
//	                bos.close();
//	            }
//	            catch (IOException e)
//	            {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//    private static boolean isChinese(char c) {
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean isNumeric(String str){
//    	  for (int i = 0; i < str.length(); i++){
//    	   //System.out.println(str.charAt(i));
//    	   if (!Character.isDigit(str.charAt(i))){
//    	    return false;
//    	   }
//    	  }
//    	  return true;
//    	 }
//
//    public static boolean isChinese(String strName) {
//        char[] ch = strName.toCharArray();
//        for (int i = 0; i < ch.length; i++) {
//            char c = ch[i];
//            if (isChinese(c)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//	public static boolean find(ArrayList<String> list, String target) {
//
//		Boolean targetFound=false;
//
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).equals(target)){
//				targetFound=true;
//			}
//		}
//
//	    return targetFound;
//	}
//
//
//	public static boolean find(String[] list, String target) {
//
//		Boolean targetFound=false;
//
//		for(int i=0;i<list.length;i++){
//			if(list[i].equals(target)){
//				targetFound=true;
//			}
//		}
//
//	    return targetFound;
//	}
//
//	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
//
//		if((a==null && b!=null) || (a!=null && b==null)){
//			return false;
//		}
//
//		if(a.size() != b.size())
//	        return false;
//	    Collections.sort(a);
//	    Collections.sort(b);
//	    for(int i=0;i<a.size();i++){
//	        if(!a.get(i).equals(b.get(i)))
//	            return false;
//	    }
//	    return true;
//	}
//
//	public static List removeDuplicate(List list) {
//
//		if(list==null){
//
//			return null;
//
//		}
//
//
//		Set set = new HashSet();
//        List newList = new ArrayList();
//        for (Iterator iter = list.iterator(); iter.hasNext();) {
//            Object element = iter.next();
//            if (set.add(element)){
//                newList.add(element);
//            }
//        }
//
//        return newList;
//    }
//
//
//	public static void cleanReport() throws IOException{
//
//		File f = new File(folder+"\\Report.txt");
//		FileWriter fw =  new FileWriter(f);
//		fw.write("");
//		fw.close();
//	}
//
//	public static void cleanReportSimple() throws IOException{
//
//		File f = new File(folder+"\\non-tuple_element_mapping.csv");
//		FileWriter fw =  new FileWriter(f);
//		fw.write("");
//		fw.close();
//	}
//
//
//
//	public static void printResult(String result) throws IOException{
//
//		if(!result.isEmpty()){
//			System.out.print(result+"\n");
//
//			File file = new File(folder+"\\Report.txt");
//			if(!file.exists()){
//			   file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(result+"\r\n");
//			bw.close();
//			fw.close();
//
//		}
//
//	}
//
//	public static void printResultSimple(String result) throws IOException{
//
//		if(!result.isEmpty()){
//			//System.out.print(result+"\n");
//
//			File file = new File(folder+"\\non-tuple_element_mapping.csv");
//			if(!file.exists()){
//			   file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(result+"\r\n");
//			bw.close();
//			fw.close();
//
//		}
//
//	}
//
//
//	public static Element loadxml(String filename) throws DocumentException{
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(filename));
//	    Element root=document.getRootElement();
//	    return root;
//	}
//
//	public static Element parseXML(String xmlString) throws Exception{
//
//		StringReader read = new StringReader(xmlString);
//		InputSource source = new InputSource(read);
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//		SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//		Document document = saxReader.read(source);
//	    Element root=document.getRootElement();
//	    return root;
//
//	}
//
//	public static void getAllElements(Element element, ArrayList allElements){
//
//		//utilities.getAllElements(root,allElements);
//        List elementList = element.elements();
//        for(Iterator iterator =  elementList.iterator(); iterator.hasNext();){
//            Element childElement = (Element)iterator.next();
//            allElements.add(childElement);
//            getAllElements(childElement,allElements);
//        }
//    }
//
//	public static ArrayList<String> getNumericType(){
//
//		ArrayList<String> numericTypes=new ArrayList<String>();
//
//		numericTypes.add("xbrli:monetaryItemType");
//		numericTypes.add("xbrli:sharesItemType");
//		numericTypes.add("xbrli:pureItemType");
//		numericTypes.add("xbrli:fractionItemType");
//
//		numericTypes.add("xbrli:integerItemType");
//		numericTypes.add("xbrli:nonPositiveIntegerItemType");
//		numericTypes.add("xbrli:negativeIntegerItemType");
//		numericTypes.add("xbrli:longItemType");
//		numericTypes.add("xbrli:intItemType");
//		numericTypes.add("xbrli:shortItemType");
//		numericTypes.add("xbrli:byteItemType");
//		numericTypes.add("xbrli:nonNegativeIntegerItemType");
//		numericTypes.add("xbrli:unsignedLongItemType");
//		numericTypes.add("xbrli:unsignedIntItemType");
//		numericTypes.add("xbrli:unsignedShortItemType");
//		numericTypes.add("xbrli:unsignedByteItemType");
//		numericTypes.add("xbrli:positiveIntegerItemType");
//
//		numericTypes.add("xbrli:decimalItemType");
//		numericTypes.add("xbrli:floatItemType");
//		numericTypes.add("xbrli:doubleItemType");
//
//		numericTypes.add("num:percentItemType");
//		numericTypes.add("num:perShareItemType");
//		numericTypes.add("num:areaItemType");
//		numericTypes.add("num:volumeItemType");
//		numericTypes.add("num:massItemType");
//		numericTypes.add("num:weightItemType");
//		numericTypes.add("num:energyItemType");
//		numericTypes.add("num:powerItemType");
//		numericTypes.add("num:lengthItemType");
//		numericTypes.add("num:memoryItemType");
//
//		return numericTypes;
//	}
//
//
//	public static void getAllChildren(HashMap<String,ArrayList<String>> arcMap,String parent,ArrayList<String> childrenList){
//
//        ArrayList<String> currentChildren=new ArrayList<String>();
//
//        currentChildren=arcMap.get(parent);
//        if(currentChildren!=null)
//        {
//			for(int xx=0;xx<currentChildren.size();xx++){
//				childrenList.add(currentChildren.get(xx));
//				getAllChildren(arcMap,currentChildren.get(xx),childrenList);
//			}
//        }
//
//	}
//
//
//	public static HashMap<String,ArrayList<String>> getArcMap(List<Element> arcElements,HashMap<String,String> labelHref){
//
//        HashMap<String,ArrayList<String>> arcMap=new HashMap<String,ArrayList<String>>();
//		for(int xx=0;xx<arcElements.size();xx++){
//			Element arcElement=arcElements.get(xx);
//			String fromLabel=arcElement.attributeValue("from");
//			String fromElement=labelHref.get(fromLabel);
//			String toLabel=arcElement.attributeValue("to");
//			String toElement=labelHref.get(toLabel);
//			ArrayList<String> toElementList=new ArrayList<String>();
//			if(arcMap.get(fromElement)!=null){
//				toElementList=arcMap.get(fromElement);
//				//remove duplicate
//				if(!utilities.find(toElementList, toElement)){
//					toElementList.add(toElement);
//				}
//			}else{
//				toElementList.add(toElement);
//			}
//
//			arcMap.remove(fromElement);
//			arcMap.put(fromElement,toElementList);
//		}
//		return arcMap;
//
//	}
//
//	public static boolean checkArcMap(HashMap<String,ArrayList<String>> arcMap, String rootElement, String elementTC){
//
//
//		if(rootElement.isEmpty() || elementTC.isEmpty()){
//			Iterator iter = arcMap.entrySet().iterator();
//			while (iter.hasNext()) {
//				Map.Entry entry = (Map.Entry) iter.next();
//				rootElement = (String) entry.getKey();
//				ArrayList<String> elementList=(ArrayList<String>) entry.getValue();
//				for(int i=0;i<elementList.size();i++){
//					elementTC=elementList.get(i);
//					if(elementTC.equals(rootElement)){
//						return false;
//					}else{
//						if(!utilities.checkArcMap(arcMap, rootElement,elementTC)) return false;
//					}
//				}
//			}
//		}else{
//			ArrayList<String> elementList=arcMap.get(elementTC);
//			if(elementList!=null){
//				for(int i=0;i<elementList.size();i++){
//					String elementChildrenTC=elementList.get(i);
//					if(elementChildrenTC.equals(rootElement)){
//						return false;
//					}else{
//						if(!utilities.checkArcMap(arcMap, rootElement,elementChildrenTC)) return false;
//					}
//				}
//			}
//		}
//
//		return true;
//	}
//
//
//	public static void getChildofArcMap(ArrayList<String> childrenList, HashMap<String,ArrayList<String>> arcMap, String rootElement, String elementChild){
//
//
//		if(elementChild.isEmpty()){
//			ArrayList<String> elementList=arcMap.get(rootElement);
//			childrenList.addAll(elementList);
//			for(int i=0;i<elementList.size();i++){
//				elementChild=elementList.get(i);
//				utilities.getChildofArcMap(childrenList, arcMap, rootElement, elementChild);
//			}
//		}else{
//			ArrayList<String> elementList=arcMap.get(elementChild);
//			if(elementList!=null){
//				childrenList.addAll(elementList);
//				for(int i=0;i<elementList.size();i++){
//					String elementGrandChild=elementList.get(i);
//					utilities.getChildofArcMap(childrenList, arcMap, rootElement, elementGrandChild);
//				}
//			}
//		}
//
//	}
//
//	public static ArrayList<String> getParentofArcMap(HashMap<String,ArrayList<String>> arcMap) throws Exception{
//		ArrayList<String> parentList=new ArrayList<String>();
//		Iterator iter = arcMap.entrySet().iterator();
//
//			while (iter.hasNext()) {
//				Map.Entry entry = (Map.Entry) iter.next();
//				String elementid = (String) entry.getKey();
//				parentList.add(elementid);
//			}
//
//
//		return parentList;
//	}
//
//
//	public static HashMap<String,String> getLabeHref(List<Element> locElements){
//
//        HashMap<String,String> labelHref=new HashMap<String,String>();
//		for(int xx=0;xx<locElements.size();xx++){
//			Element locElement=locElements.get(xx);
//			labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//		}
//		return labelHref;
//
//	}
//
//	public static HashMap<String,String> getLabeRole(List<Element> labelElements){
//
//        HashMap<String,String> labelRole=new HashMap<String,String>();
//		for(int xx=0;xx<labelElements.size();xx++){
//			Element labelElement=labelElements.get(xx);
//			labelRole.put(labelElement.attributeValue("label"),labelElement.attributeValue("role"));
//		}
//		return labelRole;
//
//	}
//
//	public static HashMap<String,String> getLabeLang(List<Element> labelElements){
//
//        HashMap<String,String> labelLang=new HashMap<String,String>();
//		for(int xx=0;xx<labelElements.size();xx++){
//			Element labelElement=labelElements.get(xx);
//			labelLang.put(labelElement.attributeValue("label"),labelElement.attributeValue("lang"));
//		}
//		return labelLang;
//
//	}
//
//	public static HashMap<String,String> getLabeText(List<Element> labelElements){
//
//        HashMap<String,String> labelText=new HashMap<String,String>();
//		for(int xx=0;xx<labelElements.size();xx++){
//			Element labelElement=labelElements.get(xx);
//			labelText.put(labelElement.attributeValue("label"),labelElement.getText());
//		}
//		return labelText;
//
//	}
//
//
//	public static boolean isLineitems(String elementid) throws Exception{
//
//		return elementid.matches("(.*)LineItems");
//	}
//
//	public static boolean isTable(List<Element> schemaElements, String elementid) throws Exception{
//
//		String elementSG=utilities.getElementAttribute(schemaElements, elementid).get("sg");
//
//		if(elementSG.equals("xbrldt:hypercubeItem")){
//
//			return true;
//		}
//
//		return false;
//	}
//
//	public static boolean isAxis(List<Element> schemaElements, String elementid) throws Exception{
//
//		String elementSG=utilities.getElementAttribute(schemaElements, elementid).get("sg");
//
//		if(elementSG.equals("xbrldt:dimensionItem")){
//
//			return true;
//		}
//
//		return false;
//	}
//
//
//	public static boolean isDomain(List<Element> schemaElements, HashMap<String,String> xbrlFiles, String elementid) throws Exception{
//
//		String elementType=utilities.getElementAttribute(schemaElements, elementid).get("type");
//
//		String elementStandardlabel=utilities.getStandardlabel(xbrlFiles, elementid);
//
//		//System.out.println(elementType+","+elementStandardlabel);
//
//		if(elementType.equals("nonnum:domainItemType") && elementStandardlabel.matches("(.*)\\[Domain\\]")){
//
//			return true;
//		}
//
//		return false;
//	}
//
//	public static boolean isMember(List<Element> schemaElements, HashMap<String,String> xbrlFiles, String elementid) throws Exception{
//
//		String elementType=utilities.getElementAttribute(schemaElements, elementid).get("type");
//
//		String elementStandardlabel=utilities.getStandardlabel(xbrlFiles, elementid);
//
//		//System.out.println(elementType+","+elementStandardlabel);
//
//		if(elementType.equals("nonnum:domainItemType") && !elementStandardlabel.matches("(.*)\\[Domain\\]")){
//
//			return true;
//		}
//
//		return false;
//	}
//
//	public static String getStandardlabel(HashMap<String,String> xbrlFiles, String elementid) throws Exception{
//
//		String standardlabel="";
//		String labxmlfilename=xbrlFiles.get("labxml");
//		Element labxmlroot= utilities.loadxml(labxmlfilename);
//		List<Element> labelLinks=labxmlroot.elements("labelLink");
//		for(int i=0;i<labelLinks.size();i++){
//			Element labelLink=labelLinks.get(i);
//			List<Element> labelLocs=labelLink.elements("loc");
//			List<Element> labelLabels=labelLink.elements("label");
//			List<Element> labelArcs=labelLink.elements("labelArc");
//
//			HashMap<String,String> labelHref=new HashMap<String,String>();
//			for(int jj=0;jj<labelLocs.size();jj++){
//				Element locElement=labelLocs.get(jj);
//				labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//			}
//
//			HashMap<String,String> labelRole=new HashMap<String,String>();
//			HashMap<String,String> labelText=new HashMap<String,String>();
//			for(int jj=0;jj<labelLabels.size();jj++){
//				Element locElement=labelLabels.get(jj);
//				labelRole.put(locElement.attributeValue("label"),locElement.attributeValue("role"));
//				labelText.put(locElement.attributeValue("label"),locElement.getText());
//			}
//
//			for(int j=0;j<labelArcs.size();j++){
//				Element labelArc=labelArcs.get(j);
//				String from=labelArc.attributeValue("from");
//				String to=labelArc.attributeValue("to");
//
//				String fromElement=labelHref.get(from);
//				String toRole=labelRole.get(to);
//				String labeltext=labelText.get(to);
//
//				if(fromElement.equals(elementid) && toRole.equals("http://www.xbrl.org/2003/role/label")){
//					standardlabel=labeltext;
//				}
//			}
//		}
//
//		return standardlabel;
//	}
//
//	public static List<Element> getSchemaElements(HashMap<String,String> xbrlFiles) throws DocumentException{
//
//		List<Element> schemaElements=new ArrayList<Element>();
//
//		//parsing xbrl schema file
//		String xbrlschemafilename=xbrlFiles.get("schema");
//		Element xbrlschemaroot= utilities.loadxml(xbrlschemafilename);
//		List<Element> xbrlElements=xbrlschemaroot.elements("element");
//		schemaElements.addAll(xbrlElements);
//		//System.out.println(xbrlschemaroot.elements().size());
//
//		//parsing taxonomy files
//		String taxonomyFolder=System.getProperty("user.dir")+"\\taxonomy";
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(taxonomyFolder,taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			String filename=taxonomyFiles.get(i);
//			Element root= utilities.loadxml(filename);
//			if(root.getName().equals("schema")){
//				List<Element> allElements=root.elements("element");
//				schemaElements.addAll(allElements);
//			}
//		}
//
//		return schemaElements;
//
//	}
//
//	public static List<Element> getSchemaElements() throws DocumentException{
//
//		List<Element> schemaElements=new ArrayList<Element>();
//
//		//parsing taxonomy files
//		String taxonomyFolder=System.getProperty("user.dir")+"\\taxonomy";
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(taxonomyFolder,taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			String filename=taxonomyFiles.get(i);
//			Element root= utilities.loadxml(filename);
//			if(root.getName().equals("schema")){
//				List<Element> allElements=root.elements("element");
//				schemaElements.addAll(allElements);
//			}
//		}
//
//		return schemaElements;
//
//	}
//
//	//get element type and substitutionGroup
//	public static HashMap<String,String> getElementAttribute(List<Element> schemaElements,String elementid) throws DocumentException{
//
//		HashMap<String,String> elementAttribute=new HashMap<String,String>();
//
//		String elementSG="";
//		String elementPT="";
//		String elementType="";
//		String elementABS="";
//
//		//parsing type schema file
//		for(int m=0;m<schemaElements.size();m++){
//			Element schemaElement=schemaElements.get(m);
//
//			if(schemaElement.attributeValue("id")!=null){
//				if(schemaElement.attributeValue("id").equals(elementid)){
//
//					elementSG=schemaElement.attributeValue("substitutionGroup");
//					elementType=schemaElement.attributeValue("type");
//					elementPT=schemaElement.attributeValue("periodType");
//					elementABS=schemaElement.attributeValue("abstract");
//				}
//			}
//		}
//		elementAttribute.put("type", elementType);
//		elementAttribute.put("sg", elementSG);
//		elementAttribute.put("pt", elementPT);
//		elementAttribute.put("abs", elementABS);
//
//		return elementAttribute;
//
//	}
//
//
//
//	public static ArrayList<String> getDeprecatedElements(HashMap<String,String> xbrlFiles) throws DocumentException{
//
//		ArrayList<String> deprecatedElements=new ArrayList<String>();
//
//		//parsing taxonomy files
//		String xbrlschemafilename=xbrlFiles.get("schema");
//		String taxonomyFolder=System.getProperty("user.dir")+"\\taxonomy";
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(taxonomyFolder,taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			String filename=taxonomyFiles.get(i);
//			Element root= utilities.loadxml(filename);
//			if(root.getName().equals("linkbase")){
//				List<Element> presentationLinks=root.elements("presentationLink");
//				for(int j=0;j<presentationLinks.size();j++){
//					Element presentationLink=presentationLinks.get(j);
//
//					List<Element> locElements=presentationLink.elements("loc");
//					HashMap<String,String> labelHref=new HashMap<String,String>();
//					for(int mm=0;mm<locElements.size();mm++){
//						Element locElement=locElements.get(mm);
//						labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//					}
//
//					List<Element> presentationArcs=presentationLink.elements("presentationArc");
//					for(int k=0;k<presentationArcs.size();k++){
//						Element presentationArc=presentationArcs.get(k);
//						String from=presentationArc.attributeValue("from");
//						String to=presentationArc.attributeValue("to");
//						String toHref=labelHref.get(to);
//						if(from.matches("(.*)Deprecated(.*)")){
//							deprecatedElements.add(toHref);
//						}
//
//					}
//				}
//			}
//		}
//
//		return deprecatedElements;
//
//	}
//
//
//	public static ArrayList<String> getRoleTypeElements(HashMap<String,String> xbrlFiles) throws DocumentException{
//
//		ArrayList<String> roletypeElements=new ArrayList<String>();
//
//		//parsing taxonomy files
//		String xbrlschemafilename=xbrlFiles.get("schema");
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
//				if(elementCurrent.getName().equals("roleType")){
//					roletypeElements.add(elementCurrent.attributeValue("id"));
//				}
//			}
//		}
//
//		return roletypeElements;
//
//	}
//
//
//	public static ArrayList<String> getArcRoleTypeElements(HashMap<String,String> xbrlFiles) throws DocumentException{
//
//		ArrayList<String> arcroletypeElements=new ArrayList<String>();
//
//		//parsing taxonomy files
//		String xbrlschemafilename=xbrlFiles.get("schema");
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
//				if(elementCurrent.getName().equals("arcroleType")){
//					arcroletypeElements.add(elementCurrent.attributeValue("id"));
//				}
//			}
//		}
//
//		return arcroletypeElements;
//
//	}
//
//	public static String findFile(HashMap<String,String> xbrlFiles, String filename){
//		String file="";
//
//		//System.out.println(filename);
//		//parsing taxonomy files
//		String xbrlschemafilename=xbrlFiles.get("schema");
//		String taxonomyFolder=System.getProperty("user.dir")+"\\taxonomy";
//		ArrayList<String> taxonomyFiles=new ArrayList<String>();
//		utilities.getAllfiles(taxonomyFolder,taxonomyFiles);
//
//		for(int i=0;i<taxonomyFiles.size();i++){
//			if(taxonomyFiles.get(i).endsWith(filename)){
//
//				file=taxonomyFiles.get(i);
//			}
//		}
//
//
//		return file;
//	}
//
//	public static  String getAllfiles(String path, ArrayList<String> files){
//		File rootDir = new File(path);
//		if(!rootDir.isDirectory()){
//			files.add(rootDir.getAbsolutePath());
//		}else{
//			String[] fileList =  rootDir.list();
//			for (int i = 0; i < fileList.length; i++) {
//				if(!fileList[i].startsWith(".")){
//					path = rootDir.getAbsolutePath()+"\\"+fileList[i];
//					getAllfiles(path,files);
//				}
//			}
//		}
//		return null;
//	}
//
//	public static ArrayList<String> filterNonAbstract(List<Element> schemaElements,ArrayList<String> elements) throws Exception{
//
//		ArrayList<String> elementlist=new ArrayList<String>();
//
//		for(int i=0;i<elements.size();i++){
//			String elementid=elements.get(i);
//			//System.out.println(elementid+":"+utilities.getElementAttribute(schemaElements, elementid).get("type")+","+utilities.getElementAttribute(schemaElements, elementid).get("abs"));
//			if(utilities.getElementAttribute(schemaElements, elementid).get("abs")==null || utilities.getElementAttribute(schemaElements, elementid).get("abs").equals("false")){
//				elementlist.add(elementid);
//			}
//		}
//
//		return elementlist;
//
//	}
//
//
//	public static ArrayList<String> filterMember(List<Element> schemaElements, HashMap<String,String> xbrlFiles,ArrayList<String> elements) throws Exception{
//
//		ArrayList<String> elementlist=new ArrayList<String>();
//
//		for(int i=0;i<elements.size();i++){
//			String elementid=elements.get(i);
//			if(isMember(schemaElements,xbrlFiles, elementid)){
//				elementlist.add(elementid);
//			}
//		}
//
//		return elementlist;
//
//	}
//
//	public static ArrayList<String> filterAxis(List<Element> schemaElements,ArrayList<String> elements) throws Exception{
//
//		ArrayList<String> elementlist=new ArrayList<String>();
//
//		//System.out.println(elements);
//
//		for(int i=0;i<elements.size();i++){
//			String elementid=elements.get(i);
//			if(isAxis(schemaElements, elementid)){
//				elementlist.add(elementid);
//			}
//		}
//
//
//
//		return elementlist;
//
//	}
//
//	public static boolean isInteger(String value) {
//		  try {
//		   Integer.parseInt(value);
//		   return true;
//		  } catch (NumberFormatException e) {
//		   return false;
//		  }
//	 }
//
//
//
//}
