drop table if exists test;
drop table if exists client;
drop table if exists manager;
create table test
(
    id   int,
    name varchar(50)
);
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
create table manager
(
    no   bigserial not null primary key,
    label varchar(50),
    param1 varchar(50)
);
