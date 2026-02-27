package org.example.data;

import org.example.data.exceptions.InvalidSignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureTest {

    @Test
    @DisplayName("Should throw InvalidSignatureException when null")
    void testNullSignature() {
        Throwable exception = assertThrows(
                InvalidSignatureException.class, () -> {
                    new DigitalSignature(null);
                }
        );
        assertEquals("Signature cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidSignatureException when empty")
    void testEmptySignature() {
        Throwable exception = assertThrows(
                InvalidSignatureException.class, () -> {
                    new DigitalSignature(new byte[]{});
                }
        );
        assertEquals("Signature cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Signature is stored correctly")
    void testSignatureStored() {
        byte[] signature = new byte[]{1, 1};
        DigitalSignature digitalSignature = new DigitalSignature(signature);
        assertArrayEquals(signature, digitalSignature.getSignature());
    }
}