package src.Promote.New.DataClean.Domain;

/**
 * 中心表的数据实体类
 */
public class ControlSheetDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名称
    private String ReportYear;//报告年度
    private String ReportType;//报告类型
    private String IsChecked;//是否通过数据清洗

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