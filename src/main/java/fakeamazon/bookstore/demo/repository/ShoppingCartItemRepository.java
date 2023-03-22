package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {

    ShoppingCartItem findById(long id);
    ShoppingCartItem findShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);
    void deleteShoppingCartItemByBook_IdAndCustomer_Username(long book_Id, String customer_username);
    void deleteAllByCustomer(Customer customer);

    List<ShoppingCartItem> findAllByCustomer_Username(String customer_username);
}
