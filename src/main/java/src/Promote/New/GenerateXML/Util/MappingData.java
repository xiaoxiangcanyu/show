package src.Promote.New.GenerateXML.Util;


import src.Promote.New.GenerateXML.Domain.MappedDataDO;
import src.Promote.New.GenerateXML.Domain.ReadyMappingDataDO;
import src.Promote.New.ReadyMappingCheck.Domain.ReadyMappingConfigDO;
import src.Promote.Old.XLSXGenerateXMl.Config.ElementMappingDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 将提取的原始数据与mappingList进行map
 */
public class MappingData {
    public static List<MappedDataDO> MappingDataMethod(List<ReadyMappingDataDO> readymappingdatalist, List<ReadyMappingConfigDO> mappinglist) {
        List<MappedDataDO> mappedDataDOList = new ArrayList<>();
        List<ReadyMappingConfigDO> mappingFailureList = new ArrayList<>();//mapped不成功的集合
        for (ReadyMappingDataDO readyMappingDataDO : readymappingdatalist) {
            String subject = readyMappingDataDO.getSubject();
            String table = readyMappingDataDO.getTable();
            String column = readyMappingDataDO.getColumnum();
            for (ReadyMappingConfigDO elementMappingDO : mappinglist) {
                if ("封面信息".equals(readyMappingDataDO.getTable())) {
//                    System.out.println("Mapping里面的列:"+elementMappingDO.getColumnum().substring(0,1)+",原始文件里面的咧:"+column);
                    if (elementMappingDO.getTable().equals(table) && elementMappingDO.getSubject().equals(subject) && elementMappingDO.getColumnum().substring(0, 1).equals(column)) {
                        MappedDataDO mappedDataDO = new MappedDataDO();
                        mappedDataDO.setQName(elementMappingDO.getCellid());
                        mappedDataDO.setContextRef(elementMappingDO.getContextid());
                        mappedDataDO.setText(readyMappingDataDO.getValue());
                        mappedDataDO.setLabelName(readyMappingDataDO.getLabelname());
                        mappedDataDOList.add(mappedDataDO);
                    } else {
                        mappingFailureList.add(elementMappingDO);
                    }
                } else {
                    if (elementMappingDO.getTable().equals(table) && elementMappingDO.getSubject().equals(subject) && elementMappingDO.getColumnum().equals(column)) {
                        MappedDataDO mappedDataDO = new MappedDataDO();
                        mappedDataDO.setQName(elementMappingDO.getCellid());
                        mappedDataDO.setContextRef(elementMappingDO.getContextid());
                        mappedDataDO.setText(readyMappingDataDO.getValue());
                        mappedDataDO.setLabelName(readyMappingDataDO.getLabelname());
                        mappedDataDOList.add(mappedDataDO);
                    } else {
                        mappingFailureList.add(elementMappingDO);
                    }
                }
            }
        }
        //输出未匹配成功的数据
//        ExportExceptionMappingXslx(mappingFailureList);
        return mappedDataDOList;
    }
}