package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import fakeamazon.bookstore.demo.dto.UserDTO;
import fakeamazon.bookstore.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    private final CustomerDetailsService detailsService;

    private final UserService userService;

    @Autowired
    public UserController(CustomerDetailsService detailsService, final UserService userService) {
        this.detailsService = detailsService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registerUser(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "userregistration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            BindingResult bindingResult) {
        try {
            final UserDetails user = this.userService.registerNewUser(userDto);
        } catch (final IllegalArgumentException e) {
            bindingResult.reject("user.registration.invalid", e.getMessage());
            return "userregistration";
        }
        return "redirect:/login";

    }

    @GetMapping("")
    public String getUserIndex(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        model.addAttribute("cart", customer.getCart().size());
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