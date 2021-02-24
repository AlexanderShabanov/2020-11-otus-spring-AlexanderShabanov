insert into author (name, surName)
values ('Александр', 'Пушкин');
insert into author (name, surName)
values ('Александр', 'Дюма');


insert into genre (name)
values ('комедия');

insert into genre (name)
values ('трагедия');

insert into genre (name)
values ('непонятная хрень');

insert into book (name, authorId)
values ('Три мушкетера', 2);

insert into bookToGenresLink (bookId, genreId)
values (1, 1);
insert into bookToGenresLink (bookId, genreId)
values (1, 3);

insert into user (name)
values ('user12');