package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.ComplianceChecking.Domain.ComplianceCellValueDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从ComplianceCellValueSheet中获取信息
 */
public class GetInformationFromComplianceCellValueSheet {
    public static List<ComplianceCellValueDO> GetInformationFromComplianceCellValueSheetMethod(String InformationFromComplianceCellValueSheetPath) throws IOException, InvalidFormatException {
        List<ComplianceCellValueDO> list = new ArrayList<ComplianceCellValueDO>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(InformationFromComplianceCellValueSheetPath));
        Sheet sheet = workbook.getSheet("SheetCellComparison");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ComplianceCellValueDO complianceCellValueDO = new ComplianceCellValueDO();
            if (row.getCell(0)!=null)
            {
                String sheetname = row.getCell(0).getStringCellValue();
                complianceCellValueDO.setSheet(sheetname);
            }
            if (row.getCell(1)!=null)
            {
                String cell1 = row.getCell(1).getStringCellValue();
                complianceCellValueDO.setCell_1(cell1);
            }
            if (row.getCell(2)!=null)
            {
                String cell2 = row.getCell(2).getStringCellValue();
                complianceCellValueDO.setCell_2(cell2);
            }
            list.add(complianceCellValueDO);
        }
        return list;
    }


}
