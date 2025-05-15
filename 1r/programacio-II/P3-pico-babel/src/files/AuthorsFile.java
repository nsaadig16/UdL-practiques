package files;

import domain.Author;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class provides methods to write and read data
 * about authors in a random access file.
 *
 * It has been made so the author with the id "1L" is
 * the first one saved.
 */

public class AuthorsFile {

    private final RandomAccessFile authors;

    /**
     * Initializes the file where the authors' data is read from and written to.
     * @param fname the name of the file
     * @throws IOException an error in the input / output of data
     */
    public AuthorsFile(String fname) throws IOException {
        this.authors = new RandomAccessFile(fname,"rw");
    }

    /**
     * Writes the information of the author as a record of bytes in the file
     * @param author the author that is written
     * @throws IOException an error in the input / output of data
     */
    public void writeAuthor(Author author) throws IOException {
        this.authors.seek((author.getId() - 1L )* Author.SIZE );
        byte[] record = author.toBytes();
        this.authors.write(record);
    }

    /**
     * Reads the information of an author recorded in the file.
     * @param idAuthor the id of the author that is read
     * @return the author with that id
     * @throws IOException an error in the input / output of data
     */
    public Author readAuthor(long idAuthor) throws IOException {
        this.authors.seek((idAuthor - 1L) * Author.SIZE );
        byte[] record = new byte[Author.SIZE];
        this.authors.read(record);
        return Author.fromBytes(record);
    }

    /**
     * Returns the number of authors that are recorded in the file.
     * @return the number of authors recorded
     * @throws IOException an error in the input / output of data
     */
    public long numAuthors() throws IOException {
        return this.authors.length() / Author.SIZE;
    }

    /**
     * Returns the identification number of the next author that can be recorded.
     * @return the id of the next possible author
     * @throws IOException an error in the input / output of data
     */
    public long nextAuthorId() throws IOException {
        return numAuthors() + 1L;
    }

    /**
     * Checks if an id is a valid author id (between 1L and the number of authors).
     * @param idAuthor the id that is checked
     * @return whether the id is valid for an author
     * @throws IOException an error in the input / output of data
     */
    public boolean isValidId(long idAuthor) throws IOException {
        return 1L <= idAuthor && idAuthor <= numAuthors();
    }

    /**
     * Erases all the contents of the data file.
     * @throws IOException an error in the input / output of data
     */
    public void reset() throws IOException {
        this.authors.setLength(0L);
    }

    /**
     * Closes the file.
     * @throws IOException an error in the input / output of data
     */
    public void close() throws IOException {
        this.authors.close();
    }
}
