package src.Promote.New.ReadyMappingCheck.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetInformationFromMappingConfig {
   public static List<ReadyMappingConfigDO> GetInformationFromMappingConfig(String ExcelPath) throws IOException, InvalidFormatException {
       List<ReadyMappingConfigDO> list = new ArrayList<>();
       Workbook workbook = WorkbookFactory.create(new FileInputStream(ExcelPath));
       Sheet sheet = workbook.getSheet("Transform-Element Mapping-2015");
       for (int i = 1;i<=sheet.getLastRowNum();i++){
           Row row = sheet.getRow(i);
           ReadyMappingConfigDO readyMappingConfigDO = new ReadyMappingConfigDO();
           if (row!=null){
               if (row.getCell(0)!=null){
                   row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                   readyMappingConfigDO.setTable(row.getCell(0).getStringCellValue());
               }
               if (row.getCell(1)!=null){
                   row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                   readyMappingConfigDO.setSubject(row.getCell(1).getStringCellValue());
               }
               if (row.getCell(2)!=null){
                   row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                   readyMappingConfigDO.setColumnum(row.getCell(2).getStringCellValue());
               }
               if (row.getCell(3)!=null){
                   row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                   readyMappingConfigDO.setCellid(row.getCell(3).getStringCellValue());
               }
               if (row.getCell(4)!=null){
                   row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                   readyMappingConfigDO.setContextid(row.getCell(4).getStringCellValue());
               }
               list.add(readyMappingConfigDO);
           }
       }
       return list;
   }

}
