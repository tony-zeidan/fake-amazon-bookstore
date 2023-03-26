package fakeamazon.bookstore.demo.exceptions;

public class BookRepoDownException extends RuntimeException  {
    public BookRepoDownException(String message) {
        super(message);
    }
}
