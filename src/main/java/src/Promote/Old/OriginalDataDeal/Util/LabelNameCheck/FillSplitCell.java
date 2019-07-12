package src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;

/**
 * 补填切割的单元格
 */
public class FillSplitCell {
    public static void FillSplitCellMethod(Sheet sheet, int firstrow, int firstcolum, int lastrow , int lastcolum) {

        //判断是行切割还是列切割
        //是行切割，进行 行的补充
        if (firstrow == lastrow && firstcolum == lastcolum) {
            String value = "";
            Row row = sheet.getRow(firstrow);
            Cell standardcell = row.getCell(firstcolum);
            Cell fillingcell = row.getCell(firstcolum+1);
            try {
                 value  = standardcell.getStringCellValue();
            }catch (Exception e){
                value = new BigDecimal(standardcell.getNumericCellValue()) +"";
            }
            fillingcell.setCellValue(value);
        }
        //列切割，进行 列的补充
        else {
            Row row = sheet.getRow(firstrow);
            Cell standardcell = row.getCell(firstcolum);
            for (int i = firstrow+1;i<=lastrow;i++){
                if (standardcell!=null){
                    Cell fillingcell = sheet.getRow(i).getCell(lastcolum);
                    fillingcell.setCellValue(standardcell.getStringCellValue());
                }

            }
        }

    }
}
