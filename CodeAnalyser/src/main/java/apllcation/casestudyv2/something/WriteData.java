/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteData<T> {
  static String path = CommandLineOperator.destinationPath;
  static XSSFWorkbook workbook = new XSSFWorkbook();

  public void writeDataToExcel(ArrayList<T> buglist, String Classname) throws Exception {
    try {
      final XSSFSheet sheet = workbook.createSheet("pmd");
      final Map<Integer, Object[]> data = new TreeMap<>();
      int i = 2;
      data.put(1,
          new Object[] { "Class Name", "Error Line Number", "Error Type", "Rule", "Error Description" });
      for (final T f : buglist) {
        data.put(i++, new Object[] { ((ToolTwo) f).getClassName(), ((ToolTwo) f).getLine(),
            ((ToolTwo) f).getCategory(), ((ToolTwo) f).getrule(), ((ToolTwo) f).getContent() });
      }


      final Set<Integer> keyset = data.keySet();
      int rownum = 0;
      for (final Integer key : keyset) {
        final Row row = sheet.createRow(rownum++);
        final Object[] objArr = data.get(key);
        int cellnum = 0;
        for (final Object obj : objArr) {
          final Cell cell = row.createCell(cellnum++);
          sheet.setColumnWidth(cellnum - 1, 15000);
          cell.setCellValue((String) obj);
        }
      }
      final FileOutputStream out = new FileOutputStream(new File(path + Classname + ".xlsx"));
      workbook.write(out);
      out.close();

    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

}