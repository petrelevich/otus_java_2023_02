-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);


CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE,
    name VARCHAR(255)
);