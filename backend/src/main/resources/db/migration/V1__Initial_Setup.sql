
CREATE TABLE customer
(
    id       bigserial primary key,
    name     text not null,
    email    text not null unique ,
    phone    text,
    password text not null,
    at       text
);
