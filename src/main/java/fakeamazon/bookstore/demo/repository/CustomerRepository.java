package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    /**
     * Find a customer by their username.
     *
     * @param username Customer username
     * @return Customer entity
     */
    Customer findByUsername(String username);
}
