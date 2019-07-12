package src.Promote.New.GenerateXML.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.GenerateXML.Domain.LabelNameDO;
import src.Promote.New.GenerateXML.Domain.ReadyMappingDataDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static src.Promote.New.ComplianceChecking.Util.GetInformationFromIntergrityCheck.GetInformationFromIntergrityCheck;
import static src.Promote.New.DataClean.Util.DataCleanBaseUtil.getCellValue;
import static src.Promote.New.ReadyMappingCheck.Util.GetInformationFromMappingConfig.GetInformationFromMappingConfig;
import static src.Promote.Old.XLSXGenerateXMl.Util.GetDataFromLabelNameExcel.GetDataFromLabelNameExcelMethod;

/**
 * 从数据清洗之后的excel中获取原始数据
 */
public class GetETLDataList {

    public static List<ReadyMappingDataDO> GetETLDataList(Workbook workbook, String LabelExcelPath, String ElementsMappingExcelPath) throws IOException, InvalidFormatException {
        List<ReadyMappingDataDO> list = new ArrayList<>();
        List<ReadyMappingConfigDO> MappingList = GetInformationFromMappingConfig(ElementsMappingExcelPath);
        List<LabelNameDO> LabelNameList = GetDataFromLabelNameExcelMethod(LabelExcelPath);
        List<String> stringList = GetInformationFromIntergrityCheck(LabelExcelPath);
        String[] vl = stringList.toArray(new String[stringList.size()]);
        String currency = "";
        String metricsmeasures = "";
        String accuracy = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 0; i < vl.length; i++) {
            Sheet sheet = workbook.getSheet(vl[i]);
            String table = "封面信息";
            if ("封面信息".equals(vl[i]))
            {
                for (int j =1;j<=6;j++){
//                    if (sheet!=null){
                        Row row = sheet.getRow(j);
                        ReadyMappingDataDO readyMappingDataDO = new ReadyMappingDataDO();
                        ReadyMappingDataDO readyMappingDataDO1 = new ReadyMappingDataDO();
                        if (row != null) {
                            if (row.getCell(0) != null) {
                                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                                String subject = row.getCell(0).getStringCellValue();
                                String value = row.getCell(1).getStringCellValue();
                                readyMappingDataDO.setColumnum("2");
                                readyMappingDataDO.setTable(table);
                                readyMappingDataDO.setSubject(subject);
                                readyMappingDataDO.setValue(value);
                            }
                            if (row.getCell(2) != null) {
                                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                                String subject = row.getCell(0).getStringCellValue();
                                if (row.getCell(3)!=null){
                                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                                    String value = row.getCell(3).getStringCellValue();
                                    readyMappingDataDO1.setValue(value);
                                }
                                readyMappingDataDO1.setColumnum("4");
                                readyMappingDataDO1.setTable(table);
                                readyMappingDataDO1.setSubject(subject);
                            }
                        }
                        //从封面页获取currency
                        Row currencyRow = sheet.getRow(7);
                        if (currencyRow != null) {
                            if (currencyRow.getCell(1) != null) {
                                currencyRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                                currency = currencyRow.getCell(1).getStringCellValue();
                                readyMappingDataDO.setCurrency(currency);
                                readyMappingDataDO1.setCurrency(currency);
                            }
                        }
                        // 从封面页获取metricsmeasures
                        Row metricsmeasuresRow = sheet.getRow(8);
                        if (metricsmeasuresRow != null) {
                            if (metricsmeasuresRow.getCell(1) != null) {
                                metricsmeasuresRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                                metricsmeasures = metricsmeasuresRow.getCell(1).getStringCellValue();
                                readyMappingDataDO.setMetricsmeasures(metricsmeasures);
                                readyMappingDataDO1.setMetricsmeasures(metricsmeasures);
                            }
                        }
                        //从封面页获取accuracy
                        Row accuracyRow = sheet.getRow(9);
                        if (accuracyRow != null) {
                            if (accuracyRow.getCell(1) != null) {
                                accuracyRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                                accuracy = accuracyRow.getCell(1).getStringCellValue();
                                readyMappingDataDO.setAccuracy(accuracy);
                                readyMappingDataDO1.setAccuracy(accuracy);
                            }
                        }

                        list.add(readyMappingDataDO);
                        list.add(readyMappingDataDO1);

//                    }
                }
            }
            else {
                if (sheet != null) {
                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                            String subject = "";
                            String labelname = "";
                            List<Integer> columlist = new ArrayList<>();
                            Row row = sheet.getRow(j);
                            ReadyMappingDataDO readyMappingDataDO = new ReadyMappingDataDO();
                            ReadyMappingDataDO readyMappingDataDO1 = new ReadyMappingDataDO();
                            readyMappingDataDO.setTable(vl[i]);
                            readyMappingDataDO1.setTable(vl[i]);
                            //设置单元格类型
                            //获取科目名称
                            if (row!=null){
                                if (row.getCell(0) != null) {
                                    subject = row.getCell(0).getStringCellValue();
                                    readyMappingDataDO.setSubject(subject);
                                    readyMappingDataDO1.setSubject(subject);
                                }
                                //根据科目获取value的列数
                                if (!"".equals(subject)) {
                                    columlist = getColumNum(vl[i], subject, MappingList);
                                }
                                if (columlist.size() == 2) {
                                    int columnum1 = columlist.get(0) - 1;
                                    int columnum2 = columlist.get(1) - 1;
                                    if (row.getCell(columnum1) != null ) {
                                        Double value1origi =getCellValue(row.getCell(columnum1));
                                        if (value1origi!=null){
//                                            System.out.println("value1origi:"+value1origi);
                                            readyMappingDataDO.setValue(decimalFormat.format( new BigDecimal(value1origi)));
                                            readyMappingDataDO.setColumnum(columlist.get(0)+"");
                                        }

                                    }
                                    if (row.getCell(columnum2) != null ) {
                                        Double value2origi =getCellValue(row.getCell(columnum2));
                                        if (value2origi!=null){
//                                            System.out.println("value2origi:"+value2origi);
                                            readyMappingDataDO1.setValue(decimalFormat.format( new BigDecimal(value2origi)));
                                            readyMappingDataDO1.setColumnum(columlist.get(1)+"");
                                        }

                                    }
                                }

                                //判断是否需要加标签
                                for (LabelNameDO labelNameDO : LabelNameList) {
                                    if (labelNameDO.getSheetName().equals(vl[i])) {
                                        if (row.getCell(3) != null) {
                                            labelname = row.getCell(3).getStringCellValue();
                                            if (!"有息负债表".equals(vl[i])){
                                                readyMappingDataDO.setLabelname(labelname);
                                                readyMappingDataDO1.setLabelname(labelname);
                                            }

                                        }

                                    }
                                }
                                readyMappingDataDO.setCurrency(currency);
                                readyMappingDataDO1.setCurrency(currency);
                                readyMappingDataDO.setMetricsmeasures(metricsmeasures);
                                readyMappingDataDO1.setMetricsmeasures(metricsmeasures);
                                readyMappingDataDO.setAccuracy(accuracy);
                                readyMappingDataDO1.setAccuracy(accuracy);
                                if (readyMappingDataDO.getValue()!=null && !"".equals(readyMappingDataDO.getValue())){
                                    list.add(readyMappingDataDO);
                                }
                                if (readyMappingDataDO1.getValue()!=null && !"".equals(readyMappingDataDO1.getValue())){
                                    list.add(readyMappingDataDO1);
                                }
                            }

                        }
                }
            }
        }
        return list;
    }

    public static List<Integer> getColumNum(String table, String subject, List<ReadyMappingConfigDO> list) {
        List<Integer> columList = new ArrayList<>();
        for (ReadyMappingConfigDO elementMappingDO : list) {
            if (elementMappingDO.getSubject().equals(subject) && elementMappingDO.getTable().equals(table)) {
                if (elementMappingDO.getColumnum() != null) {
                    int columnum = Integer.parseInt(elementMappingDO.getColumnum().substring(0, 1));
                    columList.add(columnum);
                }

            }
        }
        Collections.sort(columList);
        return columList;
    }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//        List<ReadyMappingDataDO> list = GetETLDataList("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906041529\\测试-3\\test\\10001694X-20151231-中国广核集团有限公司-刘波-20161102.xlsx", "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\labelname.xlsx", "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\Element ReadyMappingCheck.xlsx");
//        System.out.println(JSON.toJSONString(list));
//    }
}
