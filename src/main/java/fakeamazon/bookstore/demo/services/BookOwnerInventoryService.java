package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.exceptions.QuantityInvalidException;
import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookOwnerInventoryService {
    private final BookRepository bookRepository;

    @Autowired
    public BookOwnerInventoryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @LoggedServiceOperation
    public Optional<Book> updateQuantity(Long currId, Integer newQuantity){
        Optional<Book> currBookLookup = bookRepository.findById(currId);
        if (currBookLookup.isEmpty()) return currBookLookup;

        Book currBook = currBookLookup.get();
        if (currBook.getQuantity() + newQuantity > 0){
            currBook.setQuantity(currBook.getQuantity() + newQuantity);
            bookRepository.save(currBook);
        } else {
            throw new QuantityInvalidException(currBook.getName());
        }
        //Will return empty Optional<Book> if book not found with specified ID or quantity invalid
        return currBookLookup;
    }
}
