package src.Promote.New.ReadyMappingCheck.Domain;

/**
 * 预校验Mapping的异常表的数据实体类
 */
public class ReadyMappingExceptionPhaseDO {
    private String ReportName;
    private String SheetName;
    private String UnmatchedItems;

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public String getSheetName() {
        return SheetName;
    }

    public void setSheetName(String sheetName) {
        SheetName = sheetName;
    }

    public String getUnmatchedItems() {
        return UnmatchedItems;
    }

    public void setUnmatchedItems(String unmatchedItems) {
        UnmatchedItems = unmatchedItems;
    }
}
