package picobabel;

import acm.program.CommandLineProgram;
import domain.Author;
import domain.Book;
import files.AuthorsFile;
import files.BooksFile;
import files.Logger;

import java.io.*;
import java.util.StringTokenizer;

public class PicoBabel extends CommandLineProgram {

    private static final String MOVEMENTS = "movements.csv";
    private static final String LOG       = "logger.log";
    private static final String AUTHORS   = "authorsDB.dat";
    private static final String BOOKS     = "booksDB.dat";

    private BufferedReader movements;
    private Logger         logger;
    private AuthorsFile    authorsDB;
    private BooksFile      booksDB;

    public static void main(String[] args) {
        new PicoBabel().start(args);
    }

    @Override
    public void run() {
        try {
            openFiles();
            resetData();
            processMovements();
        } catch (IOException e) {
            println("Glups !!!");
            e.printStackTrace(getWriter());
        } finally {
            try {
                closeFiles();
            } catch (IOException e) {
                e.printStackTrace(getWriter());
            }
        }
    }

    private void openFiles() throws IOException {
        this.movements = new BufferedReader(new FileReader(MOVEMENTS));
        this.logger = new Logger(LOG);
        this.authorsDB = new AuthorsFile(AUTHORS);
        this.booksDB = new BooksFile(BOOKS);
    }

    private void resetData() throws IOException {
        this.authorsDB.reset();
        this.booksDB.reset();
    }

    private void closeFiles() throws IOException {
        this.movements.close();
        this.logger.close();
        this.authorsDB.close();
        this.booksDB.close();
    }

    private void processMovements() throws IOException {
        String line = movements.readLine();
        while(line != null){
            processMovement(line);
            line = movements.readLine();
        }
    }

    private void processMovement(String movement) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(movement,",");
        String operation = tokenizer.nextToken();
        switch (operation) {
            case "ALTA_AUTOR" -> {
                String author = tokenizer.nextToken();
                processNewAuthor(author);
            }
            case "ALTA_LIBRO" -> {
                String title = tokenizer.nextToken();
                long authorId = Long.parseLong(tokenizer.nextToken());
                processNewBook(title, authorId);
            }
            case "INFO_AUTOR" -> {
                long authorId = Long.parseLong(tokenizer.nextToken());
                processAuthorInfo(authorId);
            }
            case "INFO_LIBRO" -> {
                long bookId = Long.parseLong(tokenizer.nextToken());
                processBookInfo(bookId);
            }
            default -> logger.errorUnknownOperation(operation);
        }
    }

    private void processNewAuthor(String name) throws IOException {
        long id = authorsDB.nextAuthorId();
        Author author = new Author(id,name);
        authorsDB.writeAuthor(author);
        logger.okNewAuthor(author);
    }

    private void processNewBook(String title, long authorId) throws IOException {
        if(!authorsDB.isValidId(authorId)){
            logger.errorNewBook(authorId);
            return;
        }
        long bookId = booksDB.nextBookId();
        updatePreviousBook(bookId, authorId);
        Book book = new Book(bookId,title,authorId);
        booksDB.writeBook(book);
        Author author = authorsDB.readAuthor(authorId);
        author.addBookId(bookId);
        authorsDB.writeAuthor(author);
        logger.okNewBook(book);
    }

    private void updatePreviousBook(long bookId, long authorId) throws IOException {
        Author author = authorsDB.readAuthor(authorId);
        long lastId = author.getLastBookId();
        if(booksDB.isValidId(lastId)) {
            Book book = booksDB.readBook(lastId);
            book.setNextBookId(bookId);
            booksDB.writeBook(book);
        }
    }

    private void processAuthorInfo(long authorId) throws IOException {
        if(!authorsDB.isValidId(authorId)){
            logger.errorInfoAutor(authorId);
            return;
        }
        Author author = authorsDB.readAuthor(authorId);
        Book[] collection = booksDB.getBooksForAuthor(author);
        logger.okInfoAutor(author,collection);
    }

    private void processBookInfo(long bookId) throws IOException {
        if(!booksDB.isValidId(bookId)){
            logger.errorInfoBook(bookId);
            return;
        }
        Book book = booksDB.readBook(bookId);
        Author author = authorsDB.readAuthor(book.getAuthorId());
        logger.okInfoBook(book,author);
    }
}
