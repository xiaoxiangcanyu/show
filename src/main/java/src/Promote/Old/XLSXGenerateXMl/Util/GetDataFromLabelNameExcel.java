package src.Promote.Old.XLSXGenerateXMl.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.GenerateXML.Domain.LabelNameDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从LabelName的配置表中
 */
public class GetDataFromLabelNameExcel {
    public static List<LabelNameDO> GetDataFromLabelNameExcelMethod(String LabelExcel) throws IOException, InvalidFormatException {
        List<LabelNameDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(LabelExcel));
        Sheet sheet = workbook.getSheet("SheetIntegrityCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            LabelNameDO labelNameDO = new LabelNameDO();
            //设置单元格类型
            if (row.getCell(1) != null) {
                String sheetname = row.getCell(1).getStringCellValue();
                labelNameDO.setSheetName(sheetname);
                list.add(labelNameDO);
            }
        }
        return list;
    }

}

