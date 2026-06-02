package org.example.data;

import org.example.data.exceptions.InvalidProductIDException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIDTest {
    @Test
    @DisplayName("Should throw InvalidProductID when null")
    void testNullID() {
        Throwable exception = assertThrows(InvalidProductIDException.class, () -> {
            new ProductID(null);
        });
        assertEquals("ProductID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidProductID when empty")
    void testEmptyID() {
        Throwable exception = assertThrows(InvalidProductIDException.class, () -> {
            new ProductID("");
        });
        assertEquals("ProductID cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidProductID if length is different than 10")
    void testTooLongID() {
        Throwable exception = assertThrows(InvalidProductIDException.class, () -> {
            new ProductID("1".repeat(70));
        });
        assertEquals("ProductID length should be 10", exception.getMessage());
    }

    @Test

    @DisplayName("Should throw InvalidProductID if it doesn't contain only numbers")
    void testNotNumID() {
        String code = "a".repeat(10);
        Throwable exception = assertThrows(InvalidProductIDException.class, () -> {
            new ProductID(code);
        });
        assertEquals("ProductID can only contain numbers", exception.getMessage());
    }
}
