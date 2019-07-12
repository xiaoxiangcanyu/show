package src.Promote.New.DataClean.Domain;

/**
 * Dulplicate配置文件实体类
 */
public class DulplicateDO {
    private String Sheet;
    private String Items;
    private String key1;
    private String key2;

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
