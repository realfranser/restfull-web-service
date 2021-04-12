DROP SCHEMA IF EXISTS booknet;
CREATE SCHEMA booknet;
USE booknet;
CREATE USER 'booknet'@'localhost' IDENTIFIED BY 'booknet';


CREATE TABLE users (
  user_id INT NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  born_date DATE NOT NULL,
  reg_date DATE NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE friendship (
  friendship_id INT NOT NULL,
  user_id INT NOT NULL,
  friend_id INT NOT NULL,
  PRIMARY KEY (friendship_id),
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(friend_id) REFERENCES users(user_id)
  ON DELETE CASCADE
);

CREATE TABLE books (
  isbn INT NOT NULL,
  book_name VARCHAR(50) NOT NULL,
  authors_name VARCHAR(50) NOT NULL,
  categoty VARCHAR(50)NOT NULL,
  PRIMARY KEY (isbn)
);

CREATE TABLE read_books (
  read_id INT NOT NULL,
  user_id INT NOT NULL,
  isbn INT NOT NULL,
  user_rating INT NOT NULL,
  read_date DATE NOT NULL,
  PRIMARY KEY (read_id),
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(isbn) REFERENCES books(isbn)
  ON DELETE CASCADE
);


