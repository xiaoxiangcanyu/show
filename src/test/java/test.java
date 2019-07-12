import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class test {
    public static void main(String[] args) throws DocumentException, IOException {
        //1、读取文件
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("students");
        root.addAttribute("sex","男");
        root.addAttribute("age","18");
        root.addAttribute("grade","98");
        root.setText("宋宇");
        Element height = root.addElement("height");
        height.addAttribute("value","198cm");
        Attribute Height = height.attribute("value");
        Height.setValue("200cm");

        height.setText("身高");
        Element weight = root.addElement("weight");
        weight.setText("体重");
        weight.addAttribute("value","200");
        document.add(root);



        //2、写出文件
        FileOutputStream out = new FileOutputStream("C:\\Users\\songyu\\Desktop\\xbrl\\北金所XBRL实例文档转录一期项目\\201906051747\\test\\test\\1.xml");//指定文件输出的位置
        //指定写出的格式
        OutputFormat format = OutputFormat.createCompactFormat(); //紧凑的格式.去除空格换行.
        //OutputFormat format = OutputFormat.createPrettyPrint(); //格式好的格式.有空格和换行.
        format.setEncoding("utf-8");//2.指定生成的xml文档的编码
        XMLWriter writer = new XMLWriter(out, format);//创建写出对象
        writer.write(document);//写出对象
        writer.close();//关闭流
    }

}
