package src.Promote.Old.OriginalDataDeal.Domain.MappingCheck;

/**
 * 校验标签是否全部在库
 */
public class MappingCheckDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名
    private String SheetName;//Sheet名
    private String ExceptionCell;//异常信息所在单元格
    private String UnCheckedProjectInformation;//需排查项目信息

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

    public String getExceptionCell() {
        return ExceptionCell;
    }

    public void setExceptionCell(String exceptionCell) {
        ExceptionCell = exceptionCell;
    }

    public String getUnCheckedProjectInformation() {
        return UnCheckedProjectInformation;
    }

    public void setUnCheckedProjectInformation(String unCheckedProjectInformation) {
        UnCheckedProjectInformation = unCheckedProjectInformation;
    }
}
