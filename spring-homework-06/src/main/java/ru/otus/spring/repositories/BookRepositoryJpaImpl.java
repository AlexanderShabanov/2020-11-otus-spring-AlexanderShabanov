package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAllBooks() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public boolean checkBookExistsById(long id) {
        TypedQuery<Integer> query = em.createQuery("select 1 from Book b where b.id = :id", Integer.class);
        query.setParameter("id", id);
        return query.getSingleResult() != null;
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
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }

        return book.getId();
    }
}
