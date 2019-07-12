package src.Promote.Old.XLSXGenerateXMl.Config;

/**
 * 从ElementMapping表中读取的数据的实体类
 */
public class ElementMappingDO {
    private String table;
    private String subject;
    private String columnum;
    private String cellid;
    private String contextid;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getColumnum() {
        return columnum;
    }

    public void setColumnum(String columnum) {
        this.columnum = columnum;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getContextid() {
        return contextid;
    }

    public void setContextid(String contextid) {
        this.contextid = contextid;
    }
}
