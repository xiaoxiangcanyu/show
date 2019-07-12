package src.Promote.Old.XLSXGenerateXMl.Util;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import src.Promote.Old.XLSXGenerateXMl.Domain.MappedDataDO;
import src.Promote.Old.XLSXGenerateXMl.Domain.XSDDO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 从5个xsd文件中获取排序的数据集合
 */
public class GetSortXSDList {
    public static List<XSDDO> GetSortXSDListMethod(String xsdsortpath) throws DocumentException {
        List<XSDDO> xsddoList = new ArrayList<>();
        File filedirectory = new File(xsdsortpath);
        File[] files = filedirectory.listFiles();
        for (File file:files){
            String xsdpath = file.getAbsolutePath();
            if (xsdpath.endsWith(".xml")){
                Document doc = new SAXReader().read(new File(xsdpath));
                Element root = doc.getRootElement();
                Element presentationLink = root.element("presentationLink");
                List<Element> locelements  = presentationLink.elements("loc");
                for (Element element:locelements){
                    XSDDO xsddo = new XSDDO();
                    xsddo.setLabel(element.attributeValue("label"));
                    xsddoList.add(xsddo);
                }
            }
        }
        return  xsddoList;
    }
    public static List<MappedDataDO> Sortmappeddata(String sortxmlpath,List<MappedDataDO> list) throws DocumentException {
        List<XSDDO> xsddoList = GetSortXSDListMethod(sortxmlpath);
        List<MappedDataDO> finalList = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (XSDDO xsddo:xsddoList){
            String label = xsddo.getLabel();
            for (MappedDataDO mappedDataDO:list){
                if (mappedDataDO.getQName().contains(label)){
                    if (set.add(mappedDataDO.getText())){
                        finalList.add(mappedDataDO);
                    }
                }
            }
        }
        return finalList;
    }
//    public static void main(String[] args) throws DocumentException {
//        String XSDPath = "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\config\\实例文档生成\\sort";
//        List<XSDDO> xsddoList = GetSortXSDListMethod(XSDPath);
//        List<MappedDataDO> list = sortmappeddata(xsddoList,)
//        System.out.println(JSON.toJSONString(xsddoList));
//    }
}
