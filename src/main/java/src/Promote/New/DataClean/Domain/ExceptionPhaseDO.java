package src.Promote.New.DataClean.Domain;

/**
 * 输出异常表中的实体类
 */
public class ExceptionPhaseDO {
    private String ReportName;//报告名称
    private String SheetName;//Sheet名称
    private String ExceptionType;//异常类型
    private String ExceptionInfomation;//异常信息

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

    public String getExceptionInfomation() {
        return ExceptionInfomation;
    }

    public void setExceptionInfomation(String exceptionInfomation) {
        ExceptionInfomation = exceptionInfomation;
    }
}
