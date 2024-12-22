import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipProcessorTests {
    private final ClassLoader cl = ZipProcessorTests.class.getClassLoader();

    @Test
    void checkPdfFileFromZipFileTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean pdfFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) {
                    pdfFound = true;
                    PDF pdf = new PDF(zis);
                    assertThat(pdf.text).contains("Формат передачи данных")
                            .as("PDF should contain the section 'Формат передачи данных'");
                }
            }
            assertThat(pdfFound).isTrue().as("A PDF file should be present in the ZIP.");
        }
    }

    @Test
    void checkXlsxFileFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean xlsFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xls")) {
                    xlsFound = true;
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    assertThat(value).contains("Ожидаемый результат")
                            .as("The first cell of the XLS file should contain 'Ожидаемый результат'");
                }
            }
            assertThat(xlsFound).isTrue().as("An XLS file should be present in the ZIP.");
        }
    }

    @Test
    void checkCsvFileFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            boolean csvFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    csvFound = true;
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> rows = csvReader.readAll();
                    assertThat(rows).hasSize(4).as("CSV should contain 4 categories");
                }
            }
            assertThat(csvFound).isTrue().as("A CSV file should be present in the ZIP.");
        }
    }
}

