package ru.ulstu.is.sbapp.itcompany.models.company;

import ru.ulstu.is.sbapp.itcompany.models.developer.Developer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//Один-ко-многим к автомобилям
//Один владелец может иметь несколько машин
@Entity
public class Company {
    @Id
    @SequenceGenerator(name = "company_seq",
            sequenceName = "company_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    private Long id;
    @NotBlank(message="Company name can't be null or empty")
    private String name;
    @NotBlank(message="Company country can't be null or empty")
    private String country;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_fk")
    private List<Developer> developers = new ArrayList<>();

    public Company(){ }

    public Company(String name, String country){
        this.name = name;
        this.country = country;
    }

    public Company(String firstName, String lastName, List<Developer> developers){
        this.name = firstName;
        this.country = lastName;
        this.developers = developers;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry(){ return country; }

    public List<Developer> getDevelopers(){
        return developers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDeveloper(Developer developer){
        if(!developers.contains(developer))
        {
            developers.add(developer);
            if(developer.getCompany() != this)
            {
                developer.setCompany(this);
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

    public void removeAllDevelopers() {
        developers.clear();
    }

    public void updateDeveloper(Long id, Developer d) {
        for (var dev : developers) {
            if(Objects.equals(dev.getId(), d.getId())) {
                dev = d;
                return;
            }
        }
    }

    @Override
    public boolean equals(Object c) {
        if (this == c) return true;
        if (c == null || getClass() != c.getClass()) return false;
        Company company = (Company) c;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + "$" + country;
    }
}
