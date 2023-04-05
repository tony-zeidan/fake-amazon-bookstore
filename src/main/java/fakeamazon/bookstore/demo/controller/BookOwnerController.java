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

    /**
     * Controller for all page-related and owner-related pages.
     *
     * @param bookRepository Book repository to modify and grab books
     * @param customerRepository Customer repository to grab customer history
     */
    @Autowired
    public BookOwnerController(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Get the admin index page.
     *
     * @param model Spring boot model
     * @return Admin index page
     */
    @GetMapping("")
    public String getAdminBookStore(Model model) {
        return "bookstoreadmin";
    }

    /**
     * Get the upload page.
     *
     * @return The admin upload page
     */
    @GetMapping("upload")
    public String getAdminUpload() {
        return "bookstoreupload";
    }

    /**
     * Get the edit book page.
     *
     * @param bookId Book ID to edit
     * @param model Spring boot model
     * @return Edit book page configured for given ID
     */
    @GetMapping("edit")
    public String editBook(@RequestParam(value="bookId") String bookId, Model model){
        Book targetBook = bookRepository.findById(Long.parseLong(bookId));
        model.addAttribute("targetBook", targetBook);
        return "editbookpage";
    }

    /**
     * Returns a view for a Customer's purchase history or an admin view of all username who has purchase history.
     *
     * @param username (optional) the username which the admin wants to view their history
     * @param model the Model object to add attributes to for the view
     * @return name of the view
     */
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
