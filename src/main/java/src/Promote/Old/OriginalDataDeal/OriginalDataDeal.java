package src.Promote.Old.OriginalDataDeal;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import src.Promote.Old.OriginalDataDeal.Domain.CallitemCheck.CallitemCheckDO;
import src.Promote.Old.OriginalDataDeal.Domain.CoverCheck.CoverCheckDO;
import src.Promote.Old.OriginalDataDeal.Domain.DuplicateJudge.DuplicateJudgeDO;
import src.Promote.Old.OriginalDataDeal.Domain.DuplicateJudge.DuplicateJudgeInformation;
import src.Promote.Old.OriginalDataDeal.Domain.FilePropeties;
import src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck.IntegrityCheckDO;
import src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck.ReplacementDO;
import src.Promote.Old.OriginalDataDeal.Domain.LabelNameCheck.LabelNameCheckDO;
import src.Promote.Old.OriginalDataDeal.Domain.MappingCheck.MappingCheckDO;
import src.Promote.Old.OriginalDataDeal.Domain.MappingCheck.MappingConfigDO;
import src.Promote.Old.OriginalDataDeal.Domain.Pastespecial.PastespecialCheckDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.Promote.Old.OriginalDataDeal.Util.DulplicateJudge.GetInformationFromDulplicateConfig.GetInformationFromDulplicateConfig;
import static src.Promote.Old.OriginalDataDeal.Util.IntergrityCheck.GetInformationFromIntergrityCheck.GetSheetTransInformationFromIntergrityCheck;
import static src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck.FillSplitCell.FillSplitCellMethod;
import static src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck.GetListFromReplacementConfig.GetListFromReplacementConfig;
import static src.Promote.Old.OriginalDataDeal.Util.MappingCheck.GetElementFromElementMapping.getElementFromElementMapping;

/**
 * 该程序是对原始数据进行数据清洗
 */
public class OriginalDataDeal {
    public static void  OriginalDataDeal(String FileDirectory,String ConfigPath,String DatacleanReportPath,String ResultPathDirectory,String ExceptionpathDirectory) throws ParseException, InvalidFormatException, IOException {
//        String FileDirectory = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906112006\\test";//需要进行处理的文件路径
//        String ConfigPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\数据清洗";
//        String DatacleanReportPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906112006\\ExceptionReport";
//        String ResultPathDirectory = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906112006\\Xml&MappingInput";
//        String ExceptionpathDirectory = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906112006\\CleansingException";
//        String FileDirectory = args[0];//表格文件夹路径
//        String ConfigPath = args[1];//配置文件路径
//        String DatacleanReportPath =args[2];//生成日志路径
//        String ResultPathDirectory = args[3];//生成的表单路径
//        String ExceptionpathDirectory = args[4];//生成异常的表单路径
        System.out.println("=============================================================================开始数据清洗!=======================================================================================");
        DatacleanReportPath = DatacleanReportPath + "\\DataCleanReport.xlsx";
        File file = new File(ConfigPath);
        File[] files = file.listFiles();
        String IntegrityCheck = "";
        String ReplacementConfig = "";
        String ElementMapping = "";
        String DuplicateConfig = "";
        for (File file1 : files) {
            String AbsoConfigPath = file1.getAbsolutePath();
            if (AbsoConfigPath.contains("IntegrityCheck")) {
                IntegrityCheck = AbsoConfigPath;
            } else if (AbsoConfigPath.contains("ReplacementConfig")) {
                ReplacementConfig = AbsoConfigPath;
            } else if (AbsoConfigPath.contains("ElementMapping")) {
                ElementMapping = AbsoConfigPath;
            } else if (AbsoConfigPath.contains("DuplicateConfig")) {
                DuplicateConfig = AbsoConfigPath;
            }
        }

//        日志数据集合------------------------------------------------------------------->
        List<IntegrityCheckDO> IntegrityCheckList = new ArrayList<IntegrityCheckDO>();//信息校验完整性的日志集合
        List<PastespecialCheckDO> PastespecialList = new ArrayList<>();//信息校验完整性的日志集合
        List<CoverCheckDO> CoverCheckList = new ArrayList<>();//封面页校验完整性的日志集合
        List<LabelNameCheckDO> LabelNameCheckList = new ArrayList<>();//标签校验完整性的日志集合
        List<CallitemCheckDO> CallitemCheckList = new ArrayList<>();//资产负债表定位信息校验的日志集合
        List<DuplicateJudgeDO> DuplicateJudgeList = new ArrayList<>();//重复项位置判断的日志集合
        List<MappingCheckDO> MappingCheckList = new ArrayList<>();//校验标签是否全部在库的日志集合
//        日志数据集合------------------------------------------------------------------->
//        checkRootDirectory(RootDirectory);
        File FileDirect = new File(FileDirectory);
        File[] files2 = FileDirect.listFiles();
        for (File file2 : files2) {
            String ExcelPath = file2.getAbsolutePath();
            if (ExcelPath.endsWith(".xlsx") && !(ExcelPath.contains("VBA-0118.xlsx"))) {

                System.out.println(ExcelPath);
                String ExceptionPath =  ExceptionpathDirectory + "\\" + ExcelPath.substring(ExcelPath.lastIndexOf("\\") + 1);
                String ResultPath = ResultPathDirectory + "\\" + ExcelPath.substring(ExcelPath.lastIndexOf("\\") + 1);
//                System.out.println(ResultPath);
                DataClean(ExcelPath, ResultPath,ExceptionPath, IntegrityCheckList, IntegrityCheck,PastespecialList, CoverCheckList, ReplacementConfig, LabelNameCheckList, CallitemCheckList, DuplicateConfig, DuplicateJudgeList, ElementMapping, MappingCheckList);
            }
        }
//         输出数据清洗的日志文件------------------------------------------------------------------------->
        exportDataCleanReport(DatacleanReportPath, IntegrityCheckList,PastespecialList, CoverCheckList, LabelNameCheckList, CallitemCheckList, DuplicateJudgeList, MappingCheckList);
        System.out.println("=============================================================================数据清洗结束!=========================================================================================================");
    }

    /**
     * @param excelPath          待处理的excel文件
     * @param ResultPath         输出结果excel文件
     * @param IntegrityCheckList 完整性校验异常report的数据集合
     * @param IntegrityCheck     完整性校验表的配置文件
     * @param CoverCheckList     校验异常report的数据集合
     * @param ReplacementConfig  标签替换表的配置文件
     * @param LabelNameCheckList 标签校验异常report的数据集合
     * @param ElementMapping     元素映射表的配置文件
     * @param DuplicateConfig    去重表的配置文件
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static void DataClean(String excelPath, String ResultPath,String ExceptionPath, List<IntegrityCheckDO> IntegrityCheckList, String IntegrityCheck,List<PastespecialCheckDO> PastespecialList, List<CoverCheckDO> CoverCheckList, String ReplacementConfig, List<LabelNameCheckDO> LabelNameCheckList, List<CallitemCheckDO> CallitemCheckList, String DuplicateConfig, List<DuplicateJudgeDO> DuplicateList, String ElementMapping, List<MappingCheckDO> MappingCheckList) throws IOException, InvalidFormatException, ParseException {
        File excelFile = new File(excelPath);
        int BeforeIntegrityCheckListnum = IntegrityCheckList.size();
        int BeforePastespecialListnum = PastespecialList.size();
        int BeforeCoverCheckListnum = CoverCheckList.size();
        int BeforeLabelNameCheckListnum = LabelNameCheckList.size();
        int BeforeCallitemCheckListnum = CallitemCheckList.size();
        int BeforeDuplicateListnum = DuplicateList.size();
        int BeforeMappingCheckListnum = MappingCheckList.size();


        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile));//获取待处理的excel文件流
//////        //只复制值程序---------测试通过
//        Pastespecial(workbook,excelPath,PastespecialList);
//////        //运行封面页检查程序--------测试通过
        CoverCheck(workbook, excelPath, CoverCheckList);
//////        //标签检查程序--------测试通过
//        LabelNameCheck(workbook, excelPath, ReplacementConfig, LabelNameCheckList);
////        //资产负债表定位信息检查程序--------测试通过
        CallItemCheck(workbook, excelPath, CallitemCheckList);
////        //重复项位置判断--------测试通过
        DuplicateJudge(workbook, excelPath, DuplicateConfig, DuplicateList);
//        //检查标签是否全部在库--------测试通过
//        Mappingcheck(workbook, excelPath, ElementMapping, MappingCheckList);


    if (BeforeIntegrityCheckListnum==IntegrityCheckList.size() && BeforePastespecialListnum==PastespecialList.size() && BeforeCoverCheckListnum==CoverCheckList.size() && BeforeLabelNameCheckListnum==LabelNameCheckList.size() && BeforeCallitemCheckListnum==CallitemCheckList.size() && BeforeDuplicateListnum==DuplicateList.size() && BeforeMappingCheckListnum==MappingCheckList.size())  {
        //输出无异常后的表
        exportExcel(workbook, ResultPath);
    }else {
        //输出异常的表
        exportExcel(workbook, ExceptionPath);
    }

    }

    /**
     * 数据清洗日志生成
     *
     * @param integrityCheckList
     * @param coverCheckList
     * @param labelNameCheckList
     * @param callitemCheckList
     * @param duplicateJudgeList
     * @param mappingCheckList
     */
    private static void exportDataCleanReport(String DatacleanReportPath, List<IntegrityCheckDO> integrityCheckList, List<PastespecialCheckDO> PastespecialList,List<CoverCheckDO> coverCheckList, List<LabelNameCheckDO> labelNameCheckList, List<CallitemCheckDO> callitemCheckList, List<DuplicateJudgeDO> duplicateJudgeList, List<MappingCheckDO> mappingCheckList) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
//        完整性校验的sheet页-------------------------------------------------------------------------------------->
        //定义第一个sheet页
        XSSFSheet IntegrityCheckSheet = xssfWorkbook.createSheet("integrity check");
        //第一个IntegrityCheckSheet页数据
        Row row0 = IntegrityCheckSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "异常类型", "Sheet名称", "单元格名称", "异常信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            IntegrityCheckSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (IntegrityCheckDO integrityCheckDO : integrityCheckList) {
            Row row = IntegrityCheckSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(integrityCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(integrityCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(integrityCheckDO.getExceptionType());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(integrityCheckDO.getSheetName());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(integrityCheckDO.getCellName());
            row.createCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellValue(integrityCheckDO.getAbnormalInformation());
            i++;
        }
//       只复制值程序的sheet页-------------------------------------------------------------------------------------->
        XSSFSheet PastespecialSheet = xssfWorkbook.createSheet("pastespecial");
        Row row9 = PastespecialSheet.createRow(0);
        String[] Pastespecialheaders = new String[]{"组织机构编号", "公司名", "问题所在表", "异常信息"};
        for (int j = 0; j < Pastespecialheaders.length; j++) {
            XSSFCell cell = (XSSFCell) row9.createCell(j);
            PastespecialSheet.setColumnWidth(j + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(Pastespecialheaders[j]);
            cell.setCellValue(text);
        }
        int r = 1;
        for (PastespecialCheckDO pastespecialCheckDO : PastespecialList) {
            Row row = PastespecialSheet.createRow(r);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(pastespecialCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(pastespecialCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(pastespecialCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(pastespecialCheckDO.getExceptionType());
            r++;
        }
//        封面页校验Sheet页------------------------------------------------------------------->
        XSSFSheet CoverCheckSheet = xssfWorkbook.createSheet("Cover Check");
        //第一个CoverCheckSheet页数据
        Row row1 = CoverCheckSheet.createRow(0);
        String[] CoverCheckheaders = new String[]{"组织机构编号", "公司名", "未填列项目所在单元格", "未填列项目"};
        for (int j = 0; j < CoverCheckheaders.length; j++) {
            XSSFCell cell = (XSSFCell) row1.createCell(j);
            CoverCheckSheet.setColumnWidth(j + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(CoverCheckheaders[j]);
            cell.setCellValue(text);
        }
        int j = 1;
        for (CoverCheckDO coverCheckDO : coverCheckList) {
            Row row = CoverCheckSheet.createRow(j);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(coverCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(coverCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(coverCheckDO.getUnfilledCell());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(coverCheckDO.getUnfilledproject());
            j++;
        }
//        标签校验的sheet页---------------------------------------------------------->
        XSSFSheet LabelNameCheckfSheet = xssfWorkbook.createSheet("LabelName Check");
        //第一个sheet页数据（生成台账表）
        Row row2 = LabelNameCheckfSheet.createRow(0);
        String[] LabelNameCheckheaders = new String[]{"组织机构编号", "公司名", "Sheet名", "英文引号所在单元格", "需排查项目信息"};
        for (int l = 0; l < LabelNameCheckheaders.length; l++) {
            XSSFCell cell = (XSSFCell) row2.createCell(l);
            LabelNameCheckfSheet.setColumnWidth(l + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[l]);
            cell.setCellValue(text);
        }
        int l = 1;
        for (LabelNameCheckDO labelNameCheckDO : labelNameCheckList) {
            Row row = LabelNameCheckfSheet.createRow(l);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(labelNameCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(labelNameCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(labelNameCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(labelNameCheckDO.getEnglishQuotationCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(labelNameCheckDO.getReadyCheckInformation());
            l++;
        }
//        资产负债表定位信息校验-------------------------------------------------->
        //定义第一个sheet页
        XSSFSheet CellItemCheckSheet = xssfWorkbook.createSheet("CellItem Check");
        //第一个sheet页数据（生成台账表）
        Row row3 = CellItemCheckSheet.createRow(0);
        String[] CellItemCheckheaders = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
        for (int m = 0; m < CellItemCheckheaders.length; m++) {
            XSSFCell cell = (XSSFCell) row3.createCell(m);
            CellItemCheckSheet.setColumnWidth(m + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(CellItemCheckheaders[m]);
            cell.setCellValue(text);
        }
        int m = 1;
        for (CallitemCheckDO callitemCheckDO : callitemCheckList) {
            Row row = CellItemCheckSheet.createRow(m);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(callitemCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(callitemCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(callitemCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(callitemCheckDO.getExceptionCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(callitemCheckDO.getUnCheckedProjectInformation());
            m++;
        }
//        重复项位置判断-------------------------------------------------------------------------------->
        XSSFSheet DuplicateJudgeSheet = xssfWorkbook.createSheet("Duplicate Judge");
        //第一个sheet页数据（生成台账表）
        Row row4 = DuplicateJudgeSheet.createRow(0);
        String[] DuplicateJudgeheaders = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
        for (int n = 0; n < DuplicateJudgeheaders.length; n++) {
            XSSFCell cell = (XSSFCell) row4.createCell(n);
            DuplicateJudgeSheet.setColumnWidth(n + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(DuplicateJudgeheaders[n]);
            cell.setCellValue(text);
        }
        int n = 1;
        for (DuplicateJudgeDO duplicateJudgeDO : duplicateJudgeList) {
            Row row = DuplicateJudgeSheet.createRow(n);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(duplicateJudgeDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(duplicateJudgeDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(duplicateJudgeDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(duplicateJudgeDO.getExceptionCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(duplicateJudgeDO.getUnCheckedProjectInformation());
            n++;
        }
////        校验标签是否全部在库--------------------------------------------------------------------------------------------->
//        XSSFSheet mappingcheckSheet = xssfWorkbook.createSheet("ReadyMappingCheck Check");
//        //第一个sheet页数据（生成台账表）
//        Row row5 = mappingcheckSheet.createRow(0);
//        String[] mappingcheckheaders = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
//        for (int o = 0; o < mappingcheckheaders.length; o++) {
//            XSSFCell cell = (XSSFCell) row5.createCell(o);
//            mappingcheckSheet.setColumnWidth(o + 2, 5000);
//            XSSFRichTextString text = new XSSFRichTextString(mappingcheckheaders[o]);
//            cell.setCellValue(text);
//        }
//        int o = 1;
//        for (MappingCheckDO mappingCheckDO : mappingCheckList) {
//            Row row = mappingcheckSheet.createRow(o);
//            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(0).setCellValue(mappingCheckDO.getOrganizationNumber());
//            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(1).setCellValue(mappingCheckDO.getCompanyName());
//            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(2).setCellValue(mappingCheckDO.getSheetName());
//            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(3).setCellValue(mappingCheckDO.getExceptionCell());
//            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(4).setCellValue(mappingCheckDO.getUnCheckedProjectInformation());
//            o++;
//        }
        FileOutputStream fileOutputStream = new FileOutputStream(DatacleanReportPath);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 生成校验标签是否全部在库的日志文件
     *
     * @param mappingCheckReport
     * @param mappingCheckList
     */
    private static void exportMappingCheckReport(String mappingCheckReport, List<MappingCheckDO> mappingCheckList) throws IOException {
//创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Cover Check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (MappingCheckDO mappingCheckDO : mappingCheckList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(mappingCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(mappingCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(mappingCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(mappingCheckDO.getExceptionCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(mappingCheckDO.getUnCheckedProjectInformation());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(mappingCheckReport);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 生成重复项位置判断的日志文件
     *
     * @param duplicateJudgeReport
     * @param duplicateJudgeList
     */
    private static void exportDuplicateJudgeReport(String duplicateJudgeReport, List<DuplicateJudgeDO> duplicateJudgeList) throws IOException {
//创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Cover Check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (DuplicateJudgeDO duplicateJudgeDO : duplicateJudgeList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(duplicateJudgeDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(duplicateJudgeDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(duplicateJudgeDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(duplicateJudgeDO.getExceptionCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(duplicateJudgeDO.getUnCheckedProjectInformation());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(duplicateJudgeReport);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 生成资产负债表定位信息校验的日志文件
     *
     * @param callItemCheckReport
     * @param callitemCheckList
     */
    private static void exportCallItemCheckReport(String callItemCheckReport, List<CallitemCheckDO> callitemCheckList) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Cover Check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "Sheet名", "异常信息所在单元格", "需排查项目信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (CallitemCheckDO callitemCheckDO : callitemCheckList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(callitemCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(callitemCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(callitemCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(callitemCheckDO.getExceptionCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(callitemCheckDO.getUnCheckedProjectInformation());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(callItemCheckReport);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 生成标签校验日志
     *
     * @param labelNameCheckReport
     * @param labelNameCheckList
     */
    private static void exportLabelCheckReport(String labelNameCheckReport, List<LabelNameCheckDO> labelNameCheckList) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("LabelName Check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "Sheet名", "英文引号所在单元格", "需排查项目信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (LabelNameCheckDO labelNameCheckDO : labelNameCheckList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(labelNameCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(labelNameCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(labelNameCheckDO.getSheetName());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(labelNameCheckDO.getEnglishQuotationCell());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(labelNameCheckDO.getReadyCheckInformation());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(labelNameCheckReport);
        xssfWorkbook.write(fileOutputStream);
    }


    /**
     * 生成完整性校验的日志
     *
     * @param integrityCheckReport
     * @param integrityCheckList
     */
    private static void exportIntegrityCheckReport(String integrityCheckReport, List<IntegrityCheckDO> integrityCheckList) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("integrity check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "异常类型", "Sheet名称", "单元格名称", "异常信息"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (IntegrityCheckDO integrityCheckDO : integrityCheckList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(integrityCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(integrityCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(integrityCheckDO.getExceptionType());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(integrityCheckDO.getSheetName());
            row.createCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellValue(integrityCheckDO.getCellName());
            row.createCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellValue(integrityCheckDO.getAbnormalInformation());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(integrityCheckReport);
        xssfWorkbook.write(fileOutputStream);

    }

    /**
     * 生成封面校验的日志
     *
     * @param coverCheckReport
     * @param coverCheckList
     */
    private static void exportCoverCheckReport(String coverCheckReport, List<CoverCheckDO> coverCheckList) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Cover Check");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编号", "公司名", "未填列项目所在单元格", "未填列项目"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (CoverCheckDO coverCheckDO : coverCheckList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(coverCheckDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(coverCheckDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(coverCheckDO.getUnfilledCell());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(coverCheckDO.getUnfilledproject());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(coverCheckReport);
        xssfWorkbook.write(fileOutputStream);
    }

    /**
     * 判断是否存在根路径，如果不存在，则需要创建该目录，若存在，则继续
     *
     * @param rootDirectory
     */
    private static void checkRootDirectory(String rootDirectory) {
        File RootDirect = new File(rootDirectory);
        if (!RootDirect.exists()) {
            RootDirect.mkdir();
        }
    }


    /**
     * 校验标签是否全部在库
     *
     * @param workbook
     * @param excelPath
     * @param elementMapping
     * @param mappingCheckList
     */
    private static void Mappingcheck(Workbook workbook, String excelPath, String elementMapping, List<MappingCheckDO> mappingCheckList) throws IOException, InvalidFormatException {
        //sheet表格完整性校验---------------------------------------------------------------->
        String ExcelName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);
        String[] vl = new String[]{"合并资产负债表", "合并利润表", "合并现金流量表"};
        List<MappingConfigDO> list = getElementFromElementMapping(elementMapping);
        List<MappingConfigDO> CombineAssetList = new ArrayList<>();
        List<MappingConfigDO> CombineInterestList = new ArrayList<>();
        List<MappingConfigDO> CombineCurrentList = new ArrayList<>();

        for (MappingConfigDO mappingConfigDO : list) {
            if ("合并资产负债表".equals(mappingConfigDO.getTable())) {
                CombineAssetList.add(mappingConfigDO);

            }
            if ("合并利润表".equals(mappingConfigDO.getTable())) {
                CombineInterestList.add(mappingConfigDO);
            }
            if ("合并现金流量表".equals(mappingConfigDO.getTable())) {
                CombineCurrentList.add(mappingConfigDO);
            }
        }
        Set<String> CombineAssetSet = new HashSet<>();
        for (MappingConfigDO mappingConfigDO : CombineAssetList) {
            CombineAssetSet.add(mappingConfigDO.getValue());
        }
        Set<String> CombineInterestSet = new HashSet<>();
        for (MappingConfigDO mappingConfigDO : CombineInterestList) {
            CombineInterestSet.add(mappingConfigDO.getValue());
        }
        Set<String> CombineCurrentSet = new HashSet<>();
        for (MappingConfigDO mappingConfigDO : CombineCurrentList) {
            CombineCurrentSet.add(mappingConfigDO.getValue());
        }

//        System.out.println("合并资金表的大小："+CombineInterestSet.size());
//        System.out.println("合并利润表的大小："+CombineInterestSet.size());
//        System.out.println("合并现金流量表的大小："+CombineCurrentSet.size());


        for (int i = 0; i < vl.length; i++) {
            Sheet sheet = workbook.getSheet(vl[i]);
            try {
                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);

                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String celldata = cell.getStringCellValue();
                        switch (vl[i]) {
                            case "合并资产负债表":

                                if (CombineAssetSet.add(celldata)) {
                                    MappingCheckDO mappingCheckDO = new MappingCheckDO();
                                    mappingCheckDO.setCompanyName(CompanyName);
                                    mappingCheckDO.setOrganizationNumber(CompanyNum);
                                    mappingCheckDO.setSheetName(vl[i]);
                                    mappingCheckDO.setExceptionCell("A" + (j + 1));
                                    mappingCheckDO.setUnCheckedProjectInformation(celldata);
                                    mappingCheckList.add(mappingCheckDO);
                                }
                                break;
                            case "合并利润表":

                                if (CombineInterestSet.add(celldata)) {
                                    MappingCheckDO mappingCheckDO = new MappingCheckDO();
                                    mappingCheckDO.setCompanyName(CompanyName);
                                    mappingCheckDO.setOrganizationNumber(CompanyNum);
                                    mappingCheckDO.setSheetName(vl[i]);
                                    mappingCheckDO.setExceptionCell("A" + (j + 1));
                                    mappingCheckDO.setUnCheckedProjectInformation(celldata);
                                    mappingCheckList.add(mappingCheckDO);
                                }
                                break;
                            case "合并现金流量表":
                                if (CombineCurrentSet.add(celldata)) {
                                    MappingCheckDO mappingCheckDO = new MappingCheckDO();
                                    mappingCheckDO.setCompanyName(CompanyName);
                                    mappingCheckDO.setOrganizationNumber(CompanyNum);
                                    mappingCheckDO.setSheetName(vl[i]);
                                    mappingCheckDO.setExceptionCell("A" + (j + 1));
                                    mappingCheckDO.setUnCheckedProjectInformation(celldata);
                                    mappingCheckList.add(mappingCheckDO);
                                }
                                break;
                            default:
                                break;
                        }


                    }


                }
            } catch (NullPointerException e) {

            }
        }

    }

    /**
     * 信息完整性校验
     *
     * @param excelFile      输入的excel表路径
     * @param IntegrityCheck 完整性配置表
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static List<IntegrityCheckDO> checkcomplete(Workbook workbook, String excelFile, String ResultPath, String IntegrityCheck, List<IntegrityCheckDO> IntegrityCheckList) throws IOException, InvalidFormatException, ParseException {
        String[] actualsheetarr = new String[15];
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            actualsheetarr[i] = workbook.getSheetName(i);
        }
        List<String> templatesheetarrlist = GetSheetTransInformationFromIntergrityCheck(IntegrityCheck);
        List<String> actualsheetarrlist = new ArrayList<String>(Arrays.asList(actualsheetarr));

        //对单元格进行处理
        operationCell(workbook, templatesheetarrlist, actualsheetarrlist);
        //校验异常，输出日志
        checksheet(excelFile, templatesheetarrlist, actualsheetarrlist, workbook, IntegrityCheckList);
        return IntegrityCheckList;
    }

    /**
     * 处理单元格
     *
     * @param workbook
     */
    private static void operationCell(Workbook workbook, List<String> templatesheetarrlist, List<String> actualsheetarrlist) throws ParseException {
        actualsheetarrlist.retainAll(templatesheetarrlist);
        for (String str : actualsheetarrlist) {
            Sheet sheet = workbook.getSheet(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            if (!"封面信息".equals(str)) {
                try {
                    for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                        String A = sheet.getRow(i).getCell(0).getStringCellValue();
//                    处理A里面的空格
                        String reg = "\\s+";
                        Matcher matcher = Pattern.compile(reg).matcher(A);
                        A = matcher.replaceAll(" ");
                        A = A.trim().replace(" ", "");
                        sheet.getRow(i).getCell(0).setCellValue(A);
                    }
                } catch (Exception e) {
                }
                String B = GetCellValue(sheet.getRow(0).getCell(1));
                String C = GetCellValue(sheet.getRow(0).getCell(2));
//             处理时间格式单元格
                if (!(B.contains("年度") || C.contains("年度"))) {

                    if (B.contains("/")) {
                        Date date_B = simpleDateFormat.parse(B);
                        B = simpleDateFormat1.format(date_B);
                    }
                    if (C.contains("/")) {
                        Date date_C = simpleDateFormat.parse(C);
                        C = simpleDateFormat1.format(date_C);
                    }
                    sheet.getRow(0).getCell(1).setCellValue(B);
                    sheet.getRow(0).getCell(2).setCellValue(C);
                }
            } else {
                String D = GetCellValue(sheet.getRow(4).getCell(1));
                String E = GetCellValue(sheet.getRow(4).getCell(3));
                if (D.contains("/")) {
                    Date date_D = simpleDateFormat.parse(D);
                    D = simpleDateFormat1.format(date_D);
                }
                if (E.contains("/")) {
                    Date date_E = simpleDateFormat.parse(E);
                    E = simpleDateFormat1.format(date_E);
                }
                sheet.getRow(4).getCell(1).setCellValue(D);
                sheet.getRow(4).getCell(3).setCellValue(E);

            }
        }


    }

    private static void checksheet(String excelFile, List<String> templatesheetarrlist, List<String> actualsheetarrlist, Workbook workbook, List<IntegrityCheckDO> IntegrityCheckList) throws ParseException {
        //sheet表格完整性校验---------------------------------------------------------------->
        String ExcelName = excelFile.substring(excelFile.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);

        actualsheetarrlist.retainAll(templatesheetarrlist);
        if (actualsheetarrlist.size() == templatesheetarrlist.size()) {
//            System.out.println("封面信息完整!");
            //格式校验---------------------------------------------------------------->
            for (String str : templatesheetarrlist) {
                IntegrityCheckDO integrityCheckDO = new IntegrityCheckDO();
//                try {
                if (!"封面信息".equals(str)) {
//                    System.out.println("开始测试:" + str);
                    Sheet sheet = workbook.getSheet(str);
                    String A = sheet.getRow(0).getCell(0).getStringCellValue();
                    String B = GetCellValue(sheet.getRow(0).getCell(1));
                    String C = GetCellValue(sheet.getRow(0).getCell(2));

                    //B和C为年度的话，只比较年的大小
                    if (B.contains("年度") || C.contains("年度")) {
                        B = B.substring(0, 4);
                        C = C.substring(0, 4);
                        if (!"项目".equals(A)) {
                            integrityCheckDO.setOrganizationNumber(CompanyNum);
                            integrityCheckDO.setCompanyName(CompanyName);
                            integrityCheckDO.setExceptionType("格式不正确");
                            integrityCheckDO.setSheetName(str);
                            integrityCheckDO.setCellName("A1");
                            integrityCheckDO.setAbnormalInformation(str + ": A1 Value 不为项目");
                        }
                        if (!(Integer.parseInt(B) > Integer.parseInt(C))) {
                            integrityCheckDO.setOrganizationNumber(CompanyNum);
                            integrityCheckDO.setCompanyName(CompanyName);
                            integrityCheckDO.setExceptionType("格式不正确");
                            integrityCheckDO.setSheetName(str);
                            if (integrityCheckDO.getCellName() != null) {
                                integrityCheckDO.setCellName(integrityCheckDO.getCellName() + "B1,C1");
                            } else {
                                integrityCheckDO.setCellName("B1,C1");
                            }
                            if (integrityCheckDO.getAbnormalInformation() != null) {
                                integrityCheckDO.setAbnormalInformation(integrityCheckDO.getAbnormalInformation() + ", B1,C1的时间顺序不正确");
                            } else {
                                integrityCheckDO.setAbnormalInformation(str + ": B1,C1的时间顺序不正确");

                            }
                        }
                        if (integrityCheckDO.getCompanyName() != null || integrityCheckDO.getCellName() != null) {
                            IntegrityCheckList.add(integrityCheckDO);

                        }

                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        if (!"项目".equals(A)) {
                            integrityCheckDO.setOrganizationNumber(CompanyNum);
                            integrityCheckDO.setCompanyName(CompanyName);
                            integrityCheckDO.setExceptionType("格式不正确");
                            integrityCheckDO.setSheetName(str);
                            integrityCheckDO.setCellName("A1");
                            integrityCheckDO.setAbnormalInformation(str + "A1 Value 不为项目");
                        }
                        try {
                            if (!(simpleDateFormat.parse(B).compareTo(simpleDateFormat.parse(C)) > 0)) {
                                integrityCheckDO.setOrganizationNumber(CompanyNum);
                                integrityCheckDO.setCompanyName(CompanyName);
                                integrityCheckDO.setExceptionType("格式不正确");
                                integrityCheckDO.setSheetName(str);
                                if (integrityCheckDO.getCellName() != null) {
                                    integrityCheckDO.setCellName(integrityCheckDO.getCellName() + "B1,C1");
                                } else {
                                    integrityCheckDO.setCellName("B1,C1");
                                }
                                if (integrityCheckDO.getAbnormalInformation() != null) {
                                    integrityCheckDO.setAbnormalInformation(integrityCheckDO.getAbnormalInformation() + " , B1,C1的时间顺序不正确");
                                } else {
                                    integrityCheckDO.setAbnormalInformation(str + ": B1,C1的时间顺序不正确");

                                }
                            }
                        } catch (Exception e) {
                            integrityCheckDO.setOrganizationNumber(CompanyNum);
                            integrityCheckDO.setCompanyName(CompanyName);
                            integrityCheckDO.setExceptionType("格式不正确");
                            integrityCheckDO.setSheetName(str);
                            if (integrityCheckDO.getCellName() != null) {
                                integrityCheckDO.setCellName(integrityCheckDO.getCellName() + "B1,C1");
                            } else {
                                integrityCheckDO.setCellName("B1,C1");
                            }
                            if (integrityCheckDO.getAbnormalInformation() != null) {
                                integrityCheckDO.setAbnormalInformation(integrityCheckDO.getAbnormalInformation() + " , B1,C1的时间顺序不正确");
                            } else {
                                integrityCheckDO.setAbnormalInformation(str + ": B1,C1的时间顺序不正确");

                            }
                        }

                        if (integrityCheckDO.getCompanyName() != null || integrityCheckDO.getCellName() != null) {
                            IntegrityCheckList.add(integrityCheckDO);

                        }

                    }
                }
//                }catch (Exception e){
//
//                }
            }
            //格式校验---------------------------------------------------------------->
        } else {
            String LackList = getLackList(actualsheetarrlist, templatesheetarrlist);
            IntegrityCheckDO integrityCheckDO = new IntegrityCheckDO();
            integrityCheckDO.setOrganizationNumber(CompanyNum);
            integrityCheckDO.setCompanyName(CompanyName);
            integrityCheckDO.setExceptionType("表格缺失");
            integrityCheckDO.setSheetName(LackList);
            integrityCheckDO.setCellName("NA");
            integrityCheckDO.setAbnormalInformation("NA");
//            System.out.println("封面信息不完整！");
            IntegrityCheckList.add(integrityCheckDO);
        }
        //sheet表格完整性校验---------------------------------------------------------------->
    }

    /**
     * 根据类型获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String GetCellValue(Cell cell) {
        String value = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.
                            getNumericCellValue())) + "";
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue()) + "";
                }

                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            default:
                value = cell.getStringCellValue();
                break;
        }
        return value;
    }

    /**
     * 获取缺失的表集合
     *
     * @param actualsheetarrlist
     * @param templatesheetarrlist
     * @return
     */
    private static String getLackList(List<String> actualsheetarrlist, List<String> templatesheetarrlist) {
        List<String> LackList = new ArrayList<String>();
        for (String str : templatesheetarrlist) {
            if (!actualsheetarrlist.contains(str)) {
                LackList.add(str);
            }
        }
        String LackSheet = "";
        StringBuilder stringBuilder = new StringBuilder(LackSheet);
        for (String string : LackList) {
            if (LackList.size()>1){
                stringBuilder.append(string+"||");
            }else {
                stringBuilder.append(string);
            }
        }
        LackSheet = stringBuilder.toString();
        return LackSheet;
    }


    /**
     * 获取从文件路径中获取文件名
     *
     * @param excelFile
     * @return
     */
    private static String getCompanyName(String excelFile) {
        String[] vl = excelFile.split("-");
        String CompanyName = vl[2];
        return CompanyName;
    }

    /**
     * 输出修改后的表
     *
     * @param workbook
     */
    private static void exportExcel(Workbook workbook, String excelPath) throws IOException {
        FileOutputStream os = new FileOutputStream(excelPath);
        workbook.write(os);
        os.close();
    }

    /**
     * 更新file表格
     *
     * @param
     * @param
     * @return
     */
    private static void UpdateFile(String excelPath, List<FilePropeties> list) throws IOException, InvalidFormatException {
        String FileName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
//        System.out.println(FileName);
//        System.out.println(excelPath);
        FilePropeties filePropeties = new FilePropeties();
        filePropeties.setFileName(FileName);
        filePropeties.setFilePath(excelPath);
        list.add(filePropeties);

    }

    /**
     * 导出日志表
     */
    private static void exportLogExcel(String logPath, List<FilePropeties> list) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("日志表");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"文件路径", "文件名"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (FilePropeties filePropeties : list) {
            Row row = xssfSheet.createRow(i);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            cell1.setCellType(Cell.CELL_TYPE_STRING);
            cell2.setCellType(Cell.CELL_TYPE_STRING);
            cell1.setCellValue(filePropeties.getFilePath());
            cell2.setCellValue(filePropeties.getFileName());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(logPath);//创建VBA输出流
        xssfWorkbook.write(fileOutputStream);
    }
//
//    /**
//     * 只复制值程序
//     *
//     * @param workbook
//     */
//    private static void Pastespecial(Workbook workbook,String excelPath,List<PastespecialCheckDO> PastespecialList) {
//        String ExcelName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
//        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
//        String CompanyName = getCompanyName(ExcelName);
//        String[] vl = new String[]{"合并资产负债表", "合并利润表", "合并现金流量表", "有息负债总表"};
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//
//            XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
//            style2.setLocked(false);
//            if (sheet != null) {
//
//                //根据小数点补位
//                Row row = sheet.getRow(3);
//                Cell cell = row.getCell(5);
//                if (cell != null) {
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    String num = cell.getStringCellValue();
//                    String reg = "\\d";
//                    Pattern pattern = Pattern.compile(reg);
//                    Matcher matcher = pattern.matcher(num);
//                    while (matcher.find()) {
//                        num = matcher.group();
//                    }
//                    switch (num) {
//                        case "个位":
//                            num = "";
//                            break;
//                        case "1":
//                            num = "0";
//                            break;
//                        case "2":
//                            num = "00";
//                            break;
//                        case "3":
//                            num = "000";
//                            break;
//                        case "4":
//                            num = "0000";
//                            break;
//                        default:
//                            PastespecialCheckDO pastespecialCheckDO =new PastespecialCheckDO();
//                            pastespecialCheckDO.setCompanyName(CompanyName);
//                            pastespecialCheckDO.setOrganizationNumber(CompanyNum);
//                            pastespecialCheckDO.setSheetName(vl[i]);
//                            pastespecialCheckDO.setExceptionType("小数精确位异常");
//                            PastespecialList.add(pastespecialCheckDO);
//                    }
//                    DecimalFormat decimalFormat = new DecimalFormat("#." + num);
//                    try {
//                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
////                        //处理第一列
//                            Double data0 = getCellValue(sheet.getRow(j).getCell(1), j);
//                            if (data0 != null) {
//                                sheet.getRow(j).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//                                if ("".equals(num)) {
//                                    sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)).replace(".", ""));
//
//                                } else if ("00".equals(num)){
//                                    if (".00".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(1).setCellValue("0.00");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("0".equals(num)){
//                                    if (".0".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(1).setCellValue("0.0");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("000".equals(num)){
//                                    if (".000".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(1).setCellValue("0.000");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("0000".equals(num)){
//                                    if (".0000".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(1).setCellValue("0.0000");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }
//
//                            }
//                            //处理第二列
//                            Double data = getCellValue(sheet.getRow(j).getCell(2), j);
//                            if (data != null) {
//                                sheet.getRow(j).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//                                if ("".equals(num)) {
//                                    sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)).replace(".", ""));
//
//                                } else if ("00".equals(num)){
//                                    if (".00".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(2).setCellValue("0.00");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("0".equals(num)){
//                                    if (".0".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(2).setCellValue("0.0");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("000".equals(num)){
//                                    if (".000".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(2).setCellValue("0.000");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }else if ("0000".equals(num)){
//                                    if (".0000".equals(decimalFormat.format(new BigDecimal(data0)))) {
//                                        sheet.getRow(j).getCell(2).setCellValue("0.0000");
//
//                                    } else {
//                                        sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data0)));
//
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
////
//                    }
//                    if ("有息负债总表".equals(vl[i])) {
//                        int removeNum = getRemoveRowNum(sheet);
//                        if (removeNum != 0) {
//                            //删除24行之后的数据-------------------------------------------------------->
//                            for (int j = removeNum; j <= sheet.getLastRowNum(); j++) {
//                                try {
//                                    sheet.removeRow(sheet.getRow(j));
//                                } catch (Exception e) {
//                                    continue;
//                                }
//                            }
//                        }
//                    }
//
//
//                }
//
//            }
//
//        }
//
//
//    }

    /**
     * 获取待删除的填写说明的行数
     *
     * @return
     */
    private static int getRemoveRowNum(Sheet sheet) {
        int num = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                if (sheet.getRow(i).getCell(0) != null) {
                    String value = sheet.getRow(i).getCell(0).getStringCellValue();
                    if (value.contains("填写说明")) {
                        num = i;
                    }
                }
            }
        }
        return num;
    }

    /**
     * 根据数据类型获取单元格的值
     *
     * @param cell
     */
    private static Double getCellValue(Cell cell, int j) {
        Double value = null;
        try {
            if (cell != null) {
                //首先先判断单元格的数据类型
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC: // 数字
                        ;
//                        System.out.println("第" + j + "行的第3个单元格数据类型是数字类型:" + new DecimalFormat("0").format(cell.getNumericCellValue()));
                        value = cell.getNumericCellValue();
                        break;
                    case Cell.CELL_TYPE_STRING: // 字符串
//                        System.out.println("第" + j + "行的第3个单元格数据类型是字符串类型:" + cell.getStringCellValue());
//                        value = Double.parseDouble(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA: // 公式
//                        System.out.println("第" + j + "行的第3个单元格数据类型是公式类型:" + cell.getNumericCellValue());
                        try {
                            value = cell.getNumericCellValue();
                        } catch (Exception e) {

                        }
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空值

                        break;
                    case Cell.CELL_TYPE_ERROR: // 故障
                        break;
                    default:
                        value = 0.00;
                        break;
                }
            }
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 运行封面页检查程序
     *
     * @param workbook
     * @param
     */
    private static void CoverCheck(Workbook workbook, String excelPath, List<CoverCheckDO> CoverCheckList) {
        //sheet表格完整性校验---------------------------------------------------------------->
        String ExcelName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);
        Sheet sheet = workbook.getSheet("封面信息");
        //将第一行到第8行的第一列的*全部替换为空
        for (int j = 1; j < 9; j++) {
            Row row = sheet.getRow(j);
            row.getCell(0).setCellValue(row.getCell(0).getStringCellValue().replace("*", ""));
            row.getCell(2).setCellValue(row.getCell(2).getStringCellValue().replace("*", ""));
        }
        //删除11行以及之后填写的内容
        for (int i = 10; i <= sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
        }
        //检查封面页是否完整
        for (int j = 1; j < 9; j++) {
            Row row = sheet.getRow(j);
            //先校验B列
            CheckCoverBData(1, row, j, CompanyNum, CompanyName, CoverCheckList);

        }
        //再校验C列
        CheckCoverCData(3, sheet, CompanyNum, CompanyName, CoverCheckList);
        //将封面页校验异常的数据输出到日志里面

    }

    /**
     * 检查封面C列的数据完整性
     *
     * @param i
     * @param sheet
     * @param CompanyNum
     * @param CompanyName
     * @param CoverCheckList
     */
    private static void CheckCoverCData(int i, Sheet sheet, String CompanyNum, String CompanyName, List<CoverCheckDO> CoverCheckList) {
        //        //校验C列

        int[] arr = new int[]{1, 3, 4, 5};
        for (int k = 0; k < arr.length; k++) {
            Row row = sheet.getRow(arr[k]);
            if (row.getCell(i) != null) {
                row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                if ("".equals(row.getCell(i).getStringCellValue())) {
                    //数据校验不完整
                    CoverCheckDO coverCheckDO = new CoverCheckDO();
                    coverCheckDO.setOrganizationNumber(CompanyNum);
                    coverCheckDO.setCompanyName(CompanyName);
                    coverCheckDO.setUnfilledCell("D" + (arr[k] + 1));
                    coverCheckDO.setUnfilledproject(row.getCell(2).getStringCellValue().replace("*", ""));
                    CoverCheckList.add(coverCheckDO);
                }
            } else {
                //数据校验不完整
                CoverCheckDO coverCheckDO = new CoverCheckDO();
                coverCheckDO.setOrganizationNumber(CompanyNum);
                coverCheckDO.setCompanyName(CompanyName);
                coverCheckDO.setUnfilledCell("D" + (arr[k] + 1));
                coverCheckDO.setUnfilledproject(row.getCell(2).getStringCellValue().replace("*", ""));
                CoverCheckList.add(coverCheckDO);
            }
        }


    }

    /**
     * 检查封面B列的数据完整性
     *
     * @param i
     * @param row
     * @param j
     * @param CompanyNum
     * @param CompanyName
     * @param CoverCheckList
     */
    private static void CheckCoverBData(int i, Row row, int j, String CompanyNum, String CompanyName, List<CoverCheckDO> CoverCheckList) {
        //校验B列
        if (i == 1) {
            if (row.getCell(i) != null) {
                row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                if ("".equals(row.getCell(i).getStringCellValue())) {
                    //数据校验不完整
                    CoverCheckDO coverCheckDO = new CoverCheckDO();
                    coverCheckDO.setOrganizationNumber(CompanyNum);
                    coverCheckDO.setCompanyName(CompanyName);
                    coverCheckDO.setUnfilledCell("B" + (j + 1));
                    coverCheckDO.setUnfilledproject(row.getCell(0).getStringCellValue().replace("*", ""));
                    CoverCheckList.add(coverCheckDO);
                }
            } else {
                //数据校验不完整
                CoverCheckDO coverCheckDO = new CoverCheckDO();
                coverCheckDO.setOrganizationNumber(CompanyNum);
                coverCheckDO.setCompanyName(CompanyName);
                coverCheckDO.setUnfilledCell("B" + (j + 1));
                coverCheckDO.setUnfilledproject(row.getCell(0).getStringCellValue().replace("*", ""));
                CoverCheckList.add(coverCheckDO);
            }
        }


    }

//    /**
//     * 标签检查程序
//     *
//     * @param workbook
//     * @param
//     */
//    private static void LabelNameCheck(Workbook workbook, String excelPath, String ReplacementConfig, List<LabelNameCheckDO> LabelNameCheckList) throws IOException, InvalidFormatException {
//        String[] vl = new String[]{"合并资产负债表", "合并利润表", "合并现金流量表"};
//        String ExcelName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
//        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
//        String CompanyName = getCompanyName(ExcelName);
////        //将A列复制到D列----------------------------------------------------------->
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            try {
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//
//                    row.createCell(3).setCellValue("1|" + row.getCell(0).getStringCellValue().trim());
//                }
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //在A-D列范围内将特殊字符，包括*、回车符、#N/A、-、--替换为空
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            try {
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//                    for (int k = 0; k <= 3; k++) {
//
//                        if (row.getCell(k) != null) {
//                            Cell cell = row.getCell(k);
//                            LabelNameCheckDataClean(cell);
//                        }
//
//
//                    }
//                }
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //依据replacement配置文档，批量替换三张主表标签中的特殊符号。若配置文件中C列（是否替换labelName）为“N”，则只替换原始文件A列；反之A列和D列都需要进行替换
//        List<ReplacementDO> list = GetListFromReplacementConfig(ReplacementConfig);
//        //替换A列
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            try {
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//
//                    Cell cell = row.getCell(0);
//                    String data = cell.getStringCellValue();
//                    for (ReplacementDO replacementDO : list) {
//                        data = data.replace(replacementDO.getBeforeReplacementStr(), replacementDO.getAfterreplacementStr());
//                    }
//                    cell.setCellValue(data);
//                }
//
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //替换D列
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            try {
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//                    Cell cell = row.getCell(3);
//                    String data = cell.getStringCellValue();
//                    for (ReplacementDO replacementDO : list) {
//                        if ("Y".equals(replacementDO.getReplacelabelName())) {
//                            data = data.replace(replacementDO.getBeforeReplacementStr(), replacementDO.getAfterreplacementStr());
//                        }
//
//                    }
//                    cell.setCellValue(data);
//                }
//
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //检查英文引号，并将填写不完整的信息输出至“Result文件夹，“数据清洗结果”Excel文件中“LabelName Check”sheet。
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            try {
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//                    for (int k = 0; k <= 3; k++)
//                    {
//                        Cell cell = row.getCell(k);
//                        LabelNameCheckDO labelNameCheckDO = new LabelNameCheckDO();
//                        String data = cell.getStringCellValue();
////                        System.out.println(data);
//                        if (data.contains("\""))
//                        {
////                            System.out.println("有双引号+++++++++++++++++++++++++++++++++++++");
//                            data = replaceDoubleQuotationMarks(data);
//                            cell.setCellValue(data);
//                            labelNameCheckDO.setOrganizationNumber(CompanyNum);
//                            labelNameCheckDO.setCompanyName(CompanyName);
//                            labelNameCheckDO.setSheetName(vl[i]);
//                            String cellinformation = getCellInformation(j, k);
//                            labelNameCheckDO.setEnglishQuotationCell(cellinformation);
//                            labelNameCheckDO.setReadyCheckInformation(data);
//                            LabelNameCheckList.add(labelNameCheckDO);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //检查合并单元格,并切割补全
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            if (sheet!=null){
//                int nummergedregions = sheet.getNumMergedRegions();
//                if (nummergedregions > 1) {
//                    int num = sheet.getNumMergedRegions();
//                    for (int n = 1; n < num; n++) {
//                        CellRangeAddress cellRangeAddress = sheet.getMergedRegion(1);
////                    System.out.println("删除的合并区域的首行:"+cellRangeAddress.getFirstRow()+",首列:"+cellRangeAddress.getFirstColumn()+",末行:"+cellRangeAddress.getLastRow()+"，末列:"+cellRangeAddress.getFirstColumn());
//                        sheet.removeMergedRegion(1);
//                        FillSplitCellMethod(sheet,cellRangeAddress.getFirstRow(),cellRangeAddress.getFirstColumn(),cellRangeAddress.getLastRow(),cellRangeAddress.getFirstColumn());
//
//                    }
//            }
//
//            }
//
//        }
//    }

    /**
     * 替换英文双引号
     *
     * @param data
     * @return
     */
    private static String replaceDoubleQuotationMarks(String data) {
        String regex = "\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        String reCT=data;

        while(matcher.find()){
            String itemMatch = "“" + matcher.group(1) + "”";
            reCT=reCT.replace("\""+matcher.group(1)+"\"", itemMatch);
        }

        return reCT;
    }

    /**
     * 获取当前单元格的位置信息
     *
     * @param j
     * @param k
     * @return
     */
    private static String getCellInformation(int j, int k) {
        String cellinformation = "";
        switch (k) {
            case 0:
                cellinformation = "A" + (j + 1);
                break;
            case 1:
                cellinformation = "B" + (j + 1);
                break;
            case 2:
                cellinformation = "C" + (j + 1);
                break;
            case 3:
                cellinformation = "D" + (j + 1);
                break;
            default:
        }
        return cellinformation;
    }

    /**
     * 标签校验数据清洗
     */
    private static void LabelNameCheckDataClean(Cell cell) {
        if (cell != null) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String celldata = cell.getStringCellValue();
            celldata = celldata.replace("*", "");
            celldata = celldata.replace("\r|\n|\t", "");
            celldata = celldata.replace("#N/A", "");
            celldata = celldata.replace("-", "");
            celldata = celldata.replace("--", "");
            cell.setCellValue(celldata);
        }


    }

    /**
     * 资产负债表定位信息检查程序
     *
     * @param workbook
     * @param
     */
    private static void CallItemCheck(Workbook workbook, String excelPath, List<CallitemCheckDO> CallitemCheckList) {
        String ExcelName = excelPath.substring(excelPath.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);
        Sheet sheet = workbook.getSheet("合并资产负债表");
        List<String> list = new ArrayList<String>();
        //获取A列所有的数据------------------------------------------------------>
        try {
            for (int i = 1; i < sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                String str = row.getCell(0).getStringCellValue();
                list.add(str);
            }

        } catch (Exception e) {

        }
        //获取A列所有的数据------------------------------------------------------>
        try {
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

//            System.out.println("进行到第" + i + "行");
                String str = row.getCell(0).getStringCellValue();
                String labelstr = row.getCell(3).getStringCellValue();
//        资产负债表检查是否存在永续股-------------------------------------------------------------------------------------》
                if (str.contains("永续股")) {
//                    System.out.println("发现永续股------------------------------------");
                    row.getCell(0).setCellValue(str.replace("永续股","永续债"));
                    CallitemCheckDO callitemCheckDO = new CallitemCheckDO();
                    callitemCheckDO.setCompanyName(CompanyName);
                    callitemCheckDO.setOrganizationNumber(CompanyNum);
                    callitemCheckDO.setSheetName("合并资产负债表");
                    callitemCheckDO.setExceptionCell("A" + (i + 1));
                    callitemCheckDO.setUnCheckedProjectInformation("永续股");
                    CallitemCheckList.add(callitemCheckDO);
                }
                if (labelstr.contains("永续股")) {
//                    System.out.println("发现永续股------------------------------------");
                    row.getCell(3).setCellValue(labelstr.replace("永续股","永续债"));
                    CallitemCheckDO callitemCheckDO = new CallitemCheckDO();
                    callitemCheckDO.setCompanyName(CompanyName);
                    callitemCheckDO.setOrganizationNumber(CompanyNum);
                    callitemCheckDO.setSheetName("合并资产负债表");
                    callitemCheckDO.setExceptionCell("D" + (i + 1));
                    callitemCheckDO.setUnCheckedProjectInformation("永续股");
                    CallitemCheckList.add(callitemCheckDO);
                }
//        资产负债表检查是否存在永续股-------------------------------------------------------------------------------------》
//        模糊搜索“实收资本*净额”和“股本*净额”，若未搜索到该项，将“实收资本*”或“股本*”改为“实收资本（或股本）净额”；若搜索到，不做任何处理。-------------------------------------------------------------------------------------》
                int i1 = 0;
                for (String s1 : list) {
                    boolean b = s1.matches("(实收资本)([\\w\\W]*)(净额)");
                    boolean c = s1.matches("(股本)([\\w\\W]*)(净额)");
                    if (!(b == false && c == false)) {
                        i1++;
                    }
                }
                boolean d = str.matches("(股本)([\\w\\W]*)");
                boolean e = str.matches("(实收资本)([\\w\\W]*)");
                if (i1 == 0) {
                    if (d == true && e != true) {
                        row.getCell(0).setCellValue("股本净额");
                    } else if (d != true && e == true) {
                        row.getCell(0).setCellValue("实收资本净额");
                    }
                }
//        模糊搜索“实收资本*净额”和“股本*净额”，若未搜索到该项，将“实收资本*”或“股本*”改为“实收资本（或股本）净额”；若搜索到，不做任何处理。-------------------------------------------------------------------------------------》
//                流动资产替换--------------------------------------------------------------------------------------------->
                String[] vl = new String[]{"流动资产", "非流动资产", "流动负债", "非流动负债"};
                for (int k = 0; k < vl.length; k++) {
                    if (!str.equals(vl[k] + ":")) {
                        if (str.equals(vl[k]) && getcount(vl[k], list) == 1) {
//                            System.out.println("修改了"+vl[k]+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
                            row.getCell(0).setCellValue(row.getCell(0).getStringCellValue().replace(vl[k], vl[k] + ":"));

                        } else if (str.equals(vl[k]) && getcount(vl[k], list) > 1) {
//                            System.out.println(vl[k]+"重复，请输出到报告中");
                            CallitemCheckDO callitemCheckDO = new CallitemCheckDO();
                            callitemCheckDO.setCompanyName(CompanyName);
                            callitemCheckDO.setOrganizationNumber(CompanyNum);
                            callitemCheckDO.setSheetName("合并资产负债表");
                            callitemCheckDO.setExceptionCell("A" + (i + 1));
                            callitemCheckDO.setUnCheckedProjectInformation(vl[k]);
                            CallitemCheckList.add(callitemCheckDO);
                        }
                    }
                    if (str.equals(vl[k] + "：")) {
                        if (getcount(vl[k] + "：", list) > 1) {
//                            System.out.println("修改了"+vl[k]+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
                            CallitemCheckDO callitemCheckDO = new CallitemCheckDO();
                            callitemCheckDO.setCompanyName(CompanyName);
                            callitemCheckDO.setOrganizationNumber(CompanyNum);
                            callitemCheckDO.setSheetName("合并资产负债表");
                            callitemCheckDO.setExceptionCell("A" + (i + 1));
                            callitemCheckDO.setUnCheckedProjectInformation(str);
                            CallitemCheckList.add(callitemCheckDO);

                        }
                    }
                }
//                流动资产替换--------------------------------------------------------------------------------------------->
//            System.out.println("完成到第" + i + "行");


            }
        } catch (Exception e) {
        }
    }

    private static int getcount(String s, List<String> list) {
        int count = 0;
        for (String str : list) {
            if (s.equals(str)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 重复项位置判断
     *
     * @param workbook
     * @param
     */
    private static void DuplicateJudge(Workbook workbook, String ExcelPath, String DuplicateConfig, List<DuplicateJudgeDO> DuplicateList) throws IOException, InvalidFormatException {
        List<DuplicateJudgeInformation> list = GetInformationFromDulplicateConfig(DuplicateConfig);
        String ExcelName = ExcelPath.substring(ExcelPath.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);
        Set<String> set = new HashSet<>();
        Set<String> set1 = new HashSet<>();
        List<String> list1 = new ArrayList<>();
        for (DuplicateJudgeInformation duplicateJudgeInformation : list) {
            RemoveDuplicate(duplicateJudgeInformation.getTable(), workbook.getSheet(duplicateJudgeInformation.getTable()), duplicateJudgeInformation, ExcelPath, DuplicateList, set);
            if (set1.add(duplicateJudgeInformation.getTable())) {
                list1.add(duplicateJudgeInformation.getTable());
            }

        }

        //待所有的去重过程都结束之后，对A列查看是否有重复的，如果有，输出到报告文件
        for (String str : list1) {
            Sheet sheet = workbook.getSheet(str);
            if (sheet!=null){
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    if (sheet.getRow(i) != null) {
                        if (sheet.getRow(i).getCell(0) != null && !"".equals(sheet.getRow(i).getCell(0).getStringCellValue())) {
                            if (!set1.add(sheet.getRow(i).getCell(0).getStringCellValue())) {
                                DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();
                                duplicateJudgeDO.setCompanyName(CompanyName);
                                duplicateJudgeDO.setSheetName(str);
                                duplicateJudgeDO.setOrganizationNumber(CompanyNum);
                                duplicateJudgeDO.setExceptionCell("A" + (i + 1));
                                duplicateJudgeDO.setUnCheckedProjectInformation("此单元格内容重复!");
                                DuplicateList.add(duplicateJudgeDO);

                            }
                        }
                    }
                }
            }

        }

    }


    /**
     * 具体的去重规则
     */
    private static void RemoveDuplicate(String SheetName, Sheet sheet, DuplicateJudgeInformation duplicateJudgeInformation, String ExcelPath, List<DuplicateJudgeDO> DuplicateList, Set<String> set) {

        List<String> list = new ArrayList<String>();
        Set<String> set1 = new HashSet<>();
        try {
            //获取A列的字符串数组
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i) != null) {
                    if (sheet.getRow(i).getCell(0) != null) {
                        String str = sheet.getRow(i).getCell(0).getStringCellValue();
                        list.add(str);
                    }
                }


            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i) != null) {
                    if (sheet.getRow(i).getCell(0) != null) {
                        String str = sheet.getRow(i).getCell(0).getStringCellValue();

                        String t1 = duplicateJudgeInformation.getT1();
                        String t2 = duplicateJudgeInformation.getT2();
                        String key = duplicateJudgeInformation.getWhat();
                        int t1num = 0;
                        int t2num = 0;
                        int keynum = 0;
                        t1num = getnum(t1, list);
                        t2num = getnum(t2, list);
                        if (str.equals(key)) {
                            keynum = getnum(key, list);
                            String value = getStatus(set, SheetName, ExcelPath, DuplicateList, t1num, t2num, keynum, str, t1, t2);
                            if (!"".equals(value)) {
                                sheet.getRow(i).getCell(0).setCellValue(value);
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {

        }

    }

    /**
     * 获取去重状态
     *
     * @param t1num
     * @param t2num
     * @param keynum
     */
    private static String getStatus(Set<String> set, String SheetName, String ExcelPath, List<DuplicateJudgeDO> DuplicateList, int t1num, int t2num, int keynum, String str, String t1, String t2) {

        String status = "";
        String ExcelName = ExcelPath.substring(ExcelPath.lastIndexOf("\\") + 1);
        String CompanyNum = ExcelName.substring(0, ExcelName.indexOf("-"));
        String CompanyName = getCompanyName(ExcelName);
        String finalstr = "";
        if (t1num == 0 && t2num == 0) {

            status = t1 + "和" + t2 + "都不存在";
//            System.out.println(status);
            DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();

            duplicateJudgeDO.setCompanyName(CompanyName);
            duplicateJudgeDO.setOrganizationNumber(CompanyNum);
            duplicateJudgeDO.setSheetName(SheetName);
            duplicateJudgeDO.setExceptionCell("A" + (keynum + 1));
            duplicateJudgeDO.setUnCheckedProjectInformation(status);
            if (set.add(duplicateJudgeDO.getExceptionCell())) {
                DuplicateList.add(duplicateJudgeDO);
            }
        }
        if (t1num == 0 && t2num != 0) {
            if (keynum < t2num) {
                status = t1 + "不存在，且" + str + "在" + t2 + "之前";
//                System.out.println(status);
                DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();
                duplicateJudgeDO.setCompanyName(CompanyName);
                duplicateJudgeDO.setOrganizationNumber(CompanyNum);
                duplicateJudgeDO.setSheetName(SheetName);
                duplicateJudgeDO.setExceptionCell("A" + (keynum + 1));
                duplicateJudgeDO.setUnCheckedProjectInformation(status);
                if (set.add(duplicateJudgeDO.getExceptionCell())) {
                    DuplicateList.add(duplicateJudgeDO);
                }
            } else if (keynum > t2num) {
                finalstr = str + t2;
            }
        }
        if (t1num != 0 && t2num == 0) {
            if (keynum < t1num) {
                status = t2 + "不存在且" + str + "在" + t1 + "之前";
                DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();
                duplicateJudgeDO.setCompanyName(CompanyName);
                duplicateJudgeDO.setOrganizationNumber(CompanyNum);
                duplicateJudgeDO.setSheetName(SheetName);
                duplicateJudgeDO.setExceptionCell("A" + (keynum + 1));
                duplicateJudgeDO.setUnCheckedProjectInformation(status);
                if (set.add(duplicateJudgeDO.getExceptionCell())) {
                    DuplicateList.add(duplicateJudgeDO);
                }
            } else if (keynum > t1num) {
                finalstr = str + t1;
            }
        }
        if (t1num != 0 && t2num != 0) {
            if (t1num > t2num) {
                status = t1 + "在" + t2 + "之后";
                DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();
                duplicateJudgeDO.setCompanyName(CompanyName);
                duplicateJudgeDO.setOrganizationNumber(CompanyNum);
                duplicateJudgeDO.setSheetName(SheetName);
                duplicateJudgeDO.setExceptionCell("A" + (keynum + 1));
                duplicateJudgeDO.setUnCheckedProjectInformation(status);
                if (set.add(duplicateJudgeDO.getExceptionCell())) {
                    DuplicateList.add(duplicateJudgeDO);
                }
//                System.out.println(status);
            }
            if (keynum < t1num) {
                status = t1 + t2 + "同时存在," + str + "在" + t1 + "之前";
                DuplicateJudgeDO duplicateJudgeDO = new DuplicateJudgeDO();
                duplicateJudgeDO.setCompanyName(CompanyName);
                duplicateJudgeDO.setOrganizationNumber(CompanyNum);
                duplicateJudgeDO.setSheetName(SheetName);
                duplicateJudgeDO.setExceptionCell("A" + (keynum + 1));
                duplicateJudgeDO.setUnCheckedProjectInformation(status);
                if (set.add(duplicateJudgeDO.getExceptionCell())) {
                    DuplicateList.add(duplicateJudgeDO);
                }
//                System.out.println(status);
            }
            if (keynum < t2num) {
                finalstr = str + t1;
            }
            if (keynum > t2num) {
                finalstr = str + t2;
            }
        }
        return finalstr;
    }

    /**
     * 获取字符串在数组集合中的位置
     *
     * @param t
     * @param list
     * @return
     */
    private static int getnum(String t, List<String> list) {
        int num = 0;
        for (String str : list) {
            if (str.equals(t)) {
                num = list.indexOf(str) + 1;
            }
        }
//        System.out.println(t+"的数量是:"+num);
        return num;
    }

//    /**
//     * 检查标签是否全部在库
//     *
//     * @param workbook
//     * @param
//     */
//    private static void Databasecheck(Workbook workbook) {
//        //遍历三张表A列的数据
//        Map<String, String> map = new HashMap<String, String>();
//        String[] vl = new String[]{"合并资产负债表", "合并利润表", "合并现金流量表"};
//        for (int i = 0; i < vl.length; i++) {
//            try {
//                Sheet sheet = workbook.getSheet(vl[i]);
//                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                    Row row = sheet.getRow(j);
//                    map.put(vl[i], row.getCell(0).getStringCellValue());
//                    System.out.println("key:" + vl[i] + "value:" + row.getCell(0).getStringCellValue());
//                }
//            } catch (Exception e) {
//                continue;
//            }
//
//        }
////        //获取VBA的表中的数据
//        Sheet databasesheet = .getSheet("database");
//        for (int i = 1; i <= databasesheet.getLastRowNum(); i++) {
//            Row row = databasesheet.getRow(i);
//            System.out.println("第" + i + "行的table:" + row.getCell(0).getStringCellValue() + "，list:" + row.getCell(1).getStringCellValue());
//        }
//
//    }


}



