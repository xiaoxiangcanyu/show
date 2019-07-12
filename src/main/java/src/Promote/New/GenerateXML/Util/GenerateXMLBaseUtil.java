package src.Promote.New.GenerateXML.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.nfunk.jep.JEP;
import src.Promote.New.GenerateXML.Domain.*;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static src.Promote.New.GenerateXML.Util.GetETLDataList.GetETLDataList;
import static src.Promote.New.GenerateXML.Util.GetInformationFromSpecialElements.GetInformationFromSpecialElementsMethod;
import static src.Promote.New.GenerateXML.Util.MappingData.MappingDataMethod;
import static src.Promote.New.GenerateXML.Util.StartGenerateXMLGenerateXML.StartGenerateXMLMethod;
import static src.Promote.New.ReadyMappingCheck.Util.GetInformationFromMappingConfig.GetInformationFromMappingConfig;

/**
 * 生成XML的基类
 */
public class GenerateXMLBaseUtil {
    /**
     * 获取Mapping之后的元素集合
     *
     * @param workbook
     * @param year
     * @param sheetIntegrityCheckPah
     * @param elementMappingPath
     */
    public static List<MappedDataDO> MappingElements(Workbook workbook, String year, String sheetIntegrityCheckPah, String elementMappingPath) throws IOException, InvalidFormatException {
//       ==========================================================获取从清洗之后的xlsx获取需要的数据=================================================================================
        List<ReadyMappingDataDO> list = GetETLDataList(workbook, sheetIntegrityCheckPah, elementMappingPath);
//       ==========================================================获取从清洗之后的xlsx获取需要的数据=================================================================================
//       ==========================================================获取从配置文件中获取MappingList=================================================================================
        List<ReadyMappingConfigDO> MappingList = GetInformationFromMappingConfig(elementMappingPath);
//       ==========================================================获取从配置文件中获取MappingList==================================================================================

        //=========================================================将提取的数据集合和MappingList进行Map=============================================================================
        List<MappedDataDO> mappedList = MappingDataMethod(list, MappingList);
        //=========================================================将提取的数据集合和MappingList进行Map=============================================================================
        //====================================================================集合数据去重==========================================================================================
//        List<MappedDataDO> removemappedlist = RemoveDuplicateMapped(mappedList);
        //====================================================================集合数据去重==========================================================================================
        return mappedList;
    }

    /**
     * 特殊元素转换
     *
     * @param finallist
     * @param specialElemmentConfigPath
     */
    public static List<MappedDataDO> TranferSpecialItems(List<MappedDataDO> finallist, String specialElemmentConfigPath, String Year) throws IOException, InvalidFormatException {
        List<SpecialElementsDO> list = GetInformationFromSpecialElementsMethod(specialElemmentConfigPath);
        for (MappedDataDO mappedDataDO : finallist) {
            String Qname = mappedDataDO.getQName();
            for (SpecialElementsDO specialElementsDO : list) {
                if (Qname.equals(specialElementsDO.getElementName())) {
                    String ContextIdYear = specialElementsDO.getYear();
                    JEP jep = new JEP();
                    jep.addVariable("n", Double.parseDouble(Year));
                    jep.parseExpression(ContextIdYear);
                    String finalYear = jep.getValue() + "";
                    finalYear = finalYear.substring(0, 4);
                    if (mappedDataDO.getContextRef().contains(finalYear)) {
                        mappedDataDO.setLabelName("");
                    }

                }
            }
        }
        return finallist;
    }

    /**
     * 匹配好之后的集合去重
     *
     * @param mappedList
     * @return
     */
    public static List<MappedDataDO> RemoveDuplicateMapped(List<MappedDataDO> mappedList) {

        List<MappedDataDO> rmovemappedlist = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (MappedDataDO mappedDataDO : mappedList)
        {
            if (set.add(mappedDataDO.getText()+mappedDataDO.getQName()+mappedDataDO.getLabelName()+mappedDataDO.getUnitRef()+mappedDataDO.getType()+mappedDataDO.getContextRef())) {
                rmovemappedlist.add(mappedDataDO);
            }
        }
        return rmovemappedlist;
    }


    /**
     * 获取OrganizationNumber
     *
     * @param companyName
     * @return
     */
    public static String GetOrganizationNumber(String companyName) {
        String OrganizationNumber = companyName.split("-")[0];
        return OrganizationNumber;
    }


    /**
     * 获取校验中心表的是否通过
     *
     * @param exceptionphasedolist
     * @param controlsheetlist
     */
    public static void CheckReadyMapping(List<GenerateXMLExceptionDO> exceptionphasedolist, List<GenerateXMLControlSheetDO> controlsheetlist) {
        Set<String> set = new HashSet<>();//创建去重集合
        for (GenerateXMLExceptionDO exceptionPhaseDO : exceptionphasedolist) {
            String companyname = exceptionPhaseDO.getReportName().split("-")[2];
            set.add(companyname);
        }

        for (GenerateXMLControlSheetDO controlSheetDO : controlsheetlist) {
            String reportname = controlSheetDO.getCompanyName();
            if (reportname.length() != 0) {
                if (!set.add(reportname)) {
                    controlSheetDO.setIsChecked("Fail");
                } else {
                    controlSheetDO.setIsChecked("Pass");
                }
            } else {
                controlSheetDO.setIsChecked("Fail");
            }

        }
    }


    /**
     * 中心表数据初始化
     *
     * @param
     */
    public static void GenerateXMLCentralSheetInitialize(List<GenerateXMLControlSheetDO> controlSheetDOS, String excelPath) throws IOException, InvalidFormatException {
        String[] vl = excelPath.split("-");
        GenerateXMLControlSheetDO controlSheetDO = new GenerateXMLControlSheetDO();

        controlSheetDO.setOrganizationNumber(vl[0]);

        controlSheetDO.setCompanyName(vl[2]);

        controlSheetDO.setReportYear(vl[1]);
        controlSheetDOS.add(controlSheetDO);

    }

    /**
     * 去重操作
     *
     * @param finallist
     * @param generateXMLSuccessPath
     */
    public static List<MappedDataDO> DeleteDuplicateXMLNode(List<MappedDataDO> finallist, String generateXMLSuccessPath, String CompanyName, List<GenerateXMLExceptionDO> generateXMLexceptionphasedolist) throws DocumentException, IOException {
//=====================================================================去重步骤1=====================================================================
        List<MappedDataDO> list = DeleteDuplicateXMLElement(generateXMLSuccessPath, finallist, CompanyName, generateXMLexceptionphasedolist);
//=====================================================================去重步骤1=====================================================================

//=====================================================================去重步骤2=====================================================================
        DeleteUnusedElements(finallist, generateXMLSuccessPath);
//=====================================================================去重步骤2=====================================================================


        return list;
    }

    private static void DeleteUnusedElements(List<MappedDataDO> finallist, String generateXMLSuccessPath) throws DocumentException, IOException {
        Set<String> ContextSet = new HashSet<>();
        Set<String> UnitSet = new HashSet<>();
        for (MappedDataDO mappedDataDO : finallist) {
            if (mappedDataDO.getContextRef() != null) {
                ContextSet.add(mappedDataDO.getContextRef());
            }
            if (mappedDataDO.getUnitRef() != null) {
                UnitSet.add(mappedDataDO.getUnitRef());
            }
        }
        Document doc = new SAXReader().read(new File(generateXMLSuccessPath));
        Element root = doc.getRootElement();
        List<Element> ContextList = root.elements("context");
        List<Element> UnitList = root.elements("unit");
        for (Element element : ContextList) {
            if (ContextSet.add(element.attributeValue("id"))) {
                root.remove(element);
            }
        }
        for (Element element : UnitList) {
            if (UnitSet.add(element.attributeValue("id"))) {
                root.remove(element);
            }
        }

        FileOutputStream out = new FileOutputStream(generateXMLSuccessPath);//指定文件输出的位置
        //指定写出的格式
        OutputFormat format = OutputFormat.createCompactFormat(); //紧凑的格式.去除空格换行.
        //OutputFormat format = OutputFormat.createPrettyPrint(); //格式好的格式.有空格和换行.
        format.setEncoding("utf-8");//2.指定生成的xml文档的编码
        XMLWriter writer = new XMLWriter(out, format);//创建写出对象
        writer.write(doc);//写出对象
        writer.close();//关闭流
    }

    /**
     * 逐行遍历实例文档，当context 、elementName、decimal和unit都一致时，判断 value是否一致：
     * 若value一致，删除其余冗余只保留一条数据；
     * 若value不一致，错误信息输出至“Exception-Phase3-Transform.xls”文件中
     */
    private static List<MappedDataDO> DeleteDuplicateXMLElement(String generateXMLSuccessPath, List<MappedDataDO> finallist, String CompanyName, List<GenerateXMLExceptionDO> generateXMLexceptionphasedolist) {
        List<MappedDataDO> list = new ArrayList<>();
        List<MappedDataDO> list1 = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
//        for (MappedDataDO mappedDataDO : finallist) {
//            String str = mappedDataDO.getContextRef() + mappedDataDO.getQName() + mappedDataDO.getUnitRef() + mappedDataDO.getDecimals() + mappedDataDO.getLabelName() + mappedDataDO.getText();
//            if (set1.add(str)) {
//                list.add(mappedDataDO);
//            }
//
//        }
        for (MappedDataDO mappedDataDO : finallist) {
            String str1 = mappedDataDO.getContextRef() + mappedDataDO.getQName() + mappedDataDO.getUnitRef() + mappedDataDO.getDecimals()+mappedDataDO.getLabelName();
            String str = mappedDataDO.getContextRef() + mappedDataDO.getQName() + mappedDataDO.getUnitRef() + mappedDataDO.getDecimals()+mappedDataDO.getLabelName() + mappedDataDO.getText() ;
            if (set.add(str1)) {
                if (set.add(str)) {
                    list.add(mappedDataDO);
                }
            } else {
                if (set.add(str)) {
                    GenerateXMLExceptionDO generateXMLExceptionDO = new GenerateXMLExceptionDO();
                    generateXMLExceptionDO.setReportName(CompanyName);
                    generateXMLExceptionDO.setContext(mappedDataDO.getContextRef());
                    generateXMLExceptionDO.setDecimal(mappedDataDO.getDecimals());
                    generateXMLExceptionDO.setElementName(mappedDataDO.getQName());
                    generateXMLExceptionDO.setUnit(mappedDataDO.getUnitRef());
                    generateXMLexceptionphasedolist.add(generateXMLExceptionDO);
                }
            }
        }


        return list;
    }
    //    ===========================================================输出文件函数==============================================================

    /**
     * 输出校验未通过的函数
     */
    public static void ExportUnMappedSource(Workbook workbook, String compileuncheckedpasspath) throws IOException {
        FileOutputStream os = new FileOutputStream(compileuncheckedpasspath);
        workbook.write(os);
        os.close();
    }

    /**
     * 输出校验通过的函数
     */
    public static void ExportMappedSource(Workbook workbook, String compilecheckedpasspath) throws IOException {
        FileOutputStream os = new FileOutputStream(compilecheckedpasspath);
        workbook.write(os);
        os.close();
    }

    /**
     * 输出异常表
     */
    public static void ExportGenerateXMLExceptionPhaseReport(List<GenerateXMLExceptionDO> GenerateXMLControlDOList, String GenerateXMLExceptionReportPath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("GenerateXMLExceptionReportSheet");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"报告名称", "元素名称", "Context", "Decimal", "Unit"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (GenerateXMLExceptionDO generateXMLExceptionDO : GenerateXMLControlDOList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(generateXMLExceptionDO.getReportName());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(generateXMLExceptionDO.getElementName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(generateXMLExceptionDO.getContext());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(generateXMLExceptionDO.getDecimal());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(generateXMLExceptionDO.getUnit());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(GenerateXMLExceptionReportPath);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 输出控制表
     */
    public static void ExportGenerateXMLControlSheetPhase(List<GenerateXMLControlSheetDO> GenerateXMLControlSheetList, String GenerateXMLCentrolSheetPath) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(GenerateXMLCentrolSheetPath));
        Sheet sheet = workbook.getSheet("ControlSheet");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                   String code = cell.getStringCellValue();
                   for (GenerateXMLControlSheetDO generateXMLControlSheetDO:GenerateXMLControlSheetList){
                       if (generateXMLControlSheetDO.getOrganizationNumber().equals(code)){
                           row.createCell(5).setCellValue(generateXMLControlSheetDO.getIsChecked());
                       }

                   }
                }
            }

        }
        FileOutputStream fileOutputStream = new FileOutputStream(GenerateXMLCentrolSheetPath);
        workbook.write(fileOutputStream);
    }

    /**
     * 将含有labelName的元素直接输出
     *
     * @param GenerateXMLSuccessPath
     */
    public static void ExportLabelNameReport(List<MappedDataDO> List, String GenerateXMLSuccessPath, String CompanyName, String ElementMappingPath) throws IOException, InvalidFormatException {
        String GenerateXMLLabelSuccessPath = GenerateXMLSuccessPath.substring(0, GenerateXMLSuccessPath.lastIndexOf("\\") + 1) + CompanyName + "-标签统计.xlsx";
        String Company = CompanyName.split("-")[2];
        String Year = CompanyName.split("-")[1].substring(0,4);
        String Code = CompanyName.split("-")[0];
        List<LabelStatisticsDO> list = new ArrayList<>();
        for (MappedDataDO mappedDataDO : List) {
            if (mappedDataDO.getLabelName() != null) {
                if (mappedDataDO.getLabelName().trim().length() > 0) {
                    String sheet = getsheet(mappedDataDO.getLabelName().replace("1|", ""), mappedDataDO.getQName(), ElementMappingPath);
                    LabelStatisticsDO labelStatisticsDO = new LabelStatisticsDO();
                    labelStatisticsDO.setSheet(sheet);
                    labelStatisticsDO.setCompanyName(Company);
                    labelStatisticsDO.setLabelName(mappedDataDO.getLabelName());
                    labelStatisticsDO.setReportYear(Year);
                    labelStatisticsDO.setOrganizationNum(Code);
                    labelStatisticsDO.setElementName(mappedDataDO.getQName());
                    list.add(labelStatisticsDO);
                }
            }
        }
        ExportLabelStatstics(list, GenerateXMLLabelSuccessPath);
    }

    /**
     * 根据Qname和科目名称获取Sheet名
     *
     * @param LabelName
     * @param qName
     * @param elementMappingPath
     * @return
     */
    private static String getsheet(String LabelName, String qName, String elementMappingPath) throws IOException, InvalidFormatException {
        String SheetName = "";
        List<ReadyMappingConfigDO> MappingList = GetInformationFromMappingConfig(elementMappingPath);
        for (ReadyMappingConfigDO readyMappingConfigDO : MappingList) {
            if (qName.equals(readyMappingConfigDO.getCellid()) && LabelName.equals(readyMappingConfigDO.getSubject())) {
                SheetName = readyMappingConfigDO.getTable();
            }
            if ("".equals(SheetName)) {
                if (qName.equals(readyMappingConfigDO.getCellid())) {
                    SheetName = readyMappingConfigDO.getTable();
                }
            }

        }
        return SheetName;
    }

    private static void ExportLabelStatstics(List<LabelStatisticsDO> list, String generateXMLLabelSuccessPath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("GenerateXMLControlSheet");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名称", "报告年份", "表格信息", "标签名称", "元素名称"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (LabelStatisticsDO labelStatisticsDO : list) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(labelStatisticsDO.getOrganizationNum());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(labelStatisticsDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(labelStatisticsDO.getReportYear());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(labelStatisticsDO.getSheet());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(labelStatisticsDO.getLabelName());
            row.createCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellValue(labelStatisticsDO.getElementName());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(generateXMLLabelSuccessPath);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 生成校验失败的XML
     *
     * @param list
     * @param generateXMLSuccessPath
     */
    public static void GenerateFailureXML(List<MappedDataDO> list, String generateXMLSuccessPath, String GenerateXMLExceptionPath) throws IOException, DocumentException {
        StartGenerateXMLMethod(list, generateXMLSuccessPath, GenerateXMLExceptionPath);
    }

    /**
     * 生成校验成功的XML
     *
     * @param list
     * @param generateXMLSuccessPath
     */
    public static void GenerateSuccessXML(List<MappedDataDO> list, String generateXMLSuccessPath) throws IOException, DocumentException {
        StartGenerateXMLMethod(list, generateXMLSuccessPath, generateXMLSuccessPath);

    }
//    ===========================================================输出文件函数==============================================================
}
