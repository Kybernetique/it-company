package ru.ulstu.is.sbapp.itcompany.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.project.ProjectDTO;
import ru.ulstu.is.sbapp.itcompany.models.project.Project;
import ru.ulstu.is.sbapp.itcompany.repositories.ProjectRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;
    private final ValidatorUtil validatorUtil;

    public ProjectService(ProjectRepository projectRepository, ValidatorUtil validatorUtil) {
        this.projectRepository = projectRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Project addProject(String name) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Project name is null or empty");
        }
        final Project project = new Project(name);
        validatorUtil.validate(project);
        return projectRepository.save(project);
    }

    @Transactional
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        return new ProjectDTO(addProject(projectDTO.getName()));
    }

    @Transactional(readOnly = true)
    public Project findProject(Long id) {
        final Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project updateProject(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Project name is null or empty");
        }
        final Project currentProject = findProject(id);
        currentProject.setName(name);
        validatorUtil.validate(currentProject);
        return projectRepository.save(currentProject);
    }

    @Transactional
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        return new ProjectDTO(updateProject(projectDTO.getId(), projectDTO.getName()));
    }

    @Transactional
    public Project deleteProject(Long id) {
        final Project currentProject = findProject(id);
        projectRepository.delete(currentProject);
        return currentProject;
    }

    @Transactional
    public void deleteAllProjects() throws InProjectFoundDevelopersException {
        var projects = findAllProjects();
        for (var project : projects) {
            if (project.getDevelopers().size() > 0) {
                throw new InProjectFoundDevelopersException("В проекте " + project.getName() + " есть разработчики");
            }
        }
        projectRepository.deleteAll();
    }
}
