package files;

import domain.Author;
import domain.Book;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private final BufferedWriter output;

    public Logger(String name) throws IOException {
        this.output = new BufferedWriter(new FileWriter(name));
    }

    private void writeln(String message) throws IOException {
        output.write(message);
        output.newLine();
    }

    public void errorUnknownOperation(String operation) throws IOException {
        writeln(String.format("ERROR Operación desconocida: %s", operation));
    }

    public void okNewAuthor(Author author) throws IOException {
        writeln(String.format("OK ALTA AUTOR %s", author));
    }

    public void okNewBook(Book book) throws IOException {
        writeln(String.format("OK ALTA LIBRO %s", book));
    }

    public void errorNewBook(long idAuthor) throws IOException {
        writeln(String.format("ERROR ALTA LIBRO (Autor inexistente) %d", idAuthor));
    }

    public void okInfoAutor(Author author, Book[] books) throws IOException {
        writeln(String.format("OK INFO AUTOR %s", author));
        for (int i = 0; i < books.length; i++) {
            writeln(String.format("    %d - %s", i + 1, books[i]));
        }
    }

    public void errorInfoAutor(long idAuthor) throws IOException {
        writeln(String.format("ERROR INFO AUTOR (Autor inexistente) %d", idAuthor));
    }

    public void okInfoBook(Book book, Author author) throws IOException {
        writeln(String.format("OK INFO LIBRO %s", book));
        writeln(String.format("   - %s", author));
    }

    public void errorInfoBook(long idBook) throws IOException {
        writeln(String.format("ERROR INFO LIBRO (Libro inexistente) %d", idBook));
    }

    public void close() throws IOException {
        output.close();
    }
}
