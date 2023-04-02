package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.CustomerRepository;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.Optional;

@Controller
@RequestMapping("owner")
public class BookOwnerController {

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookOwnerController(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
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
    @GetMapping("history")
    public String history(@RequestParam(required = false) String username, Model model) {
        if (username == null) {
            return "adminhistoryview";
        } else {
            Customer customer = customerRepository.findByUsername(username);
            model.addAttribute("username", customer.getUsername());
            model.addAttribute("items", customer.getHistory().getPurchaseItemHistory());
            return "adminhistoryviewdetail";
        }
    }
}
