package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findById(long id);

    List<Book> findByName(String name);

    Book findByIsbn(String isbn);
}
