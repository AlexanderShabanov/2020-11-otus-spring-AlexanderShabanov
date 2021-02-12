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

insert into genre (name)
values ('сказка');

insert into book (name, authorId)
values ('Три мушкетера', 2);

insert into book (name, authorId)
values ('Три поросенка', 2);

insert into bookToGenresLink (bookId, genreId)
values (1, 2);

insert into bookToGenresLink (bookId, genreId)
values (2, 1);
insert into bookToGenresLink (bookId, genreId)
values (2, 4);

insert into user (name)
values ('user1');

insert into user (name)
values ('user2');

insert into comment (bookId, userId, comment)
values (2, 1, 'крутой блокбастер!!');

insert into comment (bookId, userId, comment)
values (2, 2, 'шняга полная');