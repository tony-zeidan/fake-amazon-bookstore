package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {

    ShoppingCartItem findById(long id);
    ShoppingCartItem findShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);
    void deleteShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);
}
