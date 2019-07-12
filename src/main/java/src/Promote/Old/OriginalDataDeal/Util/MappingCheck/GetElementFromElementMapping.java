package src.Promote.Old.OriginalDataDeal.Util.MappingCheck;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.Old.OriginalDataDeal.Domain.MappingCheck.MappingConfigDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从ElementMapping中提取信息集合
 */
public class GetElementFromElementMapping {
    public static List<MappingConfigDO> getElementFromElementMapping(String excelPath) throws IOException, InvalidFormatException {
        List<MappingConfigDO> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet("Element ReadyMappingCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            MappingConfigDO mappingConfigDO = new MappingConfigDO();
            //设置单元格类型
            for (int j = 0; j < 2; j++) {
                if (row.getCell(j) != null) {
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                } else {
                    row.createCell(j).setCellType(Cell.CELL_TYPE_STRING);
                }


            }
            String A = row.getCell(0).getStringCellValue();
            String B = row.getCell(1).getStringCellValue();
            mappingConfigDO.setTable(A);
            mappingConfigDO.setValue(B);
//            System.out.println("第" + i + "行"+"A列是:"+A+",B列是:"+B);
            list.add(mappingConfigDO);
        }
        return list;
    }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//        getElementFromElementMapping("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\01 原始数据处理VBA\\test\\20190531\\config\\ElementMapping.xlsx");
//    }
}
