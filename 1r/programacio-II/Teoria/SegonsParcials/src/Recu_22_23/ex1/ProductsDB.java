package Recu_22_23.ex1;

import java.io.*;

public class ProductsDB {
    private RandomAccessFile raf;

    public ProductsDB(String fname) throws IOException {
        this.raf = new RandomAccessFile(fname,"rw");
    }
    public void close() throws IOException {
        this.raf.close();
    }
    public Product read(long id) throws IOException {
        byte[] record = new byte[Product.SIZE];
        this.raf.seek(id * Product.SIZE);
        this.raf.read(record);
        return Product.fromBytes(record);
    }

    /**
     * (l. 31)Guardem en un array de bytes (record) el producte amb toBytes.
     * <p>
     * (l. 32)Ens fiquem a la posició del producte (comença a 0, llavors no cal restar 1)
     * <p>
     * (l. 33)Escrivim el producte
     * @param product el producte a escriure
     * @throws IOException
     */
    public void write(Product product) throws IOException {
        byte[] record = product.toBytes();
        this.raf.seek(product.getId() * Product.SIZE);
        this.raf.write(record);
    }
    public long numProducts() throws IOException {
        return this.raf.length() / Product.SIZE;
    }

}

