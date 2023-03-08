package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackendApplication.class, args);
	}
}
