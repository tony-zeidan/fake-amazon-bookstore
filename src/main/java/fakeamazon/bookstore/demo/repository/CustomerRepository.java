package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    /**
     * Find a customer by their username.
     *
     * @param username Customer username
     * @return Customer entity
     */
    Customer findByUsername(String username);

    /**
     * Retrieves a list of Customers from the database who have at least one PurchaseItem in their PurchaseHistory.
     *
     * @return a List of Customers who have at least one PurchaseItem in their PurchaseHistory
     */
    @Query("SELECT c FROM Customer c WHERE SIZE(c.history.purchaseItemHistory) > 0")
    List<Customer> findCustomersWithPurchaseItems();
}
