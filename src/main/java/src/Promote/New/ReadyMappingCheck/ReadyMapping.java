package src.Promote.New.ReadyMappingCheck;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingControlSheetDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingExceptionPhaseDO;
import src.Promote.New.ReadyMappingCheck.Util.ReadyMappingBaseUil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Mapping预校验
 */
public class ReadyMapping extends ReadyMappingBaseUil {
    //进行预Mapping校验
    public static void ReadyMappingMethod(Workbook workbook, String CompanyName, String ReadyNappingConfigDirectory, List<ReadyMappingControlSheetDO> readymappingcontrolsheetlist, List<ReadyMappingExceptionPhaseDO> readymappingexceptionphasedolist, String ReadyMappingSuccessPath, String MappingExceptionPath) throws Exception {
        int BeforeReadyMapping = readymappingexceptionphasedolist.size();
        String Year = GetYear(CompanyName);
//        System.out.println("Mapping文件夹的位置："+ReadyNappingConfigDirectory);
        //=============================================获取要进行匹配的表的列表=============================================================================
        List<String> SheetList = GetReadyMappingSheetList(ReadyNappingConfigDirectory);
//        =======================================================================开始进行Mapping预校验================================================================================

        //=============================================获取对应年度的Mapping表里面的数据=============================================================================
        List<ReadyMappingConfigDO> list = GetReadyMappingDataByYear(Year, ReadyNappingConfigDirectory);
        if (list.size()==0){
            throw new Exception("没有匹配到对应年份的ElementMapping文件");
        }
        //=============================================获取对应年度的Mapping表里面的数据=============================================================================

        //=============================================获取对应原始文件表里面的数据=============================================================================
        List<ReadyMappingDO> ReadyMappingList = GetMappingList(SheetList, workbook);
        //=============================================获取对应原始文件表里面的数据=============================================================================


        //=========================================================进行预匹配=========================================================================================
        List<ReadyMappingDO> UnMappedList = GetUnMappedList(ReadyMappingList, list);
//        System.out.println(JSON.toJSONString(UnMappedList));
        //=========================================================进行预匹配=========================================================================================

        //=========================================================将预匹配不到的集合添加到异常报告中=========================================================================================
        for (ReadyMappingDO readyMappingDO:UnMappedList){
            ReadyMappingExceptionPhaseDO readyMappingExceptionPhaseDO = new ReadyMappingExceptionPhaseDO();
            readyMappingExceptionPhaseDO.setSheetName(readyMappingDO.getSheet());
            readyMappingExceptionPhaseDO.setReportName(CompanyName);
            readyMappingExceptionPhaseDO.setUnmatchedItems(readyMappingDO.getSubject());
            readymappingexceptionphasedolist.add(readyMappingExceptionPhaseDO);
        }
        //=========================================================将预匹配不到的集合添加到异常报告中=========================================================================================

        //==============================================================输出Mapping预校验的文件====================================================================================
        if (BeforeReadyMapping == readymappingexceptionphasedolist.size()) {
            ExportMappedSource(workbook, ReadyMappingSuccessPath);
        } else {
            ExportUnMappedSource(workbook, MappingExceptionPath);

        }
//        //         ==============================================================输出Mapping预校验的文件====================================================================================
    }

    /**
     * @param readyMappingList
     * @param list
     * @return
     */
    private static List<ReadyMappingDO> GetUnMappedList(List<ReadyMappingDO> readyMappingList, List<ReadyMappingConfigDO> list) {
        Set<String> set = new HashSet<>();
        List<ReadyMappingDO> MappedList = new ArrayList<>();
        for (ReadyMappingDO readyMappingDO : readyMappingList) {
            for (ReadyMappingConfigDO readyMappingConfigDO : list) {
                if (readyMappingDO.getSheet().equals(readyMappingConfigDO.getTable()) && readyMappingDO.getSubject().equals(readyMappingConfigDO.getSubject())) {
                    if (set.add(readyMappingDO.getSubject()+readyMappingDO.getSheet())){
                        MappedList.add(readyMappingDO);
                    }
                }
            }
        }
//        System.out.println(JSON.toJSONString(readyMappingList,true));
        //原有集合删除掉匹配成功的集合的元素
        for (Iterator<ReadyMappingDO> iterator = readyMappingList.iterator();iterator.hasNext();){
            ReadyMappingDO readyMappingDO1 = iterator.next();
                for (ReadyMappingDO readyMappingDO:MappedList){
                    if (readyMappingDO1.getSubject().trim().equals(readyMappingDO.getSubject().trim()) && readyMappingDO1.getSheet().equals(readyMappingDO.getSheet())){
//                        System.out.println(readyMappingDO1.getSheet()+readyMappingDO1.getSubject());
                        try {
                            iterator.remove();
//                            System.out.println("删掉"+readyMappingDO1.getSheet()+"表的"+readyMappingDO1.getSubject());
                        }catch (Exception e){
                            continue;
                        }

                    }
                }


        }
        return readyMappingList;
    }

    public static String ReadyMappingcheck(String DirectoryPath,String MappingCentrolSheetPath) throws Exception {
        System.out.println("====================================================开始预Mapping校验===============================================================================================");
//        String DirectoryPath = "C:\\Users\\songyu\\Desktop\\XBRL新版\\生成实例文档所需文件";//根目录文件夹
    String InputDirectory = "";//进行mapping预处理的Excel文件夹
    String InputFilePath = "";//进行mapping预处理的Excel绝对路径

    //        ===================================================ReadyMapping的配置文件路径=====================================================================
    String ReadyNappingConfigDirectory = "";
    String ReadyNappingConfigFilePath = "";
//        ===================================================ReadyMapping的配置文件路径=====================================================================


    //        ===================================================ReadyMapping的输出文件路径=====================================================================
    String TransformOutputDirectory = "";//Transform-输出文件夹

    String ReadyMappingSuccessDirecory = "";//ReadyMapping预处理成功的文件夹
    String ReadyMappingSuccessPath = "";//ReadyMapping预处理成功的文件绝对路径

    String MappingExceptionDirectory = "";//ReadyMapping异常输出的文件夹
    String MappingExceptionPath = "";//ReadyMapping异常输出的绝对路径
    String MappingExceptionReportDirectory = "";//ReadyMapping异常输出的日志的父文件夹
    String MappingExceptionReportPath = "";//ReadyMapping异常输出的日志的绝对路径


    //        ===================================================ReadyMapping的输出文件路径===========================================================================
    String CompanyName = "";

    File file = new File(DirectoryPath);
    File[] files = file.listFiles();
//===========================================================遍历文件夹，获取各文件真实路径==========================================================================
        for (File file1 : files) {
        if (file1.getAbsolutePath().contains("MappingInput")) {
            InputDirectory = file1.getAbsolutePath();
        } else if (file1.getAbsolutePath().contains("Transform-config")) {
            ReadyNappingConfigDirectory = file1.getAbsolutePath();
        } else if (file1.getAbsolutePath().contains("Transform-输出文件")) {
            MappingCentrolSheetPath = file1.getAbsolutePath()+"\\"+"Control Sheet-Phase3.xlsx";
            TransformOutputDirectory = file1.getAbsolutePath();
            File file3 = new File(TransformOutputDirectory);
            File[] files1 = file3.listFiles();
            for (File file2 : files1) {
                if (file2.getAbsolutePath().contains("ReadyMappingOuput")) {
                    ReadyMappingSuccessDirecory = file2.getAbsolutePath();
                } else if (file2.getAbsolutePath().contains("Exception-Phase3")) {
                    MappingExceptionReportDirectory = file2.getAbsolutePath();
                    MappingExceptionReportPath = MappingExceptionReportDirectory + "\\Exception-Phase3-Mapping.xlsx";
                    MappingExceptionDirectory = MappingExceptionReportDirectory + "\\Failed Souce-Mapping";
                }
            }
        }
    }
        //Exception文件夹每次更新前都删除和清空
        File ExceptionFile = new File(MappingExceptionDirectory);
        File[] Exceptionfiles = ExceptionFile.listFiles();
        for (File Exceptionfile:Exceptionfiles){
            Exceptionfile.delete();
        }
    //===========================================================遍历文件夹，获取各文件真实路径===============================================================================
    List<ReadyMappingControlSheetDO> readymappingcontrolsheetlist = new ArrayList<>();
    List<ReadyMappingExceptionPhaseDO> readymappingexceptionphasedolist = new ArrayList<>();
    //===========================================================遍历输入文件夹，开始进行Mapping预校验=========================================================================
    File InputFileDiretory = new File(InputDirectory);
    File[] files1 = InputFileDiretory.listFiles();
        for (File file1 : files1) {
        String inputexcelpath = file1.getAbsolutePath();
        CompanyName = inputexcelpath.substring(inputexcelpath.lastIndexOf("\\") + 1, inputexcelpath.lastIndexOf("."));
        Workbook workbook = WorkbookFactory.create(new FileInputStream(inputexcelpath));//获取待处理的excel文件流

        ReadyMappingCentralSheetInitialize(readymappingcontrolsheetlist, CompanyName);//初始化中心表的数据
//  ======================================输出文件路径==================================================================================
        ReadyMappingSuccessPath = ReadyMappingSuccessDirecory + "\\" + CompanyName + ".xlsx";
        MappingExceptionPath = MappingExceptionDirectory + "\\" + CompanyName + ".xlsx";
//  ======================================输出文件路径==================================================================================
        System.out.println(CompanyName);
        ReadyMappingMethod(workbook, CompanyName, ReadyNappingConfigDirectory, readymappingcontrolsheetlist, readymappingexceptionphasedolist, ReadyMappingSuccessPath, MappingExceptionPath);
    }
    //===========================================================遍历文件夹，开始进行Mapping预校验=============================================================================
    //==============================================================输出异常报告====================================================================================
    ExportReadyMappingCleanExceptionPhaseReport(readymappingexceptionphasedolist, MappingExceptionReportPath);
    //==============================================================输出异常报告====================================================================================

    //==============================================================输出控制表======================================================================================================
    CheckReadyMapping(readymappingexceptionphasedolist, readymappingcontrolsheetlist);//校验合规性是否通过
    ExportReadyMappingControlSheetPhase(readymappingcontrolsheetlist, MappingCentrolSheetPath);
    //==============================================================输出控制表======================================================================================================
        System.out.println("====================================================预Mapping校验结束===============================================================================================");
    return MappingCentrolSheetPath;
    }


}
