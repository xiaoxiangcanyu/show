//package src.Promote.Old.XLSXGenerateXMl;
//
//import com.alibaba.fastjson.JSON;
//import src.Promote.Old.XLSXGenerateXMl.Config.ElementMappingDO;
//import src.Promote.Old.XLSXGenerateXMl.Domain.MappedDataDO;
//import src.Promote.Old.XLSXGenerateXMl.Domain.ReadyMappingDataDO;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static src.Promote.Old.XLSXGenerateXMl.Util.FillMappedData.FillMappedDataMethod;
//import static src.Promote.Old.XLSXGenerateXMl.Util.StartGenerateXMLGenerateXML.GenerateXMLMethod;
//import static src.Promote.Old.XLSXGenerateXMl.Util.GetDataFromElementsMappingExcel.GetDataFromElementsMappingExcelMethod;
//import static src.Promote.Old.XLSXGenerateXMl.Util.GetETLDataList.GetETLDataList;
//import static src.Promote.Old.XLSXGenerateXMl.Util.GetSortXSDList.Sortmappeddata;
//import static src.Promote.Old.XLSXGenerateXMl.Util.MappingData.MappingDataMethod;
//import static src.Promote.Old.XLSXGenerateXMl.Util.XsdMapping.XSDMappingaMethod;
//
//
///**
// * 该程序是对数据清洗之后的xlsx进行生成XML文件
// */
//public class XLSXGenerateXML {
//    public static void StartGenerateXMLGenerateXML(String FileDirectory,String ConfigDirectory, String XMlDirectoryPath)throws Exception {
////////        //获取标准xml文件
////        String instancefilebefore = args[0];
////        //清洗之后的excel路径
////        String FileDirectory = args[1];
////        //配置文件路径
////        String ConfigDirectory = args[2];
//        String LabelExcelPath ="";
//        String ElementsMappingExcelPath = "";
//        String CurrencyPath="";
//        String DecimalPath = "";
//        String WeighttransformPath = "";
//        String instancefilebefore ="";
//        String XSDFilePath ="";
//        File Config = new File(ConfigDirectory);
//        File[] files = Config.listFiles();
//        for (File file : files) {
//            String path = file.getAbsolutePath();
//            if (path.contains("LabelName")) {
//                LabelExcelPath = path;
//            } else if (path.contains("ElementMapping")) {
//                ElementsMappingExcelPath = path;
//            } else if (path.contains("Currency")) {
//                CurrencyPath = path;
//            } else if (path.contains("Decimal")) {
//                DecimalPath = path;
//            } else if (path.contains("WeightTransform")) {
//                WeighttransformPath = path;
//            }else if (path.contains("standard_instance")){
//                instancefilebefore =path;
//            }else if (path.contains("nafmii_cln_core_2016-12-31")){
//                XSDFilePath =path;
//            }
//        }
////        //XSD文件路径
////        String XSDFilePath =args[3];
////        //排序的xml文件路径
////        String SortXmlPath =args[4];
////        //生成XML路径
////        String XMlDirectoryPath= args[5];
////        获取标准xml文件
////        String instancefilebefore="C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\standard_instance.xml";//标准的xml模板
//////        清洗之后的excel路径
////        String FileDirectory = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906112006\\Xml&MappingInput";//清洗的excel的文件夹路径
////        配置文件路径
////        String LabelExcelPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\LabelName.xlsx";
////        String ElementsMappingExcelPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\ElementMapping.xlsx";
////        String XSDFilePath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\nafmii_cln_core_2016-12-31.xsd";
////        String SortXmlPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\sort";
////        String CurrencyPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\Currency.xlsx";
////        String DecimalPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\Decimal.xlsx";
////        String WeighttransformPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\WeightTransform.xlsx";
//
//
//        //生成XML路径
////        String XMlPath= "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\output\\test.xml";
//        System.out.println("=============================================================================开始生成实例文档!===============================================================================================================");
//        File file = new File(FileDirectory);
//        File[] files1 = file.listFiles();
//        for (File file1:files1){
//            if (file1.getAbsolutePath().endsWith("xlsx")){
//                System.out.println(file1.getAbsolutePath());
//                String XMlPath = XMlDirectoryPath+"\\"+file1.getAbsolutePath().substring(file1.getAbsolutePath().lastIndexOf("\\")+1,file1.getAbsolutePath().lastIndexOf("."))+".xml";
//                //获取从清洗之后的xlsx获取需要的数据
////                List<ReadyMappingDataDO> list = GetETLDataList(file1.getAbsolutePath(), LabelExcelPath, ElementsMappingExcelPath);
//
//                //获取从配置文件中获取MappingList
//                List<ElementMappingDO> mappinglist = GetDataFromElementsMappingExcelMethod(ElementsMappingExcelPath);
//
//                //将提取的数据集合和MappingList进行Map
//                List<MappedDataDO> mappedList = MappingDataMethod(list, mappinglist);
//
//                //集合数据去重
//                List<MappedDataDO> rmovemappedlist = RemoveDuplicateMapped(mappedList);
//
//                //与xsd文件进行遍历，分类
//                List<MappedDataDO> xsdmappedlist = XSDMappingaMethod(rmovemappedlist, XSDFilePath);
//
//                //xsd排序
////                List<MappedDataDO> sortedList = Sortmappeddata(SortXmlPath, xsdmappedlist);
//
//                //将mapped,xsd好之后的元素进行属性补充，并针对pershare类型的footnote
//                List<MappedDataDO> finallist = FillMappedDataMethod(xsdmappedlist, list, CurrencyPath, DecimalPath, WeighttransformPath);
////                System.out.println(JSON.toJSONString(finallist));
//                //
//                //将处理好的元素数据生成xml文件
//                GenerateXMLMethod(finallist, instancefilebefore, XMlPath);
//            }
//        }
//
//        System.out.println("=============================================================================实例文档生成成功!=======================================================================================================================");
//    }
//
//    /**
//     * 匹配好之后的集合去重
//     *
//     * @param mappedList
//     * @return
//     */
//    public static List<MappedDataDO> RemoveDuplicateMapped(List<MappedDataDO> mappedList) {
//
//        List<MappedDataDO> rmovemappedlist = new ArrayList<>();
//        Set<String> set = new HashSet<>();
//        for (MappedDataDO mappedDataDO : mappedList) {
//            if (set.add(mappedDataDO.getText())) {
//                rmovemappedlist.add(mappedDataDO);
//            }
//        }
//        return rmovemappedlist;
//    }
//
//
//}
