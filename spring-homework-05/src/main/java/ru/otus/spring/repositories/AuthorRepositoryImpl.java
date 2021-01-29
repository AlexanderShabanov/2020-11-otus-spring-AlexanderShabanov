package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.transformer.EntityToMapTransformer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorRepositoryImpl implements AuthorRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final EntityToMapTransformer entityToMapTransformer;


    @Override
    public List<Author> findAllAuthors() {
        return namedParameterJdbcOperations.query("select a.id, a.name, a.surName from author a", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        Map<String, Long> paramMap = Collections.singletonMap("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                "select a.id, a.name, a.surName from author a where id = :id", paramMap, new AuthorRowMapper()));
    }

    @Override
    public void insertAuthor(Author author) {
        namedParameterJdbcOperations.update("insert into author (id, name, surName) values (:id, :name, :surName)",
                entityToMapTransformer.toMap(author));
    }

    @Override
    public void updateAuthor(Author author) {
        namedParameterJdbcOperations.update("update author set name = :name, surName = surName where id = :id",
                entityToMapTransformer.toMap(author));
    }

    @Override
    public boolean checkAuthorExistsById(long id) {
        var paramsMap = Collections.singletonMap("id", id);
        Long result = null;
        try {
            result = namedParameterJdbcOperations.queryForObject("select 1 from author a where a.id = :id", paramsMap,
                    Long.class);
        } catch (EmptyResultDataAccessException exception) {
            log.debug(String.format("Автор с ID = %d не найден! stacktrace: %s", id, Arrays.toString(exception.getStackTrace())));
        }
        return (result != null);
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(rs.getLong(1), rs.getString(2), rs.getString(3));
        }
    }

}
