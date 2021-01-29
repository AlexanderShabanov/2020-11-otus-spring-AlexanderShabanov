package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.transformer.EntityToMapTransformer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreRepositoryImpl implements GenreRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final EntityToMapTransformer entityToMapTransformer;

    @Override
    public List<Genre> findAllGenres() {
        return namedParameterJdbcOperations.query("select id, name from genre", new GenreRowMapper());
    }


    @Override
    public Optional<Genre> findGenreById(long id) {
        Map<String, Long> paramMap = Collections.singletonMap("id", id);
        Genre returnedGenre;
        returnedGenre = namedParameterJdbcOperations.queryForObject("select id, name from genre where id = :id",
                paramMap, new GenreRowMapper());
        return Optional.ofNullable(returnedGenre);
    }

    @Override
    public void insertGenre(Genre genre) {
        namedParameterJdbcOperations.update("insert into genre (id, name) values (:id, :name)",
                entityToMapTransformer.toMap(genre));
    }

    @Override
    public void updateGenre(Genre genre) {
        namedParameterJdbcOperations.update("update genre set name = :name where id = :id",
                entityToMapTransformer.toMap(genre));
    }

    @Override
    public boolean checkGenreExistsById(long id) {
        var paramsMap = Collections.singletonMap("id", id);
        Long result = null;
        try {
            result = namedParameterJdbcOperations.queryForObject("select 1 from genre g where g.id = :id", paramsMap,
                    Long.class);
        } catch (EmptyResultDataAccessException exception) {
            log.debug(String.format("Жанр с ID = %d не найден! stacktrace: %s", id, Arrays.toString(exception.getStackTrace())));
        }
        return (result != null);
    }


    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(rs.getLong(1), rs.getString(2));
        }
    }

}
