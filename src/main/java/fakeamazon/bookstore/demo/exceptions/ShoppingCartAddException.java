package fakeamazon.bookstore.demo.exceptions;

public class ShoppingCartAddException extends RuntimeException {

    private final long actual;

    /**
     * An exception thrown when the cart can't be added to.
     *
     * @param actual The ID given during the add operation
     */
    public ShoppingCartAddException(long actual) {
        super("Couldn't add shopping cart item; BOOKID="+actual);
        this.actual = actual;
    }

    /**
     * Retrieve the actual id of the book attempt
     *
     * @return book actual id
     */
    public long getActual() {
        return actual;
    }
}
