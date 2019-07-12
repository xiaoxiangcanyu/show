package src.Promote.Old.OriginalDataDeal.Domain.CoverCheck;

public class CoverCheckDO {
    private String OrganizationNumber;//组织机构编号
    private String CompanyName;//公司名
    private String UnfilledCell;//未填列项目所在单元格
    private String Unfilledproject;//未填列项目

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

    public String getUnfilledCell() {
        return UnfilledCell;
    }

    public void setUnfilledCell(String unfilledCell) {
        UnfilledCell = unfilledCell;
    }

    public String getUnfilledproject() {
        return Unfilledproject;
    }

    public void setUnfilledproject(String unfilledproject) {
        Unfilledproject = unfilledproject;
    }
}
