package org.example.data;

import org.example.data.exceptions.InvalidPersonalIDException;

/**
 * The personal identifying code in the National Health Service.
 */
final public class HealthCardID {

    private final String personalID;

    /**
     * Constructor of HealthCardID
     *
     * @param code alphanumerical string of 16 characters
     * @throws InvalidPersonalIDException when code is null, not 16 characters long or not alphanumerical
     */
    public HealthCardID(String code) throws InvalidPersonalIDException {
        if (code == null) {
            throw new InvalidPersonalIDException("Personal ID cannot be null");
        }
        if (code.length() != 16) {
            throw new InvalidPersonalIDException("Personal ID should be 16 characters long");
        }
        if (!code.matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidPersonalIDException("Personal ID should be alphanumerical");
        }
        this.personalID = code.toUpperCase();
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}
