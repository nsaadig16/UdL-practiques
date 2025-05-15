package helpers;

import domain.Author;
import domain.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Checkers {

    public static void checkAuthorEquals(Author expected, Author actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getNumBooks(), actual.getNumBooks());
        assertEquals(expected.getFirstBookId(), actual.getFirstBookId());
        assertEquals(expected.getLastBookId(), actual.getLastBookId());
    }

    public static void checkBookEquals(Book expected, Book actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthorId(), actual.getAuthorId());
        assertEquals(expected.getNextBookId(), actual.getNextBookId());
    }
}
