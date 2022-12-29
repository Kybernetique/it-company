package ru.ulstu.is.sbapp.itcompany.models.project;

import ru.ulstu.is.sbapp.itcompany.models.developer.Developer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class Project {
    @Id
    @SequenceGenerator(name = "project_seq",
            sequenceName = "project_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Long id;
    @NotBlank(message="Project name can't be null or empty")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_fk")
    private List<Developer> developers = new ArrayList<>();

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Project(String name, List<Developer> developers){
        this.name = name;
        this.developers = developers;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Developer> getDevelopers(){
        return developers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeveloper(Developer developer){
        if(!developers.contains(developer))
        {
            developers.add(developer);
            if(developer.getProject() != this)
            {
                developer.setProject(this);
            }
        }
    }

    public Developer removeDeveloper(Long developerId) {
        for (var dev : developers) {
            if (Objects.equals(dev.getId(), developerId)){
                developers.remove(dev);
                return dev;
            }
        }
        return null;
    }

    public void updateDeveloper(Long id, Developer d) {
        for (var dev : developers) {
            if(Objects.equals(dev.getId(), d.getId())) {
                dev = d;
                return;
            }
        }
    }

    public void removeAllDevelopers() {
        developers.clear();
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        Project project = (Project) p;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return name;
    }
}
