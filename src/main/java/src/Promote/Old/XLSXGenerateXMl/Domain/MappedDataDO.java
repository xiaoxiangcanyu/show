package src.Promote.Old.XLSXGenerateXMl.Domain;

/**
 * Mapped之后的元素实体类
 */
public class MappedDataDO {
    private String QName;
    private String contextRef;
    private String type;

    //    ===============================================其他三个sheet页======================================================
    private String labelName;
    private String unitRef;
    private String decimals;
    //    ===============================================其他三个sheet页======================================================

    private String text;

    public String getQName() {
        return QName;
    }

    public void setQName(String QName) {
        this.QName = QName;
    }

    public String getContextRef() {
        return contextRef;
    }

    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getUnitRef() {
        return unitRef;
    }

    public void setUnitRef(String unitRef) {
        this.unitRef = unitRef;
    }

    public String getDecimals() {
        return decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
