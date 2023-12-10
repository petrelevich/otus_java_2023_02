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

create table address
(
    id   bigserial not null primary key,
    address varchar(50)
);

create table phones
(
    id   bigserial not null primary key,
    phone varchar(50)
);
