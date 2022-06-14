package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.itcompany.models.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
