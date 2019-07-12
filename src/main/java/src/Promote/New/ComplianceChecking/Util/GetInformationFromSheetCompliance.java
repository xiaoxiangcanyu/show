package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.ComplianceChecking.Domain.ComplianceSheetCompletenessDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Compliance-SheetCompletenessCheck表中获取数据
 */
public class GetInformationFromSheetCompliance {
    public static List<ComplianceSheetCompletenessDO> GetInformationFromComplianceSheetCompletenessCheckSheetMethod(String complianceSheetCompletenessCheckConfigPath) throws IOException, InvalidFormatException {
        List<ComplianceSheetCompletenessDO> list = new ArrayList<ComplianceSheetCompletenessDO>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(complianceSheetCompletenessCheckConfigPath));
        Sheet sheet = workbook.getSheet("SheetCompletenessCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ComplianceSheetCompletenessDO complianceSheetCompletenessDO = new ComplianceSheetCompletenessDO();
            if (row.getCell(0)!=null)
            {
                String sheetname = row.getCell(0).getStringCellValue();
                complianceSheetCompletenessDO.setSheet(sheetname);
            }
            if (row.getCell(1)!=null)
            {
                String compulsorycells = row.getCell(1).getStringCellValue();
                complianceSheetCompletenessDO.setCompulsoryCells(compulsorycells);
            }
            if (row.getCell(2)!=null)
            {
                String filenamematchingcell = row.getCell(2).getStringCellValue();
                complianceSheetCompletenessDO.setFileNameMatchingCell(filenamematchingcell);
            }
            if (row.getCell(3)!=null)
            {
                String reporttype = row.getCell(3).getStringCellValue();
                complianceSheetCompletenessDO.setReportType(reporttype);
            }
            list.add(complianceSheetCompletenessDO);
        }
        return list;
    }

}
