package src.Promote.New.ReadyMappingCheck.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.Promote.New.DataClean.Domain.ControlSheetDO;
import src.Promote.New.DataClean.Domain.ExceptionPhaseDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingControlSheetDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingExceptionPhaseDO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static src.Promote.New.ComplianceChecking.Util.GetInformationFromIntergrityCheck.GetInformationFromIntergrityCheck;
import static src.Promote.New.ReadyMappingCheck.Util.GetInformationFromMappingConfig.GetInformationFromMappingConfig;

/**
 * Mapping预校验基础工具类
 */
public class ReadyMappingBaseUil {
    /**
     * 根据sheet页，从工作表中获取BC列任意一个不为空的匹配元素
     *
     * @param sheetList
     * @param workbook
     * @return
     */
    public static List<ReadyMappingDO> GetMappingList(List<String> sheetList, Workbook workbook) {
        List<ReadyMappingDO> list = new ArrayList<>();
        for (String str : sheetList) {

            Sheet sheet = workbook.getSheet(str);
            if (sheet != null) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row!=null){
                        Cell A = row.getCell(0);
                        Cell B = row.getCell(1);
                        Cell C = row.getCell(2);
                        if (A!=null ){
                            A.setCellType(Cell.CELL_TYPE_STRING);
                            if (B!=null){
                                B.setCellType(Cell.CELL_TYPE_STRING);
                            }
                            if (C!=null){
                                C.setCellType(Cell.CELL_TYPE_STRING);
                            }
                            if ((B!=null && B.getStringCellValue().length()>0) || (C!=null && C.getStringCellValue().length()>0)){
                                ReadyMappingDO readyMappingDO = new ReadyMappingDO();
                                readyMappingDO.setSheet(str);
                                readyMappingDO.setSubject(A.getStringCellValue());
                                if (!"币种".equals(readyMappingDO.getSubject()) && !"度量衡".equals(readyMappingDO.getSubject()) && !"精确度".equals(readyMappingDO.getSubject())){
                                    list.add(readyMappingDO);
                                }
                            }
                        }

                    }

                }
            }
        }
        return list;
    }


    /**
     * 获取报告的年度
     *
     * @param companyName
     * @return
     */
    public static String GetYear(String companyName) {
        String year = "";
        if (companyName.length() > 0) {
            year = companyName.split("-")[1].substring(0, 4);
        }
        return year;
    }
    /**
     * 获取要进行匹配的表的列表
     * @param readyNappingConfigDirectory
     * @return
     */
    public static List<String> GetReadyMappingSheetList(String readyNappingConfigDirectory) throws IOException, InvalidFormatException {
        List<String> list = new ArrayList<>();
        File file = new File(readyNappingConfigDirectory);
        File[] files = file.listFiles();
        for (File file1:files){
            if(file1.getAbsolutePath().contains("Compliance-SheetIntegrityCheck")){
                 list = GetInformationFromIntergrityCheck(file1.getAbsolutePath());
            }
        }
        return list;
    }
    /**
     * 根据报告年度提取Mapping文件中的数据
     * @param year
     * @param readyNappingConfigDirectory
     */
    public static List<ReadyMappingConfigDO> GetReadyMappingDataByYear(String year, String readyNappingConfigDirectory) throws IOException, InvalidFormatException {
        List<ReadyMappingConfigDO> list = new ArrayList<>();
        File FilereadyNappingConfigDirectory = new File(readyNappingConfigDirectory);
        File[] files = FilereadyNappingConfigDirectory.listFiles();
        for (File file:files){
            if (file.getAbsolutePath().contains(year) && file.getAbsolutePath().endsWith(".xlsx")){
                list = GetInformationFromMappingConfig(file.getAbsolutePath());
            }
        }
        return list;
    }
    /**
     * 中心表数据初始化
     *
     * @param
     */
    public static void ReadyMappingCentralSheetInitialize(List<ReadyMappingControlSheetDO> controlSheetDOS, String excelPath) throws IOException, InvalidFormatException {
        String[] vl = excelPath.split("-");
        ReadyMappingControlSheetDO controlSheetDO = new ReadyMappingControlSheetDO();

        controlSheetDO.setOrganizationNumber(vl[0]);

        controlSheetDO.setCompanyName(vl[2]);

        controlSheetDO.setReportYear(vl[1]);
        controlSheetDOS.add(controlSheetDO);

    }
    /**
     * 获取校验中心表的是否通过
     *
     * @param exceptionphasedolist
     * @param controlsheetlist
     */
    public static void CheckReadyMapping(List<ReadyMappingExceptionPhaseDO> exceptionphasedolist, List<ReadyMappingControlSheetDO> controlsheetlist) {
        Set<String> set = new HashSet<>();//创建去重集合
        for (ReadyMappingExceptionPhaseDO exceptionPhaseDO : exceptionphasedolist) {
            String companyname = exceptionPhaseDO.getReportName().split("-")[2];
            set.add(companyname);
        }

        for (ReadyMappingControlSheetDO controlSheetDO : controlsheetlist) {
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
     * 输出控制表
     */
    public static void ExportReadyMappingControlSheetPhase(List<ReadyMappingControlSheetDO> ControlSheetDOList, String ExportControlSheetPhasePath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("ControlSheet");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编码", "企业名称", "报告年度","报告类型", "是否通过Mapping","是否通过Transform"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (ReadyMappingControlSheetDO controlSheetDO : ControlSheetDOList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(controlSheetDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(controlSheetDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(controlSheetDO.getReportYear());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(controlSheetDO.getReportType());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(controlSheetDO.getIsChecked());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(ExportControlSheetPhasePath);
        xssfWorkbook.write(fileOutputStream);

    }


    /**
     * 输出异常报告和校验未通过的文件
     */
    public static void ExportReadyMappingCleanExceptionPhaseReport(List<ReadyMappingExceptionPhaseDO> exceptionphasedolist, String exceptionphasepath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("ExceptionPhase");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"报告名称", "Sheet名称", "未匹配到的项目"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (ReadyMappingExceptionPhaseDO exceptionPhaseDO : exceptionphasedolist) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(exceptionPhaseDO.getReportName());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(exceptionPhaseDO.getSheetName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(exceptionPhaseDO.getUnmatchedItems());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(exceptionphasepath);
        xssfWorkbook.write(fileOutputStream);
    }


//    ===========================================================输出文件函数==============================================================

}
