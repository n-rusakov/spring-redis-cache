package com.example.springredis.mapper;

import com.example.springredis.entity.Book;
import com.example.springredis.entity.Category;
import com.example.springredis.service.impl.BookServiceImpl;
import com.example.springredis.web.model.BookListResponse;
import com.example.springredis.web.model.BookResponse;
import com.example.springredis.web.model.UpsertBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final BookServiceImpl bookService;

    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategoryTitle(book.getCategory().getTitle());

        return response;
    }

    public List<BookResponse> toResponseList(List<Book> books) {
        return books.stream().map(this::toResponse).toList();
    }

    public BookListResponse toBookListResponse(List<Book> books) {
        BookListResponse response = new BookListResponse();

        response.setBooks(toResponseList(books));

        return response;
    }

    public Book toBook(UpsertBookRequest request) {
        Book book = new Book();
        Category category = bookService.findCategoryOrCreate(
                request.getCategoryTitle());

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(category);

        return book;
    }

    public Book toBook(Long id, UpsertBookRequest request) {
        Book book = toBook(request);
        book.setId(id);
        return book;
    }


}
