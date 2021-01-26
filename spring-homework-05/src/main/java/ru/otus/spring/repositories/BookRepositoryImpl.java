package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.ext.BookResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookRepositoryImpl implements BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Book> findAllBooks() {
        return namedParameterJdbcOperations.query("select b.id,  a.id as author_id, a.name as author_name," +
                        "  a.surName as author_surName, g.id as genre_id, g.name as genre_name, b.name " +
                        " from book b join author a on a.id = b.authorId join genre g on g.id = b.genreId",
                new BookResultSetExtractor());
    }

    @Override
    public Optional<Book> findBookById(long id) {
        var paramsMap = Collections.singletonMap("id", id);
        Book bookToReturn = null;
        try {
            bookToReturn = namedParameterJdbcOperations.queryForObject("select b.id, b.name, a.id as author_id, a.name as author_name," +
                            "  a.surName as author_surName, g.id as genre_id, g.name as genre_name" +
                            " from book b join author a on a.id = b.authorId join genre g on g.id = b.genreId where b.id = :id", paramsMap,
                    new BookRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            log.debug(String.format("Книга с ID = %d не найдена! stacktrace: %s", id, exception.getStackTrace()));
        }
        return Optional.ofNullable(bookToReturn);
    }

    @Override
    public void insertBook(Book book) {
        namedParameterJdbcOperations.update("insert into book (id, name, authorId, genreId) values (:id, :name, :author_id, :genre_id)", book.toMap());
    }

    @Override
    public void updateBook(Book book) {
        namedParameterJdbcOperations.update("update book set name = :name, authorId = :author_id, genreId = :genre_id where id = :id", book.toMap());
    }

    @Override
    public void deleteBookById(long id) {
        var paramsMap = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete book where id = :id", paramsMap);

    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            return new Book(rs.getLong(1), new Author(rs.getLong(3), rs.getString(4), rs.getNString(5)),
                    new Genre(rs.getLong(6), rs.getString(7)), rs.getString(2));
        }
    }

}
