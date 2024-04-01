CREATE SCHEMA IF NOT EXISTS books_schema;

CREATE TABLE IF NOT EXISTS books_schema.categories
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS books_schema.books
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES books_schema.categories(id)
        ON DELETE RESTRICT
);