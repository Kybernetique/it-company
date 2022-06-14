package ru.ulstu.is.sbapp.itcompany.models.job;

import ru.ulstu.is.sbapp.itcompany.models.developer.Developer;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Developer> developers;

    public Job() {
    }

    public Job(String name, Double hourlyRate) {

        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Job(String name, Double hourlyRate, List<Developer> developers) {

        this.name = name;
        this.hourlyRate = hourlyRate;
        this.developers = developers;
    }

    public Long getID() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDeveloper(Developer developer) {
        developers.add(developer);
    }

    public void updateDeveloper(Long id, Developer d) {
        for (var dev : developers) {
            if(Objects.equals(dev.getId(), d.getId())) {
                dev = d;
                return;
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

    @Override
    public String toString() {
        return "Должность: " + name;
    }
}
