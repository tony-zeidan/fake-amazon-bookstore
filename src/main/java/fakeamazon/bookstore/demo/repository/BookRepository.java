package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Book findById(long id);

    List<Book> findByName(String name);

    Book findByIsbn(String isbn);
}
