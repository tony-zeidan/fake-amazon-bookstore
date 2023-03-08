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
        Optional<Book> currBookLookup = bookRepository.findById(currId);
        if (currBookLookup.isEmpty()) return currBookLookup;

        Book currBook = currBookLookup.get();
        if (currBook.getQuantity() + newQuantity > 0){
            currBook.setQuantity(currBook.getQuantity() + newQuantity);
            bookRepository.save(currBook);
        } else {
            throw new IllegalArgumentException();
        }
        //Will return empty Optional<Book> if book not found with specified ID or quantity invalid
        return currBookLookup;
    }
}
