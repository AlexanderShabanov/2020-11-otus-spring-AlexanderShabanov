package ru.otus.spring.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.models.Author;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Author> findAllAuthors() {
        return namedParameterJdbcOperations.query("select a.id, a.name, a.surName from author a", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        Map<String, Long> paramMap = Collections.singletonMap("id", id);
        return Optional.of(namedParameterJdbcOperations.queryForObject(
                "select a.id, a.name, a.surName from author a where id = :id", paramMap, new AuthorRowMapper()));
    }

    @Override
    public void insertAuthor(Author author) {
        namedParameterJdbcOperations.update("insert into author (id, name, surName) values (:id, :name, :surName)", author.toMap());
    }

    @Override
    public void updateAuthor(Author author) {
        namedParameterJdbcOperations.update("update author set name = :name, surName = surName where id = :id", author.toMap());
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(rs.getLong(1), rs.getString(2), rs.getString(3));
        }
    }

}
