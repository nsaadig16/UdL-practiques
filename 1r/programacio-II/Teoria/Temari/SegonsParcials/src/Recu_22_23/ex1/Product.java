package Recu_22_23.ex1;

import utils.PackUtils;

public class Product {
    private static final int NAME_LIMIT = 20; // In chars
    private long id;
    private String name;
    private int price;
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4; // In bytes

    public Product(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public byte[] toBytes() {
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packLong(id,record,offset);
        offset += 8;
        PackUtils.packLimitedString(name, NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        PackUtils.packInt(price,record,offset);
        return record;
    }

    public static Product fromBytes(byte[] bytes) {
        int offset = 0;
        long id = PackUtils.unpackLong(bytes,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,bytes,offset);
        offset += 2 * NAME_LIMIT;
        int price = PackUtils.unpackInt(bytes,offset);
        return new Product(id,name,price);
    }
}

