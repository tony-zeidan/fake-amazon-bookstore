package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import fakeamazon.bookstore.demo.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private final CustomerDetailsService detailsService;
    private final UserService userService;
    private final RecommendationService recommendationService;

    /**
     * User controller for all page-related functions.
     *
     * @param detailsService Details service to get customer entities
     * @param userService User service to create new users
     * @param recommendationService Recommendation service to get recommended books
     */
    @Autowired
    public UserController(CustomerDetailsService detailsService, final UserService userService, RecommendationService recommendationService) {
        this.detailsService = detailsService;
        this.userService = userService;
        this.recommendationService = recommendationService;
    }

    /**
     * Endpoint for rendering the user registration page.
     *
     * @param model the Spring UI model used for rendering the view
     * @return the name of the user registration view to render
     */
    @GetMapping("/registration")
    public String registerUser(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "userregistration";
    }

    /**
     * Endpoint for processing user registration requests from the frontend.
     *
     * @param userDto the data transfer object used to fill in the newlu-created user
     * @param bindingResult the result of the mapping of request parameters to the DTO
     * @return the name of the view to which the user will be redirected
     */
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

    /**
     * Endpoint for fetching the user index page.
     *
     * @param auth Spring boot authentication context
     * @param model Spring boot model
     * @return User index
     */
    @GetMapping("")
    public String getUserIndex(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        model.addAttribute("cart", customer.getCart().size());
        return "userindex";
    }

    /**
     * Endpoint for fetching of the viewcart page.
     *
     * @param auth Spring boot authentication context
     * @param model Spring boot model
     * @return Viewcart page
     */
    @GetMapping("viewcart")
    public String getUserCartPage(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("cart", customer.getCart());
        return "cartview";
    }

    /**
     * Returns the purchase history view for the current customer.
     *
     * @param auth the Authentication object
     * @param model the Model object to set the attribute of username and purchase history
     * @return the name of the view that displays the purchase history
     */
    @GetMapping("history")
    public String getUserHistoryPage(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("items", customer.getHistory().getPurchaseItemHistory());
        return "historyview";
    }

    /**
     * Endpoint for Getting a customers recommendation page.
     * @param auth Spring boot authentication context
     * @param model Spring boot model for recommendation page
     * @return
     */
    @GetMapping("viewrecommendations")
    public String getUserRecommendationsPage(Authentication auth, Model model) {
        Customer customer = detailsService.getCustomerDetails(auth);
        List<Book> items = recommendationService.getRecommendations(customer);
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("cart", customer.getCart());
        model.addAttribute("recommendations", items);
        return "recommendationsview";
    }
}