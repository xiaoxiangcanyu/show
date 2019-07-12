package src.Promote.New.ReadyMappingAndGenerateXML;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;

import java.io.IOException;

import static src.Promote.New.GenerateXML.GenerateXML.GenerateXMLTotal;
import static src.Promote.New.ReadyMappingCheck.ReadyMapping.ReadyMappingcheck;

/**
 * 预 Mapping校验和 生成实例文档
 */
public class ReadyMappingAndGenerateXML {
    public static void main(String[] args) throws Exception {
        String RootDirectory =args[0];
//        String RootDirectory = "C:\\Users\\songyu\\Desktop\\XBRL新版\\生成实例文档所需文件";
        String  MappingCentrolSheetPath = "";//ReadyMapping中心表的绝对路径
        //===================================================预 Mapping校验======================================================
        MappingCentrolSheetPath  = ReadyMappingcheck(RootDirectory,MappingCentrolSheetPath);
        //===================================================预 Mapping校验======================================================

        //===================================================生成实例文档======================================================
        GenerateXMLTotal(RootDirectory,MappingCentrolSheetPath);
        //===================================================生成实例文档======================================================


    }
}
