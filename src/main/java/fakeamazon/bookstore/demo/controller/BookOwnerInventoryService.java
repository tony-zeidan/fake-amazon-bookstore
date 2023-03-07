package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookOwnerInventoryService {
    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> updateQuantity(Book newBook){
        Optional<Book> currBook = bookRepository.findById(newBook.getId());
        if (!currBook.isPresent()){
            return Optional.empty();
        } else {
            //repo.save() updates book if ID matches existing book
            return Optional.of(bookRepository.save(newBook));
        }
    }
}
