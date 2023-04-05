package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.PurchaseHistory;
import fakeamazon.bookstore.demo.model.PurchaseItem;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.services.RecommendationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for recommendation-related functionality.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class RecommendServiceImplIntegrationTest {

    @TestConfiguration
    static class RecommendServiceImplTestContextConfiguration {
        @Bean
        public RecommendationService recommendService() {
            return new RecommendationService();
        }
    }

    @Autowired
    private RecommendationService recommendationService;
    @MockBean
    private CustomerRepository customerRepo;

    private Customer bob;
    private Customer alice;

    /**
     * Setup books and users.
     */
    @Before
    public void setUp() {
        bob = new Customer();
        bob.setUsername("Bob");
        Book commonBook = new Book();
        commonBook.setName("commonBook");
        commonBook.setId(1L);

        Book bobBook = new Book();
        bobBook.setName("Bob Book");
        bobBook.setId(2L);

        PurchaseItem item1 = new PurchaseItem();
        item1.setBook(commonBook);

        PurchaseItem item2 = new PurchaseItem();
        item2.setBook(bobBook);

        List<PurchaseItem> history = new LinkedList<>();
        history.add(item1);
        history.add(item2);

        PurchaseHistory bobPurchases = new PurchaseHistory();
        bobPurchases.setPurchaseItemHistory(history);
        bob.setHistory(bobPurchases);


        alice = new Customer();
        alice.setUsername("Alice");

        Book aliceBook = new Book();
        aliceBook.setName("Alice Book");
        aliceBook.setId(3L);

        PurchaseItem item3 = new PurchaseItem();
        item3.setBook(aliceBook);


        List<PurchaseItem> history2 = new LinkedList<>();
        history2.add(item3);
        history2.add(item1);

        PurchaseHistory alicePurchases = new PurchaseHistory();
        alicePurchases.setPurchaseItemHistory(history2);
        alice.setHistory(alicePurchases);

        Mockito.when(customerRepo.findAll()).thenReturn(Arrays.asList(bob,alice));

    }

    /**
     * Test the ability of the system to provide accurate recommendations based on Jaccard distance.
     */
    @Test
    public void RecommendServiceTest() {
        List<Book> books = recommendationService.getRecommendations(bob);
        System.out.println(books);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0).getName(), "Alice Book");

        books = recommendationService.getRecommendations(alice);
        System.out.println(books);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0).getName(), "Bob Book");

    }
}
