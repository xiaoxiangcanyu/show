package src.Promote.New.GenerateXML.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.GenerateXML.Domain.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 补全所有的数据
 */
public class FillMappedData {
    public static List<MappedDataDO> FillMappedDataMethod(List<MappedDataDO> sortedList, List<ReadyMappingDataDO> originalList, String CurrencyPath, String DecimalPath, String WeighttransformPath) throws IOException, InvalidFormatException {
        for (MappedDataDO mappedDataDO:sortedList){
            String text = mappedDataDO.getText();
//            String context = mappedDataDO.getContextRef();
            for (ReadyMappingDataDO dataDO:originalList){
                if (dataDO.getValue()!=null){
                    try {
                        if (dataDO.getValue().endsWith(text)){
                            //monetaryItemType类型的数据进行补充属性
                            if ("xbrli:monetaryItemType".equals(mappedDataDO.getType())){
                                String unitref = GenerateUnitRef(CurrencyPath,dataDO.getCurrency());
                                String decimals = GenerateDecimal(mappedDataDO,DecimalPath,WeighttransformPath,dataDO.getAccuracy(),dataDO.getMetricsmeasures());
                                mappedDataDO.setUnitRef(unitref);
                                mappedDataDO.setDecimals(decimals);
                            }
                            //pershare类型的数据进行footnote补充
                            if ("num:perShareItemType".equals(mappedDataDO.getType())){
                                String unitref = GenerateFootNoteUnitRef(CurrencyPath,dataDO.getCurrency());
                                String decimals = GenerateDecimal(mappedDataDO,DecimalPath,WeighttransformPath,dataDO.getAccuracy(),dataDO.getMetricsmeasures());
                                mappedDataDO.setUnitRef(unitref);
                                mappedDataDO.setDecimals(decimals);
                            }
                            if ("xbrli:stringItemType".equals(mappedDataDO.getType())){
                                mappedDataDO.setLabelName(null);
                            }
                        }
                    }catch (Exception e){
                        continue;
                    }

                }
            }
        }
        return  sortedList;
    }

    private static String GenerateFootNoteUnitRef(String currencyPath, String currency) throws IOException, InvalidFormatException {

        String unitRef = "";
        Workbook workbook = WorkbookFactory.create(new FileInputStream(currencyPath));
        List<CurrencyDO> list = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Transform-UnitConfig");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row!=null){
                CurrencyDO currencyDO = new CurrencyDO();
                String cellvalue = row.getCell(0).getStringCellValue();
                String Pershare = row.getCell(2).getStringCellValue();
                currencyDO.setCellvalue(cellvalue);
                currencyDO.setPershare(Pershare);
                list.add(currencyDO);
            }

        }

        for (CurrencyDO currencyDO:list){
            if (currencyDO.getCellvalue().equals(currency)){
                unitRef = currencyDO.getPershare();
            }
        }
        return unitRef;
    }

    /**]
     * 获取decimal
     * @param decimalPath
     * @param WeighttransformPath
     * @param accuracy
     * @param metricsmeasures
     * @return
     */
    private static String GenerateDecimal(MappedDataDO mappedDataDO,String decimalPath, String WeighttransformPath, String accuracy, String metricsmeasures) throws IOException, InvalidFormatException {
        int Decimal = 0;
        String type = mappedDataDO.getType();
        if ("xbrli:monetaryItemType".equals(type)){
            int Decimal1 = getDecimal1(decimalPath,accuracy);
            int Decimal2 = getDecimal2(WeighttransformPath,metricsmeasures);
            Decimal = Decimal1+Decimal2;
        }else if ("num:perShareItemType".equals(type)){
         String length1 = mappedDataDO.getText().substring( mappedDataDO.getText().lastIndexOf(".")+1).length()+"";
            int Decimal1 =Integer.parseInt(length1);
            int Decimal2 = getperShareItemTypeDecimal2(WeighttransformPath,metricsmeasures);
            Decimal = Decimal1+Decimal2;
        }

        return Decimal+"";
    }

    private static int getperShareItemTypeDecimal2(String weighttransformPath, String metricsmeasures) throws IOException, InvalidFormatException {
        int Decimal2 = 0;
        Workbook workbook = WorkbookFactory.create(new FileInputStream(weighttransformPath));
        List<Decimal2DO> list = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Transform-WeightConfig");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Decimal2DO decimal2DO = new Decimal2DO();
                String cellvalue = row.getCell(0).getStringCellValue();
                int Value = (int) row.getCell(3).getNumericCellValue();
                decimal2DO.setCellValue(cellvalue);
                decimal2DO.setValue(Value);
                list.add(decimal2DO);
            }

        }
        for (Decimal2DO decimal2DO:list){
            if (decimal2DO.getCellValue().equals(metricsmeasures)){
                Decimal2 = decimal2DO.getValue();
            }
        }
        return Decimal2;
    }

    /**
     * 获取Decimal2
     * @param weighttransformPath
     * @param metricsmeasures
     * @return
     */
    private static int getDecimal2(String weighttransformPath, String metricsmeasures) throws IOException, InvalidFormatException {
        int Decimal2 = 0;
        Workbook workbook = WorkbookFactory.create(new FileInputStream(weighttransformPath));
        List<Decimal2DO> list = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Transform-WeightConfig");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Decimal2DO decimal2DO = new Decimal2DO();
                String cellvalue = row.getCell(0).getStringCellValue();
                int Value = (int) row.getCell(2).getNumericCellValue();
                decimal2DO.setCellValue(cellvalue);
                decimal2DO.setValue(Value);
                list.add(decimal2DO);
            }

        }
        for (Decimal2DO decimal2DO:list){
            if (decimal2DO.getCellValue().equals(metricsmeasures)){
                Decimal2 = decimal2DO.getValue();
            }
        }
        return Decimal2;
    }

    /**
     * 获取Decimal1
     * @param decimalPath
     * @param accuracy
     * @return
     */
    private static int getDecimal1(String decimalPath, String accuracy) throws IOException, InvalidFormatException {
        int Decimal1 = 0;
        Workbook workbook = WorkbookFactory.create(new FileInputStream(decimalPath));
        List<Decimal1DO> list = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Transform-DecimalConfig");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row!=null){
                Decimal1DO decimal1DO = new Decimal1DO();
                String cellvalue = row.getCell(0).getStringCellValue();
                int Value = (int) row.getCell(1).getNumericCellValue();
                decimal1DO.setCellValue(cellvalue);
                decimal1DO.setValue(Value);
                list.add(decimal1DO);
            }

            for (Decimal1DO decimal1DO:list){
                if (decimal1DO.getCellValue().equals(accuracy)){
                    Decimal1 = decimal1DO.getValue();
                }
            }
        }
        return Decimal1;
    }

    /**
     * 补全UnitRef，根据Currency
     * @return
     */
    private static String GenerateUnitRef(String currencypath, String Currency) throws IOException, InvalidFormatException {
        String unitRef = "";
        Workbook workbook = WorkbookFactory.create(new FileInputStream(currencypath));
        List<CurrencyDO> list = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Transform-UnitConfig");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
           if (row!=null){
               CurrencyDO currencyDO = new CurrencyDO();
               String cellvalue = row.getCell(0).getStringCellValue();
               String Monetary = row.getCell(1).getStringCellValue();
               currencyDO.setCellvalue(cellvalue);
               currencyDO.setMonetary(Monetary);
               list.add(currencyDO);
           }

        }

        for (CurrencyDO currencyDO:list){
            if (currencyDO.getCellvalue().equals(Currency)){
                unitRef = currencyDO.getMonetary();
            }
        }
        return unitRef;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
//        System.out.println(GenerateFootNoteUnitRef("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\config\\Currency.xlsx","人民币"));
    }
}
