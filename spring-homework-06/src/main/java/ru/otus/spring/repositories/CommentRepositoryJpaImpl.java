package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class CommentRepositoryJpaImpl implements CommentRepository {
    private final EntityManager em;
    @Override
    public List<Comment> findAll() {
        return em.createQuery("select  c from Comment c").getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.user where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.id = :id"
                , Comment.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public long save(Comment comment) {
        if (comment.getId() == null){
            em.persist(comment);
        }
        else {
            em.merge(comment);
        }

        return comment.getId();
    }

}
