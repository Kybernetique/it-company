package ru.ulstu.is.sbapp.itcompany.models.project;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.itcompany.services.ProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id){
        return new ProjectDTO(projectService.findProject(id));
    }

    @GetMapping("/")
    public List<ProjectDTO> getProjects(){
        return projectService.findAllProjects().stream()
                .map(ProjectDTO::new)
                .toList();
    }

    @PostMapping("/")
    public ProjectDTO createProject(@RequestParam("name") String name) {
        return new ProjectDTO(projectService.addProject(name));
    }

    @PatchMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody @Valid ProjectDTO projectDTO){
        return new ProjectDTO(projectService.updateProject(id, projectDTO.getName()));
    }

    @DeleteMapping("/{id}")
    public ProjectDTO deleteProject(@PathVariable Long id) {
        return new ProjectDTO(projectService.deleteProject(id));
    }
}
