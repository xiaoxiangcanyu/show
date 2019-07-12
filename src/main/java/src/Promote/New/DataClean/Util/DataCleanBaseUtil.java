package src.Promote.New.DataClean.Util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.Promote.New.ComplianceChecking.Domain.KeyLabelDO;
import src.Promote.New.DataClean.Domain.ControlSheetDO;
import src.Promote.New.DataClean.Domain.ExceptionPhaseDO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//数据清洗的基本工具类
public class DataCleanBaseUtil {

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
     * 获取校验中心表的是否通过
     *
     * @param exceptionphasedolist
     * @param controlsheetlist
     */
    public static void CheckClean(List<ExceptionPhaseDO> exceptionphasedolist, List<ControlSheetDO> controlsheetlist) {
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

    /**
     * 根据数据类型获取单元格的值
     *
     * @param cell
     */
    public static Double getCellValue(Cell cell) {
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
                        value = Double.parseDouble(cell.getStringCellValue());
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
     * 根据数据类型获取时间格式单元格的值
     *
     * @param cell
     */
    public static String getDateCellValue(Cell cell) {
        String value = null;
        try {
            if (cell != null) {
                //首先先判断单元格的数据类型
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC: // 数字
                        SimpleDateFormat sdf = null;
                        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                                .getBuiltinFormat("h:mm")) {
                            sdf = new SimpleDateFormat("HH:mm");
                        } else {// 日期
                            sdf = new SimpleDateFormat("yyyy/MM/dd");
                        }
                        Date date = cell.getDateCellValue();
                        value = sdf.format(date);
                        break;
                    case Cell.CELL_TYPE_STRING: // 字符串
//                        System.out.println("第" + j + "行的第3个单元格数据类型是字符串类型:" + cell.getStringCellValue());
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA: // 公式
//                        System.out.println("第" + j + "行的第3个单元格数据类型是公式类型:" + cell.getNumericCellValue());
                        try {
                            value = cell.getNumericCellValue() + "";
                        } catch (Exception e) {

                        }
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空值

                        break;
                    case Cell.CELL_TYPE_ERROR: // 故障
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 获取关键词在sheet中的数量
     *
     * @param sheet
     * @param keyword1
     */
    public static int getKeywordCount(Sheet sheet, String keyword1) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    String cellvalue = cell.getStringCellValue();
                    if (cellvalue.equals(keyword1)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param sheet
     * @param keyword2
     */
    public static int getCellPosition(Sheet sheet, String keyword2) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    if (cell.getStringCellValue().equals(keyword2)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 获取A列中所有字符串
     *
     * @param sheet
     * @return
     */
    public static List<String> GetList(Sheet sheet) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    list.add(cell.getStringCellValue());
                }
            }
        }
        return list;
    }

    /**
     * 获取模糊查询的结果
     *
     * @param list
     * @param keyLabelDO
     */
    public static void GetResult(List<String> list, KeyLabelDO keyLabelDO, Sheet sheet) {
        String a = "\\*";
        String key1 = keyLabelDO.getKeyword1().split(a)[0];
        String key3 = keyLabelDO.getKeyword1().split(a)[1];
        String key2 = keyLabelDO.getKeyword2().split(a)[0];
        String reg1 = "(" + key1 + ")([\\w\\W]*)(" + key3 + ")";
        String reg2 = "(" + key2 + ")([\\w\\W]*)";
        Set<String> set = new LinkedHashSet<>();
        int count = 0;
        for (String str : list) {
            if (str.matches(reg1)) {
                count++;
            }
        }
        if (count == 0) {
            for (String str : list) {
                if (str.matches(reg2)) {
                    set.add(str);
                }
            }
        }
        for (String str : set) {
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        if (str.equals(cell.getStringCellValue())) {
                            cell.setCellValue(keyLabelDO.getKeyword3());
                        }
                    }
                }
            }

        }

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

    /**
     * 获取去重状态
     *
     * @param t1num
     * @param t2num
     * @param keynum
     */
    private static String getStatus( String SheetName, String CompanyName, List<ExceptionPhaseDO> exceptionPhaseDOList, int t1num, int t2num, int keynum, String str, String t1, String t2) {

        String status = "";
        String finalstr = "";
        if (t1num == 0 && t2num == 0) {
            status = t1 + "和" + t2 + "都不存在";
            ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
            exceptionPhaseDO.setSheetName(SheetName);
            exceptionPhaseDO.setExceptionType("标签重复异常");
            exceptionPhaseDO.setExceptionInfomation(status);
            exceptionPhaseDOList.add(exceptionPhaseDO);
        }
        if (t1num == 0 && t2num != 0) {
            if (keynum < t2num) {
                status = t1 + "不存在，且" + str + "在" + t2 + "之前";
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(SheetName);
                exceptionPhaseDO.setExceptionType("标签重复异常");
                exceptionPhaseDO.setExceptionInfomation(status);
                exceptionPhaseDOList.add(exceptionPhaseDO);
            } else if (keynum > t2num) {
                finalstr = str + t2;
            }
        }
        if (t1num != 0 && t2num == 0) {
            if (keynum < t1num) {
                status = t2 + "不存在且" + str + "在" + t1 + "之前";
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(SheetName);
                exceptionPhaseDO.setExceptionType("标签重复异常");
                exceptionPhaseDO.setExceptionInfomation(status);
                exceptionPhaseDOList.add(exceptionPhaseDO);
            } else if (keynum > t1num) {
                finalstr = str + t1;
            }
        }
        if (t1num != 0 && t2num != 0) {
            if (t1num > t2num) {
                status = t1 + "在" + t2 + "之后";
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(SheetName);
                exceptionPhaseDO.setExceptionType("标签重复异常");
                exceptionPhaseDO.setExceptionInfomation(status);
                exceptionPhaseDOList.add(exceptionPhaseDO);
            }
            if (keynum < t1num) {
                status = t1 + t2 + "同时存在," + str + "在" + t1 + "之前";
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(SheetName);
                exceptionPhaseDO.setExceptionType("标签重复异常");
                exceptionPhaseDO.setExceptionInfomation(status);
                exceptionPhaseDOList.add(exceptionPhaseDO);
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
//    ===========================================================输出文件函数==============================================================

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
        String[] headers = new String[]{"组织机构编码", "企业名称", "报告年度", "是否通过数据清洗"};
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
     * 输出异常报告和校验未通过的文件
     */
    public static void ExportCleanExceptionPhaseReport(List<ExceptionPhaseDO> exceptionphasedolist, String exceptionphasepath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("ExceptionPhase");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"报告名称", "Sheet名称", "异常类型", "异常信息"};
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
            row.createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellValue(exceptionPhaseDO.getExceptionInfomation());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(exceptionphasepath);
        xssfWorkbook.write(fileOutputStream);
    }


//    ===========================================================输出文件函数==============================================================

}
