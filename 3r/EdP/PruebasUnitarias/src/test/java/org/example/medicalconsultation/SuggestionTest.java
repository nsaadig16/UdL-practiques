package org.example.medicalconsultation;

import org.example.data.ProductID;
import org.example.medicalconsultation.exceptions.InvalidSuggestionValuesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SuggestionTest {

    private ProductID prodID;
    private dayMoment moment;
    private int duration;
    private float dose;
    private float frequency;
    private FqUnit freqUnit;
    private String instruction;

    @BeforeEach
    void setup() {
        prodID = new ProductID("1".repeat(10));
        moment = dayMoment.DURINGBREAKFAST;
        duration = 2;
        dose = 0.5f;
        frequency = 3;
        freqUnit = FqUnit.DAY;
        instruction = "Test";
    }

    @Test
    @DisplayName("Product ID is stored correctly")
    void testProductID() {
        Suggestion[] suggestions = {
                new Suggestion.Insert(prodID, moment, duration, dose, frequency, freqUnit, instruction),
                new Suggestion.Remove(prodID),
                new Suggestion.Modify(prodID, null, 0, 0, 0, null, "test")
        };
        for (Suggestion s : suggestions) {
            assertEquals(prodID, s.getProductID());
        }
    }

    @Test
    @DisplayName("Operation is stored correctly")
    void testOperation() {
        Suggestion insert = new Suggestion.Insert(prodID, moment, duration, dose, frequency, freqUnit, instruction);
        assertEquals(Suggestion.OperationType.INSERT, insert.getOperation());
        Suggestion remove = new Suggestion.Remove(prodID);
        assertEquals(Suggestion.OperationType.REMOVE, remove.getOperation());
        Suggestion modify = new Suggestion.Modify(prodID, null, 0, 0, 0, null, "test");
        assertEquals(Suggestion.OperationType.MODIFY, modify.getOperation());
    }

    @Test
    @DisplayName("Should throw InvalidSuggestionValuesException when ProductID is null")
    void testNullProductID() {
        Throwable e1 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(null, moment, duration, dose, frequency, freqUnit, instruction);
        });
        Throwable e2 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Remove(null);
        });
        Throwable e3 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Modify(null, moment, duration, dose, frequency, freqUnit, instruction);
        });
        Throwable[] exc = {e1, e2, e3};
        for (Throwable t : exc) {
            assertEquals("ProductID cannot be null", t.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw InvalidSuggestionValuesException when Insert parameters are null")
    void testNullParameters() {
        Throwable e1 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, null, duration, dose, frequency, freqUnit, instruction);
        });
        Throwable e2 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, moment, duration, dose, frequency, null, instruction);
        });
        Throwable e3 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, moment, duration, dose, frequency, freqUnit, null);
        });
        Throwable[] throwables = {e1, e2, e3};
        for (Throwable t : throwables) {
            assertEquals("Suggestion values cannot be null", t.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw InvalidSuggestionValuesException when Insert parameters are not positive")
    void testNonPositiveParametersInsert() {
        Throwable e1 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, moment, 0, dose, frequency, freqUnit, instruction);
        });
        Throwable e2 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, moment, duration, -1, frequency, freqUnit, instruction);
        });
        Throwable e3 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Insert(prodID, moment, duration, dose, 0, freqUnit, instruction);
        });
        Throwable[] throwables = {e1, e2, e3};
        for (Throwable t : throwables) {
            assertEquals("Suggestion values must be positive", t.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw InvalidSuggestionValuesException when Modify parameters are negative")
    void testNegativeParametersModify() {
        Throwable e1 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Modify(prodID, moment, -1, dose, frequency, freqUnit, instruction);
        });
        Throwable e2 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Modify(prodID, moment, duration, -1, frequency, freqUnit, instruction);
        });
        Throwable e3 = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Modify(prodID, moment, duration, dose, -1, freqUnit, instruction);
        });
        Throwable[] throwables = {e1, e2, e3};
        for (Throwable t : throwables) {
            assertEquals("Numerical suggestion values should be positive", t.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw InvalidSuggestionValuesException when Modify instructions does not change parameters")
    void testUnchangedModify() {
        Throwable t = assertThrows(InvalidSuggestionValuesException.class, () -> {
            new Suggestion.Modify(prodID, null, 0, 0, 0, null, null);
        });
        assertEquals("MODIFY suggestions should at least modify one value", t.getMessage());
    }

}
