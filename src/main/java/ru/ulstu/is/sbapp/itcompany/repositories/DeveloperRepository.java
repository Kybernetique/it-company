package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.itcompany.models.developer.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
