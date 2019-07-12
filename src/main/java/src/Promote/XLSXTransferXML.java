//package src.Promote;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;
//
//import static src.Promote.Old.OriginalDataDeal.OriginalDataDeal.OriginalDataDeal;
//import static src.Promote.Old.XLSXGenerateXMl.XLSXGenerateXML.StartGenerateXMLGenerateXML;
//
///**
// *XLSX 数据清洗生成XML完整流程
// */
//public class XLSXTransferXML {
//    public static void main(String[] args) throws Exception {
//        String CurrentDirectory = args[0];
//        String OriginalSource = "";
//        String ExceptionReport ="";
//        String DataCleansingFailed ="";
//        String MappingInput = "";
//        String FillingInput ="";
//        String DatacleanConfig = "";
//        String GenerateXmlConfig = "";
////        String OriginalSource = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\OriginalSource";//待清洗的Xlsx的文件夹
//        //=============================配置文件===========================
////        String DatacleanConfig = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\DataCleanConfig";//数据清洗的配置文件夹
////        String GenerateXmlConfig = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\GenerateXMLConfig";
//        //=============================配置文件===========================
////        String ExceptionReport = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\ExceptionReport";//生成清洗异常报告的文件夹
////        String DataCleansingFailed = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\DataCleansingFailed";//数据异常输出的文件夹
////        String MappingInput ="C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\Xml&MappingInput";//清洗成功无异常的文件夹
////        String FillingInput = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906130000\\FillingInput";//生成xml的文件夹
////        String CurrentDirectory = "C:\\Users\\songyu\\Desktop\\程序包示例-20190613";
//        File file = new File(CurrentDirectory);
//        File[] files = file.listFiles();
//        for (File file1:files){
//            if (file1.isDirectory()){
//                if (file1.getAbsolutePath().contains("01 数据初始校验及清洗阶段")){
//                    String DatacleanDirectory = file1.getAbsolutePath();
//                    ExceptionReport =file1.getAbsolutePath();
//
//                    File DatacleanFileDirectory = new File(DatacleanDirectory);
//                    File[] DatacleanFileDirectoryFiles = DatacleanFileDirectory.listFiles();
//                    for (File DatacleanFileDirectoryFile:DatacleanFileDirectoryFiles){
//                        if (DatacleanFileDirectoryFile.getAbsolutePath().contains("DataCleanConfig")){
//                            DatacleanConfig = DatacleanFileDirectoryFile.getAbsolutePath();
//                        }
//                        else if (DatacleanFileDirectoryFile.getAbsolutePath().contains("OriginalSource")){
//                            OriginalSource = DatacleanFileDirectoryFile.getAbsolutePath();
//                        }
//                        else if (DatacleanFileDirectoryFile.getAbsolutePath().contains("DataCleansingFailed")){
//                            DataCleansingFailed= DatacleanFileDirectoryFile.getAbsolutePath();
//                        }
//
//                    }
//
//                }else if (file1.getAbsolutePath().contains("02 实例文档生成阶段")){
//                    String GenerateXMLDirectory = file1.getAbsolutePath();
//                    File GenerateXMLFileDirectory  = new File(GenerateXMLDirectory);
//                    File[] GenerateXMLFileDirectoryFiles = GenerateXMLFileDirectory.listFiles();
//                    for (File GenerateXMLFileDirectoryFile:GenerateXMLFileDirectoryFiles){
//                        if (GenerateXMLFileDirectoryFile.getAbsolutePath().contains("GenerateXMLConfig")){
//                            GenerateXmlConfig = GenerateXMLFileDirectoryFile.getAbsolutePath();
//                        }else if (GenerateXMLFileDirectoryFile.getAbsolutePath().contains("Xml&MappingInput")){
//                            MappingInput = GenerateXMLFileDirectoryFile.getAbsolutePath();
//                        }
//                    }
//                }else if (file1.getAbsolutePath().contains("03 Excel标准模板灌数阶段")){
//                    String XMLFilledirectory = file1.getAbsolutePath();
//                    File XMLFilledFileDirectory = new File(XMLFilledirectory);
//                    File[] XMLFilledFileDirectoryFiles = XMLFilledFileDirectory.listFiles();
//                    for (File XMLFilledFileDirectoryFile:XMLFilledFileDirectoryFiles){
//                        if (XMLFilledFileDirectoryFile.getAbsolutePath().contains("DataFillingInput")){
//                            FillingInput = XMLFilledFileDirectoryFile.getAbsolutePath();
//                        }
//                    }
//                }
//            }
//        }
//        //===================================================数据清洗阶段===============================================================
//        OriginalDataDeal(OriginalSource,DatacleanConfig,ExceptionReport,MappingInput,DataCleansingFailed);
//        //===================================================数据清洗阶段===============================================================
//
//        //=====================================================生成XML=================================================================
//        StartGenerateXMLGenerateXML(MappingInput,GenerateXmlConfig,FillingInput);
//        //======================================================生成XML================================================================
//    }
//}
