package qa.planet.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.planet.pojo.Book;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HWJsonParsingTest {
    private ClassLoader cl = HWJsonParsingTest.class.getClassLoader();
    List<String> mainCharacters = Arrays.asList("Harry Potter", "Ronald Weasley", "Hermione Granger", "Lord Voldemort");

    @Test
    @DisplayName("Проверка JSON")
    void parseBookDataFromJsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream resource = cl.getResourceAsStream("book.json");
             InputStreamReader reader = new InputStreamReader(resource)) {
            Book book = objectMapper.readValue(reader, Book.class);
            assertEquals("J.K.ROWLING", book.getAuthor());
            assertEquals("Harry Potter and the Philosopher's Stone", book.getTitle());
            assertEquals("Fantasy", book.getGenre());
            assertEquals(mainCharacters, book.getMainCharacters());
            assertEquals("1990-1995", book.getEditions().get(0).getDateOfWriting());
            assertEquals(1997, book.getEditions().get(0).getPublishedYear());
            assertEquals("Bloomsbury Publishing", book.getEditions().get(0).getPublisher());
            assertEquals(2000, book.getEditions().get(1).getPublishedYear());
            assertEquals("РОСМЭН-ИЗДАТ", book.getEditions().get(1).getPublisher());
        }
    }
}
