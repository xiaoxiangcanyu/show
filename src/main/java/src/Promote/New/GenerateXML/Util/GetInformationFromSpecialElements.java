package src.Promote.New.GenerateXML.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.GenerateXML.Domain.SpecialElementsDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从特殊元素表中获取数据信息
 */
public class GetInformationFromSpecialElements {
    public static List<SpecialElementsDO> GetInformationFromSpecialElementsMethod(String ExcelPath) throws IOException, InvalidFormatException {
        List<SpecialElementsDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(ExcelPath));
        Sheet sheet = workbook.getSheet("Special");
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            SpecialElementsDO specialElementsDO = new SpecialElementsDO();
            if (row!=null){
                if (row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    specialElementsDO.setTalbeName(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1)!=null){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    specialElementsDO.setElementName(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2)!=null){
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    specialElementsDO.setYear(row.getCell(2).getStringCellValue());
                }

                list.add(specialElementsDO);
            }
        }
        return list;
    }


}
