package ru.ulstu.is.sbapp.itcompany.models.job;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

public class JobDTO {
    private Long id;

    @NotBlank(message = "Name can't be null or empty")
    private String name;

    private Double hourlyRate;

    private Map<Long, String> developers;


    public JobDTO() {
    }

    public JobDTO(Job job) {
        this.id = job.getID();
        this.name = job.getName();
        this.hourlyRate = job.getHourlyRate();
        developers = new HashMap<>();
        if (job.getDevelopers() != null) {
            for (var dev : job.getDevelopers()) {
                developers.put(dev.getId(), dev.getLastName());
            }
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
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
}
