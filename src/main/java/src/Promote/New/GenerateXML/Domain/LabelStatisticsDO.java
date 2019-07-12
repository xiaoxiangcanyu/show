package src.Promote.New.GenerateXML.Domain;

/**
 * 标签统计的实体类
 */
public class LabelStatisticsDO {
    private String OrganizationNum;
    private String CompanyName;
    private String ReportYear;
    private String Sheet;
    private String LabelName;
    private String ElementName;

    public String getOrganizationNum() {
        return OrganizationNum;
    }

    public void setOrganizationNum(String organizationNum) {
        OrganizationNum = organizationNum;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getReportYear() {
        return ReportYear;
    }

    public void setReportYear(String reportYear) {
        ReportYear = reportYear;
    }

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getElementName() {
        return ElementName;
    }

    public void setElementName(String elementName) {
        ElementName = elementName;
    }
}
