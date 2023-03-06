package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
