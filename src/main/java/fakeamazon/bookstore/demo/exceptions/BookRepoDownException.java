package fakeamazon.bookstore.demo.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;


public class BookRepoDownException extends HttpServerErrorException {

    /**
     * A custom exception that is thrown when the book repository is down.
     *
     * @param code The original status code thrown
     * @param message The error message
     */
    public BookRepoDownException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
