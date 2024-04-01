## spring-redis-cache
#### Simple Spring Web-MVC app using caching by Redis

Uses: Postgresql 14, Redis 7.2.4

Run:
1. docker/docker compose up
2. gradlew bootrun

#### API:

Books:
+ GET /api/book/title-author - get one book by title and author. Body: title, author. Result cached.
+ GET /api/book/category - get books list by category title. Body: categoryTitle. Result cached.
+ POST /api/book - create, body: title, author, categoryTitle.
+ PUT /api/book/{id} - update, body: title, author, categoryTitle.
+ DELETE /api/book/{id} - delete

Edit docker/docker-compose.yml and src/main/resources/application.yml to setting port, and etc.