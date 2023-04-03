package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookRepoService {

    private final BookRepository bookRepo;

    @Autowired
    public BookRepoService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @LoggedServiceOperation
    public Book upload(Book book) {
        return bookRepo.save(book);
    }

    @LoggedServiceOperation
    public Book getBookById(Long id) {
        return bookRepo.findById(id).get();
    }

}
