package src.Promote.Old.OriginalDataDeal.Util.DulplicateJudge;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.Old.OriginalDataDeal.Domain.DuplicateJudge.DuplicateJudgeInformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Dulplicate表中获取what关键词集合
 */
public class GetInformationFromDulplicateConfig {
   public static List<DuplicateJudgeInformation> GetInformationFromDulplicateConfig(String Excelpath) throws IOException, InvalidFormatException {
       List<DuplicateJudgeInformation> list = new ArrayList<DuplicateJudgeInformation>();
       Workbook workbook = WorkbookFactory.create(new FileInputStream(Excelpath));
       Sheet sheet = workbook.getSheet("duplicate");
       for (int i = 1; i <= sheet.getLastRowNum(); i++) {
           Row row = sheet.getRow(i);
           DuplicateJudgeInformation duplicateJudgeInformation =new DuplicateJudgeInformation();
           duplicateJudgeInformation.setTable(row.getCell(0).getStringCellValue());
           duplicateJudgeInformation.setWhat(row.getCell(1).getStringCellValue());
           duplicateJudgeInformation.setT1(row.getCell(2).getStringCellValue());
           duplicateJudgeInformation.setT2(row.getCell(3).getStringCellValue());
           list.add(duplicateJudgeInformation);
       }
       return list;
   }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//        GetInformationFromDulplicateConfig("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\01 原始数据处理VBA\\test\\20190531\\config\\DuplicateConfig.xlsx");
//    }
}
