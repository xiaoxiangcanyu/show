package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import src.Promote.Old.XMLCheckOperation.Domain.XMLDataDO;

import java.util.List;

/**
 * 将匹配到的结果输出到database里面
 */
public class FillDataBase {
    public static void FillDataBaseMethod(Workbook workbook, List<XMLDataDO> list){
        Sheet sheet = workbook.getSheet("database");
        int i =1;
       for (XMLDataDO xmlDataDO:list){
           Row row = sheet.getRow(i);
           if (row!=null){
               row.getCell(0).setCellValue(xmlDataDO.getQName());
               row.getCell(1).setCellValue(xmlDataDO.getContextRef());
               row.getCell(2).setCellValue(xmlDataDO.getValue());
               row.getCell(3).setCellValue(xmlDataDO.getDisclosure());
               row.getCell(4).setCellValue(xmlDataDO.getUnitRef());
               row.getCell(5).setCellValue(xmlDataDO.getDecimals());
               row.createCell(6).setCellValue(xmlDataDO.getLabelName());
           }
           i++;
       }


    }
}
