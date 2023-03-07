package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOwnerUploadService {

    @Autowired
    private BookRepository bookRepo;

    public Book upload(Book book) {
        return bookRepo.save(book);
    }
}