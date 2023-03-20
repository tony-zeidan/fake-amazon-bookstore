package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);
}