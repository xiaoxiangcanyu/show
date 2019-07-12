package src.Promote.New.DataClean.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.DataClean.Domain.DulplicateDO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从Dulplicate表中获取what关键词集合
 */
public class GetInformationFromDulplicateConfig {
   public static List<DulplicateDO> GetInformationFromDulplicateConfigMethod(String Excelpath) throws IOException, InvalidFormatException {
       List<DulplicateDO> list = new ArrayList<DulplicateDO>();
       Workbook workbook = WorkbookFactory.create(new FileInputStream(Excelpath));
       Sheet sheet = workbook.getSheet("duplicate");
       for (int i = 1; i <= sheet.getLastRowNum(); i++) {
           Row row = sheet.getRow(i);
           DulplicateDO duplicateJudgeInformation =new DulplicateDO();
           duplicateJudgeInformation.setSheet(row.getCell(0).getStringCellValue());
           duplicateJudgeInformation.setItems(row.getCell(1).getStringCellValue());
           duplicateJudgeInformation.setKey1(row.getCell(2).getStringCellValue());
           duplicateJudgeInformation.setKey2(row.getCell(3).getStringCellValue());
           list.add(duplicateJudgeInformation);
       }
       return list;
   }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        System.out.println(JSON.toJSONString(GetInformationFromDulplicateConfigMethod("C:\\Users\\songyu\\Desktop\\201906210910\\Cleansing-config\\Cleansing-DuplicateConfig.xlsx")));
    }
}
