package com.example.springredis.service;

import com.example.springredis.entity.Book;

import java.util.List;

public interface BookService {

    Book findById(Long id);

    Book findByTitleAuthor(String title, String author);

    List<Book> findByCategory(String category);

    Book create(Book book);

    Book update(Book book);

    void deleteById(Long id);

}
