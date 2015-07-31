package com.akin.libapp.service;

import org.springframework.stereotype.Service;

import com.akin.libapp.exception.ItemNotFoundException;
import com.akin.libapp.model.Book;

@Service
public interface BookService {

	Book addBookToCollection(Book book);
	
	Book updateBook(Book book);
	
	void deleteBook(String id);
	
	Book getBookById(String id) throws ItemNotFoundException;
	
	Iterable<Book> getAllBooks();

}
