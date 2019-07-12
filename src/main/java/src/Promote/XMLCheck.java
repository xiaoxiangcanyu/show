package src.Promote;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * XML VBA 校验
 */
public class XMLCheck {
    public static void getCheckResult(String VBACheckPath) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(VBACheckPath);
        Workbook wb = WorkbookFactory.create(fis);
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = wb.getSheet("Formula");
// suppose your formula is in B3
        for (int i = 1;i<=sheet.getLastRowNum();i++){
            CellReference cellReference = new CellReference("E"+(i+1));
            Row row = sheet.getRow(cellReference.getRow());
            Cell cell = row.getCell(cellReference.getCol());
            CellValue cellValue = evaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    System.out.println("E"+(i+1));
                    System.out.println(cellValue.getBooleanValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    System.out.println("E"+(i+1));
                    System.out.println(cellValue.getNumberValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    System.out.println("E"+(i+1));
                    System.out.println(cellValue.getStringValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;

                // CELL_TYPE_FORMULA will never happen
                case Cell.CELL_TYPE_FORMULA:
                    break;
            }
        }



    }
}
