package ru.otus.spring.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

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
        return Optional.of(returnedGenre);
    }

    @Override
    public void insertGenre(Genre genre) {
        namedParameterJdbcOperations.update("insert into genre (id, name) values (:id, :name)", genre.toMap());
    }

    @Override
    public void updateGenre(Genre genre) {
        namedParameterJdbcOperations.update("update genre set name = :name where id = :id", genre.toMap());
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(rs.getLong(1), rs.getString(2));
        }
    }

}
