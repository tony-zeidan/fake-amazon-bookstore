package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {

    /**
     * Find the shopping cart item with the given book id and the customer username.
     *
     * @param book_Id book ID to find
     * @param customer_username Username to find
     * @return The shopping cart item for the givens
     */
    ShoppingCartItem findShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);

    /**
     * Delete the shopping cart item with the given book id and the customer username.
     *
     * @param book_Id book ID to find
     * @param customer_username Username to find
     */
    void deleteShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);

    /**
     * Find all the shopping cart items for the given customer username.
     *
     * @param customer_username Username to find
     * @return All the books for that user
     */
    List<ShoppingCartItem> findAllByCustomer_Username(String customer_username);
}
