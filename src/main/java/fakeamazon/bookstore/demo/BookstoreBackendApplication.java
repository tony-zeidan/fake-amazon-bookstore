package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class BookstoreBackendApplication {

	private CustomerRepository customerRepo;
	private BookRepository bookRepo;
	private ShoppingCartItemRepository itemRepo;

	@Autowired
	public BookstoreBackendApplication(CustomerRepository customerRepo, BookRepository bookRepo, ShoppingCartItemRepository itemRepo) {
		this.customerRepo = customerRepo;
		this.bookRepo = bookRepo;
		this.itemRepo = itemRepo;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void addToCart() {
		Customer customer = customerRepo.findByUsername("user");
		List<Book> books = bookRepo.findAll();
		for (Book b: books) {
			ShoppingCartItem item = new ShoppingCartItem();
			item.setCustomer(customer);
			item.setQuantity(1);
			item.setBook(b);
			itemRepo.save(item);

			customer.addToCart(item);
		}
		customerRepo.save(customer);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackendApplication.class, args);
	}
}
