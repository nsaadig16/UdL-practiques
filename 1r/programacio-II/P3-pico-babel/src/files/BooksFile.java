package files;

import domain.Author;
import domain.Book;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class provides methods to write and read data
 * about books in a random access file.
 * It has been made so the book with the id "1L" is
 * the first one saved.
 */
public class BooksFile {

    private final RandomAccessFile books;

    /**
     * Initializes the file where the books' data is read from and written to.
     * @param fname the name of the file
     * @throws IOException an error in the input / output of data
     */
    public BooksFile(String fname) throws IOException {
        this.books = new RandomAccessFile(fname,"rw");
    }

    /**
     * Writes the information of the book as a record of bytes in the file
     * @param book the book that is written
     * @throws IOException an error in the input / output of data
     */
    public void writeBook(Book book) throws IOException {
        this.books.seek((book.getId() - 1L) * Book.SIZE );
        byte[] record = book.toBytes();
        this.books.write(record);
    }

    /**
     Reads the information of a book recorded in the file.
     * @param idBook the id of the book that is read
     * @return the book with that id
     * @throws IOException an error in the input / output of data
     */
    public Book readBook(long idBook) throws IOException {
        this.books.seek((idBook - 1L) * Book.SIZE);
        byte[] record = new byte[Book.SIZE];
        this.books.read(record);
        return Book.fromBytes(record);
    }

    /**
     * Returns the books that an author has.
     * @param author the author whose books are returned
     * @return an array with the author's books
     * @throws IOException an error in the input / output of data
     */
    public Book[] getBooksForAuthor(Author author) throws IOException {
        Book[] collection = new Book[author.getNumBooks()];
        if (author.getNumBooks() != 0) {
            int i = 0;
            for(long id = author.getFirstBookId(); id <= author.getLastBookId(); id += 1L ){
                Book currentBook = readBook(id);
                if(currentBook.getAuthorId() == author.getId()){
                    collection[i] = currentBook;
                    i += 1;
                }
            }
        }
        return collection;
    }

    /**
     * Returns the number of books that are recorded in the file.
     * @return the number of books recorded
     * @throws IOException an error in the input / output of data
     */
    public long numBooks() throws IOException {
        return this.books.length() / Book.SIZE;
    }

    /**
     * Returns the identification number of the next book that can be recorded.
     * @return the id of the next possible book
     * @throws IOException an error in the input / output of data
     */
    public long nextBookId() throws IOException {
        return numBooks() + 1L;
    }

    /**
     * Checks if an id is a valid book id (between 1L and the number of book).
     * @param idBook the id that is checked
     * @return whether the id is valid for a book
     * @throws IOException an error in the input / output of data
     */
    public boolean isValidId(long idBook) throws IOException {
        return 1L <= idBook && idBook <= numBooks();
    }

    /**
     * Erases all the contents of the data file.
     * @throws IOException an error in the input / output of data
     */
    public void reset() throws IOException {
        this.books.setLength(0L);
    }

    /**
     * Closes the file
     * @throws IOException an error in the input / output of data
     */
    public void close() throws IOException {
        this.books.close();
    }
}
