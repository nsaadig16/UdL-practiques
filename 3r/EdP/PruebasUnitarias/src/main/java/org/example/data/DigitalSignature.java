package org.example.data;

import org.example.data.exceptions.InvalidSignatureException;

import java.util.Arrays;

/**
 * Class representing a digital signature
 */
final public class DigitalSignature {
    private final byte[] signature;

    /**
     * Constructor of DigitalSignature
     *
     * @param signature byte array representing the digital signature
     * @throws InvalidSignatureException when signature is null or empty
     */
    public DigitalSignature(byte[] signature) throws InvalidSignatureException {
        if (signature == null) {
            throw new InvalidSignatureException("Signature cannot be null");
        }
        if (signature.length == 0) {
            throw new InvalidSignatureException("Signature cannot be empty");
        }
        this.signature = signature;
    }

    public byte[] getSignature() {
        return this.signature.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature digitalSignature = (DigitalSignature) o;
        return Arrays.equals(signature, digitalSignature.signature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" + "signature='" + signature.length + "'}";
    }
}
