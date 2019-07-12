package src.Promote.New.GenerateXML.Util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import src.Promote.New.GenerateXML.Domain.MappedDataDO;
import src.Promote.Old.XLSXGenerateXMl.Domain.XSDDO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于XSD的mapping工具类
 */
public class XsdMapping {

    //        解析Xsd文件的内容,获取xsd数据集合--------------------------------------------------------------------------》
    public static List<XSDDO> getDataListFromXSD(String XSDPath) throws DocumentException {
        List<XSDDO> xsddoList = new ArrayList<>();
        Document doc = new SAXReader().read(new File(XSDPath));
        Element root = doc.getRootElement();
        List<Element> xsdList = root.elements();
        for (Element element : xsdList) {
            XSDDO xsddo = new XSDDO();
            String id = element.attributeValue("id");
            String type = element.attributeValue("type");

//            System.out.println("QName是" + element.getName() + "的元素的id是" + id + ",类型是" + type);
            xsddo.setQname(element.getName());
            xsddo.setId(id);
            xsddo.setType(type);
            if (xsddo.getId() != null) {
                xsddoList.add(xsddo);
            }
        }
        return xsddoList;
    }

    public static List<MappedDataDO> XSDMappingProgress(List<XSDDO> xsddoList, List<MappedDataDO> mappedDataDOList) {
        List<MappedDataDO> xsdmappedlist = new ArrayList<>();
            for (MappedDataDO mappedDataDO : mappedDataDOList) {
                String qname = mappedDataDO.getQName().replace(":", "_");
                for (XSDDO xsddo : xsddoList) {
                    String id = xsddo.getId();
                    if (id.equals(qname)) {
                        mappedDataDO.setType(xsddo.getType());
                        xsdmappedlist.add(mappedDataDO);
                    }
                }
            }

        return xsdmappedlist;
    }

    public static List<MappedDataDO> XSDMappingaMethod(List<MappedDataDO> mappedList, String XSDFilePath) throws DocumentException {
        List<XSDDO> xsddoList = getDataListFromXSD(XSDFilePath);
        List<MappedDataDO> xsdmappedlist = XSDMappingProgress(xsddoList, mappedList);
//        补充LabelName

        return xsdmappedlist;
    }

}
