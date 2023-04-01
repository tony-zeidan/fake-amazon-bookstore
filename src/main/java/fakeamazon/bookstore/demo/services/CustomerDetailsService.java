package fakeamazon.bookstore.demo.services;

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

    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepo, PurchaseHistoryRepository purchaseHistoryRepository) {
        this.customerRepo = customerRepo;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    public Customer makeCustomer(String username) {
        Customer customer = new Customer();
        customer.setUsername(username);         // PRIMARY KEY needs to be set
        PurchaseHistory newPurchaseHistory = new PurchaseHistory();
        purchaseHistoryRepository.save(newPurchaseHistory);
        customer.setHistory(newPurchaseHistory);
        return customerRepo.save(customer);
    }

    public Customer getCustomerDetails(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        Customer customer = customerRepo.findByUsername(details.getUsername());
        return customer;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }
}
