package src.Promote.New.GenerateXML.Util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import src.Promote.New.GenerateXML.Domain.MappedDataDO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartGenerateXMLGenerateXML {
    /**
     * @param list
     * @param XMlPath
     */
    public static void StartGenerateXMLMethod(List<MappedDataDO> list, String standardXMlPath, String XMlPath) throws DocumentException, IOException {
        List<MappedDataDO> mappedDataDOList = new ArrayList<>();

        //1、读取文件
        Document doc = new SAXReader().read(new File(standardXMlPath));

//2、修改文件
        Element root = doc.getRootElement();
        int flag = 0;//用于判断footnote上面元素的标志
        int flag1 = 0;//用于判断footnote下面元素的标志
        for (MappedDataDO mappedDataDO : list) {
            if ("xbrli:stringItemType".equals(mappedDataDO.getType()) || "xbrli:dateItemType".equals(mappedDataDO.getType()) || "xbrli:gYearItemType".equals(mappedDataDO.getType())) {
                Element element = root.addElement(mappedDataDO.getQName());
                element.addAttribute("contextRef", mappedDataDO.getContextRef());
                element.setText(mappedDataDO.getText());

            } else if ("xbrli:monetaryItemType".equals(mappedDataDO.getType())) {
                Element element = root.addElement(mappedDataDO.getQName());
                if (mappedDataDO.getLabelName() != null) {
                    element.addAttribute("nafmii_cln_model:labelName", mappedDataDO.getLabelName());
                }
                element.addAttribute("unitRef", mappedDataDO.getUnitRef());
                element.addAttribute("decimals", mappedDataDO.getDecimals());
                element.addAttribute("contextRef", mappedDataDO.getContextRef());
                if (mappedDataDO.getText() != null && !"".equals(mappedDataDO.getText())) {
                    element.setText(mappedDataDO.getText());
                }
            } else if ("num:perShareItemType".equals(mappedDataDO.getType())) {
                Element element = root.addElement(mappedDataDO.getQName());
//                System.out.println(mappedDataDO.getQName());
                element.addAttribute("id", "id_footnote_elem_" + (flag + 1));
                element.addAttribute("unitRef", mappedDataDO.getUnitRef());
                element.addAttribute("decimals", mappedDataDO.getDecimals());
                element.addAttribute("contextRef", mappedDataDO.getContextRef());
                if (mappedDataDO.getText() != null && !"".equals(mappedDataDO.getText())) {
                    element.setText(mappedDataDO.getText());
                }
                flag = flag+1;
            }
        }
        for (MappedDataDO mappedDataDO : list) {
            if ("num:perShareItemType".equals(mappedDataDO.getType())) {
                mappedDataDOList.add(mappedDataDO);
            }
        }
        GenerateFootNote(root,flag1,mappedDataDOList);

//3、写出文件
        FileOutputStream out = new FileOutputStream(XMlPath);//指定文件输出的位置
        //指定写出的格式
        OutputFormat format = OutputFormat.createCompactFormat(); //紧凑的格式.去除空格换行.
        //OutputFormat format = OutputFormat.createPrettyPrint(); //格式好的格式.有空格和换行.
        format.setEncoding("utf-8");//2.指定生成的xml文档的编码
        format.setNewlines(true);
        XMLWriter writer = new XMLWriter(out, format);//创建写出对象
        writer.write(doc);//写出对象
        writer.close();//关闭流
    }

    /**
     * 产生footnote
     *
     * @param root
     * @param
     */
    private static int GenerateFootNote(Element root,int flag,List<MappedDataDO> list) {

        Element footnoteLink = root.addElement("link:footnoteLink");
        footnoteLink.addAttribute("xlink:type", "extended");
        footnoteLink.addAttribute("xlink:role", "http://www.xbrl.org/2003/role/link");
        for (MappedDataDO mappedDataDO:list){
            if (mappedDataDO.getLabelName()!=null){
                //loc
                Element loc = footnoteLink.addElement("link:loc");
                loc.addAttribute("xlink:type", "locator");
                loc.addAttribute("xlink:href", "#id_footnote_elem_" + (flag + 1));
                loc.addAttribute("xlink:label", (mappedDataDO.getQName().replace("nafmii-cln:", "")) + "_" + (flag + 1));
                //footnote
                Element footnote = footnoteLink.addElement("link:footnote");
                footnote.addAttribute("xlink:type", "resource");
                footnote.addAttribute("xlink:label", "footnote_" + (flag + 1));
                footnote.addAttribute("xlink:role", "http://www.xbrl.org/2003/role/footnote");
                footnote.addAttribute("xml:lang", "zh");
                footnote.setText(mappedDataDO.getLabelName());
                //footnoteArc
                Element footnoteArc = footnoteLink.addElement("link:footnoteArc");
                footnoteArc.addAttribute("xlink:type", "arc");
                footnoteArc.addAttribute("xlink:arcrole", "http://www.xbrl.org/2003/arcrole/fact-footnote");
                footnoteArc.addAttribute("xlink:from", (mappedDataDO.getQName().replace("nafmii-cln:", "")) + "_" + (flag + 1));
                footnoteArc.addAttribute("xlink:to", "footnote_" + (flag + 1));
                footnoteArc.addAttribute("order", "1.0");

                flag = flag + 1;
            }


        }


        return flag;
    }
}


