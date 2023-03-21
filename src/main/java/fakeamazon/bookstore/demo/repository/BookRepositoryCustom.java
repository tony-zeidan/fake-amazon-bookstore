package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {

    Page<Book> getFilterBooks(String name, String isbn, String description, String publisher, Pageable pageable);

}