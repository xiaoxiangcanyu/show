package src.Promote.New.GenerateXML.Domain;

/**
 * 生成XML中心表的数据
 */
public class GenerateXMLExceptionDO {
    private String ReportName;
    private String ElementName;
    private String Context;
    private String Decimal;
    private String Unit;

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public String getElementName() {
        return ElementName;
    }

    public void setElementName(String elementName) {
        ElementName = elementName;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getDecimal() {
        return Decimal;
    }

    public void setDecimal(String decimal) {
        Decimal = decimal;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
