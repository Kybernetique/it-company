package ru.ulstu.is.sbapp.itcompany.models.developer;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class DeveloperDTO {
    private long id;
    @NotBlank(message = "Model can't be null or empty")
    private String firstName;
    private String lastName;
    private long company;
    private long project;
    private long job;
    private String companyName;
    private String companyCountry;
    private String projectName;
    private String jobName;
    private Double jobHourlyRate;

    public DeveloperDTO() {
    }

    public DeveloperDTO(Developer developer) {
        this.id = developer.getId();
        this.firstName = developer.getFirstName();
        this.lastName = developer.getLastName();
        if (developer.getCompany() != null) {
            company = developer.getCompany().getId();
            companyName = developer.getCompany().getName();
            companyCountry = developer.getCompany().getCountry();
        }
        if (developer.getProject() != null) {
            project = developer.getProject().getId();
            projectName = developer.getProject().getName();
        }
        if (developer.getJob() != null) {
            job = developer.getJob().getID();
            jobName = developer.getJob().getName();
            jobHourlyRate = developer.getJob().getHourlyRate();
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getProject() {
        return project;
    }

    public long getCompany() {
        return company;
    }

    public long getJob() {
        return job;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public void setJob(long job) {
        this.job = job;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getJobName() {
        return jobName;
    }

    public Double getJobHourlyRate() {
        return jobHourlyRate;
    }

}
