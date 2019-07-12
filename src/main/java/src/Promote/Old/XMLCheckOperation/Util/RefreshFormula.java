package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;

/**
 * 更新公式的util
 */
public class RefreshFormula {
    public static void RefreshFormulaMethod(Workbook wb, Sheet sheet) {

// suppose your formula is in B3
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            CellReference cellReference = new CellReference("E" + (i + 1));
            Row row = sheet.getRow(cellReference.getRow());
            Cell cell = row.getCell(cellReference.getCol());
            wb.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell);
        }

    }
}

