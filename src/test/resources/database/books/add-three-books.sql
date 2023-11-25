INSERT INTO books(id, title, author, isbn, price, description, cover_image)
VALUES (1, 'Clean Code', 'Martin', '123123123', 99, 'Great book', 'img_src'),
       (2, 'How to cook', 'Grandma', '777777777', 48, 'Not bad book', 'img_src'),
       (3, 'Ukrainian literature 8 grade', 'Avramenko', '666666666', 10, 'Awful book', 'img_src');

INSERT INTO books_categories(book_id, categories_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);