package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final CustomerRepository customerRepo;
    public RecommendationService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }
    public ArrayList<Book> getRecommendations(Customer customer) {
        double min = 1.0;
        Customer customerRecommend = customer;

        for(Customer compCustomer: customerRepo.findAll()) {
            if (compCustomer.getUsername() == customer.getUsername()) continue;

            double jaccardDistance = calculateJaccardDistance(customer, compCustomer);
            if (jaccardDistance != 0 && min > jaccardDistance) {
                min = jaccardDistance;
                customerRecommend = compCustomer;
            }
        }

        Set<Book> customerBooks = customer.getHistory().getHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        Set<Book> customerRecommendBooks = customerRecommend.getHistory().getHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        customerRecommendBooks.removeAll(customerBooks);
        return new ArrayList<>(customerRecommendBooks);
    }
    private double calculateJaccardDistance(Customer customer1, Customer customer2) {
        Set<Book> customer1Books = customer1.getHistory().getHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        if (customer1Books.size() == 0) return 1.0;

        Set<Book> customer2Books = customer2.getHistory().getHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        Set<Book> union = new HashSet<>(customer1Books);
        union.addAll(customer2Books);
        Set<Book> intersection = customer1Books.stream().filter(book->customer2Books.contains(book)).collect(Collectors.toSet());
        return 1.0 - (intersection.size()/(double)union.size());
    }
}
