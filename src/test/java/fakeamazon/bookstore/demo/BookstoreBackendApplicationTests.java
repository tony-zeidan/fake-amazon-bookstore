package fakeamazon.bookstore.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookstoreBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAllBooks() throws Exception {

		InputStream is = BookstoreBackendApplicationTests.class.getClassLoader().getResourceAsStream("testbook.jpg");
		MockMultipartFile bookImg = new MockMultipartFile("bookimage", is);
		is = BookstoreBackendApplicationTests.class.getClassLoader().getResourceAsStream("testbook.json");
		MockMultipartFile bookDetails = new MockMultipartFile("bookdetails", is);

		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/owner-rest/upload")
						.file(bookImg)
						.file(bookDetails)
				).andExpect(status().isCreated());
	}
}
