package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.dto.UserDTO;
import fakeamazon.bookstore.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
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
    public String getUserIndex() {
        return "userindex";
    }
}
