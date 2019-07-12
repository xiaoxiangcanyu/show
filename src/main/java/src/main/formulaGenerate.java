//package src.main;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import xbrl.utilitiesCSVFile;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
//public class formulaGenerate {
//
//	public static String generateDate;
//	public static String casDate;
//	public static String casEntryFile;
//	public static String casEntryNamespace;
//	public static String formulaEntryFile;
//	public static String formulaEntryNamespace;
//	public static String formulaRoleFile;
//	public static String formulaRoleFileSimple;
//	public static String formulaRoleNamespace;
//	public static String roleURI_axis;
//	public static String roleURI_cro;
//	public static String roleURI_eps;
//	public static String roleURI_equ;
//	public static String roleURI_neg;
//	public static String roleURI_pos;
//	public static String roleURI_per;
//	public static String roleID_axis;
//	public static String roleID_cro;
//	public static String roleID_eps;
//	public static String roleID_equ;
//	public static String roleID_neg;
//	public static String roleID_pos;
//	public static String roleID_per;
//	public static String formulaAxiFile;
//	public static String formulaCroFile;
//	public static String formulaEpsFile;
//	public static String formulaEquFile;
//	public static String formulaNegFile;
//	public static String formulaPosFile;
//	public static String formulaPerFile;
//	public static String csvFileAxi;
//	public static String csvFileCro;
//	public static String csvFileEps;
//	public static String csvFileEqu;
//	public static String csvFileNeg;
//	public static String csvFilePos;
//	public static String csvFilePer;
//	public static String messageAxi;
//	public static String messageCro;
//	public static String messageEps;
//	public static String messageEqu;
//	public static String messageNeg;
//	public static String messagePos;
//	public static String messagePer;
//
//	public static void parameterInit(){
//
//		casEntryFile="http://xbrl.mof.gov.cn/taxonomy/"+casDate+"/cas_entry_point_"+casDate+".xsd";
//		casEntryNamespace="http://xbrl.mof.gov.cn/taxonomy/"+casDate+"/cas/cas_entry_point";
//		formulaEntryFile="formula_full_cas_entry_point_"+generateDate+".xsd";
//		formulaEntryNamespace="http://xbrl.ifrs.org/taxonomy/"+generateDate+"/ifrs/for_cas_entry_point";
//		formulaRoleFile="formula/rol_for_cas_"+generateDate+".xsd";
//		formulaRoleFileSimple="rol_for_cas_"+generateDate+".xsd";
//		formulaRoleNamespace="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_formula_"+generateDate;
//		roleURI_axis="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_axi_"+generateDate;
//		roleURI_cro="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_cro_"+generateDate;
//		roleURI_eps="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_eps_"+generateDate;
//		roleURI_equ="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_equ_"+generateDate;
//		roleURI_neg="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_neg_"+generateDate;
//		roleURI_pos="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_pos_"+generateDate;
//		roleURI_per="http://xbrl.mof.gov.cn/taxonomy/"+generateDate+"/cas/rol_cas_per_"+generateDate;
//		roleID_axis="for_cas_axi_"+generateDate+"_role";
//		roleID_cro="for_cas_cro_"+generateDate+"_role";
//		roleID_eps="for_cas_eps_"+generateDate+"_role";
//		roleID_equ="for_cas_equ_"+generateDate+"_role";
//		roleID_neg="for_cas_neg_"+generateDate+"_role";
//		roleID_pos="for_cas_pos_"+generateDate+"_role";
//		roleID_per="for_cas_per_"+generateDate+"_role";
//		formulaAxiFile="formula/for_cas-axi_"+generateDate+".xml";
//		formulaCroFile="formula/for_cas-cro_"+generateDate+".xml";
//		formulaEpsFile="formula/for_cas-eps_"+generateDate+".xml";
//		formulaEquFile="formula/for_cas-equ_"+generateDate+".xml";
//		formulaNegFile="formula/for_cas-neg_"+generateDate+".xml";
//		formulaPosFile="formula/for_cas-pos_"+generateDate+".xml";
//		formulaPerFile="formula/for_cas-per_"+generateDate+".xml";
//		messageAxi="Total of reported values for children members is not equal to reported value for parent memeber";
//		messageCro="Reported value at end of period is not equal to sum of reported value at beginning of period and changes in that value during period";
//		messageEps="Calculated EPS is not equal to reported value";
//		messageEqu="Reported values for alternative tagging of same economic concept are not equal";
//		messageNeg="Reported value exceeds 0";
//		messagePos="Reported value is below 0";
//		messagePer="Reported value exceeds 100%";
//
//	}
//
//	public static void genenrateEntryFile() throws IOException{
//
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("xsd:schema");
//        root.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
//        root.addNamespace("link", "http://www.xbrl.org/2003/linkbase");
//        root.addNamespace("xlink", "http://www.w3.org/1999/xlink");
//        root.addNamespace("for_cas_entry_point", formulaEntryNamespace);
//        root.addAttribute("targetNamespace", formulaEntryNamespace);
//        root.addAttribute("elementFormDefault", "qualified");
//
//
//        //construct annoctation&appinfo
//        Element annotation=root.addElement("xsd:annotation");
//        Element appinfo= annotation.addElement("xsd:appinfo");
//
//        Element LB_axi=appinfo.addElement("link:linkbaseRef");
//        LB_axi.addAttribute("xlink:type", "simple");
//        LB_axi.addAttribute("xlink:href", formulaAxiFile);
//        LB_axi.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_cro=appinfo.addElement("link:linkbaseRef");
//        LB_cro.addAttribute("xlink:type", "simple");
//        LB_cro.addAttribute("xlink:href", formulaCroFile);
//        LB_cro.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_eps=appinfo.addElement("link:linkbaseRef");
//        LB_eps.addAttribute("xlink:type", "simple");
//        LB_eps.addAttribute("xlink:href", formulaEpsFile);
//        LB_eps.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_equ=appinfo.addElement("link:linkbaseRef");
//        LB_equ.addAttribute("xlink:type", "simple");
//        LB_equ.addAttribute("xlink:href", formulaEquFile);
//        LB_equ.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_neg=appinfo.addElement("link:linkbaseRef");
//        LB_neg.addAttribute("xlink:type", "simple");
//        LB_neg.addAttribute("xlink:href", formulaNegFile);
//        LB_neg.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_pos=appinfo.addElement("link:linkbaseRef");
//        LB_pos.addAttribute("xlink:type", "simple");
//        LB_pos.addAttribute("xlink:href", formulaPosFile);
//        LB_pos.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        Element LB_per=appinfo.addElement("link:linkbaseRef");
//        LB_per.addAttribute("xlink:type", "simple");
//        LB_per.addAttribute("xlink:href", formulaPerFile);
//        LB_per.addAttribute("xlink:arcrole", "http://www.w3.org/1999/xlink/properties/linkbase");
//
//        //construct import of formula role
//        Element import_FormulaRole=root.addElement("xsd:import");
//        import_FormulaRole.addAttribute("namespace", formulaRoleNamespace);
//        import_FormulaRole.addAttribute("schemaLocation", formulaRoleFile);
//
//        //construct import of cas entry point
//        Element import_CasEntry=root.addElement("xsd:import");
//        import_CasEntry.addAttribute("namespace", casEntryNamespace);
//        import_CasEntry.addAttribute("schemaLocation", casEntryFile);
//
//
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaEntryFile)), format);
//        writer.write(document);
//        writer.close();
//
//        System.out.println("Formula Entry File generated.");
//
//
//	}
//
//	public static void generateRoleFile() throws IOException {
//
//		//parameters initialization
//		String roleDefinition_axis="Axis aggregation validations";
//		String roleDefinition_cro="Cross period validations";
//		String roleDefinition_eps="Earnings per share validations";
//		String roleDefinition_equ="Fact equivalence validations";
//		String roleDefinition_neg="Negative fact validations";
//		String roleDefinition_pos="Positive fact validations";
//		String roleDefinition_per="Percentage warnings";
//
//		//Construct the root element of formula role file
//		Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("xsd:schema");
//        root.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
//        root.addNamespace("link", "http://www.xbrl.org/2003/linkbase");
//        root.addNamespace("gen", "http://xbrl.org/2008/generic");
//        root.addNamespace("rol_cas_formula", formulaRoleNamespace);
//        root.addAttribute("targetNamespace", formulaRoleNamespace);
//        root.addAttribute("elementFormDefault", "qualified");
//        root.addAttribute("attributeFormDefault", "unqualified");
//
//        //construct annoctation&appinfo
//        Element annotation=root.addElement("xsd:annotation");
//        Element appinfo= annotation.addElement("xsd:appinfo");
//
//        //Construct the roleType List
//        Element role_axis=appinfo.addElement("link:roleType");
//        role_axis.addAttribute("roleURI",roleURI_axis);
//        role_axis.addAttribute("id", roleID_axis);
//        role_axis.addElement("link:definition").setText(roleDefinition_axis);
//        role_axis.addElement("link:usedOn").setText("gen:link");
//
//        Element role_cro=appinfo.addElement("link:roleType");
//        role_cro.addAttribute("roleURI",roleURI_cro);
//        role_cro.addAttribute("id", roleID_cro);
//        role_cro.addElement("link:definition").setText(roleDefinition_cro);
//        role_cro.addElement("link:usedOn").setText("gen:link");
//
//        Element role_eps=appinfo.addElement("link:roleType");
//        role_eps.addAttribute("roleURI",roleURI_eps);
//        role_eps.addAttribute("id", roleID_eps);
//        role_eps.addElement("link:definition").setText(roleDefinition_eps);
//        role_eps.addElement("link:usedOn").setText("gen:link");
//
//        Element role_equ=appinfo.addElement("link:roleType");
//        role_equ.addAttribute("roleURI",roleURI_equ);
//        role_equ.addAttribute("id", roleID_equ);
//        role_equ.addElement("link:definition").setText(roleDefinition_equ);
//        role_equ.addElement("link:usedOn").setText("gen:link");
//
//        Element role_neg=appinfo.addElement("link:roleType");
//        role_neg.addAttribute("roleURI",roleURI_neg);
//        role_neg.addAttribute("id", roleID_neg);
//        role_neg.addElement("link:definition").setText(roleDefinition_neg);
//        role_neg.addElement("link:usedOn").setText("gen:link");
//
//        Element role_pos=appinfo.addElement("link:roleType");
//        role_pos.addAttribute("roleURI",roleURI_pos);
//        role_pos.addAttribute("id", roleID_pos);
//        role_pos.addElement("link:definition").setText(roleDefinition_pos);
//        role_pos.addElement("link:usedOn").setText("gen:link");
//
//        Element role_per=appinfo.addElement("link:roleType");
//        role_per.addAttribute("roleURI",roleURI_per);
//        role_per.addAttribute("id", roleID_per);
//        role_per.addElement("link:definition").setText(roleDefinition_per);
//        role_per.addElement("link:usedOn").setText("gen:link");
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaRoleFile)), format);
//        writer.write(document);
//        writer.close();
//
//        System.out.println("Formula role file generated.");
//
//
//	}
//
//	public static void generateFormulaCro() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_cro);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_cro);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_cro);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messageCro);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct periodFilterStart & periodFilterEnd
//	    Element periodFilterStart=link.addElement("pf:instantDuration");
//	    periodFilterStart.addAttribute("id", "periodFilterStart");
//	    periodFilterStart.addAttribute("boundary", "start");
//	    periodFilterStart.addAttribute("variable", "change1");
//	    periodFilterStart.addAttribute("xlink:type", "resource");
//	    periodFilterStart.addAttribute("xlink:label", "periodFilterStart");
//
//	    Element periodFilterEnd=link.addElement("pf:instantDuration");
//	    periodFilterEnd.addAttribute("id", "periodFilterEnd");
//	    periodFilterEnd.addAttribute("boundary", "end");
//	    periodFilterEnd.addAttribute("variable", "change1");
//	    periodFilterEnd.addAttribute("xlink:type", "resource");
//	    periodFilterEnd.addAttribute("xlink:label", "periodFilterEnd");
//
//	    //construct value assertion
//	    generateAssertionCro(link);
//
//        //GENERATE FILE
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("utf-8");
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaCroFile)), format);
//        writer.write(document);
//        writer.close();
//
//        System.out.println("Formula file for CrossPeriod generated.");
//
//	}
//
//	public static void setNamespace(Element root) throws IOException{
//
//
//		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//
//        FileInputStream f = new FileInputStream("formulaConfig/namespaceConfig.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		String schemaLocation="";
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//			String nameSpace=lineSplit.get(0);
//			String nameSpaceURL=lineSplit.get(1);
//			String location=lineSplit.get(2);
//
//			root.addNamespace(nameSpace,nameSpaceURL);
//			schemaLocation=schemaLocation+nameSpaceURL+" "+location+" ";
//
//			line = dr.readLine();
//		}
//		root.addAttribute("xsi:schemaLocation", schemaLocation);
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateAssertionPer(Element link) throws IOException{
//
//		//generate value assertion
//		Element assertion=link.addElement("va:valueAssertion");
//		assertion.addAttribute("id", "percentage_assertion");
//		assertion.addAttribute("test", "abs($percentage) le 1");
//		assertion.addAttribute("xlink:label", "percentage_assertion");
//		assertion.addAttribute("aspectModel", "dimensional");
//		assertion.addAttribute("implicitFiltering", "true");
//		assertion.addAttribute("xlink:type", "resource");
//
//		//generate messsage arc
//		Element genarc=link.addElement("gen:arc");
//		genarc.addAttribute("xlink:from", "percentage_assertion");
//		genarc.addAttribute("xlink:to", "message");
//		genarc.addAttribute("order", "1.0");
//		genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//		genarc.addAttribute("xlink:type", "arc");
//
//		//generate variable filter
//		Element instantVariableFilter=link.addElement("cf:conceptDataType");
//		instantVariableFilter.addAttribute("xlink:type", "resource");
//		instantVariableFilter.addAttribute("id", "factVariableFilter");
//		instantVariableFilter.addAttribute("strict", "false");
//		instantVariableFilter.addAttribute("xlink:label", "factVariableFilter");
//		instantVariableFilter.addElement("cf:type").addElement("cf:qname").setText("num:percentItemType");
//
//		//generate variable
//		Element beginningVariable=link.addElement("variable:factVariable");
//		beginningVariable.addAttribute("id", "factVariable");
//		beginningVariable.addAttribute("xlink:label", "factVariable");
//		beginningVariable.addAttribute("bindAsSequence", "false");
//		beginningVariable.addAttribute("xlink:type", "resource");
//
//		//generate variable arc
//		Element beginningVariableArc=link.addElement("variable:variableArc");
//		beginningVariableArc.addAttribute("name", "percentage");
//		beginningVariableArc.addAttribute("order", "2.0");
//		beginningVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//		beginningVariableArc.addAttribute("xlink:from", "percentage_assertion");
//		beginningVariableArc.addAttribute("xlink:to", "factVariable");
//		beginningVariableArc.addAttribute("xlink:type","arc");
//
//		//generate filter arc
//		Element beginningCFArc=link.addElement("variable:variableFilterArc");
//		beginningCFArc.addAttribute("xlink:from","factVariable");
//		beginningCFArc.addAttribute("xlink:to", "factVariableFilter");
//		beginningCFArc.addAttribute("order", "1.0");
//		beginningCFArc.addAttribute("complement", "false");
//		beginningCFArc.addAttribute("cover", "true");
//		beginningCFArc.addAttribute("priority", "0");
//		beginningCFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//		beginningCFArc.addAttribute("xlink:type", "arc");
//
//	}
//
//	public static ArrayList<String> removeEmtpy(ArrayList<String> lineSplit){
//
//		ArrayList<String> nonemptyLineSplit=new ArrayList<String>();
//
//		for(int i=0;i<lineSplit.size();i++){
//			if(!lineSplit.get(i).isEmpty()){
//				nonemptyLineSplit.add(lineSplit.get(i));
//			}
//
//		}
//
//		return nonemptyLineSplit;
//	}
//
//	public static void generateFormulaPer() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_per);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_per);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_per);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messagePer);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct value assertion
//	    generateAssertionPer(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaPerFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for percentage generated.");
//
//	}
//
//	public static void generateAssertionAxi(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/axi.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionNo= "assertion"+nonemptyLineSplit.get(0);
//				String assertionID = nonemptyLineSplit.get(1);
//				String formulaString = "$parent eq sum($child)";
//				String roleURI=nonemptyLineSplit.get(2);
//				String axis=nonemptyLineSplit.get(3);
//
//				//generate value assertion
//				Element assertion=link.addElement("va:valueAssertion");
//				assertion.addAttribute("id", assertionID);
//				assertion.addAttribute("test", formulaString);
//				assertion.addAttribute("xlink:label", assertionNo);
//				assertion.addAttribute("aspectModel", "dimensional");
//				assertion.addAttribute("implicitFiltering", "true");
//				assertion.addAttribute("xlink:type", "resource");
//
//				//generate messsage arc
//				Element genarc=link.addElement("gen:arc");
//				genarc.addAttribute("xlink:from", assertionNo );
//				genarc.addAttribute("xlink:to", "message");
//				genarc.addAttribute("order", "1.0");
//				genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//				genarc.addAttribute("xlink:type", "arc");
//
//				//generate monetaryItemTypeFilter arc
//				Element monetaryItemTypeFilterArc=link.addElement("variable:variableSetFilterArc");
//				monetaryItemTypeFilterArc.addAttribute("complement", "false");
//				monetaryItemTypeFilterArc.addAttribute("order", "2.0");
//				monetaryItemTypeFilterArc.addAttribute("priority", "0");
//				monetaryItemTypeFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set-filter");
//				monetaryItemTypeFilterArc.addAttribute("xlink:from", assertionNo);
//				monetaryItemTypeFilterArc.addAttribute("xlink:to", "monetaryItemTypeFilter");
//				monetaryItemTypeFilterArc.addAttribute("xlink:type", "arc");
//
//				//generate variable for child & parent
//				Element childVariable=link.addElement("variable:factVariable");
//				childVariable.addAttribute("id", "childVariable_"+assertionNo);
//				childVariable.addAttribute("xlink:label", "childVariable_"+assertionNo);
//				childVariable.addAttribute("bindAsSequence", "true");
//				childVariable.addAttribute("xlink:type", "resource");
//
//				Element parentVariable=link.addElement("variable:factVariable");
//				parentVariable.addAttribute("id", "parentVariable_"+assertionNo);
//				parentVariable.addAttribute("xlink:label", "parentVariable_"+assertionNo);
//				parentVariable.addAttribute("bindAsSequence", "false");
//				parentVariable.addAttribute("xlink:type", "resource");
//
//				//generate variable arc for child & parent
//				Element childVariableArc=link.addElement("variable:variableArc");
//				childVariableArc.addAttribute("name", "child");
//				childVariableArc.addAttribute("order", "3.0");
//				childVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				childVariableArc.addAttribute("xlink:from", assertionNo);
//				childVariableArc.addAttribute("xlink:to", "childVariable_"+assertionNo);
//				childVariableArc.addAttribute("xlink:type","arc");
//
//				Element parentVariableArc=link.addElement("variable:variableArc");
//				parentVariableArc.addAttribute("name", "parent");
//				parentVariableArc.addAttribute("order", "4.0");
//				parentVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				parentVariableArc.addAttribute("xlink:from", assertionNo);
//				parentVariableArc.addAttribute("xlink:to", "parentVariable_"+assertionNo);
//				parentVariableArc.addAttribute("xlink:type","arc");
//
//				//generate variable filter & varialbe filter arc for child
//				Element childVariableFilter=link.addElement("df:explicitDimension");
//				childVariableFilter.addAttribute("xlink:type", "resource");
//				childVariableFilter.addAttribute("id", "childfilter_"+assertionNo);
//				childVariableFilter.addAttribute("xlink:label", "childfilter_"+assertionNo);
//				childVariableFilter.addElement("df:dimension").addElement("df:qname").setText(axis);
//				Element memberFilter=childVariableFilter.addElement("df:member");
//				memberFilter.addElement("df:variable").setText("parent");
//				memberFilter.addElement("df:linkrole").setText(roleURI);
//				memberFilter.addElement("df:arcrole").setText("http://xbrl.org/int/dim/arcrole/domain-member");
//				memberFilter.addElement("df:axis").setText("child");
//
//				Element childFilterArc=link.addElement("variable:variableFilterArc");
//				childFilterArc.addAttribute("xlink:from", "childVariable_"+assertionNo);
//				childFilterArc.addAttribute("xlink:to", "childfilter_"+assertionNo);
//				childFilterArc.addAttribute("order", "1.0");
//				childFilterArc.addAttribute("complement", "false");
//				childFilterArc.addAttribute("cover", "true");
//				childFilterArc.addAttribute("priority", "0");
//				childFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				childFilterArc.addAttribute("xlink:type", "arc");
//
//				//generate variable filter & varialbe filter arc for parent
//				Element parentVariableFilter=link.addElement("df:explicitDimension");
//				parentVariableFilter.addAttribute("xlink:type", "resource");
//				parentVariableFilter.addAttribute("id", "parentfilter_"+assertionNo);
//				parentVariableFilter.addAttribute("xlink:label", "parentfilter_"+assertionNo);
//				parentVariableFilter.addElement("df:dimension").addElement("df:qname").setText(axis);
//
//				Element parentFilterArc=link.addElement("variable:variableFilterArc");
//				parentFilterArc.addAttribute("xlink:from", "parentVariable_"+assertionNo);
//				parentFilterArc.addAttribute("xlink:to", "parentfilter_"+assertionNo);
//				parentFilterArc.addAttribute("order", "1.0");
//				parentFilterArc.addAttribute("complement", "false");
//				parentFilterArc.addAttribute("cover", "true");
//				parentFilterArc.addAttribute("priority", "0");
//				parentFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				parentFilterArc.addAttribute("xlink:type", "arc");
//
//			}
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateFormulaEps() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_eps);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_eps);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    Element arcroleref_pre=root.addElement("link:arcroleRef");
//	    arcroleref_pre.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set-precondition");
//	    arcroleref_pre.addAttribute("xlink:type", "simple");
//	    arcroleref_pre.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set-precondition");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_eps);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messageEps);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct numerator filter
//	    Element numeratorFilter=link.addElement("uf:singleMeasure");
//	    numeratorFilter.addAttribute("xlink:type", "resource");
//	    numeratorFilter.addAttribute("xlink:label", "numeratorFilter");
//	    numeratorFilter.addAttribute("id", "numeratorFilter");
//	    numeratorFilter.addElement("uf:measure").addElement("uf:qnameExpression").setText("xfi:measure-name(xfi:unit-numerator(xfi:unit($eps)))");
//
//	    //construct denominator filter
//	    Element denominatorFilter=link.addElement("uf:singleMeasure");
//	    denominatorFilter.addAttribute("xlink:type", "resource");
//	    denominatorFilter.addAttribute("xlink:label", "denominatorFilter");
//	    denominatorFilter.addAttribute("id", "denominatorFilter");
//	    denominatorFilter.addElement("uf:measure").addElement("uf:qnameExpression").setText("xfi:measure-name(xfi:unit-denominator(xfi:unit($eps)))");
//
//	    //construct precondition
//	    Element precondition=link.addElement("variable:precondition");
//	    precondition.addAttribute("xlink:type", "resource");
//	    precondition.addAttribute("xlink:label", "precondition");
//	    precondition.addAttribute("xlink:title", "precondition");
//	    precondition.addAttribute("id", "precondition");
//	    precondition.addAttribute("test", "not($averageShares eq 0)");
//
//	    //construct parameter
//	    Element parameter=link.addElement("variable:parameter");
//	    parameter.addAttribute("xlink:type", "resource");
//	    parameter.addAttribute("id", "eps_threshhold");
//	    parameter.addAttribute("name", "eps_threshhold");
//	    parameter.addAttribute("xlink:label", "parameter");
//	    parameter.addAttribute("required", "true");
//
//	    //construct value assertion
//	    generateAssertionEps(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaEpsFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for eps generated.");
//
//	}
//
//	public static void generateAssertionEps(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/eps.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionNo= "assertion"+nonemptyLineSplit.get(0);
//				String assertionID = nonemptyLineSplit.get(1);
//				String assertionFormula = "abs(($profitLoss div $averageShares) - $eps) le $threshold";
//				String eps=nonemptyLineSplit.get(2).replace("_", ":");
//				String numerator=nonemptyLineSplit.get(3).replace("_", ":");
//				String denominator=nonemptyLineSplit.get(4).replace("_", ":");
//
//				//generate value assertion
//				Element assertion=link.addElement("va:valueAssertion");
//				assertion.addAttribute("id", assertionID);
//				assertion.addAttribute("test", assertionFormula);
//				assertion.addAttribute("xlink:label", assertionNo);
//				assertion.addAttribute("aspectModel", "dimensional");
//				assertion.addAttribute("implicitFiltering", "true");
//				assertion.addAttribute("xlink:type", "resource");
//
//				//generate messsage arc
//				Element genarc=link.addElement("gen:arc");
//				genarc.addAttribute("xlink:from", assertionNo );
//				genarc.addAttribute("xlink:to", "message");
//				genarc.addAttribute("order", "1.0");
//				genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//				genarc.addAttribute("xlink:type", "arc");
//
//				//generate precondition arc
//				Element preconditionarc=link.addElement("gen:arc");
//				preconditionarc.addAttribute("xlink:type", "arc");
//				preconditionarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set-precondition");
//				preconditionarc.addAttribute("xlink:from", assertionNo);
//				preconditionarc.addAttribute("xlink:to", "precondition");
//				preconditionarc.addAttribute("xlink:title", "from "+assertionNo+" to precondition");
//				preconditionarc.addAttribute("priority", "0");
//				preconditionarc.addAttribute("order", "2.0");
//
//				//generate threshhold arc
//				Element thresholdarc=link.addElement("variable:variableArc");
//				thresholdarc.addAttribute("name", "threshold");
//				thresholdarc.addAttribute("order", "3.0");
//				thresholdarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				thresholdarc.addAttribute("xlink:from", assertionNo);
//				thresholdarc.addAttribute("xlink:to", "parameter");
//				thresholdarc.addAttribute("xlink:type","arc");
//
//				//generate for eps variable
//				Element epsVariableFilter=link.addElement("cf:conceptName");
//				epsVariableFilter.addAttribute("xlink:type", "resource");
//				epsVariableFilter.addAttribute("id", "epsVariableFilter_"+assertionNo);
//				epsVariableFilter.addAttribute("xlink:label", "epsVariableFilter_"+assertionNo);
//				epsVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(eps);
//
//				Element epsVariable=link.addElement("variable:factVariable");
//				epsVariable.addAttribute("id", "epsVariable_"+assertionNo);
//				epsVariable.addAttribute("xlink:label", "epsVariable_"+assertionNo);
//				epsVariable.addAttribute("bindAsSequence", "false");
//				epsVariable.addAttribute("xlink:type", "resource");
//
//				Element epsVariableArc=link.addElement("variable:variableArc");
//				epsVariableArc.addAttribute("name", "eps");
//				epsVariableArc.addAttribute("order", "4.0");
//				epsVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				epsVariableArc.addAttribute("xlink:from", assertionNo);
//				epsVariableArc.addAttribute("xlink:to", "epsVariable_"+assertionNo);
//				epsVariableArc.addAttribute("xlink:type","arc");
//
//				Element epsFilterArc=link.addElement("variable:variableFilterArc");
//				epsFilterArc.addAttribute("xlink:from", "epsVariable_"+assertionNo);
//				epsFilterArc.addAttribute("xlink:to", "epsVariableFilter_"+assertionNo);
//				epsFilterArc.addAttribute("order", "1.0");
//				epsFilterArc.addAttribute("complement", "false");
//				epsFilterArc.addAttribute("cover", "true");
//				epsFilterArc.addAttribute("priority", "0");
//				epsFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				epsFilterArc.addAttribute("xlink:type", "arc");
//
//				//generate for numerator variable
//				Element numeratorVariableFilter=link.addElement("cf:conceptName");
//				numeratorVariableFilter.addAttribute("xlink:type", "resource");
//				numeratorVariableFilter.addAttribute("id", "numeratorVariableFilter_"+assertionNo);
//				numeratorVariableFilter.addAttribute("xlink:label", "numeratorVariableFilter_"+assertionNo);
//				numeratorVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(numerator);
//
//				Element numeratorVariable=link.addElement("variable:factVariable");
//				numeratorVariable.addAttribute("id", "numeratorVariable_"+assertionNo);
//				numeratorVariable.addAttribute("xlink:label", "numeratorVariable_"+assertionNo);
//				numeratorVariable.addAttribute("bindAsSequence", "false");
//				numeratorVariable.addAttribute("xlink:type", "resource");
//
//				Element numeratorVariableArc=link.addElement("variable:variableArc");
//				numeratorVariableArc.addAttribute("name", "profitLoss");
//				numeratorVariableArc.addAttribute("order", "5.0");
//				numeratorVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				numeratorVariableArc.addAttribute("xlink:from", assertionNo);
//				numeratorVariableArc.addAttribute("xlink:to", "numeratorVariable_"+assertionNo);
//				numeratorVariableArc.addAttribute("xlink:type","arc");
//
//				Element numeratorFilterArc=link.addElement("variable:variableFilterArc");
//				numeratorFilterArc.addAttribute("xlink:from", "numeratorVariable_"+assertionNo);
//				numeratorFilterArc.addAttribute("xlink:to", "numeratorVariableFilter_"+assertionNo);
//				numeratorFilterArc.addAttribute("order", "1.0");
//				numeratorFilterArc.addAttribute("complement", "false");
//				numeratorFilterArc.addAttribute("cover", "true");
//				numeratorFilterArc.addAttribute("priority", "0");
//				numeratorFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				numeratorFilterArc.addAttribute("xlink:type", "arc");
//
//				Element numeratorUFArc=link.addElement("variable:variableFilterArc");
//				numeratorUFArc.addAttribute("xlink:from", "numeratorVariable_"+assertionNo);
//				numeratorUFArc.addAttribute("xlink:to", "numeratorFilter");
//				numeratorUFArc.addAttribute("order", "2.0");
//				numeratorUFArc.addAttribute("complement", "false");
//				numeratorUFArc.addAttribute("cover", "true");
//				numeratorUFArc.addAttribute("priority", "0");
//				numeratorUFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				numeratorUFArc.addAttribute("xlink:type", "arc");
//
//				//generate for denominator variable
//				Element denominatorVariableFilter=link.addElement("cf:conceptName");
//				denominatorVariableFilter.addAttribute("xlink:type", "resource");
//				denominatorVariableFilter.addAttribute("id", "denominatorVariableFilter_"+assertionNo);
//				denominatorVariableFilter.addAttribute("xlink:label", "denominatorVariableFilter_"+assertionNo);
//				denominatorVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(denominator);
//
//				Element denominatorVariable=link.addElement("variable:factVariable");
//				denominatorVariable.addAttribute("id", "denominatorVariable_"+assertionNo);
//				denominatorVariable.addAttribute("xlink:label", "denominatorVariable_"+assertionNo);
//				denominatorVariable.addAttribute("bindAsSequence", "false");
//				denominatorVariable.addAttribute("xlink:type", "resource");
//
//				Element denominatorVariableArc=link.addElement("variable:variableArc");
//				denominatorVariableArc.addAttribute("name", "averageShares");
//				denominatorVariableArc.addAttribute("order", "6.0");
//				denominatorVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				denominatorVariableArc.addAttribute("xlink:from", assertionNo);
//				denominatorVariableArc.addAttribute("xlink:to", "denominatorVariable_"+assertionNo);
//				denominatorVariableArc.addAttribute("xlink:type","arc");
//
//				Element denominatorFilterArc=link.addElement("variable:variableFilterArc");
//				denominatorFilterArc.addAttribute("xlink:from", "denominatorVariable_"+assertionNo);
//				denominatorFilterArc.addAttribute("xlink:to", "denominatorVariableFilter_"+assertionNo);
//				denominatorFilterArc.addAttribute("order", "1.0");
//				denominatorFilterArc.addAttribute("complement", "false");
//				denominatorFilterArc.addAttribute("cover", "true");
//				denominatorFilterArc.addAttribute("priority", "0");
//				denominatorFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				denominatorFilterArc.addAttribute("xlink:type", "arc");
//
//				Element denominatorUFArc=link.addElement("variable:variableFilterArc");
//				denominatorUFArc.addAttribute("xlink:from", "denominatorVariable_"+assertionNo);
//				denominatorUFArc.addAttribute("xlink:to", "denominatorFilter");
//				denominatorUFArc.addAttribute("order", "2.0");
//				denominatorUFArc.addAttribute("complement", "false");
//				denominatorUFArc.addAttribute("cover", "true");
//				denominatorUFArc.addAttribute("priority", "0");
//				denominatorUFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				denominatorUFArc.addAttribute("xlink:type", "arc");
//
//			}
//
//
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateFormulaEqu() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_equ);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_equ);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_equ);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messageEqu);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct value assertion
//	    generateAssertionEqu(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaEquFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for Equation generated.");
//
//	}
//
//	public static void generateAssertionCro(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/cro.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionNo= "assertion"+nonemptyLineSplit.get(0);
//				String assertionID = nonemptyLineSplit.get(1);
//				String assertionFormula = nonemptyLineSplit.get(2);
//
//				//convert formula
//				String formulaString="";
//				String[] formulaSplit=assertionFormula.split(" ");
//				for(int i=0;i<formulaSplit.length;i++){
//					if(!formulaSplit[i].equals("+") && !formulaSplit[i].equals("-") &&!formulaSplit[i].equals("=")){
//						formulaString=formulaString+"$"+formulaSplit[i]+" ";
//					}else if(formulaSplit[i].equals("=")){
//						formulaString=formulaString+"eq ";
//					}else{
//						formulaString=formulaString+formulaSplit[i]+" ";
//					}
//				}
//
//				//generate value assertion
//				Element assertion=link.addElement("va:valueAssertion");
//				assertion.addAttribute("id", assertionID);
//				assertion.addAttribute("test", formulaString);
//				assertion.addAttribute("xlink:label", assertionNo);
//				assertion.addAttribute("aspectModel", "dimensional");
//				assertion.addAttribute("implicitFiltering", "true");
//				assertion.addAttribute("xlink:type", "resource");
//
//				//generate messsage arc
//				Element genarc=link.addElement("gen:arc");
//				genarc.addAttribute("xlink:from", assertionNo );
//				genarc.addAttribute("xlink:to", "message");
//				genarc.addAttribute("order", "1.0");
//				genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//				genarc.addAttribute("xlink:type", "arc");
//
//				//generate variable filter for beginning and ending balance
//				String instantVariable = nonemptyLineSplit.get(3).replace("_", ":");
//				Element instantVariableFilter=link.addElement("cf:conceptName");
//				instantVariableFilter.addAttribute("xlink:type", "resource");
//				instantVariableFilter.addAttribute("id", "instantVariableFilter_"+assertionNo);
//				instantVariableFilter.addAttribute("xlink:label", "instantVariableFilter_"+assertionNo);
//				instantVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(instantVariable);
//
//				//generate variable for beginning balance & ending varialbe
//				Element beginningVariable=link.addElement("variable:factVariable");
//				beginningVariable.addAttribute("id", "beginningVariable_"+assertionNo);
//				beginningVariable.addAttribute("xlink:label", "beginningVariable_"+assertionNo);
//				beginningVariable.addAttribute("bindAsSequence", "false");
//				beginningVariable.addAttribute("xlink:type", "resource");
//
//				Element endingVariable=link.addElement("variable:factVariable");
//				endingVariable.addAttribute("id", "endingVariable_"+assertionNo);
//				endingVariable.addAttribute("xlink:label", "endingVariable_"+assertionNo);
//				endingVariable.addAttribute("bindAsSequence", "false");
//				endingVariable.addAttribute("xlink:type", "resource");
//
//				//generate variable arc for beginning variable & ending variable
//				Element beginningVariableArc=link.addElement("variable:variableArc");
//				beginningVariableArc.addAttribute("name", "beginningBalance");
//				beginningVariableArc.addAttribute("order", "2.0");
//				beginningVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				beginningVariableArc.addAttribute("xlink:from", assertionNo);
//				beginningVariableArc.addAttribute("xlink:to", "beginningVariable_"+assertionNo);
//				beginningVariableArc.addAttribute("xlink:type","arc");
//
//				Element endingVariableArc=link.addElement("variable:variableArc");
//				endingVariableArc.addAttribute("name", "endingBalance");
//				endingVariableArc.addAttribute("order", "3.0");
//				endingVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				endingVariableArc.addAttribute("xlink:from", assertionNo);
//				endingVariableArc.addAttribute("xlink:to", "endingVariable_"+assertionNo);
//				endingVariableArc.addAttribute("xlink:type","arc");
//
//				//generate cf filter arc for beginning & ending variable
//				Element beginningCFArc=link.addElement("variable:variableFilterArc");
//				beginningCFArc.addAttribute("xlink:from", "beginningVariable_"+assertionNo);
//				beginningCFArc.addAttribute("xlink:to", "instantVariableFilter_"+assertionNo);
//				beginningCFArc.addAttribute("order", "1.0");
//				beginningCFArc.addAttribute("complement", "false");
//				beginningCFArc.addAttribute("cover", "true");
//				beginningCFArc.addAttribute("priority", "0");
//				beginningCFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				beginningCFArc.addAttribute("xlink:type", "arc");
//
//				Element endingCFArc=link.addElement("variable:variableFilterArc");
//				endingCFArc.addAttribute("xlink:from", "endingVariable_"+assertionNo);
//				endingCFArc.addAttribute("xlink:to", "instantVariableFilter_"+assertionNo);
//				endingCFArc.addAttribute("order", "1.0");
//				endingCFArc.addAttribute("complement", "false");
//				endingCFArc.addAttribute("cover", "true");
//				endingCFArc.addAttribute("priority", "0");
//				endingCFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				endingCFArc.addAttribute("xlink:type", "arc");
//
//				//generate pf filter arc for beginning & ending variable
//				Element beginningPFArc=link.addElement("variable:variableFilterArc");
//				beginningPFArc.addAttribute("xlink:from", "beginningVariable_"+assertionNo);
//				beginningPFArc.addAttribute("xlink:to", "periodFilterStart");
//				beginningPFArc.addAttribute("order", "2.0");
//				beginningPFArc.addAttribute("complement", "false");
//				beginningPFArc.addAttribute("cover", "true");
//				beginningPFArc.addAttribute("priority", "0");
//				beginningPFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				beginningPFArc.addAttribute("xlink:type", "arc");
//
//				Element endingPFArc=link.addElement("variable:variableFilterArc");
//				endingPFArc.addAttribute("xlink:from", "endingVariable_"+assertionNo);
//				endingPFArc.addAttribute("xlink:to", "periodFilterEnd");
//				endingPFArc.addAttribute("order", "2.0");
//				endingPFArc.addAttribute("complement", "false");
//				endingPFArc.addAttribute("cover", "true");
//				endingPFArc.addAttribute("priority", "0");
//				endingPFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				endingPFArc.addAttribute("xlink:type", "arc");
//
//				//change variable
//				for(int k=4;k<nonemptyLineSplit.size();k++){
//
//					String changeVariableElement=nonemptyLineSplit.get(k).replace("_", ":");
//					String changeVariableName="change"+(k-3);
//
//					//generate variable
//					Element changeVariable=link.addElement("variable:factVariable");
//					changeVariable.addAttribute("id", changeVariableName+"_"+assertionNo);
//					changeVariable.addAttribute("xlink:label", changeVariableName+"_"+assertionNo);
//					changeVariable.addAttribute("bindAsSequence", "false");
//					changeVariable.addAttribute("xlink:type", "resource");
//
//					//generate variable arc
//					int changeVariableOrder=k-1;
//					String changeVariableOrderString=Integer.toString(changeVariableOrder)+".0";
//					Element changeVariableArc=link.addElement("variable:variableArc");
//					changeVariableArc.addAttribute("name", changeVariableName);
//					changeVariableArc.addAttribute("order", changeVariableOrderString);
//					changeVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//					changeVariableArc.addAttribute("xlink:from", assertionNo);
//					changeVariableArc.addAttribute("xlink:to", changeVariableName+"_"+assertionNo);
//					changeVariableArc.addAttribute("xlink:type", "arc");
//
//					//generate variable filter
//					Element changeVariableFilter=link.addElement("cf:conceptName");
//					changeVariableFilter.addAttribute("xlink:type", "resource");
//					changeVariableFilter.addAttribute("id", "filter_"+changeVariableName+"_"+assertionNo);
//					changeVariableFilter.addAttribute("xlink:label", "filter_"+changeVariableName+"_"+assertionNo);
//					changeVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(changeVariableElement);
//
//					//generate variable fitler arc
//					Element changeCFArc=link.addElement("variable:variableFilterArc");
//					changeCFArc.addAttribute("xlink:from", changeVariableName+"_"+assertionNo);
//					changeCFArc.addAttribute("xlink:to", "filter_"+changeVariableName+"_"+assertionNo);
//					changeCFArc.addAttribute("order", "1.0");
//					changeCFArc.addAttribute("complement", "false");
//					changeCFArc.addAttribute("cover", "true");
//					changeCFArc.addAttribute("priority", "0");
//					changeCFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//					changeCFArc.addAttribute("xlink:type", "arc");
//
//				}
//
//			}
//
//
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateFormulaAxi() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_axis);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_axis);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    Element arcroleref_setfilter=root.addElement("link:arcroleRef");
//	    arcroleref_setfilter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set-filter");
//	    arcroleref_setfilter.addAttribute("xlink:type", "simple");
//	    arcroleref_setfilter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set-filter");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_axis);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messageCro);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct monetaryItemTypeFilter
//	    Element monetaryItemTypeFilter=link.addElement("cf:conceptDataType");
//	    monetaryItemTypeFilter.addAttribute("id", "monetaryItemTypeFilter");
//	    monetaryItemTypeFilter.addAttribute("strict", "false");
//	    monetaryItemTypeFilter.addAttribute("xlink:label", "monetaryItemTypeFilter");
//	    monetaryItemTypeFilter.addAttribute("xlink:type", "resource");
//	    monetaryItemTypeFilter.addElement("cf:type").addElement("cf:qname").setText("xbrli:monetaryItemType");
//
//	    //construct value assertion
//	    generateAssertionAxi(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaAxiFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for Axis generated.");
//
//	}
//
//	public static void generateAssertionEqu(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/equ.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionNo= "assertion"+nonemptyLineSplit.get(0);
//				String assertionID = nonemptyLineSplit.get(1);
//				String assertionFormula=nonemptyLineSplit.get(2);
//				String formulaString = "";
//				if(assertionFormula.contains("-")){
//					formulaString="$nond eq $dim";
//				}else{
//					formulaString="-$nond eq $dim";
//				}
//				String nondim=nonemptyLineSplit.get(3);
//				String primaryitem=nonemptyLineSplit.get(4);
//				String axis=nonemptyLineSplit.get(5);
//				String member=nonemptyLineSplit.get(6);
//
//				//generate value assertion
//				Element assertion=link.addElement("va:valueAssertion");
//				assertion.addAttribute("id", assertionID);
//				assertion.addAttribute("test", formulaString);
//				assertion.addAttribute("xlink:label", assertionNo);
//				assertion.addAttribute("aspectModel", "dimensional");
//				assertion.addAttribute("implicitFiltering", "true");
//				assertion.addAttribute("xlink:type", "resource");
//
//				//generate messsage arc
//				Element genarc=link.addElement("gen:arc");
//				genarc.addAttribute("xlink:from", assertionNo );
//				genarc.addAttribute("xlink:to", "message");
//				genarc.addAttribute("order", "1.0");
//				genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//				genarc.addAttribute("xlink:type", "arc");
//
//				//generate variable for dim & non-dim
//				Element dimVariable=link.addElement("variable:factVariable");
//				dimVariable.addAttribute("id", "dimVariable_"+assertionNo);
//				dimVariable.addAttribute("xlink:label", "dimVariable_"+assertionNo);
//				dimVariable.addAttribute("bindAsSequence", "true");
//				dimVariable.addAttribute("xlink:type", "resource");
//
//				Element nondVariable=link.addElement("variable:factVariable");
//				nondVariable.addAttribute("id", "nondVariable_"+assertionNo);
//				nondVariable.addAttribute("xlink:label", "nondVariable_"+assertionNo);
//				nondVariable.addAttribute("bindAsSequence", "false");
//				nondVariable.addAttribute("xlink:type", "resource");
//
//				//generate variable arc for dim & non-dim
//				Element dimVariableArc=link.addElement("variable:variableArc");
//				dimVariableArc.addAttribute("name", "dim");
//				dimVariableArc.addAttribute("order", "2.0");
//				dimVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				dimVariableArc.addAttribute("xlink:from", assertionNo);
//				dimVariableArc.addAttribute("xlink:to", "dimVariable_"+assertionNo);
//				dimVariableArc.addAttribute("xlink:type","arc");
//
//				Element nondVariableArc=link.addElement("variable:variableArc");
//				nondVariableArc.addAttribute("name", "nond");
//				nondVariableArc.addAttribute("order", "3.0");
//				nondVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//				nondVariableArc.addAttribute("xlink:from", assertionNo);
//				nondVariableArc.addAttribute("xlink:to", "nondVariable_"+assertionNo);
//				nondVariableArc.addAttribute("xlink:type","arc");
//
//				//generate variable filter & varialbe filter arc for dim
//				Element dimVariableCF=link.addElement("cf:conceptName");
//				dimVariableCF.addAttribute("xlink:type", "resource");
//				dimVariableCF.addAttribute("id", "dimCF_"+assertionNo);
//				dimVariableCF.addAttribute("xlink:label", "dimCF_"+assertionNo);
//				dimVariableCF.addElement("cf:concept").addElement("cf:qname").setText(nondim);
//
//				Element dimCFArc=link.addElement("variable:variableFilterArc");
//				dimCFArc.addAttribute("xlink:from", "dimVariable_"+assertionNo);
//				dimCFArc.addAttribute("xlink:to", "dimCF_"+assertionNo);
//				dimCFArc.addAttribute("order", "1.0");
//				dimCFArc.addAttribute("complement", "false");
//				dimCFArc.addAttribute("cover", "true");
//				dimCFArc.addAttribute("priority", "0");
//				dimCFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				dimCFArc.addAttribute("xlink:type", "arc");
//
//				Element dimVariableDF=link.addElement("df:explicitDimension");
//				dimVariableDF.addAttribute("xlink:type", "resource");
//				dimVariableDF.addAttribute("id", "dimDF_"+assertionNo);
//				dimVariableDF.addAttribute("xlink:label", "dimDF_"+assertionNo);
//				dimVariableDF.addElement("df:dimension").addElement("df:qname").setText(axis);
//				dimVariableDF.addElement("df:member").addElement("df:qname").setText(member);;
//
//				Element dimDFArc=link.addElement("variable:variableFilterArc");
//				dimDFArc.addAttribute("xlink:from", "dimVariable_"+assertionNo);
//				dimDFArc.addAttribute("xlink:to", "dimDF_"+assertionNo);
//				dimDFArc.addAttribute("order", "2.0");
//				dimDFArc.addAttribute("complement", "false");
//				dimDFArc.addAttribute("cover", "true");
//				dimDFArc.addAttribute("priority", "0");
//				dimDFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				dimDFArc.addAttribute("xlink:type", "arc");
//
//				//generate variable filter & varialbe filter arc for nond
//				Element nondVariableFilter=link.addElement("cf:conceptName");
//				nondVariableFilter.addAttribute("xlink:type", "resource");
//				nondVariableFilter.addAttribute("id", "nondfilter_"+assertionNo);
//				nondVariableFilter.addAttribute("xlink:label", "nondfilter_"+assertionNo);
//				nondVariableFilter.addElement("cf:concept").addElement("cf:qname").setText(nondim);
//
//				Element nondFilterArc=link.addElement("variable:variableFilterArc");
//				nondFilterArc.addAttribute("xlink:from", "nondVariable_"+assertionNo);
//				nondFilterArc.addAttribute("xlink:to", "nondfilter_"+assertionNo);
//				nondFilterArc.addAttribute("order", "1.0");
//				nondFilterArc.addAttribute("complement", "false");
//				nondFilterArc.addAttribute("cover", "true");
//				nondFilterArc.addAttribute("priority", "0");
//				nondFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//				nondFilterArc.addAttribute("xlink:type", "arc");
//
//			}
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateFormulaNeg() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_neg);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_neg);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    Element arcroleref_booleanfilter=root.addElement("link:arcroleRef");
//	    arcroleref_booleanfilter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/boolean-filter");
//	    arcroleref_booleanfilter.addAttribute("xlink:type", "simple");
//	    arcroleref_booleanfilter.addAttribute("xlink:href", "http://www.xbrl.org/2008/boolean-filter.xsd#boolean-filter");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_neg);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messageNeg);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct value assertion
//	    generateAssertionNeg(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaNegFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for Negative generated.");
//
//	}
//
//	public static void generateAssertionNeg(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/neg.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		DecimalFormat decimalFormat=new DecimalFormat("0000");
//		int itemCount=0;
//		String assertionIDPrevious="";
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionID = nonemptyLineSplit.get(1);
//				String formulaString = "$neg le 0";
//				String elementname=nonemptyLineSplit.get(2);
//				String axis=nonemptyLineSplit.get(3);
//				String member=nonemptyLineSplit.get(4);
//				String complement=nonemptyLineSplit.get(5);
//				itemCount++;
//
//				//generate value assertion if new assertionID is detected
//				if(!assertionID.equals(assertionIDPrevious)){
//
//					assertionIDPrevious=assertionID;
//					itemCount=1;
//
//					//generate value assertion
//					Element assertion=link.addElement("va:valueAssertion");
//					assertion.addAttribute("id", assertionID);
//					assertion.addAttribute("test", formulaString);
//					assertion.addAttribute("xlink:label", assertionID);
//					assertion.addAttribute("aspectModel", "dimensional");
//					assertion.addAttribute("implicitFiltering", "true");
//					assertion.addAttribute("xlink:type", "resource");
//
//					//generate messsage arc
//					Element genarc=link.addElement("gen:arc");
//					genarc.addAttribute("xlink:from", assertionID );
//					genarc.addAttribute("xlink:to", "message");
//					genarc.addAttribute("order", "1.0");
//					genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//					genarc.addAttribute("xlink:type", "arc");
//
//					//generate variable & variable arc
//					Element negVariable=link.addElement("variable:factVariable");
//					negVariable.addAttribute("id", "negVariable_"+assertionID);
//					negVariable.addAttribute("xlink:label", "negVariable_"+assertionID);
//					negVariable.addAttribute("bindAsSequence", "true");
//					negVariable.addAttribute("xlink:type", "resource");
//
//					Element negVariableArc=link.addElement("variable:variableArc");
//					negVariableArc.addAttribute("name", "neg");
//					negVariableArc.addAttribute("order", "2.0");
//					negVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//					negVariableArc.addAttribute("xlink:from", assertionID);
//					negVariableArc.addAttribute("xlink:to", "negVariable_"+assertionID);
//					negVariableArc.addAttribute("xlink:type","arc");
//
//					//generate dimension filter & arc
//					Element negDF=link.addElement("df:explicitDimension");
//					negDF.addAttribute("xlink:type", "resource");
//					negDF.addAttribute("id", "negDF_"+assertionID);
//					negDF.addAttribute("xlink:label", "negDF_"+assertionID);
//					negDF.addElement("df:dimension").addElement("df:qname").setText(axis);
//					negDF.addElement("df:member").addElement("df:qname").setText(member);
//
//					Element negDFArc=link.addElement("variable:variableFilterArc");
//					negDFArc.addAttribute("xlink:from", "negVariable_"+assertionID);
//					negDFArc.addAttribute("xlink:to", "negDF_"+assertionID);
//					negDFArc.addAttribute("order", "1.0");
//					negDFArc.addAttribute("complement", complement.toLowerCase());
//					negDFArc.addAttribute("cover", "true");
//					negDFArc.addAttribute("priority", "0");
//					negDFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//					negDFArc.addAttribute("xlink:type", "arc");
//
//					//generate boolean filter & arc
//					Element blFilter=link.addElement("bf:orFilter");
//					blFilter.addAttribute("xlink:label", "blFilter_"+assertionID);
//					blFilter.addAttribute("xlink:type", "resource");
//
//					Element blFilterArc=link.addElement("variable:variableFilterArc");
//					blFilterArc.addAttribute("xlink:from", "negVariable_"+assertionID);
//					blFilterArc.addAttribute("xlink:to", "blFilter_"+assertionID);
//					blFilterArc.addAttribute("complement", "false");
//					blFilterArc.addAttribute("cover", "true");
//					blFilterArc.addAttribute("order", "2.0");
//					blFilterArc.addAttribute("priority", "0");
//					blFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//					blFilterArc.addAttribute("xlink:type", "arc");
//
//					//generate primary item filter and filter arc
//					Element itemFilter=link.addElement("cf:conceptName");
//					itemFilter.addAttribute("xlink:type", "resource");
//					itemFilter.addAttribute("id", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addAttribute("xlink:label", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addElement("cf:concept").addElement("cf:qname").setText(elementname);
//
//					Element itemFilterArc=link.addElement("variable:variableFilterArc");
//					itemFilterArc.addAttribute("xlink:from", "blFilter_"+assertionID);
//					itemFilterArc.addAttribute("xlink:to", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilterArc.addAttribute("order", itemCount+".0");
//					itemFilterArc.addAttribute("complement", "false");
//					itemFilterArc.addAttribute("cover", "true");
//					itemFilterArc.addAttribute("priority", "0");
//					itemFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/boolean-filter");
//					itemFilterArc.addAttribute("xlink:type", "arc");
//
//
//
//				}else{
//
//					//generate primary item filter and filter arc
//					Element itemFilter=link.addElement("cf:conceptName");
//					itemFilter.addAttribute("xlink:type", "resource");
//					itemFilter.addAttribute("id", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addAttribute("xlink:label", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addElement("cf:concept").addElement("cf:qname").setText(elementname);
//
//					Element itemFilterArc=link.addElement("variable:variableFilterArc");
//					itemFilterArc.addAttribute("xlink:from", "blFilter_"+assertionID);
//					itemFilterArc.addAttribute("xlink:to", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilterArc.addAttribute("order", itemCount+".0");
//					itemFilterArc.addAttribute("complement", "false");
//					itemFilterArc.addAttribute("cover", "true");
//					itemFilterArc.addAttribute("priority", "0");
//					itemFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/boolean-filter");
//					itemFilterArc.addAttribute("xlink:type", "arc");
//
//				}
//
//
//
//
//			}
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//
//	public static void generateFormulaPos() throws IOException{
//
//		//Construct label
//		Document document = DocumentHelper.createDocument();
//	    Element root = document.addElement("link:linkbase");
//	    setNamespace(root);
//
//	    //construct roleref
//	    Element rolerefMessage=root.addElement("link:roleRef");
//	    rolerefMessage.addAttribute("roleURI", "http://www.xbrl.org/2010/role/message");
//	    rolerefMessage.addAttribute("xlink:type", "simple");
//	    rolerefMessage.addAttribute("xlink:href", "http://www.xbrl.org/2010/generic-message.xsd#standard-message");
//
//	    Element rolerefCro=root.addElement("link:roleRef");
//	    rolerefCro.addAttribute("roleURI", roleURI_pos);
//	    rolerefCro.addAttribute("xlink:type", "simple");
//	    rolerefCro.addAttribute("xlink:href", formulaRoleFileSimple+"#"+roleID_pos);
//
//	    //construct arcroleref
//	    Element arcroleref_filter=root.addElement("link:arcroleRef");
//	    arcroleref_filter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-filter");
//	    arcroleref_filter.addAttribute("xlink:type", "simple");
//	    arcroleref_filter.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-filter");
//
//	    Element arcroleref_set=root.addElement("link:arcroleRef");
//	    arcroleref_set.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/variable-set");
//	    arcroleref_set.addAttribute("xlink:type", "simple");
//	    arcroleref_set.addAttribute("xlink:href", "http://www.xbrl.org/2008/variable.xsd#variable-set");
//
//	    Element arcroleref_msg=root.addElement("link:arcroleRef");
//	    arcroleref_msg.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//	    arcroleref_msg.addAttribute("xlink:type", "simple");
//	    arcroleref_msg.addAttribute("xlink:href", "http://www.xbrl.org/2010/validation-message.xsd#assertion-unsatisfied-message");
//
//	    Element arcroleref_booleanfilter=root.addElement("link:arcroleRef");
//	    arcroleref_booleanfilter.addAttribute("arcroleURI", "http://xbrl.org/arcrole/2008/boolean-filter");
//	    arcroleref_booleanfilter.addAttribute("xlink:type", "simple");
//	    arcroleref_booleanfilter.addAttribute("xlink:href", "http://www.xbrl.org/2008/boolean-filter.xsd#boolean-filter");
//
//	    //construct linkbase
//	    Element link=root.addElement("gen:link");
//	    link.addAttribute("xlink:role", roleURI_pos);
//	    link.addAttribute("xlink:type", "extended");
//
//	    //construct message
//	    Element message=link.addElement("msg:message");
//	    message.setText(messagePos);
//	    message.addAttribute("id", "message");
//	    message.addAttribute("xlink:label", "message");
//	    message.addAttribute("xlink:role","http://www.xbrl.org/2010/role/message");
//	    message.addAttribute("xlink:type","resource");
//	    message.addAttribute("xml:lang","en");
//
//	    //construct value assertion
//	    generateAssertionPos(link);
//
//	    //GENERATE FILE
//	    OutputFormat format = OutputFormat.createPrettyPrint();
//	    format.setEncoding("utf-8");
//	    XMLWriter writer = new XMLWriter(new FileWriter(new File(formulaPosFile)), format);
//	    writer.write(document);
//	    writer.close();
//
//	    System.out.println("Formula file for Positive generated.");
//
//	}
//
//	public static void generateAssertionPos(Element link) throws IOException{
//
//	    FileInputStream f = new FileInputStream("formulaCsv/pos.csv");
//		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
//		dr.readLine();//skip header
//		String line=dr.readLine();
//		DecimalFormat decimalFormat=new DecimalFormat("0000");
//		int itemCount=0;
//		String assertionIDPrevious="";
//
//		while(line!= null){
//
//			ArrayList<String> lineSplit=utilitiesCSVFile.fromCSVLinetoArray(line);
//
//			ArrayList<String> nonemptyLineSplit=removeEmtpy(lineSplit);
//
//
//			if(nonemptyLineSplit.size()!=0){
//
//				String assertionID = nonemptyLineSplit.get(1);
//				String formulaString = "$pos ge 0";
//				String elementname=nonemptyLineSplit.get(2);
//
//				itemCount++;
//
//				//generate value assertion with the first item
//				if(itemCount==1){
//
//					assertionIDPrevious=assertionID;
//					itemCount=1;
//
//					//generate value assertion
//					Element assertion=link.addElement("va:valueAssertion");
//					assertion.addAttribute("id", assertionID);
//					assertion.addAttribute("test", formulaString);
//					assertion.addAttribute("xlink:label", assertionID);
//					assertion.addAttribute("aspectModel", "dimensional");
//					assertion.addAttribute("implicitFiltering", "true");
//					assertion.addAttribute("xlink:type", "resource");
//
//					//generate messsage arc
//					Element genarc=link.addElement("gen:arc");
//					genarc.addAttribute("xlink:from", assertionID );
//					genarc.addAttribute("xlink:to", "message");
//					genarc.addAttribute("order", "1.0");
//					genarc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message");
//					genarc.addAttribute("xlink:type", "arc");
//
//					//generate variable & variable arc
//					Element posVariable=link.addElement("variable:factVariable");
//					posVariable.addAttribute("id", "posVariable_"+assertionID);
//					posVariable.addAttribute("xlink:label", "posVariable_"+assertionID);
//					posVariable.addAttribute("bindAsSequence", "true");
//					posVariable.addAttribute("xlink:type", "resource");
//
//					Element posVariableArc=link.addElement("variable:variableArc");
//					posVariableArc.addAttribute("name", "pos");
//					posVariableArc.addAttribute("order", "2.0");
//					posVariableArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-set");
//					posVariableArc.addAttribute("xlink:from", assertionID);
//					posVariableArc.addAttribute("xlink:to", "posVariable_"+assertionID);
//					posVariableArc.addAttribute("xlink:type","arc");
//
//					int DFCount=1;
//					for(int columnIndex=3;columnIndex<nonemptyLineSplit.size();columnIndex++){
//						if(columnIndex%3==0){
//							String axis=nonemptyLineSplit.get(columnIndex);
//							String member=nonemptyLineSplit.get(columnIndex+1);
//							String complement=nonemptyLineSplit.get(columnIndex+2);
//
//							//generate dimension filter & arc
//							Element posDF=link.addElement("df:explicitDimension");
//							posDF.addAttribute("xlink:type", "resource");
//							posDF.addAttribute("id", "posDF"+DFCount+"_"+assertionID);
//							posDF.addAttribute("xlink:label", "posDF"+DFCount+"_"+assertionID);
//							posDF.addElement("df:dimension").addElement("df:qname").setText(axis);
//							posDF.addElement("df:member").addElement("df:qname").setText(member);
//
//							Element posDFArc=link.addElement("variable:variableFilterArc");
//							posDFArc.addAttribute("xlink:from", "posVariable_"+assertionID);
//							posDFArc.addAttribute("xlink:to", "posDF"+DFCount+"_"+assertionID);
//							posDFArc.addAttribute("order", "1.0");
//							posDFArc.addAttribute("complement", complement.toLowerCase());
//							posDFArc.addAttribute("cover", "true");
//							posDFArc.addAttribute("priority", "0");
//							posDFArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//							posDFArc.addAttribute("xlink:type", "arc");
//							DFCount++;
//
//						}
//					}
//
//					//generate boolean filter & arc
//					Element blFilter=link.addElement("bf:orFilter");
//					blFilter.addAttribute("xlink:label", "blFilter_"+assertionID);
//					blFilter.addAttribute("xlink:type", "resource");
//
//					Element blFilterArc=link.addElement("variable:variableFilterArc");
//					blFilterArc.addAttribute("xlink:from", "posVariable_"+assertionID);
//					blFilterArc.addAttribute("xlink:to", "blFilter_"+assertionID);
//					blFilterArc.addAttribute("complement", "false");
//					blFilterArc.addAttribute("cover", "true");
//					blFilterArc.addAttribute("order", "2.0");
//					blFilterArc.addAttribute("priority", "0");
//					blFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/variable-filter");
//					blFilterArc.addAttribute("xlink:type", "arc");
//
//					//generate primary item filter and filter arc
//					Element itemFilter=link.addElement("cf:conceptName");
//					itemFilter.addAttribute("xlink:type", "resource");
//					itemFilter.addAttribute("id", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addAttribute("xlink:label", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addElement("cf:concept").addElement("cf:qname").setText(elementname);
//
//					Element itemFilterArc=link.addElement("variable:variableFilterArc");
//					itemFilterArc.addAttribute("xlink:from", "blFilter_"+assertionID);
//					itemFilterArc.addAttribute("xlink:to", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilterArc.addAttribute("order", itemCount+".0");
//					itemFilterArc.addAttribute("complement", "false");
//					itemFilterArc.addAttribute("cover", "true");
//					itemFilterArc.addAttribute("priority", "0");
//					itemFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/boolean-filter");
//					itemFilterArc.addAttribute("xlink:type", "arc");
//
//
//
//				}else{
//
//					//generate primary item filter and filter arc
//					Element itemFilter=link.addElement("cf:conceptName");
//					itemFilter.addAttribute("xlink:type", "resource");
//					itemFilter.addAttribute("id", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addAttribute("xlink:label", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilter.addElement("cf:concept").addElement("cf:qname").setText(elementname);
//
//					Element itemFilterArc=link.addElement("variable:variableFilterArc");
//					itemFilterArc.addAttribute("xlink:from", "blFilter_"+assertionID);
//					itemFilterArc.addAttribute("xlink:to", "itemfilter"+decimalFormat.format(itemCount)+"_"+assertionID);
//					itemFilterArc.addAttribute("order", itemCount+".0");
//					itemFilterArc.addAttribute("complement", "false");
//					itemFilterArc.addAttribute("cover", "true");
//					itemFilterArc.addAttribute("priority", "0");
//					itemFilterArc.addAttribute("xlink:arcrole", "http://xbrl.org/arcrole/2008/boolean-filter");
//					itemFilterArc.addAttribute("xlink:type", "arc");
//
//				}
//
//
//
//
//			}
//
//			line = dr.readLine();
//		}
//
//
//		dr.close();
//		f.close();
//
//	}
//}
