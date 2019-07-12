//package src.main;
//
//
//import org.dom4j.Element;
//import xbrl.efmRules;
//import xbrl.extendRules;
//import xbrl.preCheck;
//import xbrl.utilities;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class checkMain {
//
//	public static ArrayList<String> xbrlIndex= new ArrayList<String>();
//	public static HashMap<String,String> xbrlFiles=new HashMap<String,String>();
//	public static String base,date,startdate,enddate,rule;
//	public static String rulesToCheck;
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
//		//validate config parameters load
//		Boolean xmlLoad=true;
//		if(xmlLoad){
//			Element config=utilities.loadxml(xbrlFilepath+"\\"+"validateConfig.xml");
//			base=config.element("base").getText();
//			date=config.element("date").getText();
//			startdate=config.element("startdate").getText();
//			enddate=config.element("enddate").getText();
//			rule=config.element("rule").getText();
//		}else{
//	        System.out.print(">>>>>Please input the expected base(XXXX):");
//			base=sc.nextLine();
//	        System.out.print(">>>>>Please input the expected date(YYYYMMDD):");
//			date=sc.nextLine();
//	        System.out.print(">>>>>Please input the expected cik(10 digit):");
//			startdate=sc.nextLine();
//	        System.out.print(">>>>>Please input the expected enddate(YYYY-MM-DD):");
//			enddate=sc.nextLine();
//		}
//
//
//		//directory scan
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+".xsd").exists()){
//			xbrlIndex.add("schema");
//			xbrlFiles.put("schema", xbrlFilepath+"\\"+base+"-"+date+".xsd");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+".xml").exists()){
//			xbrlIndex.add("instance");
//			xbrlFiles.put("instance", xbrlFilepath+"\\"+base+"-"+date+".xml");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+"_cal.xml").exists()){
//			xbrlIndex.add("calxml");
//			xbrlFiles.put("calxml", xbrlFilepath+"\\"+base+"-"+date+"_cal.xml");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+"_def.xml").exists()){
//			xbrlIndex.add("defxml");
//			xbrlFiles.put("defxml", xbrlFilepath+"\\"+base+"-"+date+"_def.xml");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+"_lab.xml").exists()){
//			xbrlIndex.add("labxml");
//			xbrlFiles.put("labxml", xbrlFilepath+"\\"+base+"-"+date+"_lab.xml");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+"_pre.xml").exists()){
//			xbrlIndex.add("prexml");
//			xbrlFiles.put("prexml", xbrlFilepath+"\\"+base+"-"+date+"_pre.xml");
//		}
//		if(new File(xbrlFilepath+"\\"+base+"-"+date+"_ref.xml").exists()){
//			xbrlIndex.add("refxml");
//			xbrlFiles.put("refxml", xbrlFilepath+"\\"+base+"-"+date+"_ref.xml");
//		}
//
//		//cleanReport
//		utilities.cleanReport();
//
//		if(xbrlFiles.size()==0){
//			utilities.printResult(">>>>>Expected xbrl files not detected. Please check the validateConfig.xml.");
//			System.exit(0);
//		}
//
//		//Precheck
//		utilities.printResult("****************************************\n");
//		utilities.printResult("Precheck being processed.\n");
//		preCheck.checkInput(base,date,startdate,enddate);
//		preCheck.checkFileExist(xbrlIndex,xbrlFiles);
//		preCheck.checkChinese(xbrlIndex,xbrlFiles);
//		preCheck.tryParse(xbrlIndex, xbrlFiles);
//		utilities.printResult("Precheck processing finished.\n");
//		utilities.printResult("****************************************\n");
//
//		//Inform ruleschecked
//		String rulechecked=
//		"The following Rules are available for validation.\n"+
//		"EFM Rules: 6.6.1&6.6.2, 6.6.15, 6.6.36, 6.6.37, 6.7.2, 6.7.6, 6.8.3, 6.8.11, 6.9.1, 6.11.4, 6.11.7\n"+
//		"Extended Rules: xsd.01, xml.01, xml.02, xml.03, pre.01, pre.02, pre.03, pre��04�� def.01, def.02, lab.01, lab.02, lab.03, cal.01\n";
//
//		utilities.printResult(rulechecked);
//		utilities.printResult("****************************************\n");
//
//		if(!xmlLoad){
//        System.out.print(">>>>>Specify rules to check(rule1,rule2...no input then all rules will be checked):");
//		rulesToCheck=sc.nextLine();
//		}else{
//			rulesToCheck=rule;
//		}
//
//		sc.close();
//
//
//
//		utilities.printResult("Rules selected to check: "+rulesToCheck);
//
//		//generateReport
//		if(rulesToCheck.isEmpty()){
//			utilities.printResult("All rules will be checked");
//			testMain();
//		}else{
//			selectMain();
//		}
//
//
//
//	}
//
//	public static void testMain() throws Exception{
//
//
//		//Generating test result
//		String testresult="";
//
//		utilities.printResult("EFM rules being checked.\n");
//
//		//6.6.1&6.6.2
//		testresult=efmRules.six_six_onetwo_Check(xbrlFiles,startdate,enddate);
//		utilities.printResult(testresult);
//
//		//6.6.15
//		testresult=efmRules.six_six_fifteen_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.6.36
//		testresult=efmRules.six_six_thirtysix_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.6.37
//		testresult=efmRules.six_six_thirtyseven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.7.2
//		testresult=efmRules.six_seven_two_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.7.6
//		testresult=efmRules.six_seven_six_Check(xbrlFiles,date);
//		utilities.printResult(testresult);
//
//		//6.8.3
//		testresult=efmRules.six_eight_three_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.8.11
//		testresult=efmRules.six_eight_eleven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.9.1
//		testresult=efmRules.six_nine_one_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.11.4
//		testresult=efmRules.six_eleven_four_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		//6.11.7
//		testresult=efmRules.six_eleven_seven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//
//		utilities.printResult("EFM rules check finished.\n");
//		utilities.printResult("****************************************\n");
//
//
//
//		utilities.printResult("Extended rules being checked.\n");
//		utilities.printResult("This process will take a few minutes.\n");
//
//		testresult=extendRules.xsd_one(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.xml_one(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.xml_two(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.xml_three(xbrlFiles,base);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.pre_one(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.pre_two(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.pre_three(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.pre_four(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.def_one(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.def_two(xbrlFiles);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.lab_one(xbrlFiles,base);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.lab_two(xbrlFiles,base);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.lab_three(xbrlFiles,base);
//		utilities.printResult(testresult);
//
//		testresult=extendRules.cal_one(xbrlFiles);
//		utilities.printResult(testresult);
//
//
//		utilities.printResult("Extended rules check finish.\n");
//		utilities.printResult("****************************************\n");
//
//
//		//Inform check status
//		utilities.printResult("Validation finished. Please see the error info above.\n");
//
//	}
//
//	public static void selectMain() throws Exception{
//
//		//Generating test result
//		String testresult="";
//
//		//set rule list
//		ArrayList<String> ruleList=new ArrayList<String>();
//		String[] ruleArray=rulesToCheck.split(",");
//		for(int i=0;i<ruleArray.length;i++){
//			ruleList.add(ruleArray[i]);
//		}
//
//
//
//
//		utilities.printResult("Selected rules being checked.\n");
//
//		//xsd.01
//		if(utilities.find(ruleList, "xsd.01")){
//		testresult=extendRules.xsd_one(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//xml.01
//		if(utilities.find(ruleList, "xml.01")){
//		testresult=extendRules.xml_one(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//xml.02
//		if(utilities.find(ruleList, "xml.02")){
//		testresult=extendRules.xml_two(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//xml.03
//		if(utilities.find(ruleList, "xml.03")){
//		testresult=extendRules.xml_three(xbrlFiles,base);
//		utilities.printResult(testresult);
//		}
//
//		//pre.01
//		if(utilities.find(ruleList, "pre.01")){
//		testresult=extendRules.pre_one(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//pre.02
//		if(utilities.find(ruleList, "pre.02")){
//		testresult=extendRules.pre_two(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//pre.03
//		if(utilities.find(ruleList, "pre.03")){
//		testresult=extendRules.pre_three(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//pre.04
//		if(utilities.find(ruleList, "pre.04")){
//			testresult=extendRules.pre_four(xbrlFiles);
//			utilities.printResult(testresult);
//		}
//
//
//		//def.01
//		if(utilities.find(ruleList, "def.01")){
//		testresult=extendRules.def_one(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//def.02
//		if(utilities.find(ruleList, "def.02")){
//		testresult=extendRules.def_two(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//lab.01
//		if(utilities.find(ruleList, "lab.01")){
//		testresult=extendRules.lab_one(xbrlFiles,base);
//		utilities.printResult(testresult);
//		}
//
//		//lab.02
//		if(utilities.find(ruleList, "lab.02")){
//		testresult=extendRules.lab_two(xbrlFiles,base);
//		utilities.printResult(testresult);
//		}
//
//		//lab.03
//		if(utilities.find(ruleList, "lab.03")){
//		testresult=extendRules.lab_three(xbrlFiles,base);
//		utilities.printResult(testresult);
//		}
//
//		//cal.01
//		if(utilities.find(ruleList, "cal.01")){
//		testresult=extendRules.cal_one(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.6.1&6.6.2
//		if(utilities.find(ruleList, "6.6.1-6.6.2")){
//		testresult=efmRules.six_six_onetwo_Check(xbrlFiles,startdate,enddate);
//		utilities.printResult(testresult);
//		}
//
//		//6.6.15
//		if(utilities.find(ruleList, "6.6.15")){
//		testresult=efmRules.six_six_fifteen_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.6.36
//		if(utilities.find(ruleList, "6.6.36")){
//		testresult=efmRules.six_six_thirtysix_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.6.37
//		if(utilities.find(ruleList, "6.6.37")){
//		testresult=efmRules.six_six_thirtyseven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.7.2
//		if(utilities.find(ruleList, "6.7.2")){
//		testresult=efmRules.six_seven_two_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.7.6
//		if(utilities.find(ruleList, "6.7.6")){
//		testresult=efmRules.six_seven_six_Check(xbrlFiles,date);
//		utilities.printResult(testresult);
//		}
//
//		//6.8.3
//		if(utilities.find(ruleList, "6.8.3")){
//		testresult=efmRules.six_eight_three_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.8.11
//		if(utilities.find(ruleList, "6.8.11")){
//		testresult=efmRules.six_eight_eleven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.9.1
//		if(utilities.find(ruleList, "6.9.1")){
//		testresult=efmRules.six_nine_one_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.11.4
//		if(utilities.find(ruleList, "6.11.4")){
//		testresult=efmRules.six_eleven_four_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		//6.11.7
//		if(utilities.find(ruleList, "6.11.7")){
//		testresult=efmRules.six_eleven_seven_Check(xbrlFiles);
//		utilities.printResult(testresult);
//		}
//
//		utilities.printResult("Selected rules check finished. See error info above.\n");
//
//	}
//
//}
