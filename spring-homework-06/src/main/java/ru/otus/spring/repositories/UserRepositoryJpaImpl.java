package ru.otus.spring.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.User;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpaImpl implements UserRepository {
    private final EntityManager em;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Long save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }

        return user.getId();
    }
}
