CREATE TABLE public.phone
(
    id bigserial NOT NULL,
    client_id bigint NOT NULL,
    phone_number character varying(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_phone FOREIGN KEY (client_id)
        REFERENCES public.client (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT uc_phone_client UNIQUE (client_id, phone_number)
);