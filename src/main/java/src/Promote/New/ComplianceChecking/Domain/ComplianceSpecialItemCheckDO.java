package src.Promote.New.ComplianceChecking.Domain;

/**
 * ComplianceSpecialItemCheck表中的信息实体类
 */
public class ComplianceSpecialItemCheckDO {
    private String Sheet;
    private String Items;
    private String Counts;

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getCounts() {
        return Counts;
    }

    public void setCounts(String counts) {
        Counts = counts;
    }
}
