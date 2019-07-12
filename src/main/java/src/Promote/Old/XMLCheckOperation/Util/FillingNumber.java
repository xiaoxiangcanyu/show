package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.dom4j.DocumentException;
import src.Promote.Old.XMLCheckOperation.Domain.XMLDataDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.Promote.Old.XMLCheckOperation.Util.ExportUnmatched.ExportUnmatchedMethod;
import static src.Promote.Old.XMLCheckOperation.Util.FillDataBase.FillDataBaseMethod;
import static src.Promote.Old.XMLCheckOperation.Util.GetDataListFromXML.GetDataListFromXMlMethod;
import static src.Promote.Old.XMLCheckOperation.Util.RefreshFormula.RefreshFormulaMethod;

/**
 * 使用xml对校验模板进行灌数
 */
public class FillingNumber {
    public static void FillingNumberMethod(String XMLPath, String VBACheckPath, String VBAOuputPath, List<XMLDataDO> UnMatchList) throws DocumentException, IOException, InvalidFormatException {
        //获取灌数的数据
        Map<String, List> map = GetDataListFromXMlMethod(XMLPath, VBACheckPath);
        //灌数
        FileInputStream fis = new FileInputStream(VBACheckPath);
        Workbook wb = WorkbookFactory.create(fis);
        String[] vl = new String[]{"CP", "BS", "PL", "CF", "SI"};
        String year = "";
        for (int i = 0; i < vl.length; i++) {
            Sheet sheet = wb.getSheet(vl[i]);
//            sheet.setForceFormulaRecalculation(true);
            List<XMLDataDO> list = map.get(vl[i]);
            if ("CP".equals(vl[i])) {
                for (XMLDataDO xmlDataDO : list) {
                    if ("CollectionCurrentReportingPeriod".equals(xmlDataDO.getQName())) {
                        year = xmlDataDO.getValue();
                        year = clearyear(year);
                    }
                }
            }
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        String Qname = cell.getStringCellValue().replace("nafmii-cln:", "");
                        for (XMLDataDO xmlDataDO : list) {
                            if (xmlDataDO.getQName().equals(Qname)) {
                                switch (vl[i]) {
                                    case "CP":
                                        row.getCell(2).setCellValue(xmlDataDO.getValue().trim());
                                        break;
                                    case "BS":
                                        if (xmlDataDO.getContextRef().contains(year)) {
                                            row.getCell(3).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        } else {
                                            row.getCell(4).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        }
                                        break;
                                    case "PL":
                                        if (xmlDataDO.getContextRef().contains(year)) {
                                            row.getCell(3).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        } else {
                                            row.getCell(4).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        }
                                        break;
                                    case "CF":
                                        if (xmlDataDO.getContextRef().contains(year)) {
                                            row.getCell(3).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        } else {
                                            row.getCell(4).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        }
                                        break;
                                    case "SI":
                                        if (xmlDataDO.getContextRef().contains(year)) {
                                            row.getCell(3).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        } else {
                                            row.getCell(4).setCellValue(Double.parseDouble(xmlDataDO.getValue()));
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        //灌数
        //更新公式
        RefreshFormulaMethod(wb, wb.getSheet("Formula"));
        List<XMLDataDO> list = map.get("original");
        //将结果输出到database里面
        FillDataBaseMethod(wb, list);
        UnMatchList.addAll(map.get("Unmatched"));
        //输出
        FileOutputStream os = new FileOutputStream(VBAOuputPath);
        wb.write(os);
        os.close();
        System.out.println("===================================================灌数成功================================================================");

        //获取数据更新后的结果
//        getCheckResult(VBACheckPath);


    }

    /**
     * 清除换行符
     * @param year
     * @return
     */
    private static String clearyear(String year) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(year);
        year = m.replaceAll("");
        return year;
    }

    public static void main(String[] args) throws DocumentException, IOException, InvalidFormatException {
        String CurrentDirectory  = "C:\\Users\\songyu\\Desktop\\项目结构";
//        String CurrentDirectory = args[0];
        String XMLPath ="";
        String VBACheckPath = "";
        String UnmatchedReportPath ="";
        String DataFillingOutput = "";
        File DirectoryFile = new File(CurrentDirectory);
        File[] Directoryfiles = DirectoryFile.listFiles();
        for (File file:Directoryfiles){
            if (file.getAbsolutePath().contains("03 Excel标准模板灌数阶段")){
                String FillPath = file.getAbsolutePath();
                File file1 = new File(FillPath);
                File[] files = file1.listFiles();
                for (File file2:files){
                    if (file2.getAbsolutePath().contains("DataFillingInput")){
                        XMLPath = file2.getAbsolutePath();
                    }else if (file2.getAbsolutePath().contains("DataFillingExceptionReport")){
                        UnmatchedReportPath = file2.getAbsolutePath()+"\\"+"FillingExceptionReport.xlsx";
                    }else if (file2.getAbsolutePath().contains("Validation Excel-whole")){
                        DataFillingOutput = file2.getAbsolutePath();
                    }else if (file2.getAbsolutePath().contains("VBACheckTemplate")){
                        VBACheckPath =file2.getAbsolutePath()+"\\"+"Validate-NafmiiFormula-2014&2015.xlsm";
                    }
                }
            }
        }
//        String XMLPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\03 footnote修改-20170119\\process\\output\\";//XML的地址
//        String VBACheckPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\04 实例文档校验\\校验调试\\Nafmii-FormulaValidate-2014&2015-0119.xlsm";//VBA校验模板的地址
//        String UnmatchedReportPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\04 实例文档校验\\校验调试\\Exception.xlsx";
        List<XMLDataDO> UnMatchList = new ArrayList<>();
        File file = new File(XMLPath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.getAbsolutePath().endsWith(".xml")) {
                String CompanyName = file1.getAbsolutePath().substring(file1.getAbsolutePath().lastIndexOf("\\") + 1, file1.getAbsolutePath().lastIndexOf("."));
                 String DataFillingOutputPath = DataFillingOutput+ "\\" + CompanyName + "-校验文件.xlsm";
                System.out.println("===================================================开始灌数================================================================");
                System.out.println(file1.getAbsolutePath());
                FillingNumberMethod(file1.getAbsolutePath(), VBACheckPath, DataFillingOutputPath, UnMatchList);
            }


        }
        //将未匹配出来的结果输出到report文件
        ExportUnmatchedMethod(UnMatchList, UnmatchedReportPath);

    }
}
