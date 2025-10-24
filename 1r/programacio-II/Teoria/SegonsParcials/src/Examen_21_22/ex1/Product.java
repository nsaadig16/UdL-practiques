package Examen_21_22.ex1;

import utils.PackUtils;

public class Product {


    private static final int NAME_LIMIT = 25;
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4;
    private long id;        // identificador del producto
    private String name;    // nombre del producto
    private int units;      // cantidad de producto disponible (siempre >= 0)

    public Product(long id, String name) {
        // Precondición: id >= 1L
        this.id = id; this.name = name; this.units = 0;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getUnits(){
        return this.units;
    }

    public void incrementUnits(int amount) {
        this.units += amount;
    }

    public boolean decrementUnits(int amount) {
        if(amount <= this.units){
            this.units -= amount;
            return true;
        }
        return false;
    }

    public byte[] toBytes() {
        //TODO
        return null;
    }

    public static Product fromBytes(byte[] record) {
        int offset = 0;
        long id = PackUtils.unpackLong(record,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        int units = PackUtils.unpackInt(record,offset);
        Product product = new Product(id,name);
        product.incrementUnits(units);
        return product;
    }
}
