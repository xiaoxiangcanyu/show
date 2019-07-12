package src.Promote.New.GenerateXML.Domain;

public class GenerateXMLControlSheetDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名称
    private String ReportYear;//报告年度
    private String ReportType;//报告类型
    private String IsChecked;//是否通过预Mapping校验

    public String getOrganizationNumber() {
        return OrganizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        OrganizationNumber = organizationNumber;
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

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String reportType) {
        ReportType = reportType;
    }

    public String getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(String isChecked) {
        IsChecked = isChecked;
    }
}
