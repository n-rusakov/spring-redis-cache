package com.example.springredis.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpsertBookRequest {

    @NotNull(message = "Название книги должно быть указано")
    String title;

    @NotNull(message = "Автор книги должен быть указан")
    String author;

    @NotNull(message = "Категория должна быть указана")
    String categoryTitle;

}
