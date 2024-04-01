package com.example.springredis.service.impl;

import com.example.springredis.configuration.properties.AppCacheProperties;
import com.example.springredis.entity.Book;
import com.example.springredis.entity.Category;
import com.example.springredis.exception.BookNotFoundException;
import com.example.springredis.repository.BookRepository;
import com.example.springredis.repository.CategoryRepository;
import com.example.springredis.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(MessageFormat.format(
                        "Книга с id {0} не найдена", id)));
    }

    @Override
    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR,
            key = "#title + #author")
    public Book findByTitleAuthor(String title, String author) {
        log.info("findByTitleAuthor execute");

        return bookRepository.findByTitleAndAuthor(title, author).orElseThrow(
                () -> new BookNotFoundException(MessageFormat.format(
                        "Книга с названием {0} и автором {1} не найдена",
                        title, author)));

    }

    @Override
    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY, key = "#categoryTitle")
    public List<Book> findByCategory(String categoryTitle) {
        log.info("findByCategory execute");

        Category category = categoryRepository.findByTitle(categoryTitle).orElseThrow(
                () -> new BookNotFoundException(MessageFormat.format(
                        "Категория {0} не существует", categoryTitle)));

        return bookRepository.findByCategoryId(category.getId());

    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY,
                    key = "#book.category.title", beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR,
                    key ="#book.title + #book.author", beforeInvocation = true)
    })
    public Book create(Book book) {
        return bookRepository.save(book);
    }


    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY,
                    allEntries = true, beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR,
                    allEntries = true, beforeInvocation = true)
    })
    @Override
    public Book update(Book book) {
        Book existedBook = bookRepository.findById(book.getId()).orElseThrow(
                () -> new BookNotFoundException(MessageFormat.format(
                        "Книга с id {0} не найдена", book.getId())));

        existedBook.setTitle(book.getTitle());
        existedBook.setAuthor(book.getAuthor());
        existedBook.setCategory(book.getCategory());

        return bookRepository.save(existedBook);
    }


    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY,
                    allEntries = true, beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR,
                    allEntries = true, beforeInvocation = true)
    })
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }


    public Category findCategoryOrCreate(String categoryTitle) {
        var category = categoryRepository.findByTitle(categoryTitle);
        if (category.isPresent()) {
            return category.get();
        }

        Category newCategory = new Category();
        newCategory.setTitle(categoryTitle);
        categoryRepository.save(newCategory);

        return newCategory;
    }


}
