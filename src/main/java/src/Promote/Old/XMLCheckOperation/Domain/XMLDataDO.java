package src.Promote.Old.XMLCheckOperation.Domain;

/**
 *XML中数据实体类
 */
public class XMLDataDO {
    private String QName;//QName
    private String ContextRef;//contextRef
    private String value;
    private String LabelName;
    private String UnitRef;
    private String Decimals;
    private String Disclosure;

    public String getQName() {
        return QName;
    }

    public void setQName(String QName) {
        this.QName = QName;
    }

    public String getContextRef() {
        return ContextRef;
    }

    public void setContextRef(String contextRef) {
        ContextRef = contextRef;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getUnitRef() {
        return UnitRef;
    }

    public void setUnitRef(String unitRef) {
        UnitRef = unitRef;
    }

    public String getDecimals() {
        return Decimals;
    }

    public void setDecimals(String decimals) {
        Decimals = decimals;
    }

    public String getDisclosure() {
        return Disclosure;
    }

    public void setDisclosure(String disclosure) {
        Disclosure = disclosure;
    }
}
