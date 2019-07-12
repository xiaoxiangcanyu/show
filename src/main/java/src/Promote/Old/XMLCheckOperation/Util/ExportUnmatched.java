package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.Promote.Old.XMLCheckOperation.Domain.XMLDataDO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 输出未匹配的集合的数据
 */
public class ExportUnmatched {
    public static void ExportUnmatchedMethod(List<XMLDataDO> list,String ExportPath) throws IOException {
        //创建表格
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //定义第一个sheet页
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Unmatched");
        //第一个sheet页数据（生成台账表）
        Row row0 = xssfSheet.createRow(0);
        String[] headers = new String[]{"qName", "Context","Value","Unit","Decimal","LabelName"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            xssfSheet.setColumnWidth(i + 2, 5000);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int i = 1;
        for (XMLDataDO xmlDataDO : list) {
            Row row = xssfSheet.createRow(i);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            Cell cell5 = row.createCell(4);
            Cell cell6 = row.createCell(5);
            cell1.setCellValue(xmlDataDO.getQName());
            cell2.setCellValue(xmlDataDO.getContextRef());
            cell3.setCellValue(xmlDataDO.getValue());
            cell4.setCellValue(xmlDataDO.getUnitRef());
            cell5.setCellValue(xmlDataDO.getDecimals());
            cell6.setCellValue(xmlDataDO.getLabelName());
            i++;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(ExportPath);//创建VBA输出流
        xssfWorkbook.write(fileOutputStream);
    }

}
