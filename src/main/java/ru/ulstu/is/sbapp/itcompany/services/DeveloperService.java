package ru.ulstu.is.sbapp.itcompany.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.developer.DeveloperDTO;
import ru.ulstu.is.sbapp.itcompany.models.developer.Developer;
import ru.ulstu.is.sbapp.itcompany.repositories.DeveloperRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final ValidatorUtil validatorUtil;
    private final CompanyService companyService;
    private final JobService jobService;
    private final ProjectService projectService;

    public DeveloperService(DeveloperRepository developerRepository, ValidatorUtil validatorUtil, CompanyService companyService,
                      JobService jobService, ProjectService projectService) {
        this.developerRepository = developerRepository;
        this.validatorUtil = validatorUtil;
        this.projectService = projectService;
        this.jobService = jobService;
        this.companyService = companyService;
    }

    @Transactional
    public Developer addDeveloper(String firstName, String lastName, long companyId, long jobId,long projectId) {
        if(!StringUtils.hasText(firstName) || companyId == 0 || projectId == 0) {
            throw new IllegalArgumentException("Developer's data is null or empty");
        }
        var company = companyService.findCompany(companyId);
        var job = jobService.findJob(jobId);
        var project = projectService.findProject(projectId);
        var developer = new Developer(firstName, lastName);
        developer.setCompany(company);
        developer.setJob(job);
        developer.setProject(project);
        validatorUtil.validate(developer);
        return developerRepository.save(developer);
    }

    @Transactional
    public DeveloperDTO addDeveloper(DeveloperDTO developerDTO) {
        return new DeveloperDTO(addDeveloper(developerDTO.getFirstName(), developerDTO.getLastName(),
                developerDTO.getCompany(), developerDTO.getJob(), developerDTO.getProject()));
    }


    @Transactional(readOnly = true)
    public Developer findDeveloper(Long id) {
        final Optional<Developer> developer = developerRepository.findById(id);
        return developer.orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Developer> findAllDevelopers() {
        return developerRepository.findAll();
    }

    @Transactional
    public Developer updateDeveloper(Long id, String firstName, String lastName, Long companyId,
                                     Long jobId, Long projectId) {
        if(!StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("Developer's data is null or empty");
        }
        final Developer currentDeveloper = findDeveloper(id);
        var company = companyService.findCompany(companyId);
        var job = jobService.findJob(jobId);
        var project = projectService.findProject(projectId);
        currentDeveloper.setFirstName(firstName);
        currentDeveloper.setLastName(lastName);
        if (currentDeveloper.getCompany().getId().equals(companyId)) {
            currentDeveloper.getCompany().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getCompany().removeDeveloper(id);
            currentDeveloper.setCompany(company);
        }

        if (currentDeveloper.getProject().getId().equals(projectId)) {
            currentDeveloper.getProject().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getProject().removeDeveloper(id);
            currentDeveloper.setProject(project);
        }

        if (currentDeveloper.getJob().getID().equals(jobId)) {
            currentDeveloper.getJob().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getJob().removeDeveloper(id);
            currentDeveloper.setJob(job);
        }
        validatorUtil.validate(currentDeveloper);
        return developerRepository.save(currentDeveloper);
    }

    @Transactional
    public DeveloperDTO updateDeveloper(DeveloperDTO developerDto) {
        return new DeveloperDTO(updateDeveloper(developerDto.getId(), developerDto.getFirstName(),
                developerDto.getLastName(), developerDto.getCompany(), developerDto.getJob(), developerDto.getProject()));
    }

    @Transactional
    public Developer deleteDeveloper(Long id) {
        final Developer currentDeveloper = findDeveloper(id);
        developerRepository.delete(currentDeveloper);
        return currentDeveloper;
    }

    @Transactional
    public void deleteAllDevelopers() {
        developerRepository.deleteAll();
    }
}
