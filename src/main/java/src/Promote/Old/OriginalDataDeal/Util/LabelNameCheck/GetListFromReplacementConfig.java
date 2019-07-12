package src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck.ReplacementDO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从replacement config文件中获取数据集合
 */
public class GetListFromReplacementConfig {
    public static List<ReplacementDO> GetListFromReplacementConfig(String replacementconfigpath) throws IOException, InvalidFormatException {
        List<ReplacementDO> list = new ArrayList<ReplacementDO>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(replacementconfigpath));
        Sheet sheet = workbook.getSheet("replacement");
        if (sheet!=null){
            for (int i =1;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                ReplacementDO replacementDO = new ReplacementDO();
//            System.out.println("第"+i+"行");
                //设置单元格类型
                for (int j = 0;j<4;j++){
                    if (row.getCell(j)!=null){
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    }else {
                        row.createCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    }

                }
                String BeforeReplacementStr = row.getCell(0).getStringCellValue().trim();
                String AfterreplacementStr = row.getCell(1).getStringCellValue().trim();
                String ReplacelabelName = row.getCell(2).getStringCellValue();
                String CellReplacement = row.getCell(3).getStringCellValue();
//            System.out.println("替换前内容："+BeforeReplacementStr+",替换后内容:"+AfterreplacementStr+",是否替换labelName:"+ReplacelabelName+",是否单元格完整替换:"+CellReplacement);

                replacementDO.setBeforeReplacementStr(BeforeReplacementStr);
                replacementDO.setAfterreplacementStr(AfterreplacementStr);
                replacementDO.setReplacelabelName(ReplacelabelName);
                replacementDO.setCellReplacement(CellReplacement);
                list.add(replacementDO);
            }
        }

        return list;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        System.out.println(JSON.toJSONString(GetListFromReplacementConfig("C:\\Users\\songyu\\Desktop\\201906210910\\Cleansing-config\\Cleansing-ReplacementConfig.xlsx"),true));
    }
}
