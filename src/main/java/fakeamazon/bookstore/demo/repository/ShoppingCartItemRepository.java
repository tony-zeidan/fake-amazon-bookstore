package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {

    ShoppingCartItem findById(long id);
}
