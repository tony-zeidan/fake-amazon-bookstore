package fakeamazon.bookstore.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
public class BookOwnerController {

    @GetMapping("/upload")
    public String getUploadForm() {
        return "bookstoreupload";
    }
}
