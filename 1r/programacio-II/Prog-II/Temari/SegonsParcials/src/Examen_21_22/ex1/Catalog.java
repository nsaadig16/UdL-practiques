package Examen_21_22.ex1;

import java.io.*;

public class Catalog {

    private RandomAccessFile raf;

    public Catalog(String name) throws IOException {
        raf = new RandomAccessFile(name,"rw");
    }

    public void close() throws IOException { raf.close(); }

    public Product read(long id) throws IOException {
        //TODO
        return null;
    }

    public void write(Product product) throws IOException {
        byte[] record = product.toBytes();
        this.raf.seek((product.getId() - 1L) * Product.SIZE);
        this.raf.write(record);
    }

    private long numProducts() throws IOException{
        return this.raf.length() / Product.SIZE;
    }

    public long nextId() throws IOException {
        return numProducts() + 1L;
    }

    public boolean contains(long id) throws IOException {
        return 1L <= id && id <= numProducts();
    }
}
