DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;

CREATE TABLE users (
  id INTEGER PRIMARY KEY auto_increment,
  name VARCHAR(25),
  surname VARCHAR(25)
);

CREATE TABLE addresses (
  id INTEGER PRIMARY KEY NOT NULL,
  userid INTEGER NOT NULL,
  town VARCHAR(50),
  country VARCHAR(50),
  CONSTRAINT user_fk FOREIGN KEY (userid) REFERENCES users (id)
  );

INSERT INTO users (name, surname) VALUES ('Jurek', 'Ogórek');


