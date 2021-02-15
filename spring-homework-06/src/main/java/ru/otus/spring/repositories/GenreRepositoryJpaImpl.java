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
        return em.createQuery("select  g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findGenreById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
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
        TypedQuery<Integer> query = em.createQuery("select 1 from Genre g where g.id = :id", Integer.class);
        query.setParameter("id", id);
        return query.getSingleResult() != null;
    }
}
