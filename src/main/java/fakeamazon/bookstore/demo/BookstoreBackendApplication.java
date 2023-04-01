package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.*;
import fakeamazon.bookstore.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BookstoreBackendApplication {

	private CustomerRepository customerRepo;
	private BookRepository bookRepo;
	private ShoppingCartItemRepository itemRepo;
	private PurchaseHistoryRepository purchaseHistoryRepository;
	private PurchaseItemRepository purchaseItemRepository;

	@Autowired
	public BookstoreBackendApplication(CustomerRepository customerRepo, BookRepository bookRepo, ShoppingCartItemRepository itemRepo, PurchaseItemRepository purchaseItemRepository, PurchaseHistoryRepository purchaseHistoryRepository) {
		this.customerRepo = customerRepo;
		this.bookRepo = bookRepo;
		this.itemRepo = itemRepo;
		this.purchaseItemRepository = purchaseItemRepository;
		this.purchaseHistoryRepository = purchaseHistoryRepository;
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

	@EventListener(ApplicationReadyEvent.class)
	public void addToOrderHistory() {
		Customer customer = customerRepo.findByUsername("user");
		List<Book> books = bookRepo.findAll();
		List<PurchaseItem> purchaseItems = new ArrayList<>();
		PurchaseHistory purchaseHistory = customer.getHistory();
		for (Book book: books) {
			PurchaseItem purchaseItem = new PurchaseItem();
			purchaseItem.setBook(book);
			purchaseItem.setQuantity(1);
			purchaseItem.setCreatedAt(new Date());
			purchaseItemRepository.save(purchaseItem);
			purchaseItems.add(purchaseItem);
			purchaseHistory.addToHistory(purchaseItem);
		}
		customer.setHistory(purchaseHistory);
		customerRepo.save(customer);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackendApplication.class, args);
	}
}
