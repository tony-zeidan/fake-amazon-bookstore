package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.*;
import fakeamazon.bookstore.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Calendar;
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

//	@EventListener(ApplicationReadyEvent.class)
	public void addToOrderHistory() {
		Customer customer = customerRepo.findByUsername("user");
		Customer customer1 = customerRepo.findByUsername("Nick");
		List<Book> books = bookRepo.findAll();
		List<PurchaseItem> purchaseItems = new ArrayList<>();
		List<PurchaseItem> purchaseItems1 = new ArrayList<>();
		PurchaseHistory purchaseHistory = customer.getHistory();
		PurchaseHistory purchaseHistory1 = customer1.getHistory();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();
		for (int i=0; i < books.size(); i++) {
			Book book = books.get(i);
			PurchaseItem purchaseItem = new PurchaseItem();
			purchaseItem.setBook(book);
			purchaseItem.setQuantity(1);
			purchaseItem.setCreatedAt(today);
			purchaseItemRepository.save(purchaseItem);
			purchaseItems.add(purchaseItem);
			purchaseHistory.addToHistory(purchaseItem);
			if (i > 2) {
				PurchaseItem purchaseItem1 = new PurchaseItem();
				purchaseItem1.setBook(book);
				purchaseItem1.setQuantity(2);
				purchaseItem1.setCreatedAt(yesterday);
				purchaseItemRepository.save(purchaseItem1);
				purchaseItems1.add(purchaseItem1);
				purchaseHistory1.addToHistory(purchaseItem1);
			}
		}
		customer.setHistory(purchaseHistory);
		customer1.setHistory(purchaseHistory1);
		customerRepo.save(customer);
		customerRepo.save(customer1);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackendApplication.class, args);
	}
}
