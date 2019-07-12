package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.Promote.New.ComplianceChecking.Domain.ControlSheetDO;
import src.Promote.New.ComplianceChecking.Domain.ExceptionPhaseDO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 合规性校验的基础工具类
 */
public class ComplianceBasicUtil {
    /**
     * 中心表数据初始化
     *
     * @param
     */
    public static void CentralSheetInitialize(List<ControlSheetDO> controlSheetDOS, String excelPath) throws IOException, InvalidFormatException {
        String[] vl = excelPath.split("-");
        ControlSheetDO controlSheetDO = new ControlSheetDO();

        controlSheetDO.setOrganizationNumber(vl[0]);

        controlSheetDO.setCompanyName(vl[2]);

        controlSheetDO.setReportYear(vl[1]);
        controlSheetDOS.add(controlSheetDO);

    }


    /**
     * 获取缺失的表集合
     *
     * @param actualsheetarrlist
     * @param templatesheetarrlist
     * @return
     */
    public static String getLackList(List<String> actualsheetarrlist, List<String> templatesheetarrlist) {
        List<String> LackList = new ArrayList<String>();
        for (String str : templatesheetarrlist) {
            if (!actualsheetarrlist.contains(str)) {
                LackList.add(str);
            }
        }
        String LackSheet = "";
        StringBuilder stringBuilder = new StringBuilder(LackSheet);
        for (String string : LackList) {
            if (LackList.size() > 1) {
                stringBuilder.append(string + "-");
            } else {
                stringBuilder.append(string);
            }
        }
        LackSheet = stringBuilder.toString();
        return LackSheet;
    }

    /**
     * 获取校验中心表的是否通过
     *
     * @param exceptionphasedolist
     * @param controlsheetlist
     */
    public static void CheckCompliance(List<ExceptionPhaseDO> exceptionphasedolist, List<ControlSheetDO> controlsheetlist) {
        Set<String> set = new HashSet<>();//创建去重集合
        for (ExceptionPhaseDO exceptionPhaseDO : exceptionphasedolist) {
            String companyname = exceptionPhaseDO.getReportName().split("-")[2];
            set.add(companyname);
        }

        for (ControlSheetDO controlSheetDO : controlsheetlist) {
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


    //=====================================================文件输出的函数========================================================================

    /**
     * 输出控制表
     */
    public static void ExportControlSheetPhase(List<ControlSheetDO> ControlSheetDOList, String ExportControlSheetPhasePath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("ComplianceCheck");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"组织机构编码", "企业名称", "报告年度", "是否通过合规性校验"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (ControlSheetDO controlSheetDO : ControlSheetDOList) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(controlSheetDO.getOrganizationNumber());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(controlSheetDO.getCompanyName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(controlSheetDO.getReportYear());
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(controlSheetDO.getIsChecked());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(ExportControlSheetPhasePath);
        xssfWorkbook.write(fileOutputStream);

    }

    /**
     * 输出异常报告
     */
    public static void ExportExceptionPhaseReport(List<ExceptionPhaseDO> exceptionphasedolist, String exceptionphasepath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("ExceptionPhase");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"报告名称", "Sheet名称", "异常类型"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (ExceptionPhaseDO exceptionPhaseDO : exceptionphasedolist) {
            Row row = xssfSheet.createRow(i);
            row.createCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(0).setCellValue(exceptionPhaseDO.getReportName());
            row.createCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellValue(exceptionPhaseDO.getSheetName());
            row.createCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellValue(exceptionPhaseDO.getExceptionType());
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(exceptionphasepath);
        xssfWorkbook.write(fileOutputStream);

    }

    /**
     * 输出校验未通过的函数
     */
    public static void ExportUnCheckedSource(Workbook workbook, String compileuncheckedpasspath) throws IOException {
        FileOutputStream os = new FileOutputStream(compileuncheckedpasspath);
        workbook.write(os);
        os.close();
    }

    /**
     * 输出校验通过的函数
     */
    public static void ExportCheckedSource(Workbook workbook, String compilecheckedpasspath) throws IOException {
        FileOutputStream os = new FileOutputStream(compilecheckedpasspath);
        workbook.write(os);
        os.close();
    }
//=====================================================文件输出的函数========================================================================
}
