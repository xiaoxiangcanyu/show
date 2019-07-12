package src.Promote.New.ComplianceChecking.Domain;

/**
 * KeyLabel中的数据实体类
 */
public class KeyLabelDO {

    private String Sheet;
    private String keyword1;
    private String keyword2;
    private String keyword3;

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }
}
