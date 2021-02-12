package ru.otus.spring.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class GenreRepositoryJpaImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAllGenres() {
        return em.createQuery("select  g from Genre g").getResultList();
    }

    @Override
    public Optional<Genre> findGenreById(long id) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.id = :id"
                , Genre.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insertGenre(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        em.merge(genre);
    }

    @Override
    public boolean checkGenreExistsById(long id) {
        Query query = em.createQuery("select 1 from Genre g where g.id = :id");
        query.setParameter("id", id);
        return query.getSingleResult() == null ? false : true;
    }
}
