package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c WHERE SIZE(c.history.purchaseItemHistory) > 0")
    List<Customer> findCustomersWithPurchaseItems();
}
