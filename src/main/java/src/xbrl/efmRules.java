//package src.xbrl;
//
//import org.dom4j.Element;
//import xmlparse.ElementWithLocation;
//
//import java.util.*;
//
//public class efmRules {
//
//
//	//6.6.15
//	public static String six_six_fifteen_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		String filename=xbrlFiles.get("instance");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//				String elementname=elementUnderTest.getName();
//				String nil=elementUnderTest.attributeValue("nil");
//				if(elementname.equals("CommitmentsAndContingencies")){
//					if(nil==null || !nil.toLowerCase().equals("true")){
//						testResult=testResult+"6.6.15>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}else{
//					if(nil!=null){
//						testResult=testResult+"6.6.15>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//			}
//		}
//
//
//		return testResult;
//
//	}
//
//	//6.8.3
//	public static String six_eight_three_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		ArrayList<String> roleTypeElements=utilities.getRoleTypeElements(xbrlFiles);
//		ArrayList<String> arcroleTypeElements=utilities.getArcRoleTypeElements(xbrlFiles);
//
//
//		String filename=xbrlFiles.get("schema");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root, allElements);
//		for(int i=0;i<allElements.size();i++){
//
//			Element elementCurrent=allElements.get(i);
//
//			if(elementCurrent.getName().equals("roleType")){
//
//				if(utilities.find(roleTypeElements, elementCurrent.attributeValue("id"))){
//					testResult=testResult+"6.8.3>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementCurrent).getLineNumber()+"\n";
//				}
//			}
//
//			if(elementCurrent.getName().equals("arcroleType")){
//
//				if(utilities.find(arcroleTypeElements, elementCurrent.attributeValue("id"))){
//					testResult=testResult+"6.8.3>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementCurrent).getLineNumber()+"\n";
//				}
//			}
//		}
//
//
//
//
//		return testResult;
//
//	}
//
//
//	//6.8.11
//	public static String six_eight_eleven_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		String filename=xbrlFiles.get("schema");
//		Element root= utilities.loadxml(filename);
//		List<Element> allElements=root.elements("element");
//
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//				String type=elementUnderTest.attributeValue("type");
//				String balance=elementUnderTest.attributeValue("balance");
//				if(type.equals("xbrli:monetaryItemType") && balance==null){
//					testResult=testResult+"6.8.11>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//				}
//		}
//		return testResult;
//	}
//
//
//	//6.7.6
//	public static String six_seven_six_Check(HashMap<String,String> xbrlFiles, String date) throws Exception{
//
//		String testResult="";
//		String filename=xbrlFiles.get("schema");
//		Element root= utilities.loadxml(filename);
//		String targetnamespace=root.attributeValue("targetNamespace");
//		if(!targetnamespace.matches("http://(\\w+\\.)+\\w+/"+date)){
//			testResult=testResult+"6.7.6>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) root).getLineNumber()+"\n";
//		}
//
//
//		return testResult;
//
//	}
//
//
//	//6.7.2
//	public static String six_seven_two_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		String filename=xbrlFiles.get("schema");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			String elementname=elementUnderTest.getName();
//			if(elementname.equals("import")){
//				String namespace=elementUnderTest.attributeValue("namespace");
//				String schemaLocation=elementUnderTest.attributeValue("schemaLocation");
//				String schemafilename=schemaLocation.split("/")[schemaLocation.split("/").length-1];
//				String localLocation=utilities.findFile(xbrlFiles, schemafilename);
//
//				Element importroot= utilities.loadxml(localLocation);
//				String importrootNamespace=importroot.attributeValue("targetNamespace");
//				if(!importrootNamespace.equals(namespace)){
//					testResult=testResult+"6.7.2>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//				}
//			}
//		}
//		return testResult;
//	}
//
//
//	//6.6.1&6.6.2
//	public static String six_six_onetwo_Check(HashMap<String,String> xbrlFiles,String startdate, String enddate) throws Exception{
//
//		String testResult="";
//
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		String filename=xbrlFiles.get("instance");
//		Element root= utilities.loadxml(filename);
//
//		List<Element> contextElements=root.elements("context");
//
//		ArrayList<String> contextList=new ArrayList<String>();
//		for(int j=0;j<contextElements.size();j++){
//			Element elementUnderTest=contextElements.get(j);
//			String startdateTC="";
//			String enddateTC="";
//			if(elementUnderTest.element("period").element("startDate")!=null){
//				startdateTC=elementUnderTest.element("period").element("startDate").getText();
//			}
//			if(elementUnderTest.element("period").element("endDate")!=null){
//				enddateTC=elementUnderTest.element("period").element("endDate").getText();
//			}
//			if(startdateTC.equals(startdate)&&enddateTC.equals(enddate)){
//				 contextList.add(elementUnderTest.attributeValue("id"));
//			}
//		}
//
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//
//				String contextRef=elementUnderTest.attributeValue("contextRef");
//				String elementname=elementUnderTest.getName();
//				String elementid=elementUnderTest.getNamespacePrefix()+"_"+elementname;
//				String nil=elementUnderTest.attributeValue("nil");
//				String elementType=utilities.getElementAttribute(schemaElements, elementid).get("type");
//				if(elementType.equals("xbrli:stringItemType") || elementType.equals("nonnum:textBlockItemType")){
//					if(!utilities.find(contextList, contextRef)){
//						testResult=testResult+"6.6.1&6.6.2>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//			}
//		}
//
//		return testResult;
//	}
//
//
//	//6.6.37
//	public static String six_six_thirtyseven_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		//System.out.println(schemaElements.size());
//
//		String filename=xbrlFiles.get("instance");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//				String elementvalue=elementUnderTest.getText();
//				String elementid=elementUnderTest.getNamespacePrefix()+"_"+elementUnderTest.getName();
//				String elementtype="";
//
//				for(int m=0;m<schemaElements.size();m++){
//					Element schemaElement=schemaElements.get(m);
//
//					if(schemaElement.attributeValue("id")!=null && schemaElement.attributeValue("id").equals(elementid) ){
//						elementtype=schemaElement.attributeValue("type");
//					}
//				}
//
//				if(elementtype.matches("(.*)durationStringItemType") || elementtype.matches("(.*)durationItemType")){
//					if(!elementvalue.matches("P(\\d+Y)?(\\d+M)?(\\d+D)?(T)?(\\d+H)?(\\d+M)?(\\d+(\\.\\d+)?S)?")){
//						testResult=testResult+"6.6.37>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//			}
//		}
//
//
//		return testResult;
//
//	}
//
//
//	//6.6.36
//	public static String six_six_thirtysix_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		//System.out.println(schemaElements.size());
//
//		String filename=xbrlFiles.get("instance");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			if(elementUnderTest.attributeValue("contextRef")!=null){
//				String elementvalue=elementUnderTest.getText();
//
//				String elementid=elementUnderTest.getNamespacePrefix()+"_"+elementUnderTest.getName();
//				String elementtype="";
//
//				for(int m=0;m<schemaElements.size();m++){
//					Element schemaElement=schemaElements.get(m);
//					//System.out.println(schemaElement.asXML());
//					if(schemaElement.attributeValue("id")!=null && schemaElement.attributeValue("id").equals(elementid)){
//						elementtype=schemaElement.attributeValue("type");
//					}
//				}
//
//				if(elementtype.matches("(.*)dateStringItemType") || elementtype.matches("(.*)dateItemType")){
//					if(!elementvalue.matches("\\d{4}-\\d{2}-\\d{2}")){
//						testResult=testResult+"6.6.36>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//
//			}
//		}
//
//
//		return testResult;
//
//	}
//
//
//	//6.11.4
//	public static String six_eleven_four_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		//parsing label link
//		ArrayList<String> totallabelElement=new ArrayList<String>();
//		ArrayList<String> totallabelElementND=new ArrayList<String>();
//
//		if(xbrlFiles.get("labxml")!=null){
//			String labxmlfilename=xbrlFiles.get("labxml");
//			Element labxmlroot= utilities.loadxml(labxmlfilename);
//			List<Element> labelLinks=labxmlroot.elements("labelLink");
//			for(int i=0;i<labelLinks.size();i++){
//				Element labelLink=labelLinks.get(i);
//				List<Element> locElements=labelLink.elements("loc");
//				HashMap<String,String> labelHref=new HashMap<String,String>();
//				for(int j=0;j<locElements.size();j++){
//					Element locElement=locElements.get(j);
//					labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//				}
//
//				List<Element> labelElements=labelLink.elements("label");
//				HashMap<String,String> labelRole=new HashMap<String,String>();
//				for(int j=0;j<labelElements.size();j++){
//					Element labelElement=labelElements.get(j);
//					labelRole.put(labelElement.attributeValue("label"),labelElement.attributeValue("role"));
//				}
//
//				List<Element> labelArcElements=labelLink.elements("labelArc");
//				for(int j=0;j<labelArcElements.size();j++){
//					Element labelArcElement=labelArcElements.get(j);
//					String role=labelArcElement.attributeValue("arcrole");
//					String fromElement=labelHref.get(labelArcElement.attributeValue("from"));
//					String toRole=labelRole.get(labelArcElement.attributeValue("to"));
//					if(toRole.equals("http://www.xbrl.org/2003/role/totalLabel") || toRole.equals("http://www.xbrl.org/2009/role/negatedTotalLabel")){
//						totallabelElement.add(fromElement);
//					}
//				}
//			}
//			totallabelElementND=(ArrayList<String>) utilities.removeDuplicate(totallabelElement);
//		}
//
//
//		//parsing calculation link
//		String calxmlfilename=xbrlFiles.get("calxml");
//		Element calxmlroot= utilities.loadxml(calxmlfilename);
//		List<Element> calculationLinks=calxmlroot.elements("calculationLink");
//		for(int i=0;i<calculationLinks.size();i++){
//			Element calculationLink=calculationLinks.get(i);
//
//			List<Element> locElements=calculationLink.elements("loc");
//			HashMap<String,String> labelHref=new HashMap<String,String>();
//			for(int j=0;j<locElements.size();j++){
//				Element locElement=locElements.get(j);
//				labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//			}
//
//			List<Element> calArcElements=calculationLink.elements("calculationArc");
//			for(int j=0;j<calArcElements.size();j++){
//				Element calArcElement=calArcElements.get(j);
//				String role=calArcElement.attributeValue("arcrole");
//				String fromElement=labelHref.get(calArcElement.attributeValue("from"));
//				if(role.equals("http://www.xbrl.org/2003/arcrole/summation-item")){
//					Boolean totallabelFound=false;
//					for(int k=0;k<totallabelElementND.size();k++){
//						if(fromElement.equals(totallabelElementND.get(k))){
//							totallabelFound=true;
//						}
//					}
//					if(!totallabelFound){
//						testResult=testResult+"6.11.4>>>>>error detected. Filename:"+calxmlfilename+"; Line number:"+((ElementWithLocation) calArcElement).getLineNumber()+"\n";
//					}
//				}
//			}
//		}
//
//		return testResult;
//
//	}
//
//
//	//6.11.7
//	public static String six_eleven_seven_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//		//System.out.println(schemaElements.size());
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		List<Element> presentaitonLinks=prexmlroot.elements("presentationLink");
//
//		for(int i=0;i<presentaitonLinks.size();i++){
//			Element presentationLink=presentaitonLinks.get(i);
//
//			List<Element> locElements=presentationLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//
//			HashMap<String,ArrayList<String>> elementLabel=new HashMap<String,ArrayList<String>>();
//
//			List<Element> arcElements=presentationLink.elements("presentationArc");
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//				String tolabel=arcElement.attributeValue("to");
//				String toelement=labelHref.get(tolabel);
//				String labelrole=arcElement.attributeValue("preferredLabel");
//				String elementPT=utilities.getElementAttribute(schemaElements,toelement).get("pt");
//
//				if(elementPT.equals("instant")){
//					if(elementLabel.get(toelement)==null){
//						ArrayList<String> labels=new ArrayList<String>();
//						labels.add(labelrole);
//						elementLabel.put(toelement,labels);
//					}else{
//						ArrayList<String> labels=elementLabel.get(toelement);
//						labels.add(labelrole);
//						elementLabel.remove(toelement);
//						elementLabel.put(toelement, labels);
//					}
//				}
//			}
//			if(elementLabel.size()!=0){
//
//				Iterator iter = elementLabel.entrySet().iterator();
//
//				while (iter.hasNext()) {
//					Map.Entry entry = (Map.Entry) iter.next();
//					String elementname = (String) entry.getKey();
//					ArrayList<String> roles = (ArrayList<String>) entry.getValue();
//					Integer occurrence=roles.size();
//
//					if(occurrence>2){
//						testResult=testResult+"6.11.7>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//					}
//
//					if(occurrence==2){
//						ArrayList<String> rolesTCA = new ArrayList<String>();
//						rolesTCA.add("http://www.xbrl.org/2003/role/periodStartLabel");
//						rolesTCA.add("http://www.xbrl.org/2003/role/periodEndLabel");
//						ArrayList<String> rolesTCB = new ArrayList<String>();
//						rolesTCB.add("http://www.xbrl.org/2009/role/negatedPeriodStartLabel");
//						rolesTCB.add("http://www.xbrl.org/2009/role/negatedPeriodEndLabel");
//						if(!(utilities.compare(roles,rolesTCA) || utilities.compare(roles, rolesTCB))){
//							testResult=testResult+"6.11.7>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//						}
//					}
//
//					if(occurrence==1){
//						if(roles.get(0).equals("http://www.xbrl.org/2003/role/periodStartLabel") || roles.get(0).equals("http://www.xbrl.org/2003/role/periodEndLabel") || roles.get(0).equals("http://www.xbrl.org/2009/role/negatedPeriodStartLabel") || roles.get(0).equals("http://www.xbrl.org/2009/role/negatedPeriodEndLabel")){
//							testResult=testResult+"6.11.7>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//						}
//					}
//				}
//			}
//		}
//
//
//
//		return testResult;
//
//	}
//
//
//	//6.9.1
//	public static String six_nine_one_Check(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//
//		if(xbrlFiles.get("prexml")!=null){
//			String prexmlfilename=xbrlFiles.get("prexml");
//			Element prexmlroot= utilities.loadxml(prexmlfilename);
//			List<Element> presentationLinks=prexmlroot.elements("presentationLink");
//
//			for(int j=0;j<presentationLinks.size();j++){
//				Element presentationLink=presentationLinks.get(j);
//				List<Element> locElements=presentationLink.elements("loc");
//				List<Element> arcElements=presentationLink.elements("presentationArc");
//				ArrayList<String> locLabels=new ArrayList<String>();
//				ArrayList<String> presentationArcs=new ArrayList<String>();
//				for(int k=0;k<locElements.size();k++){
//					locLabels.add(locElements.get(k).attributeValue("label"));
//				}
//				for(int k=0;k<arcElements.size();k++){
//					presentationArcs.add(arcElements.get(k).attributeValue("from")+arcElements.get(k).attributeValue("to"));
//				}
//				if(utilities.removeDuplicate(locLabels).size()!=locLabels.size() || utilities.removeDuplicate(presentationArcs).size()!=presentationArcs.size()){
//					testResult=testResult+"6.9.1>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//				}
//
//			}
//		}
//
//
//		if(xbrlFiles.get("labxml")!=null){
//			String labxmlfilename=xbrlFiles.get("labxml");
//			Element labxmlroot= utilities.loadxml(labxmlfilename);
//			List<Element> labelLinks=labxmlroot.elements("labelLink");
//
//			for(int j=0;j<labelLinks.size();j++){
//				Element labelLink=labelLinks.get(j);
//				List<Element> locElements=labelLink.elements("loc");
//				List<Element> arcElements=labelLink.elements("presentationArc");
//				ArrayList<String> locLabels=new ArrayList<String>();
//				ArrayList<String> presentationArcs=new ArrayList<String>();
//				for(int k=0;k<locElements.size();k++){
//					locLabels.add(locElements.get(k).attributeValue("label"));
//				}
//				for(int k=0;k<arcElements.size();k++){
//					presentationArcs.add(arcElements.get(k).attributeValue("from")+arcElements.get(k).attributeValue("to"));
//				}
//				if(utilities.removeDuplicate(locLabels).size()!=locLabels.size() || utilities.removeDuplicate(presentationArcs).size()!=presentationArcs.size()){
//					testResult=testResult+"6.9.1>>>>error detected. Filename:"+labxmlfilename+"; Line number:"+((ElementWithLocation) labelLink).getLineNumber()+"\n";
//				}
//
//			}
//		}
//
//		if(xbrlFiles.get("defxml")!=null){
//			String defxmlfilename=xbrlFiles.get("defxml");
//			Element defxmlroot= utilities.loadxml(defxmlfilename);
//			List<Element> definitionLinks=defxmlroot.elements("definitionLink");
//
//			for(int j=0;j<definitionLinks.size();j++){
//				Element definitionLink=definitionLinks.get(j);
//				List<Element> locElements=definitionLink.elements("loc");
//				List<Element> arcElements=definitionLink.elements("presentationArc");
//				ArrayList<String> locLabels=new ArrayList<String>();
//				ArrayList<String> presentationArcs=new ArrayList<String>();
//				for(int k=0;k<locElements.size();k++){
//					locLabels.add(locElements.get(k).attributeValue("label"));
//				}
//				for(int k=0;k<arcElements.size();k++){
//					presentationArcs.add(arcElements.get(k).attributeValue("from")+arcElements.get(k).attributeValue("to"));
//				}
//				if(utilities.removeDuplicate(locLabels).size()!=locLabels.size() || utilities.removeDuplicate(presentationArcs).size()!=presentationArcs.size()){
//					testResult=testResult+"6.9.1>>>>error detected. Filename:"+defxmlfilename+"; Line number:"+((ElementWithLocation) definitionLink).getLineNumber()+"\n";
//				}
//
//			}
//		}
//
//		if(xbrlFiles.get("calxml")!=null){
//			String calxmlfilename=xbrlFiles.get("calxml");
//			Element calxmlroot= utilities.loadxml(calxmlfilename);
//			List<Element> calculationLinks=calxmlroot.elements("calculationLink");
//
//			for(int j=0;j<calculationLinks.size();j++){
//				Element calculationLink=calculationLinks.get(j);
//				List<Element> locElements=calculationLink.elements("loc");
//				List<Element> arcElements=calculationLink.elements("presentationArc");
//				ArrayList<String> locLabels=new ArrayList<String>();
//				ArrayList<String> presentationArcs=new ArrayList<String>();
//				for(int k=0;k<locElements.size();k++){
//					locLabels.add(locElements.get(k).attributeValue("label"));
//				}
//				for(int k=0;k<arcElements.size();k++){
//					presentationArcs.add(arcElements.get(k).attributeValue("from")+arcElements.get(k).attributeValue("to"));
//				}
//				if(utilities.removeDuplicate(locLabels).size()!=locLabels.size() || utilities.removeDuplicate(presentationArcs).size()!=presentationArcs.size()){
//					testResult=testResult+"6.9.1>>>>error detected. Filename:"+calxmlfilename+"; Line number:"+((ElementWithLocation) calculationLink).getLineNumber()+"\n";
//				}
//
//			}
//		}
//
//		return testResult;
//
//	}
//
//
//
//}
