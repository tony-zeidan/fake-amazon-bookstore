package fakeamazon.bookstore.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
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
		MockMultipartFile bookImg = new MockMultipartFile("bookimage","testbook", MediaType.IMAGE_JPEG_VALUE, is);
		is = BookstoreBackendApplicationTests.class.getClassLoader().getResourceAsStream("testbook.json");
		MockMultipartFile bookDetails = new MockMultipartFile("bookdetails","testbook",MediaType.APPLICATION_JSON_VALUE, is);

		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/owneractions/upload")
						.file(bookImg)
						.file(bookDetails)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
				).andExpect(status().isCreated());
	}
}
