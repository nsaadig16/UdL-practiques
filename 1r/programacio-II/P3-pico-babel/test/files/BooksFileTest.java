package files;

import domain.Author;
import domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static helpers.Checkers.checkBookEquals;
import static org.junit.jupiter.api.Assertions.*;

public class BooksFileTest {

    private static final String BOOKS = "testBooksDB.dat";

    private static final Book book1 = new Book(5L, "Book 5L", 1L);
    private static final Book book2 = new Book(6L, "Book 6L", 2L);
    private static final Book book3 = new Book(4L, "Book 4L", 3L);

    private BooksFile booksDB;

    @BeforeEach
    public void openFile() throws IOException {
        booksDB = new BooksFile(BOOKS);
    }

    @AfterEach
    public void tearDown() throws IOException {
        booksDB.reset();
        booksDB.close();
    }

    @Test
    public void each_test_begin_with_empty_db() throws IOException {
        assertEquals(0L, booksDB.numBooks());
        assertEquals(1L, booksDB.nextBookId());
    }

    @Test
    public void write_and_read_one_book() throws IOException {
        booksDB.writeBook(book1);
        Book recovered = booksDB.readBook(book1.getId());
        checkBookEquals(book1, recovered);
        assertEquals(5L, booksDB.numBooks());
        assertEquals(6L, booksDB.nextBookId());
    }

    @Test
    public void write_and_read_various_books() throws IOException {
        booksDB.writeBook(book1);
        booksDB.writeBook(book2);
        booksDB.writeBook(book3);

        Book recovered1 = booksDB.readBook(book1.getId());
        Book recovered2 = booksDB.readBook(book2.getId());
        Book recovered3 = booksDB.readBook(book3.getId());

        checkBookEquals(book1, recovered1);
        checkBookEquals(book2, recovered2);
        checkBookEquals(book3, recovered3);

        assertEquals(6L, booksDB.numBooks());
        assertEquals(7L, booksDB.nextBookId());
    }

    @Test
    public void books_of_autor_with_zero_books() throws IOException {
        Author author = new Author(1L, "Author");
        Book[] books = booksDB.getBooksForAuthor(author);
        assertEquals(0, books.length);
    }

    @Test
    public void books_of_autor_with_one_books() throws IOException {
        Author author = new Author(1L, "Author", 1, 10L, 10L);
        Book book = new Book(10L, "Book", 1L);
        booksDB.writeBook(book);

        Book[] books = booksDB.getBooksForAuthor(author);

        assertEquals(1, books.length);
        checkBookEquals(book, books[0]);
    }

    @Test
    public void books_of_autor_with_two_books() throws IOException {
        Author author = new Author(1L, "Author", 2, 10L, 20L);
        Book book1 = new Book(10L, "Book 1", 1L, 20L);
        Book book2 = new Book(20L, "Book 2", 1L);
        booksDB.writeBook(book1);
        booksDB.writeBook(book2);

        Book[] books = booksDB.getBooksForAuthor(author);

        assertEquals(2, books.length);
        checkBookEquals(book1, books[0]);
        checkBookEquals(book2, books[1]);
    }

    @Test
    public void validBook() throws IOException {
        booksDB.writeBook(book1);
        assertFalse(booksDB.isValidId(0L));
        assertTrue(booksDB.isValidId(4L));
        assertTrue(booksDB.isValidId(5L));
        assertFalse(booksDB.isValidId(6L));
    }
}