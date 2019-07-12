package src.Promote.New.ComplianceChecking.Util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从校验完整性配置文件中获取表的数组
 */
public class GetInformationFromIntergrityCheck {
    public static List<String> GetInformationFromIntergrityCheck(String excelPath) throws IOException, InvalidFormatException {
        List<String> list = new ArrayList<String>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet("SheetIntegrityCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String SheetName = row.getCell(0).getStringCellValue();
            list.add(SheetName);
        }
        return list;
    }
    public static List<String> GetSheetLabelNameInformationFromIntergrityCheck(String excelPath) throws IOException, InvalidFormatException {
        List<String> list = new ArrayList<String>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet("SheetIntegrityCheck");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row!=null){
                if (row.getCell(1)!=null){
                    String SheetName = row.getCell(1).getStringCellValue();
                    list.add(SheetName);
                }
            }

        }
        return list;
    }
    public static void main(String[] args) throws IOException, InvalidFormatException {
        String excelPath = "C:\\Users\\songyu\\Desktop\\XBRL新版\\生成实例文档所需文件\\Transform-config\\Compliance-SheetIntegrityCheck.xlsx";
        List<String> list = GetInformationFromIntergrityCheck(excelPath);
        System.out.println(JSON.toJSONString(list));
    }
}
