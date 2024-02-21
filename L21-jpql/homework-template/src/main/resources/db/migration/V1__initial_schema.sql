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

create table phone
(
    id   UUID primary key not null,
    number varchar(50),
    client_id bigint references client (id)
);

create table address
(
    id   UUID primary key not null,
    street varchar(50),
    client_id bigint references client (id)
);