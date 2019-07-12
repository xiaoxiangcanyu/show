//package src.Promote.Old.OriginalDataDeal.Util.LabelNameCheck;
//
//import org.apache.poi.hssf.util.Region;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.util.CellRangeAddress;
//
///**
// * 判断单元格是否合并的实体类
// */
//public class isMergedRegion {
//
//
//
//    /**
//     * 判断指定的单元格是否是合并单元格
//     * @param sheet
//     * @param row 行下标
//     * @param column 列下标
//     * @return
//     */
//    public static  boolean isMergedRegion(Sheet sheet,int row ,int column) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();
//            int lastColumn = range.getLastColumn();
//            int firstRow = range.getFirstRow();
//            int lastRow = range.getLastRow();
//            if(row >= firstRow && row <= lastRow){
//                if(column >= firstColumn && column <= lastColumn){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//}
