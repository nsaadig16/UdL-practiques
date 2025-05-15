package domain;

import org.junit.jupiter.api.Test;

import static helpers.Checkers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorTest {

    @Test
    public void simple_constructor() {
        Author author = new Author(1L, "Author name");
        assertEquals(1L, author.getId());
        assertEquals("Author name", author.getName());
        assertEquals(0, author.getNumBooks());
        assertEquals(-1L, author.getFirstBookId());
        assertEquals(-1L, author.getLastBookId());
    }

    @Test
    public void complex_constructor() {
        Author author = new Author(1L, "Author name", 2, 5L, 12L);
        assertEquals(1L, author.getId());
        assertEquals("Author name", author.getName());
        assertEquals(2, author.getNumBooks());
        assertEquals(5L, author.getFirstBookId());
        assertEquals(12L, author.getLastBookId());
    }

    @Test
    public void add_one_book() {
        Author author = new Author(1L, "Author name");
        author.addBookId(12L);
        assertEquals(1, author.getNumBooks());
        assertEquals(12L, author.getFirstBookId());
        assertEquals(12L, author.getLastBookId());
    }

    @Test
    public void add_two_books() {
        Author author = new Author(1L, "Author name");
        author.addBookId(12L);
        author.addBookId(15L);
        assertEquals(2, author.getNumBooks());
        assertEquals(12L, author.getFirstBookId());
        assertEquals(15L, author.getLastBookId());
    }

    @Test
    public void add_three_books() {
        Author author = new Author(1L, "Author name");
        author.addBookId(12L);
        author.addBookId(15L);
        author.addBookId(20L);
        assertEquals(3, author.getNumBooks());
        assertEquals(12L, author.getFirstBookId());
        assertEquals(20L, author.getLastBookId());
    }

    @Test
    public void roundtrip() {
        Author author = new Author(1L, "Author name", 2, 5L, 12L);
        byte[] record = author.toBytes();
        Author recovered = Author.fromBytes(record);
        checkAuthorEquals(author, recovered);
    }

    @Test
    public void roundtrip_longname() {
        Author author = new Author(1L, "Author name with more than twenty characters");
        byte[] record = author.toBytes();
        Author recovered = Author.fromBytes(record);
        assertEquals(author.getId(), recovered.getId());
        String reducedName = author.getName().substring(0, Author.NAME_LIMIT);
        assertEquals(reducedName, recovered.getName());
        assertEquals(author.getNumBooks(), recovered.getNumBooks());
        assertEquals(author.getFirstBookId(), recovered.getFirstBookId());
        assertEquals(author.getLastBookId(), recovered.getLastBookId());
    }
}