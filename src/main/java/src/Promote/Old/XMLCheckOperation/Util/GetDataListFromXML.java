package src.Promote.Old.XMLCheckOperation.Util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import src.Promote.Old.XMLCheckOperation.Domain.VBADO;
import src.Promote.Old.XMLCheckOperation.Domain.XMLDataDO;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static src.Promote.Old.XMLCheckOperation.Util.GetDataListFromVBATemplate.GetDataListFromVBATemplateMethod;

/**
 * 从XML里面获取数据集合，为灌数做准备
 */
public class GetDataListFromXML {
    public static Map<String, List> GetDataListFromXMlMethod(String XMLPath, String VBAPath) throws DocumentException, IOException, InvalidFormatException {
//        =================================================数据初始化===========================================================
        Map<String, List> map = new HashMap<>();//创建存放所有sheet页数据的map集合
        List<XMLDataDO> CoverageList = new ArrayList<>();//封面信息表的集合数据
        List<XMLDataDO> ConsolidatedBalanceList = new ArrayList<>();//合并资产负债表的集合数据
        List<XMLDataDO> ConsolidatedProfitList = new ArrayList<>();//合并利润表的集合数据
        List<XMLDataDO> ConsolidatedCashFlowList = new ArrayList<>();//合并现金流量表的集合数据
        List<XMLDataDO> InterestBearingLiabilities = new ArrayList<>();//有息负债总表的集合数据
        List<XMLDataDO> UnmatchedXMLDataList = new ArrayList<>();//未匹配的xml数据集合
//        =================================================数据初始化===========================================================
//        =================================================获取数据=============================================================
        //读取XML文件
        Document doc = new SAXReader().read(new File(XMLPath));
        Element root = doc.getRootElement();
        List<Element> DataList = root.elements();
        List<Element> EffectiveDataList = new ArrayList<>();
        List<XMLDataDO> XMlDataDOList = new ArrayList<>();
        for (Element element : DataList) {
            if (element.getName().contains("Collection")) {
                EffectiveDataList.add(element);
            }
        }
        for (Element element : EffectiveDataList) {
            XMLDataDO xmlDataDO = new XMLDataDO();
            xmlDataDO.setQName(element.getName());
            xmlDataDO.setContextRef(element.attributeValue("contextRef"));
            xmlDataDO.setValue(element.getText());
            if (element.attributeValue("labelName") != null) {
                xmlDataDO.setLabelName(element.attributeValue("labelName"));
            }
            if (element.attributeValue("unitRef") != null) {
                xmlDataDO.setUnitRef(element.attributeValue("unitRef"));
            }
            if (element.attributeValue("decimals") != null) {
                xmlDataDO.setDecimals(element.attributeValue("decimals"));
            }

            XMlDataDOList.add(xmlDataDO);
        }
//        System.out.println(JSON.toJSONString(XMlDataDOList));
//        =================================================获取数据======================================================================
//        =================================================根据表名来分类数据=============================================================
        Map<String, List> VBAMap = GetDataListFromVBATemplateMethod(VBAPath);
        for (XMLDataDO xmlDataDO : XMlDataDOList) {
        String[] vl = new String[]{"CP", "BS", "PL", "CF", "SI"};
            int num = 0;//用于标志未能匹配的数据
            for (int i = 0; i < vl.length; i++) {
            List<VBADO> list = VBAMap.get(vl[i]);
            Set<String> set = new HashSet<>();
            for (VBADO vbado : list) {
                String str  = vbado.getQname();
                String disclosure = vbado.getDisclosure();
                str = str.replace("nafmii-cln:","");
                    if (str.equals(xmlDataDO.getQName())) {
                        String str1 = xmlDataDO.getQName()+xmlDataDO.getValue()+xmlDataDO.getContextRef()+xmlDataDO.getDecimals()+xmlDataDO.getLabelName()+xmlDataDO.getUnitRef();
                        if ( set.add(str1)){
                            xmlDataDO.setDisclosure(disclosure);
                            switch (vl[i]){
                                case "CP":
                                    CoverageList.add(xmlDataDO);
                                    num++;
                                    break;
                                case "BS":
                                    if (xmlDataDO.getLabelName()!=null){
                                        ConsolidatedBalanceList.add(xmlDataDO);
                                        num++;
                                    }else if ("CNYPerShare".equals(xmlDataDO.getUnitRef())){
                                        ConsolidatedBalanceList.add(xmlDataDO);
                                        num++;
                                    }
                                    break;
                                case "PL":
                                    if (xmlDataDO.getLabelName()!=null){
                                        ConsolidatedProfitList.add(xmlDataDO);
                                        num++;
                                    }else if ("CNYPerShare".equals(xmlDataDO.getUnitRef())){
                                        ConsolidatedProfitList.add(xmlDataDO);
                                        num++;
                                    }
                                    break;
                                case "CF":
                                    if (xmlDataDO.getLabelName()!=null){
                                        ConsolidatedCashFlowList.add(xmlDataDO);
                                        num++;
                                    }else if (xmlDataDO.getQName().contains("CollectionCashAndCashEquivalents")){
                                        ConsolidatedCashFlowList.add(xmlDataDO);
                                        num++;
                                    }else if ("CNYPerShare".equals(xmlDataDO.getUnitRef())){
                                        ConsolidatedCashFlowList.add(xmlDataDO);
                                        num++;
                                    }
                                    break;
                                case "SI":
                                    if (xmlDataDO.getLabelName()==null){
                                        InterestBearingLiabilities.add(xmlDataDO);
                                        num++;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                }
            }
        }
        //当这个数匹配完之后未匹配成功，则将输出到错误集合里面
            if (num==0){
                UnmatchedXMLDataList.add(xmlDataDO);
            }
        }
        map.put("CP", CoverageList);
        map.put("BS",ConsolidatedBalanceList);
        map.put("PL",ConsolidatedProfitList);
        map.put("CF",ConsolidatedCashFlowList);
        map.put("SI",InterestBearingLiabilities);
        map.put("Unmatched",UnmatchedXMLDataList);
        map.put("original",XMlDataDOList);
//        =================================================根据表名来分类数据=============================================================
//        System.out.println(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect));
        return map;
    }

    public static void main(String[] args) throws DocumentException, IOException, InvalidFormatException {
        GetDataListFromXMlMethod("C:\\Users\\songyu\\Desktop\\201906182013\\03 Excel标准模板灌数阶段\\DataFillingInput\\134916858-2015-南京高新技术经济开发有限责任公司.xml", "C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\项目完成后的优化\\04 实例文档校验\\校验调试\\Nafmii-FormulaValidate-2014&2015-0119.xlsm");
    }
}
