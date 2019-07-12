package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.New.ComplianceChecking.Domain.ComplianceSpecialItemCheckDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Compliance-SpecialItemCheck表中获取对应的信息
 */
public class GetInformationFromComplianceSpecialItemCheck {
    public static List<ComplianceSpecialItemCheckDO> GetInformationFromComplianceSpecialItemCheckMethod(String excelPath) throws IOException, InvalidFormatException {
        List<ComplianceSpecialItemCheckDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet("Compliance-SpecialItemCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ComplianceSpecialItemCheckDO complianceSpecialItemCheckDO = new ComplianceSpecialItemCheckDO();
            if (row.getCell(0)!=null)
            {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String sheetname = row.getCell(0).getStringCellValue();
                complianceSpecialItemCheckDO.setSheet(sheetname);
            }
            if (row.getCell(1)!=null)
            {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String items = row.getCell(1).getStringCellValue();
                complianceSpecialItemCheckDO.setItems(items);
            }
            if (row.getCell(2)!=null)
            {
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String counts = row.getCell(2).getStringCellValue();
                complianceSpecialItemCheckDO.setCounts(counts);
            }
            list.add(complianceSpecialItemCheckDO);
        }
        return list;
    }


}
