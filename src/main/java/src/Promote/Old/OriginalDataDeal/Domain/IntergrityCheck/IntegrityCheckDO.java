package src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck;

/**
 * 信息校验完整性的日志实体类
 */
public class IntegrityCheckDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名称
    private String ExceptionType;//异常类型
    private String SheetName;//表名
    private String CellName;//单元格名称
    private String AbnormalInformation;//异常信息


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

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }

    public String getSheetName() {
        return SheetName;
    }

    public void setSheetName(String sheetName) {
        SheetName = sheetName;
    }

    public String getCellName() {
        return CellName;
    }

    public void setCellName(String cellName) {
        CellName = cellName;
    }

    public String getAbnormalInformation() {
        return AbnormalInformation;
    }

    public void setAbnormalInformation(String abnormalInformation) {
        AbnormalInformation = abnormalInformation;
    }
}
