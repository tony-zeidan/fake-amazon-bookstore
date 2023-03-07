package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("owner")
public class BookOwnerController {

    private final BookRepository bookRepository;

    @Autowired
    public BookOwnerController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public String getAdminBookStore(Model model) {
        return "bookstoreadmin";
    }

    @GetMapping("upload")
    public String getAdminUpload() {
        return "bookstoreupload";
    }
    @GetMapping("edit")
    public String editBook(@RequestParam(value="bookId") String bookId, Model model){
        Book targetBook = bookRepository.findById(Long.parseLong(bookId));
        model.addAttribute("targetBook", targetBook);
        return "editbookpage";
    }
}
