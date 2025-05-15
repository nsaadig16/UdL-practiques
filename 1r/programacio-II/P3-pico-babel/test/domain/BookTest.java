package domain;

import helpers.Checkers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTest {

    @Test
    public void simple_constructor() {
        Book book = new Book(4L, "Book title", 5L);
        assertEquals(4L, book.getId());
        assertEquals("Book title", book.getTitle());
        assertEquals(5L, book.getAuthorId());
        assertEquals(-1L, book.getNextBookId());
    }

    @Test
    public void complex_constructor() {
        Book book = new Book(4L, "Book title", 5L, 87L);
        assertEquals(4L, book.getId());
        assertEquals("Book title", book.getTitle());
        assertEquals(5L, book.getAuthorId());
        assertEquals(87L, book.getNextBookId());
    }

    @Test
    public void setting_next_book() {
        Book book = new Book(4L, "Book title", 5L, 87L);
        book.setNextBookId(215L);
        assertEquals(4L, book.getId());
        assertEquals("Book title", book.getTitle());
        assertEquals(5L, book.getAuthorId());
        assertEquals(215L, book.getNextBookId());
    }

    @Test
    public void roundtrip() {
        Book book = new Book(4L, "Book title", 5L, 87L);
        byte[] record = book.toBytes();
        Book recovered = Book.fromBytes(record);
        Checkers.checkBookEquals(book, recovered);
    }

    @Test
    public void roundtrip_long_title() {
        Book book = new Book(4L, "Book with a very long, long, title", 5L, 87L);
        byte[] record = book.toBytes();
        Book recovered = Book.fromBytes(record);
        assertEquals(book.getId(), recovered.getId());
        String reducedTitle = book.getTitle().substring(0, Book.TITLE_LIMIT);
        assertEquals(reducedTitle, recovered.getTitle());
        assertEquals(book.getAuthorId(), recovered.getAuthorId());
        assertEquals(book.getNextBookId(), recovered.getNextBookId());
    }
}