# Products schema

# --- !Ups

CREATE TABLE products (
    id varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255),
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE IF EXISTS products;