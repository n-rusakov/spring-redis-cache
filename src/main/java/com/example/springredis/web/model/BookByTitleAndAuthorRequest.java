package com.example.springredis.web.model;

import lombok.Data;

@Data
public class BookByTitleAndAuthorRequest {

    String title;

    String author;
}
