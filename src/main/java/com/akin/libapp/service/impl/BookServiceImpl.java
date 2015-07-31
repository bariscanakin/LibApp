package com.akin.libapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akin.libapp.exception.ItemNotFoundException;
import com.akin.libapp.model.Book;
import com.akin.libapp.repository.BookRepository;
import com.akin.libapp.service.BookService;

@Component
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book addBookToCollection(Book book) {
		Book saveResult = bookRepository.save(book);
		return saveResult;
	}

	@Override
	public Book updateBook(Book book) {
		Book saveResult = bookRepository.save(book);
		return saveResult;
	}

	@Override
	public void deleteBook(String id) {
		Book book = new Book();
		book.setId(id);
		bookRepository.delete(book);
	}
	
	@Override
	public Book getBookById(String id) throws ItemNotFoundException {
		Book foundBook = bookRepository.findOne(id);
		if (foundBook == null) {
			throw new ItemNotFoundException(Book.class.getName(), id);
		}
		return foundBook;
	}

	@Override
	public Iterable<Book> getAllBooks() {
		Iterable<Book> allBooks = bookRepository.findAll();
		return allBooks;
	}

}
