package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import src.Promote.Old.XMLCheckOperation.Domain.VBADO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从VBA的模板里面根据数据表获取QName
 */
public class GetDataListFromVBATemplate {
    public static Map<String,List> GetDataListFromVBATemplateMethod(String VBATemplatePath) throws IOException, InvalidFormatException {
        //        =================================================数据初始化===========================================================
        Map<String,List> map = new HashMap<>();//创建存放所有sheet页数据的map集合
//        =================================================数据初始化===========================================================
        FileInputStream fis = new FileInputStream(VBATemplatePath);
        Workbook wb = WorkbookFactory.create(fis);
        String[] vl = new String[]{"CP","BS","PL","CF","SI"};
        for (int i = 0;i<vl.length;i++)
        {
            Sheet sheet = wb.getSheet(vl[i]);
            List<VBADO> List = new ArrayList<>();//用于存放XML中封面页的数据集合
            for (int j=1;j<=sheet.getLastRowNum();j++){
                Row row = sheet.getRow(j);
                VBADO vbado  = new VBADO();
                if (row!=null)
                {
                    Cell cell = row.getCell(0);
                    if (cell!=null){
                        String cellvalue = cell.getStringCellValue();
//                        System.out.println("表"+vl[i]+"的QName是"+cellvalue);
                        vbado.setQname(cellvalue);
                    }
                    if (!"CP".equals(vl[i])){
                        Cell cell1 = row.getCell(1);
                        if (cell1!=null){
                            String cellvalue = cell1.getNumericCellValue()+"";
                            cellvalue = cellvalue.substring(0,1);
//                        System.out.println("表"+vl[i]+"的Disclosure是"+cellvalue);
                            vbado.setDisclosure(cellvalue);
                        }
                    }

                    if (vbado.getQname()!=null){
                        List.add(vbado);
                    }
                }
            }
            map.put(vl[i],List);
        }
//        System.out.println(JSON.toJSONString(map,true));
        return map;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        GetDataListFromVBATemplateMethod("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\04 实例文档校验\\校验调试");
    }
}
