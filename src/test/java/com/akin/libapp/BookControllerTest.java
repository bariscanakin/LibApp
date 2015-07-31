package com.akin.libapp;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.akin.libapp.controller.BookController;
import com.akin.libapp.model.Book;
import com.akin.libapp.service.BookService;
import com.akin.libapp.service.CaptchaService;

public class BookControllerTest {

	@Mock
	private BookService bookService;

	@Mock
	private CaptchaService captchaService;

	@InjectMocks
	private BookController bookController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void delete_sucess() throws Exception {
		mockMvc.perform(delete("/book?id=123")).andDo(print());
		verify(bookService).deleteBook("123");
	}

	@Test
	public void getById_success() throws Exception {
		Book book = new Book("123", "title", "author");
		when(bookService.getBookById("123")).thenReturn(book);
		mockMvc.perform(get("/book?id=123")).andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.data.title", is("title"))).andExpect(jsonPath("$.data.author", is("author")))
				.andDo(print());
	}

	@Test
	public void getAll_success() throws Exception {
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book("title", "author"));
		when(bookService.getAllBooks()).thenReturn(bookList);
		mockMvc.perform(get("/book/getAll")).andExpect(jsonPath("$[0].title", is("title")))
				.andExpect(jsonPath("$[0].author", is("author"))).andDo(print());
	}
}
