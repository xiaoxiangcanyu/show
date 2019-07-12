package src.Promote.New.ComplianceChecking;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.*;
import src.Promote.New.ComplianceChecking.Util.ComplianceBasicUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static src.Promote.New.ComplianceChecking.Util.GetInformationFromComplianceCellValueSheet.GetInformationFromComplianceCellValueSheetMethod;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromComplianceSpecialItemCheck.GetInformationFromComplianceSpecialItemCheckMethod;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromSheetCompliance.GetInformationFromComplianceSheetCompletenessCheckSheetMethod;
import static src.Promote.Old.OriginalDataDeal.Util.IntergrityCheck.GetInformationFromIntergrityCheck.GetSheetTransInformationFromIntergrityCheck;

/**
 * 合规性校验的工具类
 */
public class ComplianceCheck extends ComplianceBasicUtil {
    /**
     * @param compliancesheetintegritycheckconfigpath    Compliance-SheetIntegrityCheck 配置文件
     * @param ComplianceSheetCompletenessCheckConfigPath Compliance-SheetCompletenessCheck 配置文件
     * @param ComplianceCellValueComparisonConfigPath    Compliance-CellValueComparison 配置文件
     * @param ComplianceSpecialItemCheckConfigPath       Compliance-SpecialItemCheck 配置文件
     * @param compileuncheckpasspath                     合规性校验没通过的文件路径
     * @param compilecheckpasspath                       合规性校验通过的文件路径
     * @param workbook                                   待校验的输入文件工作簿
     * @param controlsheetlist                           控制表数据集合
     * @param exceptionphasedolist                       异常表数据集合
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void ComplianceCheckMethod(String compliancesheetintegritycheckconfigpath, String ComplianceSheetCompletenessCheckConfigPath, String ComplianceCellValueComparisonConfigPath, String ComplianceSpecialItemCheckConfigPath, String companyname, String compileuncheckpasspath, String compilecheckpasspath, Workbook workbook, List<ControlSheetDO> controlsheetlist, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {

        int BeforeComplianceCheckNum = exceptionphasedolist.size();
        //==============================================================Compliance-SheetIntegrityCheck====================================================================================
        ComplianceSheetIntegrityCheck(workbook, companyname, compliancesheetintegritycheckconfigpath, exceptionphasedolist);
        //==============================================================Compliance-SheetIntegrityCheck====================================================================================

        //==============================================================Compliance-SheetCompletenessCheck1====================================================================================
        ComplianceSheetCompletenessCheck1(workbook, companyname, ComplianceSheetCompletenessCheckConfigPath, exceptionphasedolist);
        //==============================================================Compliance-SheetCompletenessCheck1====================================================================================

        //==============================================================Compliance-SheetCompletenessCheck2====================================================================================
        ComplianceSheetCompletenessCheck2(workbook, companyname, ComplianceSheetCompletenessCheckConfigPath, exceptionphasedolist);
        //==============================================================Compliance-SheetCompletenessCheck2====================================================================================

        //==============================================================Compliance-SheetCompletenessCheck3====================================================================================
        ComplianceSheetCompletenessCheck3(workbook, companyname, ComplianceSheetCompletenessCheckConfigPath, exceptionphasedolist);
        //==============================================================Compliance-SheetCompletenessCheck3====================================================================================

        //==============================================================Compliance-CellValueComparison====================================================================================
        ComplianceCellValueComparison(workbook, companyname, ComplianceCellValueComparisonConfigPath, exceptionphasedolist);
        //==============================================================Compliance-CellValueComparison====================================================================================

        //==============================================================Compliance-SpecialItemCheck====================================================================================
        ComplianceSpecialItemCheck(workbook, companyname, ComplianceSpecialItemCheckConfigPath, exceptionphasedolist);
        //==============================================================Compliance-SpecialItemCheck====================================================================================


//        //==============================================================输出合规性校验的文件====================================================================================
        if (BeforeComplianceCheckNum < exceptionphasedolist.size()) {
            ExportUnCheckedSource(workbook, compileuncheckpasspath);
        } else {
            ExportCheckedSource(workbook, compilecheckpasspath);
        }
//        //==============================================================输出合规性校验的文件====================================================================================

    }



//=====================================================文件校验函数========================================================================

    /**
     * 校验sheet页的数量是否完整
     */
    private static void ComplianceSheetIntegrityCheck(Workbook workbook, String companyname, String integritycheckPath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        String[] vl = new String[16];
        //获取当前sheet名集合
        String[] actualsheetarr = new String[15];
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            actualsheetarr[i] = workbook.getSheetName(i);
        }

        //获取完整性校验集合
        List<String> templatesheetarrlist = GetSheetTransInformationFromIntergrityCheck(integritycheckPath);
        List<String> actualsheetarrlist = new ArrayList<String>(Arrays.asList(actualsheetarr));
        //两个集合取交集
        actualsheetarrlist.retainAll(templatesheetarrlist);
        //如果真实表的sheet表的数量缺失
        if (actualsheetarrlist.size() != templatesheetarrlist.size()) {
            String LackList = getLackList(actualsheetarrlist, templatesheetarrlist);
            if (LackList.contains("-")) {
                vl = LackList.split("-");
            } else {
                vl[0] = LackList;
            }
//            //表完整性校验不通过，输出异常信息
            for (int i = 0; i < vl.length; i++) {
                if (vl[i] != null) {
                    if (vl[i].length() > 0) {
                        ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                        exceptionPhaseDO.setReportName(companyname);
                        exceptionPhaseDO.setSheetName(vl[i]);
                        exceptionPhaseDO.setExceptionType(vl[i] + "Sheet缺失");
                        exceptionphasedolist.add(exceptionPhaseDO);
                    }
                }


            }


        }

    }

    /**
     * 校验必填的单元格是否为空
     */
    private static void ComplianceSheetCompletenessCheck1(Workbook workbook, String companyname, String compliancesheetcompletenesscheckconfigpath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<ComplianceSheetCompletenessDO> list = GetInformationFromComplianceSheetCompletenessCheckSheetMethod(compliancesheetcompletenesscheckconfigpath);
        for (ComplianceSheetCompletenessDO complianceSheetCompletenessDO : list) {
            Sheet sheet = workbook.getSheet(complianceSheetCompletenessDO.getSheet());
            CellReference cellReference = new CellReference(complianceSheetCompletenessDO.getCompulsoryCells());
            if (sheet != null) {
                Row row = sheet.getRow(cellReference.getRow());
                if (row != null) {
                    Cell cell = row.getCell(cellReference.getCol());
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue().length() == 0) {
                            ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                            exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                            exceptionPhaseDO.setReportName(companyname);
                            exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getCompulsoryCells() + "单元格为空");
                            exceptionphasedolist.add(exceptionPhaseDO);
                        }
                    } else {
                        ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                        exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                        exceptionPhaseDO.setReportName(companyname);
                        exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getCompulsoryCells() + "单元格为空");
                        exceptionphasedolist.add(exceptionPhaseDO);
                    }
                }


            }

        }

    }


    /**
     * 文件名校验，判断是否一致
     */
    private static void ComplianceSheetCompletenessCheck2(Workbook workbook, String companyname, String compliancesheetcompletenesscheckconfigpath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<ComplianceSheetCompletenessDO> list = GetInformationFromComplianceSheetCompletenessCheckSheetMethod(compliancesheetcompletenesscheckconfigpath);
        String[] vl = companyname.split("-");
        String companycode = vl[0];
        String year = vl[1];
        if (year!=null){
            year = year.substring(0,4);
        }
        String organizeName = vl[2];
        for (ComplianceSheetCompletenessDO complianceSheetCompletenessDO : list) {
            if (complianceSheetCompletenessDO.getFileNameMatchingCell() != null) {
                if (complianceSheetCompletenessDO.getFileNameMatchingCell().length() > 0) {
                    CellReference cellReference = new CellReference(complianceSheetCompletenessDO.getFileNameMatchingCell());
                    Sheet sheet = workbook.getSheet(complianceSheetCompletenessDO.getSheet());
                    if (sheet != null) {
                        Row row = sheet.getRow(cellReference.getRow());
                        Cell cell = row.getCell(cellReference.getCol());
                        Cell subjectcell = row.getCell(cellReference.getCol() - 1);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        subjectcell.setCellType(Cell.CELL_TYPE_STRING);
                        switch (subjectcell.getStringCellValue().replace("*", "").trim()) {
                            case "公司组织机构代码":
                                if (!companycode.equals(cell.getStringCellValue())) {
                                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                    exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                                    exceptionPhaseDO.setReportName(companyname);
                                    exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getFileNameMatchingCell() + "单元格与报告名比对失败");
                                    exceptionphasedolist.add(exceptionPhaseDO);
                                }
                                break;
                            case "报告年度":
                                if (!year.equals(cell.getStringCellValue())) {
                                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                    exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                                    exceptionPhaseDO.setReportName(companyname);
                                    exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getFileNameMatchingCell() + "单元格与报告名比对失败");
                                    exceptionphasedolist.add(exceptionPhaseDO);
                                }
                                break;
                            case "公司名称":
                                if (!organizeName.equals(cell.getStringCellValue())) {
                                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                    exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                                    exceptionPhaseDO.setReportName(companyname);
                                    exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getFileNameMatchingCell() + "单元格与报告名比对失败");
                                    exceptionphasedolist.add(exceptionPhaseDO);
                                }
                                break;
                            default:
                                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                                exceptionPhaseDO.setReportName(companyname);
                                exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getFileNameMatchingCell() + "单元格与报告名比对失败");
                                exceptionphasedolist.add(exceptionPhaseDO);
                                break;
                        }

                    }
                }
            }
        }
    }

    /**
     * 文件名报告类型测试-配置文件D列指定单元格与报告名称比对：将D列指定单元格信息（年报）转化为对应年份信息（1231）并判断报告名第二个字段（20151231）是否包含日期信息
     * @param workbook
     * @param companyname
     * @param complianceSheetCompletenessCheckConfigPath
     * @param exceptionphasedolist
     */
    private static void ComplianceSheetCompletenessCheck3(Workbook workbook, String companyname, String complianceSheetCompletenessCheckConfigPath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<ComplianceSheetCompletenessDO> list = GetInformationFromComplianceSheetCompletenessCheckSheetMethod(complianceSheetCompletenessCheckConfigPath);
//        System.out.println(JSON.toJSONString(list));
        String[] vl = companyname.split("-");
        String year = vl[1];
        if (year!=null && year.length()>=8){
            year = year.substring(4);
        }
        for (ComplianceSheetCompletenessDO complianceSheetCompletenessDO : list) {
            Sheet sheet = workbook.getSheet(complianceSheetCompletenessDO.getSheet());
            if (complianceSheetCompletenessDO.getReportType()!=null && complianceSheetCompletenessDO.getReportType().length()>=2){
                CellReference cellReference = new CellReference(complianceSheetCompletenessDO.getReportType());
                if (sheet != null) {
                    Row row = sheet.getRow(cellReference.getRow());
                    if (row != null) {
                        Cell cell = row.getCell(cellReference.getCol());
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if (cell.getStringCellValue().length() > 0) {
                                String reportType = cell.getStringCellValue();
                                switch (reportType){
                                    case "年报":
                                        reportType = "1231";
                                        break;
                                    case "一季报":
                                        reportType = "0331";
                                        break;
                                    case "半年报":
                                        reportType = "0630";
                                        break;
                                    case "三季报":
                                        reportType = "0930";
                                        break;
                                    default:
                                        break;
                                }
                                if (!year.equals(reportType)){
                                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                    exceptionPhaseDO.setSheetName(complianceSheetCompletenessDO.getSheet());
                                    exceptionPhaseDO.setReportName(companyname);
                                    exceptionPhaseDO.setExceptionType(complianceSheetCompletenessDO.getReportType() + "报告类型与报告名比对失败");
                                    exceptionphasedolist.add(exceptionPhaseDO);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 单元格的值大小比较的校验
     */
    private static void ComplianceCellValueComparison(Workbook workbook, String companyname, String compliancecellvaluecomparisonconfigpath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<ComplianceCellValueDO> list = GetInformationFromComplianceCellValueSheetMethod(compliancecellvaluecomparisonconfigpath);
        for (ComplianceCellValueDO complianceCellValueDO : list) {
            Sheet sheet = workbook.getSheet(complianceCellValueDO.getSheet());
            CellReference cellReference1 = new CellReference(complianceCellValueDO.getCell_1());
            CellReference cellReference2 = new CellReference(complianceCellValueDO.getCell_2());
            Integer cellvalue1 = getCellValue(sheet, cellReference1);
            Integer cellvalue2 = getCellValue(sheet, cellReference2);
            if (cellvalue1 != null && cellvalue2 != null) {
                if (cellvalue2 >= cellvalue1) {
                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                    exceptionPhaseDO.setSheetName(complianceCellValueDO.getSheet());
                    exceptionPhaseDO.setReportName(companyname);
                    exceptionPhaseDO.setExceptionType(complianceCellValueDO.getCell_1() + "单元格大于或等于" + complianceCellValueDO.getCell_2() + "单元格");
                    exceptionphasedolist.add(exceptionPhaseDO);
                }
            } else {
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(complianceCellValueDO.getSheet());
                exceptionPhaseDO.setReportName(companyname);
                exceptionPhaseDO.setExceptionType(complianceCellValueDO.getCell_1() + "和" + complianceCellValueDO.getCell_2() + "单元格的内容不存在");
                exceptionphasedolist.add(exceptionPhaseDO);
            }


        }
    }

    /**
     * 次数校验
     */
    private static void ComplianceSpecialItemCheck(Workbook workbook, String companyname, String compliancecellvaluecomparisonconfigpath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<ComplianceSpecialItemCheckDO> list = GetInformationFromComplianceSpecialItemCheckMethod(compliancecellvaluecomparisonconfigpath);
        for (ComplianceSpecialItemCheckDO complianceSpecialItemCheckDO : list) {
            Sheet sheet = workbook.getSheet(complianceSpecialItemCheckDO.getSheet());
            int count = getCountOfItem(sheet, complianceSpecialItemCheckDO.getItems());
            if (count > Integer.parseInt(complianceSpecialItemCheckDO.getCounts())) {
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(complianceSpecialItemCheckDO.getSheet());
                exceptionPhaseDO.setReportName(companyname);
                exceptionPhaseDO.setExceptionType(complianceSpecialItemCheckDO.getItems() + "校验未通过");
                exceptionphasedolist.add(exceptionPhaseDO);
            }

        }
    }

    /**
     * 统计A列中Item的count
     *
     * @param sheet
     * @param items
     */
    private static int getCountOfItem(Sheet sheet, String items) {
        int count = 0;
        if (sheet != null) {
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        if (cell.getStringCellValue().equals(items)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * 获取单元格有效的数据
     *
     * @param sheet
     * @param cellReference
     * @return
     */
    private static Integer getCellValue(Sheet sheet, CellReference cellReference) {
        String cellvalue = "";
        try {
            Row row = sheet.getRow(cellReference.getRow());
            Cell cell = row.getCell(cellReference.getCol());
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell != null) {
                cellvalue = cell.getStringCellValue().substring(0, 4);
                return Integer.parseInt(cellvalue);

            }
        } catch (Exception e) {

        }
        return null;
    }


//=====================================================文件校验函数========================================================================


    public static void main(String[] args) throws IOException, InvalidFormatException {
        String directorypath = args[0];
//        String directorypath = "C:\\Users\\songyu\\Desktop\\XBRL新版\\合规性校验阶段所需文件";
        String excelpath = "";
        String companyname = "";
        String checkfilePathDirectory = "";
        String exceptionfilePathDirectory = "";
//        ========================================================输出文件的路径==================================================================
        String exportcontrolsheetphasepath = "";//控制表路径
        String exceptionphasepath = "";//异常表路径
        String exceptionfilepath = "";//异常文件路径
        String checkfilePath = "";//校验合格表的路径
//        ========================================================输出文件的路径==================================================================
//        ==============================================================输出表数据集合的初始化=======================================================================================
        List<ControlSheetDO> controlsheetlist = new ArrayList<>();//控制表的数据集合
        List<ExceptionPhaseDO> exceptionphasedolist = new ArrayList<>();//异常报告的数据集合
//        ==============================================================输出表数据集合的初始化=======================================================================================
//        ========================================================配置文件的路径==================================================================
        String complianceconfigdirectory = "";
        String compliancesheetintegritycheckconfigpath = "";
        String compliancesheetcompletenesscheckconfigpath = "";
        String compliancecellvaluecomparisonconfigpath = "";
        String compliancespecialitemcheckconfigpath = "";
//        ========================================================配置文件的路径==================================================================

        File directoryfile = new File(directorypath);
        File[] files = directoryfile.listFiles();
        System.out.println("===========================================================开始合规性校验=================================================================");
        for (File file : files) {
            //输出文件的文件夹
            if (file.getAbsolutePath().contains("Compliance-输出文件")) {
                exportcontrolsheetphasepath = file.getAbsolutePath() + "\\Control Sheet-Phase1.xlsx";
                File outputDirectory = new File(file.getAbsolutePath());
                File[] outputDirectoryList = outputDirectory.listFiles();
                for (File outputDirectoryDO : outputDirectoryList) {
                    if (outputDirectoryDO.getAbsolutePath().contains("Checked Source")) {
                        checkfilePathDirectory = outputDirectoryDO.getAbsolutePath();

                    } else if (outputDirectoryDO.getAbsolutePath().contains("Exception-Phase1")) {
                        exceptionphasepath = outputDirectoryDO.getAbsolutePath() + "\\" + "Exception-Phase1.xlsx";
                        exceptionfilePathDirectory = outputDirectoryDO.getAbsolutePath() + "\\Failed Souce";
                        //Fail文件夹每次更新前都删除和清空
                        File ExceptionFile = new File(exceptionfilePathDirectory);
                        File[] files8 = ExceptionFile.listFiles();
                        for (File file1:files8){
                            file1.delete();
                        }
                    }
                }
            } else if (file.getAbsolutePath().contains("Compliance-config")) {
                complianceconfigdirectory = file.getAbsolutePath();
                File complianceconfigfiledirectory = new File(complianceconfigdirectory);
                File[] complianceconfigfileList = complianceconfigfiledirectory.listFiles();
                for (File complianceconfigfile : complianceconfigfileList) {
                    if (complianceconfigfile.getAbsolutePath().contains("Compliance-SheetIntegrityCheck")) {
                        compliancesheetintegritycheckconfigpath = complianceconfigfile.getAbsolutePath();
                    } else if (complianceconfigfile.getAbsolutePath().contains("Compliance-SheetCompletenessCheck")) {
                        compliancesheetcompletenesscheckconfigpath = complianceconfigfile.getAbsolutePath();
                    } else if (complianceconfigfile.getAbsolutePath().contains("Compliance-CellValueComparison")) {
                        compliancecellvaluecomparisonconfigpath = complianceconfigfile.getAbsolutePath();
                    } else if (complianceconfigfile.getAbsolutePath().contains("Compliance-SpecialItemCheck")) {
                        compliancespecialitemcheckconfigpath = complianceconfigfile.getAbsolutePath();
                    }
                }
            } else if (file.getAbsolutePath().contains("InputFile")) {
                String InputFileDirectoy = file.getAbsolutePath();
                File InputFileDirectoyPath = new File(InputFileDirectoy);
                File[] files1 = InputFileDirectoyPath.listFiles();
                for (File file1 : files1) {
                    excelpath = file1.getAbsolutePath();
                    companyname = excelpath.substring(excelpath.lastIndexOf("\\") + 1, excelpath.lastIndexOf("."));
//  ======================================输出文件路径==================================================================================
                    checkfilePath = checkfilePathDirectory + "\\" + companyname + ".xlsx";
                    exceptionfilepath = exceptionfilePathDirectory + "\\" + companyname + ".xlsx";
//  ======================================输出文件路径==================================================================================

                    Workbook workbook = WorkbookFactory.create(new FileInputStream(excelpath));//获取待处理的excel文件流
                    System.out.println(excelpath);
//        ==============================================================**************************合规性校验*************************=======================================================================================
                    CentralSheetInitialize(controlsheetlist, companyname);//初始化中心表的数据
                    ComplianceCheckMethod(compliancesheetintegritycheckconfigpath, compliancesheetcompletenesscheckconfigpath, compliancecellvaluecomparisonconfigpath, compliancespecialitemcheckconfigpath, companyname, exceptionfilepath, checkfilePath, workbook, controlsheetlist, exceptionphasedolist);
//        ==============================================================**************************合规性校验**************************=======================================================================================
                }

            }

        }
//        ==============================================================路径初始化=======================================================================================


//

        //==============================================================输出异常报告====================================================================================
        ExportExceptionPhaseReport(exceptionphasedolist, exceptionphasepath);
        //==============================================================输出异常报告====================================================================================

        //==============================================================输出控制表======================================================================================================
        CheckCompliance(exceptionphasedolist, controlsheetlist);//校验合规性是否通过
        ExportControlSheetPhase(controlsheetlist, exportcontrolsheetphasepath);
        //==============================================================输出控制表======================================================================================================
        System.out.println("===========================================================合规性校验结束=================================================================");

    }


}
