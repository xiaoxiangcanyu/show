package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.RedundacncyDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从去冗余表中获取信息
 */
public class GetInformationFromRedundancyConfig {
    public static List<RedundacncyDO> GetInformationFromRedundancyConfigMethod(String excelPath) throws IOException, InvalidFormatException {
        List<RedundacncyDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet("redundancy");
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            RedundacncyDO redundacncyDO = new RedundacncyDO();
            if (row!=null){
                if (row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    redundacncyDO.setSheet(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1)!=null){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    redundacncyDO.setRowNum(row.getCell(1).getStringCellValue());
                }
                list.add(redundacncyDO);
            }
        }
        return  list;
    }


}
