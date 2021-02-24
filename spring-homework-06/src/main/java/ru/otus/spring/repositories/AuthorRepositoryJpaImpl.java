package ru.otus.spring.repositories;

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
        return em.createQuery("select  a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
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
        TypedQuery<Integer> query = em.createQuery("select 1 from Author a where a.id = :id", Integer.class);
        query.setParameter("id", id);
        return query.getSingleResult() != null;
    }
}
