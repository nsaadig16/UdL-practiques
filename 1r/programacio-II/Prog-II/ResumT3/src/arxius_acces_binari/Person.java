package arxius_acces_binari;
import utils.PackUtils;

public class Person {

    private final long id;
    private final String name;
    private final int age;
    private final boolean married;
    private static final int NAME_LIMIT = 20;
    /**
     * SIZE: length of the record in bytes
     * <p>
     * id (long) -> 64 bits -> 8 bytes
     * <p>
     * name (String with NAME_LIMIT characters) -> 1 char == 16 bits == 2 bytes -> 2 * NAME_LIMIT bytes
     * <p>
     * age (integer) -> 32 bits -> 4 bytes
     * <p>
     * married (boolean) -> 1 bit -> 1 byte
     */
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4 + 1;

    /**
     * Person constructor
     */
    public Person (long id, String name, int age, boolean married){
        this.id = id;
        this.name = name;
        this.age = age;
        this.married = married;
    }

    /**
     * Transforms this Person to an array of bytes
     * <p>
     * We create an array of bytes, a "record"
     * <p>
     * We set the offset to 0, and we pack the first info (id)
     * <p>
     * We add the length of id (8 bytes) to the offset
     * <p>
     * We keep doing it until we pack the last info (married)
     * @return The record
     */
    public byte[] toBytes(){
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packLong(this.id,record,offset);
        offset += 8;
        PackUtils.packLimitedString(this.name,NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        PackUtils.packInt(this.age,record,offset);
        offset += 4;
        PackUtils.packBoolean(this.married,record,offset);
        return record;
    }

    /**
     * Transforms the record to a Person
     * <p>
     *     We do the same as before, but we un-pack the info and assign it to the variables
     * @param record the record that we have to transform
     * @return a Person with the data from the record
     */
    public static Person fromBytes(byte[] record){
        int offset = 0;
        long id =  PackUtils.unpackLong(record,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        int age = PackUtils.unpackInt(record,offset);
        offset += 4;
        boolean married = PackUtils.unpackBoolean(record,offset);
        return new Person(id,name,age,married);
    }

    /**
     * Retorna la id
     * @return la id (XD)
     */
    public long getId(){
        return this.id;
    }

}
