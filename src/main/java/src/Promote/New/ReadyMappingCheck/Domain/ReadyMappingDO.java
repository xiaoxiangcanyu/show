package src.Promote.New.ReadyMappingCheck.Domain;

/**
 * 根据对应的sheet从清洗之后的数据中获取的匹配的集合
 */
public class ReadyMappingDO {
    private String sheet;
    private String subject;

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
