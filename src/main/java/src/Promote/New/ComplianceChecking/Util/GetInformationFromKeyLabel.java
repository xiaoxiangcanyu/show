package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.KeyLabelDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从KeyLabel表中获取数据的实体类
 */
public class GetInformationFromKeyLabel {
    public static List<KeyLabelDO> GetInformationFromKeyLabelMethod(String ExcelPath) throws IOException, InvalidFormatException {
        List<KeyLabelDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(ExcelPath));
        Sheet sheet = workbook.getSheet("key label");
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            KeyLabelDO keyLabelDO = new KeyLabelDO();
            if (row!=null){
                if (row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    keyLabelDO.setSheet(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1)!=null){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    keyLabelDO.setKeyword1(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2)!=null){
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    keyLabelDO.setKeyword2(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3)!=null){
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    keyLabelDO.setKeyword3(row.getCell(3).getStringCellValue());
                }
                list.add(keyLabelDO);
            }
        }
        return  list;
    }
}
