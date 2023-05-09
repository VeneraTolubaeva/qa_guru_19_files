package qa.planet.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.openqa.selenium.io.Zip;
import qa.planet.Human;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfParsingTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File download = $("a[href^='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(download);
        Assertions.assertEquals("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein", pdf.author);
    }

    @Test
    void xlsParsingTest() throws Exception {
        Selenide.open("https://excelvba.ru/programmes/Teachers");
        File download = $("a[href^='https://ExcelVBA.ru/sites/default/files/teachers.xls']").download();
        XLS xls = new XLS(download);
        Assertions.assertEquals("1. Суммарное количество часов планируемое на штатную по всем разделам плана  должно \n" +
                        "составлять примерно 1500 час в год.  ",
                xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue());
    }

    @Test
    void csvParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("qaguru.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"Тучс", "JUnit5"}, content.get(1));
        }
    }

    @Test
    void filesEqualTest() throws Exception {
        Selenide.open("https://github.com/qa-guru/qa_guru_18_files/blob/master/src/test/resources/expectedfiles/qaguru.csv");
        File download = $("#raw-url").download();
        try (InputStream isExpected = cl.getResourceAsStream("expectedfiles/qaguru.csv");
             InputStream downloaded = new FileInputStream(download)) {
            //Assertions.assertArrayEquals(isExpected.readAllBytes(), downloaded.readAllBytes());
            Assertions.assertEquals(
                    new String(isExpected.readAllBytes(), StandardCharsets.UTF_8),
                    new String(downloaded.readAllBytes(), StandardCharsets.UTF_8)
            );
        }
    }

    @Test
    void zipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("qaguru.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                Assertions.assertTrue(entry.getName().contains("qaguru.csv"));
            }
        }
    }

    @Test
    void jsonTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = cl.getResourceAsStream("human.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonObject jsonObject = gson.fromJson(isr, JsonObject.class);
            Assertions.assertTrue(jsonObject.get("isClever").getAsBoolean());
            Assertions.assertEquals(35,jsonObject.get("age").getAsInt());
        }
    }

    @Test
    void jsonCleverTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = cl.getResourceAsStream("human.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Human human = gson.fromJson(isr, Human.class);
            Assertions.assertTrue(human.isClever);
            Assertions.assertEquals(35,human.age);
        }
    }
}
