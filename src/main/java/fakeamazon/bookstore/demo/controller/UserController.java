package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    private final CustomerDetailsService detailsService;

    @Autowired
    public UserController(CustomerDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @GetMapping("")
    public String getUserIndex() {
        return "userindex";
    }

    @GetMapping("viewcart")
    public String getUserCartPage(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("cart", customer.getCart());
        return "cartview";
    }
}
