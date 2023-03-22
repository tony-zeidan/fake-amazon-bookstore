package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.PurchaseHistory;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, Long> {
    PurchaseHistory findById(long id);
}
