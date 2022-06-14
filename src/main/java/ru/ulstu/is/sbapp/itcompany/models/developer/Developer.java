package ru.ulstu.is.sbapp.itcompany.models.developer;
import ru.ulstu.is.sbapp.itcompany.models.job.Job;
import ru.ulstu.is.sbapp.itcompany.models.project.Project;
import ru.ulstu.is.sbapp.itcompany.models.company.Company;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Developer {
    @Id
    @SequenceGenerator(name = "developer_seq",
            sequenceName = "developer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
    private Long id;
    @NotBlank (message="Developer's first name can't be null or empty")
    private String firstName;
    @NotBlank (message="Developer's last name can't be null or empty")
    private String lastName;

    @ManyToOne()
    @JoinColumn(name = "company_fk")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "project_fk")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "job_fk")
    private Job job;

    public Developer(){
    }

    public Developer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Company getCompany(){ return company; }

    public Project getProject(){ return project; }

    public Job getJob() {return job;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCompany(Company company){
        this.company = company;
        if(!company.getDevelopers().contains(this)){
            company.setDeveloper(this);
        }
    }
    public void setProject(Project project){
        this.project = project;
        if(!project.getDevelopers().contains(this)){
            project.setDeveloper(this);
        }
    }

    public void setJob(Job job) {
        this.job = job;
        if (!job.getDevelopers().contains(this)) {
            job.setDeveloper(this);
        }
    }

    public void removeCompany() {
        if(company.removeDeveloper(getId()) != null)
        {
            company.removeDeveloper(getId());
        }
        company = null;
    }

    public void removeProject() {
        if(project.removeDeveloper(getId()) != null)
        {
            project.removeDeveloper(getId());
        }
        project = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
