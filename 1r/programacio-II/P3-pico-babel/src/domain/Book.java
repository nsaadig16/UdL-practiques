package domain;

import utils.PackUtils;

/**
 * This class is used to represent a book with an id, a title,
 * its author's id and the id of the next book.
 * It provides methods to pack and unpack the information to/from an array of bytes.
 */
public class Book {

    /**
     * The maximum length a book's title can have.
     */
    public static final int TITLE_LIMIT = 20;

    /**
     * The size (in bytes) of the record associated with the book.
     */
    public static final int SIZE = 8 + 2 * TITLE_LIMIT + 8 + 8;

    private final long id;
    private final String title;
    private final long authorId;

    private long nextBookId;

    /**
     * Initializes a book for which the next book is unknown.
     * @param id the identification number of the book
     * @param title the title of the book
     * @param authorId the identification number of the books author
     */
    public Book(long id, String title, long authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.nextBookId = -1L;
    }

    /**
     * Initializes a book for which the next book is known.
     * @param id the identification number of the book
     * @param title the title of the book
     * @param authorId the identification number of the books author
     * @param nextBookId the identification number of the next book
     */
    public Book(long id, String title, long authorId, long nextBookId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.nextBookId = nextBookId;
    }

    /**
     * Transforms the book to a record made of bytes.
     * @return the array of bytes where the book is recorded
     */
    public byte[] toBytes() {
        byte[] record = new byte[SIZE];
        int offset = 0;
        PackUtils.packLong(this.id, record, offset);
        offset += 8;
        PackUtils.packLimitedString(this.title,TITLE_LIMIT,record,offset);
        offset += 2 * TITLE_LIMIT;
        PackUtils.packLong(this.authorId, record, offset);
        offset += 8;
        PackUtils.packLong(this.nextBookId, record, offset);
        return record;
    }

    /**
     * Transforms a record of bytes to a book.
     * @param record the array of bytes with the information of the book
     * @return a book with the information extracted from the record
     */
    public static Book fromBytes(byte[] record) {
        int offset = 0;
        long id = PackUtils.unpackLong(record,offset);
        offset += 8;
        String title = PackUtils.unpackLimitedString(TITLE_LIMIT,record,offset);
        offset += 2 * TITLE_LIMIT;
        long authorId = PackUtils.unpackLong(record,offset);
        offset += 8;
        long nextBookId = PackUtils.unpackLong(record,offset);
        return new Book(id, title, authorId, nextBookId);
    }

    /**
     * Returns the identification number of the book.
     * @return the book's id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the title of the book.
     * @return the book's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the identification number of the book's author.
     * @return the id of the book's author
     */
    public long getAuthorId() {
        return  this.authorId;
    }

    /**
     * Returns the identification number of the next book.
     * @return the next book's id
     */
    public long getNextBookId() {
        return this.nextBookId;
    }

    /**
     * Changes the book's next book.
     * @param idBook the id of the next book
     */
    public void setNextBookId(long idBook) {
        this.nextBookId = idBook;
    }

    /**
     * Returns a String showcasing all the information of the book.
     * @return a String with the book's information.
     *
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", nextBookId=" + nextBookId +
                '}';
    }
}
