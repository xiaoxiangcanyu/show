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
//import java.util.*;
//
//public class contextGenerate {
//
//	public static String sourceMode;
//	public static String generateMode;
//	public static Boolean factbasedMode;
//	public static String instanceName;
//	public static String CIK;
//	public static String scheme;
//	public static String schemaFile;
//	public static String definitionFile;
//	public static String definitionFolder="defFolder";
//	public static String instanceFile;
//	public static String[] instantList;
//	public static String[] durationList;
//	public static ArrayList<String> contextIdList=new ArrayList<String>();
//	public static HashMap<String,String[]> typedDimensionConfig=new HashMap<String,String[]>();
//	public static ArrayList<String> instanceContextIdList=new ArrayList<String>();
//
//	public static void main(String[] args) throws Exception {
//
//		setTypedDimension();
//
//
//
//		//handle user input
//		String instantListInput="";
//		String durationListInput="";
//		Scanner sc = new Scanner(System.in);
//		Boolean testmode=false;
//		if(testmode){
//			factbasedMode=false;
//			sourceMode="folder";
//			generateMode="scenario";
//			instanceName="circ_2015-12-31";
//			scheme="circ";
//			CIK="0001509646";
//			instantListInput="2012-12-31";
//			durationListInput="2012-01-01_2012-12-31";
//		}else{
//			System.out.print(">>>>>Enable factbased mode(Y/N):");
//			if(sc.nextLine().equals("Y")){
//				factbasedMode=true;
//			}else{
//				factbasedMode=false;
//			}
//			System.out.print(">>>>>Please select source mode(file/folder):");
//			sourceMode=sc.nextLine();
//			System.out.print(">>>>>Please select generate mode(segment/scenario):");
//			generateMode=sc.nextLine();
//	        System.out.print(">>>>>Please input the instance name(xxxx-yyyymmdd):");
//			instanceName=sc.nextLine();
//			System.out.print(">>>>>Please input the scheme(example:http://www.sec.gov/CIK):");
//			scheme=sc.nextLine();
//	        System.out.print(">>>>>Please input the identifier number:");
//			CIK=sc.nextLine();
//			System.out.print(">>>>>Please input the instant list(example:2012-12-31,2013-12-31,2014-12-31):");
//			instantListInput=sc.nextLine();
//			System.out.print(">>>>>Please input the duration list(example:2012-01-01_2012-12-31,2013-01-01_2013-12-31,2014-01-01_2014-12-31):");
//			durationListInput=sc.nextLine();
//		}
//
//		instantList=instantListInput.split(",");
//		durationList=durationListInput.split(",");
//
//
//		//parameterInit
//		schemaFile=instanceName+".xsd";
//
//		instanceFile=instanceName+".xml";
//
//		//generate instance
//		generateInstance();
//
//		//Delete unused contexts -- Pending
//
//	}
//
//	public static void generateAxisContext(Element root) throws Exception{
//
//		//System.out.println("Start reading definition file.");
//		List<Element> definitionLinks=new ArrayList<Element>();
//
//		if(sourceMode.equals("file")){
//			definitionFile=instanceName+"_def.xml";
//			Element defxmlroot= utilities.loadxml(definitionFile);
//			definitionLinks=defxmlroot.elements("definitionLink");
//		}else{
//			ArrayList<String> definitionFiles=new ArrayList<String>();
//			utilities.getAllfiles(System.getProperty("user.dir")+"\\"+definitionFolder,definitionFiles);
//			for(int jk=0;jk<definitionFiles.size();jk++){
//				Element defxmlroot= utilities.loadxml(definitionFiles.get(jk));
//				List<Element> definitionFileLinks=defxmlroot.elements("definitionLink");
//				definitionLinks.addAll(definitionFileLinks);
//
//			}
//		}
//
//
//
//
//		for(int i=0;i<definitionLinks.size();i++){
//
//			//System.out.println("---");
//
//
//			ArrayList<String> AxisList=new ArrayList<String>();
//			HashMap<String,ArrayList<String>> axisMemberList=new HashMap<String,ArrayList<String>>();
//
//			Element definitionLink=definitionLinks.get(i);
//			//System.out.println(definitionLink.attributeValue("role"));
//
//			List<Element> definitionLocs=definitionLink.elements("loc");
//			List<Element> definitionArcs=definitionLink.elements("definitionArc");
//			HashMap<String,String> labelHref=utilities.getLabeHref(definitionLocs);
//			HashMap<String,ArrayList<String>> arcMap=utilities.getArcMap(definitionArcs,labelHref);
//
//
//			//Extract axis list
//			for(int j=0;j<definitionArcs.size();j++){
//				Element definitionArc=definitionArcs.get(j);
//				String arcrole=definitionArc.attributeValue("arcrole");
//				//System.out.println(arcrole);
//				String toLabel=definitionArc.attributeValue("to");
//				String toElement=labelHref.get(toLabel);
//				if(arcrole.toLowerCase().equals("http://xbrl.org/int/dim/arcrole/hypercube-dimension")){
//					AxisList.add(toElement);
//				}
//			}
//			Collections.sort(AxisList);
//
//
//			//extract axis member list
//			String typedDimension="";
//			int jj=0;
//			while(jj<AxisList.size()){
//
//				ArrayList<String> domainList=arcMap.get(AxisList.get(jj));
//
//				if(domainList!=null){
//					String domain=domainList.get(0);
//					ArrayList<String> memberList=new ArrayList<String>();
//					utilities.getAllChildren(arcMap, domain, memberList);
//					//System.out.println("memberList: "+memberList);
//					if(memberList!=null && memberList.size()!=0){
//						Collections.sort(memberList);
//						axisMemberList.put(AxisList.get(jj), memberList);
//						jj++;
//					}else{
//						//System.out.println("Before remove: "+AxisList.size());
//						AxisList.remove(jj);
//						//System.out.println("After remove: "+AxisList.size());
//					}
//				}else{
//					typedDimension=AxisList.get(jj);
//					AxisList.remove(jj);
//
//					//System.out.println("typedDimension:"+typedDimension);
//				}
//
//			}
//
//
//
//			ArrayList<String> ccAxisList=(ArrayList<String>) utilities.removeDuplicate(AxisList);
//			int axisCount=ccAxisList.size();
//			//System.out.println(ccAxisList.size());
//			//System.out.println(ccAxisList);
//			List<Integer> iL = new ArrayList<Integer>();
//			for(int im=1;im<ccAxisList.size();im++){
//
//				plzh(root, axisMemberList, ccAxisList,iL,im,axisCount,typedDimension);
//
//			}
//
//			//System.out.println(ccAxisList);
//			//System.out.println(axisMemberList);
//
//			generateContext(root,ccAxisList,axisMemberList,axisCount,typedDimension);
//			//System.out.println("number of contexts: "+contextIdList.size());
//
//		}
//
//	}
//
//    public static void plzh(Element root, HashMap<String,ArrayList<String>> axisMemberList, ArrayList<String> axisFullList,  List<Integer> iL, int m,int axisCount,String typedDimension) {
//
//
//    	//System.out.println("axisShortList:"+axisShortList);
//    	//System.out.println("iL:"+iL);
//    	//System.out.println("m:"+m);
//    	//System.out.println("---");
//
//        if(m == 0) {
//
//        	ArrayList<String> axisShortList=new ArrayList<String>();
//        	for(int k=0;k<iL.size();k++){
//        		axisShortList.add(axisFullList.get(iL.get(k)));
//        	}
//
//            Collections.sort(axisShortList);
//            ArrayList<String> ccAxisList=(ArrayList<String>) utilities.removeDuplicate(axisShortList);
//
//            //System.out.println(axisShortList);
//
//            generateContext(root,ccAxisList,axisMemberList,axisCount,typedDimension);
//
//            //System.out.println("number of contexts-end: "+contextIdList.size());
//
//            axisShortList.clear();
//        }else{
//	        List<Integer> iL2;
//	        for(int i = 0; i < axisFullList.size(); i++) {
//	            iL2 = new ArrayList<Integer>();
//	            iL2.addAll(iL);
//	            if(!iL.contains(i)) {
//	                //axisShortList.add(axisFullList.get(i));
//	                iL2.add(i);
//	                plzh(root,axisMemberList,axisFullList, iL2, m-1,axisCount,typedDimension);
//	            }
//	        }
//        }
//    }
//
//
//	public static void generateInstance() throws Exception{
//
//		System.out.println("Start generating instance file.");
//
//		//Construct instance
//		String instancefilebefore="input_instance.xml";
//
//		Locator locator = new LocatorImpl();
//		DocumentFactory docFactory = new DocumentFactoryWithLocator(locator);
//	    SAXReaderEnhanced saxReader = new SAXReaderEnhanced(docFactory,locator);
//	    Document document = saxReader.read(new File(instancefilebefore));
//	    Element root=document.getRootElement();
//	    ArrayList<Element> allElements=new ArrayList<Element>();
//	    utilities.getAllElements(root, allElements);
//
//	    for(int i=0;i<allElements.size();i++){
//	    	if(allElements.get(i).attributeValue("contextRef")!=null){
//	    		instanceContextIdList.add(allElements.get(i).attributeValue("contextRef"));
//	    	}
//
//	    }
//
//	    if(factbasedMode){
//	    	System.out.println("No of contexts used by facts :"+utilities.removeDuplicate(instanceContextIdList).size());
//	    }
//
//	    //System.out.println(instanceContextIdList);
//
//	    //construct context
//	    generateInstantContext(root);
//	    generateDurationContext(root);
//	    generateAxisContext(root);
//
//	    List<Element> contextList=root.elements("context");
//	    System.out.println("Number of Contexts: "+contextList.size());
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(instanceFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Finish generating instance file.");
//
//	}
//
//	public static void generateInstantContext(Element root){
//
//		for(int ig=0;ig<instantList.length;ig++){
//
//			String instant=instantList[ig];
//			String instantId="As_Of_"+instant.replace("-", "_");
//
//			String contextId=instantId;
//
//			Boolean toGenerate=true;
//			if(factbasedMode){
//				if(!utilities.find(instanceContextIdList,contextId)){
//					toGenerate=false;
//				}
//			}
//
//			if(toGenerate){
//				Element context=root.addElement("xbrli:context");
//				context.addAttribute("id",contextId);
//
//				Element entityIdentifier=context.addElement("xbrli:entity").addElement("xbrli:identifier");
//				entityIdentifier.addAttribute("scheme", scheme);
//				entityIdentifier.setText(CIK);
//
//				context.addElement("xbrli:period").addElement("xbrli:instant").setText(instant);;
//			}
//
//
//		}
//
//	}
//
//	public static void generateDurationContext(Element root){
//
//		for(int ig=0;ig<durationList.length;ig++){
//
//			String duration=durationList[ig];
//			String durationId="From_"+duration.split("_")[0].replace("-", "_")+"_To_"+duration.split("_")[1].replace("-", "_");
//
//			String contextId=durationId;
//
//			Boolean toGenerate=true;
//			if(factbasedMode){
//				if(!utilities.find(instanceContextIdList,contextId)){
//					toGenerate=false;
//				}
//			}
//
//			if(toGenerate){
//
//				Element context=root.addElement("xbrli:context");
//				context.addAttribute("id",contextId);
//
//				Element entityIdentifier=context.addElement("xbrli:entity").addElement("xbrli:identifier");
//				entityIdentifier.addAttribute("scheme", scheme);
//				entityIdentifier.setText(CIK);
//
//				Element period=context.addElement("xbrli:period");
//				period.addElement("xbrli:startDate").setText(duration.split("_")[0]);
//				period.addElement("xbrli:endDate").setText(duration.split("_")[1]);
//
//			}
//
//
//
//		}
//
//	}
//
//
//	public static void setTypedDimension() throws IOException{
//
//
//        FileInputStream f = new FileInputStream("contextConfig/typedDimensionConfig.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		String schemaLocation="";
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			String typedDimension=lineSplit.get(0);
//			String memberElement=lineSplit.get(1);
//			String memberPrefix=lineSplit.get(2);
//			String repeatTimes=lineSplit.get(3);
//			typedDimensionConfig.put(typedDimension, new String[]{memberElement,memberPrefix,repeatTimes});
//
//			line = dr.readLine();
//		}
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateContext(Element root, ArrayList<String> AxisList, HashMap<String,ArrayList<String>> axisMemberList,int axisCount,String typedDimension){
//
//		//System.out.println(typedDimension);
//
//		if(AxisList.size()!=0){
//			//generate array
//			ArrayList<ArrayList<String[]>> axisArray=new ArrayList<ArrayList<String[]>>();
//			generateArray(axisArray,AxisList,axisMemberList,0);
//			//System.out.println("axislist size:"+AxisList.size()+";axisArray size:"+axisArray.size());
//
//			//generate instant Context
//			for(int ig=0;ig<instantList.length;ig++){
//
//				String instant=instantList[ig];
//				String instantId="As_Of_"+instant.replace("-", "_");
//
//				for(int bv=0;bv<axisArray.size();bv++){
//
//
//
//					ArrayList<String[]> axisCombination=axisArray.get(bv);
//
//					if(!typedDimension.isEmpty() && axisCombination.size()==axisCount && typedDimensionConfig.get(typedDimension)!=null){
//						String memberElementname=typedDimensionConfig.get(typedDimension)[0];
//						String memberPrefix=typedDimensionConfig.get(typedDimension)[1];
//						int repeatTimes=Integer.valueOf(typedDimensionConfig.get(typedDimension)[2]);
//
//						for(int i=1;i<=repeatTimes;i++){
//
//							String contextId=instantId;
//
//							for(int ck=0;ck<axisCombination.size();ck++){
//
//								String[] axisMember=axisCombination.get(ck);
//								String axis=axisMember[0].split("_")[1];
//								String member=axisMember[1].split("_")[1];
//								contextId=contextId+"_"+axis+"_"+member;
//							}
//							contextId=contextId+"_"+typedDimension.split("_")[1]+"_"+memberPrefix.replace(".", "")+String.valueOf(i);
//
//
//							Boolean toGenerate=true;
//							if(factbasedMode){
//								if(!utilities.find(instanceContextIdList,contextId)){
//									toGenerate=false;
//								}
//							}
//
//							if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//								contextIdList.add(contextId);
//
//								Element context=root.addElement("xbrli:context");
//								context.addAttribute("id",contextId);
//
//								Element entity=context.addElement("xbrli:entity");
//
//								Element entityIdentifier=entity.addElement("xbrli:identifier");
//								entityIdentifier.addAttribute("scheme", scheme);
//								entityIdentifier.setText(CIK);
//
//								if(generateMode.equals("segment")){
//
//									Element segment=entity.addElement("xbrli:segment");
//
//									for(int ck=0;ck<axisCombination.size();ck++){
//
//										String[] axisMember=axisCombination.get(ck);
//										String axis=axisMember[0].replace("_", ":");
//										String member=axisMember[1].replace("_", ":");
//
//										//handling array
//										Element memberElement=segment.addElement("xbrldi:explicitMember");
//										memberElement.addAttribute("dimension",axis);
//										memberElement.setText(member);
//									}
//
//									Element memberElement=segment.addElement("xbrldi:typedMember");
//									memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//									memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//								}else{
//									Element scenario=context.addElement("xbrli:scenario");
//
//									for(int ck=0;ck<axisCombination.size();ck++){
//										String[] axisMember=axisCombination.get(ck);
//										String axis=axisMember[0].replace("_", ":");
//										String member=axisMember[1].replace("_", ":");
//
//										//handling array
//										Element memberElement=scenario.addElement("xbrldi:explicitMember");
//										memberElement.addAttribute("dimension",axis);
//										memberElement.setText(member);
//									}
//
//									Element memberElement=scenario.addElement("xbrldi:typedMember");
//									memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//									memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//								}
//
//								context.addElement("xbrli:period").addElement("xbrli:instant").setText(instant);
//							}
//
//
//
//						}
//					}else{
//
//						String contextId=instantId;
//
//						for(int ck=0;ck<axisCombination.size();ck++){
//
//							String[] axisMember=axisCombination.get(ck);
//							String axis=axisMember[0].split("_")[1];
//							String member=axisMember[1].split("_")[1];
//							contextId=contextId+"_"+axis+"_"+member;
//						}
//
//						Boolean toGenerate=true;
//						if(factbasedMode){
//							if(!utilities.find(instanceContextIdList,contextId)){
//								toGenerate=false;
//							}
//						}
//
//						if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//							contextIdList.add(contextId);
//
//							Element context=root.addElement("xbrli:context");
//							context.addAttribute("id",contextId);
//
//							Element entity=context.addElement("xbrli:entity");
//
//							Element entityIdentifier=entity.addElement("xbrli:identifier");
//							entityIdentifier.addAttribute("scheme", scheme);
//							entityIdentifier.setText(CIK);
//
//							if(generateMode.equals("segment")){
//
//								Element segment=entity.addElement("xbrli:segment");
//
//								for(int ck=0;ck<axisCombination.size();ck++){
//
//									String[] axisMember=axisCombination.get(ck);
//									String axis=axisMember[0].replace("_", ":");
//									String member=axisMember[1].replace("_", ":");
//
//									//handling array
//									Element memberElement=segment.addElement("xbrldi:explicitMember");
//									memberElement.addAttribute("dimension",axis);
//									memberElement.setText(member);
//								}
//							}else{
//								Element scenario=context.addElement("xbrli:scenario");
//
//								for(int ck=0;ck<axisCombination.size();ck++){
//									String[] axisMember=axisCombination.get(ck);
//									String axis=axisMember[0].replace("_", ":");
//									String member=axisMember[1].replace("_", ":");
//
//									//handling array
//									Element memberElement=scenario.addElement("xbrldi:explicitMember");
//									memberElement.addAttribute("dimension",axis);
//									memberElement.setText(member);
//								}
//
//							}
//
//							context.addElement("xbrli:period").addElement("xbrli:instant").setText(instant);
//						}
//
//					}
//
//
//
//
//				}
//
//			}
//
//
//			//generate duration Context
//			for(int ig=0;ig<durationList.length;ig++){
//
//				String duration=durationList[ig];
//				String durationId="From_"+duration.split("_")[0].replace("-", "_")+"_To_"+duration.split("_")[1].replace("-", "_");
//
//				for(int bv=0;bv<axisArray.size();bv++){
//
//
//
//					ArrayList<String[]> axisCombination=axisArray.get(bv);
//
//					if(!typedDimension.isEmpty() && axisCombination.size()==axisCount && typedDimensionConfig.get(typedDimension)!=null){
//						String memberElementname=typedDimensionConfig.get(typedDimension)[0];
//						String memberPrefix=typedDimensionConfig.get(typedDimension)[1];
//						int repeatTimes=Integer.valueOf(typedDimensionConfig.get(typedDimension)[2]);
//
//						for(int i=1;i<=repeatTimes;i++){
//
//							String contextId=durationId;
//
//							for(int ck=0;ck<axisCombination.size();ck++){
//
//								String[] axisMember=axisCombination.get(ck);
//								String axis=axisMember[0].split("_")[1];
//								String member=axisMember[1].split("_")[1];
//								contextId=contextId+"_"+axis+"_"+member;
//
//							}
//
//							contextId=contextId+"_"+typedDimension.split("_")[1]+"_"+memberPrefix.replace(".", "")+String.valueOf(i);
//
//
//							Boolean toGenerate=true;
//							if(factbasedMode){
//								if(!utilities.find(instanceContextIdList,contextId)){
//									toGenerate=false;
//								}
//							}
//
//							if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//								contextIdList.add(contextId);
//
//								Element context=root.addElement("xbrli:context");
//								context.addAttribute("id",contextId);
//
//								Element entity=context.addElement("xbrli:entity");
//
//								Element entityIdentifier=entity.addElement("xbrli:identifier");
//								entityIdentifier.addAttribute("scheme", scheme);
//								entityIdentifier.setText(CIK);
//
//								if(generateMode.equals("segment")){
//
//									Element segment=entity.addElement("xbrli:segment");
//
//
//									for(int ck=0;ck<axisCombination.size();ck++){
//
//										String[] axisMember=axisCombination.get(ck);
//										String axis=axisMember[0].replace("_", ":");
//										String member=axisMember[1].replace("_", ":");
//
//										//handling array
//										Element memberElement=segment.addElement("xbrldi:explicitMember");
//										memberElement.addAttribute("dimension",axis);
//										memberElement.setText(member);
//
//									}
//
//									Element memberElement=segment.addElement("xbrldi:typedMember");
//									memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//									memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//								}else{
//
//									Element scenario=context.addElement("xbrli:scenario");
//
//									for(int ck=0;ck<axisCombination.size();ck++){
//
//										String[] axisMember=axisCombination.get(ck);
//										String axis=axisMember[0].replace("_", ":");
//										String member=axisMember[1].replace("_", ":");
//
//										//handling array
//										Element memberElement=scenario.addElement("xbrldi:explicitMember");
//										memberElement.addAttribute("dimension",axis);
//										memberElement.setText(member);
//
//									}
//
//									Element memberElement=scenario.addElement("xbrldi:typedMember");
//									memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//									memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//								}
//
//								Element period=context.addElement("xbrli:period");
//								period.addElement("xbrli:startDate").setText(duration.split("_")[0]);
//								period.addElement("xbrli:endDate").setText(duration.split("_")[1]);
//
//							}
//
//
//						}
//					}else{
//
//						String contextId=durationId;
//
//						for(int ck=0;ck<axisCombination.size();ck++){
//
//							String[] axisMember=axisCombination.get(ck);
//							String axis=axisMember[0].split("_")[1];
//							String member=axisMember[1].split("_")[1];
//							contextId=contextId+"_"+axis+"_"+member;
//
//						}
//
//
//
//						Boolean toGenerate=true;
//						if(factbasedMode){
//							if(!utilities.find(instanceContextIdList,contextId)){
//								toGenerate=false;
//							}
//						}
//
//						if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//							contextIdList.add(contextId);
//
//							Element context=root.addElement("xbrli:context");
//							context.addAttribute("id",contextId);
//
//							Element entity=context.addElement("xbrli:entity");
//
//							Element entityIdentifier=entity.addElement("xbrli:identifier");
//							entityIdentifier.addAttribute("scheme", scheme);
//							entityIdentifier.setText(CIK);
//
//							if(generateMode.equals("segment")){
//
//								Element segment=entity.addElement("xbrli:segment");
//
//
//								for(int ck=0;ck<axisCombination.size();ck++){
//
//									String[] axisMember=axisCombination.get(ck);
//									String axis=axisMember[0].replace("_", ":");
//									String member=axisMember[1].replace("_", ":");
//
//									//handling array
//									Element memberElement=segment.addElement("xbrldi:explicitMember");
//									memberElement.addAttribute("dimension",axis);
//									memberElement.setText(member);
//
//								}
//
//							}else{
//
//								Element scenario=context.addElement("xbrli:scenario");
//
//								for(int ck=0;ck<axisCombination.size();ck++){
//
//									String[] axisMember=axisCombination.get(ck);
//									String axis=axisMember[0].replace("_", ":");
//									String member=axisMember[1].replace("_", ":");
//
//									//handling array
//									Element memberElement=scenario.addElement("xbrldi:explicitMember");
//									memberElement.addAttribute("dimension",axis);
//									memberElement.setText(member);
//
//								}
//
//
//							}
//
//							Element period=context.addElement("xbrli:period");
//							period.addElement("xbrli:startDate").setText(duration.split("_")[0]);
//							period.addElement("xbrli:endDate").setText(duration.split("_")[1]);
//
//						}
//
//
//					}
//
//
//
//
//
//				}
//			}
//	}else if(!typedDimension.isEmpty()){
//
//
//
//		//generate instant Context
//		for(int ig=0;ig<instantList.length;ig++){
//
//			String instant=instantList[ig];
//			String instantId="As_Of_"+instant.replace("-", "_");
//
//
//			//System.out.println(typedDimension);
//
//
//				if( typedDimensionConfig.get(typedDimension)!=null){
//
//
//					String memberElementname=typedDimensionConfig.get(typedDimension)[0];
//					String memberPrefix=typedDimensionConfig.get(typedDimension)[1];
//					int repeatTimes=Integer.valueOf(typedDimensionConfig.get(typedDimension)[2]);
//
//					for(int i=1;i<=repeatTimes;i++){
//
//						String contextId=instantId;
//
//
//						contextId=contextId+"_"+typedDimension.split("_")[1]+"_"+memberPrefix.replace(".", "")+String.valueOf(i);
//
//
//						Boolean toGenerate=true;
//						if(factbasedMode){
//							if(!utilities.find(instanceContextIdList,contextId)){
//								toGenerate=false;
//							}
//						}
//
//						if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//							contextIdList.add(contextId);
//
//							Element context=root.addElement("xbrli:context");
//							context.addAttribute("id",contextId);
//
//							Element entity=context.addElement("xbrli:entity");
//
//							Element entityIdentifier=entity.addElement("xbrli:identifier");
//							entityIdentifier.addAttribute("scheme", scheme);
//							entityIdentifier.setText(CIK);
//
//							if(generateMode.equals("segment")){
//
//								Element segment=entity.addElement("xbrli:segment");
//
//
//
//								Element memberElement=segment.addElement("xbrldi:typedMember");
//								memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//								memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//							}else{
//								Element scenario=context.addElement("xbrli:scenario");
//
//
//
//								Element memberElement=scenario.addElement("xbrldi:typedMember");
//								memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//								memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//							}
//
//							context.addElement("xbrli:period").addElement("xbrli:instant").setText(instant);
//						}
//
//
//
//					}
//				}
//
//
//
//
//
//		}
//
//
//		//generate duration Context
//		for(int ig=0;ig<durationList.length;ig++){
//
//			String duration=durationList[ig];
//			String durationId="From_"+duration.split("_")[0].replace("-", "_")+"_To_"+duration.split("_")[1].replace("-", "_");
//
//
//
//
//
//
//
//				if(typedDimensionConfig.get(typedDimension)!=null){
//					String memberElementname=typedDimensionConfig.get(typedDimension)[0];
//					String memberPrefix=typedDimensionConfig.get(typedDimension)[1];
//					int repeatTimes=Integer.valueOf(typedDimensionConfig.get(typedDimension)[2]);
//
//					for(int i=1;i<=repeatTimes;i++){
//
//						String contextId=durationId;
//
//
//
//						contextId=contextId+"_"+typedDimension.split("_")[1]+"_"+memberPrefix.replace(".", "")+String.valueOf(i);
//
//
//						Boolean toGenerate=true;
//						if(factbasedMode){
//							if(!utilities.find(instanceContextIdList,contextId)){
//								toGenerate=false;
//							}
//						}
//
//						if(!utilities.find(contextIdList,contextId) && toGenerate){
//
//							contextIdList.add(contextId);
//
//							Element context=root.addElement("xbrli:context");
//							context.addAttribute("id",contextId);
//
//							Element entity=context.addElement("xbrli:entity");
//
//							Element entityIdentifier=entity.addElement("xbrli:identifier");
//							entityIdentifier.addAttribute("scheme", scheme);
//							entityIdentifier.setText(CIK);
//
//							if(generateMode.equals("segment")){
//
//								Element segment=entity.addElement("xbrli:segment");
//
//
//								Element memberElement=segment.addElement("xbrldi:typedMember");
//								memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//								memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//							}else{
//
//								Element scenario=context.addElement("xbrli:scenario");
//
//
//
//								Element memberElement=scenario.addElement("xbrldi:typedMember");
//								memberElement.addAttribute("dimension",typedDimension.split("_")[0]+":"+typedDimension.split("_")[1]);
//								memberElement.addElement(memberElementname.split("_")[0]+":"+memberElementname.split("_")[1]).setText(memberPrefix+i);
//
//
//							}
//
//							Element period=context.addElement("xbrli:period");
//							period.addElement("xbrli:startDate").setText(duration.split("_")[0]);
//							period.addElement("xbrli:endDate").setText(duration.split("_")[1]);
//
//						}
//
//
//					}
//				}
//
//
//
//
//
//			}
//		}
//
//
//
//
//	}
//
//	public static void generateArray(ArrayList<ArrayList<String[]>> axisArray, ArrayList<String> axislist,HashMap<String,ArrayList<String>> axisMemberList, int Count){
//
//
//
//		int axisArraySize=axisArray.size();
//
//		if(axisArraySize==0){
//
//			if(axislist.size()>=2){
//				String axis1=axislist.get(0);
//				ArrayList<String> memberList1=axisMemberList.get(axis1);
//				String axis2=axislist.get(1);
//				ArrayList<String> memberList2=axisMemberList.get(axis2);
//				for(int i=0;i<memberList1.size();i++){
//					for(int j=0;j<memberList2.size();j++){
//						ArrayList<String[]> axisCombination=new ArrayList<String[]>();
//						axisCombination.add(new String[]{axis1,memberList1.get(i)});
//						axisCombination.add(new String[]{axis2,memberList2.get(j)});
//						//System.out.println(axisCombination);
//						axisArray.add(axisCombination);
//					}
//				}
//				//System.out.println(axislist);
//				generateArray(axisArray,axislist,axisMemberList, 2);
//			}else{
//				String axis1=axislist.get(0);
//				ArrayList<String> memberList1=axisMemberList.get(axis1);
//				for(int i=0;i<memberList1.size();i++){
//						ArrayList<String[]> axisCombination=new ArrayList<String[]>();
//						axisCombination.add(new String[]{axis1,memberList1.get(i)});
//						axisArray.add(axisCombination);
//				}
//			}
//
//		}else if(Count<axislist.size()){
//
//			ArrayList<ArrayList<String[]>> axisArrayTemp=new ArrayList<ArrayList<String[]>>();
//			String axis3=axislist.get(Count);
//			ArrayList<String> memberList3=axisMemberList.get(axis3);
//			for(int i=0;i<memberList3.size();i++){
//				for(int j=0;j<axisArray.size();j++){
//
//					ArrayList<String[]> axisCombination=new ArrayList<String[]>();
//					for(int hj=0;hj<axisArray.get(j).size();hj++){
//						axisCombination.add(axisArray.get(j).get(hj));
//					}
//
//					//System.out.println(axisCombination);
//					axisCombination.add(new String[]{axis3,memberList3.get(i)});
//					//System.out.println(axisCombination);
//
//					axisArrayTemp.add(axisCombination);
//				}
//			}
//
//			//System.out.println(axisArray.size()+","+memberList3.size()+","+axisArrayTemp.size());
//			axisArray.clear();
//			for(int j=0;j<axisArrayTemp.size();j++){
//				ArrayList<String[]> axisCombination=axisArrayTemp.get(j);
//				axisArray.add(axisCombination);
//			}
//
//			//System.out.println(axisArray.size());
//
//			generateArray(axisArray,axislist,axisMemberList, Count+1);
//
//		}
//
//	}
//
//
//}
