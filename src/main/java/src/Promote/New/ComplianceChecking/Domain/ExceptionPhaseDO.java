package src.Promote.New.ComplianceChecking.Domain;

/**
 * 输出异常表中的实体类
 */
public class ExceptionPhaseDO {
    private String ReportName;//报告名称
    private String SheetName;//Sheet名称
    private String ExceptionType;//异常类型

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

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
