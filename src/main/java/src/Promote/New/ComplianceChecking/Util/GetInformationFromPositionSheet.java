package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.PositionDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从位置页获取配置信息
 */
public class GetInformationFromPositionSheet {
    public static List<PositionDO> GetInformationFromPositionSheetMethod(String ExcelPath) throws IOException, InvalidFormatException {
        List<PositionDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(ExcelPath));
        Sheet sheet = workbook.getSheet("positioning label");
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            PositionDO positionDO = new PositionDO();
            if (row!=null){
                if (row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    positionDO.setSheetName(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1)!=null){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    positionDO.setKeyword1(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2)!=null){
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    positionDO.setKeyword2(row.getCell(2).getStringCellValue());
                }
                list.add(positionDO);
            }
        }
        return  list;

    }
}
