package com.akin.libapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akin.libapp.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
	
}
