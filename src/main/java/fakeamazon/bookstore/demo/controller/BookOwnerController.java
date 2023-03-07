package fakeamazon.bookstore.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("owner")
public class BookOwnerController {

    @Autowired
    public BookOwnerController() {
    }

    @GetMapping("")
    public String getAdminBookStore(Model model) {
        return "bookstoreadmin";
    }

    @GetMapping("upload")
    public String getAdminUpload() {
        return "bookstoreupload";
    }

}
