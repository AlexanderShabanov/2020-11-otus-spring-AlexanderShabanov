#Приложение, хранящее информацию о книгах в библиотеке.
_Цель: использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных._
##Команды Shell
Есть предзаполнение авторов, жанров и книг.

`af` - вывод списка всех авторов

`gf` - аналогично для жанров

`bf` - для книг

`afid`, `gfid`, `bfid` - аналогично поиск по ID

`ai`, `gi` - добавление сущности автора и жанра соответственно
для добавления сущности книги существует 2 альтернативные команды

`bi_id` - добавление книги со ссылкой на существующие связанные сущности автора и жанра по id

`bi_full` - добавление книги с полным указанием связанных сущностей. В случае, если сущность не найдена, она будет добавлена.
аналогичный комплект команд существует для обновления

`au`, `gu` - изменение автора и жанра соответственно

`bu_id`, `bu_full` - изменение книги со ссылкой по ID или целиком связанными сущностями.

`bd_id` - удаление книги