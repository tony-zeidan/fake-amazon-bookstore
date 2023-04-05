package fakeamazon.bookstore.demo.exceptions;

import javax.management.RuntimeErrorException;

public class ShoppingCartEditException extends RuntimeException {

    private final int actual;

    private final int attempted;

    /**
     * An exception thrown when an edit on inventory could not be processed.
     *
     * @param actual The actual quantity of the book
     * @param attempted The quantity attempted
     */
    public ShoppingCartEditException(int actual, int attempted) {
        super("Couldn't change quantity of shopping cart item; Actual="+actual+" Attempted="+attempted);
        this.actual = actual;
        this.attempted = attempted;
    }

    /**
     * Retrieve quantity attempted
     *
     * @return attempted quantity
     */
    public int getAttempted() {
        return attempted;
    }

    /**
     * Retrieve quantity that there actually is
     *
     * @return actual quantity
     */
    public int getActual() {
        return actual;
    }
}
