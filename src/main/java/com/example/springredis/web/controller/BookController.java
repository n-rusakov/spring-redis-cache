package com.example.springredis.web.controller;

import com.example.springredis.entity.Book;
import com.example.springredis.mapper.BookMapper;
import com.example.springredis.service.BookService;
import com.example.springredis.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;


    @GetMapping("/title-author")
    public ResponseEntity<BookResponse> findByTitleAndAuthor(
            @RequestBody BookByTitleAndAuthorRequest request){

        return ResponseEntity.ok(bookMapper.toResponse(
                bookService.findByTitleAuthor(request.getTitle(), request.getAuthor())));
    }

    @GetMapping("/category")
    public ResponseEntity<BookListResponse> findByCategoryTitle(
            @RequestBody BookByCategoryTitleRequest request) {

        return ResponseEntity.ok(bookMapper.toBookListResponse(
                bookService.findByCategory(request.getCategoryTitle())));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody UpsertBookRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookMapper.toResponse(bookService.create(bookMapper.toBook(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                               @RequestBody UpsertBookRequest request) {

        Book book = bookService.update(bookMapper.toBook(id, request));

        return ResponseEntity.ok(bookMapper.toResponse(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        bookService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
