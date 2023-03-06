package fakeamazon.bookstore.demo;

import fakeamazon.bookstore.demo.model.Book;
import fakeamazon.bookstore.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookstoreBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Test
	void testGetAllBooks() throws Exception {
		this.mockMvc.perform(get("/getAllBooks")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));

		bookRepository.save(new Book("book1", "desc"));

		this.mockMvc.perform(get("/getAllBooks")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[{\"id\":1,\"name\":\"book1\",\"description\":\"desc\"}]")));

		bookRepository.save(new Book("book2", "desc2"));

		this.mockMvc.perform(get("/getAllBooks")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[{\"id\":1,\"name\":\"book1\",\"description\":\"desc\"},{\"id\":2,\"name\":\"book2\",\"description\":\"desc2\"}]")));

	}

}
