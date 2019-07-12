package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.DatingDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从日期表中获取数据实体类
 */
public class GetInformaitonFromDatingSheet {
    public static List<DatingDO> GetInformaitonFromDatingSheetMethod(String ExcelPath) throws IOException, InvalidFormatException {
        List<DatingDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(ExcelPath));
        Sheet sheet = workbook.getSheet("Dating");
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            DatingDO datingDO = new DatingDO();
            if (row!=null){
                if (row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    datingDO.setSheet(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1)!=null){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    datingDO.setCellposition(row.getCell(1).getStringCellValue());
                }
                list.add(datingDO);
            }
        }
        return  list;
    }
}
