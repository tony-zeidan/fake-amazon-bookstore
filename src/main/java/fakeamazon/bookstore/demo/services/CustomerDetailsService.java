package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.PurchaseHistory;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.repository.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CustomerDetailsService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CustomerRepository customerRepo;

    /**
     * Service that allows for the getting of customer details.
     *
     * @param customerRepo The customer crud repository
     * @param purchaseHistoryRepository The purchase history crud repository
     */
    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepo, PurchaseHistoryRepository purchaseHistoryRepository) {
        this.customerRepo = customerRepo;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    /**
     * Service function to make a new user.
     *
     * @param username The username of the new user
     * @return The user added to the repo
     */
    @LoggedServiceOperation
    public Customer makeCustomer(String username) {
        Customer customer = new Customer();
        customer.setUsername(username);         // PRIMARY KEY needs to be set
        PurchaseHistory newPurchaseHistory = new PurchaseHistory();
        purchaseHistoryRepository.save(newPurchaseHistory);
        customer.setHistory(newPurchaseHistory);
        return customerRepo.save(customer);
    }

    /**
     * Get the details of the customer with the given authentication context.
     *
     * @param auth Spring boot authentication context
     * @return Customer entity
     */
    @LoggedServiceOperation
    public Customer getCustomerDetails(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        Customer customer = customerRepo.findByUsername(details.getUsername());
        return customer;
    }

    /**
     * Save a new customer.
     *
     * @param customer The customer repository
     * @return The customer saved to the repository
     */
    @LoggedServiceOperation
    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }
}
