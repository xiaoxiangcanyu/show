package src.Promote.New.ComplianceChecking.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCoverageKeyCellPosition {
    /**
     * 从Compliance-SheetCompletenessCheck.xlsx中获取封面页关键信息的单元格位置
     * @param compliancesheetcompletenesscheckpath
     * @return
     */
    public static List<String> getCoverageKeyCellPositionMethod(String compliancesheetcompletenesscheckpath) throws IOException, InvalidFormatException {
        List<String> list = new ArrayList<String>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(compliancesheetcompletenesscheckpath));
        Sheet sheet = workbook.getSheet("SheetCompletenessCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(2)!=null)
            {
                String sheetname = row.getCell(2).getStringCellValue();
                list.add(sheetname);
            }
        }
        return list;
    }


}
