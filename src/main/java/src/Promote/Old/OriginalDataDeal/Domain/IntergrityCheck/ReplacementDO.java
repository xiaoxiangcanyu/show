package src.Promote.Old.OriginalDataDeal.Domain.IntergrityCheck;

public class ReplacementDO {
    private String BeforeReplacementStr;//替换前内容
    private String AfterreplacementStr;//替换后内容
    private String ReplacelabelName;//是否替换labelName
    private String CellReplacement;//是否单元格完整替换

    public String getBeforeReplacementStr() {
        return BeforeReplacementStr;
    }

    public void setBeforeReplacementStr(String beforeReplacementStr) {
        BeforeReplacementStr = beforeReplacementStr;
    }

    public String getAfterreplacementStr() {
        return AfterreplacementStr;
    }

    public void setAfterreplacementStr(String afterreplacementStr) {
        AfterreplacementStr = afterreplacementStr;
    }

    public String getReplacelabelName() {
        return ReplacelabelName;
    }

    public void setReplacelabelName(String replacelabelName) {
        ReplacelabelName = replacelabelName;
    }

    public String getCellReplacement() {
        return CellReplacement;
    }

    public void setCellReplacement(String cellReplacement) {
        CellReplacement = cellReplacement;
    }
}
