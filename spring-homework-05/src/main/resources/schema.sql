
drop table if exists author;
create table author(
    id bigserial primary key,
    name varchar(255),
    surName varchar(255)
);

drop table if exists genre;
create table genre(
    id bigserial primary key,
    name varchar(255)
);

drop table if exists book;
create table book(
    id bigserial primary key,
    authorId bigint references author(id),
    genreId bigint references genre(id),
    name varchar(255)
);
