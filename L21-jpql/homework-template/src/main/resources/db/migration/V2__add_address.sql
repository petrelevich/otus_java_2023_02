CREATE TABLE address
(
    id   bigserial not null primary key,
    client_id bigint not null,
    address_line varchar(150) NOT NULL,
    CONSTRAINT fk_address_client FOREIGN KEY (client_id) REFERENCES public.client (id)
);