package qa.planet.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.PDF.containsExactText;
import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HWTestFileParsingFromZipArchive {
    private ClassLoader classLoad = HWTestFileParsingFromZipArchive.class.getClassLoader();

    @Test
    @DisplayName("Проверка PDF")
    void pdfTest() throws Exception {
        try (InputStream is = classLoad.getResourceAsStream("HW_qaguru_files_19.zip");
             ZipInputStream zipInpSt = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zipInpSt.getNextEntry()) != null) {
                if (entry.getName().equals("PDF_Converter_Pro_Quick_Reference_Guide.RU.pdf")) {
                    PDF pdf = new PDF(zipInpSt);
                    assertEquals(59, pdf.numberOfPages);
                    assertEquals("Nuance Communications, Inc.", pdf.author);
                    assertThat(pdf, containsText("Краткое справочное руководство"));
                    assertThat(pdf, containsText("Почему популярны файлы PDF?"));
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка XLSX")
    void xlsxTest() throws Exception {
        try (InputStream is = classLoad.getResourceAsStream("HW_qaguru_files_19.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("PARFUM-demo.xlsx")) {
                    XLS xls = new XLS(zis);
                    assertEquals("Артикул во внутренней системе составителя документа.", xls.excel.getSheetAt(0).getRow(3).getCell(3).getStringCellValue());
                    assertEquals("ДА", xls.excel.getSheetAt(1).getRow(1).getCell(7).getStringCellValue());
                    assertEquals(2, xls.excel.getNumberOfSheets());
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка CSV")
    void csvTest() throws Exception {
        try (InputStream is = classLoad.getResourceAsStream("HW_qaguru_files_19.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("SampleCSVFile_119kb.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> csvContent = csvReader.readAll();
                    assertArrayEquals(new String[]{"1","Eldon Base for stackable storage shelf, platinum","Muhammed MacIntyre","3","-213.25","38.94","35","Nunavut","Storage & Organization","0.8"}, csvContent.get(0));
                    assertArrayEquals(new String[]{"2","1.7 Cubic Foot Compact \"Cube\" Office Refrigerators","Barry French","293","457.81","208.16","68.02","Nunavut","Appliances","0.58"}, csvContent.get(1));
                }
            }
        }
    }

}
