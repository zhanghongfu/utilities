package dolf.zhang.utilities.xls;

import dolf.zhang.utilities.date.DateUtilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class XlsUtilities {

    private XlsUtilities() {
    }

    public static void main(String[] args) throws Exception {
        String filepath = "/Users/dolf/Desktop/2016双11淘宝爆款清单.xls";
//        String filepath = "/Users/admaster/Desktop/1.xls";

        for (Object o : read(filepath)){
            System.out.println(o);

        }
    }


    public static List<Object> read(String filepath) throws Exception {
        Workbook work = null;
        String ext = filepath.substring(filepath.lastIndexOf("."));
        InputStream is = new FileInputStream(filepath);
        if (".xls".equals(ext)) {
            work = new HSSFWorkbook(is);
        } else if (".xlsx".equals(ext)) {
            work = new XSSFWorkbook(is);
        }
        if (work == null)
            return null;
       List< Object> date = new ArrayList<Object>();
        List<Sheet> sheets = getSheets(work);
        for (Sheet sheet : sheets) {
            List<Row> rows = getRows(sheet);
            List< Object> sheetValues = new ArrayList<Object>();
            for (Row row : rows) {
                List<String> rowValues = getRowValues(row);
                if (CollectionUtils.isNotEmpty(rowValues))
                    sheetValues.add(rowValues);
            }
            date.add(sheetValues);
        }
        return date;
    }

    private static List<String> getRowValues(Row row) {
        if (row == null) return null;
        List<String> list = new ArrayList<String>();
        int count = 0;
        for (Cell cell : row) {
            if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                count++;
            }
            list.add(getValue(cell));
        }
        if (count == 0) return null;
        return list;
    }

    private static List<Row> getRows(Sheet sheet) {
        List<Row> rows = new ArrayList<Row>();
        if (sheet == null) return rows;
        for (int r = 0; r < sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            rows.add(row);
        }
        return rows;
    }

    private static List<Sheet> getSheets(Workbook work) {
        List<Sheet> sheets = new ArrayList<Sheet>();
        if (work == null)
            return sheets;
        int numberOfSheets = work.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(work.getSheetAt(i));
        }
        return sheets;
    }


    private static String getValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return DateUtilities.defaultFormatDate(cell.getDateCellValue());
            }
            double v = cell.getNumericCellValue();
            DecimalFormat df;
            if (v % 1 == 0) {
                df = new DecimalFormat("0");
            } else {
                df = new DecimalFormat("0.00");
            }
            return df.format(v);
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

}
