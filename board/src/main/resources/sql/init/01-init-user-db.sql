CREATE USER IF NOT EXISTS 'book_user'@'%' INDENTIFIED by 'bookpass';
GRANT ALL PRIVILEGES ON book_db.* TO 'book_user'@'%';
FLUSH PRIVILEGES;