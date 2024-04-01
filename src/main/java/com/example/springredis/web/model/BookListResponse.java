package com.example.springredis.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookListResponse {
    List<BookResponse> books = new ArrayList<>();
}
