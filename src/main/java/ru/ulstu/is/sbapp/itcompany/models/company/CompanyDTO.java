package ru.ulstu.is.sbapp.itcompany.models.company;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CompanyDTO {
    private long id;
    private String name;
    private String country;
    private Map<Long, String> developers;

    public CompanyDTO() {}

    public CompanyDTO(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.country = company.getCountry();
        developers = new HashMap<>();
        if (company.getDevelopers() != null) {
            for (var dev : company.getDevelopers()) {
                developers.put(dev.getId(), dev.getLastName());
            }
        }
    }
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() { return id; }

    public String getName() { return name; }

    public String getCountry() { return country; }

    public Map<Long, String> getDevelopers() { return developers; }

    public void setName(String name) { this.name = name; }

    public void setCountry(String country) { this.country = country; }
}
