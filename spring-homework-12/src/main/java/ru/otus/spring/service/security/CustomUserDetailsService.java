package ru.otus.spring.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.User;
import ru.otus.spring.repositories.UserRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final static String USER_NOT_FOUND_MESSAGE = "Пользователь %s не найден!";

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    log.debug("вызов loadUserByUsername для {}", userName);
    User user = userRepository.findByName(userName)
            .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userName)));
    return org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPassword())
            .roles(user.getRoles()).build();
  }

}
