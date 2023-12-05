INSERT INTO books(id, title, author, isbn, price, description, cover_image)
VALUES (1, 'Clean Code', 'Martin', '123123123', 99, 'Great book', 'img_src');

INSERT INTO books_categories(categories_id, book_id)
VALUES (1, 1);
