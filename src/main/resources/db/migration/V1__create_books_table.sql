
create sequence book_id_seq start with 1 increment by 1;

create table books (
    book_id bigint not null default nextval('book_id_seq'),
    title varchar(255),
    author varchar(255),
    publication_year varchar(255),
    genre varchar(255),
    isbn varchar(255) unique,
    primary key (book_id)
);
