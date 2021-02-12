
drop table if exists author;
create table author(
    id bigserial AUTO_INCREMENT primary key,
    name varchar(255),
    surName varchar(255)
);

drop table if exists genre;
create table genre(
    id bigserial AUTO_INCREMENT primary key,
    name varchar(255)
);


drop table if exists book;
create table book(
    id bigserial AUTO_INCREMENT primary key,
    authorId bigint references author(id),
    name varchar(255)
);

drop table if exists bookToGenresLink;
create table bookToGenresLink(
    bookId bigint references book(id),
    genreId bigint references genre(id)
);

drop table if exists user;
create table user(
    id bigserial AUTO_INCREMENT primary key,
    name varchar(255)
);


drop table if exists comment;
create table comment(
    id bigserial AUTO_INCREMENT primary key,
    bookId bigint references book(id),
    userId bigint references user(id),
    comment varchar(255)
);
