package src.Promote.New.ComplianceChecking.Domain;

/**
 * 从Compliance-SheetCompletenessCheck表中获取对应的信息
 */
public class ComplianceSheetCompletenessDO {
    private String Sheet;
    private String CompulsoryCells;
    private String FileNameMatchingCell;
    private String ReportType;

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getCompulsoryCells() {
        return CompulsoryCells;
    }

    public void setCompulsoryCells(String compulsoryCells) {
        CompulsoryCells = compulsoryCells;
    }

    public String getFileNameMatchingCell() {
        return FileNameMatchingCell;
    }

    public void setFileNameMatchingCell(String fileNameMatchingCell) {
        FileNameMatchingCell = fileNameMatchingCell;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String reportType) {
        ReportType = reportType;
    }
}
