package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.configuration.TestSetup;
import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suite for general bookstore functions.
 */
@ExtendWith(SpringExtension.class)
@Import(TestSetup.class)
@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
class BookstoreTests {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BookRepository bookRepository;

    /**
     * Tests the ability of anyone in the bookstore to get all books with filters.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username="user222")
    void testGetAllBooksWithFilter() throws Exception {
        bookRepository.save(new Book("book1", "desc"));

        this.mockMvc.perform(get("http://localhost:8080/bookstore/getbooks?name=book")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\" : \"book1\"")))
                .andExpect(content().string(containsString("\"description\" : \"desc\"")));

        bookRepository.save(new Book("book2", "desc2"));

        this.mockMvc.perform(get("http://localhost:8080/bookstore/getbooks?description=desc")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\" : \"book1\"")))
                .andExpect(content().string(containsString("\"description\" : \"desc\"")))
                .andExpect(content().string(containsString("\"name\" : \"book2\"")))
                .andExpect(content().string(containsString("\"description\" : \"desc2\"")));

    }
}
