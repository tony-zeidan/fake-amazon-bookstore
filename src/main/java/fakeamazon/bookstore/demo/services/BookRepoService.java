package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookRepoService {

    private final BookRepository bookRepo;

    @Autowired
    public BookRepoService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book upload(Book book) {
        return bookRepo.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id).get();
    }

    public Map<String, Object> getAllBooks(String name, String isbn, String description, String publisher, Pageable paging) {
        Page<Book> booksPage = bookRepo.getFilterBooks(name, isbn, description, publisher, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("books", booksPage.getContent());
        response.put("currentPage", booksPage.getNumber());
        response.put("totalItems", booksPage.getTotalElements());
        response.put("totalPages", booksPage.getTotalPages());

        return response;
    }
}
