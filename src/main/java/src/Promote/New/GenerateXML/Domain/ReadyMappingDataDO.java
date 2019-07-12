package src.Promote.New.GenerateXML.Domain;

/**
 * 从数据清洗之后获取的各页的数据
 */
public class ReadyMappingDataDO {
    private String table;//所属的sheet表
    private String subject;//科目名称
    private String columnum;//列数

    //    ===================封面页============================
    //    ===============================================
    private String currency;//货币，用来计算Unit的
    private String metricsmeasures;//度量衡,用来计算Decimal
    private String accuracy;//精确度,用来计算Decimal
    //    ===================封面页============================
    //    ===================其他三个sheet页============================
    //    ===============================================
    private String labelname;//其他3个sheet页的D列
    private String value;//其他3个sheet页的数字列的数值
    //    ===================其他三个sheet页============================

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMetricsmeasures() {
        return metricsmeasures;
    }

    public void setMetricsmeasures(String metricsmeasures) {
        this.metricsmeasures = metricsmeasures;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
