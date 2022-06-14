package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.itcompany.models.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLoginIgnoreCase(String login);
}
