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

    public Optional<Book> updateQuantity(Long currId, Integer newQuantity){
        Optional<Book> currBook = bookRepository.findById(currId);
        currBook.ifPresent(book -> {
            book.setQuantity(newQuantity);
            bookRepository.save(book);
        });
        //Will return empty Optional<Book> if book not found with specified ID
        return currBook;
    }
}
