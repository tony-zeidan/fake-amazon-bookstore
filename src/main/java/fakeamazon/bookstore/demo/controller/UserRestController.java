package fakeamazon.bookstore.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useractions")
public class UserRestController {

    @Autowired
    public UserRestController() {
    }
}
