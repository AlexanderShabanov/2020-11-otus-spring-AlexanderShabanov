package ru.otus.spring.repositories.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            Book book = new Book(id, new Author(rs.getLong("author_id"), rs.getString("author_name"), rs.getString("author_surName")), new Genre(rs.getLong("genre_id"), rs.getString("genre_name")), rs.getString("name"));
            books.add(book);
        }
        return books;
    }
}
