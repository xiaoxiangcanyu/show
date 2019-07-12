package src.Promote.Old.XLSXGenerateXMl.Domain;


/**
 * xsd的实体类
 */
public class XSDDO {
    private String Qname;
    private String id;
    private String type;
    private String label;

    public String getQname() {
        return Qname;
    }

    public void setQname(String qname) {
        Qname = qname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
