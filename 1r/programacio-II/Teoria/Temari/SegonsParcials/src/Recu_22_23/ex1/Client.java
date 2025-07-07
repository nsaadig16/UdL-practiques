package Recu_22_23.ex1;

import utils.PackUtils;

public class Client {
    private static final int NAME_LIMIT = 20;
    private long id;
    private String name;
    private int total;
    /**
     * Id (long), 64 bits, 8 bytes
     * name (String), 16 bits/caràcter, 2 bytes/caracter
     * total (int), 32 bits, 4 bytes
     */
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4;

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
        this.total = 0;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getTotal(){
        return this.total;
    }

    public void add(int quantity, int price) {
        this.total += quantity * price;
    }

    public byte[] toBytes() {
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packLong(id,record,offset);
        offset += 8;
        PackUtils.packLimitedString(name, NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        PackUtils.packInt(total,record,offset);
        return record;
    }

    /**
     * (l. 60 - 66) Com sempre, explicat a ProvesLINE
     * <p>
     * Creem el client sense el total (ja que no existeix com a paràmetre)
     * <p>
     * Li sumem amb el add (l. 35), i ja que total ja són els diners, només multipliquem per 1
     * <p>
     * @param bytes el registre del client
     * @return el client
     */
    public static Client fromBytes(byte[] bytes) {
        int offset = 0;
        long id = PackUtils.unpackLong(bytes,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,bytes,offset);
        offset += 2 * NAME_LIMIT;
        int total = PackUtils.unpackInt(bytes,offset);
        Client client = new Client(id,name);
        client.add(total,1);
        return client;
    }
}

