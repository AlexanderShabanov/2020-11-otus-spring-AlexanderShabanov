package ru.otus.spring.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAllBooks() {
        return em.createQuery("select b from Book b").getResultList();
    }

    @Override
    public Optional<Book> findBookById(long id) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id = :id"
                , Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean checkBookExistsById(long id) {
        Query query = em.createQuery("select 1 from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.getSingleResult() == null ? false : true;
    }

    @Override
    public long insertBook(Book book) {
        return save(book);
    }

    @Override
    public void updateBook(Book book) {
        save(book);
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long save(Book book) {
        if (book.getId() == null){
            em.persist(book);
        }
        else {
            em.merge(book);
        }

        return book.getId();
    }
}
