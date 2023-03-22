package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.PurchaseItem;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseItemRepository extends CrudRepository<PurchaseItem, Long> {
    PurchaseItem findById(long id);
}
