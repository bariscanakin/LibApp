package com.akin.libapp.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.akin.libapp.constant.SessionKeys;
import com.akin.libapp.exception.ItemNotFoundException;
import com.akin.libapp.model.Book;
import com.akin.libapp.model.BookWithCaptcha;
import com.akin.libapp.model.RestResponse;
import com.akin.libapp.service.BookService;
import com.akin.libapp.service.CaptchaService;

import nl.captcha.Captcha;

@RequestMapping("/books")
@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> addBook(@RequestBody BookWithCaptcha book, HttpSession session) {
		if (StringUtils.isBlank(book.getAuthor()) || StringUtils.isBlank(book.getTitle())
				|| StringUtils.isBlank(book.getCaptchaValue())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Captcha captcha = (Captcha) session.getAttribute(SessionKeys.CAPTCHA_KEY);
		Boolean isCaptchaCorrect = captchaService.checkIfCaptchaIsCorrect(captcha, book.getCaptchaValue());

		if (isCaptchaCorrect) {
			Book saveResult = bookService.addBookToCollection(book);
			return new ResponseEntity<RestResponse>(RestResponse.ok(saveResult), HttpStatus.OK);
		} else {
			return new ResponseEntity<RestResponse>(RestResponse.error("Invalid captcha!!"), HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> updateBook(@RequestBody BookWithCaptcha book, HttpSession session) {
		if (StringUtils.isBlank(book.getId()) || StringUtils.isBlank(book.getAuthor())
				|| StringUtils.isBlank(book.getCaptchaValue()) || StringUtils.isBlank(book.getTitle())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Captcha captcha = (Captcha) session.getAttribute(SessionKeys.CAPTCHA_KEY);
		Boolean isCaptchaCorrect = captchaService.checkIfCaptchaIsCorrect(captcha, book.getCaptchaValue());

		if (isCaptchaCorrect) {
			Book updateResult = bookService.updateBook(book);
			return new ResponseEntity<RestResponse>(RestResponse.ok(updateResult), HttpStatus.OK);
		} else {
			return new ResponseEntity<RestResponse>(RestResponse.error("Invalid captcha!!"), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> deleteBook(@PathVariable("id") String id) {
		if (StringUtils.isBlank(id)) {
			return new ResponseEntity<RestResponse>(HttpStatus.BAD_REQUEST);
		}
		bookService.deleteBook(id);
		return new ResponseEntity<RestResponse>(RestResponse.ok(Boolean.TRUE), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getBook(@PathVariable("id") String id) throws ItemNotFoundException {
		if (StringUtils.isBlank(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Book foundBook = bookService.getBookById(id);
		return new ResponseEntity<RestResponse>(RestResponse.ok(foundBook), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> getAllBooks() {
		Iterable<Book> allBooks = bookService.getAllBooks();
		return new ResponseEntity<RestResponse>(RestResponse.ok(allBooks), HttpStatus.OK);
	}

}
