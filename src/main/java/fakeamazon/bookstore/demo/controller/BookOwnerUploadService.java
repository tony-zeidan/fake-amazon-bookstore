package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BookOwnerUploadService {

    @Autowired
    private BookRepository bookRepo;

    public Book upload(PartialBookBody book) {
        Book toAdd = new Book();
        toAdd.setName(book.getName());
        toAdd.setDescription(book.getDescription());
        toAdd.setIsbn(book.getIsbn());
        toAdd.setQuantity(book.getQuantity());
        toAdd.setPublisher(book.getPublisher());
        toAdd.setPicture(book.getPicture());
        return bookRepo.save(toAdd);
    }
}
