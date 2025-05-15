package files;

import domain.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static helpers.Checkers.checkAuthorEquals;
import static org.junit.jupiter.api.Assertions.*;


public class AuthorsFileTest {

    private static final Author author1 = new Author(3L, "Author 3L");
    private static final Author author2 = new Author(4L, "Author 4L");
    private static final Author author3 = new Author(2L, "Author 2L");

    private AuthorsFile authors;

    @BeforeEach
    public void openFile() throws IOException {
        authors = new AuthorsFile("testAuthorsDB.dat");
    }

    @AfterEach
    public void resetAndClose() throws IOException {
        authors.reset();
        authors.close();
    }

    @Test
    public void each_test_begin_with_empty_db() throws IOException {
        assertEquals(0L, authors.numAuthors());
        assertEquals(1L, authors.nextAuthorId());
    }

    @Test
    public void write_and_read_one_author() throws IOException {
        authors.writeAuthor(author1);
        Author recovered = authors.readAuthor(author1.getId());
        checkAuthorEquals(author1, recovered);
        assertEquals(3L, authors.numAuthors());
        assertEquals(4L, authors.nextAuthorId());
    }

    @Test
    public void write_and_read_various_authors() throws IOException {
        authors.writeAuthor(author1);
        authors.writeAuthor(author2);
        authors.writeAuthor(author3);

        Author recovered1 = authors.readAuthor(author1.getId());
        Author recovered2 = authors.readAuthor(author2.getId());
        Author recovered3 = authors.readAuthor(author3.getId());

        checkAuthorEquals(author1, recovered1);
        checkAuthorEquals(author2, recovered2);
        checkAuthorEquals(author3, recovered3);

        assertEquals(4L, authors.numAuthors());
        assertEquals(5L, authors.nextAuthorId());
    }

    @Test
    public void validAuthor() throws IOException {
        authors.writeAuthor(author1);
        assertFalse(authors.isValidId(0L));
        assertTrue(authors.isValidId(2L));
        assertTrue(authors.isValidId(3L));
        assertFalse(authors.isValidId(4L));
    }

}