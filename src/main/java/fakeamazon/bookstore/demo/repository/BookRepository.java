package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    /**
     * Find a book by its ID.
     *
     * @param id Book ID to find
     * @return The book entity
     */
    Book findById(long id);

    /**
     * Can also use the unique ISBN to search for books.
     *
     * @param isbn The isbn to find
     * @return The book entity
     */
    Book findByIsbn(String isbn);
}
