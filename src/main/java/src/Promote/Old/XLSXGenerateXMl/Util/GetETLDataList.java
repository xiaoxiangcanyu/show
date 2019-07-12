//package src.Promote.Old.XLSXGenerateXMl.Util;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.*;
//import src.Promote.Old.XLSXGenerateXMl.Config.ElementMappingDO;
//import src.Promote.Old.XLSXGenerateXMl.Config.LabelNameDO;
//import src.Promote.Old.XLSXGenerateXMl.Domain.ReadyMappingDataDO;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static src.Promote.Old.XLSXGenerateXMl.Util.GetDataFromElementsMappingExcel.GetDataFromElementsMappingExcelMethod;
//import static src.Promote.Old.XLSXGenerateXMl.Util.GetDataFromLabelNameExcel.GetDataFromLabelNameExcelMethod;
//
///**
// * 从数据清洗之后的excel中获取原始数据
// */
//public class GetETLDataList {
//
//    public static List<ReadyMappingDataDO> GetETLDataList(String ETLExcelpath, String LabelExcelPath, String ElementsMappingExcelPath) throws IOException, InvalidFormatException {
//        List<ReadyMappingDataDO> list = new ArrayList<>();
//        Workbook workbook = WorkbookFactory.create(new FileInputStream(ETLExcelpath));
//        List<ElementMappingDO> MappingList = GetDataFromElementsMappingExcelMethod(ElementsMappingExcelPath);
//        List<LabelNameDO> LabelNameList = GetDataFromLabelNameExcelMethod(LabelExcelPath);
//        String[] vl = new String[]{"封面信息", "合并资产负债表", "合并利润表", "合并现金流表", "有息负债总表"};
//        String currency = "";
//        String metricsmeasures = "";
//        String accuracy = "";
//        for (int i = 0; i < vl.length; i++) {
//            Sheet sheet = workbook.getSheet(vl[i]);
//            String table = "封面信息";
//
//            if ("封面信息".equals(vl[i])) {
//
//                for (int j =1;j<=5;j++){
//                    Row row = sheet.getRow(j);
//                    ReadyMappingDataDO readyMappingDataDO = new ReadyMappingDataDO();
//                    ReadyMappingDataDO readyMappingDataDO1 = new ReadyMappingDataDO();
//                    if (row != null) {
//                        if (row.getCell(0) != null) {
//                            String subject = row.getCell(0).getStringCellValue();
//                            String value = row.getCell(1).getStringCellValue();
//                            readyMappingDataDO.setColumnum("2");
//                            readyMappingDataDO.setTable(table);
//                            readyMappingDataDO.setSubject(subject);
//                            readyMappingDataDO.setValue(value);
//                        }
//                        if (row.getCell(2) != null) {
//                            String subject = row.getCell(0).getStringCellValue();
//                            if (row.getCell(3)!=null){
//                                String value = row.getCell(3).getStringCellValue();
//                                readyMappingDataDO1.setValue(value);
//
//                            }
//                            readyMappingDataDO1.setColumnum("4");
//                            readyMappingDataDO1.setTable(table);
//                            readyMappingDataDO1.setSubject(subject);
//                        }
//                    }
//                    //从封面页获取currency
//                    Row currencyRow = sheet.getRow(6);
//                    if (currencyRow != null) {
//                        if (currencyRow.getCell(1) != null) {
//                            currency = currencyRow.getCell(1).getStringCellValue();
//                            readyMappingDataDO.setCurrency(currency);
//                            readyMappingDataDO1.setCurrency(currency);
//                        }
//                    }
//                    // 从封面页获取metricsmeasures
//                    Row metricsmeasuresRow = sheet.getRow(7);
//                    if (metricsmeasuresRow != null) {
//                        if (metricsmeasuresRow.getCell(1) != null) {
//                            metricsmeasures = metricsmeasuresRow.getCell(1).getStringCellValue();
//                            readyMappingDataDO.setMetricsmeasures(metricsmeasures);
//                            readyMappingDataDO1.setMetricsmeasures(metricsmeasures);
//                        }
//                    }
//                    //从封面页获取accuracy
//                    Row accuracyRow = sheet.getRow(8);
//                    if (accuracyRow != null) {
//                        if (accuracyRow.getCell(1) != null) {
//                            accuracy = accuracyRow.getCell(1).getStringCellValue();
//                            readyMappingDataDO.setAccuracy(accuracy);
//                            readyMappingDataDO1.setAccuracy(accuracy);
//                        }
//                    }
//
//                    list.add(readyMappingDataDO);
//                    list.add(readyMappingDataDO1);
//                }
//            } else {
//                if (sheet != null) {
//                    for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                        String subject = "";
//                        String value1 = "";
//                        String value2 = "";
//                        String labelname = "";
//                        List<Integer> columlist = new ArrayList<>();
//                        Row row = sheet.getRow(j);
//                        ReadyMappingDataDO readyMappingDataDO = new ReadyMappingDataDO();
//                        ReadyMappingDataDO readyMappingDataDO1 = new ReadyMappingDataDO();
//                        readyMappingDataDO.setTable(vl[i]);
//                        readyMappingDataDO1.setTable(vl[i]);
//                        //设置单元格类型
//                        //获取科目名称
//                        if (row!=null){
//                            if (row.getCell(0) != null) {
//                                subject = row.getCell(0).getStringCellValue();
//                                readyMappingDataDO.setSubject(subject);
//                                readyMappingDataDO1.setSubject(subject);
//                            }
//                            //根据科目获取value的列数
//                            if (!"".equals(subject)) {
//                                columlist = getColumNum(vl[i], subject, MappingList);
//                            }
//                            if (columlist.size() == 2) {
//                                int columnum1 = columlist.get(0) - 1;
//                                int columnum2 = columlist.get(1) - 1;
//                                if (row.getCell(columnum1) != null ) {
//                                    row.getCell(columnum1).setCellType(Cell.CELL_TYPE_STRING);
//                                    if (!"".equals(row.getCell(columnum1).getStringCellValue())){
//                                        value1 = row.getCell(columnum1).getStringCellValue();
//                                        readyMappingDataDO.setValue(value1);
//                                        readyMappingDataDO.setColumnum(columlist.get(0)+"");
//                                    }
//
//                                }
//                                if (row.getCell(columnum2) != null ) {
//                                    row.getCell(columnum2).setCellType(Cell.CELL_TYPE_STRING);
//                                    if (!"".equals(row.getCell(columnum2).getStringCellValue())){
//                                        value2 = row.getCell(columnum2).getStringCellValue();
//                                        readyMappingDataDO1.setValue(value2);
//                                        readyMappingDataDO1.setColumnum(columlist.get(1)+"");
//                                    }
//
//                                }
//                            }
//
//                            //判断是否需要加标签
//                            for (LabelNameDO labelNameDO : LabelNameList) {
//                                if (labelNameDO.getSheetName().equals(vl[i])) {
//                                    if (row.getCell(3) != null) {
//                                        labelname = row.getCell(3).getStringCellValue();
//                                        if (!"有息负债表".equals(vl[i])){
//                                            readyMappingDataDO.setLabelname(labelname);
//                                            readyMappingDataDO1.setLabelname(labelname);
//                                        }
//
//                                    }
//
//                                }
//                            }
//                            readyMappingDataDO.setCurrency(currency);
//                            readyMappingDataDO1.setCurrency(currency);
//                            readyMappingDataDO.setMetricsmeasures(metricsmeasures);
//                            readyMappingDataDO1.setMetricsmeasures(metricsmeasures);
//                            readyMappingDataDO.setAccuracy(accuracy);
//                            readyMappingDataDO1.setAccuracy(accuracy);
//                            if (readyMappingDataDO.getValue()!=null && !"".equals(readyMappingDataDO.getValue())){
//                                list.add(readyMappingDataDO);
//                            }
//                            if (readyMappingDataDO1.getValue()!=null && !"".equals(readyMappingDataDO1.getValue())){
//                                list.add(readyMappingDataDO1);
//                            }
//                        }
//
//                    }
//                }
//
//            }
//        }
//        return list;
//    }
//
//    public static List<Integer> getColumNum(String table, String subject, List<ElementMappingDO> list) {
//        List<Integer> columList = new ArrayList<>();
//        for (ElementMappingDO elementMappingDO : list) {
//            if (elementMappingDO.getSubject().equals(subject) && elementMappingDO.getTable().equals(table)) {
//                if (elementMappingDO.getColumnum() != null) {
//                    int columnum = Integer.parseInt(elementMappingDO.getColumnum().substring(0, 1));
//                    columList.add(columnum);
//                }
//
//            }
//        }
//        Collections.sort(columList);
//        return columList;
//    }
//
////    public static void main(String[] args) throws IOException, InvalidFormatException {
////        List<ReadyMappingDataDO> list = GetETLDataList("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906041529\\测试-3\\test\\10001694X-20151231-中国广核集团有限公司-刘波-20161102.xlsx", "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\labelname.xlsx", "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\Element ReadyMappingCheck.xlsx");
////        System.out.println(JSON.toJSONString(list));
////    }
//}
