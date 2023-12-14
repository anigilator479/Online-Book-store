INSERT INTO users (id, email, password, first_name, last_name, shipping_address, is_deleted)
VALUES (1, 'user@email.com', '$2a$10$WryuZgVDLNWVu/nhqQNfpeoH/gvWya8GnBS63TWa7r.Y7Jiktk.8.', 'userVolodya', 'Voldkovich', 'sonyachna 5/42', false);

INSERT INTO users_roles (user_id, roles_id)
VALUES (1, 1);