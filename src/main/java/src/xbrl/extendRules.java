//package src.xbrl;
//
//import org.dom4j.Element;
//import xmlparse.ElementWithLocation;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class extendRules {
//
//	//xml.02
//	public static String xml_three(HashMap<String,String> xbrlFiles, String base) throws Exception{
//
//			String testResult="";
//			ArrayList<Element> unitElements=new ArrayList<Element>();
//			ArrayList<Element> factElements=new ArrayList<Element>();
//
//			//parsing instance
//			String filename=xbrlFiles.get("instance");
//			Element root= utilities.loadxml(filename);
//			ArrayList<Element> allElements=new ArrayList<Element>();
//			utilities.getAllElements(root,allElements);
//			for(int j=0;j<allElements.size();j++){
//				Element elementUnderTest=allElements.get(j);
//				if(elementUnderTest.getName().equals("unit")){
//					unitElements.add(elementUnderTest);
//				}
//				if(elementUnderTest.attributeValue("unitRef")!=null){
//					factElements.add(elementUnderTest);
//				}
//			}
//
//			//validation
//			for(int i=0;i<factElements.size();i++){
//				Element factUnderTest=factElements.get(i);
//				Element unitUnderTest=null;
//				String unitref=factUnderTest.attributeValue("unitRef");
//				utilities.printResult(unitref);
//				for(int j=0;j<unitElements.size();j++){
//					if(unitElements.get(j).attributeValue("id").equals(unitref)){
//						unitUnderTest=unitElements.get(j);
//					}
//				}
//				if(unitUnderTest==null){
//					testResult=testResult+"xml.03>>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) factUnderTest).getLineNumber()+"\n";
//				}
//
//			}
//
//			List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//			for(int j=0;j<allElements.size();j++){
//				Element elementUnderTest=allElements.get(j);
//				if(elementUnderTest.getName().equals("measure")){
//					String unitPrefix=elementUnderTest.getText().split(":")[0];
//					if(!unitPrefix.equals(base) && !unitPrefix.equals("xbrli") && !unitPrefix.equals("iso4217")){
//							testResult=testResult+"xml.03>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//			}
//
//			return testResult;
//
//		}
//
//	//PRE��01
//	public static String pre_one(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		List<Element> prelinkElements=prexmlroot.elements("presentationLink");
//		for(int i=0;i<prelinkElements.size();i++){
//
//			Element prelinkElement=prelinkElements.get(i);
//			List<Element> locElements=prelinkElement.elements("loc");
//			List<Element> preArcElements=prelinkElement.elements("presentationArc");
//			for(int j=0;j<preArcElements.size();j++){
//				Element preArcElement=preArcElements.get(j);
//				Boolean validation=true;
//				String preArcFrom=preArcElement.attributeValue("from");
//				String preArcTo=preArcElement.attributeValue("to");
//				String fromElement="";
//				String toElement="";
//
//				for(int k=0;k<locElements.size();k++){
//					Element locElement=locElements.get(k);
//					if(locElement.attributeValue("label").equals(preArcFrom)){
//						fromElement=locElement.attributeValue("href").split("#")[1];
//					}
//					if(locElement.attributeValue("label").equals(preArcTo)){
//						toElement=locElement.attributeValue("href").split("#")[1];
//					}
//				}
//
//				//validation
//				//Table->Axis/Lineitems
//				if(utilities.isTable(schemaElements, fromElement)){
//					if(!(utilities.isAxis(schemaElements, toElement) || utilities.isLineitems(toElement))){
//						validation=false;
//					}
//				}
//				if(utilities.isAxis(schemaElements, toElement) || utilities.isLineitems(toElement)){
//					if(!utilities.isTable(schemaElements, fromElement)){
//						validation=false;
//					}
//				}
//
//				//Axis->Domain/member
//				if(utilities.isAxis(schemaElements, fromElement)){
//					if(!(utilities.isDomain(schemaElements, xbrlFiles, toElement) || utilities.isMember(schemaElements, xbrlFiles, toElement))){
//						validation=false;
//					}
//				}
//
//				//Domain->Domain/member
//				if(utilities.isDomain(schemaElements, xbrlFiles, fromElement)){
//					if(!(utilities.isDomain(schemaElements, xbrlFiles, toElement) || utilities.isMember(schemaElements, xbrlFiles, toElement))){
//						validation=false;
//					}
//				}
//
//				if(!validation){
//					testResult=testResult+"pre.01>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) preArcElement).getLineNumber()+"\n";
//				}
//			}
//		}
//
//		return testResult;
//
//	}
//
//	//def.02
//	public static String def_two(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		//parsing definition link
//		String defxmlfilename=xbrlFiles.get("defxml");
//		Element defxmlroot= utilities.loadxml(defxmlfilename);
//		List<Element> deflinkElements=defxmlroot.elements("definitionLink");
//		for(int i=0;i<deflinkElements.size();i++){
//
//			Element deflinkElement=deflinkElements.get(i);
//			List<Element> locElements=deflinkElement.elements("loc");
//			List<Element> defArcElements=deflinkElement.elements("definitionArc");
//			for(int j=0;j<defArcElements.size();j++){
//				Element defArcElement=defArcElements.get(j);
//				Boolean validation=true;
//				String defArcFrom=defArcElement.attributeValue("from");
//				String defArcTo=defArcElement.attributeValue("to");
//				String fromElement="";
//				String toElement="";
//
//				for(int k=0;k<locElements.size();k++){
//					Element locElement=locElements.get(k);
//					if(locElement.attributeValue("label").equals(defArcFrom)){
//						fromElement=locElement.attributeValue("href").split("#")[1];
//					}
//					if(locElement.attributeValue("label").equals(defArcTo)){
//						toElement=locElement.attributeValue("href").split("#")[1];
//					}
//				}
//
//
//				//validation
//				//from must be lineitems if to is table
//				if(utilities.isTable(schemaElements, toElement)){
//					if(!utilities.isLineitems(fromElement)){
//						validation=false;
//					}
//					if(defArcElement.attributeValue("close")!=null && !defArcElement.attributeValue("close").equals("true")){
//						validation=false;
//					}
//					if(!defArcElement.attributeValue("arcrole").equals("http://xbrl.org/int/dim/arcrole/all")){
//						validation=false;
//					}
//				}
//
//				//from must be table if to is axis
//				if(utilities.isAxis(schemaElements, toElement)){
//					if(!utilities.isTable(schemaElements, fromElement)){
//						validation=false;
//					}
//					if(!defArcElement.attributeValue("arcrole").equals("http://xbrl.org/int/dim/arcrole/hypercube-dimension")){
//						validation=false;
//					}
//				}
//
//				//to must be axis if from is table
//				if(utilities.isTable(schemaElements, fromElement)){
//					if(!utilities.isAxis(schemaElements, toElement)){
//						validation=false;
//					}
//					if(!defArcElement.attributeValue("arcrole").equals("http://xbrl.org/int/dim/arcrole/hypercube-dimension")){
//						validation=false;
//					}
//				}
//
//				//to must be Domain/member if from is axis
//				if(utilities.isAxis(schemaElements, fromElement)){
//					if(!(utilities.isDomain(schemaElements, xbrlFiles,toElement)||utilities.isMember(schemaElements, xbrlFiles, toElement))){
//						validation=false;
//					}
//					if(!(defArcElement.attributeValue("arcrole").equals("http://xbrl.org/int/dim/arcrole/dimension-domain") || defArcElement.attributeValue("arcrole").equals("http://xbrl.org/int/dim/arcrole/dimension-default"))){
//						validation=false;
//					}
//				}
//
//				if(!validation){
//					testResult=testResult+"def.02>>>>>error detected. Filename:"+defxmlfilename+"; Line number:"+((ElementWithLocation) defArcElement).getLineNumber()+"\n";
//				}
//			}
//		}
//
//		return testResult;
//
//	}
//
//	//selfdefine.4
//	public static String pre_two(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//
//		//parsing definition link
//		String defxmlfilename=xbrlFiles.get("defxml");
//		Element defxmlroot= utilities.loadxml(defxmlfilename);
//		List<Element> deflinkElements=defxmlroot.elements("definitionLink");
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		List<Element> prelinkElements=prexmlroot.elements("presentationLink");
//
//		//validation
//		for(int i=0;i<prelinkElements.size();i++){
//
//			boolean dimensionDetected=false;
//			Boolean validation=true;
//			Element presentationLink=prelinkElements.get(i);
//
//			List<Element> presentationLocs=presentationLink.elements("loc");
//			HashMap<String,String> prelabelHref=new HashMap<String,String>();
//			for(int jj=0;jj<presentationLocs.size();jj++){
//				Element locElement=presentationLocs.get(jj);
//				prelabelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//			}
//
//			List<Element> presentationArcs=presentationLink.elements("presentationArc");
//			for(int j=0;j<presentationArcs.size();j++){
//				Element preArcElement=presentationArcs.get(j);
//
//				String fromElement=prelabelHref.get(preArcElement.attributeValue("from"));
//				String toElement=prelabelHref.get(preArcElement.attributeValue("to"));
//
//				//validation
//				//presentation link with a table
//				if((utilities.isTable(schemaElements, fromElement)||utilities.isTable(schemaElements, toElement)) && !dimensionDetected){
//
//					dimensionDetected=true;
//
//					String role=preArcElement.getParent().attributeValue("role");
//
//
//					for(int ii=0;ii<deflinkElements.size();ii++){
//
//
//
//						Element definitionLink=deflinkElements.get(ii);
//						if(definitionLink.attributeValue("role").equals(role)){
//
//
//							List<Element> definitionLocs=definitionLink.elements("loc");
//							List<Element> definitionArcs=definitionLink.elements("definitionArc");
//
//
//							Integer lineitemsOrder=2;//initialize the lineitemsorder count
//							//sort the presentation arcs
//							ArrayList<Element> presentationArcsSort=new ArrayList<Element>();
//							for(int km=0;km<presentationArcs.size();km++){
//								Element presentationArcInsert=presentationArcs.get(km);
//								Float order=Float.valueOf(presentationArcInsert.attributeValue("order"));
//								Integer insertIndex=presentationArcsSort.size();
//								if(presentationArcsSort.size()==0){
//									presentationArcsSort.add(presentationArcInsert);
//								}else{
//									for(int kn=0;kn<presentationArcsSort.size();kn++){
//										Float orderTC=Float.valueOf(presentationArcsSort.get(kn).attributeValue("order"));
//										if(order<=orderTC){
//											insertIndex=kn;break;
//										}
//									}
//									presentationArcsSort.add(insertIndex,presentationArcInsert);
//								}
//							}
//
//
//
//
//
//
//							//compare locs between presentationlink and definitonlink
//							//System.out.println(((ElementWithLocation) presentationLink).getLineNumber());
//							Boolean locMatch=true;
//					        ArrayList<String> hrefList=new ArrayList<String>();
//					        String abstractLabel="";
//					        for(int g=0;g<presentationLocs.size();g++){
//					        	String locLabel=presentationLocs.get(g).attributeValue("label");
//					        	String locHref=presentationLocs.get(g).attributeValue("href");
//					        	String elementId=locHref.split("#")[1];
//
//					        	Boolean hasFather=false;
//					        	for(int h=0;h<presentationArcs.size();h++){
//					        		if(presentationArcs.get(h).attributeValue("to").equals(locLabel)){
//					        			hasFather=true;
//					        		}
//					        	}
//
//					        	if(hasFather){
//					        		Boolean duplicateFound=false;
//					        		for(int k=0;k<hrefList.size();k++){
//					        			if(presentationLocs.get(g).attributeValue("href").equals(hrefList.get(k))){
//					        				duplicateFound=true;
//					        			}
//					        		}
//					        		if(!duplicateFound){
//					        			hrefList.add(presentationLocs.get(g).attributeValue("href"));
//										if(utilities.isDomain(schemaElements,xbrlFiles,elementId)){
//											//duplicate loc for domain elements
//											hrefList.add(presentationLocs.get(g).attributeValue("href"));
//							        	}
//					        		}
//
//					        	}else{
//					        		abstractLabel=locLabel;
//					        	}
//					        }
//					       //System.out.println(abstractLabel);
//
//					        ArrayList<String> hrefListTC=new ArrayList<String>();
//					        for(int g=0;g<definitionLocs.size();g++){
//					        	hrefListTC.add(definitionLocs.get(g).attributeValue("href"));
//					        }
//					        locMatch=utilities.compare(hrefList, hrefListTC);
//
//
//
//
//
//					        //compare arcs between defintion
//					        Boolean arcMatch=true;
//					        Boolean arcMatchWithoutOrder=true;
//					        HashMap<String,String> loclabelHref=new HashMap<String,String>();
//							for(int xx=0;xx<presentationLocs.size();xx++){
//								Element prelocElement=presentationLocs.get(xx);
//								loclabelHref.put(prelocElement.attributeValue("label"),prelocElement.attributeValue("href").split("#")[1]);
//							}
//					        HashMap<String,String> defloclabelHref=new HashMap<String,String>();
//							for(int xx=0;xx<definitionLocs.size();xx++){
//								Element deflocElement=definitionLocs.get(xx);
//								defloclabelHref.put(deflocElement.attributeValue("label"),deflocElement.attributeValue("href").split("#")[1]);
//							}
//
//							ArrayList<String> fromtoList=new ArrayList<String>();
//							ArrayList<String> fromtoroleList=new ArrayList<String>();
//							ArrayList<String> fromtoroleorderList=new ArrayList<String>();
//							ArrayList<String> fromtoroleListTC=new ArrayList<String>();
//							ArrayList<String> fromtoroleorderListTC=new ArrayList<String>();
//
//							presentationArcs=presentationArcsSort;//use sorted arcs for validation
//
//							HashMap<String,Integer> fromDuplicate=new HashMap<String,Integer>();
//
//					        for(int g=0;g<presentationArcs.size();g++){
//					        	String fromLabel=presentationArcs.get(g).attributeValue("from");
//					        	String toLabel=presentationArcs.get(g).attributeValue("to");
//					        	String order=presentationArcs.get(g).attributeValue("order");
//
//
//
//					        	String fromElementUsedforCompare=loclabelHref.get(fromLabel);
//					        	String toElementUsedforCompare=loclabelHref.get(toLabel);
//
//
//					        	if(!fromLabel.equals(abstractLabel)){
//					        		Boolean fromtoduplciateFound=false;
//					        		for(int xk=0;xk<fromtoList.size();xk++){
//					        			if(fromtoList.get(xk).equals(fromElementUsedforCompare+toElementUsedforCompare)){
//					        				fromtoduplciateFound=true;
//					        				if(fromDuplicate.get(fromLabel)!=null){
//					        					fromDuplicate.put(fromLabel, fromDuplicate.get(fromLabel)+1);
//					        				}else{
//					        					fromDuplicate.put(fromLabel, 1);
//					        				}
//
//					        			}
//					        		}
//					        		if(!fromtoduplciateFound){
//
//					        			String arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//
//								        //toElementUsedforCompare is axis
//								        if(utilities.isAxis(schemaElements, toElementUsedforCompare)){
//								        	arcrole="http://xbrl.org/int/dim/arcrole/hypercube-dimension";
//								        }
//
//								        //toElementUsedforCompare is domain
//								        if(utilities.isDomain(schemaElements, xbrlFiles, toElementUsedforCompare)){
//								        	arcrole="http://xbrl.org/int/dim/arcrole/dimension-domain";
//								        }
//
//								        //toElementUsedforCompare is member
//								        if(utilities.isMember(schemaElements, xbrlFiles, toElementUsedforCompare)){
//								        	if(utilities.isAxis(schemaElements, fromElementUsedforCompare)){
//								        		arcrole="http://xbrl.org/int/dim/arcrole/dimension-domain";
//								        	}else{
//								        		arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//								        	}
//								        }
//
//								        //fromElementUsedforCompare is lineitems
//								        if(utilities.isLineitems(fromElementUsedforCompare)){
//
//								        	order=String.valueOf(lineitemsOrder)+".0";
//								        	//System.out.println(order);
//								        	arcrole="http://xbrl.org/int/dim/arcrole/domain-member";
//								        	lineitemsOrder++;
//
//								        }else{
//						        			if(fromDuplicate.get(fromLabel)!=null){
//						        				order=String.valueOf(Float.valueOf(order)-fromDuplicate.get(fromLabel));
//						        			}
//								        }
//
//
//
//					        			//from is table, to is lineitems
//					        			if(utilities.isTable(schemaElements, fromElementUsedforCompare) && utilities.isLineitems(toElementUsedforCompare)){
//
//					        				String temp=fromElementUsedforCompare;
//					        				fromElementUsedforCompare=toElementUsedforCompare;
//					        				toElementUsedforCompare=temp;
//					        				order="1.0";
//					        				arcrole="http://xbrl.org/int/dim/arcrole/all";
//					        			}
//
//								        fromtoList.add(fromElementUsedforCompare+toElementUsedforCompare);
//								        fromtoroleList.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole);
//								        fromtoroleorderList.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole+",Order:"+order);
//
//								        //toElementUsedforCompare is domain
//								        if(utilities.isDomain(schemaElements, xbrlFiles, toElementUsedforCompare)){
//								        	//domain
//								        	order=String.valueOf(Float.valueOf(order)+1);
//								        	arcrole="http://xbrl.org/int/dim/arcrole/dimension-default";
//								        	fromtoroleList.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole);
//								        	fromtoroleorderList.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole+",Order:"+order);
//								        }
//
//					        		}
//					        	}
//					        }
//
//					        for(int g=0;g<definitionArcs.size();g++){
//
//					        	String fromLabel=definitionArcs.get(g).attributeValue("from");
//					        	String toLabel=definitionArcs.get(g).attributeValue("to");
//					        	String order=definitionArcs.get(g).attributeValue("order");
//					        	String arcrole=definitionArcs.get(g).attributeValue("arcrole");
//
//					        	String fromElementUsedforCompare=defloclabelHref.get(fromLabel);
//					        	String toElementUsedforCompare=defloclabelHref.get(toLabel);
//					        	fromtoroleListTC.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole);
//					        	fromtoroleorderListTC.add("From:"+fromElementUsedforCompare+",To:"+toElementUsedforCompare+",Arcrole:"+arcrole+",Order:"+order);
//					        }
//					        arcMatchWithoutOrder=utilities.compare(fromtoroleList, fromtoroleListTC);
//					        arcMatch=utilities.compare(fromtoroleorderList, fromtoroleorderListTC);
//
//
//							if(!locMatch || !arcMatchWithoutOrder){
//								//System.out.println(((ElementWithLocation) presentationLink).getLineNumber());
//
//								testResult=testResult+"selfdefine.4>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//
//						        if(!locMatch){
//						        	ArrayList hrefListClone=(ArrayList) hrefList.clone();
//						        	hrefList.removeAll(hrefListTC);
//						        	hrefListTC.removeAll(hrefListClone);
//						        	for(int xm=0;xm<hrefList.size();xm++){
//						        		testResult=testResult+"Expected loc element not found in the definition link. Element name:"+hrefList.get(xm)+"\n";
//						        	}
//						        	for(int xm=0;xm<hrefListTC.size();xm++){
//						        		testResult=testResult+"Unexpected loc element detected in the definition link. Element name:"+hrefListTC.get(xm)+"\n";
//						        	}
//						        }
//
//						        if(!arcMatchWithoutOrder){
//						        	//System.out.println(((ElementWithLocation) presentationLink).getLineNumber());
//						        	//System.out.println(fromtoroleList);
//						        	//System.out.println(fromtoroleListTC);
//
//						        	ArrayList fromtoroleListClone= (ArrayList) fromtoroleList.clone();
//						        	fromtoroleList.removeAll(fromtoroleListTC);
//						        	fromtoroleListTC.removeAll(fromtoroleListClone);
//						        	for(int xm=0;xm<fromtoroleList.size();xm++){
//						        		testResult=testResult+"Expected definitionArc element not found in the definition link. "+fromtoroleList.get(xm)+"\n";
//						        	}
//						        	for(int xm=0;xm<fromtoroleListTC.size();xm++){
//						        		testResult=testResult+"Unexpected definitionArc element detected in the definition link. "+fromtoroleListTC.get(xm)+"\n";
//						        	}
//						        }
//							}else if(!arcMatch){
//						        //utilities.printResult(fromtoroleorderList.toString());
//						        //utilities.printResult(fromtoroleorderListTC.toString());
//								testResult=testResult+"selfdefine.4>>>>>order inconsistency detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//					        }
//
//
//
//
//
//
//						}
//
//
//					}
//
//
//
//				}
//
//
//			}
//		}
//
//
//
//		return testResult;
//
//	}
//
//	//selfdefine.5
//	public static String xsd_one(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		//parsing schema
//		String schemafilename=xbrlFiles.get("schema");
//		Element schemaroot= utilities.loadxml(schemafilename);
//		List<Element> schemaElements=schemaroot.elements("element");
//
//		ArrayList<String> hrefList=new ArrayList<String>();
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		ArrayList<Element> prexmlElements=new ArrayList<Element>();
//		utilities.getAllElements(prexmlroot,prexmlElements);
//		for(int i=0;i<prexmlElements.size();i++){
//			Element prexmlElement=prexmlElements.get(i);
//			if(prexmlElement.getName().equals("loc")){
//				hrefList.add(prexmlElement.attributeValue("href").split("#")[1]);
//			}
//		}
//
//		//parsing definition link
//		String defxmlfilename=xbrlFiles.get("defxml");
//		Element defxmlroot= utilities.loadxml(defxmlfilename);
//		ArrayList<Element> defxmlElements=new ArrayList<Element>();
//		utilities.getAllElements(defxmlroot,defxmlElements);
//		for(int i=0;i<defxmlElements.size();i++){
//			Element defxmlElement=defxmlElements.get(i);
//			if(defxmlElement.getName().equals("loc")){
//				hrefList.add(defxmlElement.attributeValue("href").split("#")[1]);
//			}
//		}
//
//		//parsing calculation link
//		String calxmlfilename=xbrlFiles.get("calxml");
//		Element calxmlroot= utilities.loadxml(calxmlfilename);
//		ArrayList<Element> calxmlElements=new ArrayList<Element>();
//		utilities.getAllElements(calxmlroot,calxmlElements);
//		for(int i=0;i<calxmlElements.size();i++){
//			Element calxmlElement=calxmlElements.get(i);
//			if(calxmlElement.getName().equals("loc")){
//				hrefList.add(calxmlElement.attributeValue("href").split("#")[1]);
//			}
//		}
//
//		//parsing label link
//		String labxmlfilename=xbrlFiles.get("labxml");
//		Element labxmlroot= utilities.loadxml(labxmlfilename);
//		ArrayList<Element> labxmlElements=new ArrayList<Element>();
//		utilities.getAllElements(labxmlroot,labxmlElements);
//		for(int i=0;i<labxmlElements.size();i++){
//			Element labxmlElement=labxmlElements.get(i);
//			if(labxmlElement.getName().equals("loc")){
//				hrefList.add(labxmlElement.attributeValue("href").split("#")[1]);
//			}
//		}
//
//		for(int i=0;i<schemaElements.size();i++){
//			Element schemaElement=schemaElements.get(i);
//			String elementid=schemaElement.attributeValue("id");
//			Boolean elementidFound=false;
//			for(int j=0;j<hrefList.size();j++){
//				if(hrefList.get(j).equals(elementid)){
//					  elementidFound=true;
//				}
//			}
//			if(!elementidFound){
//				testResult=testResult+"selfdefine.5>>>>>error detected. Filename:"+schemafilename+"; Line number:"+((ElementWithLocation) schemaElement).getLineNumber()+"\n";
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
//	//selfdefine.6
//	public static String pre_three(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//		ArrayList<String> deprecatedElementsRaw=utilities.getDeprecatedElements(xbrlFiles);
//		ArrayList<String> deprecatedElements=(ArrayList<String>) utilities.removeDuplicate(deprecatedElementsRaw);
//
//		//parsing presentation link
//		String filename=xbrlFiles.get("prexml");
//		Element root= utilities.loadxml(filename);
//		ArrayList<Element> allElements=new ArrayList<Element>();
//		utilities.getAllElements(root,allElements);
//
//		//validation
//		for(int j=0;j<allElements.size();j++){
//			Element elementUnderTest=allElements.get(j);
//			//only facts
//			if(elementUnderTest.getName().equals("loc")){
//				String href=elementUnderTest.attributeValue("href").split("#")[1];
//				for(int k=0;k<deprecatedElements.size();k++){
//					if(href.equals(deprecatedElements.get(k))){
//						testResult=testResult+"selfdefine.6>>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) elementUnderTest).getLineNumber()+"\n";
//					}
//				}
//			}
//		}
//
//		return testResult;
//	}
//
//	//selfdefine.7
//	public static String lab_one(HashMap<String,String> xbrlFiles,String base) throws Exception{
//
//		String testResult="";
//
//		//parsing presentation link
//		ArrayList<String> elementlabelroleRaw=new ArrayList<String>();
//		String prefilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prefilename);
//		List<Element> presentaitonLinks=prexmlroot.elements("presentationLink");
//		for(int i=0;i<presentaitonLinks.size();i++){
//			Element presentationLink=presentaitonLinks.get(i);
//			List<Element> locElements=presentationLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//			List<Element> arcElements=presentationLink.elements("presentationArc");
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//
//				String tolabel=arcElement.attributeValue("to");
//				String toElement=labelHref.get(tolabel);
//				String toRole=arcElement.attributeValue("preferredLabel");
//				elementlabelroleRaw.add(toElement+toRole);
//			}
//		}
//		ArrayList<String> elementlabelrole=(ArrayList<String>) utilities.removeDuplicate(elementlabelroleRaw);
//		//System.out.println(elementlabelrole);
//
//		//parsing label link
//		String labfilename=xbrlFiles.get("labxml");
//		Element labxmlroot= utilities.loadxml(labfilename);
//		List<Element> labelLinks=labxmlroot.elements("labelLink");
//		for(int i=0;i<labelLinks.size();i++){
//
//			Element labelLink=labelLinks.get(i);
//
//			List<Element> locElements=labelLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//			List<Element> labelElements=labelLink.elements("label");
//			HashMap<String,String> labelRole=utilities.getLabeRole(labelElements);
//			List<Element> arcElements=labelLink.elements("labelArc");
//
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//				String fromlabel=arcElement.attributeValue("from");
//				String fromelement=labelHref.get(fromlabel);
//				String tolabel=arcElement.attributeValue("to");
//				String torole=labelRole.get(tolabel);
//				if(!torole.equals("http://www.xbrl.org/2003/role/label")){
//					if(!(torole.equals("http://www.xbrl.org/2003/role/documentation") && fromelement.split("_")[0].equals(base))){
//					Boolean labelroleFound=false;
//					for(int k=0;k<elementlabelroleRaw.size();k++){
//						if(elementlabelroleRaw.get(k).equals(fromelement+torole)){
//							labelroleFound=true;
//						}
//					}
//					if(!labelroleFound){
//						//System.out.println(fromelement+torole);
//						testResult=testResult+"selfdefine.7>>>>>error detected. Filename:"+labfilename+"; Line number:"+((ElementWithLocation) arcElement).getLineNumber()+"\n";
//					}
//					}
//				}
//			}
//		}
//
//
//		return testResult;
//	}
//
//	//xml.01
//	public static String xml_one(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		//parsing schema
//		String filename=xbrlFiles.get("instance");
//		Element root= utilities.loadxml(filename);
//		List<Element> contextElements=root.elements("context");
//
//		for(int i=0;i<contextElements.size();i++){
//
//			Element contextElement=contextElements.get(i);
//			String contextid=contextElement.attributeValue("id");
//			Element periodElement=contextElement.element("period");
//			Element instantElement=periodElement.element("instant");
//			Element startdateElement=periodElement.element("startDate");
//			Element enddateElement=periodElement.element("endDate");
//
//
//
//			String contextidDimension="";
//			if(contextElement.element("entity").element("segment")!=null){
//				List<Element> memberList=contextElement.element("entity").element("segment").elements("explicitMember");
//
//				for(int jk=0;jk<memberList.size();jk++){
//					Element memberElement=memberList.get(jk);
//					String axis=memberElement.attributeValue("dimension").split(":")[1];
//					String member=memberElement.getText().split(":")[1];
//					contextidDimension=contextidDimension+"_"+axis+"_"+member;
//				}
//
//			}
//
//
//
//			if(instantElement!=null){
//				String contextidPrefix="As_Of_"+instantElement.getText().replace("-", "_");
//				contextidPrefix=contextidPrefix+contextidDimension;
//				if(!contextid.equals(contextidPrefix)){
//					testResult=testResult+"xml.01>>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) contextElement).getLineNumber()+"\n";
//				}
//			}else{
//				String startdate=startdateElement.getText().replace("-", "_");
//				String enddate=enddateElement.getText().replace("-", "_");
//				String contextidPrefix="From_"+startdate+"_To_"+enddate;
//				contextidPrefix=contextidPrefix+contextidDimension;
//				if(!contextid.equals(contextidPrefix)){
//					testResult=testResult+"xml.01>>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) contextElement).getLineNumber()+"\n";
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
//	//def.01
//	public static String def_one(HashMap<String,String> xbrlFiles) throws Exception{
//
//			String testResult="";
//			ArrayList<Element> contextElements=new ArrayList<Element>();
//			ArrayList<Element> factElements=new ArrayList<Element>();
//
//
//			//parsing presentation link
//			String defxmlfilename=xbrlFiles.get("defxml");
//			Element defxmlroot= utilities.loadxml(defxmlfilename);
//
//			//parsing instance
//			String filename=xbrlFiles.get("instance");
//			Element root= utilities.loadxml(filename);
//			ArrayList<Element> allElements=new ArrayList<Element>();
//			utilities.getAllElements(root,allElements);
//			for(int j=0;j<allElements.size();j++){
//				Element elementUnderTest=allElements.get(j);
//				if(elementUnderTest.getName().equals("context")){
//					contextElements.add(elementUnderTest);
//				}
//				if(elementUnderTest.attributeValue("contextRef")!=null){
//					factElements.add(elementUnderTest);
//				}
//			}
//
//			//dimension members set in context
//			HashMap<String,ArrayList<String>> contextMembers=new HashMap<String,ArrayList<String>>();
//			for(int j=0;j<contextElements.size();j++){
//				Element contextCurrent=contextElements.get(j);
//				String contextID=contextCurrent.attributeValue("id");
//				ArrayList<String> dimensionmemberList=new ArrayList<String>();
//				if(contextCurrent.element("entity").element("segment")!=null){
//					List<Element> members=contextCurrent.element("entity").element("segment").elements();
//					for(int k=0;k<members.size();k++){
//						Element memberUnderTest=members.get(k);
//						String dimension=memberUnderTest.attributeValue("dimension");
//						String[] dimensionbreak=dimension.split(":");
//						String dimensionID=dimensionbreak[0]+"_"+dimensionbreak[1];
//						String dimensionmember=memberUnderTest.getText();
//						String[] dimensionmemberbreak=dimensionmember.split(":");
//						String dimensionmemberID=dimensionmemberbreak[0]+"_"+dimensionmemberbreak[1];
//						dimensionmemberList.add(dimensionID+"#"+dimensionmemberID);
//					}
//				}
//				//System.out.println("context dimensionmember size: "+dimensionmemberList.size());
//				contextMembers.put(contextID,dimensionmemberList);
//			}
//			//System.out.println(contextMembers);
//
//			//element members
//			HashMap<String,ArrayList<String>> elementMembers=new HashMap<String,ArrayList<String>>();
//			for(int j=0;j<factElements.size();j++){
//				Element factCurrent=factElements.get(j);
//				String elementid=factCurrent.getNamespacePrefix()+"_"+factCurrent.getName();
//				ArrayList<String> dimensionmemberList=new ArrayList<String>();
//
//
//				for(int k=0;k<factElements.size();k++){
//					Element factTest=factElements.get(k);
//					String contextRef=factTest.attributeValue("contextRef");
//					String elementidTest=factTest.getNamespacePrefix()+"_"+factTest.getName();
//					if(elementidTest.equals(elementid)){
//						dimensionmemberList.addAll(contextMembers.get(contextRef));
//					}
//				}
//				if(elementMembers.get(elementid)==null){
//					elementMembers.put(elementid, (ArrayList<String>) utilities.removeDuplicate(dimensionmemberList));
//					//System.out.println(elementid+":"+elementMembers.get(elementid));
//				}
//			}
//
//
//			//validation
//			List<Element> defLinks=defxmlroot.elements("definitionLink");
//			//for(int jk=0;jk<defLinks.size();jk++){
//			for(int jk=0;jk<1;jk++){
//				Element definitionLink= defLinks.get(jk);
//
//				/***Extract axisMember List**/
//				ArrayList<String> AxisList=new ArrayList<String>();
//				ArrayList<String> ItemList=new ArrayList<String>();
//				ArrayList<String> axisMemberList=new ArrayList<String>();
//
//				List<Element> definitionLocs=definitionLink.elements("loc");
//				List<Element> definitionArcs=definitionLink.elements("definitionArc");
//				HashMap<String,String> labelHref=utilities.getLabeHref(definitionLocs);
//				HashMap<String,ArrayList<String>> arcMap=utilities.getArcMap(definitionArcs,labelHref);
//
//
//
//				//Extract axis list
//				for(int j=0;j<definitionArcs.size();j++){
//					Element definitionArc=definitionArcs.get(j);
//					String arcrole=definitionArc.attributeValue("arcrole");
//					//System.out.println(arcrole);
//					String fromLabel=definitionArc.attributeValue("from");
//					String toLabel=definitionArc.attributeValue("to");
//					String toElement=labelHref.get(toLabel);
//					if(arcrole.toLowerCase().equals("http://xbrl.org/int/dim/arcrole/hypercube-dimension")){
//						AxisList.add(toElement);
//					}
//				}
//
//				//extract axis member list
//				int jj=0;
//				while(jj<AxisList.size()){
//
//					ArrayList<String> domainList=arcMap.get(AxisList.get(jj));
//					if(domainList!=null){
//						String domain=domainList.get(0);
//						ArrayList<String> memberList=new ArrayList<String>();
//						utilities.getAllChildren(arcMap, domain, memberList);
//						//System.out.println("memberList: "+memberList);
//						if(memberList!=null && memberList.size()!=0){
//							for(int kk=0;kk<memberList.size();kk++){
//								axisMemberList.add(AxisList.get(jj)+"#"+memberList.get(kk));
//							}
//							jj++;
//						}else{
//							AxisList.remove(jj);
//						}
//					}
//					else{
//						AxisList.remove(jj);
//					}
//				}
//
//
//				/***Extract lineitem List**/
//				String abstractLabel="";
//				for(int j=0;j<definitionArcs.size();j++){
//					Element definitionArc=definitionArcs.get(j);
//					String arcrole=definitionArc.attributeValue("arcrole");
//					String fromLabel=definitionArc.attributeValue("from");
//					if(arcrole.toLowerCase().equals("http://xbrl.org/int/dim/arcrole/all")){
//						abstractLabel=fromLabel;
//					}
//				}
//
//				for(int j=0;j<definitionArcs.size();j++){
//					Element definitionArc=definitionArcs.get(j);
//					String arcrole=definitionArc.attributeValue("arcrole");
//					//System.out.println(arcrole);
//					String fromLabel=definitionArc.attributeValue("from");
//					String toLabel=definitionArc.attributeValue("to");
//					String toElement=labelHref.get(toLabel);
//					if(fromLabel.equals(abstractLabel) && !arcrole.toLowerCase().equals("http://xbrl.org/int/dim/arcrole/all")){
//						ItemList.add(toElement);
//						ArrayList<String> memberList=new ArrayList<String>();
//						utilities.getAllChildren(arcMap, toElement, memberList);
//						if(memberList!=null && memberList.size()!=0){
//							for(int kk=0;kk<memberList.size();kk++){
//								ItemList.add(memberList.get(kk));
//							}
//						}
//					}
//				}
//				//System.out.println("itemList:"+ItemList);
//
//				ArrayList<String> itemMemberListRaw=new ArrayList<String>();
//				for(int hj=0;hj<ItemList.size();hj++){
//					//System.out.println(elementMembers.get(ItemList.get(hj)));
//					//System.out.println(itemMemberListRaw);
//					if(elementMembers.get(ItemList.get(hj))!=null){
//						itemMemberListRaw.addAll(elementMembers.get(ItemList.get(hj)));
//					}
//				}
//				ArrayList<String> itemMemberList=(ArrayList<String>) utilities.removeDuplicate(itemMemberListRaw);
//				//System.out.println("itemMemberList:"+itemMemberList);
//
//				for(int hj=0;hj<axisMemberList.size();hj++){
//					String axisMember=axisMemberList.get(hj);
//					if(!utilities.find(itemMemberList, axisMember)){
//						testResult=testResult+"selfdefine.9>>>>>Error dectected.Filename:"+filename+"; Axis#Member: "+axisMember+";Definition link Line number:"+((ElementWithLocation) definitionLink).getLineNumber()+"\n";
//					}
//				}
//			}
//			return testResult;
//
//
//	}
//
//	//pending
//	public static String xml_two(HashMap<String,String> xbrlFiles) throws Exception{
//
//			String testResult="";
//			ArrayList<Element> contextElements=new ArrayList<Element>();
//			ArrayList<Element> factElements=new ArrayList<Element>();
//
//			//parsing instance
//			String filename=xbrlFiles.get("instance");
//			Element root= utilities.loadxml(filename);
//			ArrayList<Element> allElements=new ArrayList<Element>();
//			utilities.getAllElements(root,allElements);
//			for(int j=0;j<allElements.size();j++){
//				Element elementUnderTest=allElements.get(j);
//				if(elementUnderTest.getName().equals("context")){
//					contextElements.add(elementUnderTest);
//				}
//				if(elementUnderTest.attributeValue("contextRef")!=null){
//					factElements.add(elementUnderTest);
//				}
//			}
//
//			//parsing presentation link
//			String prexmlfilename=xbrlFiles.get("prexml");
//			Element prexmlroot= utilities.loadxml(prexmlfilename);
//			ArrayList<Element> prexmlallElements=new ArrayList<Element>();
//			utilities.getAllElements(prexmlroot,prexmlallElements);
//
//			//validation
//			for(int i=0;i<factElements.size();i++){
//				Element factUnderTest=factElements.get(i);
//				Element contextUnderTest=null;
//				String contextref=factUnderTest.attributeValue("contextRef");
//				for(int j=0;j<contextElements.size();j++){
//					if(contextElements.get(j).attributeValue("id").equals(contextref)){
//						contextUnderTest=contextElements.get(j);
//					}
//				}
//
//				ArrayList<Element> prexmlElements=new ArrayList<Element>();
//				for(int j=0;j<prexmlallElements.size();j++){
//					String factnamespaceprefix=factUnderTest.getNamespacePrefix();
//					String factname=factUnderTest.getName();
//					String factnamenew=factnamespaceprefix+"_"+factname;
//					if(prexmlallElements.get(j).attributeValue("href")!=null){
//
//						if(prexmlallElements.get(j).attributeValue("href").matches("(.*)#"+factnamenew)){
//							prexmlElements.add(prexmlallElements.get(j));
//						}
//					}
//				}
//
//				if(contextUnderTest==null){
//					testResult=testResult+"xml.02>>>>>error detected. Filename:"+filename+"; Line number:"+((ElementWithLocation) factUnderTest).getLineNumber()+"\n";
//
//				}else{
//					if(contextUnderTest.element("entity").element("segment")!=null){
//
//						List<Element> members=contextUnderTest.element("entity").element("segment").elements();
//						for(int k=0;k<members.size();k++){
//							Element memberUnderTest=members.get(k);
//
//							String dimension=memberUnderTest.attributeValue("dimension");
//							String[] dimensionbreak=dimension.split(":");
//							String dimensionUnderTest=dimensionbreak[0]+"_"+dimensionbreak[1];
//							String dimensionmember=memberUnderTest.getText();
//							String[] dimensionmemberbreak=dimensionmember.split(":");
//							String dimensionmemberUnderTest=dimensionmemberbreak[0]+"_"+dimensionmemberbreak[1];
//
//							Boolean prexmlDimensionFound=false;
//							Boolean prexmlDimensionMemberFound=false;
//							for(int m=0;m<prexmlElements.size();m++){
//								Element prexmlElement=prexmlElements.get(m);
//								List<Element> prexmlElementsUnderTest=prexmlElement.getParent().elements("loc");
//								for(int n=0;n<prexmlElementsUnderTest.size();n++){
//									if(prexmlElementsUnderTest.get(n).attributeValue("href").matches("(.*)"+dimensionUnderTest)){
//										prexmlDimensionFound=true;
//									}
//									if(prexmlElementsUnderTest.get(n).attributeValue("href").matches("(.*)"+dimensionmemberUnderTest)){
//										prexmlDimensionMemberFound=true;
//									}
//								}
//
//							}
//							//still false, report an error
//							if(!prexmlDimensionFound || !prexmlDimensionMemberFound){
//								testResult=testResult+"xml.02>>>>>Error dectected.Filename:"+filename+"; Line number:"+((ElementWithLocation) factUnderTest).getLineNumber()+"\n";
//							}
//						}
//					}
//				}
//
//			}
//
//
//			return testResult;
//
//		}
//
//	//lab.02
//
//	public static String lab_two(HashMap<String,String> xbrlFiles,String base) throws Exception{
//
//		String testResult="";
//
//		//parsing label link
//		String labfilename=xbrlFiles.get("labxml");
//		Element labxmlroot= utilities.loadxml(labfilename);
//		List<Element> labelLinks=labxmlroot.elements("labelLink");
//		for(int i=0;i<labelLinks.size();i++){
//
//			Element labelLink=labelLinks.get(i);
//
//			List<Element> locElements=labelLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//			List<Element> labelElements=labelLink.elements("label");
//			HashMap<String,String> labelRole=utilities.getLabeRole(labelElements);
//			HashMap<String,String> labelText=utilities.getLabeText(labelElements);
//			List<Element> arcElements=labelLink.elements("labelArc");
//
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//				String fromlabel=arcElement.attributeValue("from");
//				String fromelement=labelHref.get(fromlabel);
//				String tolabel=arcElement.attributeValue("to");
//				String torole=labelRole.get(tolabel);
//				String text=labelText.get(tolabel);
//
//				if(torole.equals("http://www.xbrl.org/2003/role/label")){
//
//					//System.out.println(fromelement);
//					//System.out.println(text);
//					String[] textSplit=text.split(" ");
//					String namefromlabel="";
//					for(int kk=0;kk<textSplit.length;kk++){
//						String segment=textSplit[kk].replaceAll("[^0-9a-zA-Z]","");
//						namefromlabel=namefromlabel+segment.substring(0,1).toUpperCase()+segment.substring(1, segment.length());
//					}
//					if(!fromelement.split("_")[1].equals(namefromlabel)){
//						testResult=testResult+"lab.02>>>>>error detected. Filename:"+labfilename+"; element name:"+fromelement+"\n";
//					}
//				}
//			}
//		}
//
//
//		return testResult;
//	}
//
//	public static String lab_three(HashMap<String,String> xbrlFiles,String base) throws Exception{
//		String testResult="";
//		List<Element> schemaElements=utilities.getSchemaElements(xbrlFiles);
//
//		//parsing labxml
//		String labxmlfilename=xbrlFiles.get("labxml");
//		Element labxmlroot= utilities.loadxml(labxmlfilename);
//		List<Element> labelLinks=labxmlroot.elements("labelLink");
//		HashMap<String,String> elementTerseLabel=new HashMap<String,String>();
//		HashMap<String,String> elementVerboseLabel=new HashMap<String,String>();
//
//		for(int i=0;i<labelLinks.size();i++){
//			Element labelLink=labelLinks.get(i);
//
//			List<Element> locElements=labelLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//			List<Element> labelElements=labelLink.elements("label");
//			HashMap<String,String> labelRole=utilities.getLabeRole(labelElements);
//			HashMap<String,String> labelText=utilities.getLabeText(labelElements);
//
//			List<Element> arcElements=labelLink.elements("labelArc");
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//				String fromlabel=arcElement.attributeValue("from");
//				String tolabel=arcElement.attributeValue("to");
//				String fromelement=labelHref.get(fromlabel);
//				String torole=labelRole.get(tolabel);
//				String totext=labelText.get(tolabel);
//				if(torole.equals("http://www.xbrl.org/2003/role/verboseLabel")){
//					elementVerboseLabel.put(fromelement, totext);
//				}
//
//			}
//		}
//
//
//		for(int i=0;i<labelLinks.size();i++){
//			Element labelLink=labelLinks.get(i);
//
//			List<Element> locElements=labelLink.elements("loc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(locElements);
//			List<Element> labelElements=labelLink.elements("label");
//			HashMap<String,String> labelRole=utilities.getLabeRole(labelElements);
//			HashMap<String,String> labelText=utilities.getLabeText(labelElements);
//
//			List<Element> arcElements=labelLink.elements("labelArc");
//			for(int j=0;j<arcElements.size();j++){
//				Element arcElement=arcElements.get(j);
//				String fromlabel=arcElement.attributeValue("from");
//				String tolabel=arcElement.attributeValue("to");
//				String fromelement=labelHref.get(fromlabel);
//				String torole=labelRole.get(tolabel);
//				String totext=labelText.get(tolabel);
//				if(torole.equals("http://www.xbrl.org/2003/role/terseLabel")){
//					String verboseText=elementVerboseLabel.get(fromelement);
//					if(verboseText!=null && !verboseText.isEmpty()){
//						if(totext.equals(verboseText)){
//							testResult=testResult+"lab.03>>>>>Error dectected.Filename:"+labxmlfilename+"; Line number:"+((ElementWithLocation) arcElement).getLineNumber()+"\n";
//						}
//					}
//				}
//			}
//		}
//
//
//		return testResult;
//	}
//
//	//pre.04
//	public static String pre_four(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		List<Element> presentationLinks=prexmlroot.elements("presentationLink");
//		ArrayList<String> preroleList=new ArrayList<String>();
//
//		HashMap<String,ArrayList<String>> preRoleHrefs=new HashMap<String,ArrayList<String>>();
//		for(int i=0;i<presentationLinks.size();i++){
//			Element presentationLink=presentationLinks.get(i);
//			String role=presentationLink.attributeValue("role");
//			List<Element> presentationArcs=presentationLink.elements("presentationArc");
//
//			List<Element> locElements=presentationLink.elements("loc");
//			HashMap<String,String> labelHref=new HashMap<String,String>();
//			for(int mm=0;mm<locElements.size();mm++){
//				Element locElement=locElements.get(mm);
//				labelHref.put(locElement.attributeValue("label"),locElement.attributeValue("href").split("#")[1]);
//			}
//
//			for(int j=0;j<presentationArcs.size();j++){
//				Element presentationArc=presentationArcs.get(j);
//				String to=presentationArc.attributeValue("to");
//				String toHref=labelHref.get(to);
//				String preferredLabel=presentationArc.attributeValue("preferredLabel");
//				if(preferredLabel!=null && (preferredLabel.equals("http://www.xbrl.org/2003/role/totalLabel") || preferredLabel.equals("http://www.xbrl.org/2009/role/negatedTotalLabel"))){
//
//					String calxmlfilename=xbrlFiles.get("calxml");
//					Element calxmlroot= utilities.loadxml(calxmlfilename);
//					List<Element> calculationLinks=calxmlroot.elements("calculationLink");
//					Boolean RoleFound=false;
//					Boolean toHrefFound=false;
//					for(int k=0;k<calculationLinks.size();k++){
//						Element calculationLink=calculationLinks.get(k);
//						if(role.equals(calculationLink.attributeValue("role"))){
//							RoleFound=true;
//							List<Element> callocElements=calculationLink.elements("loc");
//							HashMap<String,String> callabelHref=new HashMap<String,String>();
//							for(int jk=0;jk<callocElements.size();jk++){
//								Element callocElement=callocElements.get(jk);
//								callabelHref.put(callocElement.attributeValue("label"),callocElement.attributeValue("href").split("#")[1]);
//							}
//
//							ArrayList<String> actualHrefs=new ArrayList<String>();
//							List<Element> calArcElements=calculationLink.elements("calculationArc");
//							for(int jk=0;jk<calArcElements.size();jk++){
//								Element calArcElement=calArcElements.get(jk);
//								String calrole=calArcElement.attributeValue("arcrole");
//								String fromElement=labelHref.get(calArcElement.attributeValue("from"));
//								if(calrole.equals("http://www.xbrl.org/2003/arcrole/summation-item")){
//									if(fromElement.equals(toHref)){
//										toHrefFound=true;
//									}
//								}
//							}
//						}
//					}
//					if(!RoleFound ||!toHrefFound){
//						testResult=testResult+"pre.04>>>>>error detected. Filename:"+prexmlfilename+"; Line number:"+((ElementWithLocation) presentationLink).getLineNumber()+"\n";
//					}
//
//				}
//			}
//
//		}
//
//
//		return testResult;
//
//	}
//
//	//cal.01
//	public static String cal_one(HashMap<String,String> xbrlFiles) throws Exception{
//
//		String testResult="";
//
//		//parsing presentation link
//		String prexmlfilename=xbrlFiles.get("prexml");
//		Element prexmlroot= utilities.loadxml(prexmlfilename);
//		List<Element> presentationLinks=prexmlroot.elements("presentationLink");
//
//		String calxmlfilename=xbrlFiles.get("calxml");
//		Element calxmlroot= utilities.loadxml(calxmlfilename);
//		List<Element> calculationLinks=calxmlroot.elements("calculationLink");
//
//		for(int k=0;k<calculationLinks.size();k++){
//			Element calculationLink=calculationLinks.get(k);
//			String calrole=calculationLink.attributeValue("role");
//
//			boolean RoleFound = false;
//			boolean ElementsFound = true;
//
//			List<Element> callocElements=calculationLink.elements("loc");
//			ArrayList<String> calroleElements=new ArrayList<String>();
//
//			for(int jk=0;jk<callocElements.size();jk++){
//				Element callocElement=callocElements.get(jk);
//				calroleElements.add(callocElement.attributeValue("href").split("#")[1]);
//			}
//
//			//checking presentation
//			for(int i=0;i<presentationLinks.size();i++){
//				Element presentationLink=presentationLinks.get(i);
//				String prerole=presentationLink.attributeValue("role");
//
//				if(calrole.equals(prerole)){
//					RoleFound=true;
//				}
//
//				List<Element> prelocElements=presentationLink.elements("loc");
//				ArrayList<String> preroleElements=new ArrayList<String>();
//				for(int mm=0;mm<prelocElements.size();mm++){
//					Element prelocElement=prelocElements.get(mm);
//					preroleElements.add(prelocElement.attributeValue("href").split("#")[1]);
//				}
//
//				for(int jk=0;jk<calroleElements.size();jk++){
//					if(!utilities.find(preroleElements, calroleElements.get(jk))){
//						ElementsFound=false;
//					}
//				}
//
//				if(!RoleFound || !ElementsFound){
//
//					testResult=testResult+"cal.01>>>>>error detected. Filename:"+calxmlfilename+"; Line number:"+((ElementWithLocation) calculationLink).getLineNumber()+"\n";
//
//				}
//
//			}
//
//
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
//}
