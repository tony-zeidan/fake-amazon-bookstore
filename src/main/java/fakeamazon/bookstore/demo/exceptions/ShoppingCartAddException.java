package fakeamazon.bookstore.demo.exceptions;

public class ShoppingCartAddException extends RuntimeException {

    private final long actual;

    public ShoppingCartAddException(long actual) {
        super("Couldn't add shopping cart item; BOOKID="+actual);
        this.actual = actual;
    }

    public long getActual() {
        return actual;
    }
}
