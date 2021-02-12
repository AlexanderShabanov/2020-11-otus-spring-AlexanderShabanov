package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryJpaImpl implements UserRepository {
    private final EntityManager em;
    @Override
    public Optional<User> findById(Long id) {
        TypedQuery<User> query = em.createQuery(
                "select u from User u where u.id = :id"
                , User.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(User user) {
        if (user.getId() == null){
            em.persist(user);
        }
        else {
            em.merge(user);
        }

        return user.getId();
    }
}
