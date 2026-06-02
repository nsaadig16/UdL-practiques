package org.example.data;

import org.example.data.exceptions.InvalidProductIDException;

/**
 * Class that represents a ProductID
 */
final public class ProductID {

    private final String productID;

    /**
     * Constructor of ProductID
     *
     * @param code String representing the product ID
     * @throws InvalidProductIDException when the code is null, empty, not 10 characters long, or contains non-numeric characters
     */
    public ProductID(String code) throws InvalidProductIDException {
        if (code == null) {
            throw new InvalidProductIDException("ProductID cannot be null");
        }
        if (code.isBlank()) {
            throw new InvalidProductIDException("ProductID cannot be empty");
        }
        if (code.length() != 10) {
            throw new InvalidProductIDException("ProductID length should be 10");
        }
        if (!code.matches("^[0-9]+$")) {
            throw new InvalidProductIDException("ProductID can only contain numbers");
        }
        this.productID = code;
    }

    public String getProductID() {
        return productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID prodID = (ProductID) o;
        return productID.equals(prodID.productID);
    }

    @Override
    public int hashCode() {
        return productID.hashCode();
    }

    @Override
    public String toString() {
        return "ProductID {" + "product code = '" + productID + '\'' + '}';
    }
}