package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.PurchaseHistory;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsService {

    private final CustomerRepository customerRepo;

    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer makeCustomer(String username) {
        Customer customer = new Customer();
        customer.setUsername(username);         // PRIMARY KEY needs to be set
        customer.setHistory(new PurchaseHistory());
        return customerRepo.save(customer);
    }

    public Customer getCustomerDetails(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        System.out.println(details);

        List<Customer> customers = (List<Customer>) customerRepo.findAll();
        for (Customer c: customers) {
            System.out.println(c.getUsername());
        }

        Customer customer = customerRepo.findByUsername(details.getUsername());
        return customer;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }
}
