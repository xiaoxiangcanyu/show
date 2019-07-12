package src.Promote.New.GenerateXML;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import src.Promote.New.GenerateXML.Domain.GenerateXMLControlSheetDO;
import src.Promote.New.GenerateXML.Domain.GenerateXMLExceptionDO;
import src.Promote.New.GenerateXML.Domain.MappedDataDO;
import src.Promote.New.GenerateXML.Domain.ReadyMappingDataDO;
import src.Promote.New.GenerateXML.Util.GenerateXMLBaseUtil;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static src.Promote.New.GenerateXML.Util.FillMappedData.FillMappedDataMethod;
import static src.Promote.New.GenerateXML.Util.GetETLDataList.GetETLDataList;
import static src.Promote.New.GenerateXML.Util.XsdMapping.XSDMappingaMethod;
import static src.Promote.New.ReadyMappingCheck.Util.ReadyMappingBaseUil.GetYear;

/**
 * 生成实例文档
 */
public class GenerateXML extends GenerateXMLBaseUtil {
    public static void GenerateXMLMethod(Workbook workbook,String CompanyName,String GenerateXMLConfigDirectory,List<GenerateXMLControlSheetDO> generateXMLcontrolsheetlist,List<GenerateXMLExceptionDO> generateXMLexceptionphasedolist,String GenerateXMLSuccessPath,String GenerateXMLExceptionPath) throws IOException, DocumentException, InvalidFormatException {
        int BeforeReadyMapping = generateXMLexceptionphasedolist.size();
        String Year = GetYear(CompanyName);
        String instanceXMLPath = "";
        String XMLCorePath = "";
        String ElementMappingPath = "";
        String SheetIntegrityCheckPath = "";
        String DecimalConfigPath = "";
        String SpecialElemmentConfigPath = "";
        String UnitConfigPath = "";
        String WeightConfigPath ="";
        File GenerateXMLConfig = new File(GenerateXMLConfigDirectory);
        File[] files = GenerateXMLConfig.listFiles();
        for (File file:files){
            if (file.getAbsolutePath().contains("nafmii_cln_core_2016-12-31"))
            {
                XMLCorePath = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("standard_instance")){
                instanceXMLPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Compliance-SheetIntegrityCheck")){
                SheetIntegrityCheckPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Transform-DecimalConfig")){
                DecimalConfigPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Transform-Element Mapping-") && file.getAbsolutePath().contains(Year)){
                ElementMappingPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Transform-SpecialElemmentConfig")){
                SpecialElemmentConfigPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Transform-UnitConfig")){
                UnitConfigPath  = file.getAbsolutePath();
            }else if (file.getAbsolutePath().contains("Transform-WeightConfig")){
                WeightConfigPath  = file.getAbsolutePath();
            }
        }

        //======================================================根据报告名称组织机构代码信息（报告名第一段信息），将空白XML模板中的“00000000”替换为对应的组织机构代码==============================================================
        ReplacementOrganizationNumber(CompanyName,instanceXMLPath,GenerateXMLSuccessPath);
        //======================================================根据报告名称组织机构代码信息（报告名第一段信息），将空白XML模板中的“00000000”替换为对应的组织机构代码==============================================================

        //======================================================实例文档转录。主要分为四个阶段：匹配元素、调用core schema确定元素类型、生成信息以及删除冗余信息==============================================================
        List<MappedDataDO> List =GenerateXMLMajority(workbook,CompanyName,Year,GenerateXMLSuccessPath,SheetIntegrityCheckPath,ElementMappingPath,XMLCorePath,DecimalConfigPath,UnitConfigPath,WeightConfigPath,SpecialElemmentConfigPath,generateXMLexceptionphasedolist);
        //======================================================实例文档转录。主要分为四个阶段：匹配元素、调用core schema确定元素类型、生成信息以及删除冗余信息==============================================================

//        //==============================================================输出Mapping预校验的文件====================================================================================
        if (BeforeReadyMapping == generateXMLexceptionphasedolist.size()) {
            GenerateSuccessXML(List, GenerateXMLSuccessPath);
            //==================对于Compliance-SheetIntegrityCheck配置文件所列示的sheet中，所有参与mapping的元素，提取数据中的第四列数据赋予给元素的nafmii_cln_model:labelName属性，并且将组织机构代码、公司名、报告年度、表名、第四列标签描述、元素名等信息输出至“组织机构代码-报告年份-公司名称-标签统计”文件中==============================================================
            ExportLabelNameReport(List,GenerateXMLSuccessPath,CompanyName,ElementMappingPath);
            //==================对于Compliance-SheetIntegrityCheck配置文件所列示的sheet中，所有参与mapping的元素，提取数据中的第四列数据赋予给元素的nafmii_cln_model:labelName属性，并且将组织机构代码、公司名、报告年度、表名、第四列标签描述、元素名等信息输出至“组织机构代码-报告年份-公司名称-标签统计”文件中==============================================================
        } else {
            GenerateFailureXML(List,GenerateXMLSuccessPath, GenerateXMLExceptionPath);
            ExportLabelNameReport(List,GenerateXMLExceptionPath,CompanyName,ElementMappingPath);
            File file  = new File(GenerateXMLSuccessPath);
            file.delete();
        }
//        //         ==============================================================输出Mapping预校验的文件====================================================================================
    }




    /**
     *根据报告名称组织机构代码信息（报告名第一段信息），将空白XML模板中的“00000000”替换为对应的组织机构代码
     * @param CompanyName
     * @param instanceXMLPath
     */
    private static void ReplacementOrganizationNumber(String CompanyName,String instanceXMLPath,String GenerateXMLSuccessPath) throws DocumentException, IOException {
        String OrganizationNumber =  GetOrganizationNumber(CompanyName);
        Document doc = new SAXReader().read(new File(instanceXMLPath));
        Element root = doc.getRootElement();
        List<Element> contextList = root.elements("context");
        for (Element element:contextList){
            Element entity = element.element("entity");
            Element identifier = entity.element("identifier");
            identifier.setText(OrganizationNumber);
        }
        FileOutputStream out = new FileOutputStream(GenerateXMLSuccessPath);//指定文件输出的位置
        //指定写出的格式
        OutputFormat format = OutputFormat.createCompactFormat(); //紧凑的格式.去除空格换行.
        //OutputFormat format = OutputFormat.createPrettyPrint(); //格式好的格式.有空格和换行.
        format.setEncoding("utf-8");//2.指定生成的xml文档的编码
        XMLWriter writer = new XMLWriter(out, format);//创建写出对象
        writer.write(doc);//写出对象
        writer.close();//关闭流
    }


    /**
     * 实例文档转录。主要分为四个阶段：匹配元素、调用core schema确定元素类型、生成信息以及删除冗余信息
     * @param sheetIntegrityCheckPah
     * @param elementMappingPath
     * @param xmlCorePath
     * @param decimalConfigPath
     * @param unitConfigPath
     * @param weightConfigPath
     * @param specialElemmentConfigPath
     * @param generateXMLexceptionphasedolist
     */
    private static List<MappedDataDO> GenerateXMLMajority(Workbook workbook,String CompanyName,String Year,String GenerateXMLSuccessPath,String sheetIntegrityCheckPah, String elementMappingPath, String xmlCorePath, String decimalConfigPath, String unitConfigPath, String weightConfigPath, String specialElemmentConfigPath, List<GenerateXMLExceptionDO> generateXMLexceptionphasedolist) throws IOException, InvalidFormatException, DocumentException {

        //================================================================================匹配元素==========================================================================================
        List<MappedDataDO> MappedList = MappingElements(workbook,Year,sheetIntegrityCheckPah,elementMappingPath);
        //================================================================================匹配元素==========================================================================================

        //================================================================================调用core schema确定元素类型==========================================================================================
        List<ReadyMappingDataDO> list = GetETLDataList(workbook,sheetIntegrityCheckPah,elementMappingPath);
        List<MappedDataDO> xsdmappedlist = XSDMappingaMethod(MappedList, xmlCorePath);
        //================================================================================调用core schema确定元素类型==========================================================================================

        //================================================================================生成信息==========================================================================================
        //将mapped,xsd好之后的元素进行属性补充，并针对pershare类型的footnote
        List<MappedDataDO> finallist = FillMappedDataMethod(xsdmappedlist, list, unitConfigPath, decimalConfigPath, weightConfigPath);
        //================================================================================生成信息==========================================================================================

        //================================================================================特殊元素转换==========================================================================================
        finallist = TranferSpecialItems(finallist,specialElemmentConfigPath,Year);
        //================================================================================特殊元素转换==========================================================================================

        //================================================================================删除冗余信息==========================================================================================
        List<MappedDataDO> List = DeleteDuplicateXMLNode(finallist,GenerateXMLSuccessPath,CompanyName,generateXMLexceptionphasedolist);

        //================================================================================删除冗余信息==========================================================================================
        return List;
    }




    public static void GenerateXMLTotal(String DirectoryPath,String GenerateXMLCentrolSheetPath) throws IOException, InvalidFormatException, DocumentException {
        System.out.println("===================================================开始生成实例文档================================================================================================");
//        String DirectoryPath = "C:\\Users\\songyu\\Desktop\\XBRL新版\\生成实例文档所需文件";//根目录文件夹
        String InputDirectory = "";//进行GenerateXML预处理的Excel文件夹
//        ===================================================GenerateXML的配置文件路径=====================================================================
        String ReadyNappingConfigDirectory = "";
//        ===================================================GenerateXML的配置文件路径=====================================================================

//        ===================================================GenerateXML的输出文件路径=====================================================================
        String TransformOutputDirectory = "";//Transform-输出文件夹

        String GenerateXMLSuccessDirecory = "";//GenerateXML预处理成功的文件夹
        String GenerateXMLSuccessPath = "";//GenerateXML预处理成功的文件绝对路径

        String GenerateXMLExceptionDirectory = "";//GenerateXML异常输出的文件夹
        String GenerateXMLExceptionPath = "";//GenerateXML异常输出的绝对路径
        String GenerateXMLExceptionReportDirectory = "";//生成XML异常输出的日志的父文件夹
        String GenerateXMLExceptionReportPath = "";//GenerateXML异常输出的日志的绝对路径

//        ===================================================GenerateXML的输出文件路径===========================================================================
        String CompanyName = "";

        File file = new File(DirectoryPath);
        File[] files = file.listFiles();
//===========================================================遍历文件夹，获取各文件真实路径==========================================================================
        for (File file1 : files) {
             if (file1.getAbsolutePath().contains("Transform-config")) {
                ReadyNappingConfigDirectory = file1.getAbsolutePath();
            } else if (file1.getAbsolutePath().contains("Transform-输出文件")) {
                 GenerateXMLSuccessDirecory = file1.getAbsolutePath()+"\\Transformed Source";
                TransformOutputDirectory = file1.getAbsolutePath();
                File file3 = new File(TransformOutputDirectory);
                File[] files1 = file3.listFiles();
                for (File file2 : files1) {
                    if (file2.getAbsolutePath().contains("ReadyMappingOuput")) {
                        InputDirectory = file2.getAbsolutePath();
                    } else if (file2.getAbsolutePath().contains("Exception-Phase3")) {
                        GenerateXMLExceptionReportDirectory = file2.getAbsolutePath();
                        GenerateXMLExceptionReportPath = GenerateXMLExceptionReportDirectory + "\\Exception-Phase3-Transform.xlsx";
                        GenerateXMLExceptionDirectory = GenerateXMLExceptionReportDirectory + "\\Failed Souce-Transform";
                    }
                }
            }
        }
        //Exception文件夹每次更新前都删除和清空
        File ExceptionFile = new File(GenerateXMLExceptionDirectory);
        File[] Exceptionfiles = ExceptionFile.listFiles();
        for (File Exceptionfile:Exceptionfiles){
            Exceptionfile.delete();
        }
//===========================================================遍历文件夹，获取各文件真实路径===============================================================================
        List<GenerateXMLControlSheetDO> generateXMLcontrolsheetlist = new ArrayList<>();
        List<GenerateXMLExceptionDO> generateXMLexceptionphasedolist = new ArrayList<>();
//===========================================================遍历输入文件夹，开始进行Mapping预校验=========================================================================
        File InputFileDiretory = new File(InputDirectory);
        File[] files1 = InputFileDiretory.listFiles();
        for (File file1 : files1) {
            String inputexcelpath = file1.getAbsolutePath();
            CompanyName = inputexcelpath.substring(inputexcelpath.lastIndexOf("\\") + 1, inputexcelpath.lastIndexOf("."));
            Workbook workbook = WorkbookFactory.create(new FileInputStream(inputexcelpath));//获取待处理的excel文件流

            GenerateXMLCentralSheetInitialize(generateXMLcontrolsheetlist, CompanyName);//初始化中心表的数据
//  ======================================输出文件路径==================================================================================
            GenerateXMLSuccessPath = GenerateXMLSuccessDirecory + "\\" + CompanyName + ".xml";
            GenerateXMLExceptionPath = GenerateXMLExceptionDirectory + "\\" + CompanyName + ".xml";
//  ======================================输出文件路径==================================================================================
            System.out.println(CompanyName);
            GenerateXMLMethod(workbook, CompanyName, ReadyNappingConfigDirectory, generateXMLcontrolsheetlist, generateXMLexceptionphasedolist, GenerateXMLSuccessPath, GenerateXMLExceptionPath);
        }
//===========================================================遍历文件夹，开始进行Mapping预校验=============================================================================
        //==============================================================输出异常报告====================================================================================
        ExportGenerateXMLExceptionPhaseReport(generateXMLexceptionphasedolist, GenerateXMLExceptionReportPath);
        //==============================================================输出异常报告====================================================================================

        //==============================================================输出控制表======================================================================================================
        CheckReadyMapping(generateXMLexceptionphasedolist, generateXMLcontrolsheetlist);//校验合规性是否通过
        ExportGenerateXMLControlSheetPhase(generateXMLcontrolsheetlist, GenerateXMLCentrolSheetPath);
        //==============================================================输出控制表======================================================================================================
        System.out.println("===================================================实例文档生成结束===============================================================================================");

    }
    }

