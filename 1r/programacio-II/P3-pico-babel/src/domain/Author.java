package domain;

import utils.PackUtils;

/**
 * This class is used to represent an author with an id, a name and
 * an amount of books as well as the first and last one.
 * It provides methods to pack and unpack the information to/from an array of bytes.
 */
public class Author {

    /**
     * The maximum characters an author's name can have.
     */
    public static final int NAME_LIMIT = 20;

    /**
     * The size (in bytes) of the record associated with the author.
     */
    public static final int SIZE = 8 + 2 * NAME_LIMIT + 4 + 8 + 8;

    private final long id;
    private final String name;

    private int  numBooks;
    private long firstBookId;
    private long lastBookId;

    /**
     * Initializes an author that hasn't got any registered books yet.
     * @param id the identification number of the author
     * @param name the name of the author
     */
    public Author(long id, String name) {
        this.id = id;
        this.name = name;
        this.numBooks = 0;
        this.firstBookId = -1L;
        this.lastBookId = -1L;
    }

    /**
     * Initializes an author whose books are registered.
     * @param id the identification number of the author
     * @param name the name of the author
     * @param numBooks the number of books the author has
     * @param firstBookId the identification number of the first book of the author
     * @param lastBookId the identification number of the last known book of the author
     */
    public Author(long id, String name, int numBooks, long firstBookId, long lastBookId) {
        this.id = id;
        this.name = name;
        this.numBooks = numBooks;
        this.firstBookId = firstBookId;
        this.lastBookId = lastBookId;
    }

    /**
     * Adds a book to the author, setting it as the last book.
     * @param idBook the identification number of the book
     */
    public void addBookId(long idBook) {
        this.lastBookId = idBook;
        if(this.firstBookId == -1L){
            this.firstBookId = idBook;
        }
        this.numBooks += 1;
    }

    /**
     * Transforms the author to a record made of bytes.
     * @return the array of bytes where the author is recorded
     */
    public byte[] toBytes() {
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packLong(this.id,record,offset);
        offset += 8;
        PackUtils.packLimitedString(this.name,NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        PackUtils.packInt(this.numBooks,record,offset);
        offset += 4;
        PackUtils.packLong(this.firstBookId,record,offset);
        offset += 8;
        PackUtils.packLong(this.lastBookId,record,offset);
        return record;
    }

    /**
     * Transforms a record of bytes to an author.
     * @param record the array of bytes with the information of the author
     * @return an author with the information extracted from the record
     */
    public static Author fromBytes(byte[] record) {
        int offset = 0;
        long id = PackUtils.unpackLong(record,offset);
        offset += 8;
        String name = PackUtils.unpackLimitedString(NAME_LIMIT,record,offset);
        offset += 2 * NAME_LIMIT;
        int numBooks = PackUtils.unpackInt(record,offset);
        offset += 4;
        long firstBookId = PackUtils.unpackLong(record,offset);
        offset += 8;
        long lastBookId = PackUtils.unpackLong(record,offset);
        return new Author(id,name,numBooks,firstBookId,lastBookId);
    }

    /**
     * Returns the author's identification number.
     * @return the author's id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the name of the author.
     * @return the author's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the identification number of the last registered book.
     * @return the last book's id
     */
    public long getLastBookId() {
        return this.lastBookId;
    }

    /**
     * Returns the number of books that the author has.
     * @return the author's number of books
     */
    public int getNumBooks() {
        return this.numBooks;
    }

    /**
     * Returns the identification number of the first registered book.
     * @return the first book's id
     */
    public long getFirstBookId() {
        return this.firstBookId;
    }

    /**
     * Returns a String showcasing all the information of the author.
     * @return a String with the author's information.
     *
     */
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numBooks=" + numBooks +
                ", firstBookId=" + firstBookId +
                ", lastBookId=" + lastBookId +
                '}';
    }
}
