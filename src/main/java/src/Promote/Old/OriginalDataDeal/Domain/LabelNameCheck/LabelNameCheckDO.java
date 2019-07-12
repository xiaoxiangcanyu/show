package src.Promote.Old.OriginalDataDeal.Domain.LabelNameCheck;

public class LabelNameCheckDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名
    private String SheetName;//Sheet名
    private String EnglishQuotationCell;//英文引号所在单元格
    private String ReadyCheckInformation;//需排查项目信息

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

    public String getSheetName() {
        return SheetName;
    }

    public void setSheetName(String sheetName) {
        SheetName = sheetName;
    }

    public String getEnglishQuotationCell() {
        return EnglishQuotationCell;
    }

    public void setEnglishQuotationCell(String englishQuotationCell) {
        EnglishQuotationCell = englishQuotationCell;
    }

    public String getReadyCheckInformation() {
        return ReadyCheckInformation;
    }

    public void setReadyCheckInformation(String readyCheckInformation) {
        ReadyCheckInformation = readyCheckInformation;
    }
}
