package ru.otus.spring.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findAllAuthors() {
        return em.createQuery("select  a from Author a").getResultList();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.id = :id"
                , Author.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insertAuthor(Author author) {
        em.persist(author);
    }

    @Override
    public void updateAuthor(Author author) {
        em.merge(author);
    }

    @Override
    public boolean checkAuthorExistsById(long id) {
        Query query = em.createQuery("select 1 from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.getSingleResult() == null ? false : true;
    }
}
