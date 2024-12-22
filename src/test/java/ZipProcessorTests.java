import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipProcessorTests {
    private ClassLoader cl = ZipProcessorTests.class.getClassLoader();

    @Test
    void checkPdfFileFromZipFileTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains("Формат передачи данных"), "PDF should contain the section 'Формат передачи данных'");
                }
            }
        }
    }

    @Test
    void checkXlsxFileFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(".xls")) {
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    Assertions.assertTrue(value.contains("Ожидаемый результат"));
                }
            }
        }
    }
    @Test
    void checkCsvFileFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> rows = csvReader.readAll();
                    Assertions.assertEquals(4, rows.size(), "CSV should contain 4 categories");
                }
            }
        }
    }
}


