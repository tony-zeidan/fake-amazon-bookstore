package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    @Autowired
    private CustomerRepository customerRepo;

    /**
     * Gets the books to recommend for the customer.
     * @param customer the customer that needs recommendations.
     * @return ArrayList<Book> that are the recommended books.
     */
    @LoggedServiceOperation
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

        Set<Book> customerBooks = customer.getHistory().getPurchaseItemHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        Set<Book> customerRecommendBooks = customerRecommend.getHistory().getPurchaseItemHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        customerRecommendBooks.removeAll(customerBooks);
        return new ArrayList<>(customerRecommendBooks);
    }

    /**
     * Calculates the Jaccard Distance of two customers to determine the best fitting recommendation.
     *
     * @param customer1 customer that needs recommendations.
     * @param customer2 customer that is being compared to.
     * @return double a value from 1 to 0 that represents its fitness to be the recommendation.
     */
    private double calculateJaccardDistance(Customer customer1, Customer customer2) {
        Set<Book> customer1Books = customer1.getHistory().getPurchaseItemHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        if (customer1Books.size() == 0) return 1.0;

        Set<Book> customer2Books = customer2.getHistory().getPurchaseItemHistory().stream().map(x-> x.getBook()).collect(Collectors.toSet());
        Set<Book> union = new HashSet<>(customer1Books);
        union.addAll(customer2Books);
        Set<Book> intersection = customer1Books.stream().filter(book->customer2Books.contains(book)).collect(Collectors.toSet());
        return 1.0 - (intersection.size()/(double)union.size());
    }
}
