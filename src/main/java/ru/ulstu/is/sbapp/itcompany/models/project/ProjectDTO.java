package ru.ulstu.is.sbapp.itcompany.models.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class ProjectDTO {
    private long id;
    private String name;
    private Map<Long, String> developers;

    public ProjectDTO() {}

    public ProjectDTO(Project project){
        this.id = project.getId();
        this.name = project.getName();
        developers = new HashMap<>();
        if (project.getDevelopers() != null) {
            for (var developer : project.getDevelopers()) {
                developers.put(developer.getId(), developer.getLastName());
            }
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() { return id; }

    public String getName() { return name; }

    public Map<Long, String> getDevelopers() { return developers; }

    public void setName(String name) { this.name = name; }
}
