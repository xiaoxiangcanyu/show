package src.Promote.Old.XLSXGenerateXMl.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.Old.XLSXGenerateXMl.Config.ElementMappingDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从mapping表里获取所有的数据
 */
public class GetDataFromElementsMappingExcel {
    public static List<ElementMappingDO> GetDataFromElementsMappingExcelMethod(String ExccelPath) throws IOException, InvalidFormatException {
        List<ElementMappingDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(ExccelPath));
        Sheet sheet = workbook.getSheet("Element ReadyMappingCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ElementMappingDO elementMappingDO = new ElementMappingDO();
            //设置单元格类型
            if (row.getCell(0) != null) {
                String table = row.getCell(0).getStringCellValue();
                ;
                elementMappingDO.setTable(table);
            }
            if (row.getCell(1) != null) {
                String subject = row.getCell(1).getStringCellValue();
                elementMappingDO.setSubject(subject);
            }
            if (row.getCell(2) != null) {
                String columnum = row.getCell(2).getNumericCellValue()+"";
                elementMappingDO.setColumnum(columnum);
            }
            if (row.getCell(3) != null) {
                String cellid = row.getCell(3).getStringCellValue();
                elementMappingDO.setCellid(cellid);
            }
            if (row.getCell(4) != null) {
                String contextid = row.getCell(4).getStringCellValue();
                elementMappingDO.setContextid(contextid);
            }
            if (!"".equals(elementMappingDO.getTable())) {
                list.add(elementMappingDO);
            }
        }
        return list;
    }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//        List<ElementMappingDO> list = GetDataFromElementsMappingExcel("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\Element ReadyMappingCheck.xlsx");
//        for (ElementMappingDO elementMappingDO:list){
//            System.out.println("table:"+elementMappingDO.getTable()+",list:"+elementMappingDO.getSubject()+",Column："+elementMappingDO.getColumnum()+",Cell ID："+elementMappingDO.getCellid()+"，Context ID："+elementMappingDO.getContextid());
//        }
//    }
}
