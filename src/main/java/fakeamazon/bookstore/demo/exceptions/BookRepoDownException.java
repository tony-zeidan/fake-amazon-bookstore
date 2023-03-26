package fakeamazon.bookstore.demo.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;

public class BookRepoDownException extends HttpServerErrorException {
    public BookRepoDownException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
