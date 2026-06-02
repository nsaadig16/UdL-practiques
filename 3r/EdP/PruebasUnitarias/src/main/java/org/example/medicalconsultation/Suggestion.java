package org.example.medicalconsultation;

import org.example.data.ProductID;
import org.example.medicalconsultation.exceptions.InvalidSuggestionValuesException;

import java.util.Objects;

/**
 * Represents a suggestion made by the Decision-Making AI
 */
public abstract class Suggestion {
    protected final OperationType operation;
    protected final ProductID productID;

    // Types of operations for suggestions
    public enum OperationType {
        INSERT, REMOVE, MODIFY
    }

    protected Suggestion(OperationType op, ProductID prodID) {
        if (prodID == null) {
            throw new InvalidSuggestionValuesException("ProductID cannot be null");
        }
        this.operation = op;
        this.productID = prodID;
    }

    public OperationType getOperation() {
        return this.operation;
    }

    public ProductID getProductID() {
        return productID;
    }

    protected abstract void validate();

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    /**
     * Insert suggestion class
     */
    public static class Insert extends Suggestion {
        private final dayMoment moment;
        private final int duration;
        private final float dose;
        private final float frequency;
        private final FqUnit freqUnit;
        private final String instruction;

        /**
         * Constructor for Insert suggestion
         *
         * @param prodID      ProductID of the medicine
         * @param moment      dayMoment when the medicine should be taken
         * @param duration    Duration in days for which the medicine should be taken
         * @param dose        Dose of the medicine
         * @param frequency   Frequency of intake
         * @param freqUnit    Unit of frequency
         * @param instruction Additional instructions
         */
        public Insert(ProductID prodID, dayMoment moment, int duration,
                      float dose, float frequency, FqUnit freqUnit, String instruction) {
            super(OperationType.INSERT, prodID);
            this.moment = moment;
            this.duration = duration;
            this.dose = dose;
            this.frequency = frequency;
            this.freqUnit = freqUnit;
            this.instruction = instruction;
            validate();
        }

        @Override
        protected void validate() {
            if (moment == null || freqUnit == null || instruction == null) {
                throw new InvalidSuggestionValuesException("Suggestion values cannot be null");
            }
            if (duration <= 0 || dose <= 0 || frequency <= 0) {
                throw new InvalidSuggestionValuesException("Suggestion values must be positive");
            }
        }

        @Override
        public String toString() {
            return "Suggestion{" +
                    "operation=" + operation +
                    ", productId='" + productID.getProductID() +
                    "', moment='" + moment +
                    "', duration=" + duration +
                    "', dose=" + dose +
                    "', frequency=" + frequency +
                    "', frequencyUnit='" + freqUnit +
                    "', instruction='" + instruction +
                    "'}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Insert sugg = (Insert) o;
            return duration == sugg.duration &&
                    dose == sugg.dose &&
                    frequency == sugg.frequency &&
                    operation == sugg.operation &&
                    Objects.equals(productID, sugg.productID) &&
                    moment == sugg.moment &&
                    freqUnit == sugg.freqUnit &&
                    Objects.equals(instruction, sugg.instruction)
                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operation, productID, moment, duration,
                    dose, frequency, freqUnit, instruction
            );
        }

    }

    /**
     * Remove suggestion class
     */
    public static class Remove extends Suggestion {

        /**
         * Constructor for Remove suggestion
         *
         * @param productID ProductID of the medicine to be removed
         */
        public Remove(ProductID productID) {
            super(OperationType.REMOVE, productID);
        }

        @Override
        protected void validate() {
        }

        @Override
        public String toString() {
            return "Suggestion{" +
                    "operation='" + operation +
                    "', productId='" + productID +
                    "'}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Remove sugg = (Remove) o;
            return sugg.operation == operation &&
                    Objects.equals(sugg.productID, productID)
                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operation, productID);
        }
    }

    /**
     * Modify suggestion class
     * If a value is not to be modified, it can be set to:
     * - moment: null
     * - duration: 0
     * - dose: 0
     * - frequency: 0
     * - freqUnit: null
     * - instruction: null
     */
    public static class Modify extends Suggestion {

        private final dayMoment moment;
        private final int duration;
        private final float dose;
        private final float frequency;
        private final FqUnit freqUnit;
        private final String instruction;

        /**
         * Constructor for Modify suggestion
         *
         * @param prodID      ProductID of the medicine
         * @param moment      dayMoment when the medicine should be taken
         * @param duration    Duration in days for which the medicine should be taken
         * @param dose        Dose of the medicine
         * @param frequency   Frequency of intake
         * @param freqUnit    Unit of frequency
         * @param instruction Additional instructions
         */
        public Modify(ProductID prodID, dayMoment moment, int duration,
                      float dose, float frequency, FqUnit freqUnit, String instruction) {
            super(OperationType.MODIFY, prodID);
            this.moment = moment;
            this.duration = duration;
            this.dose = dose;
            this.frequency = frequency;
            this.freqUnit = freqUnit;
            this.instruction = instruction;
            validate();
        }

        @Override
        protected void validate() {
            if (duration < 0 || dose < 0 || frequency < 0) {
                throw new InvalidSuggestionValuesException("Numerical suggestion values should be positive");
            }
            int changed = 0;
            if (moment != null) {
                changed++;
            }
            if (duration > 0) {
                changed++;
            }
            if (dose > 0) {
                changed++;
            }
            if (frequency > 0) {
                changed++;
            }
            if (freqUnit != null) {
                changed++;
            }
            if (instruction != null) {
                changed++;
            }
            if (changed == 0) {
                throw new InvalidSuggestionValuesException("MODIFY suggestions should at least modify one value");
            }
        }

        @Override
        public String toString() {
            String s = "Suggestion{" +
                    "operation='" + operation +
                    "', productId='" + productID;
            s += (moment != null) ? "', moment='" + moment : "";
            s += (duration > 0) ? "', duration='" + duration : "";
            s += (dose > 0) ? "', dose='" + dose : "";
            s += (frequency > 0) ? "', frequency='" + frequency : "";
            s += (freqUnit != null) ? "', frequencyUnit='" + freqUnit : "";
            s += (instruction != null) ? "', instruction='" + instruction : "";
            s += "'}";
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Modify sugg = (Modify) o;
            return duration == sugg.duration &&
                    dose == sugg.dose &&
                    frequency == sugg.frequency &&
                    operation == sugg.operation &&
                    Objects.equals(productID, sugg.productID) &&
                    moment == sugg.moment &&
                    freqUnit == sugg.freqUnit &&
                    Objects.equals(instruction, sugg.instruction)
                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operation, productID, moment, duration,
                    dose, frequency, freqUnit, instruction
            );
        }
    }
}
