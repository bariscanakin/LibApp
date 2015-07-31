package com.akin.libapp;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.akin.libapp.model.Book;
import com.akin.libapp.repository.BookRepository;

public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;
	
	@Before
	public void steup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addBook_success() {
		Book book = new Book("title", "author");
		Book expectedBook = new Book("123", "title", "author");
		when(bookRepository.save(book)).thenReturn(expectedBook);
		Book returnedBook = bookRepository.save(book);
		assertEquals(expectedBook, returnedBook);
		verify(bookRepository).save(book);
	}
	
	public void editBook_success() {
		Book book = new Book("123", "title", "author");
		Book expectedBook = new Book("123", "title", "author");
		when(bookRepository.save(book)).thenReturn(expectedBook);
		Book returnedBook = bookRepository.save(book);
		assertEquals(expectedBook, returnedBook);
		verify(bookRepository).save(book);
		
	}
}
