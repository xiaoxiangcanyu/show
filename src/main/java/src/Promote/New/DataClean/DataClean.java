package src.Promote.New.DataClean;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.DatingDO;
import src.Promote.New.ComplianceChecking.Domain.KeyLabelDO;
import src.Promote.New.ComplianceChecking.Domain.PositionDO;
import src.Promote.New.ComplianceChecking.Domain.RedundacncyDO;
import src.Promote.New.DataClean.Domain.ControlSheetDO;
import src.Promote.New.DataClean.Domain.DulplicateDO;
import src.Promote.New.DataClean.Domain.ExceptionPhaseDO;
import src.Promote.New.DataClean.Util.DataCleanBaseUtil;
import src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck.ReplacementDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.Promote.New.ComplianceChecking.Util.GetInformaitonFromDatingSheet.GetInformaitonFromDatingSheetMethod;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromIntergrityCheck.GetSheetLabelNameInformationFromIntergrityCheck;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromKeyLabel.GetInformationFromKeyLabelMethod;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromPositionSheet.GetInformationFromPositionSheetMethod;
import static src.Promote.New.ComplianceChecking.Util.GetInformationFromRedundancyConfig.GetInformationFromRedundancyConfigMethod;
import static src.Promote.New.DataClean.Util.GetInformationFromDulplicateConfig.GetInformationFromDulplicateConfigMethod;
import static src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck.GetListFromReplacementConfig.GetListFromReplacementConfig;

/**
 * 数据清洗的工具类
 */
public class DataClean extends DataCleanBaseUtil {
    public static void DataCleanMethod(String ComplianceIntergrityConfigPath, String DataCleanDatingConfigPath, String DataCleanDuplicateConfigPath, String DataCleanKeyLabelConfigPath, String DataCleanPositioningLabelConfigPath, String DataCleanRedundancyConfig, String DataCleanReplacementConfig, String companyname, String exceptionfilepath, String checkfilePath, Workbook workbook, List<ControlSheetDO> controlsheetlist, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException, ParseException {
        int BeforeDataCleanNum = exceptionphasedolist.size();

        //==================================将B列中对应sheet页中的A列label复制到D列，并增加披露形式编码1|===============================================
        CopyLabelName(workbook, ComplianceIntergrityConfigPath);
        //==================================将B列中对应sheet页中的A列label复制到D列，并增加披露形式编码1|===============================================

        //==================================抓取有息负债总表的精确度信息（单元格F4），并根据精确度信息对BC两列信息进行小数位填充===============================================
        FillingDecimalAccuracy(workbook);
        //==================================抓取有息负债总表的精确度信息（单元格F4），并根据精确度信息对BC两列信息进行小数位填充===============================================

        //==================================依据ReplacementConfig.xlsx配置文件，对Excel报告中所有sheet的A列及D列（如适用）内容进行替换操作===============================================
        ReplacementElements(workbook, DataCleanReplacementConfig);
        //==================================依据ReplacementConfig.xlsx配置文件，对Excel报告中所有sheet的A列及D列（如适用）内容进行替换操作===============================================

        //==================================调用RedundancyConfig 配置文件A列信息，锁定需删除冗余内容的sheet页，并根据B列行信息，将该行及以下所有行内容进行删除===============================================
        DeleteRedundancy(workbook, DataCleanRedundancyConfig);
        //==================================调用RedundancyConfig 配置文件A列信息，锁定需删除冗余内容的sheet页，并根据B列行信息，将该行及以下所有行内容进行删除===============================================

        //==================================调用DatingConfig 配置文件A列信息，锁定需更改日期格式的sheet页，并根据B列单元格位置对日期格式进行更改，更改规则为：将日期数据，统一转换为yyyy-mm-dd格式===============================================
        UpdateDate(workbook, DataCleanDatingConfigPath);
        //==================================调用DatingConfig 配置文件A列信息，锁定需更改日期格式的sheet页，并根据B列单元格位置对日期格式进行更改，更改规则为：将日期数据，统一转换为yyyy-mm-dd格式===============================================

        //==================================根据配置文件PositioningLabelConfig A列信息，锁定查找标签所在的sheet名称，并根据如下规则逐条进行处理===============================================
        PositioningLabel(workbook, companyname, DataCleanPositioningLabelConfigPath, exceptionphasedolist);
        //==================================根据配置文件PositioningLabelConfig A列信息，锁定查找标签所在的sheet名称，并根据如下规则逐条进行处理===============================================

        //==================================根据配置文件KeyLabelConfig  A列信息，锁定查找标签所在的sheet名称，并根据如下规则进行处理===============================================
        KeyLabelOperation(workbook, DataCleanKeyLabelConfigPath);
        //==================================根据配置文件KeyLabelConfig  A列信息，锁定查找标签所在的sheet名称，并根据如下规则进行处理===============================================

//==================================据DuplicateConfig配置文档，根据指定关键定位词（C列和D列）判断指定sheet（A列）中的关键标签（B列）所在的位置，根据判断结果，将原项目名称修改为原项目名称&关键词。===============================================
        RemoveDulpicate(workbook, companyname, DataCleanDuplicateConfigPath,ComplianceIntergrityConfigPath, exceptionphasedolist);
        //==================================据DuplicateConfig配置文档，根据指定关键定位词（C列和D列）判断指定sheet（A列）中的关键标签（B列）所在的位置，根据判断结果，将原项目名称修改为原项目名称&关键词。===============================================


        //        //==============================================================输出合规性校验的文件====================================================================================
        if (BeforeDataCleanNum < exceptionphasedolist.size()) {
            ExportUnCheckedSource(workbook, exceptionfilepath);
        } else {
            ExportCheckedSource(workbook, checkfilePath);
        }
//        //==============================================================输出合规性校验的文件====================================================================================
    }


    /**
     * 将B列中对应sheet页中的A列label复制到D列，并增加披露形式编码1|
     *
     * @param workbook
     * @param sheetIntegrityCheck
     */
    private static void CopyLabelName(Workbook workbook, String sheetIntegrityCheck) throws IOException, InvalidFormatException {
        List<String> list = GetSheetLabelNameInformationFromIntergrityCheck(sheetIntegrityCheck);
        for (String str : list) {
            Sheet sheet = workbook.getSheet(str);
            try {
                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);

                    row.createCell(3).setCellValue("1|" + row.getCell(0).getStringCellValue().trim());
                }
            } catch (Exception e) {
                continue;
            }
        }

    }

    /**
     * 抓取有息负债总表的精确度信息（单元格F4），并根据精确度信息对BC两列信息进行小数位填充
     *
     * @param workbook
     */
    private static void FillingDecimalAccuracy(Workbook workbook) {
        Sheet sheet = workbook.getSheet("有息负债总表");
        if (sheet != null) {

            //根据小数点补位
            Row row = sheet.getRow(3);
            Cell cell = row.getCell(5);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String num = cell.getStringCellValue();
                String reg = "\\d";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(num);
                while (matcher.find()) {
                    num = matcher.group();
                }
                switch (num) {
                    case "个位":
                        num = "";
                        break;
                    case "1":
                        num = "0";
                        break;
                    case "2":
                        num = "00";
                        break;
                    case "3":
                        num = "000";
                        break;
                    case "4":
                        num = "0000";
                        break;
                    default:
                        break;
                }
                DecimalFormat decimalFormat = new DecimalFormat("#." + num);
                try {
                    for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                        //处理第一列
                        Double data0 = getCellValue(sheet.getRow(j).getCell(1));
                        if (data0 != null) {
                            sheet.getRow(j).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                            if ("".equals(num)) {
                                sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)).replace(".", ""));

                            } else if ("00".equals(num)) {
                                if (".00".equals(decimalFormat.format(new BigDecimal(data0)))) {
                                    sheet.getRow(j).getCell(1).setCellValue("0.00");

                                } else {
                                    sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));

                                }
                            } else if ("0".equals(num)) {
                                if (".0".equals(decimalFormat.format(new BigDecimal(data0)))) {
                                    sheet.getRow(j).getCell(1).setCellValue("0.0");

                                } else {
                                    sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));

                                }
                            } else if ("000".equals(num)) {
                                if (".000".equals(decimalFormat.format(new BigDecimal(data0)))) {
                                    sheet.getRow(j).getCell(1).setCellValue("0.000");

                                } else {
                                    sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));

                                }
                            } else if ("0000".equals(num)) {
                                if (".0000".equals(decimalFormat.format(new BigDecimal(data0)))) {
                                    sheet.getRow(j).getCell(1).setCellValue("0.0000");

                                } else {
                                    sheet.getRow(j).getCell(1).setCellValue(decimalFormat.format(new BigDecimal(data0)));

                                }
                            }

                        }
                        //处理第二列
                        Double data = getCellValue(sheet.getRow(j).getCell(2));
                        if (data != null) {
                            sheet.getRow(j).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                            if ("".equals(num)) {
                                sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)).replace(".", ""));

                            } else if ("00".equals(num)) {
                                if (".00".equals(decimalFormat.format(new BigDecimal(data)))) {
                                    sheet.getRow(j).getCell(2).setCellValue("0.00");

                                } else {
                                    sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)));

                                }
                            } else if ("0".equals(num)) {
                                if (".0".equals(decimalFormat.format(new BigDecimal(data)))) {
                                    sheet.getRow(j).getCell(2).setCellValue("0.0");

                                } else {
                                    sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)));

                                }
                            } else if ("000".equals(num)) {
                                if (".000".equals(decimalFormat.format(new BigDecimal(data)))) {
                                    sheet.getRow(j).getCell(2).setCellValue("0.000");

                                } else {
                                    sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)));

                                }
                            } else if ("0000".equals(num)) {
                                if (".0000".equals(decimalFormat.format(new BigDecimal(data)))) {
                                    sheet.getRow(j).getCell(2).setCellValue("0.0000");

                                } else {
                                    sheet.getRow(j).getCell(2).setCellValue(decimalFormat.format(new BigDecimal(data)));

                                }
                            }
                        }
                    }
                } catch (Exception e) {
//
                }


            }

        }
    }


    /**
     * 依据ReplacementConfig.xlsx配置文件，对Excel报告中所有sheet的A列及D列（如适用）内容进行替换操作
     *
     * @param workbook
     * @param replacementConfig
     */
    private static void ReplacementElements(Workbook workbook, String replacementConfig) throws IOException, InvalidFormatException {
        //依据replacement配置文档，批量替换三张主表标签中的特殊符号。若配置文件中C列（是否替换labelName）为“N”，则只替换原始文件A列；反之A列和D列都需要进行替换
        List<ReplacementDO> list = GetListFromReplacementConfig(replacementConfig);
        String[] vl = new String[20];
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            vl[i] = workbook.getSheetName(i);
        }
//        //若ReplacementConfig.xlsx配置文件中C列（是否替换labelName）为“N”，则只替换原始文件A列；反之A列和D列都需要进行替换
        for (int i = 0; i < vl.length; i++) {
            if (vl[i] != null) {
                Sheet sheet = workbook.getSheet(vl[i]);
                try {
                    for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                        Row row = sheet.getRow(j);

                        Cell cellA = row.getCell(0);
                        Cell cellD = row.getCell(3);
                        String dataA = cellA.getStringCellValue();
                        String dataD = cellD.getStringCellValue();
                        for (ReplacementDO replacementDO : list) {
                            if ("Y".equals(replacementDO.getReplacelabelName())) {
                                if ("N".equals(replacementDO.getCellReplacement())) {
                                    dataA = dataA.replace(replacementDO.getBeforeReplacementStr(), replacementDO.getAfterreplacementStr());
                                    dataD = dataD.replace(replacementDO.getBeforeReplacementStr(), replacementDO.getAfterreplacementStr());
                                    cellA.setCellValue(dataA);
                                    cellD.setCellValue(dataD);
                                } else if ("Y".equals(replacementDO.getCellReplacement())) {
                                    if (dataA.contains(replacementDO.getBeforeReplacementStr())) {
                                        cellA.setCellValue(replacementDO.getAfterreplacementStr());
                                    }
                                    if (dataD.contains(replacementDO.getBeforeReplacementStr())) {
                                        cellD.setCellValue(replacementDO.getAfterreplacementStr());
                                    }
                                }
                            } else if ("N".equals(replacementDO.getReplacelabelName())) {
                                if ("N".equals(replacementDO.getCellReplacement())) {
                                    dataA = dataA.replace(replacementDO.getBeforeReplacementStr(), replacementDO.getAfterreplacementStr());
                                    cellA.setCellValue(dataA);
                                } else if ("Y".equals(replacementDO.getCellReplacement())) {
                                    if (dataA.contains(replacementDO.getBeforeReplacementStr())) {
                                        dataA = replacementDO.getAfterreplacementStr();
                                    }
                                    cellA.setCellValue(dataA);

                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
//
        }

    }

    /**
     * 调用RedundancyConfig 配置文件A列信息，锁定需删除冗余内容的sheet页，并根据B列行信息，将该行及以下所有行内容进行删除
     *
     * @param workbook
     * @param redundancyConfig
     */
    private static void DeleteRedundancy(Workbook workbook, String redundancyConfig) throws IOException, InvalidFormatException {
        List<RedundacncyDO> list = GetInformationFromRedundancyConfigMethod(redundancyConfig);
        for (RedundacncyDO redundacncyDO : list) {
            Sheet sheet = workbook.getSheet(redundacncyDO.getSheet());
            for (int i = Integer.parseInt(redundacncyDO.getRowNum()) - 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);

                }
            }
        }

    }

    /**
     * 调用DatingConfig 配置文件A列信息，锁定需更改日期格式的sheet页，并根据B列单元格位置对日期格式进行更改，更改规则为：将日期数据，统一转换为yyyy-mm-dd格式
     *
     * @param workbook
     * @param datingConfig
     */
    private static void UpdateDate(Workbook workbook, String datingConfig) throws IOException, InvalidFormatException, ParseException {
        List<DatingDO> list = GetInformaitonFromDatingSheetMethod(datingConfig);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-mm-dd");
        for (DatingDO datingDO : list) {
            Sheet sheet = workbook.getSheet(datingDO.getSheet());
            CellReference cellReference = new CellReference(datingDO.getCellposition());
            Row row = sheet.getRow(cellReference.getRow());
            if (row != null) {
                Cell cell = row.getCell(cellReference.getCol());
                String DateTime = getDateCellValue(cell);
                try {
                    Date DateTime1 = simpleDateFormat.parse(DateTime);
                    DateTime = simpleDateFormat1.format(DateTime1);
                    cell.setCellValue(DateTime);
                } catch (Exception e) {
                    continue;
                }

            }

        }
    }

    /**
     * 根据配置文件PositioningLabelConfig A列信息，锁定查找标签所在的sheet名称，并根据如下规则逐条进行处理
     *
     * @param workbook
     * @param companyname
     * @param positioningLabelConfig
     * @param exceptionphasedolist
     */
    private static void PositioningLabel(Workbook workbook, String companyname, String positioningLabelConfig, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<PositionDO> positioningLabelConfiglist = GetInformationFromPositionSheetMethod(positioningLabelConfig);
        for (PositionDO positionDO : positioningLabelConfiglist) {
            Sheet sheet = workbook.getSheet(positionDO.getSheetName());
            //若未能找到配置文件B列所示关键词keyword1（流动资产：）但能找到唯一一个配置文件C列所示关键词keyword2 （流动资产），则将C列关键词keyword2（流动资产）替换为B列关键词keyword1（流动资产：）
            if (getKeywordCount(sheet, positionDO.getKeyword1()) == 0 && getKeywordCount(sheet, positionDO.getKeyword2()) == 1) {
                Row row = sheet.getRow(getCellPosition(sheet, positionDO.getKeyword2()));
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        cell.setCellValue(positionDO.getKeyword1());
                    }
                }
            }
            //若未能找到配置文件B列所示关键词keyword1，且能找到不止一个配置文件C列所示关键词keyword2，则将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签定位异常”，异常信息为“存在不止一个XX”，XX为keyword2。
            if (getKeywordCount(sheet, positionDO.getKeyword1()) == 0 && getKeywordCount(sheet, positionDO.getKeyword2()) > 1) {
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(positionDO.getSheetName());
                exceptionPhaseDO.setReportName(companyname);
                exceptionPhaseDO.setExceptionInfomation("存在不止一个" + positionDO.getKeyword2());
                exceptionPhaseDO.setExceptionType("标签定位异常");
                exceptionphasedolist.add(exceptionPhaseDO);
            }
            //若找到不止一个配置文件B列所示关键词keyword1，则将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签定位异常”，异常信息为“存在不止一个XX”，XX为keyword1。
            if (getKeywordCount(sheet, positionDO.getKeyword1()) > 1) {
                ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                exceptionPhaseDO.setSheetName(positionDO.getSheetName());
                exceptionPhaseDO.setReportName(companyname);
                exceptionPhaseDO.setExceptionType("标签定位异常");
                exceptionPhaseDO.setExceptionInfomation("存在不止一个" + positionDO.getKeyword1());
                exceptionphasedolist.add(exceptionPhaseDO);
            }
        }

    }


    /**
     * 根据配置文件KeyLabelConfig  A列信息，锁定查找标签所在的sheet名称，并根据如下规则进行处理
     *
     * @param workbook
     * @param positioningLabelConfig
     */
    private static void KeyLabelOperation(Workbook workbook, String positioningLabelConfig) throws IOException, InvalidFormatException {
        List<KeyLabelDO> KeyLabelList = GetInformationFromKeyLabelMethod(positioningLabelConfig);
        for (KeyLabelDO keyLabelDO : KeyLabelList) {
            Sheet sheet = workbook.getSheet(keyLabelDO.getSheet());
//        模糊搜索“实收资本*净额”和“股本*净额”，若未搜索到该项，将“实收资本*”或“股本*”改为“实收资本（或股本）净额”；若搜索到，不做任何处理。-------------------------------------------------------------------------------------》
            List<String> list = GetList(sheet);
            GetResult(list, keyLabelDO, sheet);
        }

    }


    /**
     * 据DuplicateConfig配置文档，根据指定关键定位词（C列和D列）判断指定sheet（A列）中的关键标签（B列）所在的位置，根据判断结果，将原项目名称修改为原项目名称&关键词。
     *
     * @param workbook
     * @param companyname
     * @param duplicateConfig
     * @param exceptionphasedolist
     */
    private static void RemoveDulpicate(Workbook workbook, String companyname, String duplicateConfig,String ComplianceIntergrityConfigPath, List<ExceptionPhaseDO> exceptionphasedolist) throws IOException, InvalidFormatException {
        List<DulplicateDO> list = GetInformationFromDulplicateConfigMethod(duplicateConfig);
        //获取duplicate的表集合
        Set<String> duplicateSheetSet = new HashSet<>();
        for (DulplicateDO dulplicateDO1: list){
            duplicateSheetSet.add(dulplicateDO1.getSheet());
        }
        Set<String> set = new HashSet<>();

        for (String sheetName : duplicateSheetSet) {
            if (sheetName!=null){
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet!=null){
                    for (int i = 1;i<=sheet.getLastRowNum();i++){
                        Row row1 = sheet.getRow(i);
                        if (row1!=null){
                            Cell cell1 = row1.getCell(0);
                            if (cell1!=null){
                                cell1.setCellType(Cell.CELL_TYPE_STRING);
                                for (DulplicateDO dulplicateDO:list){
                                    if (cell1.getStringCellValue().equals(dulplicateDO.getItems())){
                                        int key1Index = getCellPosition(sheet, dulplicateDO.getKey1());
                                        int key2Index = getCellPosition(sheet, dulplicateDO.getKey2());
                                        int ItemsIndex = getCellPosition(sheet, dulplicateDO.getItems());
                                        ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                        exceptionPhaseDO.setSheetName(dulplicateDO.getSheet());
                                        exceptionPhaseDO.setReportName(companyname);
                                        exceptionPhaseDO.setExceptionType("标签重复异常");
                                        //如果关键定位词key1和key2都不存在，则将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“key1”&“key2”都不存在，其中key1和key2为配置文件对应信息。
                                        if (key1Index == -1 && key2Index == -1) {
                                            exceptionPhaseDO.setExceptionInfomation(dulplicateDO.getKey1() + " 和 " + dulplicateDO.getKey2() + " 都不存在 ");
                                            if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                                exceptionphasedolist.add(exceptionPhaseDO);
                                            }
                                        }
                                        //如果关键定位词key1不存在，key2存在
                                        //如果B列内容所在行小于D列关键定位词key2所在行，则为异常情况，将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“key1”不存在，“Item”在“key2”之前，其中key1、key2和Item为配置文件中对应信息
                                        //如果B列内容所在行大于D列关键定位词key2所在行，则将该sheet中找到的B列内容替换为“B&D”，例如“预付款项非流动资产：
                                        if (key1Index == -1 && key2Index != -1) {
                                            if (ItemsIndex < key2Index) {
                                                exceptionPhaseDO.setExceptionInfomation(dulplicateDO.getKey1() + " 不存在，且 " + dulplicateDO.getItems() + " 在 " + dulplicateDO.getKey2() + " 之前 ");
                                                if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                                    exceptionphasedolist.add(exceptionPhaseDO);
                                                }
                                            } else if (ItemsIndex > key2Index) {
                                                Row row = sheet.getRow(ItemsIndex);
                                                Cell cell = row.getCell(0);
                                                cell.setCellValue(dulplicateDO.getItems() + dulplicateDO.getKey2());
                                            }
                                        }
                                        //如果关键定位词key2不存在，key1存在
                                        //如果B列内容所在行小于C列关键定位词key1所在行，为异常情况，将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“key2”不存在，“Item”在“key1”之前，其中key1、key2和Item为配置文件中对应信息
                                        //如果B列内容所在行大于C列关键定位词key1所在行，，则将该sheet中找到的B列内容替换为“B&C”，例如“预付款项流动资产
                                        if (key1Index != -1 && key2Index == -1) {
                                            if (ItemsIndex < key1Index) {
                                                exceptionPhaseDO.setExceptionInfomation(dulplicateDO.getKey2() + " 不存在，且 " + dulplicateDO.getItems() + " 在 " + dulplicateDO.getKey1() + " 之前 ");
                                                if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                                    exceptionphasedolist.add(exceptionPhaseDO);
                                                }
                                            } else if (ItemsIndex > key1Index) {
                                                Row row = sheet.getRow(ItemsIndex);
                                                Cell cell = row.getCell(0);
                                                cell.setCellValue(dulplicateDO.getItems() + dulplicateDO.getKey1());
                                            }

                                        }
                                        //如果关键定位词key1和key2都存在
                                        //如果C列内容关键定位词key1所在行大于D列内容key2所在行，为异常情况，则将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“key1”在“key2”之后，其中key1和key2为配置文件对应信息。
                                        //如果B列内容所在行小于C列关键定位词key1所在行，为异常情况，则将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“key1”&“key2”同时存在，“Item”在“key1”之前，其中key1、key2、Item为配置文件对应信息。
                                        //A和B两种场景都不符合，且B列内容所在行小于D列关键定位词kye2所在行，则将该sheet中找到的B列内容替换为“B&C”，例如“预付款项流动资产：”A和B两种场景都不符合，且B列内容所在行大于D列内容关键定位词key2所在行，则将该sheet中找到的B列内容替换为“B&D”，例如“预付款项非流动资产”
                                        if (key1Index != -1 && key2Index != -1) {
                                            if (key1Index > key2Index) {
                                                exceptionPhaseDO.setExceptionInfomation(dulplicateDO.getKey1() + " 在 " + dulplicateDO.getKey2() + " 之后");
                                                if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                                    exceptionphasedolist.add(exceptionPhaseDO);
                                                }
                                            } else if (ItemsIndex < key1Index) {
                                                exceptionPhaseDO.setExceptionInfomation(dulplicateDO.getKey1() + " 和 " + dulplicateDO.getKey2() + " 同时存在. " + dulplicateDO.getItems() + " 在 " + dulplicateDO.getKey1() + " 之前 ");
                                                if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                                    exceptionphasedolist.add(exceptionPhaseDO);
                                                }
                                            } else if (ItemsIndex < key2Index && ItemsIndex > key1Index) {
                                                Row row = sheet.getRow(ItemsIndex);
                                                Cell cell = row.getCell(0);
                                                cell.setCellValue(dulplicateDO.getItems() + dulplicateDO.getKey1());
                                            } else if (ItemsIndex > key2Index && ItemsIndex > key1Index) {
                                                Row row = sheet.getRow(ItemsIndex);
                                                Cell cell = row.getCell(0);
                                                cell.setCellValue(dulplicateDO.getItems() + dulplicateDO.getKey2());
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            }

        }

        //根据duplicate配置文档校验后，检查所有sheet页A列cell value是否有重复，如果有，将异常信息及对应的Excel报告按照3.5的要求输出至Exception-Phase2文件夹，异常类型为“标签重复异常”，异常信息为“XX单元格与YY单元格重复”，XX与YY分别为重复单元格位置信息
        List<String> list1 = GetSheetLabelNameInformationFromIntergrityCheck(ComplianceIntergrityConfigPath);
        for (String str : list1) {
            if (str!=null){
                Sheet sheet = workbook.getSheet(str);
                for (int i = 1;i<=sheet.getLastRowNum();i++){
                    Row row = sheet.getRow(i);
                    if (row!=null){
                        Cell cell  = row.getCell(0);
                        if (cell!=null){
                            if (cell.getStringCellValue().length()>0){
                                int count = getKeywordCount(sheet,cell.getStringCellValue());
                                if (count>1){
                                    String dulpicatecellPostion =  getotherDulpicatecellPostion(sheet,cell.getStringCellValue());
                                    ExceptionPhaseDO exceptionPhaseDO = new ExceptionPhaseDO();
                                    exceptionPhaseDO.setSheetName(str);
                                    exceptionPhaseDO.setReportName(companyname);
                                    exceptionPhaseDO.setExceptionType("标签重复异常");
                                    exceptionPhaseDO.setExceptionInfomation(dulpicatecellPostion+"单元格重复");
                                    if (set.add(exceptionPhaseDO.getSheetName()+exceptionPhaseDO.getReportName()+exceptionPhaseDO.getExceptionType()+exceptionPhaseDO.getExceptionInfomation())){
                                        exceptionphasedolist.add(exceptionPhaseDO);
                                    }
                                }
                            }

                        }
                    }
                }
            }
            
        }
    }

    /**
     * 获取A列重复数据的单元格位置
     *
     * @param sheet
     * @param stringCellValue
     * @return
     */
    private static String getotherDulpicatecellPostion(Sheet sheet, String stringCellValue) {
        String duplicateposition = "";
        for (int i =1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            if (row!=null){
                Cell cell = row.getCell(0);
                if (cell!=null){
                    String value = cell.getStringCellValue();
                    if (value.equals(stringCellValue)){
                        duplicateposition = duplicateposition +"A" +(i+1)+" ";
                    }                }
            }
        }
        return duplicateposition;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
//        String directorypath =args[0];
        String directorypath = "C:\\Users\\songyu\\Desktop\\XBRL新版\\数据清洗校验阶段所需文件";
        String inputexcelpath = "";//输入清洗文件的路径
        String companyname = "";//公司名称
        String checkfilePathDirectory = "";//清洗成功的输出文件夹
        String exceptionfilePathDirectory = "";//清洗异常的输出文件夹

//        ========================================================输出文件的路径==================================================================
        String exportcontrolsheetphasepath = "";//控制表路径
        String exceptionphasepath = "";//异常表路径
        String exceptionfilepath = "";//异常文件路径
        String checkfilePath = "";//校验合格表的路径
//        ========================================================输出文件的路径==================================================================


//        ==============================================================输出表数据集合的初始化=======================================================================================
        List<ControlSheetDO> controlsheetlist = new ArrayList<>();
        List<ExceptionPhaseDO> exceptionphasedolist = new ArrayList<>();
//        ==============================================================输出表数据集合的初始化=================================

//        ========================================================配置文件的路径==================================================================
        String ComplianceIntergrityConfigPath = "";
        String DataCleanConfigDirectory = "";
        String DataCleanDatingConfigPath = "";
        String DataCleanDuplicateConfigPath = "";
        String DataCleanKeyLabelConfigPath = "";
        String DataCleanPositioningLabelConfigPath = "";
        String DataCleanRedundancyConfig = "";
        String DataCleanReplacementConfig = "";
//        ========================================================配置文件的路径==================================================================


        File directoryfile = new File(directorypath);
        File[] files = directoryfile.listFiles();
        System.out.println("===========================================================开始数据清洗=================================================================");
        for (File file : files) {
//            //输出文件的文件夹
            if (file.getAbsolutePath().contains("Cleansing-输出文件")) {
                exportcontrolsheetphasepath = file.getAbsolutePath() + "\\Control Sheet-Phase2.xlsx";
                File outputDirectory = new File(file.getAbsolutePath());
                File[] outputDirectoryList = outputDirectory.listFiles();
                for (File outputDirectoryDO : outputDirectoryList) {
                    if (outputDirectoryDO.getAbsolutePath().contains("Cleaned Source")) {
                        checkfilePathDirectory = outputDirectoryDO.getAbsolutePath();
                    } else if (outputDirectoryDO.getAbsolutePath().contains("Exception-Phase2")) {
                        exceptionphasepath = outputDirectoryDO.getAbsolutePath() + "\\" + "Exception-Phase2.xlsx";
                        exceptionfilePathDirectory = outputDirectoryDO.getAbsolutePath() + "\\Failed Souce";
                    }
                }
            }
        }
        //Fail文件夹每次更新前都删除和清空
        File ExceptionFile = new File(exceptionfilePathDirectory);
        File[] files8 = ExceptionFile.listFiles();
        for (File file:files8){
            file.delete();
        }
        for (File file : files) {
            if (file.getAbsolutePath().contains("Cleansing-config")) {
                DataCleanConfigDirectory = file.getAbsolutePath();
                File DataCleanConfigfiledirectory = new File(DataCleanConfigDirectory);
                File[] DataCleanConfigfileList = DataCleanConfigfiledirectory.listFiles();
                for (File DataCleanConfigfile : DataCleanConfigfileList) {
                    if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-DatingConfig")) {
                        DataCleanDatingConfigPath = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-DuplicateConfig")) {
                        DataCleanDuplicateConfigPath = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-KeyLabelConfig")) {
                        DataCleanKeyLabelConfigPath = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-PositioningLabelConfig")) {
                        DataCleanPositioningLabelConfigPath = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-RedundancyConfig")) {
                        DataCleanRedundancyConfig = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Cleansing-ReplacementConfig")) {
                        DataCleanReplacementConfig = DataCleanConfigfile.getAbsolutePath();
                    } else if (DataCleanConfigfile.getAbsolutePath().contains("Compliance-SheetIntegrityCheck")) {
                        ComplianceIntergrityConfigPath = DataCleanConfigfile.getAbsolutePath();
                    }
                }
            } else if (file.getAbsolutePath().contains("Cleansing-input")) {
                String InputFileDirectoy = file.getAbsolutePath();
                File InputFileDirectoyPath = new File(InputFileDirectoy);
                File[] files1 = InputFileDirectoyPath.listFiles();
                for (File file1 : files1) {
                    inputexcelpath = file1.getAbsolutePath();
                    companyname = inputexcelpath.substring(inputexcelpath.lastIndexOf("\\") + 1, inputexcelpath.lastIndexOf("."));
//  ======================================输出文件路径==================================================================================
                    checkfilePath = checkfilePathDirectory + "\\" + companyname + ".xlsx";
                    exceptionfilepath = exceptionfilePathDirectory + "\\" + companyname + ".xlsx";
//  ======================================输出文件路径==================================================================================

                    Workbook workbook = WorkbookFactory.create(new FileInputStream(inputexcelpath));//获取待处理的excel文件流
                    System.out.println(inputexcelpath);
//        ==============================================================**************************合规性校验*************************=======================================================================================
                    CentralSheetInitialize(controlsheetlist, companyname);//初始化中心表的数据

                    DataCleanMethod(ComplianceIntergrityConfigPath, DataCleanDatingConfigPath, DataCleanDuplicateConfigPath, DataCleanKeyLabelConfigPath, DataCleanPositioningLabelConfigPath, DataCleanRedundancyConfig, DataCleanReplacementConfig, companyname, exceptionfilepath, checkfilePath, workbook, controlsheetlist, exceptionphasedolist);
//        ==============================================================**************************合规性校验**************************=======================================================================================
                }

            }

        }
//        ==============================================================路径初始化=======================================================================================


//

        //==============================================================输出异常报告====================================================================================
        ExportCleanExceptionPhaseReport(exceptionphasedolist, exceptionphasepath);
        //==============================================================输出异常报告====================================================================================

        //==============================================================输出控制表======================================================================================================
        CheckClean(exceptionphasedolist, controlsheetlist);//校验合规性是否通过
        ExportControlSheetPhase(controlsheetlist, exportcontrolsheetphasepath);
        //==============================================================输出控制表======================================================================================================
        System.out.println("===========================================================数据清洗结束=================================================================");
    }


}
