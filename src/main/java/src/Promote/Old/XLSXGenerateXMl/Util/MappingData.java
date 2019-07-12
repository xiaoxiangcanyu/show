package src.Promote.Old.XLSXGenerateXMl.Util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Element;
import src.Promote.Old.XLSXGenerateXMl.Config.ElementMappingDO;
import src.Promote.Old.XLSXGenerateXMl.Domain.MappedDataDO;
import src.Promote.Old.XLSXGenerateXMl.Domain.ReadyMappingDataDO;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将提取的原始数据与mappingList进行map
 */
public class MappingData {
    public static List<MappedDataDO> MappingDataMethod(List<ReadyMappingDataDO> readymappingdatalist, List<ElementMappingDO> mappinglist){
        List<MappedDataDO> mappedDataDOList = new ArrayList<>();
        List<ElementMappingDO> mappingFailureList = new ArrayList<>();//mapped不成功的集合
        for (ReadyMappingDataDO readyMappingDataDO:readymappingdatalist){
            String subject = readyMappingDataDO.getSubject();
            String table = readyMappingDataDO.getTable();
            String column = readyMappingDataDO.getColumnum();
            for (ElementMappingDO elementMappingDO:mappinglist){
                if ("封面信息".equals(readyMappingDataDO.getTable())){
//                    System.out.println("Mapping里面的列:"+elementMappingDO.getColumnum().substring(0,1)+",原始文件里面的咧:"+column);
                    if (elementMappingDO.getTable().equals(table) && elementMappingDO.getSubject().equals(subject) && elementMappingDO.getColumnum().substring(0,1).equals(column)){
                        MappedDataDO mappedDataDO = new MappedDataDO();
                        mappedDataDO.setQName(elementMappingDO.getCellid());
                        mappedDataDO.setContextRef(elementMappingDO.getContextid());
                        mappedDataDO.setText(readyMappingDataDO.getValue());
                        mappedDataDOList.add(mappedDataDO);
                    }else {
                        mappingFailureList.add(elementMappingDO);
                    }
                }else {
                    if (elementMappingDO.getTable().equals(table) && elementMappingDO.getSubject().equals(subject)){
                        MappedDataDO mappedDataDO = new MappedDataDO();
                        mappedDataDO.setQName(elementMappingDO.getCellid());
                        mappedDataDO.setContextRef(elementMappingDO.getContextid());
                        mappedDataDO.setText(readyMappingDataDO.getValue());
                        mappedDataDOList.add(mappedDataDO);
                    }else {
                        mappingFailureList.add(elementMappingDO);
                    }
                }
            }
        }
        //输出未匹配成功的数据
//        ExportExceptionMappingXslx(mappingFailureList);
        return mappedDataDOList;
    }

//    /**
//     * 输出为匹配成功的数据
//     * @param mappingFailureList
//     */
//    private static void ExportExceptionMappingXslx(List<ElementMappingDO> mappingFailureList) {
//        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
//
//    }

}
