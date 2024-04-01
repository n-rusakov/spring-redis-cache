package com.example.springredis.web.model;

import lombok.Data;

@Data
public class BookResponse {

    private Long id;

    private String title;

    private String author;

    private String categoryTitle;

}
