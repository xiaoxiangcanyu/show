package src.Promote.New.ComplianceChecking.Domain;

/**
 * 从ComplianceCellValueSheet中获取的信息
 */
public class ComplianceCellValueDO {
    private String Sheet;
    private String Cell_1;
    private String Cell_2;

    public String getSheet() {
        return Sheet;
    }

    public void setSheet(String sheet) {
        Sheet = sheet;
    }

    public String getCell_1() {
        return Cell_1;
    }

    public void setCell_1(String cell_1) {
        Cell_1 = cell_1;
    }

    public String getCell_2() {
        return Cell_2;
    }

    public void setCell_2(String cell_2) {
        Cell_2 = cell_2;
    }
}
