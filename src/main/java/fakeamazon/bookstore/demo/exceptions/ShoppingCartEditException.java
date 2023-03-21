package fakeamazon.bookstore.demo.exceptions;

import javax.management.RuntimeErrorException;

public class ShoppingCartEditException extends RuntimeException {

    private final int actual;

    private final int attempted;

    public ShoppingCartEditException(int actual, int attempted) {
        super("Couldn't change quantity of shopping cart item; Actual="+actual+" Attempted="+attempted);
        this.actual = actual;
        this.attempted = attempted;
    }

    public int getAttempted() {
        return attempted;
    }

    public int getActual() {
        return actual;
    }
}
